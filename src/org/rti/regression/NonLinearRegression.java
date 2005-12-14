/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/NonLinearRegression.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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

import org.apache.commons.collections.primitives.ArrayDoubleList;
import org.apache.commons.collections.primitives.DoubleList;
import org.rti.regression.matrix.BioinfoMatrix;
import org.rti.regression.matrix.BioinfoMatrixImpl;


/**
 * Peform Non linear regression, aka LOESS, aka Local Regression
 */
public class NonLinearRegression {
	
	/**
	 * Perform linear regression-based normalization
	 * @param values Array values
	 * @param averages is the average array values
	 * @param percent is the percent of data in the local neighborhoods
	 * @return DoubleList containing single column of adjusted results
	 */
	public static DoubleList nlrSingleColorAverageReplicates (DoubleList values, DoubleList averages, double percent) {
	
			return null;
	}


	/**
	 * Perform linear regression-based normalization
	 * @param color1Values Array values of first color
	 * @param color2Values Array values of second color
	 * @param averages Average values of arrays
	 * @param percent is the percent of data in the local neighborhoods
	 * @return DoubleList containing single column of adjusted results
	 */
	public static DoubleList nlrTwoColorAverageReplicates (DoubleList color1Values, DoubleList color2Values, DoubleList averages, double percent) {
	
			return null;
	}


	/**
	 * Perform linear regression-based normalization
	 * @param values Array values
	 * @param percent is the percent of data in the local neighborhoods
	 * @return DoubleList containing single column of adjusted results
	 */
	public static DoubleList nlrNormalizeSingleColor (DoubleList values, double percent) {
		
		if (percent <= 0.0 || percent > 1.0)
			throw new IllegalArgumentException("Invalid Percentage");

		if (values == null)
			return values;
		else {					
			int arraySize = values.size();
			
			int windowSize = 0;
			int value = (int)(Math.round(arraySize*percent));		
		 	if (value%2==0)
				windowSize = value - 1;
			else 
				windowSize=value;				
		
			return nlRegressionSingleColor(values, arraySize, windowSize);
		}
	}


	/**
	 * Perform linear regression-based normalization
	 * @param mValues Array values - expects log2 (intensity1 / intensity2) / 2 
	 * @param aValues Array values - expects log2 (intensity1 * intensity2)  
	 * @param percent is the percent of data in the local neighborhoods, aka degrees of freedom
	 * @return DoubleList containing single column of adjusted results
	 */
	public static DoubleList nlrNormalizeTwoColor (DoubleList mValues, DoubleList aValues, double percent) {

		System.out.print("nlrNormalizeTwoColor 1\n");		
		if (percent <= 0.0 || percent > 1.0)
			throw new IllegalArgumentException("Invalid Percentage");
						
		else if (mValues.size() != aValues.size() )
			throw new IllegalArgumentException("Color1Values not the same size as Color2Values");
						
		else if (mValues == null || aValues == null)
			return null;

			
		else {
			// Note that both arrays should be the same size, have same probes, have no missing or zero values,
			// and will be ordered the same, sorted by aValues.
			int arraySize = mValues.size();
			
			int windowSize = 0;
			int value = (int)(Math.round(arraySize*percent));		
			if (value%2==0)
				windowSize = value - 1;
			else 
				windowSize=value;			
			
			System.out.println("Start: arraySize=" + arraySize + "  windowSize=" + windowSize );
			
			// Create M A vectors for Local regression of 2 Color Intensity data
			DoubleList M = new ArrayDoubleList();
			DoubleList A = new ArrayDoubleList();
			
			for (int i = 0; i < arraySize; i++) {
				M.add( mValues.get(i));
				A.add( aValues.get(i));
			}
			 
			// Perform the Local regression and return the residuals 			
			return nlRegressionTwoColor(A, M, arraySize, windowSize);
		}
	}


