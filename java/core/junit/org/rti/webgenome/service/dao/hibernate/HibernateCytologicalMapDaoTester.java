/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $

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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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

package org.rti.webgenome.service.dao.hibernate;
import junit.framework.TestCase;

import org.rti.webgenome.domain.Cytoband;
import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.dao.hibernate.HibernateCytologicalMapDao;
import org.rti.webgenome.service.dao.hibernate.HibernateOrganismDao;
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
        "org/rti/webcgh/service/dao/hibernate/unit_test/beans.xml");
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
		assertEquals(2, map2.getCytobands().size());
		
		// Delete
		dao.delete(map);
		map2 = dao.load(org, chromosome);
		assertNull(map2);
	}
}