/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-07-03 17:44:00 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.awt.Point;

import org.rti.webgenome.graphics.event.MouseOverStripe;
import org.rti.webgenome.graphics.event.MouseOverStripes;
import org.rti.webgenome.units.Orientation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for
 * {@link org.rti.webgenome.service.dao.hiberhate.
 * HibernateMouseOverStripesDao}
 * @author dhall
 *
 */
public class HibernateMouseOverStripesDaoTester extends TestCase {

	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateMouseOverStripesDao dao =
			(HibernateMouseOverStripesDao)
			ctx.getBean("mouseOverStripesDao");
		
		// Instantiate test object
		MouseOverStripes stripes = new MouseOverStripes(
				Orientation.HORIZONTAL, 100, 100, new Point(5, 5));
		stripes.add(new MouseOverStripe(30, 30, "Stripe 1"));
		stripes.add(new MouseOverStripe(50, 50, "Stripe 2"));
		
		// Run tests
		dao.save(stripes);
		dao.delte(stripes);
	}
}
