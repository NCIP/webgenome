/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/MedianFunction.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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


package org.rti.webcgh.analytic;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.ArrayDatumMagnitudeComparator;


/**
 * Defines common methods performed by an aggregate statistical function
 */
public class MedianFunction implements AggregateStatisticalFunction {
	
	
	private List list = new ArrayList();
	
	
	/**
	 * Add value to aggregate
	 * @param datum Measured observation
	 */
	public void add(ArrayDatum datum) {
		list.add(datum);
	}
	
	
	/**
	 * Perform statistical computation
	 * @return Result of statistical computation
	 */
	public double perform() {
		double value = Double.NaN;
		if (list.size() > 0) {
			Collections.sort(list, new ArrayDatumMagnitudeComparator());
			int size = list.size();
			int p = size / 2;
			
			// List of even size, use average of two center values
			if ( size % 2 == 0) {
				ArrayDatum datum1 = (ArrayDatum)list.get(p - 1);
				ArrayDatum datum2 = (ArrayDatum)list.get(p);
				ArrayDatum newDatum = new ArrayDatum(datum1);
				newDatum.add(datum2);
				newDatum.divideBy((float)2.0);
				value = (double)newDatum.magnitude();
			}
			// List of odd size, use center value
			else {
				ArrayDatum datum = (ArrayDatum)list.get(p);
				value = (double)datum.magnitude();
			}	 
		}
		return value;
	}
	
	
	/**
	 * Return clone
	 * @return Clone
	 */
	public AggregateStatisticalFunction deepCopy() {
		MedianFunction func = new MedianFunction();
		List tempList = new ArrayList();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Double doub = (Double)it.next();
			tempList.add(new Double(doub.doubleValue()));
		}
		return func;
	}
	
	
	/**
	 * Reset internal state
	 *
	 */
	public void reset() {
		list = new ArrayList();
	}
	
	
	/**
	 * String representation
	 * @return String representation
	 */
	public String toString() {
		return this.getClass().getName();
	}

}
