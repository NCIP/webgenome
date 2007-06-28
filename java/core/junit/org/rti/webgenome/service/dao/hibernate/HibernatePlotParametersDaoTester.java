/*
$Revision: 1.1 $
$Date: 2007-06-28 22:12:17 $

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

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.service.plot.AnnotationPlotParameters;
import org.rti.webgenome.service.plot.BarPlotParameters;
import org.rti.webgenome.service.plot.IdeogramPlotParameters;
import org.rti.webgenome.service.plot.ScatterPlotParameters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for
 * {@link org.rti.webgenome.service.dao.Hibernate.
 * HibernatePlotParametersDao}.
 * @author dhall
 *
 */
public class HibernatePlotParametersDaoTester extends TestCase {
	
	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernatePlotParametersDao dao =
			(HibernatePlotParametersDao)
			ctx.getBean("plotParametersDao");
		
		// Instantiate test objects
		AnnotationPlotParameters p1 = new AnnotationPlotParameters();
		p1.add(AnnotationType.GENE);
		p1.add(AnnotationType.LOH_SEGMENT);
		p1.add(new GenomeInterval((short) 1, 1, 10));
		p1.add(new GenomeInterval((short) 1, 11, 50));
		BarPlotParameters p2 = new BarPlotParameters();
		p2.add(new GenomeInterval((short) 2, 10, 50));
		IdeogramPlotParameters p3 = new IdeogramPlotParameters();
		p3.add(new GenomeInterval((short) 3, 1, 50));
		ScatterPlotParameters p4 = new ScatterPlotParameters();
		p4.add(new GenomeInterval((short) 4, 100, 200));
		
		// Run tests
		dao.save(p1);
		dao.save(p2);
		dao.save(p3);
		dao.save(p4);
		dao.delete(p1);
		dao.delete(p2);
		dao.delete(p3);
		dao.delete(p4);
	}

}
