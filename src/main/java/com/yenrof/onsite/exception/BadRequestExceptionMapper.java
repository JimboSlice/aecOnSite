package com.yenrof.onsite.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.yenrof.onsite.rest.OnsiteMedia;

@Provider
public class BadRequestExceptionMapper implements
		ExceptionMapper<BadRequestException> {
	public Response toResponse(BadRequestException exception) {
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(exception.getMessage())
				.type(OnsiteMedia.OnsiteMediaType).build();
	}
}