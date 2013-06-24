/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:28 $


*/

package org.rti.webgenome.sandbox.load;

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
