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
 * Graphical color coded data scale showing the intensity of expression.
 */
public class DataScale {
	
	private String label = "Fold change color code";
	private double minSat = -1.5;
	private double maxSat = 1.5;
	private int numBins = 17;
	
	
	
	/**
	 * Constructor
	 * @param minSat Minimum saturation value
	 * @param maxSat Maximum saturation value
	 *
	 */
	public DataScale(double minSat, double maxSat) {
		this.minSat = minSat;
		this.maxSat = maxSat;
	}
	
	
	/**
	 * Constructor
	 * @param minSat Minimum saturation value
	 * @param maxSat Maximum saturation value
	 * @param numBins Number of colored bins
	 *
	 */
	public DataScale(double minSat, double maxSat, int numBins) {
		this(minSat, maxSat);
		this.numBins = numBins;
	}
	
	
	protected double binRange() {
		return (maxSat - minSat) / numBins;
	}
	
	
	/**
	 * Which bin does value map to?
	 * @param value A value
	 * @return A bin number
	 */
	public int binNum(double value) {
		int num = 0;
		double delta = value - minSat;
		num = (int)Math.floor(delta / binRange());
		if (num < 0)
			num = 0;
		if (num > numBins - 1)
			num = numBins - 1;
		return num;
	}
	
	
	/**
	 * Setter for property minSat
	 * @param minSat Minimum saturation value
	 */
	public void setMinSat(double minSat) {
		this.minSat = minSat;
	}
	
	
	/**
	 * Getter for property minSat
	 * @return Minimum saturation value
	 */
	public double getMinSat() {
		return minSat;
	}
	
	
	/**
	 * Setter for property maxSat
	 * @param maxSat Maximum saturation value
	 */
	public void setMaxSat(double maxSat) {
		this.maxSat = maxSat;
	}
	
	
	/**
	 * Getter for property maxSat
	 * @return Maximum saturation value
	 */
	public double getMaxSat() {
		return maxSat;
	}
	
	
	/**
	 * @return Label for scale
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return Number of colored bins
	 */
	public int getNumBins() {
		return numBins;
	}

	/**
	 * @param string Label for scale
	 */
	public void setLabel(String string) {
		label = string;
	}

	/**
	 * @param i Number of colored bins
	 */
	public void setNumBins(int i) {
		numBins = i;
	}

}
