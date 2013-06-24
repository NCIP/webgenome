/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

import java.util.List;

import org.rti.webgenome.domain.ChromosomeArrayData;

/**
 * An analytic operation on a data from a chromosome
 * from a set of experiments.
 * @author dhall
 *
 */
public interface MultiExperimentStatelessOperation
extends StatelessOperation {
	
    /**
     * Perform operation.
     * @param input Input data, which will originate from
     * one or more experiments.
     * @return Output data.  There is no correlation between
     * the input data and output data.  Different operations
     * may produce different numbers of chromosome array
     * data objects.
     * @throws AnalyticException if an error occurs
     * during this operation
     */
    List<ChromosomeArrayData> perform(List<ChromosomeArrayData> input)
        throws AnalyticException;
    
    
    /**
     * Generate a name from given chromosome array data.
     * @param cad Chromosome array data
     * @return Name
     */
    String getName(ChromosomeArrayData cad);

}
