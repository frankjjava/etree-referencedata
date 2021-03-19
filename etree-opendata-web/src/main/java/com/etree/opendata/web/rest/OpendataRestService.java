/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */

package com.etree.opendata.web.rest;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.etree.opendata.common.biz.OpendataService;
import com.etree.opendata.common.dto.OpendataDto;
import com.etree.opendata.common.exception.OpendataException;

@RestController
public class OpendataRestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpendataRestService.class);
	
	@Autowired
	private OpendataService opendataService;

	@GetMapping(path = "/ping")
	public Response ping() {
		return Response.ok().build();
	}
	
	@GetMapping(path = "/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEntity(@Context HttpServletRequest servletRequest, @PathVariable("entity") String entityName) {
		Map<String, String[]> requestParam = servletRequest.getParameterMap();
		Response response = loadAndCreateResponseEntity(entityName, null, requestParam);
		if (!response.hasEntity()) {
			String emptyResponse = "";
			return emptyResponse;
		}
		return response.getEntity().toString();
	}

	@GetMapping(path = "/{entity}/{fields}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEntity(@Context HttpServletRequest servletRequest, @PathVariable("entity") String entityName, 
			@PathVariable("fields") String fields) {
		Map<String, String[]> requestParam = servletRequest.getParameterMap();
		Response response = loadAndCreateResponseEntity(entityName, fields, requestParam);
		if (!response.hasEntity()) {
			return "";
		}
		return response.getEntity().toString();
	}

	@GetMapping(path = "/info")
	@Produces(MediaType.APPLICATION_JSON)
	public String getInfo(@Context HttpServletRequest servletRequest) {
		JSONArray jsonArray = opendataService.loadAvailableEntities();
		Response response = null;
		if (jsonArray != null) {
			response = Response.ok().entity(jsonArray).build();
		}
		if (!response.hasEntity()) {
			String emptyResponse = "";
			return emptyResponse;
		}
		return response.getEntity().toString();
	}

	@GetMapping(path = "/info/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getInfo(@Context HttpServletRequest servletRequest, @PathVariable("entity") String entity) {
		JSONArray jsonArray = opendataService.loadEntityInfo(entity);
		Response response = null;
		if (jsonArray != null) {
			response = Response.ok().entity(jsonArray).build();
		}
		if (!response.hasEntity()) {
			String emptyResponse = "";
			return emptyResponse;
		}
		return response.getEntity().toString();
	}

	private Response loadAndCreateResponseEntity(String entitiesKeyName, String fields, 
			Map<String, String[]> requestParam) {
		OpendataDto opendataDto = new OpendataDto();
		if (entitiesKeyName != null) {
			opendataDto.setEntitiesKeyName(entitiesKeyName.toLowerCase());
		}
		if (fields != null) {
			opendataDto.setKeys(Arrays.asList(fields.toLowerCase().split(",")));
		}
		if (requestParam != null) {
			opendataDto.setCriteria(requestParam);
		}
		String jsonString = null;
		try {
//			JSONArray jsonArray = opendataService.loadEntityInfo(opendataServiceDto);
			jsonString = opendataService.loadEntityInfo(opendataDto);
//			if (jsonArray != null) {
//				reply = jsonArray.toString();
//			}
		} catch (Exception e) {
			LOGGER.error("Error! ", e);
			throw new OpendataException(e);
		}
		Response respEntity = Response.ok().entity(jsonString).build();
		return respEntity;
	}

}
