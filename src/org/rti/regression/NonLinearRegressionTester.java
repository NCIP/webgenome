/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/NonLinearRegressionTester.java,v $
$Revision: 1.2 $
$Date: 2006-05-26 17:13:13 $

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
“Research Triangle Institute? and "RTI" must not be used to endorse or promote 
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

package org.rti.regression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import org.apache.commons.collections.primitives.DoubleList;
import org.apache.commons.collections.primitives.ArrayDoubleList;
import org.rti.webcgh.util.IOUtils;

import junit.framework.TestCase;
/**
 * 
 */
public class NonLinearRegressionTester extends TestCase {
	
	private String dataDir = null;
	
	private static final Logger logger = 
		Logger.getLogger(NonLinearRegressionTester.class.getName());

	DoubleList y1ArrayTestData = new ArrayDoubleList();
	DoubleList y2ArrayTestData = new ArrayDoubleList();
	/**
	 * @param id
	 */
	public void setUp( String id ){

    	String record = null;
        String dlim = ",";
        
        int recCount = 0; 
        try { 
           BufferedReader br = IOUtils.getReader(id);
           record = new String();
         
           // Get each line
           while ((record = br.readLine()) != null) {
              recCount++;
              // Get each entry
              StringTokenizer st = new StringTokenizer(record, dlim);
       		  // Read data from a file into a DoubleList object
              y1ArrayTestData.add( Double.parseDouble(st.nextToken()) );
              //System.out.println("y1ArrayTestData[" + i "]=" + id);
              y2ArrayTestData.add( Double.parseDouble(st.nextToken()) );
           } 
           
           
        } catch (IOException e) { 
           // catch possible io errors from readLine()
           System.out.println("Can't read file " + id + " - IOException error!");
           e.printStackTrace();
        }
	}

