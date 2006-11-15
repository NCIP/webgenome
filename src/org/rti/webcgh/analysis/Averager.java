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
import java.util.Collection;
import java.util.List;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.domain.Reporter;

/**
 * Calculates mean reporter values for
 * a group of chromosome array data
 * objects.  Specificly, for each
 * unique reporter across all chromosome
 * array data objects, it averages the
 * corresponding array datum values.
 * Method also calculates standard error.
 * @author dhall
 *
 */
public final class Averager implements ListToScalarAnalyticOperation {
    
    /**
     * Perform operation.
     * @param input Input data
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    public ChromosomeArrayData perform(final List<ChromosomeArrayData> input)
        throws AnalyticException {
        
        // Make sure arg okay
        if (input == null || input.size() < 1) {
            throw new AnalyticException("Input cannot be empty");
        }
        short chromosome = input.get(0).getChromosome();
        for (ChromosomeArrayData cad : input) {
            if (!(chromosome == cad.getChromosome())) {
                throw new AnalyticException(
                        "Input cannot come from different chromosome");
            }
        }
        
        ChromosomeArrayData output = new ChromosomeArrayData(chromosome);
        ArrayDatumMatcher matcher = new ArrayDatumMatcher(input);
        Collection<ArrayDatum> matches = matcher.nextMatches();
        while (matches != null && matches.size() > 0) {
            
            // Calculate mean
            float sum = (float) 0.0;
            for (ArrayDatum d : matches) {
                sum += d.getValue();
            }
            float mean = sum / (float) matches.size();
            
            // Calculate standard error
            float error = (float) 0.0;
            for (ArrayDatum d : matches) {
                float diff = d.getValue() - mean;
                error += diff * diff;
            }
            float stdError = (float) Math.sqrt(error / matches.size())
                * (float) 1.92;
            
            // Add new array datum object to output
            Reporter r = matches.iterator().next().getReporter();
            output.add(new ArrayDatum(mean, stdError, r));
            
            matches = matcher.nextMatches();
        }
        return output;
    }
    
    
    /**
     * Get name of operation.
     * @return Name of operation
     */
    public String getName() {
        return "Average";
    }
    
    
    /**
     * Get user configurable properties.
     * @param qType Quantitation type
     * @return User configurable properties
     */
    public List<UserConfigurableProperty> getUserConfigurableProperties(
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
    
    
    /**
     * Class responsble for finding matching
     * array datum objects in a group of
     * chromosome array data objects.  Matches
     * are based on having the same reporter.
     * @author dhall
     *
     */
    static final class ArrayDatumMatcher {
        
        /** Chromosome array data containing array data to match. */
        private final List<ChromosomeArrayData> chromosomeArrayData;
        
        /**
         * Indices that keep track of which <code>ArrayDatum</code>
         * match across chromosome array data.
         */
        private final List<Integer> indices;
        
        /**
         * Constructor.
         * @param chromosomeArrayData Chromosome array data
         */
        public ArrayDatumMatcher(
                final List<ChromosomeArrayData> chromosomeArrayData) {
            this.chromosomeArrayData = chromosomeArrayData;
            this.indices = new ArrayList<Integer>();
            for (int i = 0; i < chromosomeArrayData.size(); i++) {
                indices.add(0);
            }
        }
        
        /**
         * Returns next set of matching array datum.
         * Scans across chromosome left to right.
         * @return Next set of matching array datum, or
         * null if there are not more array datum.
         * (i.e., We are at the right end of the
         * chromosome beyond all reporters.)
         */
        public Collection<ArrayDatum> nextMatches() {
            Collection<ArrayDatum> next = null;
            
            // Find leftmost reporter among next batch
            Reporter leftMost = null;
            for (int i = 0; i < this.indices.size(); i++) {
                Reporter r = this.getReporter(i);
                if (r != null) {
                    if (leftMost == null) {
                        leftMost = r;
                    } else {
                        if (r.compareTo(leftMost) < 0) {
                            leftMost = r;
                        }
                    }
                }
            }
            
            // Gather up all array datum whose reporters
            // are equal to leftmost.  Also advance indices.
            if (leftMost != null) {
                next = new ArrayList<ArrayDatum>();
                for (int i = 0; i < this.indices.size(); i++) {
                    ArrayDatum d = this.getArrayDatum(i);
                    if (d != null) {
                        if (d.getReporter().compareTo(leftMost) == 0) {
                            next.add(d);
                            this.advanceIndex(i);
                        }
                    }
                }
            }
            return next;
        }
        
        /**
         * Get reporter for datum currently under examination
         * from the chromosome array data object referenced
         * by given index.
         * @param chromosomeArrayDataIndex Index of chromosome
         * array data object.
         * @return Reporter for datum currently under examination
         * from the chromosome array data object referenced
         * by given index
         */
        private Reporter getReporter(final int chromosomeArrayDataIndex) {
            Reporter r = null;
            int idx = this.indices.get(chromosomeArrayDataIndex);
            List<ArrayDatum> data =
                this.chromosomeArrayData.get(chromosomeArrayDataIndex).
                    getArrayData();
            if (idx < data.size()) {
                r = data.get(idx).getReporter();
            }
            return r;
        }
        
        
        /**
         * Get datum currently under examination
         * from the chromosome array data object referenced
         * by given index.
         * @param chromosomeArrayDataIndex Index of chromosome
         * array data object.
         * @return Datum currently under examination
         * from the chromosome array data object referenced
         * by given index
         */
        private ArrayDatum getArrayDatum(final int chromosomeArrayDataIndex) {
            ArrayDatum d = null;
            int idx = this.indices.get(chromosomeArrayDataIndex);
            List<ArrayDatum> data =
                this.chromosomeArrayData.get(chromosomeArrayDataIndex).
                    getArrayData();
            if (idx < data.size()) {
                d = data.get(idx);
            }
            return d;
        }
        
        
        /**
         * Advance index at given position.
         * @param i Position of index
         */
        private void advanceIndex(final int i) {
            int value = this.indices.get(i) + 1;
            this.indices.set(i, value);
        }
    }

}