	/**
	 * Calculate Yhat based on the usual regression formula
	 * beta=inv(xTx)*(xTy)
	 * @param values: Dependent variable vector in the form of a DoubleList
	 * @param arraySize: Size of array
	 * @param windowSize: Size of window for analysis 
	 * @return Matrix containing single column of results
	 */	
	public static DoubleList nlRegressionSingleColor(DoubleList values, int arraySize, int windowSize){
		BioinfoMatrix xData = null;
		BioinfoMatrix weights = null;
		DoubleList yData = null;
		DoubleList NLR_result = new ArrayDoubleList();
		
		int startPoint = 0;
		int endPoint = 0;
		int prevStart = 0;
		int prevEnd = 0;
		double xForValueToPred = 0.0;
		
		// Create a generic windowSize length X matrix to reweight for each point of interest 
		xData = getXdataSingleColor(windowSize);
		
		for (int pointOfInterest = 0; pointOfInterest < arraySize; pointOfInterest++){

			// Identify Start and End points for Window based on position of Point of Interest 

			// Save previous start and end point for comparison 
			prevStart = startPoint;
			prevEnd = endPoint;

			// For points at the start of the data range that cannot support a symmetric interval
			// define an interval of start to window size 
		
			// Point in Low end tail
			if (pointOfInterest < (windowSize / 2) ) {
				startPoint = 0;
				endPoint = windowSize - 1;		
			}
			// Point in High end tail 
			else if (pointOfInterest >= (arraySize - (windowSize / 2) ) ) {
				startPoint = (arraySize - 1) - windowSize + 1;
				endPoint = arraySize - 1;
			
				// Account for Last point when it isn't picked up - increment one more unit to include last point
				if ( pointOfInterest > endPoint ) {
					startPoint = (startPoint + 1);
					endPoint = endPoint + 1;		
				}		
			}
			// Point in middle where symmetric window possible
			else {
				startPoint = pointOfInterest - windowSize/2;
				endPoint = pointOfInterest + windowSize/2;
			}
//			System.out.print("  startPoint: " + startPoint + "  endPoint: " + endPoint + ":\n");
	
			yData = getWindowData(pointOfInterest, values, yData, arraySize, windowSize, startPoint, endPoint, prevStart, prevEnd);
			weights = getWeightsSingleColor(pointOfInterest, weights, arraySize, windowSize, startPoint, endPoint);		
			
			xForValueToPred = xData.getEntry( pointOfInterest - startPoint, 0);
			
			//NLR_result.add(weightedRegression( new BioinfoMatrixImpl(yData.toArray()), xData, weights, pointOfInterest, startPoint));
			NLR_result.add(weightedRegression( new BioinfoMatrixImpl(yData.toArray()), xData, weights, xForValueToPred));
		}

		return NLR_result;
	}



	/**
	 * 
	 * @param xValues
	 * @param yValues
	 * @param arraySize
	 * @param windowSize
	 * @return DoubleList result of regression
	 */
	public static DoubleList nlRegressionTwoColor(DoubleList xValues, DoubleList yValues, int arraySize, int windowSize){
		BioinfoMatrix weights = null;
		BioinfoMatrix xData = null;
		BioinfoMatrix yData = null;
		DoubleList result = new ArrayDoubleList();
		
		double xForValueToPred = 0.0;
		
		for (int pointOfInterest = 0; pointOfInterest < arraySize; pointOfInterest++){

			// Temporary code to check on status of run
			if (pointOfInterest % 100 == 0) {
				System.out.println("\tLast Point of Interest Processed = " + pointOfInterest);
			}
			//System.out.println( "Y(poi)=" + pointOfInterest + " valueToPred = " + yValues.get(pointOfInterest));
			
			Neighborhood poi = getLocalNeighborhoodTwoColor(pointOfInterest, xValues, arraySize, windowSize);			
			weights = getWeightsTwoColor(poi);
			xData = getLocalNeighborhoodDataTwoColor(poi, xValues);
			yData = getLocalNeighborhoodDataTwoColor(poi, yValues); 
						
			xForValueToPred = xValues.get(pointOfInterest);
			
			result.add(weightedRegression( yData, xData, weights, xForValueToPred));
		}

		return result;
	}

	
	/**
	 * Creates a BioinfoMatrix object containing "value" consecutive integers 
	 * @param value Number of array data points in the array data object
	 * @return a BioinfoMatrix object to use as an X matrix in the regression
	 */
	public static BioinfoMatrix getXdataSingleColor (int value){
		double[] X = new double[value];
		
		// Create an Array of X values with values from 1 to size of window
		for (int i = 0; i < value; i++){
			X[i] = (new Integer(i)).doubleValue()+1;
		}
		
		//	return the X matrix
		return new BioinfoMatrixImpl(X); 
	}


