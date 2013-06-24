/*
$Revision: 1.1 $
$Date: 2007-03-29 18:02:01 $


*/

package org.rti.webgenome.core;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.rti.webgenome.util.SystemUtils;

/**
 * Base class for checked exceptions within webCGH.
 */
public class WebGenomeException extends Exception {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
			SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	/** Divider between chained error messages. */
	private static final String DIVIDER = "\n\n***** Nested throwable:\n\n";
	
	/** Nested throwable. */
	private Throwable nestedThrowable = null;
	
	/**
	 * Get nested throwable.
	 * @return Nested throwable.
	 */
	public final Throwable getNestedThrowable() {
		return nestedThrowable;
	}

	/**
	 * Set nexted throwable.
	 * @param nestedThrowable Nested throwable.
	 */
	public final void setNestedThrowable(final Throwable nestedThrowable) {
		this.nestedThrowable = nestedThrowable;
	}

	/**
	 * Constructor.
	 */
	public WebGenomeException() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param msg Message
	 */
	public WebGenomeException(final String msg) {
		super(msg);
	}
	
	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable
	 */
	public WebGenomeException(final Throwable origThrowable) {
		super();
		nestedThrowable = origThrowable;
	}
	
	/**
	 * Constructor.
	 * @param msg Message
	 * @param origThrowable Original throwable
	 */
	public WebGenomeException(final String msg, final Throwable origThrowable) {
		super(msg);
		nestedThrowable = origThrowable;
	}
	
	
	/**
	 * Print stack trace to standard error.
	 */
	public final void printStackTrace() {
		super.printStackTrace();
		if (nestedThrowable != null) {
			System.err.println(DIVIDER);
			nestedThrowable.printStackTrace();
		}
	}
	
	
	/**
	 * Print stack trace to print stream.
	 * @param s Print stream
	 */
	public final void printStackTrace(final PrintStream s) {
		super.printStackTrace(s);
		if (nestedThrowable != null) {
			s.print(DIVIDER);
			nestedThrowable.printStackTrace(s);
		}
	}
	
	
	/**
	 * Print stack trace to print writer.
	 * @param s Print writer
	 */
	public final void printStackTrace(final PrintWriter s) {
		super.printStackTrace(s);
		if (nestedThrowable != null) {
			s.print(DIVIDER);
			nestedThrowable.printStackTrace(s);
		}
	}
}
