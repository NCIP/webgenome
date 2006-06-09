/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/ChromosomalAlterationSet.java,v $
$Revision: 1.3 $
$Date: 2006-06-09 20:01:27 $

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


package org.rti.webcgh.array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.util.CollectionUtils;

/**
 * Set of chromosomal alterations, typically corresponding to a single chromosome
 *
 */
public class ChromosomalAlterationSet {
	
	private List<ChromosomalAlteration> alterations = new ArrayList<ChromosomalAlteration>();
	
	
	// =========================
	//     Constructors
	// =========================
	
	public ChromosomalAlterationSet() {}
	
	
	
	// ===============================
	//     Public methods
	// ===============================
	
	/**
	 * Add given alteration
	 * @param chromosomalAlteration An alteration
	 */
	public void add(ChromosomalAlteration chromosomalAlteration) {
		this.alterations.add(chromosomalAlteration);
	}
	
	
	/**
	 * Add given alterations
	 * @param chromosomalAlteration Alterations
	 */
	public void add(ChromosomalAlteration[] chromosomalAlterations) {
		this.alterations.addAll(CollectionUtils.arrayToArrayList(chromosomalAlterations));
	}
	
	
	/**
	 * Add given alteration set
	 * @param chromosomalAlterationSet Alteration set
	 */
	public void add(ChromosomalAlterationSet chromosomalAlterationSet) {
		if (chromosomalAlterationSet != null)
			this.alterations.addAll(chromosomalAlterationSet.alterations);
	}
	
	
	/**
	 * Get chromosomal alteration iterator
	 * @return Chromosomal alteration iterator
	 */
	public ChromosomalAlterationIterator chromosomalAlterationIterator() {
		return new ChromosomalAlterationIterator() {
			private Iterator it = alterations.iterator();
			public boolean hasNext() {
				return it.hasNext();
			}
			public ChromosomalAlteration next() {
				return (ChromosomalAlteration)it.next();
			}
			public void remove() {
				it.remove();
			}
		};
	}
	
	
	public void bulkSet(ChromosomalAlterationSet caSet, boolean deepCopy) {
		if (deepCopy) {
			this.alterations = new ArrayList();
			for (ChromosomalAlterationIterator it = caSet.chromosomalAlterationIterator(); it.hasNext();) {
				ChromosomalAlteration alt = it.next();
				ChromosomalAlteration clone = new ChromosomalAlteration();
				clone.bulkSet(alt);
				this.add(clone);
			}
		} else
			this.alterations = caSet.alterations;
	}
	
	
	/**
	 * Convert alteration set into quantified intervals
	 * @param numSamples Number of samples
	 * @return Quantified intervals
	 */
	public QuantifiedIntervals getQuantifiedIntervals(int numSamples) {
		double quantity = 1.0 / (double)numSamples;
		QuantifiedIntervals qi = new QuantifiedIntervals();
		if (this.alterations != null) {
			for (int i = 0; i < this.alterations.size(); i++) {
				ChromosomalAlteration prevAlt = null, thisAlt = null, nextAlt = null;
				thisAlt = this.alterations.get(i);
				if (i > 0) {
					prevAlt = this.alterations.get(i - 1);
					if (! prevAlt.sameChromosome(thisAlt))
						prevAlt = null;
				}
				if (i < this.alterations.size() - 1) {
					nextAlt = this.alterations.get(i + 1);
					if (! nextAlt.sameChromosome(thisAlt))
						nextAlt = null;
				}
				long p = (prevAlt == null)? thisAlt.startBp() : (thisAlt.startBp() + prevAlt.endBp()) / (long)2;
				long q = (nextAlt == null)? thisAlt.endBp() : (thisAlt.endBp() + nextAlt.startBp()) / (long)2;
				qi.add(new QuantifiedInterval(p, q, quantity));
			}
		}
		return qi;
	}
	
	
	// =================================
	//      Static methods
	// =================================
	
	public static ChromosomalAlterationSet intersection(
			ChromosomalAlterationSet[] sets) {
		if (sets == null)
			return null;
		ChromosomalAlterationSet isect = new ChromosomalAlterationSet();
		if (sets.length > 0) {
			isect.add(sets[0]);
			for (ChromosomalAlterationIterator it = isect.chromosomalAlterationIterator();
				it.hasNext();) {
				ChromosomalAlteration alt = it.next();
				boolean overlaps = false;
				for (int i = 1; i < sets.length; i++) {
					for (ChromosomalAlterationIterator jt = 
						sets[i].chromosomalAlterationIterator(); jt.hasNext();) {
						ChromosomalAlteration alt2 = jt.next();
						if (alt.overlaps(alt2)) {
							alt.intersection(alt2);
							overlaps = true;
						}
					}
				}
				if (! overlaps)
					it.remove();
			}
		}
		return isect;
	}

}
