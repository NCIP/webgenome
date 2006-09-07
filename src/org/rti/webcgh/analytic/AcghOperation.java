/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/AcghOperation.java,v $
$Revision: 1.13 $
$Date: 2006-09-07 18:54:52 $

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
import java.util.List;

import org.rti.webcgh.analysis.AnalyticException;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.graph.PlotParameters;
import org.rti.webcgh.service.analysis.AcghService;



/**
 * The analytic operation for aCGH data smoothing
 */
public class AcghOperation implements NormalizationOperation {
	
	private AcghDataTransformer acghDataTransformer = new AcghDataTransformer();
	private AcghService acghService = null;
	
	/**
	 * Getter method for acghService
	 * @return 
	 */
	public AcghService getAcghService() {
		return acghService;
	}

	/**
	 * Setter method for acghService
	 * @param acghService
	 */
	public void setAcghService(AcghService acghService) {
		this.acghService = acghService;
	}

	/**
	 * Setter method for id
	 */
	public void setId(Long id) {
		// TODO Auto-generated method stub

	}

	/**
	 * Getter method for id
	 */
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Validates input data for the operation
	 * @param data The data given as an array of Experiment objects
	 * @param params Plotting parameters
	 * @return Return any exceptions due to data invalidity
	 */
	public DataSetInvalidations validate(Experiment[] data,
			PlotParameters params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Executes this analytic pipeline operation on the input data and returns the results
	 * @param data The Experiment objects supplied as the input from the pipline
	 * @param params Parameters used for plotting
	 * @return The resultant Experiment objects after the analytic operation is performed
	 */
	public Experiment[] perform(Experiment[] data, PlotParameters params)
			throws AnalyticException {

		Experiment[] resultData = new Experiment[data.length];
		
		try {
			
			for (int i = 0; i < data.length; i++) {

				Experiment exp = data[i];
				
				BioAssay assay;
				List<BioAssay> origAssays = new ArrayList<BioAssay>();
				List<BioAssay> resultAssays = new ArrayList<BioAssay>();
				
				origAssays = (List<BioAssay>) exp.getBioAssays();

				for (int j = 0; j < origAssays.size(); j++) {

					assay = origAssays.get(j);
					AcghData acghData = this.acghDataTransformer.transform(assay);
					this.acghService.run(acghData);
					resultAssays.add(this.acghDataTransformer.transform(acghData, assay));
				}
				
				for (int k = 0; k < resultAssays.size(); k++) {
					origAssays.add(resultAssays.get(k));
				}
				
				exp.setBioAssays(origAssays);
				resultData[i] = exp;
				
				/*
				AcghData acghData = this.acghDataTransformer.transform(exp);
				this.acghService.run(acghData);
				
				Experiment resultExp = this.acghDataTransformer.transform(acghData, exp);
				resultData[i] = resultExp;
				*/
			}
			
		} catch (Exception e) {
			throw new AnalyticException("Error performing aCGH operation", e);
		}
		
		return resultData;
	}

}
