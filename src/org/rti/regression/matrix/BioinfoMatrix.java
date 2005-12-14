/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/matrix/BioinfoMatrix.java,v $
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

import java.util.List;

import org.apache.commons.math.linear.RealMatrix;

/**
 * @author bliu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface BioinfoMatrix extends RealMatrix {

	/** Insert Columns/Matrix B into Matrix A at column m for two same row size Matrices, A and B
	 @param m    another matrix and the point to insert
	 @param col    The place of Col where to insert	 
	 @return     a new Matrix, containing A with B inserted into it 
	 @exception  IllegalArgumentException Matrix row dimensions must agree and insert point must be valid.
	 */		
	public abstract BioinfoMatrix insertColumns
	(
		BioinfoMatrix m, int col
	);

	/** Concatenate two same row sized Matrices, A and B
	@param m    another matrix
	@return     a new Matrix, containing A on left side, B on right side. 
	@exception  IllegalArgumentException Matrix row dimensions must agree.
	*/	
	public abstract BioinfoMatrix concatenateColumns 
	(
		BioinfoMatrix m
	);	

	/** Extract Columns from this Matrix to form another matrix
	 @param colGetStart     start point
	 @param colGetEnd       end point
	 @return     a new Matrix, which is a submatrix with extracted cols 
	 @exception  IllegalArgumentException Matrix row dimensions must agree and insert point must be valid.
	 */		
	public abstract BioinfoMatrix extractColumns 
	(
		int colGetStart, int colGetEnd
	);	

	/** Use the single column argument to create a list of unique values in that column. Used in creating Dummy matrices 	 	 
	 @return     A list of unique values in the argument matrix column 
	 @exception  IllegalArgumentException Matrix argument must be one column wide
	 */		
	public abstract List createList 
	(
	);	

	/** Use the single column of values to create a dummy/design matrix, one column per value 	 	 
	 @return     Matrix X, containing a dummy/design matrix based on the supplied column  
	 @exception  IllegalArgumentException Matrix column must be one column wide (createList exception criteria)
	 */		
	public abstract BioinfoMatrix createDummyX 
	(
	);	

	/** Append a column of ones to the front of the provided matrix argument 	 	 
	 @return     Matrix X = supplied matrix with an additional column at column 0, containing all ones   
	 @exception  IllegalArgumentException - none
	 */	
	public abstract BioinfoMatrix createInterceptColumn 
	(
	);

	
	/** Fast computation of X` * W that leaves out the 0 multiplications
	 @param w: the weight matrix 	 	 
	 @return     Matrix X = supplied matrix multiplied by argument W matrix   
	 @exception  IllegalArgumentException - none
	 */	
	public abstract BioinfoMatrix fastXTW (BioinfoMatrix w);

	/** Fast computation of W * Y that leaves out the 0 multiplications
	 * @param w: input BioinfoMatrix 	 	 
	 @return     Matrix Y = supplied matrix multiplied by argument W matrix   
	 @exception  IllegalArgumentException - none
	 */	
	public abstract BioinfoMatrix fastWY (BioinfoMatrix w);


	/** print the testing output
	 @return printed string
	 */		
	public abstract String toPrintableString();
	
}
