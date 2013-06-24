/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 18:02:01 $


*/


package org.rti.webgenome.core;

import org.rti.webgenome.util.SystemUtils;

/**
 * Represents failure of a system that webCGH depends on.
 */
public class WebGenomeSystemException extends WebGenomeRuntimeException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public WebGenomeSystemException() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param msg Message
	 */
	public WebGenomeSystemException(final String msg) {
		super(msg);
	}
	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable
	 */
	public WebGenomeSystemException(final Throwable origThrowable) {
		super(origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg Message
	 * @param origThrowable Original throwable
	 */
	public WebGenomeSystemException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