	/**
	 * Defines appropriate weights for the local neighborhood window and the point of interest
	 * @param pointOfInterest 
	 * @param preweights Weights used for the previous local neighborhood window - may or may not be updated
 	 * @param arraySize: Size of array
	 * @param windowSize: Size of window for analysis
	 * @param startPoint: absolute start position in the array
	 * @param endPoint: absolute end position in the array
	 * @return a BioinfoMatrix object of weights
	 */
	public static BioinfoMatrix getWeightsSingleColor (int pointOfInterest, BioinfoMatrix preweights, 
		int arraySize, int windowSize, int startPoint, int endPoint){

		double[] weightArray = new double[ windowSize ];
		double maxDelta = Math.max( (pointOfInterest - startPoint), (endPoint - startPoint - pointOfInterest));
//		System.out.print("\tgetWeights: pointOfInterest = " + pointOfInterest + " maxDelta = " + maxDelta);
		
		//If the point of interest is in tails, then we will update weights. Otherwise, it will not be changed from the last iteration
		//of the front tail, which provides the weights used in the middle (the middle means the point of interest is centered in the window)
		if (pointOfInterest <= (windowSize/2) || pointOfInterest >= (arraySize - (windowSize/2)) ) {
//		System.out.print("\t\tUpdating Weights...\n");
			// Create weight matrix in column form - need to use special math function to simplify and speed up matrix multiplication calc of X`W and WY 		
			for (int i = 0; i < windowSize; i++){
					weightArray[i] = Math.pow(1 - Math.pow(( Math.abs(pointOfInterest - startPoint - i) / maxDelta),3),3);
//					System.out.print(weightArray[i] + "\n");				    
			}
	
			return new BioinfoMatrixImpl(weightArray);
		}
//		System.out.print("\t\tLeaving Weights Alone...\n\n");
		return preweights;
	}
	

	/**
	 * Defines appropriate weights for the local neighborhood window and the point of interest
	 * @param pointOfInterest 
	 * @param arraySize: Size of array
	 * @param windowSize: Size of window for analysis
	 * @param x: data holder 
	 * @return a BioinfoMatrix object of weights
	 */
	public static Neighborhood getLocalNeighborhoodTwoColor (int pointOfInterest, DoubleList x, 
		int arraySize, int windowSize){
	
		Neighbor neighborI = new Neighbor(0,0);
		Neighborhood pointOfInterestNeighborhood = new Neighborhood(windowSize);		
		
		// Define distance from pointOfInterest to each point  
		//for (int i = 0; i < arraySize; i++){
 	 	for (int i = Math.max(0,pointOfInterest-windowSize); i < Math.min(arraySize,pointOfInterest+windowSize); i++){			
		 	
			// Define neighbor to POI based on it's index in the x list and 
			// it's Distance between point i and the Point of Interest
			neighborI.setNeighborIndex(i); 
			neighborI.setDistance(Math.abs(x.get(i) - x.get(pointOfInterest)) );			
//			System.out.println(" x=" + x.get(i) + "\t getNeighborIndex=" + neighborI.getNeighborIndex() + " \tgetDistance=" + neighborI.getDistance() );
			
			// Add to list of points in the local neighborhood if they qualify
			if ( pointOfInterestNeighborhood.withinNeighborhood(neighborI) ) {
				pointOfInterestNeighborhood.insert(new Neighbor( neighborI.getNeighborIndex(), neighborI.getDistance() ) );
			}
		}
		
		// Once the neighborhood is finalized, assign a weight to each point in the neighborhood
		pointOfInterestNeighborhood.assignWeight();

		return pointOfInterestNeighborhood;
	}

		
	/**
	 * Returns the weights for a given local neighborhood as a BioinfoMatrix object
	 * @param p
	 * @return a BioinfoMatrix object
	 */
	public static BioinfoMatrix getWeightsTwoColor (Neighborhood p){
		return new BioinfoMatrixImpl( p.returnWeightsAsArray() );
	}
	
