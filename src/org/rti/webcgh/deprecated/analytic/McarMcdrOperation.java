/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/analytic/McarMcdrOperation.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:32:38 $

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


package org.rti.webcgh.deprecated.analytic;

import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.analysis.AnalyticException;
import org.rti.webcgh.deprecated.array.BioAssay;
import org.rti.webcgh.deprecated.array.BioAssayIterator;
import org.rti.webcgh.deprecated.array.ChromosomalAlterationSet;
import org.rti.webcgh.deprecated.array.Experiment;
import org.rti.webcgh.deprecated.graph.PlotParameters;

public class McarMcdrOperation implements SummaryStatisticOperation {
	
	private Long id = null;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public DataSetInvalidations validate(Experiment[] data,
			PlotParameters params) {
		DataSetInvalidations invalidations = null;
		if (Double.isNaN(params.getLowerMaskValue()) || 
				Double.isNaN(params.getUpperMaskValue())) {
			invalidations = new DataSetInvalidations();
			invalidations.addInvalidation(new DataSetInvalidation(
					"MCAR/MCDR operation requires both minimum and maximum data mask to be set"));
		}
		return invalidations;
	}

	public Experiment[] perform(Experiment[] data, PlotParameters params)
			throws AnalyticException {
		if (data == null)
			return null;
		Experiment[] newData = new Experiment[data.length + 1];
		for (int i = 0; i < data.length; i++)
			newData[i] = data[i];
		Experiment newExp = new Experiment();
		newData[data.length] = newExp;
		newExp.setName("Minimum Altered Regions");
		
		if (data.length > 0) {
			List ampSets = new ArrayList();
			List delSets = new ArrayList();
			for (int i = 0; i < data.length; i++) {
				Experiment exp = data[i];
				for (BioAssayIterator it = exp.bioAssayIterator(); it.hasNext();) {
					BioAssay bioAssay = it.next();
					ampSets.add(bioAssay.amplifiedRegions(params.getUpperMaskValue()));
					delSets.add(bioAssay.deletedRegions(params.getLowerMaskValue()));
				}
			}
			ChromosomalAlterationSet[] amps = new ChromosomalAlterationSet[0];
			ChromosomalAlterationSet[] dels = new ChromosomalAlterationSet[0];
			amps = (ChromosomalAlterationSet[])ampSets.toArray(amps);
			dels = (ChromosomalAlterationSet[])delSets.toArray(dels);
			ChromosomalAlterationSet ampX = ChromosomalAlterationSet.intersection(amps);
			ChromosomalAlterationSet delX = ChromosomalAlterationSet.intersection(dels);
			newExp.addAmplifications(ampX);
			newExp.addDeletions(delX);
		}
		return newData;
	}

}
