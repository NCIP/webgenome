/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/util/DataAssembler.java,v $
$Revision: 1.7 $
$Date: 2006-08-03 21:52:19 $

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


package org.rti.webcgh.webui.util;


import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.rti.webcgh.analytic.AnalyticException;
import org.rti.webcgh.analytic.AnalyticOperation;
import org.rti.webcgh.analytic.AnalyticPipeline;
import org.rti.webcgh.analytic.DataSetInvalidationException;
import org.rti.webcgh.analytic.DataSetInvalidations;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.graph.PlotParameters;
import org.rti.webcgh.service.AuthenticationException;
import org.rti.webcgh.service.WebcghDatabaseException;


/**
 * Assembles data for plotting.  Performs normalization and other
 * computations as requried.
 */
public class DataAssembler {
		
	
	/**
	 * Constructor
	 */
	public DataAssembler() {}


	/**
	 * Assembles data for statistical analysis
	 * @param experiments Genome array data sets
	 * @param pipeline An analytic pipeline
	 * @param params Plotting parameters
	 * @return Data set
	 * @throws WebcghDatabaseException
	 * @throws AnalyticException
	 * @throws WebcghSystemException
	 * @throws DataSetInvalidationException
	 * @throws AuthenticationException
	 */
	public Experiment[] assembleExperiments
	(
		Experiment[] experiments, AnalyticPipeline pipeline,
		PlotParameters params
	) throws WebcghDatabaseException, AnalyticException, WebcghSystemException,
		DataSetInvalidationException, AuthenticationException {
		if (pipeline == null)
			return experiments;
		
		// Perform basic validation on data
		DataSetInvalidations invalidations = new DataSetInvalidations();
		//ValidationUtil.basicValidation(dataSets);
		
		// Retain original experiments
		Experiment[] origExperiments = experiments;
		
		// Make shallow copies of originals and mark as raw
		experiments = this.copyAndMarkAsRaw(origExperiments);
		
		// Invoke analytic pipeline
		for (Iterator it = pipeline.getOperations().iterator(); it.hasNext() && 
				invalidations.getInvalidations().size() < 1;) {
			AnalyticOperation op = (AnalyticOperation)it.next();
			DataSetInvalidations temp = op.validate(experiments, params);
			if (temp != null && temp.getInvalidations().size() > 0)
				invalidations.addInvalidations(temp);
			if (invalidations.getInvalidations().size() < 1) {
				Experiment[] newExperiments = op.perform(experiments, params);
				experiments = newExperiments;
			}
		}
		
		// If any data sets invalide, throw exception
		if (invalidations.getInvalidations().size() > 0) {
			DataSetInvalidationException e = new DataSetInvalidationException(invalidations.getMessages());
			e.setInvalidations(invalidations);
			throw e;
		}
		
		// Add raw data back if not in experiment list
		if (! this.containsRaws(experiments) && pipeline.numOperations() > 0) {
			Experiment[] raws = this.copyAndMarkAsRaw(origExperiments);
			experiments = this.concatenate(raws, experiments);
		}
		
		return experiments;
	}
	
	
	private Experiment[] copyAndMarkAsRaw(Experiment[] experiments) {
		if (experiments == null)
			return null;
		Experiment[] copies = new Experiment[experiments.length];
		for (int i = 0; i < experiments.length; i++) {
			Experiment oldExp = experiments[i];
			Experiment newExp = new Experiment();
			copies[i] = newExp;
			newExp.bulkSetMetadata(oldExp);
			for (BioAssayIterator it = oldExp.bioAssayIterator(); it.hasNext();) {
				BioAssay oldBioAssay = it.next();
				BioAssay newBioAssay = new BioAssay();
				newBioAssay.bulkSet(oldBioAssay, false);
				newExp.add(newBioAssay);
			}
			newExp.markAsRaw();
		}
		return copies;
	}
	
	
	private Experiment[] concatenate(Experiment[] a1, Experiment[] a2) {
		assert a1 != null && a2 != null;
		Experiment[] experiments = new Experiment[a1.length + a2.length];
		int count = 0;
		for (int i = 0; i < a1.length; i++)
			experiments[count++] = a1[i];
		for (int i = 0; i < a2.length; i++)
			experiments[count++] = a2[i];
		return experiments;
	}
	
	
	private boolean containsRaws(Experiment[] experiments) {
		boolean contains = false;
		if (experiments != null) {
			for (int i = 0; i < experiments.length && ! contains; i++)
				contains = experiments[i].isRaw();
		}
		return contains;
	}
}
