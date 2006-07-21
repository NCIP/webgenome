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

import org.apache.log4j.Logger;
import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ArrayDatumGenerator;
import org.rti.webcgh.domain.BioAssayData;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.service.dao.BioAssayDataDao;
import org.rti.webcgh.service.dao.ReporterDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>JdbcBioAssayDataDao</code>.
 * @author dhall
 *
 */
public final class JdbcBioAssayDataDaoTester extends TestCase {
    
    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(JdbcBioAssayDataDaoTester.class);
    
    /**
     * Number of <code>ArrayDatum</code> objects per
     * chromosome.
     */
    private static final int NUM_DATUM_PER_CHROM = 10;
    
    /**
     * Test all methods.
     *
     */
    public void testAllMethods() {
        
        // Get DAO beans
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webcgh/service/dao/jdbc/unit_test/beans.xml");
        ReporterDao rDao = (ReporterDao) ctx.getBean("reporterDao");
        BioAssayDataDao bDao = (BioAssayDataDao)
            ctx.getBean("bioAssayDataDao");
        
        // Instantiate objects
        BioAssayData bad = new BioAssayData();
        ArrayDatumGenerator adg = new ArrayDatumGenerator();
        for (int i = 0; i < NUM_DATUM_PER_CHROM; i++) {
            ArrayDatum ad = adg.newArrayDatum();
            ad.getReporter().setChromosome((short) 1);
            bad.add(ad);
        }
        for (int i = 0; i < NUM_DATUM_PER_CHROM; i++) {
            ArrayDatum ad = adg.newArrayDatum();
            ad.getReporter().setChromosome((short) 2);
            bad.add(ad);
        }
        
        // Save reporters
        LOGGER.info("Saving reporters");
        for (Reporter r : adg.getReporters()) {
            rDao.save(r);
        }
        LOGGER.info("Done saving reporters");
        
        // Save data
        LOGGER.info("Saving bioassay data");
        bDao.save(bad);
        assertNotNull(bad.getId());
        LOGGER.info("Done saving bioassay data");
        
        // Query by id
        BioAssayData bad2 = bDao.load(bad.getId());
        assertNotNull(bad2);
        assertEquals(2, bad2.getChromosomeArrayData().size());
        assertEquals(NUM_DATUM_PER_CHROM,
                bad2.getChromosomeArrayData().get((short) 1).
                getArrayData().size());
        
        // Delete data
        LOGGER.info("Deleting data");
        Long id = bad.getId();
        bDao.delete(bad);
        assertNull(bad.getId());
        assertNull(bDao.load(id));
        LOGGER.info("Done deleting data");
        
        // Delete reporters
        LOGGER.info("Deleting reporters");
        for (Reporter r : adg.getReporters()) {
            rDao.delete(r);
        }
        LOGGER.info("Done deleting reporters");
    }

}
