/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2006-10-20 03:01:24 $

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

import java.util.HashMap;
import java.util.Map;

import org.rti.webcgh.util.SystemUtils;
import org.rti.webcgh.webui.struts.BaseForm;

/**
 * Form for capturing user-inputted configurable parameters
 * for an analytic operation.
 * @author dhall
 *
 */
public class AnalyticOperationParametersForm extends BaseForm {

	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	// =====================================
	//      Attributes
	// =====================================
	
	/** Analytic operation key. */
	private String operationKey = "";
	
	/**
	 * Map containing user-input configurable parameter
	 * name-value pairs.
	 */ 
	private Map<String, Object> params = new HashMap<String, Object>();
	
	
	// ==================================
	//     Getters/setters
	// ==================================
	
	/**
	 * Get analytic operation key.
	 * @return Analytic operation key.
	 */
	public final String getOperationKey() {
		return operationKey;
	}

	
	/**
	 * Set analytic operation key.
	 * @param operationKey Analytic operation key.
	 */
	public final void setOperationKey(final String operationKey) {
		this.operationKey = operationKey;
	}
	
	
	/**
	 * Set configurable parameter name and value.
	 * @param name Name of configurable parameter.
	 * @param value Value of configurable parameter.
	 */
	public final void setParamValue(final String name, final Object value) {
		this.params.put(name, value);
	}
	
	
	/**
	 * Get configurable parameter name and value.
	 * @param name Name of configurable parameter.
	 * @return Value of configurable parameter.
	 */
	public final Object getParamValue(final String name) {
		return this.params.get(name);
	}
}
