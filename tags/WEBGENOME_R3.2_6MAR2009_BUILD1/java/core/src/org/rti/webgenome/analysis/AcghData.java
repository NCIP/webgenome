/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/analysis/AcghData.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $



*/


package org.rti.webgenome.analysis;

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
