/*
$Revision: 1.2 $
$Date: 2007-09-13 23:42:17 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.service.plot.AnnotationPlotParameters;
import org.rti.webgenome.service.plot.BarPlotParameters;
import org.rti.webgenome.service.plot.GenomeSnapshopPlotParameters;
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
		GenomeSnapshopPlotParameters p5 = new GenomeSnapshopPlotParameters();
		
		// Run tests
		dao.save(p1);
		dao.save(p2);
		dao.save(p3);
		dao.save(p4);
		dao.save(p5);
		dao.delete(p1);
		dao.delete(p2);
		dao.delete(p3);
		dao.delete(p4);
		dao.delete(p5);
	}

}
