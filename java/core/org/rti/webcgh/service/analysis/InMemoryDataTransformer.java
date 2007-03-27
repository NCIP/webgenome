/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:09 $

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

package org.rti.webcgh.service.analysis;

import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.DataContainingBioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.service.util.ChromosomeArrayDataIterator;

/**
 * A data transformer where all data are in memory.
 * @author dhall
 *
 */
public final class InMemoryDataTransformer
	extends DataTransformer {
	
    /**
     * Create new bioassay of same type as given bioassay
     * and copy some of the properties.
     * @param bioAssay A bioassay
     * @return A new bioassay of same type as given bioassay
     * and copy some of the properties
     */
    protected BioAssay clone(final BioAssay bioAssay) {
        BioAssay newBa = null;
        if (bioAssay instanceof DataContainingBioAssay) {
            newBa = new DataContainingBioAssay(bioAssay.getName(),
                    bioAssay.getOrganism());
        } else {
        	throw new IllegalArgumentException(
        			"Bioassay must be of type DataContainingBioAssay");
        }
        newBa.setArray(bioAssay.getArray());
        return newBa;
    }
    
    
    /**
     * Add given chromosome array data to given bioassay.
     * This method handles the two cases where we are
     * keeping all data in memory or are serializing
     * data when not being used.
     * @param bioAssay A bioassay
     * @param chromosomeArrayData Chromosome array data
     */
    protected void addChromosomeArrayData(final BioAssay bioAssay,
            final ChromosomeArrayData chromosomeArrayData) {
        if (bioAssay instanceof DataContainingBioAssay) {
            ((DataContainingBioAssay) bioAssay).put(chromosomeArrayData);
        } else {
        	throw new IllegalArgumentException(
			"Bioassay must be of type DataContainingBioAssay");
        }
    }
    
    
    /**
     * Get chromosome array data from given bioassay and chromosome.
     * This method handles the two cases where we are
     * keeping all data in memory or are serializing
     * data when not being used.
     * @param bioAssay Bioassay
     * @param chromosome Chromosome
     * @return Chromosome array data
     */
    protected ChromosomeArrayData getChromosomeArrayData(
            final BioAssay bioAssay, final short chromosome) {
        ChromosomeArrayData cad = null;
        if (bioAssay instanceof DataContainingBioAssay) {
            cad = ((DataContainingBioAssay)
                    bioAssay).getChromosomeArrayData(chromosome);
        } else {
        	throw new IllegalArgumentException(
			"Bioassay must be of type DataContainingBioAssay");
        }
        return cad;
    }
    
    
    /**
     * Get a chromosome data iterator.
     * @param bioAssay Bioassay containing data
     * @return Chromosome data iterator
     */
    protected ChromosomeArrayDataIterator
    	getChromosomeArrayDataIterator(final BioAssay bioAssay) {
    	return new DefChromosomeArrayDataIterator(bioAssay);
    }
    
    /**
     * Finalize experiment before it sent to garbage collector.
     * This implementation does nothing.
     * @param experiment Experiment to finalize
     */
    protected void finalize(final Experiment experiment) {
    	
    }

    
    /**
     * Implementation of <code>ChromosomeArrayDataIterator</code>
     * for in-memory data.
     * @author dhall
     *
     */
    static class DefChromosomeArrayDataIterator
    	extends ChromosomeArrayDataIterator {
    	
    	/**
    	 * Constructor.
    	 * @param bioAssay A bioassay to iterate over
    	 */
    	public DefChromosomeArrayDataIterator(final BioAssay bioAssay) {
			super(bioAssay);
		}

		/**
         * Get chromosome array data from given bioassay.
         * @param bioAssay Bioassay containing data
         * @param chromosome Chromosome number
         * @return Chromosome data iterator
         */
    	protected ChromosomeArrayData getChromosomeArrayData(
	    		final BioAssay bioAssay, final short chromosome) {
    		ChromosomeArrayData cad = null;
    		if (bioAssay instanceof DataContainingBioAssay) {
                cad = ((DataContainingBioAssay)
                        bioAssay).getChromosomeArrayData(chromosome);
            } else {
            	throw new IllegalArgumentException(
            			"Bioassay must be of type DataContainingBioAssay");
            }
    		return cad;
		}
    }
}
