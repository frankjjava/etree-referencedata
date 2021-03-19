/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package com.etree.opendata.common.client;

import java.util.List;
import java.util.Map;

public interface OpendataServiceClient {

	public Object fetchData(Map<String, List<String>> criteria, String entitiesKeyName, String ... arrKeys);
}
