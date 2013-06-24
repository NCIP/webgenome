/*
$Revision: 1.3 $
$Date: 2007-04-13 02:52:13 $


*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;

/**
 * Tester for <code>SimpleNormalizer</code>.
 * @author dhall
 *
 */
public final class SimpleNormalizerTester extends TestCase {
    
    /** Test input data. */
    private static final Collection<ChromosomeArrayData>
        TEST_DATA = new ArrayList<ChromosomeArrayData>();
    
    
    // Initialize test input data
    static {
                
        // Chromosome 1
        ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
        TEST_DATA.add(cad);
        cad.add(new ArrayDatum((float) 1.0,
        		new Reporter(null, (short) 1, (long) 0)));
        cad.add(new ArrayDatum((float) 2.0, 
        		new Reporter(null, (short) 1, (long) 1)));
        cad.add(new ArrayDatum((float) 3.0, 
        		new Reporter(null, (short) 1, (long) 2)));
        
        // Chromosome 2
        cad = new ChromosomeArrayData((short) 2);
        TEST_DATA.add(cad);
        cad.add(new ArrayDatum((float) 4.0, 
        		new Reporter(null, (short) 2, (long) 0)));
        cad.add(new ArrayDatum((float) 5.0, 
        		new Reporter(null, (short) 2, (long) 1)));
        cad.add(new ArrayDatum((float) 6.0, 
        		new Reporter(null, (short) 2, (long) 2)));
        
        // Chromosome 3
        cad = new ChromosomeArrayData((short) 3);
        TEST_DATA.add(cad);
        cad.add(new ArrayDatum((float) 7.0, 
        		new Reporter(null, (short) 3, (long) 0)));
        cad.add(new ArrayDatum((float) 8.0, 
        		new Reporter(null, (short) 3, (long) 1)));
        cad.add(new ArrayDatum((float) 9.0,  
        		new Reporter(null, (short) 3, (long) 2)));
    }
    
    
    /**
     * Test all methods using mean.
     * @throws Exception if anything bad happens
     */
    public void testMean() throws Exception {
        SimpleNormalizer n = new SimpleBioAssayNormalizer();
        n.setOperation(SimpleNormalizer.MEAN);
        for (ChromosomeArrayData input : TEST_DATA) {
            n.adjustState(input);
        }
        ChromosomeArrayData output = n.perform(TEST_DATA.iterator().next());
        List<ArrayDatum> data = output.getArrayData();
        assertNotNull(data);
        assertEquals(3, data.size());
        assertEquals((float) -4.0, data.get(0).getValue());
    }
    
    
    /**
     * Test all methods using median.
     * @throws Exception if anything bad happens
     */
    public void testMedian() throws Exception {
        SimpleNormalizer n = new SimpleBioAssayNormalizer();
        n.setOperation(SimpleNormalizer.MEDIAN);
        for (ChromosomeArrayData input : TEST_DATA) {
            n.adjustState(input);
        }
        ChromosomeArrayData output = n.perform(TEST_DATA.iterator().next());
        List<ArrayDatum> data = output.getArrayData();
        assertNotNull(data);
        assertEquals(3, data.size());
        assertEquals((float) -4.0, data.get(0).getValue());
    }

}
