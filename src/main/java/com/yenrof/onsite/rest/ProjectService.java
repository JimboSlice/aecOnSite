package com.yenrof.onsite.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yenrof.onsite.dataservice.ProjectRepository;
import com.yenrof.onsite.model.Company;
import com.yenrof.onsite.model.Person;
import com.yenrof.onsite.model.Person_HAS_Project;
import com.yenrof.onsite.model.Project;
import com.yenrof.onsite.service.ProjectRegistration;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * Project table.
 */
@Path("/project")
@RequestScoped
@Stateful
public class ProjectService {
	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private ProjectRepository repository;

	@Inject
	ProjectRegistration registration;

	@GET
	@Path("/projects")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> listAllProjects() {
		return repository.findAllOrderedByName();
	}

	
	@GET
	@Path("/companies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> listAllCompanies() {
		return repository.findAllCompaniesOrderedByName();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Project lookupProjectById(@PathParam("id") long id) {
		Project Project = repository.findById(id);
		if (Project == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Project;
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
	 * Creates a new Inspector to a Project from the values provided. Performs
	 * validation, and will return a JAX-RS response with either 200 ok, or with
	 * a map of fields, and related errors.
	 */
	@POST
	@Path("/addPerson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPerson(Person person) {

		Response.ResponseBuilder builder = null;

		try {
			// Validates Project using bean validation
			validatePerson(person);

			repository.persist(person);

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
	 * Adds a Project to a Companyfrom the values provided. Performs
	 * validation, and will return a JAX-RS response with either 200 ok, or with
	 * a map of fields, and related errors.
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
			responseObj.put("Person", "Person Name already associated to project");
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
	 * <p>
	 * Validates the given Company variable and throws validation exceptions
	 * based on the type of error. If the error is standard bean validation
	 * errors then it will throw a ConstraintValidationException with the set of
	 * the constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because an existing Company with the same name is
	 * registered it throws a regular validation exception so that it can be
	 * interpreted separately.
	 * </p>
	 * 
	 * @param Company
	 *            company to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Company with the same already exists
	 */
	private void validateCompany(Company company)
			throws ConstraintViolationException, ValidationException {
		log.fine("validateCompany started: " + company.getName());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Company>> violations = validator
				.validate(company);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the uniqueness of the company name
		if (nameAlreadyExists(company.getName())) {
			log.info("Company Name  violation: " + company.getName());
			throw new ValidationException("Unique Company Number Violation");
		}

	}

	/**
	 * <p>
	 * Validates the given Person variable and throws validation exceptions
	 * based on the type of error. If the error is standard bean validation
	 * errors then it will throw a ConstraintValidationException with the set of
	 * the constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because an existing Person with the same
	 * username is already associated with the project it throws a regular validation exception so that
	 * it can be interpreted separately.
	 * </p>
	 * 
	 * @param Project
	 *            Project with Person to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Inspector with the same already exists
	 */
	private void validatePersonProject(Project project, Company company)
			throws ConstraintViolationException, ValidationException {
		log.fine("validatePersonProject started: " + project.getProjectName());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Project>> violations = validator
				.validate(project);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the name
		if (personProjectAlreadyExists(project, company)){
			log.info("Person Project Relationship Already Exists  violation: " + project.getProjectName());
			throw new ValidationException("Person Project Already Exists  Violation");
		}

	}
	
	/**
	 * <p>
	 * Validates the given Inspector variable and throws validation exceptions
	 * based on the type of error. If the error is standard bean validation
	 * errors then it will throw a ConstraintValidationException with the set of
	 * the constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because an existing Inspector with the same
	 * username is registered it throws a regular validation exception so that
	 * it can be interpreted separately.
	 * </p>
	 * 
	 * @param Person
	 *            person to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Inspector with the same already exists
	 */
	private void validatePerson(Person person)
			throws ConstraintViolationException, ValidationException {
		log.fine("Validate started: " + person.getUsername());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Person>> violations = validator
				.validate(person);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the name
		if (userNameAlreadyExists(person.getUsername())) {
			log.info("User Name  violation: " + person.getUsername());
			throw new ValidationException("Unique User Number Violation");
		}

	}


	/**
	 * <p>
	 * Validates the given Project variable and throws validation exceptions
	 * based on the type of error. If the error is standard bean validation
	 * errors then it will throw a ConstraintValidationException with the set of
	 * the constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because an existing Project with the same ssn is
	 * registered it throws a regular validation exception so that it can be
	 * interpreted separately.
	 * </p>
	 * 
	 * @param Project
	 *            Project to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Project with the same number already exists
	 */
	private void validateProject(Project project, Company company)
			throws ConstraintViolationException, ValidationException {
		log.fine("Validate project started: " + project.getProjectName());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Project>> violations = validator
				.validate(project);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the uniqueness of the name
		if (projectNumberAlreadyExists(project, company)) {
			log.info("Project Number  violation: " + project.getProjectName() + " " + project.getProjectNumber());
			throw new ValidationException("Unique Project Number Violation");
		}
	}

	/**
	 * Creates a JAX-RS "Bad Request" response including a map of all violation
	 * fields, and their message. This can then be used by clients to show
	 * violations.
	 * 
	 * @param violations
	 *            A set of violations that needs to be reported
	 * @return JAX-RS response containing all violations
	 */
	private Response.ResponseBuilder createViolationResponse(
			Set<ConstraintViolation<?>> violations) {
		log.fine("Validation completed. violations found: " + violations.size());

		Map<String, String> responseObj = new HashMap<String, String>();

		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(),
					violation.getMessage());
			log.info("Violation: " + violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}

	/**
	 * Checks if a Project with the same projectNumber is already registered.
	 * This is the only way to easily capture the
	 * "@UniqueConstraint(columnNames = "projectNumber")" constraint from the
	 * Project class.
	 * 
	 * @param projectNumber
	 *            The projectNumber to check
	 * @return True if the projectNumber already exists, and false otherwise
	 */
	private boolean projectNumberAlreadyExists(Project project, Company company) {
		Project projectdb = null;
		try {
			projectdb = repository.findByProjectNumber(project, company);
		} catch (NoResultException e) {
			// ignore
		}
		return projectdb != null;
	}

	/**
	 * Checks if a Inspector with the same username is already registered. This
	 * is the only way to easily capture the
	 * "@UniqueConstraint(columnNames = "username")" constraint from the Project
	 * class.
	 * 
	 * @param username
	 *            The username to check
	 * @return True if the username already exists, and false otherwise
	 */
	private boolean userNameAlreadyExists(String username) {
		Person person = null;
		try {
			person = repository.findByUserName(username);
		} catch (NoResultException e) {
			// ignore
		}
		return person != null;
	}

	/**
	 * Checks if a Company with the same name is already registered. This is the
	 * only way to easily capture the "@UniqueConstraint(columnNames = "name")"
	 * constraint from the Company class.
	 * 
	 * @param username
	 *            The name to check
	 * @return True if the name already exists, and false otherwise
	 */
	private boolean nameAlreadyExists(String name) {
		Company company = null;
		try {
			company = repository.findByCompanyName(name);
		} catch (NoResultException e) {
			// ignore
		}
		return company != null;
	}
	
	/**
	 * Checks if a Person with the same name is already registered with the project. This is the
	 * only way to easily capture the "@UniqueConstraint(columnNames = "name")"
	 * constraint from the Person class.
	 * 
	 * @param username
	 *            The name to check
	 * @return True if the person already exists on the project, and false otherwise
	 */
	private boolean personProjectAlreadyExists(Project project, Company company) {
		Person_HAS_Project personProject = null;
		try {
			personProject = repository.findPersonProject(project,company);
		} catch (NoResultException e) {
			// ignore
		}
		return personProject != null;
	}
}
