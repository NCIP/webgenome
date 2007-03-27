/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:09 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.service.dao.hibernate;


import java.util.Iterator;

import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.domain.DataSerializedBioAssay;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.service.dao.hibernate.HibernateShoppingCartDao;
import org.rti.webcgh.service.dao.hibernate.HibernateOrganismDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>ShoppingCart, Experiment, DataSerializedBioArray</code>.
 */
public final class HibernateShoppingCartTester extends TestCase {
    
    /**
     * Test all methods.
     */
    public void testAllMethods() {
    	
    	// Setup for tests
    	ApplicationContext ctx = new ClassPathXmlApplicationContext(
    		"org/rti/webcgh/service/dao/hibernate/unit_test/beans.xml");
    	HibernateShoppingCartDao cartDao = (HibernateShoppingCartDao)
    		ctx.getBean("shoppingCartDao");
    	HibernateOrganismDao orgDao = (HibernateOrganismDao) 
    	    ctx.getBean("organismDao");  
    	String cartUser = "cartest";
    	String expName1 = "expone";
    	String expName2 = "exptwo";
    	String expName3 = "expthree";
    	
    	
    	// Create 1 ShoppingCart, add 2 Experiments, add 1 BioAssay to exp1
    	// Test 0 - save
        ShoppingCart c1 = new ShoppingCart(cartUser);
        Experiment exp1 = new Experiment(expName1);
    	Experiment exp2 = new Experiment(expName2);
    	exp1.setId((long) 1001);
    	exp2.setId((long) 1002);
    	
    	// Code below commented out by DHALL
//    	exp1.setShoppingCart(c1);
//    	exp2.setShoppingCart(c1);
    	c1.add(exp1);
    	c1.add(exp2);
    	DataSerializedBioAssay assay1 = new DataSerializedBioAssay();
    	Organism o1 = orgDao.load("Homo", "sapiens");
    	assay1.setOrganism(o1);
    	assay1.setExperiment(exp1);
    	exp1.add(assay1);
    	cartDao.save(c1);


    	// Test 1 - load 
    	// load ShoppingCart, check Experiments
    	ShoppingCart c2 = cartDao.load(cartUser);
    	assertNotNull(c2);
    	assertEquals(c1.getUserName(), c2.getUserName());
    	assertEquals(c1.getUserName(), cartUser);
    	assertEquals(2, c2.getExperiments().size());
    	
    	// check BioAssay
    	Iterator<Experiment> it = c2.getExperiments().iterator();
    	Experiment expTmp = null;
    	while (it.hasNext()) {
    		expTmp = it.next();
    		if (expTmp.getName().equals(expName1)) {
    			assertEquals(1, expTmp.getBioAssays().size());
    		}
    		else if (expTmp.getName().equals(expName2)) {
    			assertEquals(0, expTmp.getBioAssays().size());
    		}
    	}
    	
    	
    	// Test 2 - Add 
    	// add new experiment and update
    	Experiment exp3 = new Experiment(expName3);
    	exp3.setId((long) 1003);
    	
    	// Code below commented out by DHALL
//    	exp3.setShoppingCart(c1);
    	c1.add(exp3);
    	cartDao.update(c1);
    	c2 = cartDao.load(cartUser);
    	assertEquals(3, c2.getExperiments().size());
    	
        // add two new assays to exp3 and update
    	DataSerializedBioAssay assay2 = new DataSerializedBioAssay();
    	DataSerializedBioAssay assay3 = new DataSerializedBioAssay();
    	assay2.setOrganism(o1);
    	assay2.setExperiment(exp3);
    	assay3.setOrganism(o1);
    	assay3.setExperiment(exp3);
    	exp3.add(assay2);
    	exp3.add(assay3);
    	cartDao.update(c1);
    	c2 = cartDao.load(cartUser);
    	it = c2.getExperiments().iterator();
    	while (it.hasNext()) {
    		expTmp = it.next();
    		if (expTmp.getName().equals(expName3)) {
    			assertEquals(2, expTmp.getBioAssays().size());
    		}
    		break;
    	}
    	
    	
    	// Test 3 - Remove 
    	// Remove a experiment and update
    	c1.remove(exp2);
    	cartDao.update(c1);
    	c2 = cartDao.load(cartUser);
    	assertEquals(2, c2.getExperiments().size()); //exp1 and 3 left

    	// Remove an assay and update
    	
    	// Code below commented out by DHALL
//    	exp3.remove(assay3);
    	cartDao.update(c1); // exp1 contains assay1, exp3 contains assay2
    	c2 = cartDao.load(cartUser);
    	it = c2.getExperiments().iterator();
    	while (it.hasNext()) { 
    		expTmp = it.next();
    		if (expTmp.getName().equals(expName1)) {
    			assertEquals(1, expTmp.getBioAssays().size());
    		}
    		else if (expTmp.getName().equals(expName3)) {
    			assertEquals(1, expTmp.getBioAssays().size());
    		}
    	}
    	

    	// Test 4 - Delete shopping cart
    	cartDao.delete(c1);
    	c2 = cartDao.load(cartUser);
    	assertNull(c2);

    }
}
