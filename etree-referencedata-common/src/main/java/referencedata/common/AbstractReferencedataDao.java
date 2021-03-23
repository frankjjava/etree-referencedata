package referencedata.common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.etree.commons.dao.AbstractDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractReferencedataDao extends AbstractDaoImpl implements ReferencedataDao {
	
	public ResultSetExtractor<ArrayNode> createResultSetExtractor(Map<String, Object> entityConfig,
			ObjectMapper objectMapper) {
		return new ResultSetExtractor<ArrayNode>() {
			@Override
			public ArrayNode extractData(ResultSet rs) throws SQLException, DataAccessException {
				ObjectNode objectNode = null;
				ArrayNode arrayNode = null;
				try {
					ResultSetMetaData rsMetaData = rs.getMetaData();
					Map<String, String> propInfo = (Map<String, String>) entityConfig.get(ReferencedataConstants.COL_NAME_TO_POJO_MAPPING);
					while (rs.next()) {
						objectNode = objectMapper.createObjectNode();
						for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
							String columnName = rsMetaData.getColumnName(i + 1);
							if (propInfo != null) {
								columnName = propInfo.get(columnName);
							}
							String columnValue = rs.getString(columnName);
							if (columnValue != null) {
								objectNode.put(columnName, columnValue);
							}
						}
						if (arrayNode == null) {
							arrayNode = objectMapper.createArrayNode();
						}
						arrayNode.add(objectNode);
					}
				} catch (SQLException ex) {
					objectNode = objectMapper.createObjectNode();
					objectNode.put("result", "failure");
					objectNode.put("error", ex.getMessage());
					if (arrayNode == null) {
						arrayNode = objectMapper.createArrayNode();
					}
					arrayNode.add(objectNode);
				}
				return arrayNode;
			}
		};
	}

}
