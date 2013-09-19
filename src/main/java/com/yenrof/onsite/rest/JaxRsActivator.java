/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yenrof.onsite.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.yenrof.onsite.exception.BadRequestExceptionMapper;
import com.yenrof.onsite.exception.ConstraintExceptionMapper;
import com.yenrof.onsite.exception.EntityCreationException;
import com.yenrof.onsite.exception.NotFoundExceptionMapper;

/**
 * A class extending {@link Application} and annotated with @ApplicationPath is
 * the Java EE 6 "no XML" approach to activating JAX-RS.
 * 
 * <p>
 * Resources are served relative to the servlet path specified in the
 * {@link ApplicationPath} annotation.
 * </p>
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
	/* class body intentionally left blank */
	private static final Logger Log = Logger.getLogger(JaxRsActivator.class
			.getName());

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	//tr8769912
	public JaxRsActivator() {
		Log.info("JaxRsActivator consructor ");
		singletons.add( new OnsiteDTOWriter());
		singletons.add(new JacksonConfig());
		classes.add(ProjectService.class);
		classes.add(LoginService.class);
		classes.add(BadRequestExceptionMapper.class);
		classes.add(ConstraintExceptionMapper.class);
		classes.add(NotFoundExceptionMapper.class);
		classes.add(EntityCreationException.class);		
	}

	@Override
	public Set<Object> getSingletons() {
		Log.info("JaxRsActivator getSingletons ");
		Set<Object> defaults = super.getSingletons();
		singletons.addAll(defaults);
		return singletons;
	}

	public Set<Class<?>> getClasses() {
		Log.info("JaxRsActivator getClasses ");
		return classes;
	}
}