package com.yenrof.onsite.rest;


import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.yenrof.onsite.dto.OnsiteKeyDTO;
import com.yenrof.onsite.exception.BadRequestException;
import com.yenrof.onsite.exception.ConstraintException;
import com.yenrof.onsite.exception.EntityCreationException;
import com.yenrof.onsite.model.UserCredential;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the
 * Project table.
 */
@Path("/login")
@RequestScoped
@Stateful
public class LoginService extends Service {

	/**
	 * Validates user credentials
	 */
	@GET
	@Path("/checkCredentials/{username}-{password}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OnsiteKeyDTO checkCredentials(
			@PathParam("username") String username,
			@PathParam("password") String password) {

		try {

			UserCredential userCredential = new UserCredential();
			userCredential.setUsername(username);
			userCredential.setPassword(password);
			// Validates Company using bean validation
			return validateUserCredentials(userCredential);
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
