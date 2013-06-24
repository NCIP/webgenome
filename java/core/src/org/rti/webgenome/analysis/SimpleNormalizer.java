/*

/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.QuantitationType;

/**
 * Performs "simple" normalization, subtracting
 * either mean or median value of bioassay
 * from all values to bring to mean or median,
 * respectively, to 0.
 * @author dhall
 *
 */
public abstract class SimpleNormalizer extends
StatefulOperation.DefStatefulOperation {
    
    
    // ===============================
    //         Constants
    // ===============================
    
    /** Mean operation. */
    public static final int MEAN = 1;
    
    /** Median operation. */
    public static final int MEDIAN = 2;
    
    
    // ==============================
    //  Attributes representing state
    //  of operation when using mean
    // ==============================
    
    /**
     * Sum of all data values seen so far
     * since resetState() was last called
     * or since the object was instantiated,
     * whichever was more recent.
     */
    private float sum = (float) 0.0;
    
    /**
     * Number of data values seen so far
     * since resetState() was last called
     * or since the object was instantiated,
     * whichever was more recent.
     */
    private int numDatum = 0;
    
    
    // =================================
    //  Attributes representing state
    //  of operation when using median
    // =================================
    
    /**
     * List of all values seen so far
     * since resetState() was last called
     * or since the object was instantiated,
     * whichever was more recent.
     */
    private List<Float> values = new ArrayList<Float>();
    
    /** Keeps track of whether the list of values is sorted. */
    private boolean sorted = false;
    
    
    // ====================================
    //       Common attributes
    // ====================================
    
    
    /** Basic operation used for normalization. */
    private int operation = MEAN;
    
        
    // ==============================
    //     Getters/setters
    // ==============================
    
    /**
     * Get basic statistical operation used for
     * normalization.
     * @return Basic statistical operation used for
     * normalization
     */
    public final int getOperation() {
        return operation;
    }


    /**
     * Set basic statistical operation used for
     * normalization.
     * @param operation Basic statistical operation used for
     * normalization
     */
    public final void setOperation(final int operation) {
        this.operation = operation;
    }
    
    
    // =====================================
    //  StatefulBioAssayAnalyticOperation
    // =====================================


    /**
     * Adjust the state of this operation.
     * @param chromosomeArrayData Chromosome array
     * data that will modify the internal state
     * @throws AnalyticException if there is a
     * computational error
     */
    public final void adjustState(
            final ChromosomeArrayData chromosomeArrayData)
        throws AnalyticException {
        for (ArrayDatum ad : chromosomeArrayData.getArrayData()) {
            if (this.operation == MEAN) {
                this.sum += ad.getValue();
                this.numDatum++;
            } else if (this.operation == MEDIAN) {
                this.values.add(ad.getValue());
            }
        }
        this.sorted = false;
    }
    
    
    /**
     * Reset state of operation.
     *
     */
    public final void resetState() {
        this.sum = (float) 0.0;
        this.numDatum = 0;
        this.values = new ArrayList<Float>();
    }
    
    
    /**
     * Perform operation.
     * @param input Input data
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    public final ChromosomeArrayData perform(
            final ChromosomeArrayData input)
        throws AnalyticException {
        
        // Make sure mean or median can be calculated
        if (this.operation == MEAN) {
            if (this.numDatum == 0) {
                throw new AnalyticException(
                        "Cannot compute mean over 0 values");
            }
        } else if (this.operation == MEDIAN) {
            if (this.values.size() == 0) {
                throw new RuntimeException(
                        "Cannot compute median over 0 values");
            }
        }
        
        // Calculate mean or median, as appropriate
        float stat = (float) 0.0;
        if (this.operation == MEAN) {
            stat = this.sum / (float) this.numDatum;
        } else if (this.operation == MEDIAN) {
            if (!this.sorted) {
                Collections.sort(this.values);
                this.sorted = true;
            }
            stat = this.values.get(this.values.size() / 2);
        }
        
        // Iterate over datum and subtract mean or median
        ChromosomeArrayData output =
            new ChromosomeArrayData(input.getChromosome());
        for (ArrayDatum ad : input.getArrayData()) {
            ArrayDatum newAd = new ArrayDatum(ad);
            newAd.setValue(ad.getValue() - stat);
            output.add(newAd);
        }
        
        return output;
    }
    
    
    /**
     * Get name of operation.
     * @return Name of operation
     */
    public abstract String getName();
    
    /**
     * {@inheritDoc}
     */
    public final List<UserConfigurableProperty> getUserConfigurableProperties(
    		final Collection<QuantitationType> qTypes) {
       List<UserConfigurableProperty> props =
           new ArrayList<UserConfigurableProperty>();
       UserConfigurablePropertyWithOptions prop = new
           UserConfigurablePropertyWithOptions("operation",
        		   "Mean/Median", String.valueOf(this.operation));
       prop.addOption(String.valueOf(MEAN), "Mean");
       prop.addOption(String.valueOf(MEDIAN), "Median");
       props.add(prop);
       return props;
    }

    /**
     * Set some property of the operation.  The name of this
     * property should correspond to one of user configurable
     * property names.
     * @param name Name of property to set.
     * @param value Value of property.
     */
    public final void setProperty(final String name, final String value) {
    	int intValue = Integer.parseInt(value);
    	this.operation = intValue;
    }
}
