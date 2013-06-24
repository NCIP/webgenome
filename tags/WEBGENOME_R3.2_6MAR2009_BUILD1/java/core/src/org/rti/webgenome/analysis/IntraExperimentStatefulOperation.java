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

/**
 * Represents a stateful analytic operation where the state
 * should be adjusted by all data from an entire experiment
 * before performing the actual operation.
 * @author dhall
 *
 */
public interface IntraExperimentStatefulOperation
    extends StatefulOperation {

}
