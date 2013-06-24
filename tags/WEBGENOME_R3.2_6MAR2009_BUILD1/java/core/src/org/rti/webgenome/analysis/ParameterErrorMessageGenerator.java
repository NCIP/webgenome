/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Generate error messages for bad analytic
 * operation parameters.
 * @author dhall
 *
 */
public class ParameterErrorMessageGenerator {

	/** List of invalid parameter names. */
	private final List<String> invalidParamNames = new ArrayList<String>();
	
	/** Parameter values. */
	private final List<String> values = new ArrayList<String>();
	
	/**
	 * Add name of invalid parameter.
	 * @param paramName Invalid parameter name.
	 * @param value Value
	 */
	public final void addInvalidParameterName(
			final String paramName, final String value) {
		this.invalidParamNames.add(paramName);
		this.values.add(value);
	}
	
	
	/**
	 * Have invalid parameters been reported?
	 * @return T/F
	 */
	public final boolean invalidParameters() {
		return this.invalidParamNames.size() > 0;
	}
	
	/**
	 * Get invalid parameter message.
	 * @return Invalid parameter message.
	 */
	public final String getMessage() {
		StringBuffer buff = new StringBuffer();
		if (this.invalidParameters()) {
			buff.append("Invalid parameter");
			if (this.invalidParamNames.size() > 1) {
				buff.append("s");
			}
			buff.append(": ");
			int count = 0;
			Iterator<String> valueIt = this.values.iterator();
			for (String name : this.invalidParamNames) {
				if (count++ > 0) {
					buff.append(",");
				}
				buff.append(name + "(" + valueIt.next() + ")");
			}
		}
		return buff.toString();
	}
}
