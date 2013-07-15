package com.yenrof.onsite.dataservice;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
//import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.Date;
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

	public void persist(Project project) throws Exception {
		log.info("Persisting " + project.getProjectName());
		Date date = new Date();
		project.setTimeStamp(date);
		Set<Report> reports = project.getReports();
		if (reports != null) {
			Iterator<Report> itr = reports.iterator();
			while (itr.hasNext()) {
				Report report = itr.next();
				log.info("report:" + report.getRname());
				report.setTimeStamp(date);
				report.setProject(project);
				Set<Area> areas = report.getAreas();
				if (areas != null) {
					Iterator<Area> areasItr = areas.iterator();
					while (areasItr.hasNext()) {
						Area area = areasItr.next();
						log.info("area:" + area.getNumber());
						area.setTimeStamp(date);
						area.setReport(report);
						Set<Asset> assets = area.getAssets();
						if (assets != null) {
							Iterator<Asset> assetItr = assets.iterator();
							while (assetItr.hasNext()) {
								Asset asset = assetItr.next();
								log.info("asset:" + asset.getDescription());
								asset.setTimeStamp(date);
								asset.setArea(area);
								Set<Picture> pictures = asset.getPictures();
								if (pictures != null) {
									Iterator<Picture> pictureItr = pictures
											.iterator();
									while (pictureItr.hasNext()) {
										Picture picture = pictureItr.next();
										log.info("picture:"
												+ picture.getComment());
										picture.setTimeStamp(date);
										picture.setAsset(asset);
									}
								}
							}
						}
						Set<Note> notes = area.getNotes();
						if (notes != null) {
							Iterator<Note> noteItr = notes.iterator();
							while (noteItr.hasNext()) {
								Note note = noteItr.next();
								log.info("note:" + note.getNote());
								note.setTimeStamp(date);
								note.setArea(area);
								Set<Picture> pictures = note.getPictures();
								if (pictures != null) {
									Iterator<Picture> pictureItr = pictures
											.iterator();
									while (pictureItr.hasNext()) {
										Picture picture = pictureItr.next();
										log.info("picture:"
												+ picture.getComment());
										picture.setTimeStamp(date);
										picture.setNote(note);
									}
								}

							}
						}
					}
				}
			}
		}
		em.persist(project);
	}
}
