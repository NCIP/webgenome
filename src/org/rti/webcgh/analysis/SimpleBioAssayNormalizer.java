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

import java.util.Map;

import org.rti.webcgh.domain.ChromosomeArrayData;

/**
 * Performs "simple" normalization, subtracting
 * either mean or median value of bioassay
 * from all values to bring to mean or median,
 * respectively, to 0.
 * @author dhall
 *
 */
public final class SimpleBioAssayNormalizer
    implements StatefulBioAssayAnalyticOperation {
    
    // =================================
    //        Attributes
    // =================================
    
    /** Use median in calculation instead of mean. */
    private boolean useMedian = false;
    
    
    // =========================
    //    Getters/setters
    // =========================
    
    
    /**
     * Use median instead of mean
     * in calculations?
     * @return T/F
     */
    public boolean isUseMedian() {
        return useMedian;
    }


    /**
     * User median instead of mean
     * in calculations?
     * @param useMedian T/F
     */
    public void setUseMedian(final boolean useMedian) {
        this.useMedian = useMedian;
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
    public void adjustState(
            final ChromosomeArrayData chromosomeArrayData)
        throws AnalyticException {
        
    }
    
    
    /**
     * Perform operation.
     * @param input Input data
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    public ChromosomeArrayData perform(
            final ChromosomeArrayData input)
        throws AnalyticException {
        return null;
    }
    
    
    /**
     * Get name of operation.
     * @return Name of operation
     */
    public String getName() {
        return "Simple array-based normalization";
    }
    
    /**
     * Return configurable properties.
     * Keys are property names and values
     * the corresponding text that would
     * be displayed by users.
     * @return Configurable properties.
     * Keys are property names and values
     * the corresponding text that would
     * be displayed by users
     */
    public Map<String, String> getConfigurableProperties() {
        return null;
    }

}
