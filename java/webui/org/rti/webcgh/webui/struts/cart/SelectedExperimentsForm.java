/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:10 $

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

package org.rti.webcgh.webui.struts.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.util.SystemUtils;
import org.rti.webcgh.webui.SessionTimeoutException;
import org.rti.webcgh.webui.struts.BaseForm;
import org.rti.webcgh.webui.util.PageContext;

/**
 * This form is intended to be used to record
 * selected experiments and some action to be performed
 * on the experiments.  The list of possible experiments
 * is dynamic, so this form uses a map to back selected
 * experiments.
 * @author dhall
 *
 */
public class SelectedExperimentsForm extends BaseForm {
	
	// ==============================
	//    Constants
	// ==============================
	
	/** Default data operation. */
	private static final String DEF_OPERATION = "plot";
	
	/** Text indicating a HTML textbox is checked. */
	private static final String CHECKED = "on";
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	// ==============================
	//         Attributes
	// ==============================
	

	/** Map backing dynamic fields. */
    private Map<String, Object> values = new HashMap<String, Object>();
    
    /**
     * Operation selected by user.  Possible values are
     * 'plot' and 'analysis.'
     */
    private String operation = DEF_OPERATION;
    
    
    // ===============================
    //      Getters/setters
    // ===============================
    
    /**
     * Setter used for dynamic form fields, i.e., experiment
     * IDs.
     * @param key Key
     * @param value Value
     */
    public final void setValue(final String key, final Object value) {
        this.values.put(key, value);
    }
    
    
    /**
     * Getter used for dynamic form fields, i.e., experiment
     * IDs.
     * @param key Key
     * @return A value
     */
    public final Object getValue(final String key) {
        return this.values.get(key);
    }
	
    /**
     * Get operation to be performed on selected
     * experiments.
     * @return Operation
     */
    public final String getOperation() {
		return operation;
	}


    /**
     * Set operation to be performed on selected
     * experiments.
     * @param operation Operation
     */
	public final void setOperation(final String operation) {
		this.operation = operation;
	}
	
	
    // ==============================
    //      Business methods
    // ==============================


	/**
     * Get experiment IDs selected from upstream HTML form.
     * @return Experiment IDs
     */
    public final Collection<Long> getSelectedExperimentIds() {
    	Collection<Long> ids = new ArrayList<Long>();
    	for (String key : this.values.keySet()) {
    		if (key.indexOf(PageContext.EXPERIMENT_ID_PREFIX) == 0) {
    			String idStr = key.substring(
    					PageContext.EXPERIMENT_ID_PREFIX.length());
    			long id = Long.parseLong(idStr);
    			ids.add(id);
    		}
    	}
    	return ids;
    }
    
    
    /**
     * Set selected experiment IDs.
     * @param experiments Experiments
     */
    public final void setSelectedExperimentIds(
    		final Collection<Experiment> experiments) {
    	this.values.clear();
    	for (Experiment exp : experiments) {
    		if (exp.getId() == null) {
    			throw new WebcghSystemException("Experiment ID is null");
    		}
    		String key = PageContext.EXPERIMENT_ID_PREFIX + exp.getId();
    		this.values.put(key, CHECKED);
    	}
    }
    
    
    // ================================
    //       Overrides
    // ================================
    
    /**
     * Reset form.
     * @param actionMapping Action mappings
     * @param request Servlet request
     */
	@Override
	public final void reset(final ActionMapping actionMapping,
			final HttpServletRequest request) {
		this.values.clear();
	}


	/**
	 * Validate form fields.
	 * @param actionMappings Action mappings.
	 * @param request Servlet request.
	 * @return Action errors
	 */
	@Override
	public final ActionErrors validate(final ActionMapping actionMappings,
			final HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		// Make sure at least one experiment selected
		if (this.values.size() < 1) {
			errors.add("global", new ActionError("no.experiments.selected"));
		} else {
		
			// Make sure selected experiments not of different
			// quantitation types
			ShoppingCart cart = null;
			try {
				cart = PageContext.getShoppingCart(request);
			} catch (SessionTimeoutException e) {
				throw new WebcghSystemException(
						"Unable to obtain shopping cart", e);
			}
			Collection<Experiment> exps = cart.getExperiments(
					this.getSelectedExperimentIds());
			QuantitationType qType = null;
			for (Experiment exp : exps) {
				if (qType == null) {
					qType = exp.getQuantitationType();
				} else {
					if (qType != exp.getQuantitationType()) {
						errors.add("global",
								new ActionError("mixed.quantitation.types"));
						break;
					}
				}
			}
		}
			
		return errors;
	}
}
