/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/unit_test/AcghDataTransformerTester.java,v $
$Revision: 1.5 $
$Date: 2006-09-05 14:06:45 $

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

import org.rti.webcgh.analytic.AcghData;
import org.rti.webcgh.analytic.AcghDataTransformer;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.ArrayDatumFactory;
import org.rti.webcgh.array.ArrayDatumIterator;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.service.util.AcghService;

import junit.framework.TestCase;

public class AcghDataTransformerTester extends TestCase {
	
	protected ArrayDatumFactory arrayDatumFactory = null;
	protected Experiment experiment = null;
	protected AcghDataTransformer acghDataTransformer = null;
	protected AcghService acghService = null;
	
	protected void setUp() throws Exception {
		// instantiate attributes of tester class
		this.arrayDatumFactory = new ArrayDatumFactory("GenomeAseembly name", QuantitationType.LOG_2_RATIO, "Xenopus", "laevis");
		this.acghDataTransformer = new AcghDataTransformer();
		this.acghService = new AcghService();
		
		// set up a new BioAssay object
		BioAssay bioAssay = new BioAssay();		

		// use the following pre-generated values to create ArrayDatum objects
		
		
//		String[] reporterNames = {"Reporter A", "Reporter B", "Reporter C", "Reporter D", "Reporter E", "Reporter F", "Reporter G", "Reporter H", "Reporter I", "Reporter J", "Reporter K", "Reporter L", "Reporter M", "Reporter N", "Reporter O", "Reporter P", "Reporter Q", "Reporter R", "Reporter S", "Reporter T", "Reporter U", "Reporter V", "Reporter W", "Reporter X", "Reporter Y", "Reporter Z"};
//		long[] reporterIds = {100001, 100002, 100003, 100004, 100005, 100006, 100007, 100008, 100009, 100010, 100011, 100012, 100013, 100014, 100015, 100016, 100017, 100018, 100019, 100020, 100021, 100022, 100023, 100024, 100025, 100026};
//		short[] chromosomeNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 1, 2, 3};
//		long[] locations = {1908480, 715714, 1531876, 2562561, 965964, 2516885, 2666500, 2064606, 3023104, 7951085, 6434298, 5978488, 7226692, 5202946, 1796681, 336979, 105089, 5676835, 7024575, 6205613, 7225106, 2626146, 2172349, 2922583, 3329065, 1391403};
//		String[] genomeAssemblyNames = {"Genome Assembly A", "Genome Assembly B", "Genome Assembly C", "Genome Assembly D", "Genome Assembly E", "Genome Assembly F", "Genome Assembly G", "Genome Assembly H", "Genome Assembly I", "Genome Assembly J", "Genome Assembly K", "Genome Assembly L", "Genome Assembly M", "Genome Assembly N", "Genome Assembly O", "Genome Assembly P", "Genome Assembly Q", "Genome Assembly R", "Genome Assembly S", "Genome Assembly T", "Genome Assembly U", "Genome Assembly V", "Genome Assembly W", "Genome Assembly X", "Genome Assembly Y", "Genome Assembly Z"};
//		float[] quantValues = {5.67f, 1.66f, 8.38f, 4.70f, 4.92f, 1.40f, 4.87f, 0.07f, 1.17f, 8.75f, 6.03f, 3.67f, 0.38f, 7.07f, 2.33f, 3.58f, 9.56f, 2.63f, 0.67f, 3.55f, 6.06f, 3.74f, 1.32f, 0.54f, 1.05f, 2.95f};
		
		
		String[] reporterNames = {"RP11-82D16", "RP11-62M23", "RP11-111O5", "RP11-51B4", "RP11-60J11", "RP11-813J5", "RP11-199O1", "RP11-188F7", "RP11-178M15", "RP11-219F4", "RP11-265F14", "RP11-145C4", "RP11-224F8", "CTD-2128D14", "RP11-139H5", "CTD-2194B23", "RP11-72I19", "RP11-57F20", "RP11-285H13", "RP11-4O6", "CTD-2051J11", "RMC01P057", "RP11-62B23", "RP11-104J13", "CTD-2098K5", "RP11-4P6"};
		//String[] reporterIds = {"HumArray2H11_C9", "HumArray2H10_N30", "HumArray2H10_B18", "HumArray2H10_Q30", "HumArray2H10_T30", "HumArray2H10_B19", "HumArray2H10_W30", "HumArray2H9_C14", "HumArray2H9_F14", "HumArray2H9_I14", "HumArray2H9_A23", "HumArray2H9_L14", "HumArray2H9_O14", "HumArray2H11_L8", "HumArray2H9_B4", "HumArray2H10_E19", "HumArray2H9_U14", "HumArray2H9_R14", "HumArray2H9_X14", "HumArray2H9_C17", "HumArray2H10_S36", "HumArray2H11_I13", "HumArray2H9_D23", "HumArray2H9_F17", "HumArray2H10_H19", "HumArray2H9_I17"};
		long[] reporterIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
		short[] chromosomeNumbers = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		long[] locations = {2009, 3368, 4262, 6069, 6817, 9498, 10284, 12042, 13349, 14391, 15121, 18460, 19980, 21392, 21963, 22710, 22834, 23269, 26900, 27542, 27543, 27560, 30378, 30834, 32248, 32880};
		String[] genomeAssemblyNames = {"hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18", "hg18"};
		float[] quantValues = {0.202059711f, 0.173037143f, 0.121655619f, 0.090844669f, 0.170214084f, -0.025518441f, 0.046834206f, 0.068333228f, -0.106817212f, 0.100321085f, 0.007226233f, 0.074142148f, 0.094415251f, 0.094550487f, 0.133233632f, 0.200208669f, -0.03669642f, 0.083349635f, 0.059691028f, -0.020037068f, 0.149591568f, -0.003424064f, 0.145056477f, 0.024348312f, 0.118361578f, -0.017729363f};		
		
		// add ArrayDatum objects to BioAssay object
		for (int i = 0; i < reporterNames.length; i++) {
			// create ArrayDatum object
			ArrayDatum newArrayDatum = this.arrayDatumFactory.newArrayDatum(reporterNames[i], chromosomeNumbers[i], locations[i], quantValues[i]);
			newArrayDatum.getReporter().setId(reporterIds[i]);
			
			// add to BioAssay object
			bioAssay.add(newArrayDatum);
		}
		
		// add BioAssay object to Experiment object
		this.experiment = new Experiment("experiment name", "description", "database name", false, "user name");
		this.experiment.add(bioAssay);
		
	}
	
