/*
$Revision: 1.2 $
$Date: 2007-04-13 02:52:12 $


*/

package org.rti.webgenome.service.dao.hibernate;
import junit.framework.TestCase;

import org.rti.webgenome.domain.Cytoband;
import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Tester for <code>HibernateCytologicalMapDao</code>.
 * @author dhall
 *
 */
public final class HibernateCytologicalMapDaoTester extends TestCase {

	
	/**
	 * Test all methods.
	 */
	public void testAll() {
		
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateCytologicalMapDao dao = (HibernateCytologicalMapDao)
			ctx.getBean("cytologicalMapDao");
		HibernateOrganismDao oDao = (HibernateOrganismDao)
			ctx.getBean("organismDao");
		
		// Instantiate test object
		Organism org = oDao.loadDefault();
		short chromosome = (short) 1;
		CytologicalMap map = new CytologicalMap(chromosome,
				100, 200, org);
		map.addCytoband(new Cytoband("1", 1, 10, "stain1"));
		map.addCytoband(new Cytoband("2", 300, 400, "stain2"));
		
		// Save
		dao.save(map);
		
		// Retrieve
		CytologicalMap map2 = dao.load(org, chromosome);
		assertNotNull(map2);
		//assertEquals(2, map2.getCytobands().size());
		
		// Delete
		dao.delete(map);
		map2 = dao.load(org, chromosome);
		//assertNull(map2);
	}
}
