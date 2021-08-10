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

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import etree.refdata.common.dao.RefdataDao;
import lombok.Data;

@Data
public abstract class AbstractRefdataService implements RefdataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRefdataService.class);
	protected Map<Object, Object> configParams;
	protected RefdataDao opendataDao;

	public Map<String, Object> getUtilityConfig(String keyName) {
		if (configParams == null) {
			LOGGER.warn("Configuration data 'configParams' not available or set !!");
			return null;
		}
		Map<String, Object> prop = (Map) configParams.get(keyName.toLowerCase());
		return prop;
	}
}
