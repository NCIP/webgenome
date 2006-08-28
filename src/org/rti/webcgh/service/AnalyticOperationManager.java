/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.rti.webcgh.analysis.AnalyticException;
import org.rti.webcgh.analysis.AnalyticOperation;
import org.rti.webcgh.analysis.AnalyticPipeline;
import org.rti.webcgh.analysis.ListToScalarAnalyticOperation;
import org.rti.webcgh.analysis.ScalarToScalarAnalyticOperation;
import org.rti.webcgh.analysis.StatefulBioAssayAnalyticOperation;
import org.rti.webcgh.analysis.StatefulExperimentAnalyticOperation;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.DataContainingBioAssay;
import org.rti.webcgh.domain.DataSerializedBioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.io.DataFileManager;

/**
 * Manager analytic operation on data where
 * all data are in memory.
 * @author dhall
 *
 */
public class AnalyticOperationManager {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(AnalyticOperationManager.class);
    
    
    // ============================================
    //          Attributes
    // ============================================
    
    /**
     * Data file manager used to serialize/de-serialize data.
     * This property should be injected.
     */
    private DataFileManager dataFileManager = null;
    
    
    // ======================================
    //        Getters/setters
    // ======================================
    
    
    /**
     * Set data file manager used to serialize/de-serialize
     * data.  This property should be injected.
     * @return Data file manager
     */
    public final DataFileManager getDataFileManager() {
        return dataFileManager;
    }


    /**
     * Set data file manager used to serialize/de-serialize data.
     * This property should be injected.
     * @param dataFileManager Data file manager
     */
    public final void setDataFileManager(
            final DataFileManager dataFileManager) {
        this.dataFileManager = dataFileManager;
    }

    
    // =====================================
    //       Business methods
    // =====================================

