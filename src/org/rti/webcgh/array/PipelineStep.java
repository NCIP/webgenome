/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/PipelineStep.java,v $
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

import java.util.HashSet;
import java.util.Set;

/**
 * Mediates persistence of analytic pipeline steps
 */
public class PipelineStep implements Comparable {
    
    
    // =============================================
    //   Attributes with accessors/mutators
    // =============================================
    
    private Long id = null;
    private String className = null;
    private Set pipelineStepParameters = new HashSet();
    private int stepNum = -1;
       
    
    /**
     * @return Returns the className.
     */
    public String getClassName() {
        return className;
    }
    
    
    /**
     * @param className The className to set.
     */
    public void setClassName(String className) {
        this.className = className;
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
     * @return Returns the pipelineOperationParameters.
     */
    public Set getPipelineStepParameters() {
        return pipelineStepParameters;
    }
    
    
    /**
     * @param pipelineOperationParameters The pipelineOperationParameters to set.
     */
    public void setPipelineStepParameters(Set pipelineOperationParameters) {
        this.pipelineStepParameters = pipelineOperationParameters;
    }
    
    
    /**
     * @return Returns the stepNum.
     */
    public int getStepNum() {
        return stepNum;
    }
    
    
    /**
     * @param stepNum The stepNum to set.
     */
    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }
    
    
    // ========================================================
    //         Constructors
    // ========================================================
    
    
    /**
     * Constructor
     */
    public PipelineStep() {
        super();
    }
    
    
    /**
     * Constructor
     * @param className Class name
     * @param stepNum Step number
     */
    protected PipelineStep(String className, int stepNum) {
        super();
        this.className = className;
        this.stepNum = stepNum;
    }
    
    
    // ================================================
    //      Methods in Comparable interface
    // ================================================
    
    
    /**
     * Comparison method
     * @param object An object
     * @return Comparison value (see documentation on Object.compareTo(Object))
     */
    public int compareTo(Object object) {
        if (! (object instanceof PipelineStep))
            throw new IllegalArgumentException("Expecting type 'PipelineStep'");
        PipelineStep step = (PipelineStep)object;
        int returnVal = 0;
        if (this.stepNum < step.getStepNum())
            returnVal = -1;
        else if (this.stepNum == step.getStepNum())
            returnVal = 0;
        else if (this.stepNum > step.getStepNum())
            returnVal = 1;
        return returnVal;
    }
    
    
    // =============================================
    //        Public methods
    // =============================================
    
    
    /**
     * Add a pipeline step parameter
     * @param param A pipeline step parameter
     */
    public void add(PipelineStepParameter param) {
    	this.pipelineStepParameters.add(param);
    }
    
    
    // ==================================================
    //    Overridden methods in Object
    // ==================================================
    
    
    /**
     * Value-based equals
     * @param obj An object
     * @return T/F
     */
    public boolean equals(Object obj) {
        if (! (obj instanceof PipelineStep))
            return false;
        PipelineStep pipelineStep = (PipelineStep)obj;
        return this.stepNum == pipelineStep.getStepNum();
    }
    
    
    /**
     * Hash code
     * @return Hash code
     */
    public int hashCode() {
        return 29 * this.stepNum;
    }
}
