/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $


*/

/**
 * 
 */
package org.rti.webgenome.core;

import org.rti.webgenome.util.SystemUtils;

/**
 * Generic exception thrown when some data are encountered that
 * have an improper and unexpected format.
 * @author dhall
 *
 */
public class DataFormatException extends WebGenomeApplicationException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public DataFormatException() {
		super();
	}

	/**
	 * Constructor.
	 * @param msg Error message
	 */
	public DataFormatException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor.
	 * @param origThrowable Original throwable raised
	 */
	public DataFormatException(final Throwable origThrowable) {
		super(origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg Error message
	 * @param origThrowable Original throwable raised
	 */
	public DataFormatException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
