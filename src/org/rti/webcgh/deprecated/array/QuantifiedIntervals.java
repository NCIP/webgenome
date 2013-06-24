/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/QuantifiedIntervals.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:34:39 $

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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Specialized container for <code>QuantifiedIntervals</code>
 *
 */
public class QuantifiedIntervals {
	
	// ====================================
	//         Attributes
	// ====================================
	
	private List<QuantifiedInterval> intervals = new ArrayList<QuantifiedInterval>();
	private boolean sorted = false;
	
	/**
	 * @return Get intervals
	 */
	public List<QuantifiedInterval> getIntervals() {
		return intervals;
	}

	
	// ==================================
	//        Constructors
	// ==================================
	
	/**
	 * Constructor
	 */
	public QuantifiedIntervals() {}
	
	
	// ======================================
	//      Other public methods
	// ======================================
	
	/**
	 * Add an interval
	 * @param qi An interval
	 */
	public void add(QuantifiedInterval qi) {
		this.intervals.add(qi);
		this.sorted = false;
	}
	
	
	public void merge(QuantifiedIntervals qi) {
		this.sort();
		qi.sort();
		ListIterator<QuantifiedInterval> it1 = this.intervals.listIterator();
		ListIterator<QuantifiedInterval> it2 = qi.intervals.listIterator();
		QuantifiedInterval i1 = (it1.hasNext())? it1.next() : null;
		QuantifiedInterval i2 = (it2.hasNext())? it2.next() : null;
		while (i1 != null || i2 != null) {
			
			// First iterator not empty
			if (i1 != null) {
				
				// Second iterator not empty
				if (i2 != null) {
					
					// Intervals equal
					if (i1.equals(i2)) {
						i1.add(i2);
						i1 = (it1.hasNext())? it1.next() : null;
						i2 = (it2.hasNext())? it2.next() : null;
					}
					
					// Intervals not equal but overlapping
					else if (i1.overlaps(i2)) {
						it1.remove();
						it2.remove();
						QuantifiedInterval intersection = i1.intersection(i2);
						List<QuantifiedInterval> intervals = i1.chop(intersection);
						intervals.add(intersection);
						Collections.sort(intervals);
						int count = 0;
						for (QuantifiedInterval interval : intervals) {
							it1.add(interval);
							count++;
						}
						for (int i = 0; i < count; i++)
							i1 = it1.previous();
						count = 0;
						for (QuantifiedInterval interval: i2.chop(intersection)) {
							it2.add(interval);
							count++;
						}
						for (int i = 0; i < count; i++)
							i2 = it2.previous();
						i1 = (it1.hasNext())? it1.next() : null;
						i2 = (it2.hasNext())? it2.next() : null;
					}
					
					// Intervals not overlapping
					else if (! i1.overlaps(i2)) {
						if (i1.lessThan(i2)) {
							while (i1 != null && ! i1.overlaps(i2) && i1.lessThan(i2))
								i1 = (it1.hasNext())? it1.next() : null;
						} else if (i2.lessThan(i1)) {
							it1.previous();
							while (i2 != null && ! i2.overlaps(i1) && i2.lessThan(i1)) {
								if (i2 != null)
									it1.add(i2);
								i2 = (it2.hasNext())? it2.next() : null;
							}
							it1.next();
						}
					}
					
				// Second iterator empty
				} else
					break;
				
			//  First iterator empty
			} else {
				it1.add(i2);
				while (it2.hasNext())
					it1.add(it2.next());
				break;
			}
		}
	}
	
	
	/**
	 * Merge group of interval sets
	 * @param intervals Group of interval sets
	 * @return Quantified intervals
	 */
	public static QuantifiedIntervals merge(Collection<QuantifiedIntervals> intervals) {
		if (intervals == null)
			return null;
		Iterator<QuantifiedIntervals> it = intervals.iterator();
		QuantifiedIntervals template = it.next();
		while (it.hasNext()) {
			QuantifiedIntervals source = it.next();
			template.merge(source);
		}
		return template;
	}
	
	
	private void sort() {
		if (! this.sorted)
			Collections.sort(this.intervals);
		this.sorted = true;
	}
	

}
