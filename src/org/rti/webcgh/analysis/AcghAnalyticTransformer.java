/*
$Revision: 1.4 $
$Date: 2006-10-31 18:04:26 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.analysis;

import java.util.List;
import org.apache.log4j.Logger;
import org.rti.webcgh.deprecated.analytic.AcghData;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.ArrayDatum;


/**
 * Performs the mapping between ChromosomeArrayData object and AcghData.
 * @author Kungyen
 */
public class AcghAnalyticTransformer {

	/** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(AcghAnalyticTransformer.class);


	/**
	 * Transforms data from ChromosomeArrayData object to AcghData object.
	 * @param chrData The data given as a ChromosomeArrayData object
	 * @return AcghData
	 */
    public AcghData transform(ChromosomeArrayData chrData) {
		List<ArrayDatum> expValuesByChr = chrData.getArrayData();
		int size = expValuesByChr.size();       //# of clones, e.g. # of rows
		double[] log2Ratios = new double[size]; //log2 ratios of copy # changes
		String[] clones = new String[size];     //clone name
		String[] targets = new String[size];    //unique ID, e.g. Well ID
		int[] chromosomes = new int[size];      //chromosome number
		int[] positions = new int[size];        //kb position on the chromosome

		for (int i = 0; i < expValuesByChr.size(); i++) {
			ArrayDatum arrayDatum = expValuesByChr.get(i);
			log2Ratios[i] = (double) arrayDatum.getValue();
			clones[i] = arrayDatum.getReporter().getName();
			targets[i] = arrayDatum.getReporter().getName();
			chromosomes[i] = 1; // set chr# = 1 to avoid problem in R
			//chromosomes[i] = (int) chrData.getChromosome();
			positions[i] = (int) arrayDatum.getReporter().getLocation();
		}

		AcghData acghData = new AcghData();
		acghData.setLog2Ratios(log2Ratios);
		acghData.setClones(clones);
		acghData.setTargets(targets);
		acghData.setChromosomes(chromosomes);
		acghData.setPositions(positions);
		acghData.setSize(size);

		return acghData;
	}


	/**
	 * Transforms AcghData object into ChromosomeArrayData object.
	 * @param acghData The AcghData object after R smoothing
	 * @param origChrData The data of the original ChromosomeArrayData object
	 * @return ChromosomeArrayData
	 */
	public ChromosomeArrayData transform(AcghData acghData, ChromosomeArrayData oriChrData) {
		ChromosomeArrayData newChrData =
			new ChromosomeArrayData(oriChrData.getChromosome());

		double[] smoothedRatios = acghData.getSmoothedRatios(); // smoothed log2 ratio
		List<ArrayDatum> expValuesByChr = oriChrData.getArrayData();
		for (int i = 0; i < expValuesByChr.size(); i++) {
			ArrayDatum arrayDatum = expValuesByChr.get(i);
			ArrayDatum newDatum = new ArrayDatum();
			newDatum.setReporter(arrayDatum.getReporter());
			newDatum.setValue((float) smoothedRatios[i]);
			newChrData.add(newDatum);
		}
		return newChrData;
	}


}
