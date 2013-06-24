/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-02-05 23:28:35 $


*/

package org.rti.webgenome.domain;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.util.SystemUtils;

/**
 * This exception class represents an error caused by trying to
 * read a chromosome number with an invalid format.  Typically
 * a good format is numeric or "X" or "Y."
 * @author dhall
 *
 */
public class BadChromosomeFormat extends WebGenomeApplicationException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public BadChromosomeFormat() {
		
	}

	/**
	 * Constructor.
	 * @param msg Error message
	 */
	public BadChromosomeFormat(final String msg) {
		super(msg);
	}

	/**
	 * Constructor.
	 * @param origThrowable Some source throwable
	 */
	public BadChromosomeFormat(final Throwable origThrowable) {
		super(origThrowable);
	}

	/**
	 * Constructor.
	 * @param msg Error message
	 * @param origThrowable Source throwable
	 */
	public BadChromosomeFormat(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
