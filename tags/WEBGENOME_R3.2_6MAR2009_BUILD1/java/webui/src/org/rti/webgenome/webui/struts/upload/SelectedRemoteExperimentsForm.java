/*
$Revision: 1.2 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseForm;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Form for capturing selected experiments from a
 * remote data source.
 * @author dhall
 *
 */
public class SelectedRemoteExperimentsForm extends BaseForm {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	/** Organism associated with data. */
	private String organismId = null;
	
	/** ID of quantitation type of all data. */
	private String quantitationTypeId =
		QuantitationType.COPY_NUMBER.getId();
	
	/** Map backing dynamic fields. */
    private Map<String, Object> values = new HashMap<String, Object>();
    
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
     * Get experiment IDs selected from upstream HTML form.
     * @return Experiment IDs
     */
    public final Collection<String> getSelectedExperimentIds() {
    	Collection<String> ids = new ArrayList<String>();
    	for (String key : this.values.keySet()) {
    		if (key.indexOf(PageContext.EXPERIMENT_ID_PREFIX) == 0) {
    			String id = key.substring(
    					PageContext.EXPERIMENT_ID_PREFIX.length());
    			ids.add(id);
    		}
    	}
    	return ids;
    }
    
    
	/**
	 * Get primary key ID of organism associated with data.
	 * @return ID of organism associated with data.
	 */
	public String getOrganismId() {
		return organismId;
	}

	/**
	 * Set primary key ID of organism associated with data.
	 * @param organism ID of organism associated with data.
	 */
	public void setOrganismId(final String organism) {
		this.organismId = organism;
	}
	
	/**
	 * Get quantitation type ID of all data.
	 * @return Quantitation type ID
	 */
	public String getQuantitationTypeId() {
		return quantitationTypeId;
	}

	/**
	 * Set quantitation type ID of all data.
	 * @param quantitationTypeId Quantitation type
	 * ID
	 */
	public void setQuantitationTypeId(
			final String quantitationTypeId) {
		this.quantitationTypeId = quantitationTypeId;
	}
    
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
