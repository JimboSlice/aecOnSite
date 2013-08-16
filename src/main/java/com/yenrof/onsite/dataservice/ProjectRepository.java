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

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.yenrof.onsite.dto.AreaDTO;
import com.yenrof.onsite.dto.AssetDTO;
import com.yenrof.onsite.dto.CompanyDTO;
import com.yenrof.onsite.dto.NoteDTO;
import com.yenrof.onsite.dto.OnsiteKeyDTO;
import com.yenrof.onsite.dto.PersonDTO;
import com.yenrof.onsite.dto.ProjectDTO;
import com.yenrof.onsite.dto.ReportDTO;
import com.yenrof.onsite.model.*;
import com.yenrof.onsite.request.AddPersonToProjectRequest;

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

	public Company findCompanyById(long id) {
		Company company = em.find(Company.class, id);
		return company;
	}

	public Person_HAS_Project findPersonProject(
			AddPersonToProjectRequest addPersonToProjectRequest) {
		Person dbPerson = null;
		log.info("findPersonProject:"
				+ addPersonToProjectRequest.getPerson().getEmail());
		dbPerson = findByUserName(addPersonToProjectRequest.getPerson()
				.getEmail());
		String select = "SELECT * FROM Person_HAS_Project where personId=:personId and projectId=:projectId";
		Query query = em.createNativeQuery(select, Person_HAS_Project.class);
		query.setParameter("personId", dbPerson.getPersonId());
		query.setParameter("projectId",
				addPersonToProjectRequest.getProjectId());
		return (Person_HAS_Project) query.getSingleResult();

	}

	public Project findByProjectNumber(ProjectDTO project, long companyId) {
		Company dbCompany = findCompanyById(companyId);
		log.info("findByProjectNumber: projectname " + project.getProjectName());
		log.info("findByProjectNumber: companyname " + dbCompany.getName());
		log.info("findByProjectNumber: companyid " + dbCompany.getCompanyId());

		String number = project.getProjectNumber();
		String select = "SELECT * FROM Project  INNER JOIN Company ON Company.companyId = "
				+ "Project.Company_companyId where Project.projectNumber=:number and Company.companyId=:companyId";
		Query query = em.createNativeQuery(select, Project.class);
		query.setParameter("number", number);
		query.setParameter("companyId", companyId);
		return (Project) query.getSingleResult();
	}

	public Person findByUserName(String email) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
		Root<Person> person = criteria.from(Person.class);
		criteria.select(person).where(cb.equal(person.get("email"), email));
		return em.createQuery(criteria).getSingleResult();
	}

	public OnsiteKeyDTO checkCredentials(UserCredential userCredential) {
		String select = "SELECT DISTINCT Person.personId,  Project.Company_companyId FROM Person "
				+ "INNER JOIN Person_HAS_Project ON Person_HAS_Project.personId = Person.personId "
				+ "INNER JOIN Project ON Project.projectId = Person_HAS_Project.projectId "
				+ "where Person.email=:email and Person.password=:password";
		Query query = em.createNativeQuery(select);
		query.setParameter("email", userCredential.getUserName());
		query.setParameter("password", userCredential.getPassword());
		Object[] result = null;
		OnsiteKeyDTO onsiteKeyDTO = null;
		try {
			result = (Object[]) query.getSingleResult();
			onsiteKeyDTO = new OnsiteKeyDTO();
			BigInteger personId = (BigInteger) result[0];
			BigInteger companyId = (BigInteger) result[1];
			onsiteKeyDTO.setPersonId(personId.longValue());
			onsiteKeyDTO.setCompanyId(companyId.longValue());
		} catch (NoResultException nre) {
			// Ignore this is ok!
		}
		return onsiteKeyDTO;
	}

	public CompanyDTO findByCompanyId(long id) {
		Company company = em.find(Company.class, id);
		return (CompanyDTO) map(company);
	}

	public Company findByCompanyName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteria = cb.createQuery(Company.class);
		Root<Company> Company = criteria.from(Company.class);
		criteria.select(Company).where(cb.equal(Company.get("name"), name));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<ProjectDTO> findAllOrderedByName() {
		String select = "select * from Project order by projectName";
		return (List<ProjectDTO>) em.createNativeQuery(select).getResultList();
	}

	public List<CompanyDTO> findAllCompaniesOrderedByName() {
		List<CompanyDTO> companies = new ArrayList<CompanyDTO>(0);
		String select = "select * from Company order by name";
		Query query = em.createNativeQuery(select, Company.class);
		List<Object> list = query.getResultList();
		Company company = null;

		if (list != null) {
			Iterator<Object> companyItr = list.iterator();
			while (companyItr.hasNext()) {
				company = (Company) companyItr.next();
				companies.add((CompanyDTO) map(company));
			}
		}
		return companies;
	}

	public List<ProjectDTO> findAllCompanyProjectsOrderedByName(long companyId) {
		String select = "SELECT * FROM Project INNER JOIN Company ON Company.companyId = Project.Company_companyId where Company.companyId=:companyId";
		Query query = em.createNativeQuery(select, Project.class);
		query.setParameter("companyId", companyId);
		return (List<ProjectDTO>) query.getResultList();
	}

	public List<ProjectDTO> findAllPersonProjectsOrderedByName(long personId) {
		List<ProjectDTO> projects = new ArrayList<ProjectDTO>(0);
		String select = "SELECT * FROM Project INNER JOIN Person_HAS_Project ON Person_HAS_Project.projectId = Project.projectId where Person_HAS_Project.personId=:personId";
		Query query = em.createNativeQuery(select, Project.class);
		query.setParameter("personId", personId);
		List<Object> list = query.getResultList();
		Project project = null;

		if (list != null) {
			Iterator<Object> projectItr = list.iterator();
			while (projectItr.hasNext()) {
				project = (Project) projectItr.next();
				projects.add((ProjectDTO) map(project));
			}
		}
		return projects;
	}

	public void addPersonToProject(
			AddPersonToProjectRequest addPersonToProjectRequest)
			throws Exception {
		log.info("Adding person for company "
				+ addPersonToProjectRequest.getCompanyId());
		// find the company in db - should throw if not found
		PersonDTO person = addPersonToProjectRequest.getPerson();
		Person dbPerson = null;
		try {
			dbPerson = findByUserName(person.getEmail());
			// existing user - don't add to Project just add
			// association
			Person_HAS_Project php = new Person_HAS_Project();
			php.setPersonId(dbPerson.getPersonId());
			php.setProjectId(addPersonToProjectRequest.getProjectId());
			em.persist(php);
		} catch (NoResultException e) {
		    dbPerson = (Person) map(person); // convert to entity
		    Project dbProject = this.findById(addPersonToProjectRequest.getProjectId());
		    dbProject.addPerson(dbPerson);
			em.persist(dbProject);
		}
	}

	public long persist(ProjectDTO project, long companyId) throws Exception {
		Company dbCompany = findCompanyById(companyId);
		log.info("Persisting projects for" + dbCompany.getName());
		log.info("project:" + project.getProjectName());
		log.info("companyId:" + dbCompany.getCompanyId());
		Project dbProject = (Project) map(project); // convert to entity
		dbProject.setCompany(dbCompany);
		em.persist(dbProject);
		Project dbProject2 = findByProjectNumber(project, companyId);
		return dbProject2.getProjectId();
	}

	// public void persist(Project project) throws Exception {
	public void persistAll(CompanyDTO company) throws Exception {
		log.info("Persisting " + company.getName());
		Company dbCompany = (Company) map(company);
		ProjectDTO project = null;
		Project dbProject = null;
		Set<ProjectDTO> projects = company.getProjects();
		if (projects != null) {
			Iterator<ProjectDTO> projectItr = projects.iterator();
			while (projectItr.hasNext()) {
				project = projectItr.next();
				log.info("project:" + project.getProjectName());
				dbProject = (Project) map(project);
				dbProject.setCompany(dbCompany);
				break;
			}
		}
		log.info("Persisting " + dbProject.getProjectName());
		Set<PersonDTO> persons = project.getPersons();
		if (persons != null) {
			Iterator<PersonDTO> itr = persons.iterator();
			while (itr.hasNext()) {
				PersonDTO person = itr.next();
				log.info("person:" + person.getEmail());
				dbProject.addPerson((Person) map(person));
			}
		}
		Set<ReportDTO> reports = project.getReports();
		if (reports != null) {
			Iterator<ReportDTO> itr = reports.iterator();
			while (itr.hasNext()) {
				ReportDTO report = itr.next();
				log.info("report:" + report.getRname());
				Report dbReport = (Report) map(report);
				dbReport.setProject(dbProject);
				Set<AreaDTO> areas = report.getAreas();
				if (areas != null) {
					Iterator<AreaDTO> areasItr = areas.iterator();
					while (areasItr.hasNext()) {
						AreaDTO area = areasItr.next();
						log.info("area:" + area.getNumber());
						Area dbArea = (Area) map(area);
						dbArea.setReport(dbReport);
						Set<AssetDTO> assets = area.getAssets();
						if (assets != null) {
							Iterator<AssetDTO> assetItr = assets.iterator();
							while (assetItr.hasNext()) {
								AssetDTO asset = assetItr.next();
								log.info("asset:" + asset.getDescription());
								Asset dbAsset = (Asset) map(asset);
								dbAsset.setArea(dbArea);
							}
						}
						Set<NoteDTO> notes = area.getNotes();
						if (notes != null) {
							Iterator<NoteDTO> noteItr = notes.iterator();
							while (noteItr.hasNext()) {
								NoteDTO note = noteItr.next();
								log.info("note:" + note.getNote());
								Note dbNote = (Note) map(note);
								dbNote.setArea(dbArea);
							}
						}
					}
				}
			}
		}
		em.persist(dbCompany);
	}

	public long persist(CompanyDTO company) throws Exception {
		log.info("Persisting " + company.getName());
		em.persist((Company) (map(company)));
		Company dbCompany = findByCompanyName(company.getName());
		return dbCompany.getCompanyId();

	}

	public long persist(PersonDTO person, long companyId) throws Exception {
		log.info("Persisting " + person.getEmail());
		Company dbCompany = findCompanyById(companyId);
		Person dbPerson = null;
		try {
			dbPerson = findByUserName(person.getEmail());
			// existing user - don't add to Company just add
			// association
			Person_HAS_Company phc = new Person_HAS_Company();
			phc.setPersonId(dbPerson.getPersonId());
			phc.setCompanyId(dbCompany.getCompanyId());
			em.persist(phc);
			return dbPerson.getPersonId();
		} catch (NoResultException e) {
			dbCompany.addPerson((Person) map(person)); // JKF
			em.persist(dbCompany);
			dbPerson = findByUserName(person.getEmail());
			return dbPerson.getPersonId();
		}
	}

	protected Object map(NoteDTO noteDTO) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<NoteDTO, Note> boundMapper = mapperFactory
				.getMapperFacade(NoteDTO.class, Note.class);
		Note note = boundMapper.map(noteDTO);
		return note;
	}

	protected Object map(AreaDTO areaDTO) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<AreaDTO, Area> boundMapper = mapperFactory
				.getMapperFacade(AreaDTO.class, Area.class);
		Area area = boundMapper.map(areaDTO);
		return area;
	}

	protected Object map(AssetDTO assetDTO) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<AssetDTO, Asset> boundMapper = mapperFactory
				.getMapperFacade(AssetDTO.class, Asset.class);
		// map the fields of 'source' onto a new instance of PersonDest
		Asset asset = boundMapper.map(assetDTO);
		return asset;
	}

	protected Object map(ReportDTO reportDTO) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<ReportDTO, Report> boundMapper = mapperFactory
				.getMapperFacade(ReportDTO.class, Report.class);
		// map the fields of 'source' onto a new instance of PersonDest
		Report report = boundMapper.map(reportDTO);
		return report;
	}

	protected Object map(PersonDTO personDTO) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<PersonDTO, Person> boundMapper = mapperFactory
				.getMapperFacade(PersonDTO.class, Person.class);
		// map the fields of 'source' onto a new instance of PersonDest
		Person person = boundMapper.map(personDTO);
		return person;
	}

	protected Object map(CompanyDTO companyDTO) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<CompanyDTO, Company> boundMapper = mapperFactory
				.getMapperFacade(CompanyDTO.class, Company.class);
		// map the fields of 'source' onto a new instance of PersonDest
		Company company = boundMapper.map(companyDTO);
		return company;
	}

	protected Object map(ProjectDTO projectDTO) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<ProjectDTO, Project> boundMapper = mapperFactory
				.getMapperFacade(ProjectDTO.class, Project.class);
		// map the fields of 'source' onto a new instance of PersonDest
		Project project = boundMapper.map(projectDTO);
		return project;
	}

	protected Object map(Company company) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<Company, CompanyDTO> boundMapper = mapperFactory
				.getMapperFacade(Company.class, CompanyDTO.class);
		// map the fields of 'source' onto a new instance of PersonDest
		CompanyDTO companyDTO = boundMapper.map(company);
		return companyDTO;
	}

	protected Object map(Person person) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<Person, PersonDTO> boundMapper = mapperFactory
				.getMapperFacade(Person.class, PersonDTO.class);
		// map the fields of 'source' onto a new instance of PersonDest
		PersonDTO personDTO = boundMapper.map(person);
		return personDTO;
	}

	protected Object map(Project project) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		BoundMapperFacade<Project, ProjectDTO> boundMapper = mapperFactory
				.getMapperFacade(Project.class, ProjectDTO.class);
		// map the fields of 'source' onto a new instance of PersonDest
		ProjectDTO projectDTO = boundMapper.map(project);
		return projectDTO;
	}

}
