/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-02-15 20:03:50 $


*/

package org.rti.webgenome.service.data;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.util.SystemUtils;

/**
 * General error class for exceptions that occur during interaction
 * with a data source session.
 * @author dhall
 *
 */
public class DataSourceSessionException extends WebGenomeApplicationException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public DataSourceSessionException() {
		
	}

	/**
	 * Constructor.
	 * @param msg An error message
	 */
	public DataSourceSessionException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor.
	 * @param origThrowable Original throwable
	 */
	public DataSourceSessionException(final Throwable origThrowable) {
		super(origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg Error message
	 * @param origThrowable Original throwable
	 */
	public DataSourceSessionException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
