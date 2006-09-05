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

package org.rti.webcgh.service.dao.hibernate.unit_test;

import java.util.List;

import org.apache.log4j.Logger;
import org.rti.webcgh.domain.ArrayDatumGenerator;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.service.dao.ChromosomeArrayDataDao;
import org.rti.webcgh.service.dao.ReporterDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>HibernateChromosomeArrayDataDao</code>.
 * @author dhall
 *
 */
public final class HibernateChromosomeArrayDataDaoTester extends TestCase {
    
    /** Logger. */
    private static final Logger LOGGER = 
        Logger.getLogger(HibernateChromosomeArrayDataDaoTester.class);
    
    /**
     * Number of <code>ArrayDatum</code> objects to
     * instantiate for test on large data set.
     */
    private static final int LARGE_NUM_DATUM = 10000;
    
    
    /**
     * Test all methods on a large set
     * of data.
     */
    public void testAllMethodsOnLargeDataSet() throws Exception {
        
        // Generate large data set
        ArrayDatumGenerator gen = new ArrayDatumGenerator();
        ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
        for (int i = 0; i < LARGE_NUM_DATUM; i++) {
            cad.add(gen.newArrayDatum());
        }
        
        // Get DAO objects
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webcgh/service/dao/hibernate/unit_test/beans.xml");
        ChromosomeArrayDataDao cDao = (ChromosomeArrayDataDao)
            ctx.getBean("chromosomeArrayDataDao");
        ReporterDao rDao = (ReporterDao)
            ctx.getBean("reporterDao");
        
        // Save reporters
        LOGGER.info("Saving reporters");
        List<Reporter> reporters = gen.getReporters();
        for (Reporter r : reporters) {
            rDao.save(r);
        }
        LOGGER.info("Done saving reporters");
        
        // Save
        LOGGER.info("Saving data");
        cDao.save(cad);
        LOGGER.info("Done saving data");
        
        // Delete
        LOGGER.info("Deleting data");
        cDao.delete(cad);
        LOGGER.info("Done deleting data");
        
        // Delete reporters
        LOGGER.info("Deleting reporters");
        for (Reporter r : reporters) {
            rDao.delete(r);
        }
        LOGGER.info("Done deleting reporters");
    }

}
