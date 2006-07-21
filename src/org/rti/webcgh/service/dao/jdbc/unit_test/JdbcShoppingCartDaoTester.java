/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.service.dao.jdbc.unit_test;

import junit.framework.TestCase;

import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.service.dao.OrganismDao;
import org.rti.webcgh.service.dao.ShoppingCartDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Tester for <code>JdbcShoppingCartDao</code>.
 * @author dhall
 *
 */
public final class JdbcShoppingCartDaoTester extends TestCase {
    
    /**
     * Test all methods.
     *
     */
    public void testAllMethods() {
        
        // Get Spring DAO beans
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webcgh/service/dao/jdbc/unit_test/beans.xml");
        OrganismDao oDao = (OrganismDao) ctx.getBean("organismDao");
        ShoppingCartDao sDao = (ShoppingCartDao) ctx.getBean("shoppingCartDao");
        
        // Create initial test data
        String user = "user";
        Organism org = oDao.loadDefault();
        Experiment exp1 = new Experiment("exp1");
        exp1.add(new BioAssay("b1", org, (long) 1));
        exp1.add(new BioAssay("b2", org, (long) 1));
        ShoppingCart cart = new ShoppingCart(user);
        cart.add(exp1);
        Experiment exp2 = new Experiment("exp2");
        exp2.add(new BioAssay("b3", org, (long) 1));
        cart.add(exp2);
        
        // Save cart
        sDao.save(cart);
        assertNotNull(cart.getId());
        
        // Retrieve cart by id
        ShoppingCart cart2 = sDao.load(cart.getId());
        assertNotNull(cart2);
        assertEquals(user, cart2.getUserName());
        assertEquals(cart.getExperiments().size(),
                cart2.getExperiments().size());
        
        // Retrieve cart by user name
        cart2 = sDao.load(user);
        assertNotNull(cart2);
        assertEquals(user, cart2.getUserName());
        assertEquals(cart.getExperiments().size(),
                cart2.getExperiments().size());
        
        // Update cart
        Experiment exp3 = new Experiment("exp3");
        exp3.add(new BioAssay("b4", org, (long) 1));
        exp3.add(new BioAssay("b5", org, (long) 1));
        cart.add(exp3);
        Experiment exp4 = new Experiment("exp4");
        exp4.add(new BioAssay("b6", org, (long) 1));
        cart.remove(exp2);
        cart.add(exp3);
        cart.add(exp4);
        sDao.update(cart);
        cart2 = sDao.load(user);
        assertNotNull(cart2);
        assertEquals(cart.getExperiments().size(),
                cart2.getExperiments().size());
        
        // Delete cart
        sDao.delete(cart);
        cart2 = sDao.load(user);
        assertNull(cart2);
    }

}
