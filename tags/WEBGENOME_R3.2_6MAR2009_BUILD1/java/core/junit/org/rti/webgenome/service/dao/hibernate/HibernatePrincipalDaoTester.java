/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-10-10 17:47:02 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.domain.Principal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>HibernatePrincipalDao</code>.
 * @author dhall
 *
 */
public final class HibernatePrincipalDaoTester extends TestCase {

	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		String name = "Name";
		String password = "Password";
		String domain = "Domain";
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernatePrincipalDao dao = (HibernatePrincipalDao)
			ctx.getBean("principalDao");
		Principal p1 = new Principal(name, password, domain);
		dao.save(p1);
		Principal p2 = dao.load(name);
		assertEquals(p1.getPassword(), p2.getPassword());
		p2 = dao.load(name, password, domain);
		assertNotNull(p2);
		dao.delete(p1);
		p2 = dao.load(name);
		assertNull(p2);
	}
}
