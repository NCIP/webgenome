/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-27 15:47:15 $


*/

package org.rti.webgenome.service.dao.hibernate;

import junit.framework.TestCase;

import org.rti.webgenome.analysis.SimpleUserConfigurableProperty;
import org.rti.webgenome.analysis.UserConfigurablePropertyWithOptions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Tester for
 * {@link org.rti.webgenome.service.dao.hibernate.
 * HibernateUserConfigurableProperty}.
 * @author dhall
 *
 */
public class HibernateUserConfigurablePropertyTester extends TestCase {

	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		
		// Get bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateUserConfigurablePropertyDao dao =
			(HibernateUserConfigurablePropertyDao)
			ctx.getBean("userConfigurablePropertyDao");
		
		// Instantiate test objects
		UserConfigurablePropertyWithOptions p1 =
			new UserConfigurablePropertyWithOptions("name1",
					"displayName1", "value1");
		p1.addOption("code1", "displayName1");
		p1.addOption("code2", "displayName2");
		SimpleUserConfigurableProperty p2 =
			new SimpleUserConfigurableProperty("name2",
					"displayName2", "value2");
		
		// Perform tests
		dao.save(p1);
		dao.save(p2);
		dao.delete(p1);
		dao.delete(p2);
	}
}
