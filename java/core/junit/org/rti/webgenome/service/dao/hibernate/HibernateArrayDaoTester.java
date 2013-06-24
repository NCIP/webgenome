/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.domain.Array;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for {@link org.rti.webgenome.service.dao.HibernateArrayDao}.
 * @author dhall
 *
 */
public class HibernateArrayDaoTester extends TestCase {
	
	/**
	 * Test all methods.
	 *
	 */
	public void testAllMethods() {
		
		// Get bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateArrayDao dao =
			(HibernateArrayDao)
			ctx.getBean("arrayDao");
		
		// Instantiate test objects
		Array array = new Array();
		array.setName("array");
		array.setChromosomeReportersFileName((short) 1, "file1");
		array.setChromosomeReportersFileName((short) 2, "file2");
		
		// Run tests
		dao.saveOrUpdate(array);
		dao.delete(array);
	}

}
