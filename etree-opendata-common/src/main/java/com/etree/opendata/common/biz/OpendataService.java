/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.opendata.common.biz;

import org.json.JSONArray;

import com.etree.opendata.common.dto.OpendataDto;

public interface OpendataService {
	
	/* @param contains the entity/field/criteria for which the info to be fetched
	 * @return data that is available for the entity 
	 */
//	JSONArray loadEntityInfo(OpendataDto opendataServiceDto);  
	String loadEntityInfo(OpendataDto opendataDto);  
	
	//@return load all the entities available
	JSONArray loadAvailableEntities(); 
	
	/* @param contains the entity for which the info to be fetched
	 * @return information of that specific entity
	 */
	JSONArray loadEntityInfo(String entity);

}
