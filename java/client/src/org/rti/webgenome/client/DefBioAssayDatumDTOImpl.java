package org.rti.webgenome.client;


import org.rti.webgenome.util.SystemUtils;


/**
 * Implementation of <code>BioAssayDatumDTO</code> used primarily
 * for testing.
 * @author dhall
 *
 */
public class DefBioAssayDatumDTOImpl implements BioAssayDatumDTO {
	
	/** Serialized version ID. */
	private static final long serialVersionUID =
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/** Quantitation type. */
    private String quantitationType = null;
    
    /** Reporter. */
    private ReporterDTO reporter = null;
    
    /** Value. */
    private Double value = null;
    
    
    /**
     * Constructor.
     * @param value Value
     * @param quantitationType Quantitation type
     * @param reporter Reporter
     */
    public DefBioAssayDatumDTOImpl(final Double value, 
    		final String quantitationType, final ReporterDTO reporter) {
        this.value = value;
        this.quantitationType = quantitationType;
        this.reporter = reporter;
    }
    
    /**
     * Get quantitatio type.
     * @return Quantitation type.
     */
    public final String getQuantitationType() {
        return quantitationType;
    }

    
    /**
     * Get reporter.
     * @return reporter.
     */
    public final ReporterDTO getReporter() {
        return reporter;
    }

    
    /**
     * Get value.
     * @return Value
     */
    public final Double getValue() {
        return value;
    }

}
