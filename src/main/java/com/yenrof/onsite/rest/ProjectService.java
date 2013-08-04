package com.yenrof.onsite.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.yenrof.onsite.model.Company;
import com.yenrof.onsite.model.Person;
import com.yenrof.onsite.model.Project;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * Project table.
 */
@Path("/onsite")
@RequestScoped
@Stateful
public class ProjectService  extends Service {


	@GET
	@Path("/getProjects")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> listAllProjects() {
		return repository.findAllOrderedByName();
	}

	@GET
	@Path("/getCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> listAllCompanies() {
		return repository.findAllCompaniesOrderedByName();
	}

	@GET
	@Path("/getCompanyProjects/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> lookupProjectById(@PathParam("id") long id) {
		return repository.findAllCompanyProjectsOrderedByName(id);
	}

	/**
	 * Creates a new Company and objects below from the values provided.
	 * Performs validation, and will return a JAX-RS response with either 200
	 * ok, or with a map of fields, and related errors.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCompany(Company company) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Company using bean validation
			validateCompany(company);

			repository.persistAll(company);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("ssn", "SSN taken");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}

	/**
	 * Creates a new Person to a Project for a company from the values provided.
	 * Performs validation, and will return a JAX-RS response with either 200
	 * ok, or with a map of fields, and related errors.
	 */
	@POST
	@Path("/addPerson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPerson(Company company) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Person using bean validation
			Person person = null;
			Set<Person> persons = company.getPersons();
			if (persons != null) {
				Iterator<Person> personItr = persons.iterator();
				while (personItr.hasNext()) {
					person = personItr.next();
					log.info("validating person:" + person.getEmail());
					// Validates person using bean validation
					validatePerson(person, false);
					break; // add one only
				}
			}

			repository.persist(person, company);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("username", "Username taken");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}

	/**
	 * Creates a new Inspector to a Project from the values provided. Performs
	 * validation, and will return a JAX-RS response with either 200 ok, or with
	 * a map of fields, and related errors.
	 */
	@POST
	@Path("/addCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCompany(Company company) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Project using bean validation
			validateCompany(company);

			repository.persist(company);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("companyName", "Company Name taken");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}

	/**
	 * Adds a Project to a Companyfrom the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addProjectToCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProjectToCompany(Company company) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Project using bean validation
			Project project = null;
			Set<Project> projects = company.getProjects();
			if (projects != null) {
				Iterator<Project> projectItr = projects.iterator();
				while (projectItr.hasNext()) {
					project = projectItr.next();
					log.info("validating project:" + project.getProjectName());
					validateProject(project, company);
				}
			}

			repository.addProject(company);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Project Number", "Project Number taken");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}

	/**
	 * Adds a Inspector to a Project from the values provided. Performs
	 * validation, and will return a JAX-RS response with either 200 ok, or with
	 * a map of fields, and related errors.
	 */
	@POST
	@Path("/addPersonToProject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPersonToProject(Company company) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Project using bean validation
			Project project = null;
			Set<Project> projects = company.getProjects();
			if (projects != null) {
				Iterator<Project> projectItr = projects.iterator();
				while (projectItr.hasNext()) {
					project = projectItr.next();
					log.info("validating project:" + project.getProjectName());
					validatePersonProject(project, company);
				}
			}

			repository.addPersonToProject(company);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Person",
					"Person Name already associated to project");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}


}
