/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.core;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.rti.webgenome.util.SystemUtils;

/**
 * Base class for checked exceptions within webCGH.
 */
public class WebcghException extends Exception {
	
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
	public WebcghException() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param msg Message
	 */
	public WebcghException(final String msg) {
		super(msg);
	}
	
	
	/**
	 * Constructor.
	 * @param origThrowable Original throwable
	 */
	public WebcghException(final Throwable origThrowable) {
		super();
		nestedThrowable = origThrowable;
	}
	
	/**
	 * Constructor.
	 * @param msg Message
	 * @param origThrowable Original throwable
	 */
	public WebcghException(final String msg, final Throwable origThrowable) {
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