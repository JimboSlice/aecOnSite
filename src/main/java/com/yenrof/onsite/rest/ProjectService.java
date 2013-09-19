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
import javax.ws.rs.core.Response;
import com.yenrof.onsite.dto.AreaDTO;
import com.yenrof.onsite.dto.AssetDTO;
import com.yenrof.onsite.dto.CompanyDTO;
import com.yenrof.onsite.dto.NoteDTO;
import com.yenrof.onsite.dto.PersonDTO;
import com.yenrof.onsite.dto.ProjectDTO;
import com.yenrof.onsite.dto.ReportDTO;
import com.yenrof.onsite.exception.BadRequestException;
import com.yenrof.onsite.exception.ConstraintException;
import com.yenrof.onsite.exception.NotFoundException;
import com.yenrof.onsite.exception.EntityCreationException;
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
	@Produces(OnsiteMedia.OnsiteMediaType)
	public List<ProjectDTO> listAllProjects() {
		return repository.findAllOrderedByName();
	}

	@GET
	@Path("/getCompanies")
	@Produces(OnsiteMedia.OnsiteMediaType)
	public List<CompanyDTO> listAllCompanies() {
		return repository.findAllCompaniesOrderedByName();
	}

	@GET
	@Path("/getCompany/{id:[0-9][0-9]*}")
	@Produces(OnsiteMedia.OnsiteMediaType)
	public CompanyDTO findCompany(@PathParam("id") long id) {
		CompanyDTO company = repository.findByCompanyId(id);
		if (company == null) {
			throw new NotFoundException("Could not find company " + id);
		}
		return company;
	}

	@GET
	@Path("/getCompanyProjects/{id:[0-9][0-9]*}")
	@Produces(OnsiteMedia.OnsiteMediaType)
	public List<ProjectDTO> lookupProjectById(@PathParam("id") long id) {
		return repository.findAllCompanyProjectsOrderedByName(id);
	}

	@GET
	@Path("/getPersonProjects/{id:[0-9][0-9]*}")
	@Produces(OnsiteMedia.OnsiteMediaType)
	public List<ProjectDTO> lookupProjectByPersonId(@PathParam("id") long id) {
		return repository.findAllPersonProjectsOrderedByName(id);
	}

	/**
	 * Creates a new Company and objects below from the values provided.
	 * Performs validation, and will return a JAX-RS response with either 200
	 * ok, or with a map of fields, and related errors.
	 */
	@POST
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
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
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public PersonDTO addPerson(AddPersonRequest addPersonRequest) {
		try {
			// Validates Person using bean validation
			log.info("Validating person:"
					+ addPersonRequest.getPerson().getEmail());
			// Validates person using bean validation
			validatePerson(addPersonRequest.getPerson(), false);
			return repository.persist(addPersonRequest);
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			throw new ConstraintException(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			throw new EntityCreationException(e.getMessage());
		} catch (Exception e) {
			// Handle generic exceptions
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * Creates a new Company from the values provided. Performs validation, and
	 * will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addCompany")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public CompanyDTO addCompany(CompanyDTO company) {
		try {
			// Validates Project using bean validation
			validateCompany(company);
			return repository.persist(company);
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			throw new ConstraintException(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			throw new EntityCreationException(e.getMessage());
		} catch (Exception e) {
			// Handle generic exceptions
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * Adds a Project to a Companyfrom the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addProject")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public ProjectDTO addProject(AddProjectRequest addProjectRequest) {
		log.info("addProject project number "
				+ addProjectRequest.getProject().getProjectNumber());
		try {
			// Validates Project using bean validation
			log.info("Validating project: "
					+ addProjectRequest.getProject().getProjectName());
			validateProject(addProjectRequest);
			return repository.persist(addProjectRequest);
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			throw new ConstraintException(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			throw new EntityCreationException(e.getMessage());
		} catch (Exception e) {
			// Handle generic exceptions
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * Adds a Report to a Project from the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addReport")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public ReportDTO addReport(AddReportRequest addReportRequest) {
		try {
			validatePerson(addReportRequest.getPersonId(),
					addReportRequest.getProjectId());
			// Validates Project using bean validation
			log.info("Validating report:"
					+ addReportRequest.getReport().getRname());
			validateReport(addReportRequest);
			return repository.persist(addReportRequest);
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			throw new ConstraintException(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			throw new EntityCreationException(e.getMessage());
		} catch (Exception e) {
			// Handle generic exceptions
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * Adds a Project to a Companyfrom the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addArea")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public AreaDTO addArea(AddAreaRequest addAreaRequest) {
		try {
			validatePerson(addAreaRequest.getPersonId(),
					addAreaRequest.getProjectId());
			// Validates Project using bean validation
			log.info("validating area:" + addAreaRequest.getArea().getName());
			validateArea(addAreaRequest);
			return repository.persist(addAreaRequest);
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			throw new ConstraintException(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			throw new EntityCreationException(e.getMessage());
		} catch (Exception e) {
			// Handle generic exceptions
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * Adds a Inspector to a Project from the values provided. Performs
	 * validation, and will return a JAX-RS response with either 200 ok, or with
	 * a map of fields, and related errors.
	 */
	@POST
	@Path("/addPersonToProject")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
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
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public AssetDTO addAsset(AddAssetRequest addAssetRequest) {
		try {
			// Validates Asset using bean validation
			log.info("validating asset:"
					+ addAssetRequest.getAssetDTO().getName());
			validateAsset(addAssetRequest);
			return repository.persist(addAssetRequest);
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			throw new ConstraintException(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			throw new EntityCreationException(e.getMessage());
		} catch (Exception e) {
			// Handle generic exceptions
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * Adds a Asset to a Report from the values provided. Performs validation,
	 * and will return a JAX-RS response with either 200 ok, or with a map of
	 * fields, and related errors.
	 */
	@POST
	@Path("/addNote")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public NoteDTO addNote(AddNoteRequest addNoteRequest) {
		try {
			// Validates Asset using bean validation
			log.info("validating note:" + addNoteRequest.getNoteDTO().getNote());
			validateNote(addNoteRequest);
			return repository.persist(addNoteRequest);
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			throw new ConstraintException(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			throw new EntityCreationException(e.getMessage());
		} catch (Exception e) {
			// Handle generic exceptions
			throw new BadRequestException(e.getMessage());
		}
	}

}
