/*
$Revision: 1.1 $
$Date: 2007-07-18 21:42:48 $

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

package org.rti.webgenome.service.analysis;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.rti.webgenome.analysis.AnalyticException;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.AnalyticPipeline;
import org.rti.webgenome.analysis.MultiExperimentStatelessOperation;
import org.rti.webgenome.analysis.SingleBioAssayStatelessOperation;
import org.rti.webgenome.analysis.SingleExperimentStatelessOperation;
import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.util.IdGenerator;


/**
 * An overall facade for performing analyses.  Normally
 * all client classes wishing to perform an analysis will
 * interact only with class.  This class is designed to be
 * used as a singleton.
 * @author dhall
 *
 */
public class AnalysisService {
	
	/** Colors for altered genome segments. */
	private static final List<Color> ALTERATION_COLORS =
		new ArrayList<Color>();
	static {
		ALTERATION_COLORS.add(Color.DARK_GRAY);
		ALTERATION_COLORS.add(Color.GRAY);
	}
	
    /** Experiment ID generator. */
    private IdGenerator experimentIdGenerator = null;
    
    /** Bioassay ID generator. */
    private IdGenerator bioAssayIdGenerator = null;
    
    /**
     * Setter method used for dependency injection of generator
     * for bioassay IDs.
     * @param bioAssayIdGenerator Generator for bioassay IDs.
     */
    public void setBioAssayIdGenerator(
    		final IdGenerator bioAssayIdGenerator) {
		this.bioAssayIdGenerator = bioAssayIdGenerator;
	}


    /**
     * Setter method used for dependency injection of generator
     * for experiment IDs.
     * @param experimentIdGenerator Generator for experiment IDs.
     */
	public void setExperimentIdGenerator(
			final IdGenerator experimentIdGenerator) {
		this.experimentIdGenerator = experimentIdGenerator;
	}

	/**
	 * Perform given analytic operation on given experiments.
	 * The operation may be performed on either the analytic
	 * server or application server, depending on how much
	 * data are contained in the experiments.
	 * @param experiments Experiments to process
	 * @param operation Operation to perform
	 * @param cart Shopping cart to deposit new data into
	 * @param outputExperimentNames Map of input experiment IDs to
	 * output experiment names provided by user.  These are
	 * used to name the generated experiments
	 * @param outputBioAssayNames Map of input bioassay IDs to
	 * output bioassay names provided by user.  These are
	 * used to name the generated experiments
	 * (i.e., on the application server), or {@code false}
	 * if the operation was offloaded to the analytic server for
	 * batch processing.
	 * @param dataTransformer Data transformer
	 * @throws AnalyticException if the analytic operation is
	 * unable to complete due to improper data
	 */
	public void performAnalyticOperation(
			final Collection<Experiment> experiments,
			final AnalyticOperation operation,
			final ShoppingCart cart,
			final Map<Long, String> outputExperimentNames,
			final Map<Long, String> outputBioAssayNames,
			final DataTransformer dataTransformer)
	throws AnalyticException {
		if (operation instanceof MultiExperimentStatelessOperation) {
			Experiment output =
			dataTransformer.performMultiExperimentStatelessOperation(
					experiments,
					(MultiExperimentStatelessOperation) operation);
			output.setId(this.experimentIdGenerator.nextId());
			int count = 0;
			for (BioAssay ba : output.getBioAssays()) {
				ba.setId(this.bioAssayIdGenerator.nextId());
				ba.setColor(ALTERATION_COLORS.get(count++
						% ALTERATION_COLORS.size()));
			}
			cart.add(output);
		} else {
    		for (Experiment input : experiments) {
    			Experiment output =
    				dataTransformer.perform(input, operation);
    			output.setId(this.experimentIdGenerator.nextId());
    			for (BioAssay ba : output.getBioAssays()) {
    				ba.setId(this.bioAssayIdGenerator.nextId());
    				ba.setColor(cart.getBioassayColorChooser().nextColor());
    			}
    			cart.add(output);
    			String expName = outputExperimentNames.get(input.getId());
    			if (expName != null) {
    				output.setName(expName);
    			}
    			if (operation instanceof SingleBioAssayStatelessOperation
    					|| (operation instanceof AnalyticPipeline
    							&& ((AnalyticPipeline) operation).
    							producesSingleBioAssayPerExperiment())) {
    				for (BioAssay ba : output.getBioAssays()) {
    					String bioAssayName =
    						outputBioAssayNames.get(
    								ba.getParentBioAssayId());
    					if (bioAssayName != null) {
    						ba.setName(bioAssayName);
    					}
    				}
    			} else if (operation
    					instanceof SingleExperimentStatelessOperation) {
    				Collection<BioAssay> bioAssays = output.getBioAssays();
    				if (bioAssays.size() > 0) {
    					bioAssays.iterator().next().setName(expName);
    				}
    			}
    		}
		}
	}
	
	
	/**
	 * Re-run given analytic operation on given experiment.  This
	 * operation was previously run on the experiment.  This time
	 * it may have different user-configured parameter settings.
	 * The operation will either execute immediately (i.e., on the
	 * application server) or be offloaded to the analysis server
	 * for batch processing if there are enough data.
	 * @param experiment Experiment to run through operation
	 * @param operation Operation to perform.
	 * @param dataTransformer Data transformer
	 * @throws AnalyticException if a bad data value causes the
	 * operation to fail
	 */
	public void rePerformAnalyticOperation(
			final Experiment experiment,
			final AnalyticOperation operation,
			final DataTransformer dataTransformer)
	throws AnalyticException {
		QuantitationType qType = experiment.getQuantitationType();
    	Collection<UserConfigurableProperty> props =
    		operation.getUserConfigurableProperties(qType);
		dataTransformer.reCompute(experiment, props);
	}
	
	
	/**
	 * Re-run analytic operation on given derived experiments.  These
	 * operations were previously run on the experiments.  This time
	 * they may have different user-configured parameter settings.
	 * The operation will either execute immediately (i.e., on the
	 * application server) or be offloaded to the analysis server
	 * for batch processing if there are enough data.
	 * @param experiments Derived experiments to run through
	 * their operation again
	 * @param dataTransformer Data transformer
	 * @throws AnalyticException if a bad data value causes the
	 * operation to fail
	 */
	public void rePerformAnalyticOperation(
			final Collection<Experiment> experiments,
			final DataTransformer dataTransformer)
	throws AnalyticException {
		for (Experiment exp : experiments) {
			AnalyticOperation op = exp.getSourceAnalyticOperation();
			QuantitationType qType = exp.getQuantitationType();
			Collection<UserConfigurableProperty> props =
				op.getUserConfigurableProperties(qType);
			dataTransformer.reCompute(exp, props);
		}
	}
}
