/*
$Revision: 1.1 $
$Date: 2007-03-21 23:09:37 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.service.plot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.service.plot.BarPlotPainter;
import org.rti.webcgh.service.plot.BarPlotParameters;
import org.rti.webcgh.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webcgh.util.FileUtils;
import org.rti.webgenome.client.BioAssayDataConstraints;
import org.rti.webgenome.client.ExperimentDTO;
import org.rti.webgenome.client.ExperimentDTOGenerator;
import org.rti.webgenome.client.QuantitationTypes;


/**
 * Tester for <code>BarPlotPainter</code>.
 * @author dhall
 *
 */
public final class BarPlotPainterTester extends TestCase {
	
	/**
	 * Name of directory that will contain test output files.
	 * The absolute path will be a subdirectory to the
	 * main test directory defined by the property
	 * 'temp.dir' in the file 'unit_test.properties.'
	 */
	private static final String TEST_DIR_NAME = "bar-plot-painter-test";
	
	/** Directory that will hold test output files. */
	private static final File TEST_DIR =
		FileUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Gap between generated reporters. */
	private static final long GAP = 1000000;
	
	/** Number of simulated bioassays. */
	private static final int NUM_BIOASSAYS = 3;
	
	/** Number of simulated experiments. */
	private static final int NUM_EXPERIMENTS = 2;
	
	/** Chromosome number. */
	private static final String CHROMOSOME = "1";
	
	/** Starting chromosome position. */
	private static final long START_POS = 1;
	
	/** Ending chromosome position. */
	private static final long END_POS = 100000000;
	
	/**
	 * Test paintPlot() method.
	 */
	public void testPaintPlot() {
		BioAssayDataConstraints constraints = new BioAssayDataConstraints();
		constraints.setChromosome(CHROMOSOME);
		constraints.setPositions(START_POS, END_POS);
		constraints.setQuantitationType(QuantitationTypes.COPY_NUMBER);
		BioAssayDataConstraints[] constraintsArr =
			new BioAssayDataConstraints[] {constraints};
		ExperimentDTOGenerator gen =
			new ExperimentDTOGenerator(GAP, NUM_BIOASSAYS);
		Collection<Experiment> experiments = new ArrayList<Experiment>();
		for (int i = 0; i < NUM_EXPERIMENTS; i++) {
			String id = "Experiment " + i;
			ExperimentDTO dto = gen.newExperimentDTO(id, constraintsArr);
			Experiment exp = new Experiment(dto, constraintsArr);
			experiments.add(exp);
		}
		BarPlotPainter painter = new BarPlotPainter(
				new InMemoryChromosomeArrayDataGetter());
		BarPlotParameters params = new BarPlotParameters();
		params.setNumPlotsPerRow(8);
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		painter.paintPlot(panel, experiments, params);
		panel.toPngFile("plot.png");
	}
}
