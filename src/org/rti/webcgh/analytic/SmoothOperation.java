/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/SmoothOperation.java,v $
$Revision: 1.6 $
$Date: 2006-08-17 18:54:12 $

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

import org.rti.webcgh.analysis.AnalyticException;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.core.WebcghApplicationException;
import org.rti.webcgh.graph.PlotParameters;


/**
 * This operation smooths CGH data across the chromosome by averaging
 * adjacent measurements in a sliding window.  The smoothed
 * value for probe N is the average value of probes N - W/2 through N + W/2,
 * where W is the size of the sliding window.
 */
public class SmoothOperation implements NormalizationOperation {
	
    // ==================================
    //    Attributes
    // ==================================
    
	private int window = 5;
	private Long id = null;
	private int delta = 0;
	
	
	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	
	
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Setter for property window
	 * @param window Size of smoothing sliding window
	 */
	public void setWindow(int window) {
		this.window = window;
		this.delta = (int)Math.floor((double)window / 2.0);
	}
	
	
	/**
	 * Getter for property window
	 * @return Size of smoothing sliding window
	 */
	public int getWindow() {
		return window;
	}
	
	
	// =====================================
	//        Constructors
	// =====================================
	
	/**
	 * Constructor
	 *
	 */
	public SmoothOperation() {}
	
	
	/**
	 * Constructor
	 * @param window Sliding window size
	 */
	public SmoothOperation(int window) {
		this.window = window;
	}
	
	
	
	// =========================================
	//   Methods in AnalyticOperation interface
	// =========================================
	
	/**
	 * Validate data set prior to performing operation
	 * @param data Target data sets
	 * @param params Plot parameters
	 * @return Validation results
	 */
	public DataSetInvalidations validate(Experiment[] data,
			PlotParameters params) {
		return null;
	}
	
	
	/**
	 * Perform analytical operation
	 * @param data Target data sets
	 * @param params Plot parameters
	 * @return Data set containing the results of the operation
	 * @throws AnalyticException if any problems encountered
	 */
	public Experiment[] perform(Experiment[] data, 
			PlotParameters params) throws AnalyticException {
	    Experiment[] newData = new Experiment[data.length];
	    for (int i = 0; i < data.length; i++) {
	        Experiment exp = data[i];
	        Experiment newExp = new Experiment();
	        newData[i] = newExp;
	        newExp.bulkSetMetadata(exp);
	        for (BioAssayIterator it = exp.bioAssayIterator(); it.hasNext();) {
	            BioAssay assay = it.next();
	            BioAssay newAssay = new BioAssay();
	            newExp.add(newAssay);
	            newAssay.bulkSetMetadata(assay);
	            int n = assay.numArrayDatum();
	            for (int j = 0; j < n; j++) {
	            	ArrayDatum newDatum = new ArrayDatum(assay.getArrayDatum(j));
	            	this.average(newDatum, assay, j, n);
	            	try {
						newAssay.add(newDatum);
					} catch (WebcghApplicationException e) {
						throw new AnalyticException(e);
					}
	            }
	        }
	    }
	    return newData;
	}
	
	
	// =========================================
	//        Private methods
	// =========================================
	
	private void average(ArrayDatum datum, BioAssay bioAssay, int idx, int size) {
	    int p = idx - this.delta;
	    int q = p + this.window;
	    int count = 1;
	    for (int i = p; i <= q; i++) {
	    	if (i >= 0 && i < size && i != idx) {
	    		ArrayDatum currDatum = bioAssay.getArrayDatum(i);
	    		if (datum.sameChromosome(currDatum)) {
	    			count++;
	    			datum.add(currDatum);
	    		}
	    	}
	    }
	    datum.divideBy(count);
	}
}
