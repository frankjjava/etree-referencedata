/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2020-11-04 
*/
package referencedata.exception;

import com.etree.commons.core.exception.EtreeCommonsException;

public class ReferencedataException extends EtreeCommonsException {

	private static final long serialVersionUID = 5477607325912585120L;
	
	public ReferencedataException(String errorCode, String msg, Throwable cause) {
		super(errorCode, msg, cause);
	}

	public ReferencedataException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	public ReferencedataException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ReferencedataException(String errorCode) {
		super(errorCode);
	}

	public ReferencedataException(Throwable cause) {
		super(cause);
	}
}