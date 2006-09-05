/*

$Source$
$Revision$
$Date$

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

package org.rti.webcgh.graph;

import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.DataSet;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.ExperimentIterator;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.graph.widget.HorizontalLine;
import org.rti.webcgh.plot.Axis;
import org.rti.webcgh.plot.PlotPanel;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;


/**
 * Class to generate bar graphs.
 */
public class BarGraphGenerator {
	
	private static final int EXTRA_PADDING = 4;
	
	/**
	 * Generate bar graph
	 * @param panel Plot panel to add plot elements to
	 * @param dataSet A data set
	 * @param reporterNames Names of reporters from which to retrieve
	 * data to plot
	 * @param scale Plotting scale in pixels per plot unit
	 */
	public void generateBarGraph(PlotPanel panel, DataSet dataSet, 
			List<String> reporterNames, double scale) {
		BarGroupGenerator barGroupGenerator = new BarGroupGenerator();
		int count = 0;
		double min = 0.0, max = 0.0;
		QuantitationType quantType = null;
		for (String reporterName : reporterNames) {
			PlotPanel childPanel = panel.newChildPlotPanel();
			List<DataPoint> dataPoints = new ArrayList<DataPoint>();
			for (ExperimentIterator ei = dataSet.experimentIterator(); ei.hasNext();) {
				Experiment exp = ei.next();
				for (BioAssayIterator bai = exp.bioAssayIterator(); bai.hasNext();) {
					BioAssay bioAssay = bai.next();
					ArrayDatum datum = 
						bioAssay.getArrayDatumByReporterName(reporterName);
					if (datum != null) {
						DataPoint dp = new DataPoint();
						datum.initializeDataPoint(dp);
						dp.setLabel(bioAssay.getName());
						dataPoints.add(dp);
						double value = dp.value2PlusError();
						if (value < min)
							min = value;
						if (value > max)
							max = value;
					}
				}
			}
			barGroupGenerator.addBarGroup(childPanel, dataPoints, reporterName, scale);
			if (count++ > 0)
				panel.addExtraPadding(EXTRA_PADDING, Location.RIGHT_OF);
			panel.add(childPanel, HorizontalAlignment.RIGHT_OF, VerticalAlignment.ON_ZERO);
		}
		int length = (int)((max - min) * scale);
		Axis axis = new Axis(min, max, length, Orientation.VERTICAL, Location.LEFT_OF);
		panel.add(axis, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.ON_ZERO);
		HorizontalLine line = new HorizontalLine(panel.width());
		panel.add(line, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.ON_ZERO);
	}

}
