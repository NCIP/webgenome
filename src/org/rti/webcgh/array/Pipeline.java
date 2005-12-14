/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/Pipeline.java,v $
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

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.analytic.AnalyticOperation;
import org.rti.webcgh.analytic.AnalyticPipeline;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.util.BeanUtils;
import org.rti.webcgh.util.StringUtils;

/**
 * Used for mediating storage of pipelines
 */
public class Pipeline {
    
    // =================================================
    //       Attributes with accessors/mutators
    // =================================================
    
    private Long id = null;
    private String name = null;
    private String userName = null;
    private SortedSet pipelineSteps = new TreeSet();
    

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
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }
    
    
    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
    /**
     * @return Returns the pipelineSteps.
     */
    public SortedSet getPipelineSteps() {
        return pipelineSteps;
    }
    
    
    /**
     * @param pipelineSteps The pipelineSteps to set.
     */
    public void setPipelineSteps(SortedSet pipelineSteps) {
        this.pipelineSteps = pipelineSteps;
    }
    
    
    // ===================================================
    //         Constructors
    // ===================================================
    
    
    /**
     * Constructor
     */
    public Pipeline() {
        super();
    }
    
    
    /**
     * Constructor
     * @param name Name
     * @param userName User name
     */
    protected Pipeline(String name, String userName) {
        super();
        this.name = name;
        this.userName = userName;
    }
    
    
    // =========================================
    //     Public methods
    // =========================================
    
    
    /**
     * Add a pipeline step
     * @param step A pipeline step
     */
    public void add(PipelineStep step) {
    	this.pipelineSteps.add(step);
    }
    
    
    
    // ================================================
    //     Overridden methods from Object
    // ================================================
    
    
    /**
     * Value-based equality
     * @param obj An object
     * @return T/F
     */
    public boolean equals(Object obj) {
        if (! (obj instanceof Pipeline))
            return false;
        Pipeline pipeline = (Pipeline)obj;
        return
        	StringUtils.equal(this.name, pipeline.getName()) &&
        	StringUtils.equal(this.userName, pipeline.getName());
    }
    
    
    /**
     * Hash code
     * @return Hash code
     */
    public int hashCode() {
        return
        	(this.name + this.userName).hashCode();
    }
    
    
    // ======================================
    //     Other public methods
    // ======================================
    
    /**
     * Convert to analytic pipeline
     * @return Analytic pipeline
     */
    public AnalyticPipeline toAnalyticPipeline() {
    	AnalyticPipeline aPipeline = new AnalyticPipeline(this.name, false);
    	this.addAnalyticOperations(aPipeline);
    	return aPipeline;
    }
    
    
    // =====================================
    //      Private methods
    // =====================================
    
    
    private void addAnalyticOperations(AnalyticPipeline aPipeline) {
    	for (Iterator it = this.pipelineSteps.iterator(); it.hasNext();) {
    		PipelineStep step = (PipelineStep)it.next();
    		AnalyticOperation op = null;
    		try {
				Class opClass = Class.forName(step.getClassName());
				op = (AnalyticOperation)opClass.newInstance();
			} catch (Exception e) {
				throw new WebcghSystemException("Error creating analytic operation", e);
			}
    		aPipeline.addOperation(op);
    		this.setAnalyticOperationProperties(op, step);
    	}
    }
    
    
    private void setAnalyticOperationProperties(AnalyticOperation op, PipelineStep step) {
    	Set params = step.getPipelineStepParameters();
    	for (Iterator it = params.iterator(); it.hasNext();) {
    		PipelineStepParameter param = (PipelineStepParameter)it.next();
    		String paramName = param.getParamName();
    		String paramValue = param.getValue();
    		if (paramValue.indexOf(".class") >= 0) {
    			Object obj = null;
    			try {
    				Class.forName(paramValue).newInstance();
    			} catch (Exception e) {
    				throw new WebcghSystemException("Error setting analytic operation property", e);
    			}
    			BeanUtils.setProperty(op, paramName, obj);
    		} else
    			BeanUtils.setPrimitiveProperty(op, paramName, paramValue);
    	}
    }
}
