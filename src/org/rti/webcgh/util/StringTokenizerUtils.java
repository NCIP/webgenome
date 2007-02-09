/*
$Revision: 1.1 $
$Date: 2007-02-09 03:09:00 $

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
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
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

package org.rti.webcgh.util;

import java.util.StringTokenizer;

/**
 * General utility methods for working with
 * <code>StringTokenizer</code> objects.
 * @author dhall
 *
 */
public final class StringTokenizerUtils {

	
	/**
	 * Constructor.
	 */
	private StringTokenizerUtils() {
		
	}
	
	
	/**
	 * Skip ahead over some number of tokens to return
	 * a token desired by the caller.  If <code>numSteps</code>
	 * is one, the next token is returned.  If
	 * <code>numSteps</code> is N, then N - 1 tokens are
	 * skipped and the Nth token returned.
	 * @param numSteps Number of steps away the desired token
	 * lies
	 * @param tokenizer Tokenizer
	 * @return A token
	 */
	public static String skip(final int numSteps,
			final StringTokenizer tokenizer) {
		
		// Check args
		if (numSteps < 1) {
			throw new IllegalArgumentException(
					"Number of steps must be a positive integer");
		}
		if (tokenizer == null) {
			throw new IllegalArgumentException(
					"Tokenizer must not be null");
		}
		
		// Skip over all tokens up to the one desired
		// by the caller
		String token = null;
		for (int i = 0; i < numSteps - 1; i++) {
			if (!tokenizer.hasMoreTokens()) {
				throw new IndexOutOfBoundsException(
						"Number of steps exceeds number of "
						+ "remaining tokens");
			}
			tokenizer.nextToken();
		}
		
		// Get token desired by caller
		if (!tokenizer.hasMoreTokens()) {
			throw new IndexOutOfBoundsException(
					"Number of steps exceeds number of "
					+ "remaining tokens");
		}
		token = tokenizer.nextToken();
		
		return token;
	}
}
