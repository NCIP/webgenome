/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/matrix/BioinfoMatrixImpl.java,v $
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

package org.rti.regression.matrix;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;

/**
 * @author bliu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BioinfoMatrixImpl
	extends RealMatrixImpl
	implements BioinfoMatrix {

	/**
	 * 
	 */
	public BioinfoMatrixImpl() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BioinfoMatrixImpl(int arg0, int arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public BioinfoMatrixImpl(double[][] arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public BioinfoMatrixImpl(double[] arg0) {
		super(arg0);
	}
	
	
	/**
	 * Constructor
	 * @param m A matrix
	 */
	public BioinfoMatrixImpl(RealMatrix m) {
		this(m.getData());
	}


	/** Insert Columns/Matrix B into Matrix A at column m for two same row size Matrices, A and B
	 @param B    another matrix and the point to insert
	 @param insertAtCol    The place of Col where to insert	 
	 @return     a new Matrix, containing A with B inserted into it 
	 @exception  IllegalArgumentException Matrix row dimensions must agree and insert point must be valid.
	 */	

	public BioinfoMatrix insertColumns
	(
		BioinfoMatrix B, int insertAtCol
	){
		int n = this.getColumnDimension();
		int m = this.getRowDimension();
   	
		  // Verify A and B have the same number of rows
		  if (B.getRowDimension() != m) {
			  throw new IllegalArgumentException("Matrix row dimensions must agree.");
		  }
		  if (insertAtCol > m || insertAtCol < 0) {
			 throw new IllegalArgumentException("Insert point out of range.");
		  }
		
		  // Create a two-D array large enough to hold both 
		  double[][] C = new double[m][n + B.getColumnDimension()];

		  // For each row
		  for (int i = 0; i < m; i++) {
			// For each column
			for (int j = 0; j < n + B.getColumnDimension(); j++) {
			  // Copy over A columns up to B insert point
			  if (j < insertAtCol){
				  C[i][j] = this.getEntry(i,j);
			  }
			  // Copy over B columns 
			  else if (j >= insertAtCol && j < insertAtCol + B.getColumnDimension()) {
				  C[i][j] = B.getEntry(i,j-insertAtCol);
			  } 
			  // Copy over remaining A columns
			  else {
				 C[i][j] = this.getEntry(i,j - B.getColumnDimension());
			  }
			}
		  }
		  return new BioinfoMatrixImpl(C);

	}
	
	/** Concatenate two same row sized Matrices, A and B
	@param B    another matrix
	@return     a new Matrix, containing A on left side, B on right side. 
	@exception  IllegalArgumentException Matrix row dimensions must agree.
	*/	
	public BioinfoMatrix concatenateColumns (BioinfoMatrix B) {
		int n = this.getColumnDimension();
		int m = this.getRowDimension();
   	
		 // Verify A and B have the same number of rows
		 if (B.getRowDimension() != m) {
			 throw new IllegalArgumentException("Matrix row dimensions must agree.");
		 }
		
		 // Create a new matrix large enough to hold both 
		 double[][] C = new double[m][n + B.getColumnDimension()];
	    
		 // For each row
		 for (int i = 0; i < m; i++) {
		   // For each column
		   for (int j = 0; j < n + B.getColumnDimension(); j++) {
			 // Copy over A columns
			 if (j < n){
				 C[i][j] = this.getEntry(i,j);
			 }
			 // Copy over B columns
			 else {
				 C[i][j] = B.getEntry(i,j-n);
			 } 
		   }
		 }
		 return new BioinfoMatrixImpl(C);
	}

	/** Extract Columns from this Matrix to form another matrix
	 @param colGetStart     start point
	 @param colGetEnd       end point
	 @return     a new Matrix, which is a submatrix with extracted cols 
	 @exception  IllegalArgumentException Matrix row dimensions must agree and insert point must be valid.
	 */	
	 public BioinfoMatrix extractColumns (int colGetStart, int colGetEnd) {
	   int n = this.getColumnDimension();
	   int m = this.getRowDimension();
   	
		  // Verify A and B have the same number of rows and end points ok
		  if (m <= 0) {
			  throw new IllegalArgumentException("Matrix must have at least 1 column.");
		  }
		  if (colGetStart > m || colGetStart < 0) {
			 throw new IllegalArgumentException("Index of column to get out of range.");
		  }
		  if (colGetEnd > m || colGetEnd < 0) {
			 throw new IllegalArgumentException("Index of column to get out of range.");
		  }		
		  if (colGetEnd < colGetStart) {
			 throw new IllegalArgumentException("Index of column to get out of range.");
		  }		

		  // Create a new matrix large enough to hold both
		  int width =  colGetEnd - colGetStart + 1;
		  double[][] C = new double[m][width];

		  // For each row
		  for (int i = 0; i < m; i++) {
			// For each selected column
			for (int j = colGetStart; j <= colGetEnd; j++) {
				C[i][j - colGetStart] = this.getEntry(i,j);
			}
		  }
		  return new BioinfoMatrixImpl(C);
	 }


	/** Use the single column argument to create a list of unique values in that column. Used in creating Dummy matrices 	 	 
	 @return     A list of unique values in the argument matrix column 
	 @exception  IllegalArgumentException Matrix argument must be one column wide
	 */	
	public List createList () {
	   int n = this.getColumnDimension();
	   int m = this.getRowDimension();
	 	
		 if (n != 1) {
			 throw new IllegalArgumentException("Can only create a Collection from a one column matrix.");
		 }
		 List al = new ArrayList();  

		 // Add each unique value to the collection 
		 for (int i = 0; i < m; i++) {			 
			 Double D = new Double(this.getEntry(i, 0));
			 if (al.contains( D ) == false ) { 
				 al.add(D);			
			 }
		}
		return al;		
	}


	/** Use the single column of values to create a dummy/design matrix, one column per value 	 	 
	 @return     Matrix X, containing a dummy/design matrix based on the supplied column  
	 @exception  IllegalArgumentException Matrix column must be one column wide (createList exception criteria)
	 */	
	public BioinfoMatrix createDummyX () {
		int n = this.getColumnDimension();
	   	int m = this.getRowDimension();
		List al = this.createList();		
		
		// Create a Matrix of proper size for Dummy X matrix
		double[][] C = new double[m][al.size()];

		// For each column
		for (int j = 0; j < al.size(); j++) {
			// For each row
			for (int i = 0; i < m; i++) {
			
				Double D = new Double(this.getEntry(i,0));
				double DD = D.doubleValue();	
							
				String SS = al.get(j).toString();
				double A = Double.parseDouble(SS);
				if (A == DD) {
					C[i][j] = 1;
				}
				else {
					C[i][j] = 0;
				}
			}
		}	

		return new BioinfoMatrixImpl(C);
	}



	/** Append a column of ones to the front of the provided matrix argument 	 	 
	 @return     Matrix X = supplied matrix with an additional column at column 0, containing all ones   
	 @exception  IllegalArgumentException - none
	 */	
	public BioinfoMatrix createInterceptColumn () {
		int m = this.getRowDimension();
		
		//create a matrix with one column of all 1s
		double[][] intercept = new double[m][1];
		for (int i=0; i<m; i++){
			intercept[i][0] = 1;
		}

		// Append a column of ones to the beginning of the X matrix for use as the intercept 
		BioinfoMatrix X = (new BioinfoMatrixImpl(intercept)).concatenateColumns(this);
		return X;
	}


	/** Fast computation of X` * W that leaves out the 0 multiplications
	 @param w
	 @return  Matrix C = supplied X` matrix multiplied by W 
	 @exception  IllegalArgumentException - none
	 */	
	public BioinfoMatrix fastXTW(BioinfoMatrix w) {
		int n = this.getColumnDimension();
		int m = this.getRowDimension();
				
		// Create a Matrix of proper size for Dummy X matrix (Transposed)		
		double[][] C = new double[n][m];
//		
//		// For each column
//		for (int j = 0; j < n; j++) {
//			// For each row
//			for (int i = 0; i < m; i++) {
//				C[i][j] = this.getEntry(i,j) * w.getEntry(j,j);
//			}
//		}	

		// For each column
		for (int j = 0; j < n; j++) {
			// For each row
			for (int i = 0; i < m; i++) {
				C[j][i] = this.getEntry(i,j) * w.getEntry(i,0);
			}
		}	
		
		return new BioinfoMatrixImpl(C);
	}
	
	
	
	/** Fast computation of W * Y that leaves out the 0 multiplications
	 @param w
	 @return  Matrix C = supplied Y matrix multiplied by W 
	 @exception  IllegalArgumentException - none
	 */	
	public BioinfoMatrix fastWY(BioinfoMatrix w) {
		int n = this.getColumnDimension();
		int m = this.getRowDimension();
				
		// Create a Matrix of proper size for Resulting weighted Y matrix		
		double[][] C = new double[m][n];

		// For each column (only 1 column)
		// For each row
		for (int i = 0; i < m; i++) {
			C[i][0] = this.getEntry(i,0) * w.getEntry(i,0);
		}
		
		return new BioinfoMatrixImpl(C);
	}
	
	/** print out the test result
	 @return printed string 
	 */		
	public String toPrintableString() {
		StringBuffer buff = new StringBuffer("\n");
		for (int i = 0; i < this.getRowDimension();i++){
			for (int j = 0; j < this.getColumnDimension(); j++){
				buff.append(this.getEntry(i, j) + "  ");
			}
			buff.append("\n");
		}
		return buff.toString();
	}
	
	/** matrix transpose
	 @return transposed matrix 
	 */			
	public RealMatrix transpose() {
		return new BioinfoMatrixImpl(super.transpose());
	}
	
	/** matrix substraction, subclass
	 * @param m: the matrix to substract
	 @return (this-m) matrix 
	 */				
	public RealMatrix subtract(RealMatrix m) {
		return new BioinfoMatrixImpl(super.subtract(m));
	}

	/** matrix multiply, subclass
	 * @param m: the matrix to multiply with
	 @return (this*m) matrix 
	 */				
	public RealMatrix multiply(RealMatrix m) {
		return new BioinfoMatrixImpl(super.multiply(m));
	}

	/** matrix inverse, subclass
	 @return inversed matrix 
	 */				
	public RealMatrix inverse() {
		return new BioinfoMatrixImpl(super.inverse());
	}



}