	/**
	 * Returns the indexes of the points in the local neighborhood from the original data vector
	 * @param p local neighborhood for the point of interest
	 * @param d: data holder
	 * @return a BioinfoMatrix object
	 */
	public static BioinfoMatrix getLocalNeighborhoodDataTwoColor (Neighborhood p, DoubleList d){
		DoubleList originalData = new ArrayDoubleList();
		
		int[] temp = p.returnIndicesAsArray();
		
		for (int i = 0; i < p.getCapacity(); i++){
			originalData.add(d.get(temp[i]));
		}
		
		return new BioinfoMatrixImpl(originalData.toArray());
	}
	
	
	
	/**
	 * Calculate Yhat based on the weighted regression formula 
	 * beta=inv(xTwx)*(xTwy)
	 * @param y: Dependent variable vector
	 * @param pre_x: Independent variable vector without the intercept col
	 * @param w: vector of weights
	 * @param xForValueToPred: the predicted vector
	 * @return Matrix containing single column of results
	 */		
	public static double weightedRegression(BioinfoMatrix y, BioinfoMatrix pre_x, BioinfoMatrix w, double xForValueToPred){
		
		//generate x matrix with adding the intercept column		
		BioinfoMatrix x = pre_x.createInterceptColumn();
		
		//calculate x transpose matrix
		BioinfoMatrix x_transpose = (BioinfoMatrix)x.transpose();
		
		//calculate the inverse of xTx. It has to be full rank to be inversed.
		BioinfoMatrix xtw = x.fastXTW(w);
		
		BioinfoMatrix xtwx = (BioinfoMatrix) xtw.multiply(x);
		BioinfoMatrix inv_xtwx = null;
		
		if (!xtwx.isSingular()){
			inv_xtwx = (BioinfoMatrix)xtwx.inverse();
		}
		else {
			throw new SingularityException("The matrix is singular");	
		}
		
		//generate beta and yhat of point of interest
		BioinfoMatrix beta = (BioinfoMatrix)inv_xtwx.multiply(x_transpose.multiply(y.fastWY(w)));
        double yHat_POI = beta.getEntry(0,0) + beta.getEntry(1,0)*(xForValueToPred); 

		return yHat_POI;
	}

	
	/**
	 * Single Color Local regression method to identify which data from the array data should be
	 * included in the local neighborhood window
	 * @param pointOfInterest Point for which we are constructing the local neighborhood window
	 * @param arrayData DoubleList containing all array data
	 * @param arraySize: the size of this analyzed array
	 * @param windowSize: the size of window to be analyzed
	 * @param startPoint: the absolute starting postion
	 * @param endPoint: the absolute ending position
	 * @param prevStart: the previous absolute starting postion
	 * @param prevEnd: the previous absolute ending position
	 * @param windowData Current array of data selected to be in the local neighborhood window
	 * @return DoubleList window data
	 */
	public static DoubleList getWindowData (int pointOfInterest, DoubleList arrayData, DoubleList windowData,
		 int arraySize, int windowSize, int startPoint, int endPoint, int prevStart, int prevEnd){

		// Initial Y matrix of intensities at the beginning
		if (pointOfInterest == 0) {					
			DoubleList firstWindow = new ArrayDoubleList();
			
			// Get data from input BioinfoMatrix object within the correct range for this point of interest
			for (int i = 0; i < windowSize; i++){
				firstWindow.add(i, arrayData.get(i));
			}
			return firstWindow;
		}	

		// When not in the tails update by removing first item and adding a new one onto the end
		else if ( prevStart != startPoint || prevEnd != endPoint ) {
			windowData.add(arrayData.get(endPoint));
			windowData.removeElementAt(0);
			return windowData;
		}
				
		// Leave alone otherwise - will use the same data for each point of interest in the tails
		else {
			return windowData;
		}
				
	}

	
	/** Convenience method to prints a BioinfoMatrix object to System.out 
	 * @param x
	 */
	public static void printBioinfoMatrix(BioinfoMatrix x){		
		for (int i=0;i<x.getRowDimension();i++){
			for (int j=0;j<x.getColumnDimension();j++){
				System.out.print(x.getEntry(i, j) + "  ");
			}
			System.out.println();
		}
	}


}

