/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.service.session;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.util.SystemUtils;

/**
 * An exception that is thrown if the system tries to create
 * a new user account and the account name already exists.
 * @author dhall
 *
 */
public class AccountAlreadyExistsException extends
	WebGenomeApplicationException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/**
	 * Constructor.
	 */
	public AccountAlreadyExistsException() {
		super();
	}

	
	/**
	 * Constructor.
	 * @param msg Message
	 * @param origThrowable Original throwable
	 */
	public AccountAlreadyExistsException(final String msg,
			final Throwable origThrowable) {
		super(msg, origThrowable);
	}

	
	/**
	 * Constructor.
	 * @param msg Message.
	 */
	public AccountAlreadyExistsException(final String msg) {
		super(msg);
	}

	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable.
	 */
	public AccountAlreadyExistsException(final Throwable origThrowable) {
		super(origThrowable);
	}

}
