/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/ChromosomeBin.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:34:38 $

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

/**
 * Binned values
 */
public class ChromosomeBin {
    
    
    // =====================================
    //     Attributes
    // =====================================
    
    private Long id = null;
    private int bin = 0;
    private float value = Float.NaN;
    private int chromosomeNum = -1;
    

    /**
     * @return Returns the chromosomeNum.
     */
    public int getChromosomeNum() {
        return chromosomeNum;
    }
    
    
    /**
     * @param chromosomeNum The chromosomeNum to set.
     */
    public void setChromosomeNum(int chromosomeNum) {
        this.chromosomeNum = chromosomeNum;
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
     * @return Returns the bin.
     */
    public int getBin() {
        return bin;
    }
    
    
    /**
     * @param bin The bin to set.
     */
    public void setBin(int bin) {
        this.bin = bin;
    }
    
    
    /**
     * @return Returns the value.
     */
    public float getValue() {
        return value;
    }
    
    
    /**
     * @param value The value to set.
     */
    public void setValue(float value) {
        this.value = value;
    }
    
    
    // ========================================
    //        Constructors
    // ========================================
    
    
    /**
     * Constructor
     */
    public ChromosomeBin() {}
    
    
    /**
     * @param bin Bin number
     * @param value Value
     * @param chromosomeNum Chromosome number
     */
    public ChromosomeBin(int bin, float value, int chromosomeNum) {
        super();
        this.bin = bin;
        this.value = value;
        this.chromosomeNum = chromosomeNum;
    }
    
    
    // =============================
    //   Public methods
    // =============================
    
    
    public void bulkSet(ChromosomeBin cbin) {
    	this.bin = cbin.bin;
    	this.chromosomeNum = cbin.chromosomeNum;
    	this.value = cbin.value;
    }
}
