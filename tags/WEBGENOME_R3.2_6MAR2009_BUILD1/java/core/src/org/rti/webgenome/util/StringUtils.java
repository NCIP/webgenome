/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.util;

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
