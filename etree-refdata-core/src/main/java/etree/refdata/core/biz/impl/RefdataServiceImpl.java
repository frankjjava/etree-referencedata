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
package etree.refdata.core.biz.impl;

import static etree.refdata.common.RefdataConstants.KEY_ENTITIES_NAME;
import static etree.refdata.common.RefdataConstants.KEY_ENTITY_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import etree.refdata.common.RefdataConstants;
import etree.refdata.common.biz.AbstractRefdataService;
import etree.refdata.common.dto.OpendataDto;
import etree.refdata.common.exception.RefdataException;

public class RefdataServiceImpl extends AbstractRefdataService {


	@Override
	public String loadEntityInfo(OpendataDto opendataServiceDto) {
		if (opendataServiceDto.getEntitiesKeyName() == null
				|| !configParams.containsKey(opendataServiceDto.getEntitiesKeyName())) {
			throw new RefdataException("",
					"Error! Requested entity not available: " + opendataServiceDto.getEntitiesKeyName());
		}
		Map<String, Object> entityConfig = getUtilityConfig(opendataServiceDto.getEntitiesKeyName());
		if (entityConfig == null) {
			throw new RefdataException("", "Error! Unavailable or unconfigured entity!");
		}
		opendataServiceDto.setEntityName(entityConfig.get(KEY_ENTITY_NAME));
		List<String> keys = opendataServiceDto.getKeys();
		List<String> newKeys = null;
		Map<String, String> propInfo = (Map) entityConfig.get(RefdataConstants.PROP_INFO);
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
		Map<String, List<String>> criteria = opendataServiceDto.getCriteria();
		if (propInfo != null) {
			Map<String, List<String>> newCriteria = new HashMap<>();
			opendataServiceDto.setCriteria(newCriteria);
			for (Entry<String, List<String>> entry : criteria.entrySet()) {
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
		JSONArray jsonArray = new JSONArray();
		for (Entry<Object, Object> mapEntry : configParams.entrySet()) {
			JSONObject jsonObject = new JSONObject();
			Map<String, Object> props = (Map<String, Object>) mapEntry.getValue();
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
		Map<String, Object> props = (Map<String, Object>) configParams.get(entity);
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
}
