/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:35 $


*/

package org.rti.webgenome.service.client;

import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.client.BioAssayDTO;
import org.rti.webgenome.client.BioAssayDTOGenerator;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.ExperimentDTO;
import org.rti.webgenome.client.ExperimentDTOGenerator;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.DataContainingBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;

/**
 * Implementation of <code>ClientDataService</code>
 * for testing.  All data returned are randomly
 * generated values and positions.
 * @author dhall
 *
 */
public class SimulatedDataClientDataService implements ClientDataService {
	
	/** Gap between generated reporters in base pairs. */
	private static final long GAP = 1000000;
	
	/** Number of bioassays per experiment generated. */
	private static final int NUM_BIO_ASSAYS = 4;
	
	/** Experiment data transfer object generator. */
	private final ExperimentDTOGenerator experimentDTOGenerator =
		new ExperimentDTOGenerator(GAP, NUM_BIO_ASSAYS);
	
	/** Bioassay data transfer object generator. */
	private final BioAssayDTOGenerator bioAssayDtoGenerator =
		new BioAssayDTOGenerator(GAP);
	
	/** Quantitation type. */
	private QuantitationType quantitationType = null;
	
	
	/**
	 * Set quantitation type.
	 * @param quantitationTypeId Quantitation type ID.  Must match
	 * one of the IDs in org.rti.webcgh.domain.QuantitationType.
	 */
	public final void setQuantitationType(final String quantitationTypeId) {
		this.quantitationType =
			QuantitationType.getQuantitationType(quantitationTypeId);
		if (this.quantitationType == null) {
			throw new IllegalArgumentException("Unknown quantitation type '"
					+ quantitationTypeId + "'");
		}
	}

	/**
	 * Generate random data with given experiment IDs and constraints.
	 * @param constraints Query constraints
	 * @param experimentIds Experiment identifiers
	 * @param clientID Application client ID
	 * @return Experiments from application client
	 */
    public final Collection<Experiment> getClientData(
    		final BioAssayDataConstraints[] constraints,
    		final String[] experimentIds, final String clientID) {
    	Collection<Experiment> experiments = new ArrayList<Experiment>();
    	for (int i = 0; i < experimentIds.length; i++) {
    		ExperimentDTO dto = this.experimentDTOGenerator.newExperimentDTO(
    				experimentIds[i], constraints);
    		Experiment exp = new Experiment(dto, constraints);
    		experiments.add(exp);
    	}
    	return experiments;
    }
    
    
    /**
     * Add data to given experiments.
     * @param experiments Experiments
     * @param constraints Query constraints
     * @param clientId Application client ID
     */
    public final void addData(final Collection<Experiment> experiments,
    		final BioAssayDataConstraints[] constraints,
    		final String clientId) {
    	for (Experiment exp : experiments) {
    		for (BioAssay ba : exp.getBioAssays()) {
    			if (!(ba instanceof DataContainingBioAssay)) {
    				throw new IllegalArgumentException(
    					"Expecting BioAssay of type DataContainingBioAssay");
    			}
    			BioAssayDTO dto =
    				this.bioAssayDtoGenerator.newBioAssayDTO(ba.getName(),
    						constraints);
    			((DataContainingBioAssay) ba).addData(dto);
    		}
    	}
    }
}
