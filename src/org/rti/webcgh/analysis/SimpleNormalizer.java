/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;

/**
 * Performs "simple" normalization, subtracting
 * either mean or median value of bioassay
 * from all values to bring to mean or median,
 * respectively, to 0.
 * @author dhall
 *
 */
public abstract class SimpleNormalizer implements UserConfigurable {
    
    
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
    
    
    // ======================================
    //      UserConfigurable
    // ======================================
    
    /**
     * Get user configurable properties.
     * @return User configurable properties
     */
    public final List<UserConfigurableProperty>
        getUserConfigurableProperties() {
       List<UserConfigurableProperty> props =
           new ArrayList<UserConfigurableProperty>();
       UserConfigurablePropertyWithOptions prop = new
           UserConfigurablePropertyWithOptions("operation", "Mean/Median");
       prop.addOption(String.valueOf(MEAN), "Mean");
       prop.addOption(String.valueOf(MEDIAN), "Median");
       props.add(prop);
       return props;
    }

}
