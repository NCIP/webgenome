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

package org.rti.webgenome.util;

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
