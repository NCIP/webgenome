/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-04-13 02:52:12 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.dao.OrganismDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>HibernateOrganismDao</code>.
 * @author dhall
 *
 */
public final class HibernateOrganismDaoTester extends TestCase {
    
    /** Test object. */
    private OrganismDao dao = null;
    
    /**
     * Default constructor.
     *
     */
    public HibernateOrganismDaoTester() {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
        this.dao = (OrganismDao) ctx.getBean("organismDao");
    }
    
    /**
     * Test save(), delete(), and load(String, String).
     *
     */
    public void testSaveDeleteAndLoad() {
        String genus = "Mus";
        String species = "musculus";
        Organism org = new Organism(genus, species);
        this.dao.save(org);
        Organism org2 = dao.load(genus, species);
        assertNotNull(org2);
        assertEquals(genus, org2.getGenus());
        assertEquals(species, org2.getSpecies());
        org2 = dao.load(org.getId());
        assertNotNull(org2);
        assertEquals(genus, org2.getGenus());
        assertEquals(species, org2.getSpecies());
        this.dao.delete(org2);
    }
    
    
    /**
     * Test loadDefault() method.
     *
     */
    public void testLoadDefault() {
        assertNotNull(this.dao.loadDefault());
    }

}
