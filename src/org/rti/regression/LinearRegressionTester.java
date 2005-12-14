/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/LinearRegressionTester.java,v $
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

import org.apache.log4j.Logger;
import org.rti.regression.matrix.BioinfoMatrix;
import org.rti.regression.matrix.BioinfoMatrixImpl;

import junit.framework.TestCase;

/**
 * @author bliu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LinearRegressionTester extends TestCase {
	
	
	private static final Logger logger = 
		Logger.getLogger(LinearRegressionTester.class.getName());
	
	
	BioinfoMatrix Y = null;
	BioinfoMatrix X_group = null;
	BioinfoMatrix X_array = null;
	RegressionVariables regVars = null;
	
	
	/**
	 * 
	 */
	public void setUp(){
		double[][] temp1 = {
			{4.0}, 
			{8.0}, 
			{10.0},
			{14.0}};
		double[][] temp2 = {
			{2.0}, 
			{2.0},
			{5.0}, 
			{7.0}};
		double[][] temp3 = {
			{1.0}, 
			{1.0},
			{1.0}, 
			{9.0}};
				
		Y = new BioinfoMatrixImpl(temp1);
		X_group = new BioinfoMatrixImpl(temp2);		
		X_array = new BioinfoMatrixImpl(temp3);
		regVars = new RegressionVariables(Y, X_group, X_array);
	}
	
	
	/**
	 * 
	 */	
	public void testPrepareX(){
		BioinfoMatrix x = LinearRegression.prepareX(X_group);
		
		assertTrue(x.getEntry(0,0) == 1);
		logger.debug(x.toPrintableString());
	}

	/**
	 * 
	 */
	public void testRegression(){
		BioinfoMatrix yhat = LinearRegression.regression(Y,X_array);
		
		assertTrue(yhat.getEntry(3,0) == 14.0);
		logger.debug(yhat.toPrintableString());
	}

	/**
	 * 
	 */
	public void testNormalize(){
		BioinfoMatrix x = LinearRegression.normalize(regVars);
		
		assertTrue(x.getEntry(3,0) == 14);
		logger.debug(x.toPrintableString());
	}
	
	
}
