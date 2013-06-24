/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/

package org.rti.webgenome.webui;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.util.SystemUtils;

/**
 * Exceptions thrown when an business rule has been violated.
 */
public class SessionTimeoutException extends WebGenomeApplicationException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public SessionTimeoutException() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param msg Message
	 */
	public SessionTimeoutException(final String msg) {
		super(msg);
	}
	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable
	 */
	public SessionTimeoutException(final Throwable origThrowable) {
		super(origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg Message
	 * @param origThrowable Original throwable
	 */
	public SessionTimeoutException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
