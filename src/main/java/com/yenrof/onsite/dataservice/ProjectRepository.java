package com.yenrof.onsite.dataservice;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
//import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


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

	public Project findByProjectNumber(String projectNumber) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Project> criteria = cb.createQuery(Project.class);
		Root<Project> Project = criteria.from(Project.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(Project).where(cb.equal(Project.get(Patient_.name),
		// ssn));
		criteria.select(Project).where(
				cb.equal(Project.get("projectNumber"), projectNumber));
		return em.createQuery(criteria).getSingleResult();
	}
	
	public Inspector findByUserName(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Inspector> criteria = cb.createQuery(Inspector.class);
		Root<Inspector> Inspector = criteria.from(Inspector.class);
		criteria.select(Inspector).where(
				cb.equal(Inspector.get("username"), username));
		return em.createQuery(criteria).getSingleResult();
	}
	
	public Company findByCompanyName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteria = cb.createQuery(Company.class);
		Root<Company> Company = criteria.from(Company.class);
		criteria.select(Company).where(
				cb.equal(Company.get("name"), name));
		return em.createQuery(criteria).getSingleResult();
	}



	public List<Project> findAllOrderedByName() {
		/*
		 * Query query = em.createNamedQuery("Project.findAll");
		 * 
		 * @SuppressWarnings("unchecked") List<Project> list =
		 * query.getResultList(); Iterator<Project> itr = list.iterator(); while
		 * (itr.hasNext()) { Project project = itr.next();
		 * log.info("getProjectName:" + project.getProjectName()); } return
		 * list;
		 */

		/*
		 * String select = "select p from Project p " +
		 * "left join fetch p.reports";
		 * 
		 * return (List<Project>) em.createQuery(select).getResultList();
		 */
		String select = "select * from Project order by projectName";

		return (List<Project>) em.createNativeQuery(select).getResultList();
	}
	
   public void addProject(Company company) throws Exception {
		log.info("Persisting " + company.getName());
		Company comp = findByCompanyName(company.getName());
		Project project=null;
		Set<Project> projects = company.getProjects();
		if (projects != null) {
			Iterator<Project> projectItr = projects.iterator();
			while (projectItr.hasNext()) {
			    project = projectItr.next();
				log.info("project:" + project.getProjectName());
				project.setCompany(comp);
				em.persist(project);
			}
		}
   }


	//public void persist(Project project) throws Exception {
    public void persistAll(Company company) throws Exception {
		log.info("Persisting " + company.getName());
		Project project=null;
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
		Set<Inspector> inspectors = project.getInspectors();
		if (inspectors != null) {
			Iterator<Inspector> itr = inspectors.iterator();
			while (itr.hasNext()) {
				Inspector inspector = itr.next();
				log.info("inspector:" + inspector.getUsername());
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
	
	public void persist(Inspector inspector) throws Exception {
		log.info("Persisting " + inspector.getUsername());
		em.persist(inspector);
	}
}
