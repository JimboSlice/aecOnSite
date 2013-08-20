package com.yenrof.onsite.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.core.Response;

import com.yenrof.onsite.request.AddAreaRequest;
import com.yenrof.onsite.request.AddPersonToProjectRequest;
import com.yenrof.onsite.dataservice.ProjectRepository;
import com.yenrof.onsite.dto.AreaDTO;
import com.yenrof.onsite.dto.CompanyDTO;
import com.yenrof.onsite.dto.OnsiteKeyDTO;
import com.yenrof.onsite.dto.PersonDTO;
import com.yenrof.onsite.dto.ProjectDTO;
import com.yenrof.onsite.dto.ReportDTO;
import com.yenrof.onsite.model.Area;
import com.yenrof.onsite.model.Company;
import com.yenrof.onsite.model.Person;
import com.yenrof.onsite.model.Person_HAS_Project;
import com.yenrof.onsite.model.Project;
import com.yenrof.onsite.model.Report;
import com.yenrof.onsite.model.UserCredential;
import com.yenrof.onsite.service.ProjectRegistration;

public class Service {

	@Inject
	protected Logger log;

	@Inject
	protected Validator validator;

	@Inject
	protected ProjectRepository repository;

	@Inject
	ProjectRegistration registration;

	/**
	 * <p>
	 * Validates the given UserCredential variable and throws validation
	 * exceptions based on the type of error. If the error is standard bean
	 * validation errors then it will throw a ConstraintValidationException with
	 * the set of the constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because an existing UserCredential matches
	 * database it throws a regular validation exception so that it can be
	 * interpreted separately.
	 * </p>
	 * 
	 * @param UserCredentialDTO
	 *            userCredential to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If UserCredential don't match database
	 */
	protected OnsiteKeyDTO validateUserCredentials(UserCredential userCredential)
			throws ConstraintViolationException, ValidationException {
		log.fine("validateUserCredentials started: "
				+ userCredential.getUserName());
		OnsiteKeyDTO person = null;
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<UserCredential>> violations = validator
				.validate(userCredential);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the id/password
		person = credentialsMatch(userCredential);
		if (person == null) {
			log.info("Id and Password match violation: "
					+ userCredential.getUserName());
			throw new ValidationException("Id and Password Violation");
		}
		return person;
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
	protected void validateCompany(CompanyDTO company)
			throws ConstraintViolationException, ValidationException {
		log.fine("validateCompany started: " + company.getName());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<CompanyDTO>> violations = validator
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
	 * If the error is caused because an existing Person with the same username
	 * is already associated with the project it throws a regular validation
	 * exception so that it can be interpreted separately.
	 * </p>
	 * 
	 * @param ProjectDTO
	 *            Project with Person to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Inspector with the same already exists
	 */
	protected void validatePersonProject(
			AddPersonToProjectRequest addPersonToProjectRequest)
			throws ConstraintViolationException, ValidationException {
		log.fine("validatePersonProject started: "
				+ addPersonToProjectRequest.getProjectId());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<PersonDTO>> violations = validator
				.validate(addPersonToProjectRequest.getPerson());

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the name
		if (personProjectAlreadyExists(addPersonToProjectRequest)) {
			log.info("Person Project Relationship Already Exists  violation: "
					+ addPersonToProjectRequest.getProjectId()
					+ addPersonToProjectRequest.getPerson().getPersonId());
			throw new ValidationException(
					"Person Project Already Exists  Violation");
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
	 * If the error is caused because an existing Person with the same username
	 * is already associated with the project it throws a regular validation
	 * exception so that it can be interpreted separately.
	 * </p>
	 * 
	 * @param AddReportRequest
	 *            Report with Project id to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Report with the same name already exists
	 */
	protected void validateReport(ReportDTO report, long projectId)
			throws ConstraintViolationException, ValidationException {
		log.fine("validateReport started for project : " + projectId);
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<ReportDTO>> violations = validator
				.validate(report);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the name
		if (personReportAlreadyExists(report, projectId)) {
			log.info("Report-Project Relationship Already Exists  violation: "
					+ report.getReportId() + " " + projectId);
			throw new ValidationException(
					"Person Project Already Exists  Violation");
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
	 * If the error is caused because an existing Area with the same name is
	 * already associated with the project it throws a regular validation
	 * exception so that it can be interpreted separately.
	 * </p>
	 * 
	 * @param AddAreaRequest
	 *            Area with Project id to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Area with the same name already exists
	 */
	protected void validateArea(AddAreaRequest addAreaRequest)
			throws ConstraintViolationException, ValidationException {
		log.fine("validateReport started for area : "
				+ addAreaRequest.getArea().getName());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<AreaDTO>> violations = validator
				.validate(addAreaRequest.getArea());

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the name
		if (reportAreaAlreadyExists(addAreaRequest)) {
			log.info("Report-Area Relationship Already Exists  violation: "
					+ addAreaRequest.getReportId() + " "
					+ addAreaRequest.getProjectId());
			throw new ValidationException(
					"Report Area Already Exists  Violation");
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
	 * If the error is caused because an existing Person with the same username
	 * is registered it throws a regular validation exception so that it can be
	 * interpreted separately.
	 * </p>
	 * 
	 * @param PersonDTO
	 *            person to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Inspector with the same already exists
	 */
	protected void validatePerson(Person person)
			throws ConstraintViolationException, ValidationException {
		log.fine("Validate started: " + person.getEmail());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Person>> violations = validator
				.validate(person);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the name
		if (userNameAlreadyExists(person.getEmail())) {
			log.info("User Name  violation: " + person.getEmail());
			throw new ValidationException("Unique User Number Violation");
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
	 * If the error is caused because an existing Person with the same username
	 * is registered it throws a regular validation exception so that it can be
	 * interpreted separately.
	 * </p>
	 * 
	 * @param PersonDTO
	 *            person to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Inspector with the same already exists
	 */
	protected void validatePerson(PersonDTO person, boolean checkForExisting)
			throws ConstraintViolationException, ValidationException {
		log.fine("Validate started: " + person.getEmail());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<PersonDTO>> violations = validator
				.validate(person);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the name
		if (userNameAlreadyExists(person.getEmail())) {
			log.info("User Name  violation: " + person.getEmail());
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
	 * @param ProjectDTO
	 *            Project to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 * @throws ValidationException
	 *             If Project with the same number already exists
	 */
	protected void validateProject(ProjectDTO project, long companyId)
			throws ConstraintViolationException, ValidationException {
		log.fine("Validate project started: " + project.getProjectName());
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<ProjectDTO>> violations = validator
				.validate(project);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the uniqueness of the name
		if (projectNumberAlreadyExists(project, companyId)) {
			log.info("Project Number  violation: " + project.getProjectName()
					+ " " + project.getProjectNumber());
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
	protected Response.ResponseBuilder createViolationResponse(
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
	protected boolean projectNumberAlreadyExists(ProjectDTO project,
			long companyId) {
		Project projectdb = null;
		try {
			projectdb = repository.findByProjectNumber(project, companyId);
		} catch (NoResultException e) {
			// ignore
		}
		return projectdb != null;
	}

	/**
	 * Checks if a Report with the same reportName is already registered. This
	 * is the only way to easily capture the
	 * "@UniqueConstraint(columnNames = "reportName")" constraint from the
	 * Report class.
	 * 
	 * @param ReportDTO
	 *            The reportName to check * @param ReportDTO The projectId to of
	 *            the report check
	 * @return True if the reportName already exists, and false otherwise
	 */
	protected boolean personReportAlreadyExists(ReportDTO report, long projectId) {
		Report reportdb = null;
		try {
			reportdb = repository.findByReportName(report, projectId);
		} catch (NoResultException e) {
			// ignore
		}
		return reportdb != null;
	}

	/**
	 * Checks if a Area with the same name is already registered. This is the
	 * only way to easily capture the
	 * "@UniqueConstraint(columnNames = "reportName")" constraint from the
	 * Report class.
	 * 
	 * @param AddAreaRequest
	 *            The name to check
	 * @return True if the area name already exists, and false otherwise
	 */
	protected boolean reportAreaAlreadyExists(AddAreaRequest addAreaRequest) {
		Area areadb = null;
		try {
			areadb = repository.findByAreaName(addAreaRequest);
		} catch (NoResultException e) {
			// ignore
		}
		return areadb != null;
	}

	/**
	 * Checks if a Person with the same username is already registered. This is
	 * the only way to easily capture the
	 * "@UniqueConstraint(columnNames = "username")" constraint from the Project
	 * class.
	 * 
	 * @param username
	 *            The username to check
	 * @return True if the username already exists, and false otherwise
	 */
	protected boolean userNameAlreadyExists(String username) {
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
	protected boolean nameAlreadyExists(String name) {
		Company company = null;
		try {
			company = repository.findByCompanyName(name);
		} catch (NoResultException e) {
			// ignore
		}
		return company != null;
	}

	/**
	 * Checks if a Person with the same name is already registered with the
	 * project. This is the only way to easily capture the
	 * "@UniqueConstraint(columnNames = "name")" constraint from the Person
	 * class.
	 * 
	 * @param username
	 *            The name to check
	 * @return True if the person already exists on the project, and false
	 *         otherwise
	 */
	protected boolean personProjectAlreadyExists(
			AddPersonToProjectRequest addPersonToProjectRequest) {
		Person_HAS_Project personProject = null;
		try {
			personProject = repository
					.findPersonProject(addPersonToProjectRequest);
		} catch (NoResultException e) {
			// ignore
		}
		return personProject != null;
	}

	/**
	 * Checks if a UserCredential matches database *
	 * "@UniqueConstraint(columnNames = "name")" constraint from the Person
	 * class. validateUserCredentials
	 * 
	 * @param userCredential
	 *            The name to check
	 * @return True if the userCredential matches and false otherwise
	 */
	protected OnsiteKeyDTO credentialsMatch(UserCredential userCredential) {
		OnsiteKeyDTO personKey = null;
		try {
			personKey = repository.checkCredentials(userCredential);
		} catch (NoResultException e) {
			// ignore
		}
		return personKey;
	}

	/**
	 * Checks if a person is attached to a project
	 * 
	 * @param personId
	 * @param projectId
	 *            The person to check
	 * @return True if the person/project matches and false otherwise
	 */

	protected boolean validatePerson(long personId, long projectId) {
		Person_HAS_Project personProject = null;
		try {
			personProject = repository.findPersonProject(personId, projectId);
		} catch (NoResultException e) {
			// ignore
		}
		return personProject != null;

	}

}
