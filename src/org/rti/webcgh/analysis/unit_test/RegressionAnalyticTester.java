/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analysis/unit_test/RegressionAnalyticTester.java,v $
$Revision: 1.2 $
$Date: 2006-10-31 18:04:26 $

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
import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.service.analysis.RegressionService;

//import org.rti.webcgh.analytic.AcghData;
//import org.rti.webcgh.analysis.AcghAnalyticTransformer;
//import org.rti.webcgh.analysis.AcghAnalyticOperation;
import org.rti.webcgh.analysis.LinearRegressionAnalyticOperation;
import org.rti.webcgh.analysis.LoessRegressionAnalyticOperation;

import junit.framework.TestCase;

/**
 * Tester for <code>AcghAnalyticTransformer, AcghAnalyticOperation</code>.
 * @author kungyen
 *
 */
public final class RegressionAnalyticTester extends TestCase {
    

    /**
     * Test perform() method.
     * @throws Exception if an error occurs
     */
    public void testPerform() throws Exception {
        
        // Setup input data set
        ChromosomeArrayData in = new ChromosomeArrayData((short) 21);
        Reporter r = new Reporter("RP11-82D16", (short) 21, (long) 2009);
        in.add(new ArrayDatum((float) 0.202059711 , r));
        in.add(new ArrayDatum((float) 0.173037143,
                new Reporter("RP11-62M23", (short) 21, (long) 3368)));
        in.add(new ArrayDatum((float) 0.121655619,
                new Reporter("RP11-111O5", (short) 21, (long) 4262)));
        in.add(new ArrayDatum((float) 0.090844669,
                new Reporter("RP11-51B4" , (short) 21, (long) 6069)));
        in.add(new ArrayDatum((float) 0.170214084,
                new Reporter("RP11-60J11", (short) 21, (long) 6817)));
        in.add(new ArrayDatum((float) -0.025518441,
                new Reporter("RP11-813J5", (short) 21, (long) 9498)));
        in.add(new ArrayDatum((float) 0.046834206,
                new Reporter("RP11-199O1", (short) 21, (long) 10284)));
        
        // Perform operation
        /*
        AcghAnalyticTransformer transformer = new AcghAnalyticTransformer();
        AcghData acghData = transformer.transform(in);
        double[] smoothedRatios = 
                {100.1, 200.1, 300.1, 400.1, 500.1, 600.1, 700.1};
        acghData.setSmoothedRatios(smoothedRatios);
        ChromosomeArrayData out = transformer.transform(acghData, in);
        */
        LinearRegressionAnalyticOperation operation = new LinearRegressionAnalyticOperation();
        //LoessRegressionAnalyticOperation operation = new LoessRegressionAnalyticOperation();
        ChromosomeArrayData out = operation.perform(in);
        
        // Check output
        List<ArrayDatum> data = new ArrayList<ArrayDatum>(out.getArrayData());
        assertEquals(in.getArrayData().size(), data.size());
        assertEquals(r, data.get(0).getReporter());
        //assertEquals((float) 100.1, data.get(0).getValue());
        //assertEquals((float) 0.0, data.get(0).getError());
        //assertEquals((float) 400.1, data.get(3).getValue());
        //assertEquals((float) 22.0 / (float) 4.0, data.get(5).getValue());
        List<Reporter> inreporters = new ArrayList<Reporter>(in.getReporters());
        List<Reporter> outreporters = new ArrayList<Reporter>(out.getReporters());
		for (int i = 0; i < data.size(); i++) {
			System.out.print(String.valueOf(inreporters.get(i).getChromosome()) + ", ");
			System.out.print(String.valueOf(inreporters.get(i).getName()) + ", ");
			System.out.print(String.valueOf(data.get(i).getValue()) + ", ");
			System.out.print(String.valueOf(outreporters.get(i).getChromosome()) + ", ");
			System.out.println(String.valueOf(inreporters.get(i).getName()) + ".");
		}
    }

}
