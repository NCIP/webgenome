/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

import org.rti.webgenome.core.UserInputException;
import org.rti.webgenome.util.SystemUtils;

/**
 * Exception thrown when a user inputs bad analytic operation user
 * configurable properties.
 * @author dhall
 *
 */
public class BadUserConfigurablePropertyException extends UserInputException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 *
	 */
	public BadUserConfigurablePropertyException() {
		
	}

	/**
	 * Constructor.
	 * @param msg Message
	 */
	public BadUserConfigurablePropertyException(final String msg) {
		super(msg);
	}

	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable.
	 */
	public BadUserConfigurablePropertyException(final Throwable origThrowable) {
		super(origThrowable);
	}

	
	/**
	 * Constructor.
	 * @param msg Message.
	 * @param origThrowable Original throwable.
	 */
	public BadUserConfigurablePropertyException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

}
