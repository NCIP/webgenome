/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/AnalyticPipeline.java,v $
$Revision: 1.2 $
$Date: 2006-03-03 15:29:47 $

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


package org.rti.webcgh.analytic;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Map;

import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.array.persistent.PersistentPipeline;
import org.rti.webcgh.array.persistent.PersistentPipelineStep;
import org.rti.webcgh.array.persistent.PersistentPipelineStepParameter;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.service.UserProfile;
import org.rti.webcgh.util.BeanUtils;
import org.rti.webcgh.util.XmlUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.Serializable;


/**
 * Sequence of analytic operations
 */
public class AnalyticPipeline implements Serializable {
	
    private Long id = null;
	private String name = "";
	private List operations = new ArrayList();
	private boolean readOnly = false;
	
	
	/**
	 * Constructor
	 *
	 */
	public AnalyticPipeline() {}
	
	
	/**
	 * Constructor
	 * @param name Pipeline name
	 * @param readOnly Is pipeline readonly?
	 */
	public AnalyticPipeline(String name, boolean readOnly) {
		this.name = name;
		this.readOnly = readOnly;
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
	 * Setter for property name
	 * @param name Pipeline name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Getter for property name
	 * @return Pipeline name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Setter for property readOnly
	 * @param readOnly Can pipeline we modified by user?
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	
	/**
	 * Getter for property readOnly
	 * @return T/F depending on whether user can modify pipeline
	 */
	public boolean isReadOnly() {
		return readOnly;
	}
	
	
	/**
	 * Setter for property operations
	 * @param operations Analytic operations
	 */
	public void setOperations(List operations) {
		this.operations = operations;
	}
	
	
	/**
	 * Getter for property operations
	 * @return Analytic operations
	 */
	public List getOperations() {
		return operations;
	}
	
	
	/**
	 * Add analytic operation
	 * @param operation An analytic operation
	 */
	public void addOperation(AnalyticOperation operation) {
		operations.add(operation);
	}
	
	
	/**
	 * Does pipeline result in computing experimental means?
	 * @return T/F
	 */
	public boolean resultsInMeans() {
		boolean isAverages = false;
		for (Iterator it = operations.iterator(); it.hasNext() && ! isAverages;) {
			AnalyticOperation op = (AnalyticOperation)it.next();
			if (op instanceof AverageOperation)
				isAverages = true;
		}
		return isAverages;
	}
	
	
	/**
	 * To persistent pipeline
	 * @param objMgr Persistent domain object manager
	 * @param profile User profile
	 * @return Persistent pipeline
	 */
	public PersistentPipeline toPersistentPipeline(
			PersistentDomainObjectMgr objMgr, UserProfile profile) {
		PersistentPipeline pipeline = objMgr.getPersistentPipeline(this.name, profile.getName());
		if (pipeline != null)
			pipeline.delete();
		pipeline = objMgr.newPersistentPipeline(this.name, 
				profile.getName());
    	this.addPipelineSteps(pipeline, objMgr);
    	pipeline.update();
    	return pipeline;
	}
	
	
	/**
	 * Load default pipelines
	 * @return Default pipelines
	 * @throws WebcghSystemException
	 */
	public static Collection loadDefaultPipelines() throws WebcghSystemException {
		Collection pipelines = new ArrayList();
		Document doc = XmlUtils.loadDocument("conf/pipelines.xml", false);
		NodeList pipeEls = doc.getElementsByTagName("pipelines");
		for (int i = 0; i < pipeEls.getLength(); i++) {
			Node temp = pipeEls.item(i);
			if (temp instanceof Element) {
				Element pipeEl = (Element)temp;
				NodeList ops = pipeEl.getChildNodes();
				for (int j = 0; j < ops.getLength(); j++) {
					temp = ops.item(j);
					if (temp instanceof Element) {
						Element pipe = (Element)temp;
						AnalyticPipeline pipeline = (AnalyticPipeline)
							BeanUtils.xmlToBean(pipe);
						pipeline.setReadOnly(true);
						pipelines.add(pipeline);
					}
				}
			}
		}
		return pipelines;
	}
	
	
	/**
	 * Load analytic pipeline associated with given name
	 * @param name Pipeline name
	 * @return An analytic pipeline
	 */
	public static AnalyticPipeline loadDefaultPipeline(String name) {
	    Collection pipes = AnalyticPipeline.loadDefaultPipelines();
	    AnalyticPipeline pipe = null;
	    for (Iterator it = pipes.iterator(); it.hasNext() && pipe == null;) {
	        AnalyticPipeline tempPipe = (AnalyticPipeline)it.next();
	        if (tempPipe.name.equals(name))
	            pipe = tempPipe;
	    }
	    return pipe;
	}
	
	
	/**
	 * Number of operations in pipeline
	 * @return Number of operations in pipeline
	 */
	public int numOperations() {
		int size = 0;
		if (this.operations != null)
			size = this.operations.size();
		return size;
	}
	
	
	// =======================================
	//       Private methods
	// =======================================
	
    
    
    private void addPipelineSteps(PersistentPipeline pipeline,
    		PersistentDomainObjectMgr objMgr) {
    	if (this.operations != null) {
	    	int count = 0;
	    	for (Iterator opIt = this.operations.iterator(); opIt.hasNext();) {
	    		AnalyticOperation op = (AnalyticOperation)opIt.next();
	    		PersistentPipelineStep step = objMgr.newPersistentPipelineStep(op.getClass().getName(), count++);
	    		pipeline.add(step);
	    		this.addPipelineStepParameters(step, op, objMgr);
	    	}
    	}
    }
    
    
    private void addPipelineStepParameters(PersistentPipelineStep step, 
    		AnalyticOperation op, PersistentDomainObjectMgr objMgr) {
    	Map properties = BeanUtils.getProperties(op, true);
    	for (Iterator it = properties.keySet().iterator(); it.hasNext();) {
    		String propName = (String)it.next();
    		if (! "id".equals(propName)) {
	    		Object origPropValue = properties.get(propName);
	    		String propValue = null;
	    		Class propClass = origPropValue.getClass();
	    		if (propClass.isPrimitive() || "java.lang".equals(propClass.getPackage().getName()))
	    			propValue = String.valueOf(origPropValue);
	    		else
	    			propValue = propClass.getName();
	    		PersistentPipelineStepParameter param = 
	    			objMgr.newPersistentPipelineStepParameter(propName, propValue);
	        	step.add(param);
    		}
    	}
    }

}
