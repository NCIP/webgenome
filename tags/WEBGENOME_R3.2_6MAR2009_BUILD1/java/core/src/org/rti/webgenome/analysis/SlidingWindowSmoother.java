/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.QuantitationType;

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
extends SingleBioAssayStatelessOperation.BaseSingleBioAssayStatelessOperation {
    
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
     * {@inheritDoc}
     */
    public List<UserConfigurableProperty> getUserConfigurableProperties(
    		final Collection<QuantitationType> qTypes) {
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
