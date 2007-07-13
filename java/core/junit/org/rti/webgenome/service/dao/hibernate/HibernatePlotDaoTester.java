/*
$Revision: 1.2 $
$Date: 2007-07-13 19:35:03 $

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

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.graphics.event.MouseOverStripe;
import org.rti.webgenome.graphics.event.MouseOverStripes;
import org.rti.webgenome.graphics.io.ClickBoxes;
import org.rti.webgenome.service.plot.ScatterPlotParameters;
import org.rti.webgenome.units.Orientation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for
 * {@link org.rti.webgenome.service.dao.hibernate.HibernatePlotDao}.
 * @author dhall
 *
 */
public class HibernatePlotDaoTester extends TestCase {

	/**
	 * Test all methods.
	 *
	 */
	public void testAllMethods() {
		
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernatePlotDao dao =
			(HibernatePlotDao)
			ctx.getBean("plotDao");
		
		// Instantiate test object
		Plot plot = new Plot();
		plot.setDefaultImageFileName("file1");
		plot.setWidth(100);
		plot.setHeight(100);
		plot.addImageFile("img2", "file2");
		plot.addImageFile("img3", "file3");
		ClickBoxes cb = new ClickBoxes(100, 100, 10, 10, new Point(0, 0));
		cb.addClickBoxText("box1", 25, 10);
		cb.addClickBoxText("box2", 50, 50);
		Set<ClickBoxes> cbs = new HashSet<ClickBoxes>();
		cbs.add(cb);
		plot.setClickBoxes(cbs);
		MouseOverStripes mos = new MouseOverStripes(
				Orientation.HORIZONTAL, 100, 100, new Point(0, 0));
		mos.add(new MouseOverStripe(0, 10, "stripe1"));
		mos.add(new MouseOverStripe(11, 20, "stripe2"));
		Set<MouseOverStripes> moss = new HashSet<MouseOverStripes>();
		moss.add(mos);
		plot.setMouseOverStripes(moss);
		ScatterPlotParameters params = new ScatterPlotParameters();
		plot.setPlotParameters(params);
		
		// Run tests
		dao.save(plot);
		dao.delete(plot);
	}
}
