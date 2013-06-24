/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:11 $


*/

package org.rti.webgenome.domain;

import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.analysis.AnalyticOperation;

/**
 * Source of data for an experiment derived through
 * an analytic operation where there were multiple
 * input experiments.
 * @author dhall
 *
 */
public class MultiAnalysisDataSourceProperties
extends AnalysisDataSourceProperties {
	
	//
	//  A T T R I B U T E S
	//
	
	/** Input experiments that were run through analytic operation. */
	private Set<Experiment> inputExperiments =
		new HashSet<Experiment>();

	
	//
	//  G E T T E R S  /  S E T T E R S
	//
	
	/**
	 * Get input experiments that were run through analytic operation.
	 * @return Input experiments that were run through analytic operation.
	 */
	public Set<Experiment> getInputExperiments() {
		return inputExperiments;
	}

	/**
	 * Set input experiments that were run through analytic operation.
	 * @param inputExperiments Input experiments that were run through
	 * analytic operation.
	 */
	public void setInputExperiments(
			final Set<Experiment> inputExperiments) {
		this.inputExperiments = inputExperiments;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public MultiAnalysisDataSourceProperties() {
		
	}
	
	/**
	 * Constructor.
	 * @param inputExperiments Input experiments that were run through
	 * analytic operation.
	 * @param operation Analytic operation performed on input experiments.
	 */
	public MultiAnalysisDataSourceProperties(
			final Set<Experiment> inputExperiments,
			final AnalyticOperation operation) {
		super(operation, Experiment.getQuantitationTypes(inputExperiments));
		this.inputExperiments = inputExperiments;
	}
}
