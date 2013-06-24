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

import org.rti.webgenome.domain.ChromosomeArrayData;

/**
 * An analytic operation where some sort of cumulative
 * results are maintained and then accessed.
 * @author dhall
 *
 */
public interface AccumulatorOperation extends AnalyticOperation {

	/**
	 * Get cumulative results.
	 * @return Cumulative results
	 */
	ChromosomeArrayData getResults();
	
	/**
	 * Add more data.
	 * @param cad Chromosome array data
	 */
	void add(ChromosomeArrayData cad);
}
