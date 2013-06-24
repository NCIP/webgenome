/*
$Revision: 1.1 $
$Date: 2007-07-16 16:25:14 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.graphics.util.ColorChooser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for
 * {@link org.rti.webgenome.service.dao.hibernate.HibernateColorChooserDao}.
 * @author dhall
 *
 */
public class HibernateColorChooserDaoTester extends TestCase {

	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateColorChooserDao dao =
			(HibernateColorChooserDao)
			ctx.getBean("colorChooserDao");
		
		// Instantiate test object
		ColorChooser cc = new ColorChooser();
		
		// Run tests
		dao.saveOrUpdate(cc);
	}
}
