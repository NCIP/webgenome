/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

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
 * linear model regression on input data.
 * @author Kungyen
 */
public class LinearRegressionAnalyticOperation
extends SingleBioAssayStatelessOperation.BaseSingleBioAssayStatelessOperation {

    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(LinearRegressionAnalyticOperation.class);

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
    //      LinearRegressionAnalyticOperation interface
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
        	REG_SERVICE.runLinearRegression(acghData);
        	output = this.regChrDataTransformer.transform(acghData, input);
        } catch (Exception e) {
            throw new AnalyticException(
            		"Error performing linear regression operation", e);
        }

        LOGGER.debug("End of linear regression operation");
        return output;
    }

    /**
     * Get name of operation.
     * @return Name of operation
     */
    public final String getName() {
        return "Linear regression";
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
