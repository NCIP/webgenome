/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source$
$Revision$
$Date$

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

package org.rti.webcgh.deprecated;

/**
 * Physical map units
 */
public class Units {
	
	
	private final String name;
	private final double conversion;
	
	/**
	 * Real numbers
	 */
	public static final Units RealNumbers = new Units("Real Numbers", 1.0);
	
	/**
	 * Base pairs
	 */
	public static final Units BP = new Units("BP", 1.0);
	
	/**
	 * Kilobases
	 */
	public static final Units KB = new Units("KB", 1000.0);
	
	/**
	 * Megabases
	 */
	public static final Units MB = new Units("MB", 1000000.0);
	
	
	/**
	 * Return units object associated based on name
	 * @param name Name of units object
	 * @return Units object
	 */
	public static Units getUnits(String name) {
		Units units = null;
		if (name != null) {
			if ("BP".equals(name.toUpperCase()))
				units = BP;
			else if ("KB".equals(name.toUpperCase()))
				units = KB;
			else if ("MB".equals(name.toUpperCase()))
				units = MB;
			else if ("Real Numbers".equals(name.toUpperCase()))
				units = RealNumbers;
		}
		return units;
	}
	
	
	/**
	 * Convert given value (in units associated with this object)
	 * to base pairs
	 * @param value
	 * @return Value in bas pairs
	 */
	public double toBp(double value) {
		return value * conversion;
	}
	
	
	/**
	 * Convert given value in BP
	 * @param bpValue Value in BP
	 * @return Value in units associated with this object
	 */
	public double fromBp(double bpValue) {
		return bpValue / conversion;
	}
	
	
	/**
	 * Name of units
	 * @return Name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Constructor
	 * @param name Name
	 * @param conversion Conversion factor for obtaining base pairs
	 */
	private Units(String name, double conversion) {
		this.name = name;
		this.conversion = conversion;
	}
	
	
	/**
	 * 
	 * @return Conversion factor for obtaining base pairs
	 */
	private double getConversion() {
		return conversion;
	}

}
