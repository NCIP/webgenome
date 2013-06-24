/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/


package org.rti.webgenome.service.io;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.util.SystemUtils;

/**
 * Exception in SMD file format.  See documentation on SmdDataStream
 * for a description of this file format.
 *
 */
public class SmdFormatException extends WebGenomeApplicationException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 *
	 */
	public SmdFormatException() {
		super();
	}

	/**
	 * Constructor.
	 * @param msg A message
	 * @param origThrowable Original throwable
	 */
	public SmdFormatException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg A message
	 */
	public SmdFormatException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor.
	 * @param origThrowable Original throwable
	 */
	public SmdFormatException(final Throwable origThrowable) {
		super(origThrowable);
	}

}
