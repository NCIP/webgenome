/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-04-13 02:52:13 $


*/

package org.rti.webgenome.service.session;

import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.service.session.AccountAlreadyExistsException;
import org.rti.webgenome.service.session.SecurityMgrImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>SecurityMgrImpl</code>.
 * @author dhall
 *
 */
public final class SecurityMgrImplTester extends TestCase {

	/**
	 * Test all methods.
	 * @throws Exception if something bad happens.
	 */
	public void testAllMethods() throws Exception {
		String name = "Name";
		String password = "Password";
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        	"org/rti/webgenome/service/session/beans.xml");
		SecurityMgrImpl sm = (SecurityMgrImpl) ctx.getBean("securityMgr");
		Principal p1 = sm.newAccount(name, password);
		assertNotNull(p1);
		try {
			sm.newAccount(name, password);
			fail();
		} catch (AccountAlreadyExistsException e) {
			assertTrue(true);
		}
		sm.delete(p1);
	}
}
