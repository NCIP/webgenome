/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/service/AcghService.java,v $
$Revision: 1.4 $
$Date: 2006-05-11 18:19:57 $

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


package org.rti.webcgh.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

import org.rosuda.JRclient.REXP;
import org.rosuda.JRclient.RList;
import org.rosuda.JRclient.RSrvException;
import org.rosuda.JRclient.Rconnection;
import org.rti.webcgh.analytic.AcghData;
import org.rosuda.JRclient.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Vector;

import com.Ostermiller.util.ExcelCSVParser;

public class AcghService {
	
	private String rAddy = "localhost";
	
	
	public void run(AcghData data) {
		
		
		
//<<<<<<< AcghService.java
//		System.out.println("\n"
//						+ "*****************************************************\n"
//						+ "* Example Java program to test Rserve functionality *\n"
//						+ "* Mike Zmuda 2005-2006 RTI Intl                     *\n"
//						+ "****************************************************/\n");
//
//		try {
//			// New Rserve connection
//			System.out.print("Trying new Rserve connection to ");
////			String rAddy = "localhost";
////			if (args.length >= 1)
////				rAddy = args[0];
//			System.out.print(rAddy + "...");
//			Rconnection c = new Rconnection(rAddy);
//			System.out.println("OK!\n");
//
//			// read R commands from external R file
//			BufferedReader rcmdIn = new BufferedReader(new FileReader("unit_test-data/aCGH_smooth.R"));
//			Vector rcmdList = new Vector();
//			String line = rcmdIn.readLine();
//			while (line != null) {
//				rcmdList.addElement(line);
//				line = rcmdIn.readLine();
//			}
//			rcmdIn.close();
//
//			// read csv files
//			System.out.print("Reading data files...");
////			ExcelCSVParser ecp1 = new ExcelCSVParser(new FileReader("data/T_GPR_R_aCGH_cloneinfo_NoDups.csv"));
////			ExcelCSVParser ecp2 = new ExcelCSVParser(new FileReader("data/T_GPR_R_aCGH_log2ratios_NoDups.csv"));
//			
//			ExcelCSVParser ecp1 = new ExcelCSVParser(new FileReader("unit_test-data/T_GPR_R_aCGH_cloneinfo_NoDups.csv"));
//			ExcelCSVParser ecp2 = new ExcelCSVParser(new FileReader("unit_test-data/T_GPR_R_aCGH_log2ratios_NoDups.csv"));
//			
//			System.out.println("OK");
//
//			// create matrix table from files
//			System.out.print("Creating data matrix...");
//			String[][] cloneInfoFile = ecp1.getAllValues();
//			String[][] log2ratiosFile = ecp2.getAllValues();
//			System.out.println("OK");
//
//			// grab headers
//			// String[] cloneInfoH = cloneInfoFile[0];
//			// String[] log2ratiosH = log2ratiosFile[0];
//
//			// flip contents
//			System.out.print("Formatting matrix contents...");
//			String[][] cloneInfoC = flipContent(cloneInfoFile);
//			String[][] log2ratiosC = flipContent(log2ratiosFile);
//			System.out.println("OK");
//
//			// assign headers
//			// c.assign("clonesinfoHeadersStr",new
//			// REXP(REXP.XT_ARRAY_STR,cloneInfoH));
//			// c.assign("log2ratiosHeadersStr",new
//			// REXP(REXP.XT_ARRAY_STR,log2ratiosH));
//
//			// assign rows
//			System.out.print("Assigning matrix columns to R variables...");
//			c.assign("clonesInfo_n", new REXP(REXP.XT_ARRAY_INT,
//					convert2int(cloneInfoC[0])));
//			c.assign("clonesInfo_Clone", new REXP(REXP.XT_ARRAY_STR,
//					cloneInfoC[1]));
//			c.assign("clonesInfo_Target", new REXP(REXP.XT_ARRAY_STR,
//					cloneInfoC[2]));
//			c.assign("clonesInfo_Chrom", new REXP(REXP.XT_ARRAY_INT,
//					convert2int(cloneInfoC[3])));
//			c.assign("clonesInfo_kb", new REXP(REXP.XT_ARRAY_INT,
//					convert2int(cloneInfoC[4])));
//
//			c.assign("log2ratios_ID", new REXP(REXP.XT_ARRAY_STR,
//					log2ratiosC[0]));
//			c.assign("log2ratios_log2TR", new REXP(REXP.XT_ARRAY_DOUBLE,
//					convert2double(log2ratiosC[1])));
//			System.out.println("OK");
//
//			System.out.println("Creating R data.frame's...");
//			c.eval("clones_info <-data.frame(n=clonesInfo_n, Clone=clonesInfo_Clone, Target=clonesInfo_Target, Chrom=clonesInfo_Chrom, kb=clonesInfo_kb)");
//			c.eval("log2_ratios <-data.frame(ID=log2ratios_ID, log2TR=log2ratios_log2TR)");
//			System.out.println("OK");
//
//			String cmd;
//			String incomplete_cmd = "";
//			boolean isCompleteCmd = true;
//			int sum;
//			// loop through commands and execute each
//			for (int i = 0; i < rcmdList.size(); i++) {
//				cmd = (String) rcmdList.elementAt(i);
//
//				// some long commands across lines, to make sure whether cmd is
//				// complete
//				if (!isCompleteCmd) {
//					cmd = incomplete_cmd + cmd;
//				}
//
//				sum = 0;
//				for (int t = 0; t < cmd.length(); t++) {
//					if (cmd.charAt(t) == '(') {
//						sum++;
//					} else if (cmd.charAt(t) == ')') {
//						sum--;
//					}
//				}
//
//				if (sum % 2 == 0) { // cmd is complete
//					isCompleteCmd = true;
//					incomplete_cmd = "";
//
//					if (!cmd.equals("") && !cmd.startsWith("#")) {
//						// either blank string or comment is not a valid
//						// expression for Rserve
//						System.out.print("Trying R command: \"" + cmd + "\"...");
//						c.voidEval(cmd);
//
//						// retrieve smoothed values from R
//						if (cmd.startsWith("result <-")) {
//							RList l = c.eval(cmd).asList();
//							// double[] lx = (double[]) l.at(4).getContent();
//							// //observed values
//							double[] lx = (double[]) l.at(3).getContent(); // smoothed
//																			// values
//							// double[] lx = (double[]) l.at(2).getContent();
//							// //states
//							// double[] lx = (double[]) l.at(1).getContent();
//							// //kb
//							// double[] lx = (double[]) l.at(0).getContent();
//							// //chromosome
//
//							PrintWriter smoothOut = new PrintWriter(
//									new BufferedWriter(new FileWriter("resultJ.txt")));
//							for (int x = 0; x < lx.length; x++) {
//								smoothOut.println(String.valueOf(lx[x]));
//							}
//							smoothOut.close();
//						}
//						System.out.println("OK");
//					}
//				} else { // incomplete cmd
//					isCompleteCmd = false;
//					incomplete_cmd = cmd;
//				}
//			}
//
//			// Exit
//			System.out.println("\n\nTry OK. Exiting...");
//		} catch (RSrvException rse) {
//			System.out.println("Rserve exception: " + rse.getMessage());
//		} catch (Exception e) {
//			System.out.println("Something went wrong, but it's not the Rserve: "
//							+ e.getMessage());
//			e.printStackTrace();
//		}
//
//	}
//	
//	/** flip matrix omitting headers **/
//	public static String[][] flipContent(String[][] values) {
//		String[][] newValues = new String[values[0].length][values.length-1];
//		for(int i = 1 ; i < values.length ; i++)
//			for(int j = 0 ; j < values[i].length ; j++)
//				newValues[j][i-1] = values[i][j].trim();
//		return newValues;
//	}
//
//	/** convert string array to int array **/
//	public static int[] convert2int(String[] s) throws NumberFormatException {
//		int[] intArr = new int[s.length];
//		for(int i = 0 ; i < s.length ; i++)
//			intArr[i] = Integer.parseInt(s[i]);
//		return intArr;
//	}
//
//	/** convert string array to double array **/
//	public static double[] convert2double(String[] s) throws NumberFormatException {
//		double[] dblArr = new double[s.length];
//		for(int i = 0 ; i < s.length ; i++)
//			dblArr[i] = Double.parseDouble(s[i]);
//		return dblArr;
//	}
	
	
	
//=======
		try {
			// New Rserve connection
			System.out.print("Trying new Rserve connection to localhost...");
			Rconnection c = new Rconnection("localhost");
			System.out.println("OK!\n");
			
			// load aCGH library
			c.eval("library(aCGH)");
			
			// prepare data before running R commands from file
			int[] narray = new int[data.getSize()];
			for(int i = 0 ; i < data.getSize() ; i++) {narray[i] = i+1;}
			c.assign("clonesInfo_n",new REXP(REXP.XT_ARRAY_INT,narray));
			c.assign("clonesInfo_Clone", new REXP(REXP.XT_ARRAY_STR,data.getClones()));
			c.assign("clonesInfo_Target", new REXP(REXP.XT_ARRAY_STR,data.getTargets()));
			c.assign("clonesInfo_Chrom", new REXP(REXP.XT_ARRAY_INT,data.getChromosomes()));
			c.assign("clonesInfo_kb", new REXP(REXP.XT_ARRAY_INT,data.getPositions()));
			c.assign("log2ratios_log2TR", new REXP(REXP.XT_ARRAY_DOUBLE,data.getLog2Ratios()));
			
			c.eval("clones_info <-data.frame(n=clonesInfo_n, Clone=clonesInfo_Clone, Target=clonesInfo_Target, Chrom=clonesInfo_Chrom, kb=clonesInfo_kb)");
			c.eval("log2_ratios <-data.frame(ID=clonesInfo_Clone, log2TR=log2ratios_log2TR)");
			
			c.eval("capture.output(log2_ratios, file = \"C:/test/log2_ratiosx.txt\", append = FALSE)");
			c.eval("capture.output(clones_info, file = \"C:/test/clones_infox.txt\", append = FALSE)");

			
			// read R commands from external R file
		    BufferedReader rcmdIn = new BufferedReader(new FileReader("unit_test-data/aCGH_smooth.R"));
		    Vector rcmdList = new Vector();
		    String line = rcmdIn.readLine();
		    while (line != null) {
		    	rcmdList.addElement(line);
		    	line = rcmdIn.readLine();
		    }
		    rcmdIn.close();

            String cmd;
            String incomplete_cmd = "";
            boolean isCompleteCmd = true;
            int sum;
            // loop through commands and execute each
			for(int i = 0 ; i < rcmdList.size() ; i++) {
				cmd = (String) rcmdList.elementAt(i);
				
				// some long commands across lines, to make sure whether cmd is complete
				if (!isCompleteCmd) {
					cmd = incomplete_cmd + cmd;
				}
				
				sum = 0;
				for (int t = 0 ; t < cmd.length() ; t++) {
					if (cmd.charAt(t) == '(') {
						sum++;
					}
					else if (cmd.charAt(t) == ')') {
						sum--;
					}
				}
				
				if (sum%2 == 0) { // cmd is complete
					isCompleteCmd = true;
					incomplete_cmd = "";
				    
					if (!cmd.equals("") && !cmd.startsWith("#")) {
						// either blank string or comment is not a valid expression for Rserve
					    System.out.print("Trying R command: \"" + cmd + "\"...");
					   
					    c.voidEval(cmd);
					    
					    // retrieve smoothed values from R
					    if (cmd.startsWith("result <-")) {
					    	RList l = c.eval(cmd).asList();
					    	//double[] lx = (double[]) l.at(4).getContent(); //observed values
					    	double[] lx = (double[]) l.at(3).getContent(); //smoothed values			
					    	//double[] lx = (double[]) l.at(2).getContent(); //states
					    	//double[] lx = (double[]) l.at(1).getContent(); //kb
					    	//double[] lx = (double[]) l.at(0).getContent(); //chromosome
					    	
					    	data.setSmoothedRatios(lx);
					    }
					    
					    System.out.println("OK");
					    
					}
				}
				else { // incomplete cmd
					isCompleteCmd = false;
					incomplete_cmd = cmd;
				}
			}
			
			// Exit
			System.out.println("\n\nTry OK. Exiting...");
		}
		catch(RSrvException rse) {
			System.out.println("Rserve exception: "+rse.getMessage());
		}
		catch(Exception e) {
			System.out.println("Something went wrong, but it's not the Rserve: "+e.getMessage());
			e.printStackTrace();
		}
//>>>>>>> 1.3
	}

}
