/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package referencedata.impl;

import static referencedata.common.ReferencedataConstants.DAO_INFO;
import static referencedata.common.ReferencedataConstants.KEY_ENTITIES;
import static referencedata.common.ReferencedataConstants.KEY_ENTITY;
import static referencedata.common.ReferencedataConstants.COL_NAME_TO_POJO_MAPPING;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import referencedata.common.ReferencedataDao;
import referencedata.common.ReferencedataService;
import referencedata.config.SchemaConfig;
import referencedata.dto.ReferencedataDto;
import referencedata.exception.ReferencedataException;

public class ReferencedataServiceImpl implements ReferencedataService {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(OpendataServiceImpl.class);
	
	@Autowired
	private SchemaConfig schemaConfig;
	
	@Autowired
	private ReferencedataDao referencedataDao;

	private Map<String, ? extends Object> entities;
	private ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct 
	private void init() {
		entities = schemaConfig.getEntities();
	}
	
	@Override
	public JsonNode loadEntityInfo(ReferencedataDto referencedataDto) {
		if (referencedataDto.getEntitiesKeyName() == null || !entities.containsKey(referencedataDto.getEntitiesKeyName())) {
			throw new ReferencedataException("", "Error! Requested entity not available: " + referencedataDto.getEntitiesKeyName());
		}
		Map<String, Object> entityConfig = (Map) entities.get(referencedataDto.getEntitiesKeyName().toLowerCase());
		if (entityConfig == null) {
			throw new ReferencedataException("", "Error! Unavailable or unconfigured entity!");
		}
	    referencedataDto.setEntityName((String) entityConfig.get(KEY_ENTITY));
		List<String> keys = referencedataDto.getKeys();
		if (keys == null || keys.isEmpty()) {
			for (Entry<String, Object> entry : entityConfig.entrySet()) {
				String key = entry.getKey();
				if (key.equals(KEY_ENTITIES) || key.equals(KEY_ENTITY) || key.equals(COL_NAME_TO_POJO_MAPPING) || 
						key.equals(DAO_INFO)) {
					continue;
				}
				if (keys == null) {
					keys = new ArrayList<>();
				}
				keys.add(key);
			}
			if (keys != null) {
				referencedataDto.setKeys(keys);
			}
		}
		JsonNode jsonNode = referencedataDao.retrieveAsJsonString(referencedataDto, entityConfig);
		return jsonNode;
	}

	@Override
	public JsonNode loadAvailableEntities() {
		ObjectNode objectNode = objectMapper.createObjectNode();
		for (Entry<String, ? extends Object> mapEntry : entities.entrySet()) {
			Map<String, Object> props = (Map<String,Object>) mapEntry.getValue();
			int idx = 0;
			for (Entry<String, Object> entry : props.entrySet()) {
				String key = (String) entry.getKey();
				if (!KEY_ENTITIES.equals(key)) {
					objectNode.put("Fld-" + ++idx, key);
				}
			}
			ObjectNode parentObjectNode = objectMapper.createObjectNode();
			parentObjectNode.putPOJO(mapEntry.getKey(), objectNode);
		}
		return objectNode;
	}

	@Override
	public JsonNode loadEntityInfo(String entity) {
		Map<String, Object>  props = (Map<String, Object>) entities.get(entity);
		ObjectNode objectNode = objectMapper.createObjectNode();
		if (props != null) {
			Map<String, String> map = new LinkedHashMap<>();
			int idx = 0;
			for (Entry<String, Object> entry : props.entrySet()) {
				String key = (String) entry.getKey();
				if (key.equals(KEY_ENTITIES) || key.equals(KEY_ENTITY) || key.equals(DAO_INFO)) {
					continue;
				}
				if (!KEY_ENTITIES.equals(key)) {
					map.put("Fld-" + ++idx, key);
				}
			}
			JsonNode jsonNode = objectMapper.valueToTree(map);
			try {
				objectNode = (ObjectNode) objectMapper.readTree(jsonNode.asText());
			} catch (JsonProcessingException e) {
				throw new ReferencedataException(e);
			}
		}
		return objectNode;
	}

}
