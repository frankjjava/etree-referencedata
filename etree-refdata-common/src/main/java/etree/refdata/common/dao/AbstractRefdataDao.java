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
package etree.refdata.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.etree.commons.dao.AbstractDaoImpl;

public abstract class AbstractRefdataDao extends AbstractDaoImpl implements RefdataDao {
//	public ResultSetExtractor<JSONArray> createResultSetExtractor(Map<String, Object> entityConfig) {
//		return new ResultSetExtractor<JSONArray>() {
//			@Override
//			public JSONArray extractData(ResultSet rs) throws SQLException, DataAccessException {
//				JSONArray jsonArray = new JSONArray();
//				JSONObject rootJsonObject = new JSONObject();
//				try {
//					ResultSetMetaData rsMetaData = rs.getMetaData();
//					Map<String, String> propInfo = (Map<String, String>) entityConfig.get(OpendataConstants.PROP_INFO);
//					while (rs.next()) {
//						JSONObject jsonObject = new JSONObject();
//						for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
//							String columnName = rsMetaData.getColumnName(i + 1);
//							if (propInfo != null) {
//								columnName = propInfo.get(columnName);
//							}
//							String columnValue = rs.getString(columnName);
//							if (columnValue != null) {
//								jsonObject.put(columnName, columnValue);
//							}
//						}
//						jsonArray.put(jsonObject);
//					}
//				} catch (SQLException ex) {
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("result", "failure");
//					jsonObject.put("error", ex.getMessage());
//					jsonArray.put(jsonObject);
//				}
////				if (jsonArray.length() > 0) {
////					rootJsonObject.put("", jsonArray);
////					return rootJsonObject;
////				}
//				return jsonArray;
//			}
//		};
//	}
	public ResultSetExtractor<ResultSet> createResultSetExtractor(Map<String, Object> entityConfig) {
		return new ResultSetExtractor<ResultSet>() {
			@Override
			public ResultSet extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs;
			}
		};
	}
}
