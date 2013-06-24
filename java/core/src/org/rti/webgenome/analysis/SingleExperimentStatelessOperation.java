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

import java.util.Collection;
import java.util.List;

import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Experiment;

/**
 * An analytic operation performed on all data from
 * a chromosome from a single experiment in which
 * no state is maintained between method invocations. 
 * @author dhall
 *
 */
public interface SingleExperimentStatelessOperation extends StatelessOperation {
    
    /**
     * Perform operation.
     * @param input Input data.  This should be all
     * data from a chromosome from a single experiment
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    ChromosomeArrayData perform(List<ChromosomeArrayData> input)
        throws AnalyticException;

    
    /**
     * Abstract base class for classes implementing
     * {@link SingleExperimentStatelessOperation} that
     * provides some default implementations.
     * @author dhall
     *
     */
    abstract class DefSingleExperimentStatelessOperation
    implements SingleExperimentStatelessOperation {
    	
    	/**
         * Determine the number of bioassays that would result
         * from a proper running of the given experiments through
         * this operation.
         * @param experiments Some experiments
         * @return The number of bioassays that would result
         * from a proper running of the given experiments through
         * this operation.
         */
        public int numResultingBioAssays(
        		final Collection<Experiment> experiments) {
        	return experiments.size();
        }
    }
}
