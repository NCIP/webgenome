/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:11 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.domain;

import java.util.List;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;

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
