/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $


*/

package org.rti.webgenome.domain;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.util.SystemUtils;

/**
 * Exception thrown when a genome interval is encoded improperly.
 * @see org.rti.webcgh.util.GenomeIntervalCoder.
 * @author dhall
 */
public class GenomeIntervalFormatException
	extends WebGenomeApplicationException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	/**
	 * Constructor.
	 */
	public GenomeIntervalFormatException() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param msg Message
	 */
	public GenomeIntervalFormatException(final String msg) {
		super(msg);
	}
	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable
	 */
	public GenomeIntervalFormatException(final Throwable origThrowable) {
		super(origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg Message
	 * @param origThrowable Original throwable
	 */
	public GenomeIntervalFormatException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
