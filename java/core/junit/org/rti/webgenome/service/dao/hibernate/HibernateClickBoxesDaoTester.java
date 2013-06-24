/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-29 21:47:51 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.awt.Point;

import org.rti.webgenome.graphics.io.ClickBoxes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for {@link HibernateClickBoxesDao}.
 * @author dhall
 *
 */
public class HibernateClickBoxesDaoTester extends TestCase {
	
	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateClickBoxesDao dao =
			(HibernateClickBoxesDao)
			ctx.getBean("clickBoxesDao");
		
		// Instantiate test object and save
		ClickBoxes cb = new ClickBoxes(500, 500, 100, 100, new Point(10, 10));
		cb.addClickBoxText("Number 1", 50, 50);
		cb.addClickBoxText("Number, 2", 350, 350);
		cb.addClickBoxText("Number 3", 50, 420);
		dao.save(cb);
		dao.delete(cb);
	}
}
