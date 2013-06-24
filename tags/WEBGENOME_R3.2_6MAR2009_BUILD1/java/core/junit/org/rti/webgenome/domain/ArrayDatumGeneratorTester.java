/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/

package org.rti.webgenome.domain;

import junit.framework.TestCase;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ArrayDatumGenerator;

/**
 * Tester for <code>ArrayDatumGenerator</code>.
 * @author dhall
 *
 */
public final class ArrayDatumGeneratorTester extends TestCase {
    
    /**
     * Tests newArrayDatum() and reset() methods.
     *
     */
    public void testNewArrayDatumAndReset() {
        ArrayDatumGenerator adg = new ArrayDatumGenerator();
        ArrayDatum a1 = adg.newArrayDatum();
        ArrayDatum a2 = adg.newArrayDatum();
        assertTrue(a1.getReporter() != a2.getReporter());
        adg.reset();
        
        ArrayDatum b1 = adg.newArrayDatum();
        assertEquals(a1.getReporter(), b1.getReporter());
        ArrayDatum b2 = adg.newArrayDatum();
        assertEquals(a2.getReporter(), b2.getReporter());
        adg.newArrayDatum();
    }

}
