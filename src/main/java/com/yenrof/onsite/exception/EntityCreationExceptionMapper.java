package com.yenrof.onsite.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.yenrof.onsite.rest.OnsiteMedia;


@Provider
public class EntityCreationExceptionMapper implements
		ExceptionMapper<EntityCreationException> {
	public Response toResponse(EntityCreationException exception) {
		return Response.status(Response.Status.CONFLICT)
				.entity(exception.getMessage()).type(OnsiteMedia.OnsiteMediaType).build();
	}
}