package com.yenrof.onsite.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.yenrof.onsite.rest.OnsiteMedia;


@Provider
public class ConstraintExceptionMapper implements
		ExceptionMapper<ConstraintException> {
	public Response toResponse(ConstraintException exception) {
		Set<ConstraintViolation<?>> constraints=exception.getConstraints();
		Map<String, String> responseObj = new HashMap<String, String>();

		for (ConstraintViolation<?> violation : constraints) {
			responseObj.put(violation.getPropertyPath().toString(),
					violation.getMessage());
			//log.info("Violation: " + violation.getMessage());
		}
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(responseObj).type(OnsiteMedia.OnsiteMediaType).build();
	}
}