/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/unit_test/AcghDataTransformerTester.java,v $
$Revision: 1.1 $
$Date: 2006-05-02 21:39:30 $

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


package org.rti.webcgh.analytic.unit_test;

import java.util.ArrayList;
import java.util.Collection;

import org.rti.webcgh.analytic.AcghData;
import org.rti.webcgh.analytic.AcghDataTransformer;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.ArrayDatumFactory;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.QuantitationType;

import junit.framework.TestCase;

public class AcghDataTransformerTester extends TestCase {
	
	protected ArrayDatumFactory arrayDatumFactory = null;
	protected Experiment experiment = null;
	protected AcghDataTransformer acghDataTransformer = null;
	
	protected void setUp() {
		// instantiate attributes of tester class
		this.arrayDatumFactory = new ArrayDatumFactory("GenomeAseembly name", QuantitationType.LOG_2_RATIO, "Xenopus", "laevis");
		this.acghDataTransformer = new AcghDataTransformer();
		
		// set up a new BioAssay object
		BioAssay bioAssay = new BioAssay();		

		// use the following pre-generated values to create ArrayDatum objects
		String[] reporterNames = {"Reporter A", "Reporter B", "Reporter C", "Reporter D", "Reporter E", "Reporter F", "Reporter G", "Reporter H", "Reporter I", "Reporter J", "Reporter K", "Reporter L", "Reporter M", "Reporter N", "Reporter O", "Reporter P", "Reporter Q", "Reporter R", "Reporter S", "Reporter T", "Reporter U", "Reporter V", "Reporter W", "Reporter X", "Reporter Y", "Reporter Z"};
		short[] chromosomeNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 1, 2, 3};
		long[] locations = {1908480, 715714, 1531876, 2562561, 965964, 2516885, 2666500, 2064606, 3023104, 7951085, 6434298, 5978488, 7226692, 5202946, 1796681, 336979, 105089, 5676835, 7024575, 6205613, 7225106, 2626146, 2172349, 2922583, 3329065, 1391403};
		String[] genomeAssemblyNames = {"Genome Assembly A", "Genome Assembly B", "Genome Assembly C", "Genome Assembly D", "Genome Assembly E", "Genome Assembly F", "Genome Assembly G", "Genome Assembly H", "Genome Assembly I", "Genome Assembly J", "Genome Assembly K", "Genome Assembly L", "Genome Assembly M", "Genome Assembly N", "Genome Assembly O", "Genome Assembly P", "Genome Assembly Q", "Genome Assembly R", "Genome Assembly S", "Genome Assembly T", "Genome Assembly U", "Genome Assembly V", "Genome Assembly W", "Genome Assembly X", "Genome Assembly Y", "Genome Assembly Z"};
		float[] quantValues = {5.67f, 1.66f, 8.38f, 4.70f, 4.92f, 1.40f, 4.87f, 0.07f, 1.17f, 8.75f, 6.03f, 3.67f, 0.38f, 7.07f, 2.33f, 3.58f, 9.56f, 2.63f, 0.67f, 3.55f, 6.06f, 3.74f, 1.32f, 0.54f, 1.05f, 2.95f};
		
		// add ArrayDatum objects to BioAssay object
		for (int i = 0; i < reporterNames.length; i++) {
			ArrayDatum newArrayDatum = this.arrayDatumFactory.newArrayDatum(reporterNames[i], chromosomeNumbers[i], locations[i], genomeAssemblyNames[i], quantValues[i]);
			bioAssay.add(newArrayDatum);
		}
		
		// add BioAssay object to Experiment object
		this.experiment = new Experiment("experiment name", "description", "database name", false, "user name");
		this.experiment.add(bioAssay);
		
	}
	
	public void testTransform() throws Exception {
		Experiment exp1 = this.experiment;
		AcghData acghData = this.acghDataTransformer.transform(exp1);
		Experiment exp2 = null;
		exp2 = this.acghDataTransformer.transform(acghData, exp1);
		
		assertTrue(exp2.sameExperiment(exp1));
		// TODO: need to additionally have a more complete way of testing equality; 
		// the sameExperiment() method merely checks the Experiment's name & database name
		
		
	}

}
