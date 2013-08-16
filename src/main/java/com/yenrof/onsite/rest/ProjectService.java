package com.yenrof.onsite.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yenrof.onsite.dto.CompanyDTO;
import com.yenrof.onsite.dto.PersonDTO;
import com.yenrof.onsite.dto.ProjectDTO;
import com.yenrof.onsite.request.AddPersonRequest;
import com.yenrof.onsite.request.AddPersonToProjectRequest;
import com.yenrof.onsite.request.AddProjectRequest;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * Project table.
 */
@Path("/onsite")
@RequestScoped
@Stateful
public class ProjectService extends Service {

	@GET
	@Path("/getProjects")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectDTO> listAllProjects() {
		return repository.findAllOrderedByName();
	}

	@GET
	@Path("/getCompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CompanyDTO> listAllCompanies() {
		return repository.findAllCompaniesOrderedByName();
	}

	@GET
	@Path("/getCompany/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public CompanyDTO findCompany(@PathParam("id") long id) {
		return repository.findByCompanyId(id);
	}

	@GET
	@Path("/getCompanyProjects/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectDTO> lookupProjectById(@PathParam("id") long id) {
		return repository.findAllCompanyProjectsOrderedByName(id);
	}

	@GET
	@Path("/getPersonProjects/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectDTO> lookupProjectByPersonId(@PathParam("id") long id) {
		return repository.findAllPersonProjectsOrderedByName(id);
	}

	/**
	 * Creates a new Company and objects below from the values provided.
	 * Performs validation, and will return a JAX-RS response with either 200
	 * ok, or with a map of fields, and related errors.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCompany(CompanyDTO company) {

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
	public Response addPerson(AddPersonRequest addPersonRequest) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Person using bean validation
			PersonDTO person = addPersonRequest.getPerson();
			if (person != null) {
				log.info("validating person:" + person.getEmail());
				// Validates person using bean validation
				validatePerson(person, false);
			}

			long id = repository.persist(person,
					addPersonRequest.getCompanyId());
			person.setPersonId(id);
			// Create an "ok" response
			// builder = Response.ok();
			builder = Response.ok(person);
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
	public Response addCompany(CompanyDTO company) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Project using bean validation
			validateCompany(company);

			long companyId = repository.persist(company);
			company.setCompanyId(companyId);
			// Create an "ok" response
			// builder = Response.ok();
			builder = Response.ok(company);
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
	@Path("/addProject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProject(AddProjectRequest addProjectRequest) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Project using bean validation
			ProjectDTO project = addProjectRequest.getProject();
			log.info("validating project:" + project.getProjectName());
			validateProject(project, addProjectRequest.getCompanyId());
			long id = repository.persist(project,
					addProjectRequest.getCompanyId());
			project.setProjectId(id);
			// Create an "ok" response
			builder = Response.ok(project);
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
	public Response addPersonToProject(
			AddPersonToProjectRequest addPersonToProjectRequest) {

		Response.ResponseBuilder builder = null;

		try {
			log.info("validating person: "
					+ addPersonToProjectRequest.getProjectId());
			validatePersonProject(addPersonToProjectRequest);
			repository.addPersonToProject(addPersonToProjectRequest);
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
