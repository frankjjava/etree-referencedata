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
package etree.refdata.common.biz;

import org.json.JSONArray;
import etree.refdata.common.dto.OpendataDto;

public interface RefdataService {
	/*
	 * @param contains the entity/field/criteria for which the info to be fetched
	 * 
	 * @return data that is available for the entity
	 */
//	JSONArray loadEntityInfo(OpendataDto opendataServiceDto);  
	String loadEntityInfo(OpendataDto opendataDto);

	// @return load all the entities available
	JSONArray loadAvailableEntities();

	/*
	 * @param contains the entity for which the info to be fetched
	 * 
	 * @return information of that specific entity
	 */
	JSONArray loadEntityInfo(String entity);
}
