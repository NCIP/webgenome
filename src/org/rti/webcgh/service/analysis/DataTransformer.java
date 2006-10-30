/*
$Revision: 1.7 $
$Date: 2006-10-30 01:58:06 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.service.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.rti.webcgh.analysis.AnalyticException;
import org.rti.webcgh.analysis.AnalyticOperation;
import org.rti.webcgh.analysis.AnalyticPipeline;
import org.rti.webcgh.analysis.MinimumCommonAlteredRegionOperation;
import org.rti.webcgh.analysis.MultiExperimentToNonArrayDataAnalyticOperation;
import org.rti.webcgh.analysis.ListToScalarAnalyticOperation;
import org.rti.webcgh.analysis.ScalarToScalarAnalyticOperation;
import org.rti.webcgh.analysis.StatefulBioAssayAnalyticOperation;
import org.rti.webcgh.analysis.StatefulExperimentAnalyticOperation;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.DataContainingBioAssay;
import org.rti.webcgh.domain.DataSerializedBioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.service.util.ChromosomeArrayDataIterator;


/**
 * Abstract base class for classes that
 * Manage the transformation of data
 * through one or more analytic operations.
 * Class usese template pattern.
 * @author dhall
 *
 */
public abstract class DataTransformer {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(DataTransformer.class);

    
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
            new Experiment(experimentName, input.getOrganism(),
            		input.getQuantitationType());
        this.perform(input, output, operation);
        LOGGER.info("Completed operation");
        return output;
    }
    
    
    /**
     * Perform given analytic operation on given input data
     * writing the results in given output experiment.
     * @param input Input data
     * @param output Output experiment
     * @param operation Opeation to perform
     * @throws AnalyticException if a computation error occurs
     */
    private void perform(final Experiment input, final Experiment output,
            final AnalyticOperation operation)
    throws AnalyticException {
        if (operation instanceof ScalarToScalarAnalyticOperation) {
            this.perform(input, output,
                    (ScalarToScalarAnalyticOperation) operation);
        } else if (operation instanceof ListToScalarAnalyticOperation) {
            this.perform(input, output,
                    (ListToScalarAnalyticOperation) operation);
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
     * @throws AnalyticException if a computation error occurs
     */
    private void perform(final Experiment input, final Experiment output,
            final ScalarToScalarAnalyticOperation operation)
        throws AnalyticException {
        if (operation instanceof StatefulExperimentAnalyticOperation) {
            ((StatefulExperimentAnalyticOperation) operation).resetState();
        }
        for (BioAssay ba : input.getBioAssays()) {
            if (operation instanceof StatefulBioAssayAnalyticOperation) {
                ((StatefulBioAssayAnalyticOperation) operation).resetState();
            }
            BioAssay newBa = this.clone(ba);
            newBa.setParentBioAssayId(ba.getId());
            output.add(newBa);
            ChromosomeArrayDataIterator it =
            	this.getChromosomeArrayDataIterator(ba);
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
     * @throws AnalyticException if a computation error occurs
     */
    private void perform(final Experiment input, final Experiment output,
            final ListToScalarAnalyticOperation operation)
        throws AnalyticException {
        if (input.getBioAssays().size() < 1) {
            throw new IllegalArgumentException(
                    "Cannot perform operation on empty data set");
        }
        BioAssay bioAssay = this.clone(input.getBioAssays().iterator().next());
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
     * Perform given analytic operation on input data writing
     * results to output.  This method pools chromosome array
     * data from the same chromosome across all bioassays
     * and performs operation on these pools.
     * @param input Input data
     * @param operation Operation to perform
     * @return Experiment
     * @throws AnalyticException if a computation error occurs
     */
    public final Experiment perform(final Collection<Experiment> input,
            final MultiExperimentToNonArrayDataAnalyticOperation operation)
        throws AnalyticException {
        if (input.size() < 1) {
            throw new IllegalArgumentException(
                    "Cannot perform operation on empty data set");
        }
        QuantitationType qt = null;
        for (Experiment exp : input) {
        	if (qt == null) {
        		qt = exp.getQuantitationType();
        	} else {
        		if (qt != exp.getQuantitationType()) {
        			throw new IllegalArgumentException(
        					"Cannot perform operation on mixed data types: '"
        					+ qt.getName() + "' and '"
        					+ exp.getQuantitationType().getName() + "'");
        		}
        	}
        }
        if (operation instanceof MinimumCommonAlteredRegionOperation) {
        	qt = Experiment.getQuantitationType(input);
        	((MinimumCommonAlteredRegionOperation) operation).
        		setQuantitationType(qt);
        }
        boolean inMemory = Experiment.dataInMemory(input);
        Experiment output = new Experiment(operation.getName());
        output.setQuantitationType(Experiment.getQuantitationType(input));
        output.setTerminal(true);
        output.setOrganism(Experiment.getOrganism(input));
        List<BioAssay> bioAssays = new ArrayList<BioAssay>();
        for (Short chromosome : Experiment.chromosomes(input)) {
            List<ChromosomeArrayData> inCads =
            	new ArrayList<ChromosomeArrayData>();
            for (Experiment exp : input) {
            	for (BioAssay ba : exp.getBioAssays()) {
            		inCads.add(this.getChromosomeArrayData(ba, chromosome));
            	}
            }
            List<ChromosomeArrayData> outCads = operation.perform(inCads);
            if (bioAssays.size() < 1) {
            	for (ChromosomeArrayData cad : outCads) {
            		BioAssay ba = null;
            		if (inMemory) {
            			ba = new DataContainingBioAssay();
            		} else {
            			ba = new DataSerializedBioAssay();
            		}
            		ba.setName(operation.getName(cad));
            		bioAssays.add(ba);
            		output.add(ba);
            	}
            }
            for (int i = 0; i < outCads.size(); i++) {
            	this.addChromosomeArrayData(bioAssays.get(i), outCads.get(i));
            }
        }
        return output;
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
                this.perform(intermediateIn, output, op);
            } else {
                intermediateOut = this.perform(intermediateIn, op);
            }
            if (intermediateIn != input) {
                this.finalize(intermediateIn);
            }
            intermediateIn = intermediateOut;
        }
        
        // Rename bioassays
        for (BioAssay ba : output.getBioAssays()) {
            String newName = ba.getName() + " " + pipeline.getName();
            ba.setName(newName);
        }
    }
    
    
    // ======================================
    //      Abstract template methods
    // ======================================
    
    /**
     * Create new bioassay of same type as given bioassay
     * and copy some of the properties.
     * @param bioAssay A bioassay
     * @return A new bioassay of same type as given bioassay
     * and copy some of the properties
     */
    protected abstract BioAssay clone(BioAssay bioAssay);
    
    
    /**
     * Add given chromosome array data to given bioassay.
     * @param bioAssay A bioassay
     * @param chromosomeArrayData Chromosome array data
     */
    protected abstract void addChromosomeArrayData(
    		BioAssay bioAssay,
            ChromosomeArrayData chromosomeArrayData);
    
    
    /**
     * Get chromosome array data from given bioassay and chromosome.
     * @param bioAssay Bioassay
     * @param chromosome Chromosome
     * @return Chromosome array data
     */
    protected abstract ChromosomeArrayData getChromosomeArrayData(
            BioAssay bioAssay, short chromosome);
    
    /**
     * Get a chromosome data iterator.
     * @param bioAssay Bioassay containing data
     * @return Chromosome data iterator
     */
    protected abstract ChromosomeArrayDataIterator
    	getChromosomeArrayDataIterator(BioAssay bioAssay);
    
    /**
     * Finalize experiment before it sent to garbage collector.
     * @param experiment Experiment to finalize
     */
    protected abstract void finalize(Experiment experiment);
}
