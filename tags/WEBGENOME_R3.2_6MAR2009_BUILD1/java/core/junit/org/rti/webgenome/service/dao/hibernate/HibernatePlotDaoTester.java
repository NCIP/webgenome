/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-07-18 21:42:48 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

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
		plot.setId(new Long(1));
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
