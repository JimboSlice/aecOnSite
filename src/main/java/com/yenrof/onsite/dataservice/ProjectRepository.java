package com.yenrof.onsite.dataservice;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
//import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

//import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.yenrof.onsite.model.*;

@ApplicationScoped
public class ProjectRepository {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	public Project findById(long id) {
		Project project = em.find(Project.class, id);
		return project;
	}

	public Person_HAS_Project findPersonProject(Project project, Company company) {
		Person dbPerson = null;
		Set<Person> persons = project.getPersons();
		if (persons != null) {
			Iterator<Person> itr = persons.iterator();
			while (itr.hasNext()) {
				Person person = itr.next();
				log.info("findPersonProject:" + person.getUsername());
				dbPerson = findByUserName(person.getUsername());
				break; // only one Person at a time
			}
		}
		Project dbProject = null;
		Set<Project> projects = company.getProjects();
		if (projects != null) {
			Iterator<Project> projectItr = projects.iterator();
			while (projectItr.hasNext()) {
				project = projectItr.next();
				log.info("findPersonProject:" + project.getProjectName());
				dbProject = findByProjectNumber(project, company);
				break;
			}
		}
		String select = "SELECT * FROM mydb.Person_HAS_Project where personId=:personId and projectId=:projectId";
		Query query = em.createNativeQuery(select, Person_HAS_Project.class);
		query.setParameter("personId", dbPerson.getPersonId());
		query.setParameter("projectId", dbProject.getProjectId());
		return (Person_HAS_Project) query.getSingleResult();

	}

	public Project findByProjectNumber(Project project, Company company) {

		Company dbCompany = findByCompanyName(company.getName());
		log.info("findByProjectNumber: projectname " + project.getProjectName());
		log.info("findByProjectNumber: companyname " + dbCompany.getName());
		log.info("findByProjectNumber: companyid " + dbCompany.getCompanyId());

		String number = project.getProjectNumber();
		long companyId = dbCompany.getCompanyId();
		String select = "SELECT * FROM Project  INNER JOIN Company ON Company.companyId = "
				+ "Project.Company_companyId where Project.projectNumber=:number and Company.companyId=:companyId";
		Query query = em.createNativeQuery(select, Project.class);
		query.setParameter("number", number);
		query.setParameter("companyId", companyId);
		return (Project) query.getSingleResult();
	}

	public Person findByUserName(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
		Root<Person> Inspector = criteria.from(Person.class);
		criteria.select(Inspector).where(
				cb.equal(Inspector.get("username"), username));
		return em.createQuery(criteria).getSingleResult();
	}

	public Company findByCompanyName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteria = cb.createQuery(Company.class);
		Root<Company> Company = criteria.from(Company.class);
		criteria.select(Company).where(cb.equal(Company.get("name"), name));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<Project> findAllOrderedByName() {
		String select = "select * from Project order by projectName";
		return (List<Project>) em.createNativeQuery(select).getResultList();
	}

	public List<Company> findAllCompaniesOrderedByName() {
		String select = "select * from Company order by name";
		return (List<Company>) em.createNativeQuery(select).getResultList();
	}

	public List<Project> findAllCompanyProjectsOrderedByName(long companyId) {
		String select = "SELECT * FROM Project  INNER JOIN Company ON Company.companyId = Project.Company_companyId where Company.companyId=:companyId";
		Query query = em.createNativeQuery(select, Project.class);
		query.setParameter("companyId", companyId);
		return (List<Project>) query.getResultList();
	}

	public void addPersonToProject(Company company) throws Exception {
		log.info("Adding person for company " + company.getName());
		// find the company in db - should throw if not found
		Company dbCompany = findByCompanyName(company.getName());
		Project project = null;
		Set<Project> projects = company.getProjects();
		if (projects != null) {
			Iterator<Project> projectsItr = projects.iterator();
			while (projectsItr.hasNext()) {
				project = projectsItr.next();
				// validate project in db
				Project dbProject = this
						.findByProjectNumber(project, dbCompany);
				if (dbProject != null) { // found a project = else throw
					log.info("adding person for project:"
							+ project.getProjectName());
					// Project projectParm= em.merge(project);
					Set<Person> persons = project.getPersons();
					if (persons != null) {
						Iterator<Person> personsItr = persons.iterator();
						while (personsItr.hasNext()) {
							Person person = personsItr.next();
							Person dbPerson = null;
							try {
								dbPerson = findByUserName(person.getUsername());
								// existing user - don't add to Project just add
								// association
								Person_HAS_Project php = new Person_HAS_Project();
								php.setPersonId(dbPerson.getPersonId());
								php.setProjectId(dbProject.getProjectId());
								em.persist(php);
							} catch (NoResultException e) {
								dbProject.addPerson(person);
							}
						}
					}
				}
			}
		}
	}

	public void addProject(Company company) throws Exception {
		log.info("Persisting projects for" + company.getName());
		Company dbCompany = findByCompanyName(company.getName());
		Project project = null;
		Set<Project> projects = company.getProjects();
		if (projects != null) {
			Iterator<Project> projectItr = projects.iterator();
			while (projectItr.hasNext()) {
				project = projectItr.next();
				log.info("project:" + project.getProjectName());
				log.info("companyId:" + dbCompany.getCompanyId());
				project.setCompany(dbCompany);
				em.persist(project);
			}
		}
	}

	// public void persist(Project project) throws Exception {
	public void persistAll(Company company) throws Exception {
		log.info("Persisting " + company.getName());
		Project project = null;
		Set<Project> projects = company.getProjects();
		if (projects != null) {
			Iterator<Project> projectItr = projects.iterator();
			while (projectItr.hasNext()) {
				project = projectItr.next();
				log.info("note:" + project.getProjectName());
				project.setCompany(company);
			}
		}
		log.info("Persisting " + project.getProjectName());
		Set<Person> persons = project.getPersons();
		if (persons != null) {
			Iterator<Person> itr = persons.iterator();
			while (itr.hasNext()) {
				Person person = itr.next();
				log.info("person:" + person.getUsername());
			}
		}
		Set<Report> reports = project.getReports();
		if (reports != null) {
			Iterator<Report> itr = reports.iterator();
			while (itr.hasNext()) {
				Report report = itr.next();
				log.info("report:" + report.getRname());
				report.setProject(project);
				Set<Area> areas = report.getAreas();
				if (areas != null) {
					Iterator<Area> areasItr = areas.iterator();
					while (areasItr.hasNext()) {
						Area area = areasItr.next();
						log.info("area:" + area.getNumber());
						area.setReport(report);
						Set<Asset> assets = area.getAssets();
						if (assets != null) {
							Iterator<Asset> assetItr = assets.iterator();
							while (assetItr.hasNext()) {
								Asset asset = assetItr.next();
								log.info("asset:" + asset.getDescription());
								asset.setArea(area);
							}
						}
						Set<Note> notes = area.getNotes();
						if (notes != null) {
							Iterator<Note> noteItr = notes.iterator();
							while (noteItr.hasNext()) {
								Note note = noteItr.next();
								log.info("note:" + note.getNote());
								note.setArea(area);

							}
						}
					}
				}
			}
		}
		em.persist(company);
	}

	public void persist(Company company) throws Exception {
		log.info("Persisting " + company.getName());
		em.persist(company);
	}

	public void persist(Person person) throws Exception {
		log.info("Persisting " + person.getUsername());
		em.persist(person);
	}
}
