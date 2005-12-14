/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/service/unit_test/ClientDataServiceTester.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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
package org.rti.webcgh.service.unit_test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.service.ClientDataServiceImpl;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.QuantitationTypes;

import junit.framework.TestCase;

public class ClientDataServiceTester extends TestCase {
    
    protected ClientDataServiceImpl service = null;
    protected String clientID = null;
    protected Experiment[] experiments = null;
    protected String[] experimentIds1 = null;
    protected String[] experimentIds2 = null;
    protected BioAssayDataConstraints constraints = null;
    protected BioAssayDataConstraints[] constraintsArray = null; 

    /**
     * The setUp method contains test data to represent the objects that would be generated
     * by the parser from following the URLs:
     * http://localhost:8088/webGenome/client/plot.do?exptIDs=ASTROCYTOMA&intervals=7:53700000-57600000&clientID=1628747535
     * http://localhost:8088/webGenome/client/plot.do?exptIDs=ASTROCYTOMA,GBM,MIXED,OLIG&intervals=7:53700000-57600000&clientID=1628747535
     */
    protected void setUp() {
        
        service = new ClientDataServiceImpl();
        clientID = "1628747535";
        
        // Set up first array of experiment ids 
        experimentIds1 = new String[1]; 
        experimentIds1[0] = "ASTROCYTOMA";
        
        //Set up second array of experiment ids
        experimentIds2 = new String[4];
        experimentIds2[0] = "ASTROCYTOMA";
        experimentIds2[1] = "GBM";
        experimentIds2[2] = "MIXED";
        experimentIds2[3] = "OLIG";
        
        // Set up data constraints for the bioassay
        constraints = new BioAssayDataConstraints();
        constraints.setChromosome("7");
        Long startPos = new Long(53700000);
        Long endPos = new Long(57600000);
        constraints.setPositions(startPos, endPos);
        constraints.setQuantitationType(QuantitationTypes.COPY_NUMBER);
        
        // Must put constraints in an array to be used in call
        constraintsArray = new BioAssayDataConstraints[1];
        constraintsArray[0] = constraints;
    }
    
    public void testGetClientData() throws Exception {
        
        Experiment exp = null;
        Collection bioAssays = new ArrayList();
        
        // run tests for first set of experiments
        experiments = service.getClientData(constraintsArray, experimentIds1, clientID);
        assertTrue(experiments != null);
        assertEquals(experiments.length, 1);
        exp = experiments[0];
        bioAssays = (ArrayList) exp.getBioAssays();
        assertTrue(bioAssays.size() > 0);
        
        // run tests for second set of experiments
        experiments = service.getClientData(constraintsArray, experimentIds1, clientID);
        assertTrue(experiments != null);
        assertEquals(experiments.length, 4);
        for (int i = 0; i < 4; i++) {
            exp = experiments[0];
            bioAssays = (ArrayList) exp.getBioAssays();
            assertTrue(bioAssays.size() > 0);
        }
    }

}
