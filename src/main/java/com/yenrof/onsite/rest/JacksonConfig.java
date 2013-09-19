package com.yenrof.onsite.rest;

import java.util.logging.Logger;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

@Provider
@Produces(OnsiteMedia.OnsiteMediaType)
public class JacksonConfig implements ContextResolver<ObjectMapper> {
	private static final Logger Log = Logger.getLogger(JacksonConfig.class
			.getName());
	private final ObjectMapper objectMapper;

	public JacksonConfig()  {
		Log.info("In custom JSON provider JacksonConfig constructor");

		objectMapper = new ObjectMapper();
		objectMapper.configure(
				SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		Log.info("In custom JSON provider getContext");
		return objectMapper;
	}
}