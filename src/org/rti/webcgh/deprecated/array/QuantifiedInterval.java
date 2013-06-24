/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/QuantifiedInterval.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:34:38 $

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

package org.rti.webcgh.deprecated.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An interval in some one dimensional space with
 * an associated quantitation value
 *
 */
public class QuantifiedInterval implements Comparable {
	
	
	// =============================
	//        Attributes
	// =============================
	
	private long start = 0;
	private long end = 0;
	private double value = 0.0;
	
	/**
	 * @return End of interval
	 */
	public long getEnd() {
		return end;
	}
	
	/**
	 * @param end End of interval
	 */
	public void setEnd(long end) {
		this.end = end;
	}
	
	/**
	 * @return Start of interval
	 */
	public long getStart() {
		return start;
	}
	
	/**
	 * @param start Start of interval
	 */
	public void setStart(long start) {
		this.start = start;
	}
	
	/**
	 * @return Quantified value associated with interval
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * @param value Quantified value associated with interval
	 */
	public void setValue(double value) {
		this.value = value;
	}

	
	// =====================================
	//       Constructors
	// =====================================
	
	/**
	 * Constructor
	 */
	public QuantifiedInterval() {}

	
	/**
	 * Constructor
	 * @param start Start of interval
	 * @param end End of interval
	 * @param value Value associated with interval
	 */
	public QuantifiedInterval(long start, long end, double value) {
		this.start = start;
		this.end = end;
		this.value = value;
	}
	
	
	/**
	 * Constructor
	 * @param qi A quantified interval
	 */
	public QuantifiedInterval(QuantifiedInterval qi) {
		this.start = qi.start;
		this.end = qi.end;
		this.value = qi.value;
	}

	
	// ==================================
	//       Comparable interface
	// ==================================
	
	/**
	 * Compare to method.  See documentation on
	 * <code>Comparable</code>
	 * @param o An object
	 * @return See documentation on
	 * <code>Comparable</code>
	 */
	public int compareTo(Object o) {
		int value = 0;
		if (! (o instanceof QuantifiedInterval))
			throw new IllegalArgumentException(
					"Cannot compare a 'QuantifiedInteval' and a '" + 
					o.getClass().getName() + "'");
		QuantifiedInterval ival = (QuantifiedInterval)o;
		if (this.start < ival.start)
			value = -1;
		else if (this.start == ival.start) {
			if (this.end < ival.end)
				value = -1;
			else if (this.end == ival.end)
				value = 0;
			else if (this.end > ival.end)
				value = 1;
		} else if (this.start > ival.start)
			value = 1;
		return value;
	}
	
	// ==============================
	//     Over-rides from Object
	// ==============================

	@Override
	public boolean equals(Object obj) {
		return this.compareTo(obj) == 0;
	}
	
	
	// ========================================
	//       Other public methods
	// ========================================
	
	/**
	 * Add value of given QuantifiedInterval to this.
	 * @param qi A QuantifiedInterval
	 * @throws IllegalArgumentException if the two intervals
	 * are not equal (i.e. the <code>equals</code> method
	 * return <code>false</code>
	 */
	public void add(QuantifiedInterval qi) {
		if (! this.equals(qi))
			throw new IllegalArgumentException(
					"Can only add equal QuantifiedIntervals " +
					"(i.e., covering same interval)");
		this.value += qi.value;
	}
	
	
	/**
	 * Do the two quantified intervals overlap?
	 * @param qi A quantified interval
	 * @return T/F
	 * @return
	 */
	public boolean overlaps(QuantifiedInterval qi) {
		return qi.end > this.start && qi.start < this.end;
	}
	
	
	/**
	 * Merge two quantified interval together.  The result may be
	 * one, two, or three intervals:
	 * <ul>
	 * 	<li>
	 * 		If the two intervals are equal (i.e. the <code>equals</code>
	 * 		method returns <code>true</code>), method returns one
	 * 		interval equivalent to calling the <code>plus</code> method.
	 * 	</li>
	 * 	<li>
	 * 		If the two intervals do not overlap, the method returns
	 * 		the two intervals unaltered
	 * 	</li>
	 * 	<li>
	 * 		If the two intervals overlap, the method returns three
	 * 		intervals.  The outermost two consist of the nonoverlapping
	 * 		intervals.  The inner interval is bounded by the endpoints
	 * 		of the overlap with a value equal to the sum of the two
	 * 		original intervals.
	 * 	</li>
	 * </ul>
	 * 
	 * Note: Method may alter the original intervals and may in fact return
	 * the original intervals.
	 * 
	 * @param qi A quantified interval
	 * @return Quantified intervals
	 */
	public List<QuantifiedInterval> merge(QuantifiedInterval qi) {
		List<QuantifiedInterval> intervals = new ArrayList<QuantifiedInterval>();
		if (this.equals(qi)) {
			this.add(qi);
			intervals.add(this);
		} else if (! this.overlaps(qi)) {
			intervals.add(this);
			intervals.add(qi);
		} else {
			QuantifiedInterval intersection = this.intersection(qi);
			intervals.add(intersection);
			intervals.addAll(this.chop(intersection));
			intervals.addAll(qi.chop(intersection));
		}
		Collections.sort(intervals);
		return intervals;
	}
	
	
	/**
	 * Find intersection of two intervals.
	 * @param qi A quantified interval
	 * @return A new quantified interval whose endpoints correspond
	 * to the region of overlap between the original intervals.  The
	 * value of this new interval is equal to the sums of the original
	 * intervals.  Returns <code>null</code> if intervals do not overlap.
	 */
	public QuantifiedInterval intersection(QuantifiedInterval qi) {
		if (! this.overlaps(qi))
			return null;
		long maxStart = (this.start > qi.start)? this.start : qi.start;
		long minEnd = (this.end < qi.end)? this.end : qi.end;
		return new QuantifiedInterval(maxStart, minEnd, this.value + qi.value);
	}
	
	
	/**
	 * Chop given interval from this.  Returns intervals that remain.
	 * @param qi A quantified interval
	 * @return Remaining intervals, if any
	 */
	public List<QuantifiedInterval> chop(QuantifiedInterval qi) {
		List<QuantifiedInterval> intervals = new ArrayList<QuantifiedInterval>();
		if (! this.overlaps(qi))
			intervals.add(this);
		else {
			if (this.start < qi.start)
				intervals.add(new QuantifiedInterval(this.start, qi.start, this.value));
			if (this.end > qi.end)
				intervals.add(new QuantifiedInterval(qi.end, this.end, this.value));
		}
		return intervals;
	}
	
	
	/**
	 * Is given interval properly contained in this?  i.e., are
	 * the endpoints of this distal or equal to those of the given interval?
	 * @param qi A quantified interval
	 * @return T/F
	 */
	public boolean properlyContains(QuantifiedInterval qi) {
		return this.start <= qi.start && this.end >= qi.end;
	}
	
	
	/**
	 * Less than function
	 * @param qi A quantified interval
	 * @return T/F
	 */
	public boolean lessThan(QuantifiedInterval qi) {
		return this.compareTo(qi) < 0;
	}
	
	
	/**
	 * Greater than function
	 * @param qi A quantified interval
	 * @return T/F
	 */
	public boolean greaterThan(QuantifiedInterval qi) {
		return this.compareTo(qi) > 0;
	}
}
