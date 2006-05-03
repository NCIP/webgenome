/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/AcghData.java,v $
$Revision: 1.2 $
$Date: 2006-05-03 23:47:55 $

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


package org.rti.webcgh.analytic;

import java.io.FileReader;
import com.Ostermiller.util.ExcelCSVParser;

public class AcghData {

	private double[] log2Ratios;      // log2 ratios of copy number changes
						              // rows correspond to the clones and columns to the samples
	private String[] clones;          // clone name
	private String[] targets;         // unique ID, e.g. Well ID
	private int[] chromosomes;        // chromosome number
	                                  // X chromosome = 23 in human and 20 in mouse,
	                                  // Y chromosome = 24 in human and 21 in mouse
	private int[] positions;          // kb position on the chromosome
	private double[] smoothedRatios;  // smoothed value of log2 ratio
	private int size;                 // number of clones/number of rows
	
	public AcghData() {
//		loadDataFromFiles();
	}
	
	private void loadDataFromFiles() {
		try {
		    //read csv files
		    System.out.print("Reading data files...");
		    ExcelCSVParser ecp1 = new ExcelCSVParser(new FileReader("unit_test-data/T_GPR_R_aCGH_cloneinfo_NoDups.csv"));
		    ExcelCSVParser ecp2 = new ExcelCSVParser(new FileReader("unit_test-data/T_GPR_R_aCGH_log2ratios_NoDups.csv"));
		    System.out.println("OK");
		    
		    // create matrix table from files
			System.out.print("Creating data matrix...");
			String[][] cloneInfoFile = ecp1.getAllValues();
			String[][] log2ratiosFile = ecp2.getAllValues();
			System.out.println("OK");
			
			size = cloneInfoFile.length - 1; // fine line is header
			log2Ratios = new double[size];
			clones = new String[size];
			targets = new String[size];
			chromosomes = new int[size];
			positions = new int[size]; 
			smoothedRatios = new double[size];
			
			for(int i = 0 ; i < size ; i++) {
				clones[i] = cloneInfoFile[i+1][1];
				targets[i] = cloneInfoFile[i+1][2];
				chromosomes[i] = Integer.parseInt(cloneInfoFile[i+1][3].trim());
				positions[i] = Integer.parseInt(cloneInfoFile[i+1][4].trim());
				if (!cloneInfoFile[i+1][1].equals(log2ratiosFile[i+1][0])) 
				    {System.out.println("two files are not perfect match at "+i+"th clone.");}
				log2Ratios[i] = Double.parseDouble(log2ratiosFile[i+1][1].trim());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


	
	public double[] getLog2Ratios() {
	    return log2Ratios;
	}
	public String[] getClones() {
	    return clones;
	}
	public String[] getTargets() {
	    return targets;
	}
	public int[] getChromosomes() {
	    return chromosomes;
	}
	public int[] getPositions() {
	    return positions;
	}
	public double[] getSmoothedRatios() {
	    return smoothedRatios;
	}
	public int getSize() {
	    return size;
	}
	
	
	
	public void setSmoothedRatios(double[] smooth) {
	    smoothedRatios = smooth;
	}

	public void setChromosomes(int[] chromosomes) {
		this.chromosomes = chromosomes;
	}

	public void setClones(String[] clones) {
		this.clones = clones;
	}

	public void setLog2Ratios(double[] log2Ratios) {
		this.log2Ratios = log2Ratios;
	}

	public void setPositions(int[] positions) {
		this.positions = positions;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setTargets(String[] targets) {
		this.targets = targets;
	}
	
	
}
