/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/BinnedData.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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
package org.rti.webcgh.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Binned chromosome data
 */
public class BinnedData {
    
    
    // ======================================
    //       Attributes
    // ======================================
    
    private Long id = null;
    private QuantitationType quantitationType = null;
    protected List chromosomeBins = new ArrayList();
    private Map numBinsPerChromosome = new HashMap();
    

    /**
     * @return Returns the chromosomeBins.
     */
    public List getChromosomeBins() {
        return chromosomeBins;
    }
    
    
    /**
     * @param chromosomeBins The chromosomeBins to set.
     */
    public void setChromosomeBins(List chromosomeBins) {
    	this.chromosomeBins = chromosomeBins;
    	this.numBinsPerChromosome = new HashMap();
    	for (Iterator it = this.chromosomeBins.iterator(); it.hasNext();) {
    		ChromosomeBin bin = (ChromosomeBin)it.next();
    		this.putInIndex(bin);
    	}    			
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
     * @return Returns the quantitationType.
     */
    public QuantitationType getQuantitationType() {
        return quantitationType;
    }
    
    
    /**
     * @param quantitationType The quantitationType to set.
     */
    public void setQuantitationType(QuantitationType quantitationType) {
        this.quantitationType = quantitationType;
    }
    
    
    // ============================================
    //         Constructors
    // ============================================
    
    
    /**
     * Constructor
     */
    public BinnedData() {}
    
    
    
    /**
     * Constructor
     * @param quantitationType Quantitation type
     */
    public BinnedData(QuantitationType quantitationType) {
        this.quantitationType = quantitationType;
    }
    
    
    // ========================================
    //         Public methods
    // ========================================
    
    /**
     * Add a bin
     * @param bin A chromosome bin
     */
    public void add(ChromosomeBin bin) {
        this.chromosomeBins.add(bin);
        this.putInIndex(bin);
    }
    
    
    /**
     * Number of bins
     * @return Number of bins
     */
    public int numBins() {
        return this.chromosomeBins.size();
    }
    
    
    /**
     * Get chromosome bin iterator
     * @return Chromosome bin iterator
     */
    public ChromosomeBinIterator chromosomeBinIterator() {
        return new DefChromosomeBinIterator(this.chromosomeBins);
    }
    
    
    /**
     * Get num bins associated with given chromosome
     * @param chromosomeNum
     * @return Num bins
     */
    public int numBins(int chromosomeNum) {
    	int num = 0;
    	Integer numInt = (Integer)this.numBinsPerChromosome.get(new Integer(chromosomeNum));
    	if (numInt != null)
    		num = numInt.intValue();
    	return num;
    }
    
    
    // ===============================
    //    Private methods
    // ===============================
    
    private void putInIndex(ChromosomeBin bin) {
        int chromosomeNum = bin.getChromosomeNum();
        Integer chromNum = new Integer(chromosomeNum);
        Integer count = (Integer)this.numBinsPerChromosome.get(chromNum);
        if (count == null)
        	count = new Integer(0);
        int newCount = count.intValue() + 1;
        this.numBinsPerChromosome.put(chromNum, new Integer(newCount));
    }
    
    
    // =========================================
    //         Inner classes
    // =========================================
    
    /**
     * Implementation of ChromosomeBinIterator interface
     *
     */
    static class DefChromosomeBinIterator implements ChromosomeBinIterator {
        
        private Iterator iterator = null;
        
        
        /**
         * Constructor
         * @param bins Chromosome bins
         */
        public DefChromosomeBinIterator(List bins) {
            this.iterator = bins.iterator();
        }
        
        
        /**
         * Get next chromosome bin
         * @return A chromosome bin
         */
        public ChromosomeBin next() {
            return (ChromosomeBin)this.iterator.next();
        }
        
        
        /**
         * Does iterator have any more bins?
         * @return T/F
         */
        public boolean hasNext() {
            return this.iterator.hasNext();
        }
    }
}
