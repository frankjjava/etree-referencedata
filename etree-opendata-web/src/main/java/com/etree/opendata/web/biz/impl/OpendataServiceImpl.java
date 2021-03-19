/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package com.etree.opendata.web.biz.impl;

import static com.etree.opendata.common.OpendataConstants.KEY_ENTITIES_NAME;
import static com.etree.opendata.common.OpendataConstants.KEY_ENTITY_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.etree.opendata.common.OpendataConstants;
import com.etree.opendata.common.biz.OpendataService;
import com.etree.opendata.common.dao.OpendataDao;
import com.etree.opendata.common.dto.OpendataDto;
import com.etree.opendata.common.exception.OpendataException;
import com.etree.opendata.web.config.SchemaConfig;

public class OpendataServiceImpl implements OpendataService {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(OpendataServiceImpl.class);
	
	@Autowired
	private SchemaConfig schemaConfig;

	@Autowired
	private OpendataDao opendataDao;

	@Override
	public String loadEntityInfo(OpendataDto opendataServiceDto) {
		Map<String, Object> entities = schemaConfig.getEntities();
		if (opendataServiceDto.getEntitiesKeyName() == null || !entities.containsKey(opendataServiceDto.getEntitiesKeyName())) {
			throw new OpendataException("", "Error! Requested entity not available: " + opendataServiceDto.getEntitiesKeyName());
		}
		Map<String, Object> entityConfig = getEntityConfig(opendataServiceDto.getEntitiesKeyName());
		if (entityConfig == null) {
			throw new OpendataException("", "Error! Unavailable or unconfigured entity!");
		}
	    opendataServiceDto.setEntityName((String) entityConfig.get(KEY_ENTITY_NAME));
		List<String> keys = opendataServiceDto.getKeys();
		List<String> newKeys = null;
		Map<String, String> propInfo = (Map) entityConfig.get(OpendataConstants.PROP_INFO);
		if (keys == null || keys.isEmpty()) {
			if (keys == null) {
				keys = new ArrayList<>();
				opendataServiceDto.setKeys(keys);
			}
			for (Entry<String, Object> entry : entityConfig.entrySet()) {
				String key = entry.getKey();
				if (propInfo != null) {
					key = propInfo.get(key.toLowerCase());
				}
				if (key.equals(KEY_ENTITIES_NAME) || key.equals(KEY_ENTITY_NAME)) {
					continue;
				}
				keys.add(key);
			}
		} else if (propInfo != null) {
			newKeys = new ArrayList<>();
			opendataServiceDto.setKeys(newKeys);
			for (String key : keys) {
				String propInfoKey = key.toLowerCase();
				if (propInfo.containsKey(propInfoKey)) {
					newKeys.add(propInfo.get(propInfoKey));
				} else {
					newKeys.add(key);
				}
			}
		}
		Map<String, String[]> criteria = opendataServiceDto.getCriteria();
		if (propInfo != null) {
			Map<String, String[]> newCriteria = new HashMap<>();
			opendataServiceDto.setCriteria(newCriteria);
			for (Entry<String, String[]> entry : criteria.entrySet()) {
				String key = entry.getKey();
				String propInfoKey = key.toLowerCase();
				if (propInfo.containsKey(propInfoKey)) {
					newCriteria.put(propInfo.get(propInfoKey), entry.getValue());
				} else {
					newCriteria.put(key, entry.getValue());
				}
			}		
		}
//		JSONArray jsonArray = opendataDao.retrieveAsJSONArray(opendataServiceDto, entityConfig);
		String jsonString = opendataDao.retrieveAsJsonString(opendataServiceDto, entityConfig);
		return jsonString;
	}

	@Override
	public JSONArray loadAvailableEntities() {
//		JSONObject entityJson = new JSONObject();
		Map<String, Object> entities = schemaConfig.getEntities();
		JSONArray jsonArray = new JSONArray();
		for (Entry<String, Object> mapEntry : entities.entrySet()) {
			JSONObject jsonObject = new JSONObject();
			Map<String, Object> props = (Map<String,Object>) mapEntry.getValue();
			int idx = 0;
			for (Entry<String, Object> entry : props.entrySet()) {
				String key = (String) entry.getKey();
				if (!KEY_ENTITIES_NAME.equals(key)) {
					jsonObject.put("Field-" + ++idx, key);
				}
			}
			JSONObject jsonObj = new JSONObject();
			jsonObj.put((String) mapEntry.getKey(), jsonObject);
			jsonArray.put(jsonObj);
		}
//		entityJson.put(KEY_ENTITY_NAME, jsonArray);
		return jsonArray;
	}

	@Override
	public JSONArray loadEntityInfo(String entity) {
		Map<String, Object> entities = schemaConfig.getEntities();
		Map<String, Object>  props = (Map<String, Object>) entities.get(entity);
		JSONArray jsonArray = null;
		if (props != null) {
			JSONObject jsonObject = new JSONObject();
			int idx = 0;
			for (Entry<String, Object> entry : props.entrySet()) {
				String key = (String) entry.getKey();
				if (!KEY_ENTITIES_NAME.equals(key)) {
					jsonObject.put("Field-" + ++idx, key);
				}
			}
//			JSONObject entityObject = new JSONObject();
			jsonArray = new JSONArray();
			jsonArray.put(jsonObject);
//			entityObject.put((String) props.get(KEY_ENTITY_NAME), jsonObject);
//			return jsonArray;
		}
		return jsonArray;
	}
	
	private Map<String, Object> getEntityConfig(String keyName) {
		Map<String, Object> entities = schemaConfig.getEntities();
		Map<String, Object> prop = (Map) entities.get(keyName.toLowerCase());
		return prop;
	}

}
