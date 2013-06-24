/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/analysis/SlidingWindowSmootherTester.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $



*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.List;

import org.rti.webgenome.analysis.SlidingWindowSmoother;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;

import junit.framework.TestCase;

/**
 * Tester for <code>SlidingWindowSmoother</code>.
 * @author dhall
 *
 */
public final class SlidingWindowSmootherTester extends TestCase {
    

    /**
     * Test perform() method.
     * @throws Exception if an error occurs
     */
    public void testPerform() throws Exception {
        
        // Setup input data set
        ChromosomeArrayData in = new ChromosomeArrayData((short) 1);
        Reporter r = new Reporter("r1", (short) 1, (long) 100);
        in.add(new ArrayDatum((float) 1.0, r));
        in.add(new ArrayDatum((float) 2.0,
                new Reporter("r1", (short) 1, (long) 200)));
        in.add(new ArrayDatum((float) 3.0,
                new Reporter("r1", (short) 1, (long) 300)));
        in.add(new ArrayDatum((float) 4.0,
                new Reporter("r1", (short) 1, (long) 400)));
        in.add(new ArrayDatum((float) 5.0,
                new Reporter("r1", (short) 1, (long) 500)));
        in.add(new ArrayDatum((float) 6.0,
                new Reporter("r1", (short) 1, (long) 600)));
        in.add(new ArrayDatum((float) 7.0,
                new Reporter("r1", (short) 1, (long) 700)));
        
        // Perform operation
        SlidingWindowSmoother smoother = new SlidingWindowSmoother();
        smoother.setWindowSize(5);
        ChromosomeArrayData out = smoother.perform(in);
        
        // Check output
        List<ArrayDatum> data = new ArrayList<ArrayDatum>(out.getArrayData());
        assertEquals(in.getArrayData().size(), data.size());
        assertEquals(r, data.get(0).getReporter());
        assertEquals((float) 2.0, data.get(0).getValue());
        assertEquals((float) 0.0, data.get(0).getError());
        assertEquals((float) 4.0, data.get(3).getValue());
        assertEquals((float) 22.0 / (float) 4.0, data.get(5).getValue());
    }

}
