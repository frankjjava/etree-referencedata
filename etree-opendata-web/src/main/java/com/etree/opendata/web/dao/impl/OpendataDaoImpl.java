/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package com.etree.opendata.web.dao.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etree.opendata.common.OpendataConstants;
import com.etree.opendata.common.dao.AbstractOpendataDao;
import com.etree.opendata.common.dto.OpendataDto;
import com.etree.opendata.common.exception.OpendataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OpendataDaoImpl extends AbstractOpendataDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpendataDaoImpl.class);

//	@Override
//	public JSONArray retrieveAsJSONArray(OpendataDto opendataDto, Map<String, Object> entityConfig) {
//		throw new OpendataException("Not implemented!");
////		String sql = buildQuery(opendataDto, entityConfig);
////		JSONArray jsonArray = executeQuery(sql, createResultSetExtractor(entityConfig));
////		return jsonArray;
//	}

	@Override
	public String retrieveAsJsonString(OpendataDto opendataDto, Map<String, Object> entityConfig) {
		String sql = buildQuery(opendataDto, entityConfig);
		SimpleModule module = new SimpleModule();
		module.addSerializer(new ResultSetSerializer());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(module);

		ResultSet resultSet = executeQuery(sql, createResultSetExtractor(entityConfig));
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.putPOJO("results", resultSet);
		String jsonString = null;
		try {
			StringWriter stringWriter = new StringWriter();
			objectMapper.writeValue(stringWriter, objectNode);
			jsonString = stringWriter.toString();
		} catch (IOException e) {
			throw new OpendataException("",  e);
		}
		return jsonString;
	}

	private String buildQuery(OpendataDto opendataDto, Map<String, Object> entityConfig) {
		List<String> keys = null;
		if (opendataDto.getKeys() != null) {
			keys = opendataDto.getKeys();
		}
		if (keys == null) {
			throw new OpendataException("", "Error! Unavailable Field information.");
		}
		// -- build select query
		StringBuilder sql = null;
		for (String key : keys) {
			if (sql == null) {
				sql = new StringBuilder("Select distinct ");
			} else {
				sql.append(",");
			}
			sql.append(entityConfig.get(key)).append(" as ").append(key);
		}
		// -- build from clause
		sql.append(" from ");
		sql.append(entityConfig.get(OpendataConstants.KEY_ENTITIES_NAME));
		// -- build where clause
		if (opendataDto.getCriteria() != null && !opendataDto.getCriteria().isEmpty()) {
			sql.append(" where ");
			StringBuilder whereClause = null;
			for (Entry<String, String[]> entry : opendataDto.getCriteria().entrySet()) {
				if (whereClause == null) {
					whereClause = new StringBuilder();
				} else {
					whereClause.append(" AND ");
				}
				String likeClause = buildLikeClause(entityConfig, (String) entityConfig.get(entry.getKey()), entry.getValue());
				if (likeClause != null) {
					whereClause.append(likeClause);
				} else {
					whereClause.append(entityConfig.get(entry.getKey()));
					whereClause.append(buildInClause(entry.getValue()));
				}
			}
			sql.append(whereClause);
		}
		return sql.toString();
	}

	private String buildLikeClause(Map<String, Object> entityConfig, String columnName, String[] values) {
		StringBuilder key = null;
		String likeClause = null;
		if (!entityConfig.containsKey(OpendataConstants.DAO_INFO)) {
			return null;
		}
		ArrayList<Map> daoInfos = (ArrayList) entityConfig.get(OpendataConstants.DAO_INFO);
		if (daoInfos == null || daoInfos.isEmpty()) {
			return null;
		}
		for (Map<String, String> daoInfoMap : daoInfos) {
			if (daoInfoMap.containsKey(OpendataConstants.CLAUSE)
					&& daoInfoMap.containsKey(OpendataConstants.CRITERIA)
					&& daoInfoMap.get(OpendataConstants.KEY).equalsIgnoreCase(columnName)) {
				String criteria = daoInfoMap.get(OpendataConstants.CRITERIA);
				String logicalOperator = daoInfoMap.get(OpendataConstants.LOGICAL_OPERATOR);
				if (OpendataConstants.LIKE.equalsIgnoreCase(criteria)) {
					for (String value : values) {
						if (key == null) {
							key = new StringBuilder();
						} else {
							if (logicalOperator != null) {
								key.append(logicalOperator);
							} else {
								key.append(" or ");
							}
						}
						key.append(columnName).append(" like '%").append(value).append("%' ");
					}
				}
			}
		}
		if (key != null) {
			likeClause = key.toString();
		}
		return likeClause;
	}
}
