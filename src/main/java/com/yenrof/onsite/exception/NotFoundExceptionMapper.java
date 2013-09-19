package com.yenrof.onsite.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.yenrof.onsite.rest.OnsiteMedia;

@Provider
public class NotFoundExceptionMapper implements
		ExceptionMapper<NotFoundException> {
	public Response toResponse(NotFoundException exception) {
		return Response.status(Response.Status.NOT_FOUND)
				.entity(exception.getMessage())
				.type(OnsiteMedia.OnsiteMediaType).build();
	}
}