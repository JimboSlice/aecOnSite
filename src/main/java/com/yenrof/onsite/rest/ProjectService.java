package com.yenrof.onsite.rest;

import java.util.HashMap;
import java.util.HashSet;
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
import com.yenrof.onsite.model.Inspector;
import com.yenrof.onsite.model.Project;
import com.yenrof.onsite.service.ProjectRegistration;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the Project table.
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> listAllProjects() {
        return repository.findAllOrderedByName();
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
     * Creates a new Project from the values provided.  Performs validation, and will return a JAX-RS response with either
     * 200 ok, or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(Company company) {

        Response.ResponseBuilder builder = null;

        try {
            //Validates Project using bean validation
            validateCompany(company);

            repository.persist(company);

            //Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            //Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("ssn", "SSN taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    /**
     * Creates a new Inspector to a Project from the values provided.  Performs validation, and will return a JAX-RS response with either
     * 200 ok, or with a map of fields, and related errors.
     */
    @POST
    @Path("/addInspector")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInspector(Inspector inspector) {

        Response.ResponseBuilder builder = null;

        try {
            //Validates Project using bean validation
            validateInspector(inspector);

            repository.persist(inspector);

            //Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            //Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("ssn", "SSN taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    /**
     * Creates a new Inspector to a Project from the values provided.  Performs validation, and will return a JAX-RS response with either
     * 200 ok, or with a map of fields, and related errors.
     */
    @POST
    @Path("/addCompany")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCompany(Company company) {

        Response.ResponseBuilder builder = null;

        try {
            //Validates Project using bean validation
            validateCompany(company);

            repository.persist(company);

            //Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            //Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("ssn", "SSN taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    /**
     * <p>Validates the given Company variable and throws validation exceptions based on the type of error.
     * If the error is standard bean validation errors then it will throw a ConstraintValidationException
     * with the set of the constraints violated.</p>
     * <p>If the error is caused because an existing Company with the same ssn is registered it throws a regular
     * validation exception so that it can be interpreted separately.</p>
     *
     * @param Company company to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException          If Company with the same  already exists
     */
    private void validateCompany(Company company) throws ConstraintViolationException, ValidationException {
        log.fine("Validate started: " + company.getName());
    	//Create a bean validator and check for issues.
        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    
    /**
     * <p>Validates the given Inspector variable and throws validation exceptions based on the type of error.
     * If the error is standard bean validation errors then it will throw a ConstraintValidationException
     * with the set of the constraints violated.</p>
     * <p>If the error is caused because an existing Inspector with the same ssn is registered it throws a regular
     * validation exception so that it can be interpreted separately.</p>
     *
     * @param Inspector inspector to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException          If Inspector with the same  already exists
     */
    private void validateInspector(Inspector inspector) throws ConstraintViolationException, ValidationException {
        log.fine("Validate started: " + inspector.getUsername());
    	//Create a bean validator and check for issues.
        Set<ConstraintViolation<Inspector>> violations = validator.validate(inspector);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }


    /**
     * <p>Validates the given Project variable and throws validation exceptions based on the type of error.
     * If the error is standard bean validation errors then it will throw a ConstraintValidationException
     * with the set of the constraints violated.</p>
     * <p>If the error is caused because an existing Project with the same ssn is registered it throws a regular
     * validation exception so that it can be interpreted separately.</p>
     *
     * @param Project Project to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException          If Project with the same ssn already exists
     */
    private void validateProject(Project Project) throws ConstraintViolationException, ValidationException {
        log.fine("Validate started: " + Project.getProjectName());
    	//Create a bean validator and check for issues.
        Set<ConstraintViolation<Project>> violations = validator.validate(Project);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        //Check the uniqueness of the ssn
        /*if (projectNumberAlreadyExists(Project.getProjectName())) {
            log.info("SSN  violation: " + Project.getProjectName());
            throw new ValidationException("Unique ProjectName Violation");
        }*/
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message.
     * This can then be used by clients to show violations.
     *
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            log.info("Violation: " + violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    /**
     * Checks if a Project with the same projectNumber is already registered.  This is the only way to
     * easily capture the "@UniqueConstraint(columnNames = "projectNumber")" constraint from the Project class.
     *
     * @param projectNumber The projectNumber to check
     * @return True if the projectNumber already exists, and false otherwise
     */
    public boolean projectNumberAlreadyExists(String projectNumber) {
        Project Project = null;
        try {
            Project = repository.findByProjectNumber(projectNumber);
        } catch (NoResultException e) {
            // ignore
        }
        return Project != null;
    }
}
