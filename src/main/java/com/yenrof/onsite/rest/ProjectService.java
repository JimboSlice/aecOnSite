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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yenrof.onsite.dto.AreaDTO;
import com.yenrof.onsite.dto.AssetDTO;
import com.yenrof.onsite.dto.CompanyDTO;
import com.yenrof.onsite.dto.NoteDTO;
import com.yenrof.onsite.dto.PersonDTO;
import com.yenrof.onsite.dto.ProjectDTO;
import com.yenrof.onsite.dto.ReportDTO;
import com.yenrof.onsite.request.AddAreaRequest;
import com.yenrof.onsite.request.AddAssetRequest;
import com.yenrof.onsite.request.AddNoteRequest;
import com.yenrof.onsite.request.AddPersonRequest;
import com.yenrof.onsite.request.AddPersonToProjectRequest;
import com.yenrof.onsite.request.AddProjectRequest;
import com.yenrof.onsite.request.AddReportRequest;

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

			long id = repository.persist(addPersonRequest);
			person.setPersonId(id);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(
					SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			builder = Response.ok(objectMapper.writeValueAsString(person));
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
	 * Creates a new Company from the values provided. Performs validation, and
	 * will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
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
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(
					SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			builder = Response.ok(objectMapper.writeValueAsString(company));
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
			validateProject(addProjectRequest);
			long id = repository.persist(addProjectRequest);
			project.setProjectId(id);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(
					SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			builder = Response.ok(objectMapper.writeValueAsString(project));
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
	 * Adds a Project to a Companyfrom the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addReport")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReport(AddReportRequest addReportRequest) {

		Response.ResponseBuilder builder = null;

		try {
			if (validatePerson(addReportRequest.getPersonId(),
					addReportRequest.getProjectId())) {
				// Validates Project using bean validation
				ReportDTO report = addReportRequest.getReport();
				log.info("validating report:" + report.getRname());
				validateReport(addReportRequest);
				long id = repository.persist(addReportRequest);
				report.setReportId(id);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(
						SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				builder = Response.ok(objectMapper.writeValueAsString(report));
			}
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Report Name", "Report Name taken");
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
	@Path("/addArea")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addArea(AddAreaRequest addAreaRequest) {

		Response.ResponseBuilder builder = null;

		try {
			if (validatePerson(addAreaRequest.getPersonId(),
					addAreaRequest.getProjectId())) {
				// Validates Project using bean validation
				AreaDTO area = addAreaRequest.getArea();
				log.info("validating area:" + area.getName());
				validateArea(addAreaRequest);
				long id = repository.persist(addAreaRequest);
				area.setAreaId(id);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(
						SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				builder = Response.ok(objectMapper.writeValueAsString(area));
			}
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Report Name", "Report Name taken");
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

	/**
	 * Adds a Asset to a Report from the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addAsset")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAsset(AddAssetRequest addAssetRequest) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Asset using bean validation
			AssetDTO asset = addAssetRequest.getAssetDTO();
			log.info("validating asset:" + asset.getName());
			validateAsset(addAssetRequest);
			long id = repository.persist(addAssetRequest);
			asset.setAssetId(id);
			// Create an "ok" response
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(
					SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			builder = Response.ok(objectMapper.writeValueAsString(asset));
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Asset ", "Asset Name taken");
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
	 * Adds a Asset to a Report from the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addNote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNote(AddNoteRequest addNoteRequest) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Asset using bean validation
			NoteDTO note = addNoteRequest.getNoteDTO();
			log.info("validating note:" + note.getNote());
			validateNote(addNoteRequest);
			long id = repository.persist(addNoteRequest);
			note.setNoteId(id);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(
					SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			builder = Response.ok(objectMapper.writeValueAsString(note));
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Note note", "Note taken");
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
