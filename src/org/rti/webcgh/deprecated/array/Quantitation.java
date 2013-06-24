/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/Quantitation.java,v $
$Revision: 1.2 $
$Date: 2006-10-26 03:50:16 $

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
package org.rti.webcgh.deprecated.array;

import java.io.Serializable;

import org.rti.webcgh.deprecated.Cacheable;
import org.rti.webcgh.graphics.DataPoint;
import org.rti.webcgh.util.MathUtils;

/**
 * Quantitation of some property
 */
public class Quantitation implements Cacheable, Serializable {
    
    
    // =================================
    //     Attributes with accessors
    // =================================
    
    private Long id = null;
    private float value = Float.NaN;
    private float error = Float.NaN;
    private QuantitationType quantitationType = null;
    private LohValue lohValue = null;
    
    
    /**
     * @param error The error to set.
     */
    public void setError(float error) {
        this.error = error;
    }
    
    
    /**
     * @param quantitationType The quantitationType to set.
     */
    public void setQuantitationType(QuantitationType quantitationType) {
        this.quantitationType = quantitationType;
    }
    
    
    /**
     * @param value The value to set.
     */
    public void setValue(float value) {
        this.value = value;
    }
    
    
    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }
    
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @return Returns the error.
     */
    public float getError() {
        return error;
    }
    
    
    /**
     * @return Returns the quantitationType.
     */
    public QuantitationType getQuantitationType() {
        return quantitationType;
    }
    
    
    /**
     * @return Returns the value.
     */
    public float getValue() {
        return value;
    }
    
    
    public LohValue getLohValue() {
		return lohValue;
	}


	public void setLohValue(LohValue lohValue) {
		this.lohValue = lohValue;
	}
    
    // ==============================
    //    Constructors
    // ==============================


	/**
     * Constructor
     */
    public Quantitation(){}
    
    
    /**
     * Constructor
     * @param value Value
     * @param quantitationType Quantitation type
     */
    public Quantitation(float value, QuantitationType quantitationType) {
        this.value = value;
        this.quantitationType = quantitationType;
    }
    
    
    /**
     * Constructor
     * @param value Value
     * @param error Error
     * @param quantitationType Quantitation type
     */
    public Quantitation(float value, float error, QuantitationType quantitationType) {
        this(value, quantitationType);
        this.error = error;
    }
    
    
    /**
     * Constructor
     * @param quantitation A quantitation
     */
    public Quantitation(Quantitation quantitation) {
        this(quantitation.value, quantitation.error, quantitation.quantitationType);
    }
    
    // ===================================
    //      Public methods
    // ===================================
    
    /**
     * Does quantitation have an error term?
     * @return T/F
     */
    public boolean haveError() {
        return ! Float.isNaN(error);
    }
    
    
    /**
     * Transfer properties to given data point
     * @param dataPoint A data point
     */
    public void transferPropertiesTo(DataPoint dataPoint) {
        dataPoint.setValue2(this.value);
        dataPoint.setError(this.error);
    }
    
    
    /**
     * Is this greater than given quantitation?
     * @param q A quantitation
     * @return T/F
     */
    public boolean greaterThan(Quantitation q) {
        if (this.quantitationType != q.quantitationType)
            throw new IllegalArgumentException("Cannot compare two quantitations of different types");
        return this.value > q.value;
    }
    
    
    /**
     * Is this less than given quantitation?
     * @param q A quantitation
     * @return T/F
     */
    public boolean lessThan(Quantitation q) {
        if (this.quantitationType != q.quantitationType)
            throw new IllegalArgumentException("Cannot compare two quantitations of different types");
        return this.value < q.value;
    }
    
    
    /**
     * Maximum value represented by this quantitation
     * @return Maximum value represented by this quantitation
     */
    public float maxValue() {
    	float rValue = this.value;
    	if (! Float.isNaN(this.error))
    		rValue += error;
    	return rValue;
    }
    
    
    /**
     * Minimum value represented by this quantitation
     * @return Minimum value represented by this quantitation
     */
    public float minValue() {
    	float rValue = this.value;
    	if (! Float.isNaN(this.error))
    		rValue -= error;
    	return rValue;
    }
    
    
    /**
     * Is this in range bounded by given values?
     * @param lower Lower boundary
     * @param upper Upper boundary
     * @return T/F
     */
    public boolean inRange(double lower, double upper) {
        return (double)this.value >= lower && (double)this.value <= upper;
    }
    
    
    /**
     * Addition operation
     * @param quant A quantitation
     */
    public void add(final Quantitation quant) {
    	if (this.quantitationType != quant.quantitationType)
    		throw new IllegalArgumentException("Cannot perform operation on different quantitation types");
    	this.value = MathUtils.add(this.value, quant.value);
    	this.error = MathUtils.add(this.error, quant.error);
    }
    
    
    /**
     * Add a scalar
     * @param x Value
     */
    public void add(float x) {
    	this.value = MathUtils.add(this.value, x);
    }
    
    
    /**
     * Division operation
     * @param value A value
     */
    public void divideBy(float value) {
    	this.value = MathUtils.divide(this.value, value);
    	this.error = MathUtils.divide(this.error, error);
    }
    
    
    // ================================
    //     Cacheable interface
    // ================================
    
	/**
	 * Get a key for the cache
	 * @return A key
	 */
	public Object getCacheKey() {
		return Quantitation.cacheKey(this.quantitationType, this.value, this.error);
	}
    
    
    // =========================================
    //     Static methods
    // =========================================
    
    /**
     * Compute mean
     * @param quants Quantitations
     * @return A quantitation
     */
    public static Quantitation mean(Quantitation[] quants) {
    	if (quants.length < 1)
    		return null;
    	if(quants.length == 1)
    		return quants[0];
    	
    	Quantitation meanVal = new Quantitation();
    	meanVal.quantitationType = quants[0].quantitationType;
    	
    	// Set mean
    	float sum = (float)0.0;
    	float errorSum = (float)0.0;
    	boolean haveError = false;
    	for (int i = 0; i < quants.length; i++) {
    		if (i < quants.length - 1)
    			if (quants[i].quantitationType != quants[i + 1].quantitationType)
    				throw new IllegalArgumentException("All quantitations must be of same type");
    		sum += quants[i].value;
    		if (! Float.isNaN(quants[i].error)) {
    		    haveError = true;
    		    errorSum += quants[i].error;
    		}
    	}
    	meanVal.value = sum / quants.length;
    	
    	// Set error
    	if (haveError)
    	    meanVal.error = errorSum / (float)quants.length;
    	else {
	    	float variance = (float)0.0;
	    	for (int i = 0; i < quants.length; i++) {
	    		float diff = meanVal.value - quants[i].value;
	    		variance += diff * diff;
	    	}
	    	meanVal.error = (float)(Math.sqrt(variance / quants.length) * 1.92);
    	}
    	
    	return meanVal;
    }

    /**
     * Generate cache key
     * @param quantitationType Quantitation type
     * @param value Value
     * @param error Error
     * @return Cache key
     */
    public static Object cacheKey(QuantitationType quantitationType,
            float value, float error) {
        return "%qt%" + quantitationType.getCacheKey() + value + error;
    }
}
