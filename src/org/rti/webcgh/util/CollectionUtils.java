/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/util/CollectionUtils.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Provides extended functionality to classes in <code>java.lang.util</code>
 */
public class CollectionUtils {
	
	

	/**
	 * Convert array into list
	 * @param array An array
	 * @return A list containing contents of array
	 */
	public static ArrayList arrayToArrayList(Object[] array) {
		ArrayList list = new ArrayList();
		if (array != null)
			for (int i = 0; i < array.length; i++)
				list.add(array[i]);
		return list;
	}
	
	
	/**
	 * Null tolerant value-based comparison
	 * @param c1 A collection of objects
	 * @param c2 Another collection of objects
	 * @return T/F
	 */
	public static boolean equal(Collection c1, Collection c2) {
		if (c1 == null && c2 == null)
			return true;
		if (c1 != null && c2 == null)
			return false;
		if (c1 == null && c2 != null)
			return false;
		if (c1.size() != c2.size())
			return false;
		Iterator it1 = c1.iterator();
		Iterator it2 = c2.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			Object o1 = it1.next();
			Object o2 = it2.next();
			if (! ObjectUtils.equal(o1, o2))
				return false;
		}
		return true;
	}
	/**
	 * Value-based comparason
	 * @param m1 A map
	 * @param m2 Another map
	 * @return T/F
	 */
	public static boolean equal(Map m1, Map m2) {
		if (m1 == null && m2 == null)
			return true;
		if (m1 != null && m2 == null)
			return false;
		if (m1 == null && m2 != null)
			return false;
		if (m1.size() != m2.size())
			return false;
		Iterator it = m1.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object o1 = m1.get(key);
			Object o2 = m2.get(key);
			if (! ObjectUtils.equal(o1, o2))
				return false;
		}
		return true;
	}
	
	
	/**
	 * Are two arrays equal by value comparison?
	 * @param array1 An array
	 * @param array2 Another array
	 * @return T/F
	 */
	public static boolean equal(Object[] array1, Object[] array2) {
	    if (CollectionUtils.isEmpty(array1) & ! CollectionUtils.isEmpty(array2))
	        return false;
	    if (! CollectionUtils.isEmpty(array1) & CollectionUtils.isEmpty(array2))
	        return false;
	    if (array1 != null && array2 != null) {
	        if (array1.length != array2.length)
	            return false;
	        for (int i = 0; i < array1.length; i++) {
				Object o1 = array1[i];
				Object o2 = array2[i];
				if (! ObjectUtils.equal(o2, o2))
					return false;
	        }
	    }
	    return true;
	}
	
	
	/**
	 * Is array empty?
	 * @param array An array
	 * @return <code>true</code> if array has length 0 or is null, <code>false</code>
	 * otherwise
	 */
	public static boolean isEmpty(Object[] array) {
	    return array == null || array.length < 1;
	}
	
	
	/**
	 * Are arrays same length?
	 * @param a1 An array
	 * @param a2 Another array
	 * @return T/F
	 */
	public static boolean sameLength(Object[] a1, Object[] a2) {
	    if (a1 == null && a2 != null)
	        return false;
	    if (a1 != null && a2 == null)
	        return false;
	    if (a1 != null && a2 != null)
	        if (a1.length != a2.length)
	            return false;
	    return true;
	}
	
	
	/**
	 * Are arrays same length?
	 * @param a1 An array
	 * @param a2 Another array
	 * @return T/F
	 */
	public static boolean sameLength(int[] a1, int[] a2) {
	    if (a1 == null && a2 != null)
	        return false;
	    if (a1 != null && a2 == null)
	        return false;
	    if (a1 != null && a2 != null)
	        if (a1.length != a2.length)
	            return false;
	    return true;
	}
	
	
	/**
	 * Split string of delimited integer values into an array
	 * of integers
	 * @param str A string
	 * @param delimiter Delimiter
	 * @return Array of ints
	 */
	public static long[] splitIntoLongs(String str, String delimiter) {
	    StringTokenizer tok = new StringTokenizer(str, delimiter);
	    List tokens = new ArrayList();
	    while (tok.hasMoreTokens())
	        tokens.add(new Integer(tok.nextToken()));
	    long[] ints = new long[tokens.size()];
	    int i = 0;
	    for (Iterator it = tokens.iterator(); it.hasNext();) {
	        int p = ((Integer)it.next()).intValue();
	        ints[i++] = p;
	    }
	    return ints;
	}
	
	
	/**
	 * Does array contain value?
	 * @param array An array
	 * @param value A value
	 * @return T/F
	 */
	public static boolean contains(int[] array, int value) {
	    boolean contains = false;
	    for (int i = 0; i < array.length; i++)
	        if (array[i] == value)
	            contains = true;
	    return contains;
	}
	
	
	/**
	 * Does array contain value?
	 * @param array An array
	 * @param value A value
	 * @return T/F
	 */
	public static boolean contains(char[] array, char value) {
	    boolean contains = false;
	    for (int i = 0; i < array.length; i++)
	        if (array[i] == value)
	            contains = true;
	    return contains;
	}
	
	
	/**
	 * Concatenate two arrays
	 * @param a1 First array
	 * @param a2 Second array
	 * @return Concatenated array
	 */
	public static String[] concatenate(String[] a1, String[] a2) {
	    String[] newA = new String[a1.length + a2.length];
	    for (int i = 0; i < a1.length; i++)
	        newA[i] = a1[i];
	    for (int i = 0; i < a2.length; i++)
	        newA[i + a1.length] = a2[i];
	    return newA;
	}
}
