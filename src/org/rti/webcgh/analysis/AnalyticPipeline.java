/*
$Revision$
$Date$

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

package org.rti.webcgh.analysis;

import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.domain.QuantitationType;

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
     * @param qType Quantitation type
     * @return User configurable properties
     */
    public final List<UserConfigurableProperty> getUserConfigurableProperties(
    		final QuantitationType qType) {
    	List<UserConfigurableProperty> props =
    		new ArrayList<UserConfigurableProperty>();
    	int count = 0;
    	for (AnalyticOperation op : this.operations) {
    		for (UserConfigurableProperty prop
    				: op.getUserConfigurableProperties(qType)) {
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
