/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/BioinfoMatrixTransformer.java,v $
$Revision: 1.2 $
$Date: 2006-03-29 22:26:30 $

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

import org.apache.commons.collections.primitives.ArrayDoubleList;
import org.apache.commons.collections.primitives.DoubleList;
import org.rti.regression.RegressionVariables;
import org.rti.regression.matrix.BioinfoMatrix;
import org.rti.regression.matrix.BioinfoMatrixImpl;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.ArrayDatumIterator;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.Experiment;


/**
 * Transforms between native data model and data model requried for
 * regression.
 */
public class BioinfoMatrixTransformer {
	
	
	/**
	 * Transform from hierarchical to square representation of data
	 * @param data Data
	 * @return Square representation of data
	 */
	public static RegressionVariables transform(Experiment[] data) {
		if (data == null)
			throw new IllegalArgumentException("No data");
		DoubleList valuesList = new ArrayDoubleList();
		DoubleList groupList = new ArrayDoubleList();
		DoubleList arrayList = new ArrayDoubleList();
		double groupCount = 0.0;
		double arrayCount = 0.0;
		for (int i = 0; i < data.length; i++) {
			Experiment gads = data[i];
			groupCount += 1.0;
			for (BioAssayIterator gadIt = gads.bioAssayIterator(); gadIt.hasNext();) {
				arrayCount += 1.0;
				BioAssay gad = gadIt.next();
				for (ArrayDatumIterator adi = gad.arrayDatumIterator(); adi.hasNext();) {
					ArrayDatum datum = adi.next();
					valuesList.add(datum.magnitude());
					groupList.add(groupCount);
					arrayList.add(arrayCount);
				}
			}
		}
		
		// Instantiate member matrices
		double[][] valuesArr = new double[valuesList.size()][1];
		double[][] groupArr = new double[groupList.size()][1];
		double[][] arrayArr = new double[arrayList.size()][1];
		int size = valuesList.size();
		for (int i = 0; i < size; i++) {
			valuesArr[i][0] = valuesList.get(i);
			groupArr[i][0] = groupList.get(i);
			arrayArr[i][0] = arrayList.get(i);
		}
		BioinfoMatrix values = new BioinfoMatrixImpl(valuesArr);
		BioinfoMatrix groupVariables = new BioinfoMatrixImpl(groupArr);
		BioinfoMatrix arrayVariables = new BioinfoMatrixImpl(arrayArr);
		
		return new RegressionVariables(values, groupVariables, arrayVariables);
	}
	
	
	/**
	 * Generate output data set
	 * @param originalData Data to be transformed
	 * @param normalizedValues Normalized values
	 * @return Normalized data set
	 */
	public static Experiment[] transform
	(
		Experiment[] originalData, BioinfoMatrix normalizedValues
	) {
		if (originalData == null || normalizedValues == null)
			throw new IllegalArgumentException("Arguments cannot be null");
		Experiment[] outData = new Experiment[originalData.length];
		if (normalizedValues.getColumnDimension() > 0) {
			double[] col = normalizedValues.getColumn(0);
			int p = 0;
			for (int i = 0; i < originalData.length; i++) {
				Experiment oldExp = originalData[i];
				Experiment newExp = new Experiment();
				newExp.bulkSetMetadata(oldExp);
				outData[i] = newExp;
				for (BioAssayIterator bai = oldExp.bioAssayIterator(); bai.hasNext();) {
				    BioAssay oldAssay = bai.next();
				    BioAssay newAssay = new BioAssay();
				    newAssay.bulkSetMetadata(oldAssay);
				    newExp.add(newAssay);
					for (ArrayDatumIterator adi = oldAssay.arrayDatumIterator(); adi.hasNext();) {
						ArrayDatum datum = adi.next();
						ArrayDatum newDatum = new ArrayDatum(datum);
						newDatum.setMagnitude((float)col[p++]);
						newAssay.add(newDatum);
					}
				}
			}
		}
		return outData;
	}

}