    /**
     * Perform given analytic operation on data contained
     * in given input experiment.
     * @param input Input experiment data
     * @param operation Analytic operation to perform
     * @return New experiment containing data produced
     * by analytic operation
     * @throws AnalyticException if a computation error occurs
     */
    public final Experiment perform(final Experiment input,
            final AnalyticOperation operation) throws AnalyticException {
    	LOGGER.info("Performing '" + operation.getName()
    			+ "' operation on experiment '" + input.getName() + "'");
        String experimentName = input.getName() + " " + operation.getName();
        Experiment output =
            new Experiment(experimentName, input.getQuantitationType());
        this.perform(input, output, operation, true);
        LOGGER.info("Completed operation");
        return output;
    }
    
    
    /**
     * Perform given analytic operation on given input data
     * writing the results in given output experiment.
     * @param input Input data
     * @param output Output experiment
     * @param operation Opeation to perform
     * @param renameBioAssays Rename bioassays?
     * @throws AnalyticException if a computation error occurs
     */
    private void perform(final Experiment input, final Experiment output,
            final AnalyticOperation operation, final boolean renameBioAssays)
    throws AnalyticException {
        if (operation instanceof ScalarToScalarAnalyticOperation) {
            this.perform(input, output,
                    (ScalarToScalarAnalyticOperation) operation, true);
        } else if (operation instanceof ListToScalarAnalyticOperation) {
            this.perform(input, output,
                    (ListToScalarAnalyticOperation) operation, true);
        } else if (operation instanceof AnalyticPipeline) {
            this.perform(input, output, (AnalyticPipeline) operation);
        }
    }
    
    
    /**
     * Perform given analytic operation on input data writing
     * results to output.  This method performs operation
     * on all <code>ChromosomeArrayData</code> objects one by one.
     * @param input Input data
     * @param output Output data
     * @param operation Operation to perform
     * @param renameBioAssays Rename bioassays?
     * @throws AnalyticException if a computation error occurs
     */
    private void perform(final Experiment input, final Experiment output,
            final ScalarToScalarAnalyticOperation operation,
            final boolean renameBioAssays)
        throws AnalyticException {
        if (operation instanceof StatefulExperimentAnalyticOperation) {
            ((StatefulExperimentAnalyticOperation) operation).resetState();
        }
        for (BioAssay ba : input.getBioAssays()) {
            if (operation instanceof StatefulBioAssayAnalyticOperation) {
                ((StatefulBioAssayAnalyticOperation) operation).resetState();
            }
            BioAssay newBa = this.clone(ba);
            output.add(newBa);
            if (renameBioAssays) {
                String newName = ba.getName() + " " + operation.getName();
                newBa.setName(newName);
            }
            ChromosomeArrayDataIterator it =
                new ChromosomeArrayDataIterator(this.dataFileManager, ba);
            while (it.hasNext()) {
                ChromosomeArrayData inputCad = it.next();
                ChromosomeArrayData outputCad = operation.perform(inputCad);
                this.addChromosomeArrayData(newBa, outputCad);
            }
        }
    }
    
    
    /**
     * Perform given analytic operation on input data writing
     * results to output.  This method pools chromosome array
     * data from the same chromosome across all bioassays
     * and performs operation on these pools.
     * @param input Input data
     * @param output Output data
     * @param operation Operation to perform
     * @param renameBioAssays Rename bioassays?
     * @throws AnalyticException if a computation error occurs
     */
    private void perform(final Experiment input, final Experiment output,
            final ListToScalarAnalyticOperation operation,
            final boolean renameBioAssays)
        throws AnalyticException {
        if (input.getBioAssays().size() < 1) {
            throw new IllegalArgumentException(
                    "Cannot perform operation on empty data set");
        }
        BioAssay bioAssay = this.clone(input.getBioAssays().iterator().next());
        if (renameBioAssays) {
            String bioAssayName = input.getName() + " " + operation.getName();
            bioAssay.setName(bioAssayName);
        }
        output.add(bioAssay);
        for (Short chromosome : input.getChromosomes()) {
            List<ChromosomeArrayData> cad =
                new ArrayList<ChromosomeArrayData>();
            for (BioAssay ba : input.getBioAssays()) {
                cad.add(this.getChromosomeArrayData(ba, chromosome));
            }
            ChromosomeArrayData newCad = operation.perform(cad);
            this.addChromosomeArrayData(bioAssay, newCad);
        }
    }
    
    
    /**
     * Perform each operation in given pipeline.
     * @param input Data input into pipeline
     * @param output Data output into pipeline
     * @param pipeline Analytic pipeline
     * @throws AnalyticException if a computation error occurs
     */
    private void perform(final Experiment input, final Experiment output,
            final AnalyticPipeline pipeline) throws AnalyticException {
        if (pipeline.getOperations().size() < 1) {
            throw new IllegalArgumentException(
                    "Pipeline must have at least one operation");
        }
        
        // Perform operations
        Experiment intermediateIn = input;
        Experiment intermediateOut = null;
        List<AnalyticOperation> ops = pipeline.getOperations();
        for (int i = 0; i < ops.size(); i++) {
            AnalyticOperation op = ops.get(i);
            if (i == ops.size() - 1) {
                this.perform(intermediateIn, output, op, false);
            } else {
                intermediateOut = this.perform(intermediateIn, op);
            }
            if (intermediateIn != input) {
                this.dataFileManager.deleteDataFiles(intermediateIn, false);
            }
            intermediateIn = intermediateOut;
        }
        
        // Rename bioassays
        for (BioAssay ba : output.getBioAssays()) {
            String newName = ba.getName() + " " + pipeline.getName();
            ba.setName(newName);
        }
    }
    
    
    /**
     * Create new bioassay of same type as given bioassay
     * and copy some of the properties.
     * @param bioAssay A bioassay
     * @return A new bioassay of same type as given bioassay
     * and copy some of the properties
     */
    private BioAssay clone(final BioAssay bioAssay) {
        BioAssay newBa = null;
        if (bioAssay instanceof DataContainingBioAssay) {
            newBa = new DataContainingBioAssay(bioAssay.getName(),
                    bioAssay.getOrganism());
        } else if (bioAssay instanceof DataSerializedBioAssay) {
            newBa = new DataSerializedBioAssay(bioAssay.getName(),
                    bioAssay.getOrganism());
        }
        newBa.setArray(bioAssay.getArray());
        return newBa;
    }
    
    
    /**
     * Add given chromosome array data to given bioassay.
     * This method handles the two cases where we are
     * keeping all data in memory or are serializing
     * data when not being used.
     * @param bioAssay A bioassay
     * @param chromosomeArrayData Chromosome array data
     */
    private void addChromosomeArrayData(final BioAssay bioAssay,
            final ChromosomeArrayData chromosomeArrayData) {
        if (bioAssay instanceof DataContainingBioAssay) {
            ((DataContainingBioAssay) bioAssay).put(chromosomeArrayData);
        } else if (bioAssay instanceof DataSerializedBioAssay) {
            this.dataFileManager.saveChromosomeArrayData(
                    (DataSerializedBioAssay) bioAssay, chromosomeArrayData);
        }
    }
    
    
    /**
     * Get chromosome array data from given bioassay and chromosome.
     * This method handles the two cases where we are
     * keeping all data in memory or are serializing
     * data when not being used.
     * @param bioAssay Bioassay
     * @param chromosome Chromosome
     * @return Chromosome array data
     */
    private ChromosomeArrayData getChromosomeArrayData(
            final BioAssay bioAssay, final short chromosome) {
        ChromosomeArrayData cad = null;
        if (bioAssay instanceof DataContainingBioAssay) {
            cad = ((DataContainingBioAssay)
                    bioAssay).getChromosomeArrayData(chromosome);
        } else if (bioAssay instanceof DataSerializedBioAssay) {
            cad = this.dataFileManager.loadChromosomeArrayData(
                    (DataSerializedBioAssay) bioAssay, chromosome);
        }
        return cad;
    }
}
