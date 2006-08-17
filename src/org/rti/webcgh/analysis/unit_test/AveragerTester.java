/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.analysis.unit_test;

import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.analysis.Averager;
import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;

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
