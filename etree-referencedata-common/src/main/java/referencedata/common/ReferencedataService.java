/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2020-11-04 
*/
package referencedata.common;

import com.fasterxml.jackson.databind.JsonNode;

import referencedata.dto.ReferencedataDto;

public interface ReferencedataService {
	
	/* @param contains the entity/field/criteria for which the info to be fetched
	 * @return data that is available for the entity 
	 */
	JsonNode loadEntityInfo(ReferencedataDto opendataDto);  
	
	//@return load all the entities available
	JsonNode loadAvailableEntities(); 
	
	/* @param contains the entity for which the info to be fetched
	 * @return information of that specific entity
	 */
	JsonNode loadEntityInfo(String entity);

}
