/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/analysis/AveragerTester.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $



*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.List;

import org.rti.webgenome.analysis.Averager;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;

import junit.framework.TestCase;

/**
 * Tester for <code>Averager</code>.
 * @author dhall
 *
 */
public final class AveragerTester extends TestCase {
    
    /**
     * Test on chromosome array data
     * that have matching reporters.
     * @throws Exception if something goes wrong
     */
    public void testAllMatching() throws Exception {
        
        // Instantiate reporters
        Reporter r1 = new Reporter("r1", (short) 1, (long) 10);
        Reporter r2 = new Reporter("r2", (short) 1, (long) 20);
        Reporter r3 = new Reporter("r3", (short) 1, (long) 30);
        
        // Instantiate chromosome array data
        ChromosomeArrayData c1 = new ChromosomeArrayData((short) 1);
        ChromosomeArrayData c2 = new ChromosomeArrayData((short) 1);
        ChromosomeArrayData c3 = new ChromosomeArrayData((short) 1);
        
        // Add array datum for reporter 1
        c1.add(new ArrayDatum((float) 1.0, r1));
        c2.add(new ArrayDatum((float) 2.0, r1));
        c3.add(new ArrayDatum((float) 3.0, r1));
        
        // Add array datum for reporter 2
        c1.add(new ArrayDatum((float) 1.0, r2));
        c2.add(new ArrayDatum((float) 2.0, r2));
        c3.add(new ArrayDatum((float) 3.0, r2));
        
        // Add array datum for reporter 3
        c1.add(new ArrayDatum((float) 1.0, r3));
        c2.add(new ArrayDatum((float) 2.0, r3));
        c3.add(new ArrayDatum((float) 3.0, r3));
        
        // Calculate mean
        List<ChromosomeArrayData> cad =
            new ArrayList<ChromosomeArrayData>();
        cad.add(c1);
        cad.add(c2);
        cad.add(c3);
        Averager a = new Averager();
        ChromosomeArrayData results = a.perform(cad);
        assertNotNull(results);
        assertEquals(3, results.getArrayData().size());
        assertEquals((float) 2.0, results.getArrayData().get(0).getValue());
    }
    
    
    /**
     * Test on chromosome array data
     * that have one reporter that does
     * not match any other.
     * @throws Exception if something goes wrong
     */
    public void testOneNonMatching() throws Exception {
        
        // Instantiate reporters
        Reporter r1 = new Reporter("r1", (short) 1, (long) 10);
        Reporter r2 = new Reporter("r2", (short) 1, (long) 20);
        Reporter r3 = new Reporter("r3", (short) 1, (long) 30);
        Reporter r4 = new Reporter("r4", (short) 1, (long) 25);
        
        // Instantiate chromosome array data
        ChromosomeArrayData c1 = new ChromosomeArrayData((short) 1);
        ChromosomeArrayData c2 = new ChromosomeArrayData((short) 1);
        ChromosomeArrayData c3 = new ChromosomeArrayData((short) 1);
        
        // Add array datum for reporter 1
        c1.add(new ArrayDatum((float) 1.0, r1));
        c2.add(new ArrayDatum((float) 2.0, r1));
        c3.add(new ArrayDatum((float) 3.0, r1));
        
        // Add array datum for reporters 2 & 4
        c1.add(new ArrayDatum((float) 1.0, r2));
        c2.add(new ArrayDatum((float) 2.0, r2));
        c3.add(new ArrayDatum((float) 3.0, r4));
        
        // Add array datum for reporter 3
        c1.add(new ArrayDatum((float) 1.0, r3));
        c2.add(new ArrayDatum((float) 2.0, r3));
        c3.add(new ArrayDatum((float) 3.0, r3));
        
        // Calculate mean
        List<ChromosomeArrayData> cad =
            new ArrayList<ChromosomeArrayData>();
        cad.add(c1);
        cad.add(c2);
        cad.add(c3);
        Averager a = new Averager();
        ChromosomeArrayData results = a.perform(cad);
        assertNotNull(results);
        assertEquals(4, results.getArrayData().size());
        assertEquals((float) 2.0, results.getArrayData().get(0).getValue());
        assertEquals((float) 1.5, results.getArrayData().get(1).getValue());
        assertEquals((float) 3.0, results.getArrayData().get(2).getValue());
        assertEquals((float) 2.0, results.getArrayData().get(3).getValue());
    }
    
    
    /**
     * Test on chromosome array data
     * where no reporter match any other.
     * @throws Exception if something goes wrong
     */
    public void testAllNonMatching() throws Exception {
        
        // Instantiate chromosome array data
        ChromosomeArrayData c1 = new ChromosomeArrayData((short) 1);
        ChromosomeArrayData c2 = new ChromosomeArrayData((short) 1);
        ChromosomeArrayData c3 = new ChromosomeArrayData((short) 1);
        
        // Add array datum for reporter 1
        c1.add(new ArrayDatum((float) 1.0,
                new Reporter("r1", (short) 1, (long) 10)));
        c2.add(new ArrayDatum((float) 2.0, 
                new Reporter("r1", (short) 1, (long) 20)));
        c3.add(new ArrayDatum((float) 3.0, 
                new Reporter("r1", (short) 1, (long) 30)));
        
        // Add array datum for reporters 2 & 4
        c1.add(new ArrayDatum((float) 4.0, 
                new Reporter("r1", (short) 1, (long) 40)));
        c2.add(new ArrayDatum((float) 5.0, 
                new Reporter("r1", (short) 1, (long) 50)));
        c3.add(new ArrayDatum((float) 6.0, 
                new Reporter("r1", (short) 1, (long) 60)));
        
        // Add array datum for reporter 3
        c1.add(new ArrayDatum((float) 7.0, 
                new Reporter("r1", (short) 1, (long) 70)));
        c2.add(new ArrayDatum((float) 8.0, 
                new Reporter("r1", (short) 1, (long) 80)));
        c3.add(new ArrayDatum((float) 9.0, 
                new Reporter("r1", (short) 1, (long) 90)));
        
        // Calculate mean
        List<ChromosomeArrayData> cad =
            new ArrayList<ChromosomeArrayData>();
        cad.add(c1);
        cad.add(c2);
        cad.add(c3);
        Averager a = new Averager();
        ChromosomeArrayData results = a.perform(cad);
        assertNotNull(results);
        assertEquals(9, results.getArrayData().size());
        assertEquals((float) 1.0, results.getArrayData().get(0).getValue());
        assertEquals((float) 2.0, results.getArrayData().get(1).getValue());
        assertEquals((float) 9.0, results.getArrayData().get(8).getValue());
    }

}
