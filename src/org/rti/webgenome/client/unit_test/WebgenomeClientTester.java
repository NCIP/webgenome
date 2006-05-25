/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webgenome/client/unit_test/WebgenomeClientTester.java,v $
$Revision: 1.2 $
$Date: 2006-05-25 19:41:30 $

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

package org.rti.webgenome.client.unit_test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import gov.nih.nci.mageom.domain.BioAssayData.BioAssayDatum;

import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.ArrayDatumIterator;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayData;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.GenomeLocation;
import org.rti.webcgh.array.Reporter;
import org.rti.webcgh.service.DomainObjectFactory;
import org.rti.webgenome.client.BioAssayDTO;
import org.rti.webgenome.client.BioAssayDatumDTO;
import org.rti.webgenome.client.DefBioAssayDTOImpl;
import org.rti.webgenome.client.DefBioAssayDatumDTOImpl;
import org.rti.webgenome.client.DefExperimentDTOImpl;
import org.rti.webgenome.client.DefReporterDTOImpl;
import org.rti.webgenome.client.ExperimentDTO;
import org.rti.webgenome.client.ReporterDTO;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WebgenomeClientTester extends TestCase {
    
    protected DomainObjectFactory objFactory = null;
    
    protected Experiment exp = null;
    protected Collection bioAssays = new ArrayList();
    
    protected ExperimentDTO expdto = null;
    protected BioAssayDTO badto1 = null;
    protected BioAssayDTO badto2 = null;
    protected BioAssayDatumDTO baddto1 = null;
    protected BioAssayDatumDTO baddto2 = null;
    protected BioAssayDatumDTO baddto3 = null;
    protected BioAssayDatumDTO baddto4 = null;
    protected BioAssayDatumDTO baddto5 = null;
    protected BioAssayDatumDTO baddto6 = null;
    protected BioAssayDatumDTO baddto7 = null;
    protected ReporterDTO reporterdto1 = null;
    protected ReporterDTO reporterdto2 = null;
    protected ReporterDTO reporterdto3 = null;
    protected ReporterDTO reporterdto4 = null;
    
    protected void setUp() throws Exception {
        
        // Set up reporter DTOs to be used by the bioassay DTOs
        this.reporterdto1 = new DefReporterDTOImpl("reporter1", "11", new Long(870000));
        this.reporterdto2 = new DefReporterDTOImpl("reporter2", "22", new Long(1345200));
        this.reporterdto3 = new DefReporterDTOImpl("reporter3", "13", new Long(3451000));
        this.reporterdto4 = new DefReporterDTOImpl("reporter4", "8", new Long(9274300));
        
        // Set up first bioassay DTO
        this.baddto1 = new DefBioAssayDatumDTOImpl(new Double(3.4), "quanttype1", reporterdto1);
        this.baddto2 = new DefBioAssayDatumDTOImpl(new Double(4.6), "quanttype1", reporterdto2);
        this.baddto3 = new DefBioAssayDatumDTOImpl(new Double(6.7), "quanttype1", reporterdto3);
        BioAssayDatumDTO[] baddtos = {baddto1, baddto2, baddto3};
        this.badto1 = new DefBioAssayDTOImpl("BioAssayDTO #1", "badto1", baddtos);
        
        // Set up second bioassay DTO
        this.baddto4 = new DefBioAssayDatumDTOImpl(new Double(0.3), "quanttype1", reporterdto1);
        this.baddto5 = new DefBioAssayDatumDTOImpl(new Double(2.4), "quanttype1", reporterdto2);
        this.baddto6 = new DefBioAssayDatumDTOImpl(new Double(8.2), "quanttype1", reporterdto3);
        this.baddto7 = new DefBioAssayDatumDTOImpl(new Double(4.9), "quanttype1", reporterdto4);
        BioAssayDatumDTO[] baddtos2 = {baddto4, baddto5, baddto6, baddto7};
        this.badto2 = new DefBioAssayDTOImpl("BioAssayDTO #2", "badto2", baddtos2);
        
        // Put bioassay DTOs into array, and create experiment DTO
        BioAssayDTO[] badtos = {badto1, badto2};
        this.expdto = new DefExperimentDTOImpl("experiment234234", badtos);
        
        //use getExperiment to convert ExperimentDTO into normalized Experiment object
        this.objFactory = new DomainObjectFactory();
        this.exp = this.objFactory.getExperiment(expdto, "experiment234234");
        this.bioAssays = (ArrayList) exp.getBioAssays();
    }
    
    public void testGetExperiment() {
        
        // iterate over the bioAssays contained in Experiment
        Iterator i = bioAssays.iterator();
        while (i.hasNext()) {
            BioAssay bioAssay = (BioAssay) i.next();
            
            // do tests for first bioassay
            if (bioAssay.getName().equals("badto1")) {
                assertEquals("BioAssayDTO #1", bioAssay.getDescription());
                BioAssayData bad = bioAssay.getBioAssayData();
                int badcount = bad.numArrayDatum();
                assertEquals(3, badcount);
                
                //iterate over the arraydata contained in the BioAssayData of BioAssay
                ArrayDatumIterator j = bad.arrayDatumIterator();
                while (j.hasNext()) {
                    ArrayDatum ad = j.next();
                    Reporter reporter = ad.getReporter();
                    // check correctness for reporter #1 here
                    if (reporter.getName().equals("reporter1")) {
                        GenomeLocation loc = reporter.getGenomeLocation();
                        assertTrue((new Long(870000)).longValue() == loc.getLocation());
                        assertEquals(11, loc.getChromosome().getNumber());
                    }
                    
                    // make sure Reporter and ReporterMapping are linked to each other
                    // a simple pointer check is sufficient and appropriate
                    assertTrue(reporter == reporter.getReporterMapping().getReporter());
                }
            }
            //do tests for second bioassay
            else if (bioAssay.getName().equals("badto2")) {
                assertEquals("BioAssayDTO #2", bioAssay.getDescription());
                BioAssayData bad = bioAssay.getBioAssayData();
                int badcount = bad.numArrayDatum();
                assertEquals(4, badcount);
                
                //iterate over the arraydata contained in the BioAssayData of BioAssay
                ArrayDatumIterator j = bad.arrayDatumIterator();
                while (j.hasNext()) {
                    ArrayDatum ad = j.next();
                    Reporter reporter = ad.getReporter();
                    // check correctness for reporter #1 here
                    if (reporter.getName().equals("reporter1")) {
                        GenomeLocation loc = reporter.getGenomeLocation();
                        assertTrue((new Long(870000)).longValue() == loc.getLocation());
                        assertEquals(11, loc.getChromosome().getNumber());
                    }
                    
                    // make sure Reporter and ReporterMapping are linked to each other
                    // a simple pointer check is sufficient and appropriate
                    assertTrue(reporter == reporter.getReporterMapping().getReporter());
                }
            }
        }
    }
    
    /**
     * Return test suite
     * @return Test suite
     */
    public static Test suite() {
        return new TestSuite(WebgenomeClientTester.class);
    }

}