	/**
	 * @param outname
	 * @param i1
	 * @param i2
	 * @param results
	 */	
	public void writeResults ( String outname, DoubleList i1, DoubleList i2, DoubleList results){
		System.out.println("Testing write out functionality:" + outname + ".csv");
		try {
	        String outDir = "\\\\rtifile02\\webcgh\\Matt\\output";
	        BufferedWriter out = new BufferedWriter(new FileWriter(outDir + "\\" + outname + ".csv"));
//	        String A = null;
	        out.write("M,A,PredictedValue\n");
	        for (int i = 0; i < results.size(); i++){	        		        	
	        	out.write(String.valueOf(i1.get(i)) + "," +   
	        			  String.valueOf(i2.get(i)) + "," +
						  String.valueOf(results.get(i)) + "\n");
	        }	        	        
	        out.close();
	    } catch (IOException e) {
	    }
		
	}

	
	
	
//	public void testgetXdataSingleColor(){		
//		logger.debug("test getXdataSingleColor");
//		System.out.println("test getXdataSingleColor");		
//		setUp("test1-localRegression.csv");
//		
//		BioinfoMatrix x = NonLinearRegression.getXdataSingleColor(10);
//		logger.debug(x.toPrintableString());		
//		assertTrue(x.getEntry(5,0) == 6.0);	
//	}
//
//		
//	public void testGetWindowData() {
//
//		System.out.println("\n\n -----testGetWindowData -----");
////		logger.debug(x.toPrintableString());		
////		assertTrue(x.getEntry(5,0) == 6.0);
//
//		double percent = 1;
//		DoubleList y = null; 
//		setUp("test1-localRegression.csv");
//		
//		// Test Case 1: Full data, use 100% size window, even number of obs
////		NonLinearRegression.setArraySize(34);
////		NonLinearRegression.setWindowSize( NonLinearRegression.getArraySize(), percent);		
////	
////		System.out.println("arraySize: " + NonLinearRegression.getArraySize() + ":\n");
////		System.out.println("windowSize: " + NonLinearRegression.getWindowSize() + ":\n");
////
////		y = NonLinearRegression.getWindowData(0,y1ArrayTestData,y);
////		for (int i = 1; i < NonLinearRegression.getArraySize(); i ++){
////			y = NonLinearRegression.getWindowData(i,y1ArrayTestData,y);
////
////			System.out.println("getWindowData [" + i + "] RESULTS\n");			
////			for (int j = NonLinearRegression.getStartPoint(); j < NonLinearRegression.getEndPoint(); j++){
////				System.out.println("start=" + NonLinearRegression.getStartPoint() + " End=" + NonLinearRegression.getEndPoint() + 
////					" j=" + j + " y=" + y.get(j)+ "\n");
////			}		
////							
////		}
//
//
//		// Test Case 2: read in a subset of the data, use 100% size window, even number of obs
//		percent = .2;
//		int arraySize = 33;
//		int windowSize = 7;	
//		int startPoint = 0;
//		int endPoint = windowSize - 1;	
//		int prevStart = 0;
//		int prevEnd = 0;
//	
//		System.out.println("arraySize: " + arraySize);		
//		System.out.println("windowSize: " + windowSize);
//
//		y = NonLinearRegression.getWindowData(0,y1ArrayTestData,y,arraySize,windowSize,0,6,0,6);
//		for (int pointOfInterest = 1; pointOfInterest < arraySize; pointOfInterest ++){
//			
//			// Save previous start and end point for comparison 
//			prevStart = startPoint;
//			prevEnd = endPoint;
//
//			if (pointOfInterest < (windowSize / 2) ) {
//				startPoint = 0;
//				endPoint = windowSize - 1;		
//			}
//			// Point in High end tail 
//			else if (pointOfInterest >= (arraySize - (windowSize / 2) ) ) {
//				startPoint = (arraySize - 1) - windowSize + 1;
//				endPoint = arraySize - 1;
//			
//				// Account for Last point when it isn't picked up - increment one more unit to include last point
//				if ( pointOfInterest > endPoint ) {
//					startPoint = (startPoint + 1);
//					endPoint = endPoint + 1;		
//				}		
//			}
//			// Point in middle where symmetric window possible
//			else {
//				startPoint = pointOfInterest - windowSize/2;
//				endPoint = pointOfInterest + windowSize/2;
//			}
//
//			y = NonLinearRegression.getWindowData(pointOfInterest,y1ArrayTestData,y,arraySize,windowSize,startPoint,endPoint, prevStart, prevEnd);
//			
//			System.out.println("getWindowData [" + pointOfInterest + "] RESULTS");			
//			for (int j = 0; j < windowSize; j++){
//				System.out.println("start=" + startPoint + " End=" + endPoint + 
//					" j=" + j + " y=" + y.get(j));
//			}		
//			System.out.println();	
//		}
//
//
//		// Test Case 3: read in a subset of the data, use 100% size window, odd number of obs
////		percent = 1;			
////		NonLinearRegression.setArraySize(33);
////		NonLinearRegression.setWindowSize( NonLinearRegression.getArraySize(), percent);		
////	
////		System.out.print("arraySize: " + NonLinearRegression.getArraySize() + ":\n");
////		System.out.print("windowSize: " + NonLinearRegression.getWindowSize() + ":\n");
////
////		y = NonLinearRegression.getWindowData(0,y1ArrayTestData,y);
////		for (int i = 1; i < NonLinearRegression.getArraySize(); i ++){
////			y = NonLinearRegression.getWindowData(i,y1ArrayTestData,y);				
////		}
//
//
////		// Test Case 4: read in a subset of the data, use 20% size window, odd number of obs
////		percent = .2;
////		NonLinearRegression.setArraySize(33);
////		NonLinearRegression.setWindowSize( NonLinearRegression.getArraySize(), percent);		
////	
////		System.out.print("arraySize: " + NonLinearRegression.getArraySize() + ":\n");
////		System.out.print("windowSize: " + NonLinearRegression.getWindowSize() + ":\n");
////
////		y = NonLinearRegression.getWindowData(0,y1ArrayTestData,y);
////		for (int i = 1; i < NonLinearRegression.getArraySize(); i ++){
////			y = NonLinearRegression.getWindowData(i,y1ArrayTestData,y);				
////		}
////
////	
//	}
//
//
//	
//	public void testgetWeightsSingleColor(){
//		logger.debug("test getWeightSingleColor");
//		setUp("test1-localRegression.csv");
//		
//		double percent = .5;
//		BioinfoMatrix preweights = null;
//		DoubleList y = null;
//
//		// Test Case 1: Full data, use 50% size window, even number of obs
//		System.out.println("\n\n -----test getWeightsSingleColor-----");
//		System.out.println("Test Case 1: Full data, use 50% size window, even number of obs");
//		int arraySize = 12;
//		int windowSize = 5;
//		int startPoint = 0;
//		int endPoint = 5;		
//		int prevStart = 0;
//		int prevEnd = 0;
//			
//		System.out.println("arraySize: " + arraySize);
//		System.out.println("windowSize: " + windowSize);
//		System.out.println();
//
//		y = NonLinearRegression.getWindowData(0,y1ArrayTestData,y,12,5,0,5,0,5);
//		preweights = NonLinearRegression.getWeightsSingleColor(0, preweights,12,5,0,5);
//		System.out.println("getWeightsSingleColor RESULT[0]");
//		NonLinearRegression.printBioinfoMatrix(preweights);
//		System.out.println();
//		
//		for (int pointOfInterest = 1; pointOfInterest < arraySize; pointOfInterest ++){
//			prevStart = startPoint;
//			prevEnd = endPoint;
//			if (pointOfInterest < (windowSize / 2) ) {
//				startPoint = 0;
//				endPoint = windowSize - 1;		
//			}
//			// Point in High end tail 
//			else if (pointOfInterest >= (arraySize - (windowSize / 2) ) ) {
//				startPoint = (arraySize - 1) - windowSize + 1;
//				endPoint = arraySize - 1;
//			
//				// Account for Last point when it isn't picked up - increment one more unit to include last point
//				if ( pointOfInterest > endPoint ) {
//					startPoint = (startPoint + 1);
//					endPoint = endPoint + 1;		
//				}		
//			}
//			// Point in middle where symmetric window possible
//			else {
//				startPoint = pointOfInterest - windowSize/2;
//				endPoint = pointOfInterest + windowSize/2;
//			}
//			y = NonLinearRegression.getWindowData(pointOfInterest,y1ArrayTestData,y,arraySize,windowSize,startPoint,endPoint,prevStart,prevEnd);
//			preweights = NonLinearRegression.getWeightsSingleColor(pointOfInterest, preweights,arraySize,windowSize,startPoint,endPoint);
//								
//			System.out.println("getWeightsSingleColor RESULT[" + pointOfInterest + "]");						
//			NonLinearRegression.printBioinfoMatrix(preweights);			
//			System.out.println();						
//		}
//
////
////		// Test Case 2: Full data, use 50% size window, odd number of obs
////		System.out.print("\n\n -----testGetWeights -----\n");
////		System.out.print("Test Case 2: Full data, use 50% size window, odd number of obs\n");
////		
////		NonLinearRegression.setArraySize(13);
////		NonLinearRegression.setWindowSize( NonLinearRegression.getArraySize(), percent);		
////	
////		System.out.print("arraySize: " + NonLinearRegression.getArraySize() + ":\n");
////		System.out.print("windowSize: " + NonLinearRegression.getWindowSize() + ":\n");
////
////		y = NonLinearRegression.getWindowData(0,y1ArrayTestData,y);
////		preweights = NonLinearRegression.getWeights(0, preweights);
////		for (int i = 1; i < NonLinearRegression.getArraySize(); i ++){
////			y = NonLinearRegression.getWindowData(i,y1ArrayTestData,y);
////			preweights = NonLinearRegression.getWeights(i, preweights);					
////		}
////
////
////		// Test Case 3: Full data, use 100% size window, odd number of obs		
////		System.out.print("\n\n -----testGetWeights -----\n");
////		System.out.print("Test Case 3: Full data, use 100% size window, odd number of obs\n");
////		percent = 1;
////		
////		NonLinearRegression.setArraySize(11);
////		NonLinearRegression.setWindowSize( NonLinearRegression.getArraySize(), percent);		
////	
////		System.out.print("arraySize: " + NonLinearRegression.getArraySize() + ":\n");
////		System.out.print("windowSize: " + NonLinearRegression.getWindowSize() + ":\n");
////
////		y = NonLinearRegression.getWindowData(0,y1ArrayTestData,y);
////		preweights = NonLinearRegression.getWeights(0, preweights);
////		for (int i = 1; i < NonLinearRegression.getArraySize(); i ++){
////			y = NonLinearRegression.getWindowData(i,y1ArrayTestData,y);
////			preweights = NonLinearRegression.getWeights(i, preweights);					
////		}
//	}
//
//
////	public void testWeightedRegression(){
////		setUp("test1-localRegression.csv");
//	
////		double percent = .5;
////		double residual = 0;
////		BioinfoMatrix weights = null;
////		DoubleList y = null;
////		
////		// Test Case 1: Full data, use 50% size window, even number of obs
////		System.out.print("\n\n -----testWeightedRegression -----\n");
////		System.out.print("Test Case 1: Full data, use 50% size window, even number of obs\n");
////		NonLinearRegression.setArraySize(30);
////		NonLinearRegression.setWindowSize( NonLinearRegression.getArraySize(), percent);		
////			
////		System.out.print("arraySize: " + NonLinearRegression.getArraySize() + ":\n");
////		System.out.print("windowSize: " + NonLinearRegression.getWindowSize() + ":\n");
////
////		// Create X, Y, and Weight matrices
////		BioinfoMatrix x = NonLinearRegression.getXdata(NonLinearRegression.getWindowSize());
////		y = NonLinearRegression.getWindowData(0,y1ArrayTestData,y);
////		weights = NonLinearRegression.getWeights(0, weights);					
////		residual = NonLinearRegression.weightedRegression( new BioinfoMatrixImpl(y.toArray()), x, weights, 0);
////
////		// Get residual for each point based on the test matricies		                                        
////		for (int i = 1; i < NonLinearRegression.getArraySize(); i ++){
////			y = NonLinearRegression.getWindowData(i,y1ArrayTestData,y);
////			weights = NonLinearRegression.getWeights(i, weights);					
////			residual = NonLinearRegression.weightedRegression( new BioinfoMatrixImpl(y.toArray()), x, weights, i);
////		}
////				
////	 }
//	 
//	 
//	public void testnlRegressionSingleColor(){
//		logger.debug("test nlRegressionSingleColor");
//		System.out.println("\n----test nlRegressionSingleColor----");
//		
//		setUp("test1-localRegression.csv");
//					
//		double percent = .5;
//		double residual = 0;
//		DoubleList values = y1ArrayTestData;
//		DoubleList results = new ArrayDoubleList();
//		int arraySize = 30;
//		int windowSize = 15;
//		int startPoint = 0;
//		int endPoint = 14;		
//			
//		System.out.println("arraySize: " + arraySize);
//		System.out.println("windowSize: " + windowSize);
//
//		results = NonLinearRegression.nlRegressionSingleColor(values,arraySize,windowSize);
//
//		System.out.println("testnlRegressionSingleColor RESULTS");			
//		for (int i = 0; i < arraySize; i++){
//			System.out.println("\t[" + i + "]=\t" + results.get(i));			
//		}		
//				
//	}
//
//	 
//	public void testnlrNormalizeSingleColor(){		
//		logger.debug("\n----test nlrNormalizeSingleColor----");
//		System.out.println("\n----test nlrNormalizeSingleColor----");
//		
//		setUp("test1-localRegression.csv");
//		
//		DoubleList values = y1ArrayTestData;
//		DoubleList results = new ArrayDoubleList();
//		double percent=1;
//		int arraySize = values.size();
//		
//		System.out.println("arraySize: " + arraySize);
//	
//		results = NonLinearRegression.nlrNormalizeSingleColor(y1ArrayTestData,percent);
//
//		System.out.println("testnlrNormalizeSingleColor RESULTS");			
//		for (int i = 0; i < arraySize; i++){
//			System.out.println("\t[" + i + "]=\t" + results.get(i));			
//		}		
//	}  
//	
//	
//	
//	public void testnlrNormalizeTwoColor(){
//		logger.debug("\n----test nlrNormalizeTwoColor----");
//		setUp("test1-localRegression.csv");
//		
//		DoubleList color1Values = y1ArrayTestData;		
//		DoubleList color2Values = y2ArrayTestData;
//		DoubleList results = new ArrayDoubleList();
//		double percent = 1;
//		
//		System.out.println("testnlrNormalizeTwoColor start");
//		results = NonLinearRegression.nlrNormalizeTwoColor(color1Values, color2Values, percent);
//		System.out.println("testnlrNormalizeTwoColor RESULTS");
//		writeResults("testnlrNormalizeTwoColor-RESULTS1", color1Values, color2Values, results);
//	}
//
//	public void testnlrNormalizeTwoColor2(){
//		logger.debug("\n----test2 nlrNormalizeTwoColor----");
//		setUp("test1-localRegression.csv");
//		
//		DoubleList color1Values = y1ArrayTestData;		
//		DoubleList color2Values = y2ArrayTestData;
//		DoubleList results = new ArrayDoubleList();
//		double percent = .5;
//		
//		System.out.print("testnlrNormalizeSingleColor start\n");
//		results = NonLinearRegression.nlrNormalizeTwoColor(color1Values, color2Values, percent);
//		System.out.print("testnlrNormalizeSingleColor RESULTS percent=" + percent + "\n");			
//		writeResults("testnlrNormalizeTwoColor-RESULTS2", color1Values, color2Values, results);
//	}


//	public void testnlrNormalizeTwoColor3(){
//		logger.debug("\n----test2 nlrNormalizeTwoColor----");
//		setUp("T_GPR_Javainput2.csv");
//		//setUp("T_GPR_Javainput.csv");
//		
//		for (int i = 0; i < y1ArrayTestData.size(); i++){
//			System.out.println("y1ArrayTestData[" + i + "]=" + y1ArrayTestData.get(i));
//		}
//		
//		
//		DoubleList color1Values = y1ArrayTestData;		
//		DoubleList color2Values = y2ArrayTestData;
//		DoubleList results = new ArrayDoubleList();
//		double percent = 1.0;
//		
//		System.out.print("T_GPR_Javainput.csv 100% start\n");
//		results = NonLinearRegression.nlrNormalizeTwoColor(color1Values, color2Values, percent);
//		System.out.print("T_GPR_Javainput.csv 100% RESULTS percent=" + percent + "\n");			
//		writeResults("T_GPR_Javainput-RESULTS100", color1Values, color2Values, results);
//	}

//	public void testnlrNormalizeTwoColor4(){
//		logger.debug("\n----test2 nlrNormalizeTwoColor----");
//		setUp("T_GPR_Javainput2.csv");
//		//setUp("T_GPR_Javainput2sort.csv");
//		
//		DoubleList color1Values = y1ArrayTestData;		
//		DoubleList color2Values = y2ArrayTestData;
//		DoubleList results = new ArrayDoubleList();
//		double percent = .20;
//		
//		System.out.print("T_GPR_Javainput.csv 20% start\n");
//		results = NonLinearRegression.nlrNormalizeTwoColor(color1Values, color2Values, percent);
//		System.out.print("T_GPR_Javainput.csv 20% RESULTS percent=" + percent + "\n");			
//		writeResults("T_GPR_Javainput-RESULTS20", color1Values, color2Values, results);
//	}

