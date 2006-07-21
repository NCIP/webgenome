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

package org.rti.webcgh.service.unit_test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.service.ShoppingCartMgr;
import org.rti.webcgh.service.dao.OrganismDao;
import org.rti.webcgh.service.dao.ShoppingCartDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>ShoppingCartMgrImpl</code>.
 * @author dhall
 *
 */
public final class ShoppingCartMgrImplTester extends TestCase {
    
    /**
     * Path (relative to classpath) to directory containing
     * test files.
     */
    private static final String TEST_DIRECTORY =
        "org/rti/webcgh/service/unit_test/shopping_cart_mgr_impl_test_files";
    
//    /**
//     * Test loading a small SMD data file.
//     * @throws Exception if something crashes
//     */
//    public void testOnSmallSmdFile() throws Exception {
//        this.runTest("small-smd.csv");
//    }
    
//    /**
//     * Test loading a medium size SMD data file.
//     * @throws Exception if something crashes
//     */
//    public void testOnMediumSmdFile() throws Exception {
//        this.runTest("medium-smd.csv");
//    }
    
    /**
     * Test loading a medium-large size SMD data file.
     * @throws Exception if something crashes
     */
    public void testOnMediumLargeSmdFile() throws Exception {
        this.runTest("medium-large-smd.csv");
    }
    
//    /**
//     * Test loading a small SMD data file.
//     * @throws Exception if something crashes
//     */
//    public void testOnLargeSmdFile() throws Exception {
//        this.runTest("large-smd.csv");
//    }
    
    /**
     * Run core test logic.  Actually, no tests are performed.  A
     * successful "test" is one in which no exceptions are thrown.
     * @param fname Name of file containing test data
     * @throws Exception if something crashes
     */
    public void runTest(final String fname) throws Exception {
        
        // Get beans
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
            "org/rti/webcgh/service/unit_test/beans.xml");
        OrganismDao oDao = (OrganismDao) ctx.getBean("organismDao");
        ShoppingCartDao sDao = (ShoppingCartDao) ctx.getBean("shoppingCartDao");
        ShoppingCartMgr mgr =
            (ShoppingCartMgr) ctx.getBean("shoppingCartMgr");
        
        // Create shopping cart and persist
        String userName = "user";
        ShoppingCart cart = new ShoppingCart(userName);
        sDao.save(cart);
       
        // Load data
        File file = this.getFile(fname);
        mgr.loadSmdFile(file, userName, oDao.loadDefault());
        
        // Clean up
        mgr.clear(userName);
        sDao.delete(cart);
    }
    
    
    /**
     * Helper method to get file of given name from
     * test directory.
     * @param relativeName Relative name of file.  Does not
     * include absoluate path.
     * @return A file
     */
    private File getFile(final String relativeName) {
        String absoluteName = TEST_DIRECTORY + "/" + relativeName;
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        URL url = loader.getResource(absoluteName);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new WebcghSystemException("Error finding test file");
        }
        return file;
    }

}
