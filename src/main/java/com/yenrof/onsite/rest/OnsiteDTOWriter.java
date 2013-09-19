package com.yenrof.onsite.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

//import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
//import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
//import javax.ws.rs.ext.Providers;

import javax.ws.rs.ext.Providers;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

@Provider
@Produces(OnsiteMedia.OnsiteMediaType)
public class OnsiteDTOWriter implements MessageBodyWriter<Object> {

	protected Providers providers;

	public OnsiteDTOWriter() {
		super();
		Log.info("Default ProjectDTOWriter ");
	}

	public OnsiteDTOWriter(@Context Providers providers) {
		Log.info("@Context Providers providers ProjectDTOWriter ");
		if (providers == null) {
			throw new NullPointerException("providers");
		}
		this.providers = providers;
	}

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final Logger Log = Logger.getLogger(OnsiteDTOWriter.class
			.getName());

	public boolean isWriteable(java.lang.Class<?> type,
			java.lang.reflect.Type genericType,
			java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
		Log.info("isWriteable via ProjectDTOWriter ");
		return type.getName().contains("com.yenrof.onsite.dto");
	}

	@Override
	public long getSize(Object arg0, Class<?> arg1, Type arg2,
			Annotation[] arg3, MediaType arg4) {
		Log.info("getSize via ProjectDTOWriter ");
		return -1;
	}

	@Override
	public void writeTo(Object target, Class<?> objclass, Type type,
			Annotation[] arg3, MediaType mediaType,
			MultivaluedMap<String, Object> arg5, OutputStream entityStream)
			throws IOException, WebApplicationException {

		ObjectMapper ctx = null;
		ContextResolver<ObjectMapper> resolver = null;
		if (providers != null) {
			Log.info("providers NOT null in ProjectDTOWriter ");
			resolver = providers.getContextResolver(ObjectMapper.class,
					mediaType);
			if (resolver != null) // try to locate a cached ObjectMapper Context
				ctx = resolver.getContext(objclass);
		}
		Log.info("ObjectMapper={}");
		if (ctx == null) {
			try {
				ctx = new ObjectMapper();
			} catch (Exception ex) {
				throw new RuntimeException(
						"error creating ad-hoc ObjectMapper", ex);
			}
		}
		ctx.getSerializationConfig()
				.withSerializationInclusion(
						org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
		ctx.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		ctx.getSerializationConfig().withDateFormat(sdf);
		ctx.writeValue(entityStream, target);

		// get the Object Mapper

	}
}
