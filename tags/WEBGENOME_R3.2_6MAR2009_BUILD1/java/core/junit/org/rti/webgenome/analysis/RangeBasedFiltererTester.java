/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/analysis/RangeBasedFiltererTester.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $



*/

package org.rti.webgenome.analysis;

import org.rti.webgenome.analysis.RangeBasedFilterer;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;

import junit.framework.TestCase;

/**
 * Tester for <code>RangeBasedFilterer</code>.
 * @author dhall
 *
 */
public final class RangeBasedFiltererTester extends TestCase {
    
    /**
     * Tests perform() method.
     * @throws Exception if anything bad happens
     */
    public void testPerform() throws Exception {
        ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
        cad.add(new ArrayDatum((float) 1.0,
                new Reporter(null, (short) 1, (long) 1)));
        cad.add(new ArrayDatum((float) 0.1,
                new Reporter(null, (short) 1, (long) 2)));
        cad.add(new ArrayDatum((float) 2.0,
                new Reporter(null, (short) 1, (long) 3)));
        cad.add(new ArrayDatum((float) -1.0,
                new Reporter(null, (short) 1, (long) 4)));
        cad.add(new ArrayDatum((float) -0.1,
                new Reporter(null, (short) 1, (long) 5)));
        RangeBasedFilterer f =
            new RangeBasedFilterer((float) -0.5, (float) 0.5);
        ChromosomeArrayData output = f.perform(cad);
        assertNotNull(output);
        assertEquals(3, output.getArrayData().size());
        assertEquals((float) 1.0, output.getArrayData().get(0).getValue());
        assertEquals((float) 2.0, output.getArrayData().get(1).getValue());
        assertEquals((float) -1.0, output.getArrayData().get(2).getValue());
    }

}
