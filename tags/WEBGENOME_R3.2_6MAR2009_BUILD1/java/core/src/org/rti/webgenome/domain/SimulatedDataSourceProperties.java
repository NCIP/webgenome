/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-07-27 22:21:19 $


*/

package org.rti.webgenome.domain;

/**
 * Properties of a simulated data source.
 * @author dhall
 *
 */
public class SimulatedDataSourceProperties
extends DataSourceProperties.BaseDataSourceProperties {
	
	/**
	 * Constructor.
	 */
	public SimulatedDataSourceProperties() {
		
	}
	
	/**
	 * Equals method.
	 * @param obj An object.
	 * @return T/F
	 */
	@Override
	public final boolean equals(final Object obj) {
		return obj instanceof SimulatedDataSourceProperties;
	}

	/**
	 * Get hash code.
	 * @return Hash code
	 */
	@Override
	public final int hashCode() {
		return this.getClass().getName().hashCode();
	}
}
