/*
$Revision: 1.1 $
$Date: 2007-07-09 22:29:43 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Experiment.BioAssayDataConstraintsWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for
 * {@link org.rti.webgenome.service.dao.hibernate.
 * HibernateBioAssayDataConstraintsWrapperDao}.
 * @author dhall
 *
 */
public class HibernateBioAssayDataConstraintsWrapperDaoTester
extends TestCase {

	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateBioAssayDataConstraintsWrapperDao dao =
			(HibernateBioAssayDataConstraintsWrapperDao)
			ctx.getBean("bioAssayDataConstraintsDao");
		
		// Instantiate test objects
		BioAssayDataConstraints c = new BioAssayDataConstraints();
		c.setChromosome("1");
		c.setPositions(new Long(100), new Long(200));
		c.setQuantitationType("quantitationType1");
		BioAssayDataConstraintsWrapper w =
			new Experiment.BioAssayDataConstraintsWrapper(c);
		
		// Run tests
		dao.save(w);
		dao.delete(w);
	}
}
