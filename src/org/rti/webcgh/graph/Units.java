/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/Units.java,v $
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

package org.rti.webcgh.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Chromosome BP units
 */
public class Units {
	
	// =============================
	//      Constants
	// =============================
	
	/**
	 * Base pairs
	 */
	public static final Units BP = new Units("BP", 1);
	
	
	/**
	 * Kilobases
	 */
	public static final Units KB = new Units("KB", 1000);
	
	
	/**
	 * Megabases
	 */
	public static final Units MB = new Units("MB", 1000000);
	
	
	// ==================================
	//     Other static variables
	// ==================================
	
	private static final Map INDEX = new HashMap();
	
	static {
		INDEX.put(BP.name, BP);
		INDEX.put(KB.name, KB);
		INDEX.put(MB.name, MB);
	}
	
	
	// ===================================
	//      Attributes
	// ===================================
	
	private final String name;
	private final int numericBase;
	
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	
	
	// ===================================
	//        Constructors
	// ===================================
	
	private Units(String name, int numericBase) {
		this.name = name;
		this.numericBase = numericBase;
	}
	
	
	// =====================================
	//    Public methods
	// =====================================
	
	/**
	 * Return printable string
	 * @return A string
	 */
	public String toPrettyString() {
		return this.name;
	}
	
	
	/**
	 * Get number formatter
	 * @return A number formatter
	 */
	public NumberFormatter numberFormatter() {
	    return new BPFormatter(this.numericBase);
	}
	
	
	/**
	 * Convert given value to BP
	 * @param value A value
	 * @return Value in BP
	 */
	public long toBp(double value) {
		return (long)(value * this.numericBase);
	}
	
	// =====================================
	//       Static methods
	// =====================================
	
	/**
	 * Return units corresponding to name
	 * @param name Name of units
	 * @return Units
	 */
	public static Units getUnits(String name) {
		return (Units)INDEX.get(name.toUpperCase());
	}
	
	
	/**
	 * Get all known units
	 * @return Units
	 */
	public static Units[] getUnits() {
		Collection unitsCol = INDEX.values();
		Units[] unitsA = new Units[0];
		unitsA = (Units[])unitsCol.toArray(unitsA);
		return unitsA;
	}

}
