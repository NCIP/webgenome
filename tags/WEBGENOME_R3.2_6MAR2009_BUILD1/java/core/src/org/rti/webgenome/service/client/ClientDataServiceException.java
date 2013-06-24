/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $


*/

package org.rti.webgenome.service.client;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.util.SystemUtils;

/**
 * Exception thrown if an error occurs while getting data
 * from an application client.
 * @author dhall
 *
 */
public class ClientDataServiceException extends WebGenomeSystemException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public ClientDataServiceException() {
		super();
	}

	/**
	 * Constructor.
	 * @param msg Message.
	 * @param origThrowable Original throwable.
	 */
	public ClientDataServiceException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg Message.
	 */
	public ClientDataServiceException(final String msg) {
		super(msg);
	}

	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable.
	 */
	public ClientDataServiceException(final Throwable origThrowable) {
		super(origThrowable);
	}

}
