/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-28 22:12:17 $


*/

package org.rti.webgenome.service.dao;

import org.rti.webgenome.service.plot.PlotParameters;

/**
 * Data access class for
 * {@link org.rti.webcgh.service.plot.PlotParameters}
 * and subclasses.
 * @author dhall
 *
 */
public interface PlotParametersDao {

	/**
	 * Persist given plot parameters.
	 * @param params Plot parameters
	 */
	void save(PlotParameters params);
	
	/**
	 * Remove given plot parameters from persistent
	 * storage.
	 * @param params Plot parameters
	 */
	void delete(PlotParameters params);
}
