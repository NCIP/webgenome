/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/AcghDataTransformer.java,v $
$Revision: 1.1 $
$Date: 2006-05-02 21:39:30 $

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.ArrayDatumIterator;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.Chromosome;
import org.rti.webcgh.array.Experiment;

import sun.security.krb5.internal.ac;

public class AcghDataTransformer {
	
	/**
	 * Transforms data for aCGH into an Experiment object
	 * @param acghData
	 * @return
	 */
	public Experiment transform(AcghData acghData, Experiment origExperiment) {
		
		double[] log2Ratios;      // log2 ratios of copy number changes
				// rows correspond to the clones and columns to the samples
		String[] clones;          // clone name
		String[] targets;         // unique ID, e.g. Well ID
		int[] chromosomes;        // chromosome number
		        // X chromosome = 23 in human and 20 in mouse,
		        // Y chromosome = 24 in human and 21 in mouse
		int[] positions;          // kb position on the chromosome
		double[] smoothedRatios;  // smoothed value of log2 ratio
		int size;                 // number of clones/number of rows
		
		log2Ratios = acghData.getLog2Ratios();
		clones = acghData.getClones();
		targets = acghData.getTargets();
		chromosomes = acghData.getChromosomes();
		positions = acghData.getPositions();
		smoothedRatios = acghData.getSmoothedRatios();
		size = acghData.getSize();
		
		// TODO: run the aCGH command here to get the smoothing results
		
		Experiment newExperiment = new Experiment();
		newExperiment.bulkSet(origExperiment, true);
		
		ArrayDatumIterator arrayDatumIter = newExperiment.arrayDatumIterator();
		int i = 0;
		for ( ; arrayDatumIter.hasNext() ; ) {
			ArrayDatum arrayDatum = arrayDatumIter.next();
			arrayDatum.setMagnitude((float) smoothedRatios[i]);
			i++;
		}
		
		return newExperiment;		
	}
	
	
	
	/**
	 * Transforms data from an Experiment object into the form needed for aCGH
	 * @param experiment
	 * @return
	 */
	public AcghData transform(Experiment experiment) {
		AcghData acghData = new AcghData();
		
		// see if the Experiment object has any chromosomes.  if not, return empty object
		Set chromosomeSet = experiment.chromosomes();
		int numChromosomes = chromosomeSet.size();
		if (numChromosomes <= 0)
			return acghData;
		
		// if Experiment object has any chromosomes, save data for aCGH in arrays
		
		double[] log2Ratios;      // log2 ratios of copy number changes
        		// rows correspond to the clones and columns to the samples
		String[] clones;          // clone name
		String[] targets;         // unique ID, e.g. Well ID
		int[] chromosomes;        // chromosome number
		        // X chromosome = 23 in human and 20 in mouse,
		        // Y chromosome = 24 in human and 21 in mouse
		int[] positions;          // kb position on the chromosome
		double[] smoothedRatios;  // smoothed value of log2 ratio
		int size;                 // number of clones/number of rows
		

		size = 0;
		
		// iterate over ArrayDatum objects
		ArrayDatumIterator arrayDatumIter = experiment.arrayDatumIterator();
		
		// find how many rows of data we will send to aCGH
		for ( ; arrayDatumIter.hasNext() ; ) {
			size++;
		}
		
		// initialize arrays

		log2Ratios = new double[size];
//		clones = clonesList.toArray(new String[0]);
//		targets = targetsList.toArray(new String[0]);
		clones = new String[size];
		targets = new String[size];
		chromosomes = new int[size];
		positions = new int[size];
		
		
		arrayDatumIter = experiment.arrayDatumIterator();
		for ( int i = 0; arrayDatumIter.hasNext() ; i++ ) {
			ArrayDatum arrayDatum = arrayDatumIter.next(); 

			log2Ratios[i] = (double) arrayDatum.getQuantitation().getValue();
			clones[i] = arrayDatum.getReporter().getName();
			targets[i] = arrayDatum.getReporter().getId().toString();
			chromosomes[i] = (int) arrayDatum.chromosome().getNumber();
			positions[i] = (int) arrayDatum.getGenomeLocation().getLocation();
			
		}
		
		acghData.setLog2Ratios(log2Ratios);
		acghData.setClones(clones);
		acghData.setTargets(targets);
		acghData.setChromosomes(chromosomes);
		acghData.setPositions(positions);
		acghData.setSize(size);

		return acghData;
	}

}
