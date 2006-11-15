/*
$Revision: 1.8 $
$Date: 2006-11-15 21:54:38 $

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

import org.apache.log4j.Logger;
import org.rti.webcgh.deprecated.analytic.AcghData;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.service.analysis.AcghService;


/**
 * An analytic operation that performs
 * aCGH smoothing on input data.
 * @author Kungyen
 */
public class AcghAnalyticOperation implements ScalarToScalarAnalyticOperation {

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
        	this.ACGH_SERVICE.run(acghData);
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
     * Get user configurable properties.
     * @param qType Quantitation type
     * @return User configurable properties
     */
    public final List<UserConfigurableProperty> getUserConfigurableProperties(
    		final QuantitationType qType) {
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
