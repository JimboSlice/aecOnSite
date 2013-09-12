package com.yenrof.onsite.rest;

import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider  
@Produces(MediaType.APPLICATION_JSON)  
public class JacksonConfig implements ContextResolver<ObjectMapper>  
{  
   private ObjectMapper objectMapper;  
   public JacksonConfig() throws Exception  
   {  
      this.objectMapper = new ObjectMapper();  
      this.objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));  
      this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);  
   }  
  
  
   public ObjectMapper getContext(Class<?> objectType)  
   {  
      return objectMapper;  
   }  
}  
 
 