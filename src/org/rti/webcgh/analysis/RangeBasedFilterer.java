/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

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

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.QuantitationType;

/**
 * Filters data.  All array datum
 * with values between the filter
 * endpoints are removed.
 * @author dhall
 *
 */
public final class RangeBasedFilterer implements
    SingleBioAssayStatelessOperation {
    
    
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
     * Get user configurable properties.
     * @param qType Quantitation type
     * @return User configurable properties
     */
    public List<UserConfigurableProperty> getUserConfigurableProperties(
    		final QuantitationType qType) {
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
