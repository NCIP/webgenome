/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:11 $


*/

package org.rti.webgenome.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.analysis.AnalyticOperation;
import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.core.WebGenomeSystemException;

/**
 * Source of data for an experiment derived through
 * an analytic operation.
 * @author dhall
 *
 */
public class AnalysisDataSourceProperties
extends DataSourceProperties.BaseDataSourceProperties {

	//
	//  A T T R I B U T E S
	//
	
	/**
	 * Fully qualified name of the analytic operation class
	 * that generated the output experiment.
	 */
	private String analyticOperationClassName = null;
	
	/**
	 * User configurable properties associated with the
	 * analytic operation that generated the output experiment.
	 */
	private Set<UserConfigurableProperty> userConfigurableProperties =
    	new HashSet<UserConfigurableProperty>();
	
	
	//
	//  G E T T E R S / S E T T E R S
	//
	
	/**
	 * Get fully qualified name of the analytic operation class
	 * that generated the output experiment.
	 * @return Fully qualified name of the analytic operation class
	 * that generated the output experiment.
	 */
	public String getAnalyticOperationClassName() {
		return analyticOperationClassName;
	}

	/**
	 * Set fully qualified name of the analytic operation class
	 * that generated the output experiment.
	 * @param analyticOperationClassName Fully qualified
	 * name of the analytic operation class
	 * that generated the output experiment.
	 */
	public void setAnalyticOperationClassName(
			final String analyticOperationClassName) {
		this.analyticOperationClassName = analyticOperationClassName;
	}


	/**
	 * Get user configurable properties associated with the
	 * analytic operation that generated the output experiment.
	 * @return User configurable properties associated with the
	 * analytic operation that generated the output experiment.
	 */
	public Set<UserConfigurableProperty> getUserConfigurableProperties() {
		return userConfigurableProperties;
	}

	/**
	 * Set user configurable properties associated with the
	 * analytic operation that generated the output experiment.
	 * @param userConfigurableProperties User configurable properties
	 * associated with the
	 * analytic operation that generated the output experiment.
	 */
	public void setUserConfigurableProperties(
			final Set<UserConfigurableProperty> userConfigurableProperties) {
		this.userConfigurableProperties = userConfigurableProperties;
	}
	
	//
	//  C O N S T R U C T O R S
	//

	/**
	 * Constructor.
	 */
	public AnalysisDataSourceProperties() {
		super();
	}

	/**
	 * Constructor.
	 * @param analyticOperationClassName Fully qualified name
	 * of the analytic operation class
	 * that generated the output experiment.
	 * @param userConfigurableProperties User configurable properties
	 * associated with the
	 * analytic operation that generated the output experiment.
	 */
	public AnalysisDataSourceProperties(
			final String analyticOperationClassName,
			final Set<UserConfigurableProperty> userConfigurableProperties) {
		super();
		this.analyticOperationClassName = analyticOperationClassName;
		this.userConfigurableProperties = userConfigurableProperties;
	}
	
	
	/**
	 * Constructor.
	 * @param operation Operation that produced the output.
	 * @param quantitationTypes Quantitation types of input
	 * data.
	 */
	public AnalysisDataSourceProperties(
			final AnalyticOperation operation,
			final Collection<QuantitationType> quantitationTypes) {
		this.setSourceAnalyticOperation(operation,
				quantitationTypes);
	}
	
	/**
	 * Constructor.
	 * @param operation Operation that produced the output.
	 * @param quantitationType Quantitation type of input
	 * data.
	 */
	public AnalysisDataSourceProperties(
			final AnalyticOperation operation,
			final QuantitationType quantitationType) {
		Collection<QuantitationType> qTypes = new ArrayList<QuantitationType>();
		qTypes.add(quantitationType);
		this.setSourceAnalyticOperation(operation, qTypes);
	}
	
	
	//
	//  B U S I N E S S    M E T H O D S
	//
	
	/**
	 * Get source analytic operation.
	 * If this experiment object was generated through an analytic
     * operation, this property represents this operation.
	 * @return Source analytic operation
	 */
	public final AnalyticOperation getSourceAnalyticOperation() {
		AnalyticOperation op = null;
		if (this.analyticOperationClassName != null
				&& this.analyticOperationClassName.length() > 0) {
			try {
				Class clazz =
					Class.forName(this.analyticOperationClassName);
				op = (AnalyticOperation) clazz.newInstance();
				for (UserConfigurableProperty prop
						: this.userConfigurableProperties) {
					op.setProperty(prop.getName(), prop.getCurrentValue());
				}
			} catch (Exception e) {
				throw new WebGenomeSystemException(
						"Error reconstituting analytic operation '"
						+ this.analyticOperationClassName + "'", e);
			}
		}
		return op;
	}
	
	
	/**
	 * Set source analytic operation.
	 * If this experiment object was generated through an analytic
     * operation, this property represents this operation.
	 * @param sourceAnalyticOperation Source analytic operation.
	 * @param quantitationTypes Quantitation types on which the
	 * operation is performed.
	 */
	public final void setSourceAnalyticOperation(
			final AnalyticOperation sourceAnalyticOperation,
			final Collection<QuantitationType> quantitationTypes) {
		this.analyticOperationClassName =
			sourceAnalyticOperation.getClass().getName();
		this.userConfigurableProperties = new HashSet<UserConfigurableProperty>(
			sourceAnalyticOperation.getUserConfigurableProperties(
					quantitationTypes));
	}
}
