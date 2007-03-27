/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:09 $

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

package org.rti.webcgh.sandbox.load;

import gov.nih.nci.common.search.Directable;
import gov.nih.nci.common.search.SearchResult;
import gov.nih.nci.common.search.session.SecureSession;
import gov.nih.nci.common.search.session.SecureSessionFactory;
import gov.nih.nci.mageom.search.EnhancedSearchCriteriaFactory;
import gov.nih.nci.mageom.search.ArrayDesign.enhanced.
	ArrayDesignReporterSummary;
import gov.nih.nci.mageom.search.ArrayDesign.enhanced.
	ArrayDesignReporterSummarySearchCriteria;
import gov.nih.nci.mageom.search.ArrayDesign.enhanced.ReporterSummary;

/**
 * Class to test loading of
 * <code>ArrayDesignReportersSummary</code>
 * DTOs through MAGE-OM API.
 * @author dhall
 *
 */
public final class LoadArrayDesignReportersSummary {
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 */
	private LoadArrayDesignReportersSummary() {
		
	}
	
	
	//
	//     MAIN METHOD
	//
	
	/**
	 * Main method.
	 * @param args Command line args
	 */
	public static void main(final String[] args) {
		
		try {
			// Various parameters
			String user = "jlorenzcaarray";
			String password = "";
			String sessionUrl =
				"//cbioqa102.nci.nih.gov:9999/SecureSessionManager";
			
			// Instantiate, configure, and start session
			SecureSession sess = SecureSessionFactory.defaultSecureSession();
			((Directable) sess).direct(sessionUrl);
			sess.start(user, password);
			
			// Instantiate and configure search criteria
			String searchUrl =
				"//cbioqa102.nci.nih.gov:9999/SearchCriteriaHandler";
			ArrayDesignReporterSummarySearchCriteria criteria =
					EnhancedSearchCriteriaFactory.
					new_ARRAYDESIGNREPORTERSUMMARY_SC();
			((Directable) criteria).direct(searchUrl);
			criteria.setSessionId(sess.getSessionId());
			
			// Perform search
			SearchResult sr = criteria.search();
			ArrayDesignReporterSummary[] adrss = (ArrayDesignReporterSummary[])
				sr.getResultSet();
			System.out.println("Number of array designs: " + adrss.length);
			ArrayDesignReporterSummary summary = adrss[0];
			System.out.println(
					summary.getArrayDesignName() + ", "
					+ summary.getArrayDesignId()
			);
			ReporterSummary[] reporters = summary.getReportersSummary();
			System.out.println("Number of reporters: " + reporters.length);
			int n = 10;
			if (n > reporters.length) {
				n = reporters.length;
			}
			for (int i = 0; i < n; i++) {
				ReporterSummary rep = reporters[i];
				System.out.println(
					rep.getReporterName() + ", "
					+ rep.getChromosomeName() + ", "
					+ rep.getChromosomeValue() + ", "
					+ rep.getKbPositionName() + ", "
					+ rep.getKbPositionValue()
				);
			}
			sess.end();
		} catch (Throwable e) {
			while (e != null) {
				e.printStackTrace();
				e = e.getCause();
			}
		}
	}
}
