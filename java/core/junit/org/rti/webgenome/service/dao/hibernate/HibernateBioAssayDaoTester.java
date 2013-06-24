/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-07-18 21:42:48 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.DataSerializedBioAssay;
import org.rti.webgenome.domain.Organism;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for
 * {@link org.rti.webgenome.service.dao.hibernate.HibernateBioAssayDao}.
 * @author dhall
 *
 */
public class HibernateBioAssayDaoTester extends TestCase {

	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
		HibernateBioAssayDao dao =
			(HibernateBioAssayDao)
			ctx.getBean("bioAssayDao");
		
		// Instantiate test object
		Organism org = new Organism("Genus", "species");
		DataSerializedBioAssay ba = new DataSerializedBioAssay(
				"bioassay1", org);
		ba.setId(new Long(1));
		ba.setColor(Color.RED);
		ba.setSelected(true);
		SortedMap<Short, String> dataFileIdx = new TreeMap<Short, String>();
		dataFileIdx.put((short) 1, "file1");
		dataFileIdx.put((short) 2, "file2");
		ba.setChromosomeArrayDataFileIndex(dataFileIdx);
		Array array = new Array("array1");
		array.setChromosomeReportersFileName((short) 1, "file1");
		array.setChromosomeReportersFileName((short) 2, "file2");
		ba.setArray(array);
		Map<Short, Long> sizes = new HashMap<Short, Long>();
		sizes.put((short) 1, (long) 100);
		sizes.put((short) 2, (long) 50);
		ba.setChromosomeSizes(sizes);
		Map<Short, Float> mins = new HashMap<Short, Float>();
		mins.put((short) 1, (float) 0.1);
		mins.put((short) 2, (float) 0.2);
		ba.setMinValues(mins);
		Map<Short, Float> maxes = new HashMap<Short, Float>();
		maxes.put((short) 1, (float) 1.5);
		maxes.put((short) 2, (float) 2.5);
		ba.setMaxValues(maxes);
		Map<Short, Integer> nums = new HashMap<Short, Integer>();
		nums.put((short) 1, 500);
		nums.put((short) 2, 300);
		ba.setNumDatum(nums);
		
		// Run tests
		dao.save(ba);
		dao.delete(ba);
	}
}
