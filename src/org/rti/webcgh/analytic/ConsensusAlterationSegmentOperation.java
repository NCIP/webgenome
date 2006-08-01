/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/ConsensusAlterationSegmentOperation.java,v $
$Revision: 1.2 $
$Date: 2006-08-01 19:37:10 $

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.ArrayDatumIterator;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.Reporter;
import org.rti.webcgh.plot.PlotParameters;

/**
 * Calculates minimum amplified and deleted segments
 */
public class ConsensusAlterationSegmentOperation implements AnalyticOperation {
	
	
	// ==============================
	//      Attributes
	// ==============================
	
	private Long id = null;
	
	
	/**
	 * Set operation ID
	 * @param id ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * Get operation ID
	 * @return ID
	 */
	public Long getId() {
		return id;
	}
	
	
	/**
	 * Validate data set prior to performing operation
	 * @param data Target data sets
	 * @param params Plot parameters
	 * @return Validation results
	 */
	public DataSetInvalidations validate(Experiment[] data,
			PlotParameters params) {
		DataSetInvalidations inv = null;
		if (Double.isNaN(params.getUpperMaskValue()) || 
				Double.isNaN(params.getLowerMaskValue()))
			inv.addInvalidation(new DataSetInvalidation("Must set both lower " +
					"and upper data mask values in plot parameters"));
		return inv;
	}
	
	
	/**
	 * Perform analytical operation
	 * @param experiments Target data sets
	 * @param params Plot parameters
	 * @return Data set containing the results of the operation
	 * @throws AnalyticException if any problems encountered
	 */
	public Experiment[] perform(Experiment[] experiments, 
			PlotParameters params) throws AnalyticException {
		for (int i = 0; i < experiments.length; i++) {
			this.addConsensusAltSegs(experiments[i], RelOp.GT, params.getUpperMaskValue(),
					"MCAR", "Minimum common amplified regions");
			this.addConsensusAltSegs(experiments[i], RelOp.LT, params.getLowerMaskValue(),
					"MCDR", "Minimum common deleted regions");
		}
		return experiments;
	}
	
	
	// =======================================
	//        Private methods
	// =======================================
	
	
	private void addConsensusAltSegs(Experiment experiment, RelOp op, double threshold, 
			String bioAssayName, String bioAssayDescr) {
		int bioAssayCount = 0;
		Map datumIndex = new HashMap();
		Map magnitudes = new HashMap();
		for (BioAssayIterator it = experiment.bioAssayIterator(); it.hasNext();) {
			BioAssay tempBioAssay = it.next();
			bioAssayCount++;
			for (ArrayDatumIterator bai = tempBioAssay.arrayDatumIterator(); bai.hasNext();) {
				ArrayDatum datum = bai.next();
				double magnitude = (double)datum.magnitude();
				if (op.eval(magnitude, threshold)) {
					Reporter key = datum.getReporter();
					List magList = null;
					if (bioAssayCount == 1) {
						datumIndex.put(key, Boolean.TRUE);
						magList = new ArrayList();
						magnitudes.put(key, magList);
					} else {
						Boolean check = (Boolean)datumIndex.get(key);
						if (check != null) {
							datumIndex.put(key, Boolean.FALSE);
							magList = (List)magnitudes.get(key);
						}
					}
					magList.add(new Double(magnitude));
				}
			}
			for (Iterator checkIt = datumIndex.keySet().iterator(); checkIt.hasNext();) {
				Object key = checkIt.next();
				Boolean check = (Boolean)datumIndex.get(key);
				if (Boolean.FALSE.equals(check)) {
					datumIndex.remove(key);
					magnitudes.remove(key);
				} else
					datumIndex.put(key, Boolean.FALSE);
			}
		}
		
		BioAssay bioAssay = new BioAssay();
		bioAssay.setName(bioAssayName);
		bioAssay.setDescription(bioAssayDescr);
		for (Iterator it = magnitudes.keySet().iterator(); it.hasNext();) {
			Reporter rep = (Reporter)it.next();
			
		}
	}
	
	
	// ======================================
	//      Inner classes
	// ======================================
	
	
	/**
	 * Wrapper for a relational operation
	 *
	 */
	interface RelOp {
		
		/**
		 * Less than
		 */
		public static final RelOp LT = new Lt();
		
		/**
		 * Greater than
		 */
		public static final RelOp GT = new Gt();
		
		/**
		 * Evaluate
		 * @param val1 Value 1
		 * @param val2 Value 2
		 * @return T/F
		 */
		public boolean eval(double val1, double val2);
	}
	
	
	static class Gt implements RelOp {
		
		/**
		 * Evaluate
		 * @param val1 Value 1
		 * @param val2 Value 2
		 * @return T/F
		 */
		public boolean eval(double val1, double val2) {
			return val1 > val2;
		}
	}
	
	
	static class Lt implements RelOp {
		
		/**
		 * Evaluate
		 * @param val1 Value 1
		 * @param val2 Value 2
		 * @return T/F
		 */
		public boolean eval(double val1, double val2) {
			return val1 < val2;
		}
	}

}
