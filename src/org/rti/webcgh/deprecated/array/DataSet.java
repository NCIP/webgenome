/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/DataSet.java,v $
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.util.CollectionUtils;

/**
 * Data set for plotting
 */
public class DataSet {
	
	
	// ===============================
	//     Attributes
	// ===============================
	
	private List<Experiment> experiments = new ArrayList<Experiment>();
	
	
	// ==============================
	//     Constructors
	// ==============================
	
	/**
	 * Constructor
	 */
	public DataSet() {}
	
	
	/**
	 * Constructor
	 * @param experiments Experiments
	 */
	public DataSet(Experiment[] experiments) {
		if (experiments != null)
			this.experiments = CollectionUtils.arrayToArrayList(experiments);
	}
	
	
	// ===================================
	//      Public methods
	// ===================================
	
	
	/**
	 * Add an experiment
	 * @param experiment An experiment
	 */
	public void add(Experiment experiment) {
		this.experiments.add(experiment);
	}
	
	
	/**
	 * Get experiment iterator
	 * @return Experiment iterator
	 */
	public ExperimentIterator experimentIterator() {
		return new DataSetExperimentIterator(this.experiments);
	}
	
	
	/**
	 * Narrow given interval if it is wider than data set
	 * @param dto A genome interval DTO
	 */
	public void setEndPoints(GenomeIntervalDto dto) {
		dto.setStart(Long.MAX_VALUE);
		dto.setEnd(Long.MIN_VALUE);
		for (ExperimentIterator it = this.experimentIterator(); it.hasNext();) {
			Experiment exp = it.next();
			exp.expand(dto);
		}
		if (dto.getStart() == Long.MAX_VALUE)
			dto.setStart(0);
		if (dto.getEnd() == Long.MIN_VALUE)
			dto.setEnd(0);
	}
	
	
	/**
	 * Get ordered set of chromosomes associated with this data set
	 * @return Sorted set of Chromosome objects
	 */
    public SortedSet chromosomeSet() {
    	SortedSet set = new TreeSet();
    	for (ExperimentIterator it = this.experimentIterator(); it.hasNext();)
    		set.addAll(it.next().chromosomes());
    	return set;
    }
    
    
    /**
     * Get min y value
     * @return Min y value.  If there are no data points,
     * returns Double.NaN;
     */
    public double minY() {
		double min = Double.MAX_VALUE;
		for (ExperimentIterator it = this.experimentIterator(); it.hasNext();) {
			double candidateMin = it.next().minValue();
			if (! Double.isNaN(candidateMin))
				if (candidateMin < min)
					min = candidateMin;
		}
		return min;
    }
    
    
    /**
     * Get max y value
     * @return Max y value.  If there are no data points,
     * returns Double.NaN;
     */
    public double maxY() {
		double max = Double.MIN_VALUE;
		for (ExperimentIterator it = this.experimentIterator(); it.hasNext();) {
			double candidateMax = it.next().maxValue();
			if (! Double.isNaN(candidateMax))
				if (candidateMax > max)
					max = candidateMax;
		}
		return max;
    }
    
    
    /**
     * Get number of bioassays
     * @return Number of bioassays
     */
    public int numBioAssays() {
    	int num = 0;
    	for (ExperimentIterator it = this.experimentIterator(); it.hasNext();) {
    		Experiment exp = it.next();
    		num += exp.numBioAssays();
    	}
    	return num;
    }
    
    
    /**
     * Number of experiments
     * @return Number of experiments
     */
    public int numExperiments() {
    	return this.experiments.size();
    }
    
    
    /**
     * Bulk set properties based on given data set
     * @param dataSet A data set
     * @param deepCopy Perform deep copy of properties?
     */
    public void bulkSet(DataSet dataSet, boolean deepCopy) {
    	if (deepCopy) {
    		this.experiments = new ArrayList();
    		for (ExperimentIterator it = dataSet.experimentIterator(); it.hasNext();) {
    			Experiment clone = new Experiment();
    			clone.bulkSet(it.next(), true);
    		}
    	} else
    		this.experiments = dataSet.experiments;
    }
    
    
    public QuantifiedIntervals amplificationFrequencies(double threshold) {
    	Collection<QuantifiedIntervals> qis = new ArrayList<QuantifiedIntervals>();
    	Collection<BioAssay> bioassays = new ArrayList<BioAssay>();
    	for (Experiment exp : this.experiments) {
    		for (BioAssayIterator it = exp.bioAssayIterator(); it.hasNext();)
    			bioassays.add(it.next());
    	}
    	for (BioAssay bioassay : bioassays)
    		qis.add(bioassay.amplifiedRegions(threshold).getQuantifiedIntervals(bioassays.size()));
    	return QuantifiedIntervals.merge(qis);
    }
    
    
    public QuantifiedIntervals deletionFrequencies(double threshold) {
    	Collection<QuantifiedIntervals> qis = new ArrayList<QuantifiedIntervals>();
    	Collection<BioAssay> bioassays = new ArrayList<BioAssay>();
    	for (Experiment exp : this.experiments) {
    		for (BioAssayIterator it = exp.bioAssayIterator(); it.hasNext();)
    			bioassays.add(it.next());
    	}
    	for (BioAssay bioassay : bioassays)
    		qis.add(bioassay.deletedRegions(threshold).getQuantifiedIntervals(bioassays.size()));
    	return QuantifiedIntervals.merge(qis);
    }
	
	
	
	// ====================================
	//        Inner classes
	// ====================================
	
	static class DataSetExperimentIterator implements ExperimentIterator {
		
		private Iterator iterator = null;
		
		// ================================
		//    Constructor
		// ================================
		
		/**
		 * Constructor
		 * @param list A list
		 */
		public DataSetExperimentIterator(List list) {
			this.iterator = list.iterator();
		}
		
		/**
		 * Does iterator have any more experiments?
		 * @return T/F
		 */
		public boolean hasNext() {
			return this.iterator.hasNext();
		}
		
		
		/**
		 * Get next experiment
		 * @return An experiment
		 */
		public Experiment next() {
			return (Experiment)this.iterator.next();
		}
	}

}
