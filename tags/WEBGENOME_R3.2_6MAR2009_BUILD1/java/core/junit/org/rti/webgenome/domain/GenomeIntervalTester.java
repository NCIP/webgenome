/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/

package org.rti.webgenome.domain;

import org.rti.webgenome.domain.GenomeInterval;

import junit.framework.TestCase;

/**
 * Tester for <code>GenomeInterval</code>.
 * @author dhall
 *
 */
public final class GenomeIntervalTester extends TestCase {
	
	/** A test interval. */
	private GenomeInterval i1 = new GenomeInterval((short) 1, 100, 200);
	
	/** A test interval. */
	private GenomeInterval i2 = new GenomeInterval((short) 1, 50, 150);
	
	/** A test interval. */
	private GenomeInterval i3 = new GenomeInterval((short) 1, 150, 250);
	
	/** A test interval. */
	private GenomeInterval i4 = new GenomeInterval((short) 1, 250, 350);
	
	/** A test interval. */
	private GenomeInterval i5 = new GenomeInterval((short) 2, 150, 250);
	
	/**
	 * Test overlaps() method.
	 */
	public void testOverlaps() {
		assertTrue(i1.overlaps(i2));
		assertTrue(i1.overlaps(i3));
		assertFalse(i1.overlaps(i4));
		assertFalse(i1.overlaps(i5));
	}
	
	
	/**
	 * Test intersection() method.
	 */
	public void testIntersection() {
		GenomeInterval intersect = GenomeInterval.intersection(i1, i2);
		assertEquals(100, intersect.getStartLocation());
		assertEquals(150, intersect.getEndLocation());
		intersect = GenomeInterval.intersection(i1, i3);
		assertEquals(150, intersect.getStartLocation());
		assertEquals(200, intersect.getEndLocation());
		intersect = GenomeInterval.intersection(i1, i4);
		assertNull(intersect);
	}
}
