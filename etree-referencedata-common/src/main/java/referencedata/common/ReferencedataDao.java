/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2020-11-04 
*/
package referencedata.common;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import referencedata.dto.ReferencedataDto;

public interface ReferencedataDao {

	public JsonNode retrieveAsJsonString(ReferencedataDto opendataDto, Map<String, Object> entityConfig);
}
