/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:38 $


*/

package org.rti.webgenome.graphics.util;

import java.util.ArrayList;
import java.util.List;

import org.rti.webgenome.graphics.util.PointListCompressor;

import junit.framework.TestCase;

/**
 * Tester for <code>PointListCompressor</code>.
 * @author dhall
 *
 */
public final class PointListCompressorTester extends TestCase {
	
	
	/**
	 * Test on null lists.
	 */
	public void testNullList() {
		List<Double> xx = null;
		List<Double> yy = null;
		try {
			PointListCompressor.compress(xx, yy);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	
	/**
	 * Test on uneven size lists.
	 */
	public void testUnevenLists() {
		List<Double> xx = this.newList(new double[]	{1.0, 2.0, 3.0});
		List<Double> yy = this.newList(new double[] {1.0, 2.0});
		try {
			PointListCompressor.compress(xx, yy);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	
	/**
	 * Test on a list with no duplicate X-coordinates.
	 */
	public void testNoDuplicates() {
		List<Double> xx = this.newList(new double[]	{1.0, 2.0, 3.0});
		List<Double> yy = this.newList(new double[] {1.0, 2.0, 3.0});
		PointListCompressor.compress(xx, yy);
		assertEquals(3, xx.size());
		assertEquals(3, yy.size());
	}
	
	
	/**
	 * Test on a list with all duplicate X-coordinates.
	 */
	public void testAllDuplicates() {
		List<Double> xx = this.newList(new double[]	{1.0, 1.0, 1.0});
		List<Double> yy = this.newList(new double[] {1.0, 2.0, 3.0});
		PointListCompressor.compress(xx, yy);
		assertEquals(1, xx.size());
		assertEquals(1, yy.size());
		assertEquals(1.0, xx.get(0));
		assertEquals(2.0, yy.get(0));
	}
	
	
	/**
	 * Test on list with duplicates in the middle.
	 */
	public void testMiddleDuplicates() {
		List<Double> xx = this.newList(new double[]	{0.0, 1.0, 1.0, 1.0, 2.0});
		List<Double> yy = this.newList(new double[] {0.0, 1.0, 2.0, 3.0, 4.0});
		PointListCompressor.compress(xx, yy);
		assertEquals(3, xx.size());
		assertEquals(3, yy.size());
		assertEquals(0.0, xx.get(0));
		assertEquals(0.0, yy.get(0));
		assertEquals(1.0, xx.get(1));
		assertEquals(2.0, yy.get(1));
		assertEquals(2.0, xx.get(2));
		assertEquals(4.0, yy.get(2));
	}
	
	
	/**
	 * Test on list with duplicates at the start.
	 */
	public void testStartDuplicates() {
		List<Double> xx = this.newList(new double[]	{1.0, 1.0, 1.0, 2.0});
		List<Double> yy = this.newList(new double[] {1.0, 2.0, 3.0, 4.0});
		PointListCompressor.compress(xx, yy);
		assertEquals(2, xx.size());
		assertEquals(2, yy.size());
		assertEquals(1.0, xx.get(0));
		assertEquals(2.0, yy.get(0));
		assertEquals(2.0, xx.get(1));
		assertEquals(4.0, yy.get(1));
	}

	
	/**
	 * Test on list with duplicates at the end.
	 */
	public void testEndDuplicates() {
		List<Double> xx = this.newList(new double[]	{0.0, 1.0, 1.0, 1.0});
		List<Double> yy = this.newList(new double[] {0.0, 1.0, 2.0, 3.0});
		PointListCompressor.compress(xx, yy);
		assertEquals(2, xx.size());
		assertEquals(2, yy.size());
		assertEquals(0.0, xx.get(0));
		assertEquals(0.0, yy.get(0));
		assertEquals(1.0, xx.get(1));
		assertEquals(2.0, yy.get(1));
	}
	
	
	/**
	 * Create new list with given initial values.
	 * @param initialValues Initial values
	 * @return A list
	 */
	private List<Double> newList(final double[] initialValues) {
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < initialValues.length; i++) {
			list.add(initialValues[i]);
		}
		return list;
	}
}
