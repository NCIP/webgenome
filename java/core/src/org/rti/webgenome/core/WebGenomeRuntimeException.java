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

import java.io.PrintStream;
import java.io.PrintWriter;

import org.rti.webgenome.util.SystemUtils;

/**
 * Base class for all unchecked exceptions.
 */
public class WebGenomeRuntimeException extends RuntimeException {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	/** Divider between chained error messages. */
	private static final String DIVIDER = "\n\n***** Nested throwable:\n\n";

	/** Nested throwable. */
	private Throwable nestedThrowable = null;


	/**
	 *  Constructor.
	 */
	public WebGenomeRuntimeException() {
		super();
	}

	/**
	 * Constructor.
	 * @param message Message
	 */
	public WebGenomeRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * @param message Message
	 * @param throwable Original throwable
	 */
	public WebGenomeRuntimeException(final String message,
			final Throwable throwable) {
		super(message);
		nestedThrowable = throwable;
	}

	/**
	 * Constructor.
	 * @param throwable Original throwable
	 */
	public WebGenomeRuntimeException(final Throwable throwable) {
		super();
		nestedThrowable = throwable;
	}

	/**
	 * Print stack trace.
	 */
	public final void printStackTrace() {
		super.printStackTrace();
		if (nestedThrowable != null) {
			System.err.println(DIVIDER);
		    nestedThrowable.printStackTrace();
		}
	}

	/**
	 * Print stack trace.
	 * @param out Stream
	 */
	public final void printStackTrace(final PrintStream out) {
		super.printStackTrace(out);
		if (nestedThrowable != null) {
			out.print(DIVIDER);
			nestedThrowable.printStackTrace(out);
		}
	}

	/**
	 * Print stack trace.
	 * @param out Stream
	 */
	public final void printStackTrace(final PrintWriter out) {
		super.printStackTrace(out);
		if (nestedThrowable != null) {
			out.print(DIVIDER);
			nestedThrowable.printStackTrace(out);
		}
	}

}
