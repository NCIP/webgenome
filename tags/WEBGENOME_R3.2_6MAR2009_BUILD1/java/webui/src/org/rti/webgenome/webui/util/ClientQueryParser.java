/*
$Revision: 1.4 $
$Date: 2007-09-29 05:24:19 $


*/

package org.rti.webgenome.webui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.core.InvalidClientQueryParametersException;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.GenomeIntervalFormatException;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.units.BpUnits;

/**
 * Parses query parameters given to webGenome by client.
 */
public final class ClientQueryParser {
	
	/** Experiment IDs query parameters name. */
	private static final String EXP_IDS_PARAM_NAME = "exptIDs";
	
	/** Genome intervals query parameter name. */
	private static final String INTERVALS_PARAM_NAME = "intervals";
	
	/** Quantitation type parameter name. */
	private static final String QTYPE_PARAM_NAME = "qType";
	
	/**
	 * Constructor.
	 */
	private ClientQueryParser() {
		
	}

	/**
	 * Get experimentIDs.
	 * @param request Servlet request
	 * @return IDs of the experiments requested
	 * @throws InvalidClientQueryParametersException
	 * if experiment IDs are not coded propertly
	 * in query parameters.
	 */
	public static String[] getExperimentIds(
			final HttpServletRequest request) 
		throws InvalidClientQueryParametersException {
        String exptIDs = request.getParameter(EXP_IDS_PARAM_NAME);
        if (exptIDs == null) {
        	throw new InvalidClientQueryParametersException(
        			"Missing HTTP query parameter: "
        			+ EXP_IDS_PARAM_NAME);
        }
        List<String> experimentIDs = new ArrayList<String>();
        StringTokenizer tok = new StringTokenizer(exptIDs, ",");
        if (!tok.hasMoreTokens()) {
        	throw new InvalidClientQueryParametersException(
        			"No experiment IDs specified");
        }
        while (tok.hasMoreTokens()) {
        	String id = tok.nextToken();
        	if (id.length() < 1) {
        		throw new InvalidClientQueryParametersException(
        				"Experiment IDs cannot be null string");
        	}
        	experimentIDs.add(id);
        }
        String[] exptIDArray = new String[0];
        exptIDArray = (String[]) experimentIDs.toArray(exptIDArray);
        return exptIDArray;
	}
	
    /**
     * Returns BioAssayConstraints object based on query string
     * from request object.
     * @param request Servlet request
     * @return BioAssayDataConstraints constraints based
     * on query string from request object
     * @throws InvalidClientQueryParametersException if
     * bioassay data constraints cannot be correctely parsed
     * from query parameters.
     */
    public static BioAssayDataConstraints[] getBioAssayDataConstraints(
    		final HttpServletRequest request)
    	throws InvalidClientQueryParametersException {
    	String qType = request.getParameter(QTYPE_PARAM_NAME);
    	if (qType == null) {
    		throw new InvalidClientQueryParametersException(
    				"Missing HTTP query parameter: "
    				+ QTYPE_PARAM_NAME);
    	} else if (!validQuantitationType(qType)) {
    		throw new InvalidClientQueryParametersException(
    				"Invalid quantitation type");
    	}
    	String intervals = request.getParameter(INTERVALS_PARAM_NAME);
    	if (intervals == null) {
    		throw new InvalidClientQueryParametersException(
    				"Missing HTTP query parameter: "
    				+ INTERVALS_PARAM_NAME);
    	}
        List<BioAssayDataConstraints> constraints =
        	ClientQueryParser.parseIntervals(intervals, qType);
        BioAssayDataConstraints[] constraintsArray =
        	new BioAssayDataConstraints[0];
        constraintsArray = (BioAssayDataConstraints[])
        	constraints.toArray(constraintsArray);
        return constraintsArray;
    }
    
    
    /**
     * Is given quantitation type valid?
     * @param qType A quantitation type
     * @return T/F
     */
    private static boolean validQuantitationType(final String qType) {
    	return QuantitationType.getQuantitationType(qType) != null;
    }
    
    
    /**
     * Creates bioassay data constraints from query parameter
     * value.
     * @param encodedIntervals Value of INTERVALS_PARAM_NAME query
     * parameter
     * @param qType Quantitation type
     * @return Bioassay data constraints
     * @throws InvalidClientQueryParametersException if
     * valid bioassay data constraints cannot be parsed.
     */
    private static List<BioAssayDataConstraints> parseIntervals(
    		final String encodedIntervals, final String qType)
    throws InvalidClientQueryParametersException {
    	List<BioAssayDataConstraints> constraints =
    		new ArrayList<BioAssayDataConstraints>();
        Collection<GenomeInterval> intervals = null;
        try {
			intervals = GenomeInterval.decode(encodedIntervals,
					BpUnits.BP);
		} catch (GenomeIntervalFormatException e) {
			throw new InvalidClientQueryParametersException(
					"Invalid genome intervals: " + encodedIntervals, e);
		}
        for (GenomeInterval interval : intervals) {
            if (interval.getChromosome() < (short) 0
            		|| interval.getStartLocation() < 0
            		|| interval.getEndLocation() < 0) {
            	throw new InvalidClientQueryParametersException(
            			"Invalid genome interval: " + interval);
            }

            //create constraint using info parsed from the parameters
            BioAssayDataConstraints constraint =
            	new BioAssayDataConstraints();
            constraint.setChromosome(String.valueOf(interval.getChromosome()));
            constraint.setPositions(interval.getStartLocation(),
            		interval.getEndLocation());
            constraint.setQuantitationType(qType);
            constraints.add(constraint);
        }
    	
    	return constraints;
    }

}
