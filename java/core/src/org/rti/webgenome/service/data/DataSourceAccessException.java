/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-02-14 23:12:30 $


*/

package org.rti.webgenome.service.data;

import org.rti.webgenome.core.WebGenomeException;
import org.rti.webgenome.util.SystemUtils;

/**
 * A general exception thrown by a <code>DataSource</code> if
 * there is a problem accessing data.
 * @author dhall
 *
 */
public class DataSourceAccessException extends WebGenomeException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
			SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public DataSourceAccessException() {
		
	}

	/**
	 * Constructor.
	 * @param msg Error message
	 */
	public DataSourceAccessException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor.
	 * @param origThrowable Original throwable
	 */
	public DataSourceAccessException(final Throwable origThrowable) {
		super(origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg Error message
	 * @param origThrowable Original throwable
	 */
	public DataSourceAccessException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
