/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package referencedata.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import referencedata.common.AbstractReferencedataDao;
import referencedata.common.ReferencedataConstants;
import referencedata.dto.ReferencedataDto;
import referencedata.exception.ReferencedataException;

public class ReferencedataDaoImpl extends AbstractReferencedataDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReferencedataDaoImpl.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public JsonNode retrieveAsJsonString(ReferencedataDto referencedataDto, Map<String, Object> entityConfig) {
		String sql = buildQuery(referencedataDto, entityConfig);
		SimpleModule module = new SimpleModule();
		module.addSerializer(new ResultSetSerializer());

		objectMapper.registerModule(module);

		JsonNode jsonNode = executeQuery(sql, createResultSetExtractor(entityConfig, objectMapper));
		return jsonNode;
	}

	private String buildQuery(ReferencedataDto referencedataDto, Map<String, Object> entityConfig) {
		if (referencedataDto.getKeys() == null) {
			throw new ReferencedataException("", "Error! Unavailable Field information.");
		}
		List<String> keys = referencedataDto.getKeys();
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
		sql.append(entityConfig.get(ReferencedataConstants.KEY_ENTITIES));
		// -- build where clause
		if (referencedataDto.getCriteria() != null && !referencedataDto.getCriteria().isEmpty()) {
			sql.append(" where ");
			StringBuilder whereClause = null;
			for (Entry<String, String[]> entry : referencedataDto.getCriteria().entrySet()) {
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

	private String buildLikeClause(Map<String, Object> entityConfig, String columnName, String[] values) {
		StringBuilder key = null;
		String likeClause = null;
		if (!entityConfig.containsKey(ReferencedataConstants.DAO_INFO)) {
			return null;
		}
		ArrayList<Map> daoInfos = (ArrayList) entityConfig.get(ReferencedataConstants.DAO_INFO);
		if (daoInfos == null || daoInfos.isEmpty()) {
			return null;
		}
		for (Map<String, String> daoInfoMap : daoInfos) {
			if (daoInfoMap.containsKey(ReferencedataConstants.CLAUSE)
					&& daoInfoMap.containsKey(ReferencedataConstants.CRITERIA)
					&& daoInfoMap.get(ReferencedataConstants.KEY).equalsIgnoreCase(columnName)) {
				String criteria = daoInfoMap.get(ReferencedataConstants.CRITERIA);
				String logicalOperator = daoInfoMap.get(ReferencedataConstants.LOGICAL_OPERATOR);
				if (ReferencedataConstants.LIKE.equalsIgnoreCase(criteria)) {
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
