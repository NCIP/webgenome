/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/

package org.rti.webgenome.domain;

import java.util.List;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;

import junit.framework.TestCase;

/**
 * Tester for <code>ChromosomeArrayData</code>.
 * @author dhall
 *
 */
public final class ChromosomeArrayDataTester extends TestCase {
    
    /**
     * Test <code>getArrayData(long, long)</code> method. 
     */
    public void testGetArrayData() {
        
        // Construct test object
        ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
        
        // Do test on chromosome with no data
        List<ArrayDatum> set = cad.getArrayData((long) 100, (long) 300);
        assertEquals(0, set.size());
        
        // Add data
        cad.add(new ArrayDatum((float) 1.0, 
                new Reporter(null, (short) 1, 100)));
        cad.add(new ArrayDatum((float) 1.0, 
                new Reporter(null, (short) 1, 200)));
        cad.add(new ArrayDatum((float) 1.0, 
                new Reporter(null, (short) 1, 300)));
        
        // On first and last datum
        set = cad.getArrayData((long) 100, (long) 300);
        assertEquals(3, set.size());
        
        // Outside first and last datum
        set = cad.getArrayData((long) 1, (long) 500);
        assertEquals(3, set.size());
        
        // Left of all
        set = cad.getArrayData((long) 1, (long) 50);
        assertEquals(0, set.size());
        
        // Right of all
        set = cad.getArrayData((long) 1000, (long) 5000);
        assertEquals(0, set.size());
        
        // Left datum only
        set = cad.getArrayData((long) 50, (long) 150);
        assertEquals(1, set.size());
        
        // Right datum only
        set = cad.getArrayData((long) 250, (long) 350);
        assertEquals(1, set.size());
        
        // Center datum only
        set = cad.getArrayData((long) 150, (long) 250);
        assertEquals(1, set.size());
        
        // Between data
        set = cad.getArrayData((long) 150, (long) 160);
        assertEquals(0, set.size());
    }
}
