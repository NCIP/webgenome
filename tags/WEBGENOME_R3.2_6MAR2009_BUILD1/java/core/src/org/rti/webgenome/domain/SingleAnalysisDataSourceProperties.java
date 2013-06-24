/*
$Revision: 1.1 $
$Date: 2007-07-27 22:21:19 $


*/

package org.rti.webgenome.domain;

import org.rti.webgenome.analysis.AnalyticOperation;

/**
 * Source of data for an experiment derived through
 * an analytic operation with a single input
 * experiment.
 * @author dhall
 *
 */
public class SingleAnalysisDataSourceProperties
extends AnalysisDataSourceProperties {
	
	//
	//  A T T R I B U T E S
	//
	
	/**
	 * Experiment that was input into the analytic operation.
	 */
	private Experiment inputExperiment = null;
	
	
	//
	//  G E T T E R S  /  S E T T E R S
	//
	
	/**
	 * Get experiment that was input into the analytic operation.
	 * @return Experiment that was input into the analytic operation.
	 */
	public Experiment getInputExperiment() {
		return inputExperiment;
	}

	/**
	 * Set experiment that was input into the analytic operation.
	 * @param inputExperiment Experiment that was input into
	 * the analytic operation.
	 */
	public void setInputExperiment(final Experiment inputExperiment) {
		this.inputExperiment = inputExperiment;
	}

	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public SingleAnalysisDataSourceProperties() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param inputExperiment Experiment that was input into the
	 * analytic operation.
	 * @param operation Analytic operation performed on input
	 * experiment.
	 */
	public SingleAnalysisDataSourceProperties(final Experiment inputExperiment,
			final AnalyticOperation operation) {
		super(operation, inputExperiment.getQuantitationType());
		this.inputExperiment = inputExperiment;
	}
}
