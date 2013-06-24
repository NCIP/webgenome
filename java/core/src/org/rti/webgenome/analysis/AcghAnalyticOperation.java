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
import org.rti.webgenome.service.analysis.AcghService;


/**
 * An analytic operation that performs
 * aCGH smoothing on input data.
 * @author Kungyen
 */
public class AcghAnalyticOperation
extends SingleBioAssayStatelessOperation.BaseSingleBioAssayStatelessOperation {

    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(AcghAnalyticOperation.class);

    //  ==============================
    //      Constants
    //  ==============================

    /** aCGH service. */
    private static final AcghService ACGH_SERVICE =
    	new AcghService();

    //  ==============================
    //      Attributes
    //  ==============================
    
    /** Data transformer. */
    private AcghAnalyticTransformer acghChrDataTransformer =
    	new AcghAnalyticTransformer();


    // ================================================
    //      AcghAnalyticOperation interface
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
        // accept request from AnalyticOperationManager
    	// invoked once for every single chromosome in one bioassay
    	ChromosomeArrayData output =
            new ChromosomeArrayData(input.getChromosome());

        try {
        	AcghData acghData = this.acghChrDataTransformer.transform(input);
        	ACGH_SERVICE.run(acghData);
        	output = this.acghChrDataTransformer.transform(acghData, input);
        } catch (Exception e) {
            throw new AnalyticException(
            		"Error performing aCGH operation", e);
        }

        LOGGER.debug("End of acgh operation");
        return output;
    }

    /**
     * Get name of operation.
     * @return Name of operation
     */
    public String getName() {
        return "Bioconductor aCGH smoother";
    }


    /**
     * {@inheritDoc}
     */
    public final List<UserConfigurableProperty> getUserConfigurableProperties(
    		final Collection<QuantitationType> qTypes) {
    	return new ArrayList<UserConfigurableProperty>();
    }
    
    
    /**
     * Set some property of the operation.  The name of this
     * property should correspond to one of user configurable
     * property names.
     * @param name Name of property to set.
     * @param value Value of property.
     * @throws BadUserConfigurablePropertyException if value is invalid.
     */
    public void setProperty(final String name, final String value)
    throws BadUserConfigurablePropertyException {
    	
    }
}
