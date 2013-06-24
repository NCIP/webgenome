/*
$Revision: 1.1 $
$Date: 2007-06-25 18:41:54 $


*/

package org.rti.webgenome.service.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for {@link IdGenerator}.
 * @author dhall
 *
 */
public class IdGeneratorTester extends TestCase {

	/**
	 * Test {@code nextId()} method.
	 */
	public void testNextId() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"org/rti/webgenome/service/util/beans.xml");
		IdGenerator gen = (IdGenerator) ctx.getBean("experimentIdGenerator");
		assertEquals(1, gen.nextId().intValue());
	}
}
