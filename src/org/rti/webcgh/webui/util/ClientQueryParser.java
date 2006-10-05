/*
$Revision: 1.2 $
$Date: 2006-10-05 22:09:05 $

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

package org.rti.webcgh.webui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.rti.webcgh.core.InvalidClientQueryParametersException;
import org.rti.webcgh.util.GenomeIntervalCoder;
import org.rti.webcgh.util.GenomeIntervalFormatException;
import org.rti.webgenome.client.BioAssayDataConstraints;

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
     * Creates bioassay data constraints from query parameter
     * value.
     * @param intervals Value of INTERVALS_PARAM_NAME query
     * parameter
     * @param qType Quantitation type
     * @return Bioassay data constraints
     * @throws InvalidClientQueryParametersException if
     * valid bioassay data constraints cannot be parsed.
     */
    private static List<BioAssayDataConstraints> parseIntervals(
    		final String intervals, final String qType)
    throws InvalidClientQueryParametersException {
    	List<BioAssayDataConstraints> constraints =
    		new ArrayList<BioAssayDataConstraints>();
        StringTokenizer intervalTokenizer =
        	new StringTokenizer(intervals, ",");
        while (intervalTokenizer.hasMoreTokens()) {
            String interval = intervalTokenizer.nextToken();
            short chromosome = (short) -1;
            long start = -1, end = -2;
            try {
				chromosome =
					GenomeIntervalCoder.parseChromosome(interval);
				start = GenomeIntervalCoder.parseStart(interval);
				end = GenomeIntervalCoder.parseEnd(interval);
			} catch (GenomeIntervalFormatException e) {
				throw new InvalidClientQueryParametersException(
						"Invalid genome interval: " + interval);
			}
            if (chromosome < (short) 0 || start < 0
            		|| end < 0) {
            	throw new InvalidClientQueryParametersException(
            			"Invalid genome interval: " + interval);
            }

            //create constraint using info parsed from the parameters
            BioAssayDataConstraints constraint =
            	new BioAssayDataConstraints();
            constraint.setChromosome(String.valueOf(chromosome));
            constraint.setPositions(start, end);
            constraint.setQuantitationType(qType);
            constraints.add(constraint);
        }
    	
    	return constraints;
    }

}
