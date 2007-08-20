/*
$Revision: 1.2 $
$Date: 2007-08-20 22:09:37 $

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

package org.rti.webgenome.service.job;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.Logger;
import org.rti.webgenome.analysis.AnalyticException;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.MultiAnalysisDataSourceProperties;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.analysis.SerializedDataTransformer;
import org.rti.webgenome.service.dao.ShoppingCartDao;

/**
 * Job performing an analytic operation.
 * @author dhall
 */
public class AnalysisJob extends AbstractJob {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(AnalysisJob.class);
	
	//
	//  A T T R I B U T E S
	//
	
	/** Source of data, operation to perform, and user specified params. */
	private MultiAnalysisDataSourceProperties dataSourceProperties = null;
	
	/** Map of input bioassay IDs to output bioassay names. */
	private Map<Long, String> outputBioAssayNames = null;
	
	/** Map of input experiment IDs to output experiment names. */
	private Map<Long, String> outputExperimentNames = null;

	
	//
	//  G E T T E R S / S E T T E R S
	//
	
	/**
	 * Get data source properties giving source of data,
	 * operation to perform, and user specified params.
	 * @return Data source properties
	 */
	public MultiAnalysisDataSourceProperties getDataSourceProperties() {
		return dataSourceProperties;
	}


	/**
	 * Set data source properties giving source of data,
	 * operation to perform, and user specified params.
	 * @param dataSourceProperties Data source properties
	 */
	public void setDataSourceProperties(
			final MultiAnalysisDataSourceProperties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}


	/**
	 * Get map of input bioassay IDs to output bioassay names.
	 * @return Map of input bioassay IDs to output bioassay names.
	 */
	public Map<Long, String> getOutputBioAssayNames() {
		return outputBioAssayNames;
	}


	/**
	 * Set map of input bioassay IDs to output bioassay names.
	 * @param outputBioAssayNames Map of input bioassay IDs to
	 * output bioassay names.
	 */
	public void setOutputBioAssayNames(
			final Map<Long, String> outputBioAssayNames) {
		this.outputBioAssayNames = outputBioAssayNames;
	}


	/**
	 * Get map of input experiment IDs to output experiment names.
	 * @return Map of input experiment IDs to output experiment names.
	 */
	public Map<Long, String> getOutputExperimentNames() {
		return outputExperimentNames;
	}


	/**
	 * Set map of input experiment IDs to output experiment names.
	 * @param outputExperimentNames Map of input experiment IDs to
	 * output experiment names.
	 */
	public void setOutputExperimentNames(
			final Map<Long, String> outputExperimentNames) {
		this.outputExperimentNames = outputExperimentNames;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.  This should only be used by the
	 * persistence framework and not called explicitly
	 * from business code.
	 */
	public AnalysisJob() {
	
	}
	
	/**
	 * Constructor.
	 * @param experiments Experiments on which to perform operation.
	 * @param operation Operation to perform.
	 * @param outputBioAssayNames Map of input bioassay IDs to output
	 * bioassay names.
	 * @param outputExperimentNames Map of input experiment IDs to output
	 * experiment names.
	 * @param userId User account name
	 */
	public AnalysisJob(final Collection<Experiment> experiments,
			final AnalyticOperation operation,
			final Map<Long, String> outputBioAssayNames,
			final Map<Long, String> outputExperimentNames,
			final String userId) {
		super(userId);
		this.dataSourceProperties = new MultiAnalysisDataSourceProperties(
				new HashSet<Experiment>(experiments), operation);
		this.outputBioAssayNames = outputBioAssayNames;
		this.outputExperimentNames = outputExperimentNames;
		StringBuffer buff = new StringBuffer("Analytic operation ");
		buff.append(operation.getName());
		buff.append(" on experiments ");
		int count = 0;
		for (Experiment exp : experiments) {
			if (count++ > 0) {
				buff.append(", ");
			}
			buff.append(exp.getName());
		}
		this.setDescription(buff.toString());
	}


	//
	//  O V E R R I D E S
	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final JobServices jobServices) {
		ShoppingCartDao sDao = jobServices.getShoppingCartDao();
		ShoppingCart cart = sDao.load(this.getUserId());
		SerializedDataTransformer transformer =
			jobServices.getIoService().getSerializedDataTransformer();
		AnalysisService aService = jobServices.getAnalysisService();
		Collection<Experiment> experiments =
			this.dataSourceProperties.getInputExperiments();
		AnalyticOperation op =
			this.dataSourceProperties.getSourceAnalyticOperation();
		try {
			LOGGER.info("Analysis job starting for user "
					+ this.getUserId());
			aService.performAnalyticOperation(experiments, op, cart,
					this.outputExperimentNames, this.outputBioAssayNames,
					transformer);
			sDao.update(cart);
			this.setTerminationMessage("Succeeded");
			LOGGER.info("Analysis job completed for user "
					+ this.getUserId());
		} catch (AnalyticException e) {
			this.setTerminationMessage("Failed: " + e.getMessage());
			LOGGER.info("Analysis job failed for user " + this.getUserId());
			LOGGER.info(e);
		}
	}
}
