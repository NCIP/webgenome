/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;

/**
 * Represents a pipeline of analytic operations
 * performed in sequence.  The output from
 * a given step is fed into the next operation
 * as input.
 * @author dhall
 *
 */
public class AnalyticPipeline implements AnalyticOperation {
    
    // ==============================
    //      Attributes
    // ==============================
    
    /** Sequence of operations to execute.  */
    private List<AnalyticOperation> operations =
        new ArrayList<AnalyticOperation>();
    
    /** Name of pipeline. */
    private String name = null;
    
    
    // ===========================
    //     Getters/setters
    // ===========================

    /**
     * Get sequence of analytic operations to perform.
     * @return Sequence of analytic operations to perform
     */
    public final List<AnalyticOperation> getOperations() {
        return operations;
    }

    /**
     * Set sequence of analytic operations to perform.
     * @param operations Sequence of analytic operations to perform
     */
    public final void setOperations(final List<AnalyticOperation> operations) {
        this.operations = operations;
    }

    /**
     * Get name of pipeline.
     * @return Name of pipeline
     */
    public final String getName() {
        return name;
    }

    /**
     * Set name of pipeline.
     * @param name Name of pipeline
     */
    public final void setName(final String name) {
        this.name = name;
    }
    
    // =================================
    //   AnalyticOperation interface
    // =================================
    
    
    /**
     * Get user configurable properties.
     * @param qTypes Quantitation types
     * @return User configurable properties
     */
    public final List<UserConfigurableProperty> getUserConfigurableProperties(
    		final Collection<QuantitationType> qTypes) {
    	List<UserConfigurableProperty> props =
    		new ArrayList<UserConfigurableProperty>();
    	int count = 0;
    	for (AnalyticOperation op : this.operations) {
    		for (UserConfigurableProperty prop
    				: op.getUserConfigurableProperties(qTypes)) {
    			UserConfigurableProperty newProp = prop.createClone();
    			String newName = count + "_" + prop.getName();
    			String newDisplayName = "Step " + count + " "
    				+ op.getName() + ": " + prop.getDisplayName();
    			newProp.setName(newName);
    			newProp.setDisplayName(newDisplayName);
    		}
    		count++;
    	}
    	return props;
    }
    
    
    /**
     * Set some property of the operation.  The name of this
     * property should correspond to one of user configurable
     * property names.
     * @param name Name of property to set.
     * @param value Value of property.
     * @throws BadUserConfigurablePropertyException if value is invalid.
     */
    public final void setProperty(final String name, final String value)
    throws BadUserConfigurablePropertyException {
    	
    	// Parse operation number from name
    	int p = name.indexOf('_');
    	int opNum = Integer.parseInt(name.substring(0, p));
    	
    	// Parse name of propery with regard to operation
    	String opPropName = name.substring(p + 1);
    	
    	// Set property of operation
    	AnalyticOperation op = this.operations.get(opNum);
    	op.setProperty(opPropName, value);
    }
    
    
    /**
     * Determine the number of bioassays that would result
     * from a proper running of the given experiments through
     * this operation.
     * @param experiments Some experiments
     * @return The number of bioassays that would result
     * from a proper running of the given experiments through
     * this operation.
     */
    public int numResultingBioAssays(
    		final Collection<Experiment> experiments) {
    	int min = Experiment.countBioAssays(experiments);
    	for (AnalyticOperation op : this.operations) {
    		int candidateMin = op.numResultingBioAssays(experiments);
    		if (candidateMin < min) {
    			min = candidateMin;
    		}
    	}
    	return min;
    }
    
    
    // ================================
    //    Constructors.
    // ================================
    
    /**
     * Constructor.
     */
    public AnalyticPipeline() {
        
    }

    /**
     * Constructor.
     * @param name Name of pipeline
     */
    public AnalyticPipeline(final String name) {
        this.name = name;
    }

    
    /**
     * Constructor.
     * @param name Name of pipeline
     * @param operations Sequence of analytic operations
     * that are performed in pipeline
     */
    public AnalyticPipeline(
            final String name, final List<AnalyticOperation> operations) {
        this.operations = operations;
        this.name = name;
    }
    
    
    // ==============================
    //     Other business methods
    // ==============================

    /**
     * Add an analytic operation to end of pipeline.
     * @param analyticOperation An analytic operation
     */
    public final void add(final AnalyticOperation analyticOperation) {
        this.operations.add(analyticOperation);
    }
    
    
    /**
     * Add an analytic operation at given position.
     * @param analyticOperation An analytic operation
     * @param index Position in pipeline at which
     * to add operation
     */
    public final void add(final AnalyticOperation analyticOperation,
            final int index) {
        this.operations.add(index, analyticOperation);
    }
    
    
    /**
     * Remove operation at given position.
     * @param index Position of operation that is
     * to be removed
     */
    public final void removeAt(final int index) {
        this.operations.remove(index);
    }
    
    
    /**
     * Does pipeline produce a single bioassay per experiment?
     * @return T/F
     */
    public final boolean producesSingleBioAssayPerExperiment() {
    	boolean single = false;
    	for (AnalyticOperation op : this.operations) {
    		if (op instanceof SingleExperimentStatelessOperation) {
    			single = true;
    			break;
    		}
    	}
    	return single;
    }
}
