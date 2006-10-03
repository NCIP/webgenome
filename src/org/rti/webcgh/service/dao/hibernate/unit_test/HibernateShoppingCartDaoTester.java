/*
$Revision$
$Date$

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

package org.rti.webcgh.service.dao.hibernate.unit_test;


import org.rti.webcgh.domain.Plot;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.service.dao.hibernate.HibernateShoppingCartDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>HibernateExperimentDao</code>.
 * @author dhall
 */
public final class HibernateShoppingCartDaoTester extends TestCase {
    
    /**
     * Test all methods.
     */
    public void testAllMethods() {
    	
    	// Setup for tests
    	ApplicationContext ctx = new ClassPathXmlApplicationContext(
    		"org/rti/webcgh/service/dao/hibernate/unit_test/beans.xml");
    	HibernateShoppingCartDao dao = (HibernateShoppingCartDao)
    		ctx.getBean("shoppingCartDao");
    	String user = "user";
    	
    	// Create a shopping cart and add 2 plots
    	ShoppingCart c1 = new ShoppingCart(user);
    	c1.add(new Plot("plot1"));
    	c1.add(new Plot("plot2"));
    	dao.save(c1);
    	
    	// Test 1
    	ShoppingCart c2 = dao.load(user);
    	assertNotNull(c2);
    	assertEquals(c1.getUserName(), c2.getUserName());
    	assertEquals(2, c2.getPlots().size());
    	
    	// Test 2 - Add new plot and update
    	Plot plot3 = new Plot("plot3");
    	c1.add(plot3);
    	dao.update(c1);
    	c2 = dao.load(user);
    	assertEquals(3, c2.getPlots().size());
    	
    	// Test 3 - Change name of a plot
    	String newName = "new name";
    	plot3.setName(newName);
    	dao.update(c1);
    	c2 = dao.load(user);
    	boolean found = false;
    	for (Plot p : c2.getPlots()) {
    		if (newName.equals(p.getName())) {
    			found = true;
    			break;
    		}
    	}
    	if (!found) {
    		fail();
    	}
    	
    	// Test 4 - Remove a plot and update
    	c1.remove(plot3);
    	dao.update(c1);
    	c2 = dao.load(user);
    	assertEquals(2, c2.getPlots().size());
    	
    	// Test 5 - Delete shopping cart
    	dao.delete(c1);
    	c2 = dao.load(user);
    	assertNull(c2);
    }

}
