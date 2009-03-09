/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:38 $

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
