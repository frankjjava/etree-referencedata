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
package etree.refdata.core.dao.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

import etree.refdata.common.RefdataConstants;
import etree.refdata.common.dao.AbstractRefdataDao;
import etree.refdata.common.dto.OpendataDto;
import etree.refdata.common.exception.RefdataException;

public class RefdataDaoImpl extends AbstractRefdataDao {


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
			throw new RefdataException("", e);
		}
		return jsonString;
	}

	private String buildQuery(OpendataDto opendataDto, Map<String, Object> entityConfig) {
		List<String> keys = null;
		if (opendataDto.getKeys() != null) {
			keys = opendataDto.getKeys();
		}
		if (keys == null) {
			throw new RefdataException("", "Error! Unavailable Field information.");
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
		sql.append(entityConfig.get(RefdataConstants.KEY_ENTITIES_NAME));
		// -- build where clause
		if (opendataDto.getCriteria() != null && !opendataDto.getCriteria().isEmpty()) {
			sql.append(" where ");
			StringBuilder whereClause = null;
			for (Entry<String, List<String>> entry : opendataDto.getCriteria().entrySet()) {
				if (whereClause == null) {
					whereClause = new StringBuilder();
				} else {
					whereClause.append(" AND ");
				}
				String likeClause = buildLikeClause(entityConfig, (String) entityConfig.get(entry.getKey()),
						entry.getValue());
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

	private String buildLikeClause(Map<String, Object> entityConfig, String columnName, List<String> values) {
		StringBuilder key = null;
		String likeClause = null;
		if (!entityConfig.containsKey(RefdataConstants.DAO_INFO)) {
			return null;
		}
		ArrayList<Map> daoInfos = (ArrayList<Map>) entityConfig.get(RefdataConstants.DAO_INFO);
		if (daoInfos == null || daoInfos.isEmpty()) {
			return null;
		}
		for (Map<String, String> daoInfoMap : daoInfos) {
			if (daoInfoMap.containsKey(RefdataConstants.CLAUSE) && daoInfoMap.containsKey(RefdataConstants.CRITERIA)
					&& daoInfoMap.get(RefdataConstants.KEY).equalsIgnoreCase(columnName)) {
				String criteria = daoInfoMap.get(RefdataConstants.CRITERIA);
				String logicalOperator = daoInfoMap.get(RefdataConstants.LOGICAL_OPERATOR);
				if (RefdataConstants.LIKE.equalsIgnoreCase(criteria)) {
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
