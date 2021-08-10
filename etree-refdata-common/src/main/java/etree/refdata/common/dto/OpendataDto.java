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
package etree.refdata.common.dto;

import java.util.List;
import java.util.Map;

public class OpendataDto {
	private String entityName;
	private String entitiesKeyName;
	private List<String> keys;
	private Map<String, List<String>> criteria;
	boolean lookup;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(Object object) {
		this.entityName = (String) object;
	}

	public String getEntitiesKeyName() {
		return entitiesKeyName;
	}

	public void setEntitiesKeyName(String entitiesKeyName) {
		this.entitiesKeyName = entitiesKeyName;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> fields) {
		this.keys = fields;
	}

	public Map<String, List<String>> getCriteria() {
		return criteria;
	}

	public void setCriteria(Map<String, List<String>> criteria) {
		this.criteria = criteria;
	}

	public boolean isLookup() {
		return lookup;
	}

	public void setLookup(boolean lookup) {
		this.lookup = lookup;
	}
}
