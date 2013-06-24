/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-07-18 21:42:48 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;
import org.rti.webgenome.webui.util.PageContext;

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
    			throw new WebGenomeSystemException("Experiment ID is null");
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
		}
			
		return errors;
	}
}
