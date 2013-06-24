/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/LinearRegression.java,v $
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

import org.rti.regression.matrix.BioinfoMatrixImpl;
import org.rti.regression.matrix.BioinfoMatrix;

/**
 * Peform linear regression
 */
public class LinearRegression {
	
	
	
	/**
	 * Perform linear regression-based normalization
	 * @param regVars Regression variables
	 * @return Matrix containing single column of adjusted results
	 */
	public static BioinfoMatrix normalize
	(
		RegressionVariables regVars
	) {
		//the adjusted values = y - (y_arrayLevel - y_groupLevel)
	    BioinfoMatrix values = regVars.getValues();
	    BioinfoMatrix arrayVariables = regVars.getArrayVariables();
	    BioinfoMatrix groupVariables = regVars.getGroupVariables();
		BioinfoMatrix adjusted_values = (BioinfoMatrix)(values.subtract(regression(
				values,arrayVariables).subtract(regression(values,groupVariables))));
		return adjusted_values;
	}

	/**
	 * Calculate Yhat based on the usual regression formula
	 * beta=inv(xTx)*(xTy)
	 * @param y: Dependent variable vector
	 * @param preX: Independent variable vector without the intercept col
	 * @return Matrix containing single column of results
	 */
	
	protected static BioinfoMatrix regression(BioinfoMatrix y, BioinfoMatrix preX){
		//generate x matrix with adding the intercept column
		BioinfoMatrix x = prepareX(preX);
		
		//calculate x transpose matrix
		BioinfoMatrix xTranspose = (BioinfoMatrix)x.transpose();
		
		//calculate the inverse of xTx. It has to be full rank to be inversed.
		BioinfoMatrix xtx = (BioinfoMatrix)xTranspose.multiply(x);
		BioinfoMatrix invXtx = null;
		if (!xtx.isSingular()){
			invXtx = (BioinfoMatrix)xtx.inverse();
		}
		else {
			throw new SingularityException("The matrix is sigular");	
		}
		
		//generate beta and yhat
		BioinfoMatrix beta = (BioinfoMatrix)invXtx.multiply(xTranspose.multiply(y));
		BioinfoMatrix yHat = (BioinfoMatrix)x.multiply(beta); 

		return yHat;
	}

	/**
	 * Generate reparameterized X matrix with an intercept column
	 * @param pre_x: Initial X vector
	 * @return Reparameterized matrix containing the intercept column too
	 */
	
	protected static BioinfoMatrix prepareX (BioinfoMatrix pre_x){
		//generate dummy matrix
		BioinfoMatrix dummy_x = pre_x.createDummyX();
		
		//get dimensions.
		int row = dummy_x.getRowDimension();
		int col = dummy_x.getColumnDimension();
		
		//Create a new matrix with duplicate last columns 
		double [][] singular = new double[row][col-1] ;
		double [] singular_col = dummy_x.getColumn(col-1);
		
		for (int i=0; i<row; i++){
			for (int j=0; j<col-1; j++){
				singular[i][j] = singular_col[i];
			}
		}
		
		//Remove the last column and make every other column 
		//subtract it to remove singularity
		//Also add an intercept column		
		BioinfoMatrix p_x = new BioinfoMatrixImpl(singular);
		BioinfoMatrix pr_x = (BioinfoMatrix)(dummy_x.extractColumns(0,col-2)).subtract(p_x);
		BioinfoMatrix prepared_x = pr_x.createInterceptColumn();
			
		return prepared_x;
	}

}
