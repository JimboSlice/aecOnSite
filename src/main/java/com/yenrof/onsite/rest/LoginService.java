package com.yenrof.onsite.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yenrof.onsite.dto.OnsiteKeyDTO;
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
	public Response checkCredentials(@PathParam("username") String username,
			@PathParam("password") String password) {

		Response.ResponseBuilder builder = null;
		OnsiteKeyDTO person = null;
		try {

			UserCredential userCredential = new UserCredential();
			userCredential.setUsername(username);
			userCredential.setPassword(password);
			// Validates Company using bean validation
			person = validateUserCredentials(userCredential);
			// Create an "ok" response
			builder = Response.ok(person);
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("id/password ", "Invalid Credentials");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			e.printStackTrace();
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}
		return builder.build();
	}

}
