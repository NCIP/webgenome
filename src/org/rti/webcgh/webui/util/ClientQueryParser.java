/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/util/ClientQueryParser.java,v $
$Revision: 1.1 $
$Date: 2006-10-05 03:59:41 $

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

package org.rti.webcgh.webui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.*;

import org.rti.webcgh.core.InvalidClientQueryParametersException;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.QuantitationTypes;

/**
 * Parses query parameters given to webGenome by client
 */
public class ClientQueryParser {

	/**
	 * Getter method for the experimentIDs
	 * @return String[] Returns a String array of IDs of the experiments requested
	 * @throws InvalidClientQueryParametersException
	 */
	public static String[] getExperimentIds(HttpServletRequest request) 
		throws InvalidClientQueryParametersException {
        String clientID = request.getParameter("clientID");
        String exptIDs = request.getParameter("exptIDs");
        String intervals = request.getParameter("intervals");
        
        if (intervals == null || exptIDs == null || clientID == null) {
            request.setAttribute("DATA", "Invalid Query String");
            throw new InvalidClientQueryParametersException("Invalid query string to client");
        }
        
        List experimentIDs = ClientQueryParser.parseExptIDs(exptIDs);
        
        String[] exptIDArray = new String[0];
        exptIDArray = (String[]) experimentIDs.toArray(exptIDArray);
        return exptIDArray;
	}
	
    /**
     * Returns BioAssayConstraints object based on query string from request object
     * @param request
     * @return BioAssayDataConstraints constraints based on query string from request object
     * @throws InvalidClientQueryParametersException
     */
    public static BioAssayDataConstraints[] getBioAssayDataConstraints(HttpServletRequest request)
    	throws InvalidClientQueryParametersException {
        String clientID = request.getParameter("clientID");
        String exptIDs = request.getParameter("exptIDs");
        String intervals = request.getParameter("intervals");
        
        if (intervals == null || exptIDs == null || clientID == null) {
            request.setAttribute("DATA", "Invalid Query String");
            throw new InvalidClientQueryParametersException("Invalid query string to client");
        }
        
        List constraints = ClientQueryParser.parseIntervals(intervals);

        BioAssayDataConstraints[] constraintsArray = new BioAssayDataConstraints[0];
        constraintsArray = (BioAssayDataConstraints[]) constraints.toArray(constraintsArray);
        return constraintsArray;
    }
    
    
    /**
     * Parses string containing experiment IDs into Strings
     * @param exptIDs A comma seprated list of experiment IDs 
     * @throws InvalidClientQueryParametersException 
     */
    private static List parseExptIDs(String exptIDs) throws InvalidClientQueryParametersException {
    	List experimentIDs = new ArrayList();
    	try {
    		StringTokenizer exptTokenizer = new StringTokenizer(exptIDs, ",");
    		while(exptTokenizer.hasMoreTokens()) {
    			String exptID = exptTokenizer.nextToken();
    			experimentIDs.add(exptID);
    		}
    	}
    	catch (Exception e) {
    		throw new InvalidClientQueryParametersException("Invalid query string to client", e);
    	}
    	return experimentIDs;
    }
    
    /**
     * Parses string containing intervals into GenomeInterval objects
     * @param intervals A comma seprated list of intervals
     * @throws InvalidClientQueryParametersException 
     */
    private static List parseIntervals(String intervals) throws InvalidClientQueryParametersException {
    	List constraints = new ArrayList();
    	try {
	        StringTokenizer intervalTokenizer = new StringTokenizer(intervals, ",");
	        while(intervalTokenizer.hasMoreTokens()) {
	        	//parse chromosome, start location, and end location from the string
	            String interval = intervalTokenizer.nextToken();
	            int indexOfColon = interval.indexOf(':');
	            String chromNumStr = interval.substring(0,indexOfColon);
	            int indexOfDash = interval.indexOf('-');
	            String start = interval.substring(indexOfColon+1, indexOfDash);
	            String end = interval.substring(indexOfDash+1, interval.length());
	            Long startNum = new Long(start);
	            Long endNum = new Long(end);
	
	            //create constraint using info parsed from the parameters
	            BioAssayDataConstraints constraint = new BioAssayDataConstraints();
	            constraint.setChromosome(chromNumStr);
	            constraint.setPositions(startNum, endNum);
	            constraint.setQuantitationType(QuantitationTypes.COPY_NUMBER);
	            
	            constraints.add(constraint);
	        }
    	}
    	catch (Exception e) {
    		throw new InvalidClientQueryParametersException("Invalid query string to client", e);
    	}
    	
    	return constraints;
    }

}