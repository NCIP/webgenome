/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:11 $

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
