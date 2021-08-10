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
package etree.refdata.common.exception;

import com.etree.commons.core.exception.EtreeCommonsException;

public class RefdataException extends EtreeCommonsException {
	private static final long serialVersionUID = 5477607325912585120L;

	public RefdataException(String errorCode, String msg, Throwable cause) {
		super(errorCode, msg, cause);
	}

	public RefdataException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	public RefdataException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public RefdataException(String errorCode) {
		super(errorCode);
	}

	public RefdataException(Throwable cause) {
		super(cause);
	}
}