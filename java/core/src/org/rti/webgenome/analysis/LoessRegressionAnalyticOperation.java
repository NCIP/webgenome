/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.analysis;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.service.analysis.RegressionService;


/**
 * An analytic operation that performs
 * loess regression on input data.
 * @author Kungyen
 */
public class LoessRegressionAnalyticOperation
extends SingleBioAssayStatelessOperation.BaseSingleBioAssayStatelessOperation {

    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(LoessRegressionAnalyticOperation.class);

    //  ==============================
    //      Constants
    //  ==============================

    /** Regression service. */
    private static final RegressionService REG_SERVICE =
    	new RegressionService();

    //  ==============================
    //      Attributes
    //  ==============================
    
    /** Data transformer. */
    private AcghAnalyticTransformer regChrDataTransformer =
    	new AcghAnalyticTransformer();
    


    // ================================================
    //      LoessRegressionAnalyticOperation interface
    // ================================================

    /**
     * Perform operation.  The output data set will contain
     * new <code>ArrayDatum</code> objects that reference
     * the same <code>Reporter</code> objects as the input.
     * @param input Input data
     * @return Output data
     * @throws AnalyticException if an error occurs during this operation
     */
    public ChromosomeArrayData perform(final ChromosomeArrayData input)
        throws AnalyticException {
    	ChromosomeArrayData output =
            new ChromosomeArrayData(input.getChromosome());

        try {
        	AcghData acghData = this.regChrDataTransformer.transform(input);
        	REG_SERVICE.runLoess(acghData);
        	output = this.regChrDataTransformer.transform(acghData, input);
        } catch (Exception e) {
            throw new AnalyticException(
            		"Error performing loess regression operation", e);
        }

        LOGGER.debug("End of loess regression operation");
        return output;
    }

    /**
     * Get name of operation.
     * @return Name of operation
     */
    public String getName() {
        return "Loess regression";
    }


    /**
     * {@inheritDoc}
     */
    public List<UserConfigurableProperty> getUserConfigurableProperties(
    		final Collection<QuantitationType> qTypes) {
    	return new ArrayList<UserConfigurableProperty>();
    }
    
    
    /**
     * Set some property of the operation.  The name of this
     * property should correspond to one of user configurable
     * property names.
     * @param name Name of property to set.
     * @param value Value of property.
     */
    public void setProperty(final String name, final String value) {
    	
    }
}
