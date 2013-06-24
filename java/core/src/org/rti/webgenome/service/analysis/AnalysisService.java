/*
$Revision: 1.7 $
$Date: 2008-02-22 03:54:10 $


*/

package org.rti.webgenome.service.analysis;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rti.webgenome.analysis.AnalyticException;
import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.MultiExperimentStatelessOperation;
import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.domain.AnalysisDataSourceProperties;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.MultiAnalysisDataSourceProperties;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.SingleAnalysisDataSourceProperties;
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
					(MultiExperimentStatelessOperation) operation, null);
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
				for (BioAssay ba : output.getBioAssays()) {
					Long parentId = ba.getParentBioAssayId();
					if (parentId != null) {
						String bioAssayName =
							outputBioAssayNames.get(
									parentId);
						if (bioAssayName != null) {
							ba.setName(bioAssayName);
						} 
					} else if (expName != null) {
						ba.setName(expName);
					}
				}
    		}
		}
	}
	
	
	/**
	 * Re-run given analytic operation on given experiment.  This
	 * operation was previously run on the experiment.  This time
	 * it may have different user-configured parameter settings.
	 * @param experiment Experiment to run through operation
	 * @param operation Operation to perform.
	 * @param dataTransformer Data transformer
	 * @return Old serialized files that have been replaced
	 * @throws AnalyticException if a bad data value causes the
	 * operation to fail
	 */
	public Set<String> rePerformAnalyticOperation(
			final Experiment experiment,
			final AnalyticOperation operation,
			final DataTransformer dataTransformer)
	throws AnalyticException {
		Set<String> fNames = new HashSet<String>();
		if (operation instanceof MultiExperimentStatelessOperation) {
			MultiAnalysisDataSourceProperties props =
				(MultiAnalysisDataSourceProperties)
				experiment.getDataSourceProperties();
			Collection<Experiment> inputs = props.getInputExperiments();
			dataTransformer.performMultiExperimentStatelessOperation(
					inputs,
					(MultiExperimentStatelessOperation) operation,
					experiment);
			int count = 0;
			for (BioAssay ba : experiment.getBioAssays()) {
				ba.setId(this.bioAssayIdGenerator.nextId());
				ba.setColor(ALTERATION_COLORS.get(count++
						% ALTERATION_COLORS.size()));
			}
		} else {
			QuantitationType qType = experiment.getQuantitationType();
			Collection<QuantitationType> qTypes =
				new ArrayList<QuantitationType>();
			qTypes.add(qType);
	    	Collection<UserConfigurableProperty> props =
	    		operation.getUserConfigurableProperties(qTypes);
			fNames = dataTransformer.reCompute(experiment, props);
		}
		Collection<QuantitationType> qTypes =
			new ArrayList<QuantitationType>();
		qTypes.add(experiment.getQuantitationType());
		((AnalysisDataSourceProperties)
				experiment.getDataSourceProperties()).
				setSourceAnalyticOperation(operation, qTypes);
		return fNames;
	}
	
	
	/**
	 * Re-run analytic operation on given derived experiments.  These
	 * operations were previously run on the experiments.  This time
	 * they may have different user-configured parameter settings.
	 * @param experiments Derived experiments to run through
	 * their operation again
	 * @param dataTransformer Data transformer
	 * @return Names of files replaced but not deleted
	 * @throws AnalyticException if a bad data value causes the
	 * operation to fail
	 */
	public Set<String> rePerformAnalyticOperation(
			final Collection<Experiment> experiments,
			final DataTransformer dataTransformer)
	throws AnalyticException {
		Set<String> fNames = new HashSet<String>();
		for (Experiment exp : experiments) {
			if (!exp.isDerived()) {
				throw new IllegalArgumentException(
						"Experiments must be derived");
			}
			AnalyticOperation operation = (
					(AnalysisDataSourceProperties)
					exp.getDataSourceProperties()).
					getSourceAnalyticOperation();
			if (operation instanceof MultiExperimentStatelessOperation) {
				MultiAnalysisDataSourceProperties props =
					(MultiAnalysisDataSourceProperties)
					exp.getDataSourceProperties();
				Collection<Experiment> inputs = props.getInputExperiments();
				dataTransformer.performMultiExperimentStatelessOperation(
						inputs,
						(MultiExperimentStatelessOperation) operation,
						exp);
				int count = 0;
				for (BioAssay ba : exp.getBioAssays()) {
					ba.setId(this.bioAssayIdGenerator.nextId());
					ba.setColor(ALTERATION_COLORS.get(count++
							% ALTERATION_COLORS.size()));
				}
			} else {
				SingleAnalysisDataSourceProperties dsProps =
					(SingleAnalysisDataSourceProperties)
					exp.getDataSourceProperties();
				AnalyticOperation op = dsProps.getSourceAnalyticOperation();
				QuantitationType qType = exp.getQuantitationType();
				Collection<QuantitationType> qTypes =
					new ArrayList<QuantitationType>();
				qTypes.add(qType);
				Collection<UserConfigurableProperty> props =
					op.getUserConfigurableProperties(qTypes);
				Set<String> replacedFiles =
					dataTransformer.reCompute(exp, props);
				fNames.addAll(replacedFiles);
			}
			Collection<QuantitationType> qTypes =
				new ArrayList<QuantitationType>();
			qTypes.add(exp.getQuantitationType());
			((AnalysisDataSourceProperties)
					exp.getDataSourceProperties()).
					setSourceAnalyticOperation(operation, qTypes);
		}
		return fNames;
	}
}
