/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/SimpleNormalizationExperimentOperation.java,v $
$Revision: 1.5 $
$Date: 2006-08-17 18:54:13 $

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

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.analysis.AnalyticException;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.ArrayDatumIterator;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.graph.PlotParameters;
/**
 *  Implements Experiment and Array within based Normalization
 *
 */
public class SimpleNormalizationExperimentOperation extends SimpleNormalizationOperation{
	
	/**
	 * Perform analytical operation
	 * @param data Target data sets
	 * @param params Plot parameters
	 * @return Data set containing the results of the operation
	 * @throws AnalyticException if any problems encountered
	 */
	public Experiment[] perform(Experiment[] data, 
			PlotParameters params) throws AnalyticException {
		Experiment[] newArray = new Experiment[data.length];
		for (int i = 0; i < data.length; i++) {
		    Experiment experiment = data[i];
			Experiment clone = new Experiment();
			clone.bulkSetMetadata(experiment);
			newArray[i] = clone;
			try {
				// Arrays to keep track of biological array specific information   
				AggregateStatisticalFunction globals = operation.deepCopy();
				List statList = new ArrayList();
			
				//	Iterating over genome array data
				for (BioAssayIterator it = experiment.bioAssayIterator(); it.hasNext();){
					BioAssay bioAssay = it.next();
					AggregateStatisticalFunction currentFunc = operation.deepCopy();
				
					//	Iterating over array data and getting values for both the current array
					//  and all array data
					for (ArrayDatumIterator adi = bioAssay.arrayDatumIterator(); adi.hasNext();){
						ArrayDatum ad = adi.next();
						globals.add(ad);
						currentFunc.add(ad);
					}
					// Storing the statistics for each array 
					statList.add(new Double(currentFunc.perform()));
					operation.reset();	
				}
				
				// Save the global mean of all values across arrays in the array group
				double globalMean = globals.perform();
						
				//	Iterating over the list of arrays and array data to 
				//	correcting for array means and the global mean
				Iterator st = statList.iterator();
				BioAssayIterator ar = experiment.bioAssayIterator();
				while (st.hasNext() && ar.hasNext()){
					BioAssay bioAssay = ar.next();
					BioAssay newBioAssay = new BioAssay();
					newBioAssay.bulkSetMetadata(bioAssay);
					clone.add(newBioAssay);
					double stat = ((Double)st.next()).doubleValue();
					for (ArrayDatumIterator adi = bioAssay.arrayDatumIterator(); adi.hasNext();){
						ArrayDatum ad = adi.next();
						ArrayDatum newAd = new ArrayDatum(ad);
						newAd.add((float)(globalMean - stat));
						newBioAssay.add(newAd);
					}
				}
			} catch(Exception e){
				throw new AnalyticException("Error filtering data", e);
			}
		}
		return newArray;
	}


}
