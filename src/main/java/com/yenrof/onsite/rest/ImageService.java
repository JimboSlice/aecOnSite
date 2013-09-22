package com.yenrof.onsite.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;

import com.yenrof.onsite.exception.NotFoundException;
import com.yenrof.onsite.request.AddNoteImageRequest;
import com.yenrof.onsite.request.AddProjectImageRequest;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to store and retrieve images on the
 * file system
 */
@Path("/image")
@RequestScoped
@Stateful
public class ImageService extends Service {
	@Resource(name = "image-directory")
	private String dirPath;

	@GET
	@Path("/getProjectImage/{companyId}/{personId}/{projectId}")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public String getProjectImageFromFileSystem(
			@PathParam("companyId") String companyId,
			@PathParam("personId") String personId,
			@PathParam("projectId") String projectId) {
		String imageDataString = null;
		String companyDirectoryStructure = dirPath + "/"
				+ companyId;
		String projectDirectoryStructure = companyDirectoryStructure + "/"
				+ projectId;

		File projectDir = new File(projectDirectoryStructure);
		if (projectDir.exists()) {
			log.info("Retrieving filePathName: " + projectDirectoryStructure
					+ "/" + "project-" + projectId + ".img");
			try {

			FileInputStream imageInFile = null;
			File projectFile = new File(projectDir, "project-"
					+ projectId + ".img");
				/*
				 * Reading a Image file from file system
				 */
				imageInFile = new FileInputStream(projectFile);
				byte imageData[] = new byte[(int) projectFile.length()];
				imageInFile.read(imageData);

				/*
				 * Converting Image byte array into Base64 String
				 */
				imageDataString = encodeImage(imageData);
				imageInFile.close();
				log.info("Image Successfully Manipulated!");
			}
			catch (FileNotFoundException e) {
				log.info("Image not found" + e);
			} 
			catch (IOException ioe) {
				log.info("Exception while reading the Project Image " + ioe);
			}
			return imageDataString;
		}
		throw new NotFoundException("Could not find image for directory "
				+ projectDirectoryStructure);
	}

	@GET
	@Path("/getNoteImage/{companyId}/{projectId}/{reportId}/{noteId}")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public String getNoteImageFromFileSystem(
			@PathParam("companyId") long companyId,
			@PathParam("projectId") long projectId,
			@PathParam("reportId") long reportId,
			@PathParam("noteId") long noteId) {
		String imageDataString = null;
		String reportDirectoryStructure = dirPath + "/" + companyId + "/"
				+ projectId + "/report-" + reportId;
		File reportDir = new File(reportDirectoryStructure);
		log.info("Retrieving filePathName: " + reportDirectoryStructure + "/"
				+ "note-" + noteId + ".img");
		if (reportDir.exists()) {
			log.info("Retrieving filePathName: " + reportDirectoryStructure
					+ "/" + "note-" + noteId + ".img");
			File noteFile = new File(reportDir, "note-" + noteId + ".img");

			FileInputStream imageInFile = null;
			try {
				/*
				 * Reading a Image file from file system
				 */
				imageInFile = new FileInputStream(noteFile);
				byte imageData[] = new byte[(int) noteFile.length()];
				imageInFile.read(imageData);

				/*
				 * Converting Image byte array into Base64 String
				 */
				imageDataString = encodeImage(imageData);
				imageInFile.close();
				System.out.println("Image Successfully Manipulated!");
			} catch (FileNotFoundException e) {
				log.info("Image not found" + e);
			} catch (IOException ioe) {
				log.info("Exception while reading the Project Image " + ioe);
			}
			return imageDataString;
		}
		throw new NotFoundException("Could not find image for directory "
				+ reportDirectoryStructure);

	}

	@POST
	@Path("/writeProjectImage")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public Response writeProjectImageToFile(AddProjectImageRequest request) {
		Response.ResponseBuilder builder = null;
		log.info("writeProjectImageToFile dirPath = " + dirPath);

		// create company directory if it doesn't exist
		String companyDirectoryStructure = dirPath + "/"
				+ request.getCompanyId();
		String projectDirectoryStructure = companyDirectoryStructure + "/"
				+ request.getProjectId();

		try {

			File companyDir = new File(companyDirectoryStructure);
			if (!companyDir.exists())
				companyDir.mkdir();
			File projectDir = new File(projectDirectoryStructure);
			if (!projectDir.exists())
				projectDir.mkdirs();

			// Converting a Base64 String into Image byte array
			byte[] imageByteArray = decodeImage(request.getImage());

			/*
			 * Write a image byte array into file system
			 */
			File projectFile = new File(projectDir, "project-"
					+ request.getProjectId() + ".img");

			FileOutputStream imageOutFile = null;
			imageOutFile = new FileOutputStream(projectFile);
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
			// Create an "ok" response
			builder = Response.ok();
		} catch (FileNotFoundException e) {
			log.info("Image not written " + e);
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Project Image not written ", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		} catch (IOException ioe) {
			log.info("Exception while writing the Project Image " + ioe);
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Exception while writing the Project Image ",
					ioe.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);

		}

		return builder.build();
	}

	@POST
	@Path("/writeNoteImage")
	@Consumes(OnsiteMedia.OnsiteMediaType)
	@Produces(OnsiteMedia.OnsiteMediaType)
	public Response writeNoteImageToFile(AddNoteImageRequest request) {
		log.info("writeNoteImageToFile dirPath = " + dirPath);

		Response.ResponseBuilder builder = null;
		// create company directory if it doesn't exist
		String companyDirectoryStructure = dirPath + "/"
				+ request.getCompanyId();
		String projectDirectoryStructure = companyDirectoryStructure + "/"
				+ request.getProjectId();
		String reportDirectoryStructure = projectDirectoryStructure
				+ "/report-" + request.getReportId();
		try {

			File companyDir = new File(companyDirectoryStructure);
			if (!companyDir.exists())
				companyDir.mkdir();

			File projectDir = new File(projectDirectoryStructure);
			if (!projectDir.exists())
				projectDir.mkdirs();

			File reportDir = new File(reportDirectoryStructure);
			if (!reportDir.isDirectory())
				reportDir.mkdirs();
			log.info("writeNoteImageToFile AFTER = " + reportDirectoryStructure);

			// Converting a Base64 String into Image byte array
			byte[] imageByteArray = decodeImage(request.getImage());

			/*
			 * Write a image byte array into file system
			 */

			File reportFile = new File(reportDir, "note-" + request.getNoteId()
					+ ".img");

			FileOutputStream imageOutFile = null;

			imageOutFile = new FileOutputStream(reportFile);
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
			// Create an "ok" response
			builder = Response.ok();
		} catch (FileNotFoundException e) {
			log.info("Note Image not written " + e);
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Exception while writing the Note Image ",
					e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		} catch (IOException ioe) {
			log.info("Exception while reading the Note Image " + ioe);
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("Exception while writing the Note Image ",
					ioe.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}

	/**
	 * Encodes the byte array into base64 string
	 * 
	 * @param imageByteArray
	 *            - byte array
	 * @return String a {@link java.lang.String}
	 */
	public static String encodeImage(byte[] imageByteArray) {
		return Base64.encodeBase64URLSafeString(imageByteArray);
	}

	/**
	 * Decodes the base64 string into byte array
	 * 
	 * @param imageDataString
	 *            - a {@link java.lang.String}
	 * @return byte array
	 */
	public static byte[] decodeImage(String imageDataString) {
		return Base64.decodeBase64(imageDataString);
	}

}