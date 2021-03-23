/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package referencedata.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ReferencedataDto {

	private String entityName;
	private String entitiesKeyName;
	private List<String> keys;
	private Map<String, String[]> criteria;
	private boolean lookup;
}
