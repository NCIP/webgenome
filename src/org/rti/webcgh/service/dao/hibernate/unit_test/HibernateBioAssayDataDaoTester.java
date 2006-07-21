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

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.BioAssayData;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.service.dao.BioAssayDataDao;
import org.rti.webcgh.service.dao.ChromosomeArrayDataDao;
import org.rti.webcgh.service.dao.ReporterDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>HibernateBioAssayDataDao</code>.
 * @author dhall
 *
 */
public class HibernateBioAssayDataDaoTester extends TestCase {
    
    /**
     * Test save() and delete() methods.
     *
     */
    public void testSaveAndDelete() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "org/rti/webcgh/service/dao/hibernate/unit_test/beans.xml");
        BioAssayDataDao bDao = (BioAssayDataDao)
            ctx.getBean("bioAssayDataDao");
        ReporterDao rDao = (ReporterDao) ctx.getBean("reporterDao");
        
        // Create and save bioassay data
        BioAssayData bad = new BioAssayData();
        bDao.save(bad);
        
        // Create reporters
        Reporter r1 = new Reporter("r1", (short) 1, (long) 100);
        Reporter r2 = new Reporter("r2", (short) 1, (long) 200);
        rDao.save(r1);
        rDao.save(r2);
        
        // Create datum objects
        ArrayDatum a1 = new ArrayDatum((float) 1.0, r1);
        ArrayDatum a2 = new ArrayDatum((float) 2.0, r2);
        
        // Add data and update
        bad.add(a1);
        bad.add(a2);
        bDao.update(bad);
        
        // Retrieve chromosome array data
        ChromosomeArrayDataDao cDao = (ChromosomeArrayDataDao)
            ctx.getBean("chromosomeArrayDataDao");
        ChromosomeArrayData cad = cDao.load((short) 1, bad.getId());
        assertNotNull(cad);
        
        // Delete chromosome array data
        bDao.delete(bad);
        
        // Clean up
        rDao.delete(r1);
        rDao.delete(r2);
    }

}
