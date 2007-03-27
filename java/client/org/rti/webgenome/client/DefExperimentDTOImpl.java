package org.rti.webgenome.client;


import java.util.ArrayList;
import java.util.Collection;

import org.rti.webcgh.util.SystemUtils;

/**
 * Implementation of <code>ExperimentDTO</code> used primarily for
 * testing.
 * @author dhall
 *
 */
public class DefExperimentDTOImpl implements ExperimentDTO {
    
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	/** Experiment ID. */
    private String experimentID = null;
    
    /** Bioassays. */
    private Collection<BioAssayDTO> bioAssays = new ArrayList<BioAssayDTO>();

    /**
     * Constructor.
     * @param experimentID experiment ID
     * @param bioAssayDTOArray Array of BioAssayDTO objects
     */
    public DefExperimentDTOImpl(final String experimentID,
    		final BioAssayDTO[] bioAssayDTOArray) {
        this.experimentID = experimentID;
        for (int i = 0; i < bioAssayDTOArray.length; i++) {
            this.bioAssays.add(bioAssayDTOArray[i]);
        }
    }
    
    /**
     * Get experimentID.
     * @return String experimentID
     */
    public final String getExperimentID() {
        return experimentID;
    }

    /**
     * Get BioAssays.
     * @return BioAssayDTO[] array of BioAssayDTO's
     */
    public final BioAssayDTO[] getBioAssays() {
        BioAssayDTO[] dtos = new BioAssayDTO[0];
        dtos = (BioAssayDTO[]) this.bioAssays.toArray(dtos);
        return dtos;
    }
    
    /**
     * Add a BioAssayDTO to the array.
     * @param dto Bioassay data transfer object
     */
    public final void add(final BioAssayDTO dto) {
        this.bioAssays.add(dto);
    }

}
