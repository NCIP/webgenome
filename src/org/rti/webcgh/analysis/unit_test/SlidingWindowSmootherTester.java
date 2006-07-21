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

import org.rti.webcgh.analysis.SlidingWindowSmoother;
import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;

import junit.framework.TestCase;

/**
 * Tester for <code>SlidingWindowSmoother</code>.
 * @author dhall
 *
 */
public class SlidingWindowSmootherTester extends TestCase {
    

    /**
     * Test perform() method.
     *
     */
    public void testPerform() {
        
        // Setup input data set
        ChromosomeArrayData in = new ChromosomeArrayData((short) 1);
        Reporter r = new Reporter("r1", (short) 1, (long) 100);
        in.add(new ArrayDatum((float) 1.0, r));
        in.add(new ArrayDatum((float) 2.0, r));
        in.add(new ArrayDatum((float) 3.0, r));
        in.add(new ArrayDatum((float) 4.0, r));
        in.add(new ArrayDatum((float) 5.0, r));
        in.add(new ArrayDatum((float) 6.0, r));
        in.add(new ArrayDatum((float) 7.0, r));
        
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
    }

}
