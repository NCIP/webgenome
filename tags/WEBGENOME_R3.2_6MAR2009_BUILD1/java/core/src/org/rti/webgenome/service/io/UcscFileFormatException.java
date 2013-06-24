/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/

package org.rti.webgenome.service.io;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.util.SystemUtils;

/**
 * Thrown if UCSC data file format is bad.
 * @author dhall
 *
 */
public class UcscFileFormatException
extends WebGenomeApplicationException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public UcscFileFormatException() {
		
	}

	/**
	 * Constructor.
	 * @param msg Message.
	 */
	public UcscFileFormatException(final String msg) {
		super(msg);
	}

	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable.
	 */
	public UcscFileFormatException(
			final Throwable origThrowable) {
		super(origThrowable);
	}

	
	/**
	 * Constructor.
	 * @param msg Message.
	 * @param origThrowable Original throwable.
	 */
	public UcscFileFormatException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