	/**
	 */		
	public void testnlrNormalizeTwoColor4(){
		logger.debug("\n----test2 nlrNormalizeTwoColor----");
		setUp("MA_intensities1sort.csv");

		for (int i = 0; i < y1ArrayTestData.size(); i++){
			System.out.println("A[" + i + "]=" + y1ArrayTestData.get(i) + "\t\tM[" + i + "]=" + y2ArrayTestData.get(i));
		}

		DoubleList color1Values = y1ArrayTestData;		
		DoubleList color2Values = y2ArrayTestData;
		DoubleList results = new ArrayDoubleList();
		double percent = 1.0;
		
		System.out.print("MA_intensities1.csv 100% start\n");
		results = NonLinearRegression.nlrNormalizeTwoColor(color1Values, color2Values, percent);

		System.out.println();
		for (int i = 0; i < y1ArrayTestData.size(); i++){
			System.out.println("Result[" + i + "]=" + results.get(i));
		}
		System.out.println();
		
		System.out.print("MA_intensities1.csv 100% RESULTS percent=" + percent + "\n");			
		writeResults("MA_intensities1-RESULTS100", color1Values, color2Values, results);
	}
	
	/**
	 */	
	public void testnlrNormalizeTwoColor5(){
		logger.debug("\n----test2 nlrNormalizeTwoColor----");
	    setUp("MA_intensities1sort.csv");

		for (int i = 0; i < y1ArrayTestData.size(); i++){
			System.out.println("A[" + i + "]=" + y1ArrayTestData.get(i) + "\t\tM[" + i + "]=" + y2ArrayTestData.get(i));
		}

		DoubleList color1Values = y1ArrayTestData;		
		DoubleList color2Values = y2ArrayTestData;
		DoubleList results = new ArrayDoubleList();
		double percent = .20;
		
		System.out.print("MA_intensities1.csv 20% start\n");
		results = NonLinearRegression.nlrNormalizeTwoColor(color1Values, color2Values, percent);

		System.out.println();
		for (int i = 0; i < y1ArrayTestData.size(); i++){
			System.out.println("Result[" + i + "]=" + results.get(i));
		}
		System.out.println();
		
		System.out.print("MA_intensities1.csv 20% RESULTS percent=" + percent + "\n");			
		writeResults("MA_intensities1-RESULTS20", color1Values, color2Values, results);
	}

//
//	
////	public void testnlrNormalizeTwoColor6(){
////		logger.debug("\n----test2 nlrNormalizeTwoColor----");
////	    setUp("T_GPR_Javainput2sort_2k.csv");
////
////		DoubleList color2Values = y1ArrayTestData;	 // A	
////		DoubleList color1Values = y2ArrayTestData;   // M
////		DoubleList results = new ArrayDoubleList();
////		double percent = .10;
////		
////		System.out.print("T_GPR_Javainput2sort.csv 10% start\n");
////		results = NonLinearRegression.nlrNormalizeTwoColor(color1Values, color2Values, percent);
////		
////		writeResults("T_GPR_Javainput2sort2k-RESULTS10", color1Values, color2Values, results);
////	}
//
	/**
	 */	
	public void testnlrNormalizeTwoColor6(){
		logger.debug("\n----test2 nlrNormalizeTwoColor----");
	    setUp("T_GPR_Javainput2sort_2k.csv");

		DoubleList color2Values = y1ArrayTestData;	 // A	
		DoubleList color1Values = y2ArrayTestData;   // M
		DoubleList results = new ArrayDoubleList();
    	double percent = .20;

		System.out.print("T_GPR_Javainput2sort.csv 20% start\n");
		results = NonLinearRegression.nlrNormalizeTwoColor(color1Values, color2Values, percent);
		
		writeResults("T_GPR_Javainput2sort2k-RESULTS20", color1Values, color2Values, results);
	}
	
	
//	public void testgetWeightsTwoColor(){
//   	logger.debug("\n----test getWeightsTwoColor----");
//	    setUp("test1-localRegression.csv");
//		int pointOfInterest = 0;
//		DoubleList x = y1ArrayTestData;
//		BioinfoMatrix results;
//		
//		int arraySize = y1ArrayTestData.size();
//		int windowSize = 5;
//		int startPoint = 0;
//		int endPoint = 4;
//		double xForValueToPred = 0.0;		
//		
////		results = NonLinearRegression.getWeightsTwoColor(pointOfInterest, x, arraySize, windowSize, startPoint, endPoint);		
////		NonLinearRegression.printBioinfoMatrix(results);
//		
//		for (int pointOfInterest_i = 0; pointOfInterest_i < 35; pointOfInterest_i++){
//			System.out.print("Weights for pointOfInterest["+ pointOfInterest_i +"]\n");
//			weights = getWeightsTwoColor(poi);
//			results = NonLinearRegression.getWeightsTwoColor(pointOfInterest_i, x, arraySize, windowSize, xForValueToPred);		
//			NonLinearRegression.printBioinfoMatrix(results);
//		}			
//	 }	
	
}

