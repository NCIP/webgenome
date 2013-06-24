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
import org.rti.webgenome.domain.Reporter;

/**
 * Calculates mean reporter values for
 * a group of chromosome array data
 * objects.  Specificaly, for each
 * unique reporter across all chromosome
 * array data objects, it averages the
 * corresponding array datum values.
 * Method also calculates standard error.
 * @author dhall
 *
 */
public final class Averager
extends SingleExperimentStatelessOperation.
DefSingleExperimentStatelessOperation {
    
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
     * {@inheritDoc}
     */
    public List<UserConfigurableProperty> getUserConfigurableProperties(
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
