/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

import java.util.Collection;

import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Experiment;

/**
 * An analytic operation performed on data from a 
 * chromosome from a single
 * bioassay where no state is maintained between
 * method invocations.
 * @author dhall
 *
 */
public interface SingleBioAssayStatelessOperation extends StatelessOperation {
    
    /**
     * Perform operation.
     * @param input Input data.  This should be all data
     * from a chromosome from a single bioassay.
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    ChromosomeArrayData perform(ChromosomeArrayData input)
        throws AnalyticException;

    
    /**
     * Abstract base class for classes implementing this
     * {@link SingleBioAssayStatelessOperation}
     * that provides default implementations
     * of some methods.
     * @author dhall
     *
     */
    abstract class BaseSingleBioAssayStatelessOperation
    implements SingleBioAssayStatelessOperation {
    	
    	
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
        	return Experiment.countBioAssays(experiments);
        }
    }
}
