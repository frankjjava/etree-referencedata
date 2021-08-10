/**
* Copyright (c) eTree Technologies
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the etree-ref-data.
* 
*  The OTC framework is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, version 3 of the License.
*
*  The OTC framework is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  A copy of the GNU General Public License is made available as 'License.md' file, 
*  along with OTC framework project.  If not, see <https://www.gnu.org/licenses/>.
*
*/
package etree.refdata.web.rest;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import etree.refdata.common.biz.RefdataService;
import etree.refdata.common.dto.OpendataDto;
import etree.refdata.common.exception.RefdataException;

@RestController
@Path(value = "/")
public class RefdataRestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefdataRestService.class);
	private RefdataService opendataService;

	public RefdataService getOpendataService() {
		return opendataService;
	}

	public void setOpendataService(RefdataService openDataService) {
		this.opendataService = openDataService;
	}

	@Path(value = "ping")
	@GET
	public Response ping() {
		return Response.ok().build();
	}

	@Path(value = "{entity}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getEntity(@Context HttpServletRequest servletRequest, @Context UriInfo uriInfo,
			@PathParam("entity") String entityName) {
		String accepts = servletRequest.getHeader(HttpHeaders.ACCEPT);
		MultivaluedMap<String, String> requestParam = uriInfo.getQueryParameters();
		Response response = loadAndCreateResponseEntity(entityName, null, requestParam, accepts);
		if (!response.hasEntity()) {
			String emptyResponse = "";
			return emptyResponse;
		}
		return response.getEntity().toString();
	}

	@Path(value = "{entity}/{fields}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getEntity(@Context HttpServletRequest servletRequest, @Context UriInfo uriInfo,
			@PathParam("entity") String entityName, @PathParam("fields") String fields) {
		String accepts = servletRequest.getHeader(HttpHeaders.ACCEPT);
		MultivaluedMap<String, String> requestParam = uriInfo.getQueryParameters();
		Response response = loadAndCreateResponseEntity(entityName, fields, requestParam, accepts);
		if (!response.hasEntity()) {
			return "";
		}
		return response.getEntity().toString();
	}

	@Path(value = "info")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getInfo(@Context HttpServletRequest servletRequest) {
		String accepts = servletRequest.getHeader(HttpHeaders.ACCEPT);
		JSONArray jsonArray = opendataService.loadAvailableEntities();
		Response response = null;
		if (jsonArray != null) {
			response = createResponseEntity(jsonArray.toString(), accepts, null);
		}
		if (!response.hasEntity()) {
			String emptyResponse = "";
			return emptyResponse;
		}
		return response.getEntity().toString();
	}

	@Path(value = "info/{entity}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getInfo(@Context HttpServletRequest servletRequest, @PathParam("entity") String entity) {
		String accepts = servletRequest.getHeader(HttpHeaders.ACCEPT);
		JSONArray jsonArray = opendataService.loadEntityInfo(entity);
		Response response = null;
		if (jsonArray != null) {
			response = createResponseEntity(jsonArray.toString(), accepts, null);
		}
		if (!response.hasEntity()) {
			String emptyResponse = "";
			return emptyResponse;
		}
		return response.getEntity().toString();
	}

	private Response loadAndCreateResponseEntity(String entitiesKeyName, String fields,
			MultivaluedMap<String, String> requestParam, String accept) {
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
			throw new RefdataException(e);
		}
		Response respEntity = createResponseEntity(jsonString, accept, opendataDto);
		return respEntity;
	}

	private Response createResponseEntity(String jsonString, String accept, OpendataDto opendataDto) {
		if (jsonString == null) {
			return Response.status(200).build();
		}
		boolean isXmlFormat = false;
		if ((accept != null && accept.equalsIgnoreCase(MediaType.APPLICATION_XML))) {
			isXmlFormat = true;
		}
		Response respEntity = null;
		HttpHeaders headers = new HttpHeaders();
		if (isXmlFormat) {
			JSONObject jsonObject = new JSONObject(jsonString);
//			jsonObject.put(opendataServiceDto.getEntitiesKeyName(), reply);
			if (opendataDto != null && opendataDto.getEntitiesKeyName() != null) {
				jsonString = XML.toString(jsonObject, opendataDto.getEntitiesKeyName());
			} else {
				jsonString = XML.toString(jsonObject, "result");
			}
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_XML);
			respEntity = Response.ok().entity(jsonString).build();
		} else {
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			respEntity = Response.ok().entity(jsonString).build();
		}
		return respEntity;
	}
}
