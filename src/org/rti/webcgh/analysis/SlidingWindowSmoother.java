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

import org.apache.log4j.Logger;
import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.QuantitationType;

/**
 * An analytic operation that performs
 * "sliding window" smoothing on input data.
 * In this operation, a conceptual window
 * of N adjacent reporters is moved across
 * the chromosome.  The smoothed value for
 * the reporters in the middle of the window
 * is the mean of the window.
 * @author dhall
 *
 */
public final class SlidingWindowSmoother
implements SingleBioAssayStatelessOperation {
    
    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(SlidingWindowSmoother.class);
    
    // =========================
    //     Constants
    // =========================
    
    /** Default number of adjacent reporters in window. */
    private static final int DEF_WINDOW_SIZE = 5;
    
    // ===================================
    //       Attributes
    // ===================================
    
    /** Number of adjacent reporters in window. */
    private int windowSize = DEF_WINDOW_SIZE;
    
    // ===============================
    //     Getters/setters
    // ===============================
    
    /**
     * Get number of adjacent reporters in window.
     * @return Number of adjacent reporters in window
     */
    public int getWindowSize() {
        return windowSize;
    }

    /**
     * Set number of adjacent reporters in window.
     * @param windowSize Number of adjacent reporters in window
     */
    public void setWindowSize(final int windowSize) {
        this.windowSize = windowSize;
    }
    
    // ================================================
    //    ScalarToScalarAnalyticOperation interface
    // ================================================
    
    /**
     * Perform operation.  The output data set will contain
     * new <code>ArrayDatum</code> objects that reference
     * the same <code>Reporter</code> objects as the input.
     * @param input Input data
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    public ChromosomeArrayData perform(final ChromosomeArrayData input)
        throws AnalyticException {
        LOGGER.debug("Running sliding window smoother with window size = "
                + this.windowSize);
        
        ChromosomeArrayData out =
            new ChromosomeArrayData(input.getChromosome());
        
        try {
            List<ArrayDatum> arrayData = input.getArrayData();
            
            // Calculate number of reporters to left and right
            // boundaries of window from the "center"
            int numBack = (int) Math.floor((double) this.windowSize / 2.0);
            int numForward = (int) Math.floor((double) this.windowSize / 2.0);
            if (this.windowSize % 2 == 0) {
                numForward++;
            }
            LOGGER.debug("Number of reporters to left of center = " + numBack);
            LOGGER.debug("Number of reporters to right of center = "
                    + numForward);
            
            // Smooth data
            int indexLastDatum = arrayData.size() - 1;
            for (int i = 0; i < arrayData.size(); i++) {
                ArrayDatum center = arrayData.get(i);
                int p = i - numBack;
                if (p < 0) {
                    p = 0;
                }
                int q = i + numForward;
                if (q > indexLastDatum) {
                    q = indexLastDatum;
                }
                float totalValue = (float) 0.0;
                float totalError = (float) 0.0;
                for (int j = p; j <= q; j++) {
                    ArrayDatum ad = arrayData.get(j);
                    if (!Float.isNaN(ad.getValue())) {
                        totalValue += ad.getValue();
                    }
                    if (!Float.isNaN(ad.getError())) {
                        totalError += ad.getValue();
                    }
                }
                float actualWindowSize = (float) (q - p) + (float) 1.0;
                float smoothedValue = totalValue / actualWindowSize;
                float smoothedError = totalError / actualWindowSize;
                ArrayDatum smoothedDatum = new ArrayDatum(smoothedValue,
                        smoothedError, center.getReporter());
                out.add(smoothedDatum);
            }
        } catch (Exception e) {
            throw new AnalyticException(
                    "Error performing sliding window smoothing", e);
        }
        
        LOGGER.debug("Completed sliding window smoothing");
        return out;
    }


    /**
     * Get name of operation.
     * @return Name of operation
     */
    public String getName() {
        return "Sliding window smoother";
    }
    
    
    /**
     * Get user configurable properties.
     * @param qType Quantitation type
     * @return User configurable properties
     */
    public List<UserConfigurableProperty> getUserConfigurableProperties(
    		final QuantitationType qType) {
       List<UserConfigurableProperty> props =
           new ArrayList<UserConfigurableProperty>();
       props.add(new SimpleUserConfigurableProperty("windowSize",
    		   "Window size", String.valueOf(this.windowSize)));
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
    public void setProperty(final String name, final String value)
    throws BadUserConfigurablePropertyException {
    	try {
    		this.windowSize = Integer.parseInt(value);
    	} catch (NumberFormatException e) {
    		throw new BadUserConfigurablePropertyException(
    				"Window size not valid number");
    	}
    }
}