	public void testExperimentConstruction() throws Exception {
//		ArrayDatumIterator arrayDatumIter = this.experiment.arrayDatumIterator();
		ArrayDatumIterator arrayDatumIter = this.experiment.bioAssayIterator().next().arrayDatumIterator();
		int arrayDatumSize = 0;
		for ( ; arrayDatumIter.hasNext() ; ) {
			arrayDatumSize++;
			arrayDatumIter.next();
		}
		assertEquals(26, arrayDatumSize);
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
	
	public void testTransformAndService() throws Exception {
		Experiment exp1 = this.experiment;
		AcghData acghData = this.acghDataTransformer.transform(exp1);
		this.acghService.run(acghData);
		
		// now that service has been run, the acghData object now should contain the smoothed values array
		assertEquals(26, acghData.getSmoothedRatios().length);
	}
	
	
	public void testTransformAndServiceandReverseTransform() throws Exception {
		Experiment exp1 = this.experiment;
		AcghData acghData = this.acghDataTransformer.transform(exp1);
		this.acghService.run(acghData);
		
		Experiment exp2 = this.acghDataTransformer.transform(acghData, exp1);
		
		ArrayDatumIterator arrayDatumIter1 = exp1.bioAssayIterator().next().arrayDatumIterator();
		ArrayDatumIterator arrayDatumIter2 = exp2.bioAssayIterator().next().arrayDatumIterator();
		
		int size = 0;
		for ( ; arrayDatumIter1.hasNext() ; ) {
			ArrayDatum arrayDatum1 = arrayDatumIter1.next();
			ArrayDatum arrayDatum2 = arrayDatumIter2.next();
			
			float log2Ratio1 = arrayDatum1.getQuantitation().getValue();
			String clone1 = arrayDatum1.getReporter().getName();
			String target1 = arrayDatum1.getReporter().getId().toString();
			short chromosome1 = arrayDatum1.chromosome().getNumber();
			long position1 = arrayDatum1.getGenomeLocation().getLocation();
			
			float log2Ratio2 = arrayDatum2.getQuantitation().getValue();
			String clone2 = arrayDatum2.getReporter().getName();
			String target2 = arrayDatum2.getReporter().getId().toString();
			short chromosome2 = arrayDatum2.chromosome().getNumber();
			long position2 = arrayDatum2.getGenomeLocation().getLocation();
			
			assertEquals(log2Ratio1, log2Ratio2);
			assertEquals(clone1, clone2);
			assertEquals(target1, target2);
			assertEquals(chromosome1, chromosome2);
			assertEquals(position1, position2);
			
			size++;
		}
		
		System.out.println("size of array data is " + size + "\n");
	}

}
