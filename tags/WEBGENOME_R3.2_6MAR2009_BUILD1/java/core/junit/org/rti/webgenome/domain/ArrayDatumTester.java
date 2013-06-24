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

import org.rti.webgenome.domain.ArrayDatum;

import junit.framework.TestCase;

/**
 * Tester for <code>ArrayDatum</code>.
 * @author dhall
 *
 */
public final class ArrayDatumTester extends TestCase {
	
	/**
	 * Test the generateIntermediate() method.
	 *
	 */
	public void testGenerateIntermediate() {
		ArrayDatum d1 = new ArrayDatum((float) 5.0, (short) 1, 100);
		ArrayDatum d2 = new ArrayDatum((float) 0.0, (short) 1, 200);
		ArrayDatum d3 = ArrayDatum.generateIntermediate(d1, d2, (float) 2.5);
		assertEquals(150, d3.getReporter().getLocation());
	}

}
