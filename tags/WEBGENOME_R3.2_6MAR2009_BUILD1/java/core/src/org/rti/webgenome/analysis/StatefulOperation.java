/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

import java.util.Collection;

import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Experiment;

/**
 * A anlaytic operation that maintains
 * some sort of internal state that
 * affects subsequent operations.
 * @author dhall
 *
 */
public interface StatefulOperation extends AnalyticOperation {
    
    
    /**
     * Adjust the state of this operation.
     * @param chromosomeArrayData Chromosome array
     * data that will modify the internal state
     * @throws AnalyticException if there is a
     * computational error
     */
    void adjustState(ChromosomeArrayData chromosomeArrayData)
        throws AnalyticException;
    
    
    /**
     * Reset state of operation.
     *
     */
    void resetState();
    
    
    /**
     * Perform operation.
     * @param input Input data
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    ChromosomeArrayData perform(
            final ChromosomeArrayData input)
        throws AnalyticException;
    
    /**
     * Abstract base class for classes implementing
     * {@link StatefulOperation} that provides some
     * default implementations.
     * @author dhall
     *
     */
    abstract class DefStatefulOperation implements StatefulOperation {

    	/**
    	 * {@inheritDoc}
    	 */
		public int numResultingBioAssays(
				final Collection<Experiment> experiments) {
			return Experiment.countBioAssays(experiments);
		}
    	
    }
}
