/*
$Revision: 1.1 $
$Date: 2007-10-10 15:08:41 $


*/

package org.rti.webgenome.service.session;

import org.rti.webgenome.domain.Principal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for
 * {@link RembrandtAuthenticator}.
 * @author dhall
 *
 */
public class RembrandtAuthenticatorTester extends TestCase {
	
	/** Test user name that should authenticate. */
	private static final String GOOD_USER = "HallDav";
	
	/** Test password that should authenticate. */
	private static final String GOOD_PASSWORD = "suR4sup!";

	/**
	 * Test {@code login} method.
	 */
	public void testLogin() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
    		"org/rti/webgenome/service/session/beans.xml");
		RembrandtAuthenticator ra = (RembrandtAuthenticator)
			ctx.getBean("rembrandtAuthenticator");
		Principal p = ra.login(GOOD_USER, GOOD_PASSWORD);
		assertNotNull(p);
	}
}
