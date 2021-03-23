/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */

package referencedata.web;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.etree.commons.core.dto.MessageDto;
import com.fasterxml.jackson.databind.JsonNode;

import referencedata.common.ReferencedataService;
import referencedata.dto.ReferencedataDto;

@RestController
public class ReferencedataRestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReferencedataRestService.class);
	
	@Autowired
	private ReferencedataService referencedataService;

	@GetMapping(path = "/ping")
	public Response ping() {
		return Response.ok().build();
	}
	
	@GetMapping(path = "/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public MessageDto getEntity(@Context HttpServletRequest servletRequest, @PathVariable("entity") String entityName) {
		if ("favicon.ico".equals(entityName)) {
			MessageDto messageDto = new MessageDto();
			messageDto.setSuccess(true);
			return messageDto;
		}
		Map<String, String[]> requestParam = servletRequest.getParameterMap();
		MessageDto messageDto = loadAndCreateResponseEntity(entityName, null, requestParam);
		return messageDto;
	}

	@GetMapping(path = "/{entity}/{fields}")
	@Produces(MediaType.APPLICATION_JSON)
	public MessageDto getEntity(@Context HttpServletRequest servletRequest, @PathVariable("entity") String entityName, 
			@PathVariable("fields") String fields) {
		Map<String, String[]> requestParam = servletRequest.getParameterMap();
		MessageDto messageDto = loadAndCreateResponseEntity(entityName, fields, requestParam);
		return messageDto;
	}

	@GetMapping(path = "/info")
	@Produces(MediaType.APPLICATION_JSON)
	public MessageDto getInfo(@Context HttpServletRequest servletRequest) {
		JsonNode jsonNode = referencedataService.loadAvailableEntities();
		MessageDto messageDto = createResponse(jsonNode, "info");
		return messageDto;
	}

	@GetMapping(path = "/info/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public MessageDto getInfo(@Context HttpServletRequest servletRequest, @PathVariable("entity") String entity) {
		JsonNode jsonNode = referencedataService.loadEntityInfo(entity);
		MessageDto messageDto = createResponse(jsonNode, entity);
		return messageDto;
	}

	private MessageDto loadAndCreateResponseEntity(String entitiesKeyName, String fields, 
			Map<String, String[]> requestParam) {
		ReferencedataDto referenceDto = new ReferencedataDto();
		if (entitiesKeyName != null) {
			referenceDto.setEntitiesKeyName(entitiesKeyName.toLowerCase());
		}
		if (fields != null) {
			referenceDto.setKeys(Arrays.asList(fields.toLowerCase().split(",")));
		}
		if (requestParam != null) {
			referenceDto.setCriteria(requestParam);
		}
//		ResponseBuilder responseBuilder = null;
		String msg = null;
		MessageDto messageDto = new MessageDto();
		try {
			JsonNode jsonNode = referencedataService.loadEntityInfo(referenceDto);
//			responseBuilder = Response.status(Status.OK);
			if (jsonNode == null) {
				msg = "Oops! No data available for " + entitiesKeyName;
			} else {
				msg = "Successfully retrieved " + entitiesKeyName;
			}
			messageDto.setSuccess(true);
			messageDto.setResponse(jsonNode);
		} catch (Exception ex) {
//			responseBuilder = Response.status(Status.NO_CONTENT);
			msg = "There was an error retrieving " + entitiesKeyName;
			LOGGER.error(msg, ex);
			messageDto.setSuccess(false);
		}
		messageDto.setMessage(msg);
//		responseBuilder = responseBuilder.entity(messageDto).type(MediaType.APPLICATION_JSON);
		return messageDto;
	}

	private MessageDto createResponse(JsonNode jsonNode, String entityName) {
//		ResponseBuilder responseBuilder = null;
		String msg = null;
		MessageDto messageDto = new MessageDto();
		if (jsonNode != null) {
//			responseBuilder = Response.status(Status.OK);
			messageDto.setMessage(msg);
			messageDto.setSuccess(true);
			messageDto.setResponse(jsonNode);
		} else {
//			responseBuilder = Response.status(Status.NO_CONTENT);
			msg = "There was an error retrieving " + entityName;
			LOGGER.error(msg);
			messageDto.setMessage(msg);
			messageDto.setSuccess(false);
		}
//		responseBuilder = responseBuilder.entity(messageDto).type(MediaType.APPLICATION_JSON);
		return messageDto;
	}
	
}
