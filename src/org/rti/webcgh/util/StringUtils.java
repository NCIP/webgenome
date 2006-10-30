/*
$Revision: 1.4 $
$Date: 2006-10-30 19:00:16 $

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

import java.util.ArrayList;
import java.util.List;


/**
 * Utility methods for Strings.
 */
public final class StringUtils {
	
	/**
	 * Constructor.
	 *
	 */
	private StringUtils() {
		
	}

	/**
	 * Compares two strings with tolerance of nulls.
	 * @param s1 A string
	 * @param s2 Another string
	 * @return 
	 * <ul>
	 * 	<li>-1 if s1 lexically less than s2</li>
	 *  <li>0 if s1 and s2 equal</li>
	 *  <li>1 if s1 lexically greater than s2</li>
	 * </ul>
	 */
	public static int compare(final String s1, final String s2) {
		int value = 0;
		if (s1 == null && s2 != null) {
			value = -1;
		} else if (s1 != null && s2 == null) {
			value = 1;
		} else if (s1 != null && s2 != null) {
			if (s1.equals(s2)) {
				value = 0;
			} else {
				for (int i = 0; i < s1.length() && i < s2.length(); i++) {
					int a = (int) s1.charAt(i);
					int b = (int) s2.charAt(i);
					if (a < b) {
						value = -1;
						break;
					} else if (a > b) {
						value = 1;
						break;
					}
				}
				if (value == 0) {
					if (s1.length() < s2.length()) {
						value = -1;
					} else if (s1.length() > s2.length()) {
						value = 1;
					}
				}
			}
		}
		return value;
	}

	/**
	 * Null tolerant string equals operation.
	 * @param s1 A string
	 * @param s2 Another string
	 * @return T/F
	 */
	public static boolean equal(final String s1, final String s2) {
		boolean equal = true;
		if (s1 == null && s2 != null) {
			equal = false;
		} else if (s1 != null && s2 == null) {
			equal = false;
		} else if (s1 != null && s2 != null) {
			equal = s1.equals(s2);
		}
		return equal;
	}

	/**
	 * Remove line breaks from a string.
	 * @param str A String
	 * @return Equivalent string without line breaks
	 */
	public static String removeLineBreaks(final String str) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == '\n' || ch == '\r') {
				continue;
			}
			buff.append(ch);
		}
		return buff.toString();
	}

	/**
	 * Convert a string into a boolean primitive
	 * (i.e. {"true","yes"} -> true).
	 * @param s A boolean string equivalent
	 * @return A boolean value
	 */
	public static boolean toBoolean(final String s) {
		String temp = s.toUpperCase();
		return "TRUE".equals(temp) || "YES".equals(temp)
			|| "ON".equals(temp);               
	}
	
	
	/**
	 * Split string into separate tokens.
	 * @param str A String
	 * @param delimiter Token delimiter
	 * @return Array of tokens
	 */
	public static String[] split(final String str,
			final String delimiter) {
	    List<String> tokens = new ArrayList<String>();
	    int p = 0;
	    while (p < str.length()) {
	        int q = str.indexOf(delimiter, p);
	        if (q < 0) {
	            q = str.length();
	        }
	        if (p == q) {
	            tokens.add("");
	        } else {
	            tokens.add(str.substring(p, q).trim());
	        }
	        p = q + 1;
	    }
	    String[] tokenStr = new String[0];
	    tokenStr = (String[]) tokens.toArray(tokenStr);
	    return tokenStr;
	}
	
	
	
	/**
	 * Remove quotations from string.
	 * @param str A String
	 * @param quoteChar Quote character
	 * @return Unquoted string
	 */
	public static String removeQuotes(final String str,
			final char quoteChar) {
		String newStr = str;
		int length = str.length();
		if (str.charAt(0) == quoteChar
				&& str.charAt(length - 1) == quoteChar) {
			newStr = str.substring(1, length - 1);
		}
		return newStr;
	}
	
	
	/**
	 * Determine if given string is empty--i.e., null
	 * or length < 1.
	 * @param str A String
	 * @return T/F
	 */
	public static boolean isEmpty(final String str) {
		return str == null || str.length() < 1;
	}
    
    /**
     * Trim a String, if it isn't null.
     * @param str A String
     * @return A trimmed String, if it isn't null
     */
    public static String safeTrim(final String str) {
        return isEmpty ( str ) ? str : str.trim() ; 
    }
}
