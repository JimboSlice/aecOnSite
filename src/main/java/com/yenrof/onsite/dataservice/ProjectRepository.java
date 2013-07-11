package com.yenrof.onsite.dataservice;

import javax.enterprise.context.ApplicationScoped; 
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger; 

import com.yenrof.onsite.model.*;

@ApplicationScoped
public class ProjectRepository {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	public Project findById(Long id) {
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
		criteria.select(Project).where(cb.equal(Project.get("projectNumber"), projectNumber));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<Project> findAllOrderedByName() {
		Query query = em.createNamedQuery("Project.findAll");
		@SuppressWarnings("unchecked")
		List<Project> list = query.getResultList();
		Iterator<Project> itr = list.iterator();
		while (itr.hasNext()) {
			Project project = itr.next();
			log.info("getProjectName:" + project.getProjectName());
		}
		return list;
	}
	
		public void persist(Project project) throws Exception {
		log.info("Persisting " + project.getProjectName());
		Date date = new Date();
		project.setTimeStamp(date);
		List<Report> reports  = project.getReports();
		Iterator<Report> itr = reports.iterator();
		while (itr.hasNext()) {
			Report report = itr.next();
			log.info("report:" + report.getRname());
			report.setTimeStamp(date);
		}
		em.persist(project);
	}
}
