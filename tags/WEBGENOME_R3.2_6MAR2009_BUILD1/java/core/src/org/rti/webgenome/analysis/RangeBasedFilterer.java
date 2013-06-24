/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.QuantitationType;

/**
 * Filters data.  All array datum
 * with values between the filter
 * endpoints are removed.
 * @author dhall
 *
 */
public final class RangeBasedFilterer extends
    SingleBioAssayStatelessOperation.BaseSingleBioAssayStatelessOperation {
    
    
    // =====================
    //      Attributes
    // =====================
    
    /** Maximum filtered value. */
    private float min = Float.NEGATIVE_INFINITY;
    
    /** Minimum filtered value. */
    private float max = Float.POSITIVE_INFINITY;
    
    // =========================
    //     Getters/setters
    // =========================
    
    /**
     * Get maximum filtered value.
     * @return Right endpoint of filter range
     */
    public float getMax() {
        return max;
    }



    /**
     * Set maximum filtered value.
     * @param max Right endpoint of filter range
     */
    public void setMax(final float max) {
        this.max = max;
    }



    /**
     * Get minimum filtered value.
     * @return Left endpoint of filter range
     */
    public float getMin() {
        return min;
    }



    /**
     * Set minimum filtered value.
     * @param min Left endpoint of filter range
     */
    public void setMin(final float min) {
        this.min = min;
    }

    
    // =============================
    //     Constructors
    // =============================
    
    /**
     * Constructor.
     */
    public RangeBasedFilterer() {
        
    }


    /**
     * Constructor.
     * @param min Left endpoint of filter range.
     * @param max Right endpoint of filter range.
     */
    public RangeBasedFilterer(final float min, final float max) {
        this.min = min;
        this.max = max;
    }


    // ====================================
    //   ScalarToScalarAnalyticOperation
    // ====================================

    /**
     * Perform operation.
     * @param input Input data
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    public ChromosomeArrayData perform(final ChromosomeArrayData input)
        throws AnalyticException {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        ChromosomeArrayData output =
            new ChromosomeArrayData(input.getChromosome());
        for (ArrayDatum d : input.getArrayData()) {
            float value = d.getValue();
            if (value < this.min || value > this.max) {
                output.add(new ArrayDatum(d));
            }
        }
        return output;
    }
    
    
    /**
     * Get name of operation.
     * @return Name of operation
     */
    public String getName() {
        return "Range-based data filter";
    }
    
    
    /**
     * {@inheritDoc}
     */
    public List<UserConfigurableProperty> getUserConfigurableProperties(
    		final Collection<QuantitationType> qTypes) {
       List<UserConfigurableProperty> props =
           new ArrayList<UserConfigurableProperty>();
       String currentMin = "";
       if (!Float.isNaN(this.min) && !Float.isInfinite(this.min)) {
    	   currentMin = String.valueOf(this.min);
       }
       String currentMax = "";
       if (!Float.isNaN(this.max) && !Float.isInfinite(this.max)) {
    	   currentMax = String.valueOf(this.max);
       }
       props.add(new SimpleUserConfigurableProperty("min",
    		   "Lower bound", currentMin));
       props.add(new SimpleUserConfigurableProperty("max",
    		   "Upper bound", currentMax));
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
    	float floatValue = Float.NaN;
    	try {
    		floatValue = Float.parseFloat(value);
    	} catch (NumberFormatException e) {
    		throw new BadUserConfigurablePropertyException("Property '"
    				+ name + "' is not a valid number");
    	}
    	if ("min".equals(name)) {
    		this.min = floatValue;
    	} else if ("max".equals(name)) {
    		this.max = floatValue;
    	}
    }
}
