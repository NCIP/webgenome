/*
$Revision: 1.1 $
$Date: 2007-09-11 22:52:24 $

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

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.DataContainingBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.graphics.InterpolationType;
import org.rti.webgenome.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for {@link org.rti.webgenome.graphics.widget.GenomicSnapshotPlot}.
 * @author dhall
 *
 */
public class GenomeSnapshotPlotTester extends TestCase {
	
	/** Name of directory where test output files are written. */
	private static final String TEST_DIR_NAME = "genomic-snapshot-plot-test";
	
	/** Directory where test output files are written. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Width of plot in pixels. */
	private static final int WIDTH = 600;
	
	/** Height of plot in pixels. */
	private static final int HEIGHT = 300;
	
	/** All test experiments including copy number and expression. */
	private Collection<Experiment> experiments = new ArrayList<Experiment>();
	
	/** Experiments containing copy number data. */
	private Collection<Experiment> copyNumberExperiments =
		new ArrayList<Experiment>();
	
	/** Experiments containing expression data. */
	private Collection<Experiment> expressionExperiments =
		new ArrayList<Experiment>();
	
	/** Minimum value on Y-axis of plot. */
	private float minY = (float) 0.0;
	
	/** Maximum value on Y-axis of plot. */
	private float maxY = (float) 1.0;
	
	/** Array data getter. */
	private InMemoryChromosomeArrayDataGetter getter =
		new InMemoryChromosomeArrayDataGetter();
	
	/**
	 * Constructor.
	 */
	public GenomeSnapshotPlotTester() {
		
		// Copy number test experiment
		Experiment exp = new Experiment();
		this.experiments.add(exp);
		this.copyNumberExperiments.add(exp);
		exp.setName("Copy number");
		DataContainingBioAssay ba = new DataContainingBioAssay();
		exp.add(ba);
		exp.setQuantitationType(QuantitationType.LOG_2_RATIO_COPY_NUMBER);
		ba.setName("Copy number");
		ba.setColor(new Color(0, 0, 255));
		ba.setId((long) 1);
		ba.add(new ArrayDatum((float) 0.1, new Reporter(null, (short) 1, 100)));
		ba.add(new ArrayDatum((float) 0.3, new Reporter(null, (short) 1, 200)));
		ba.add(new ArrayDatum((float) 0.2, new Reporter(null,
				(short) 1, 300)));
		ba.add(new ArrayDatum((float) 0.7, new Reporter(null, (short) 2, 50)));
		ba.add(new ArrayDatum((float) 0.8, new Reporter(null, (short) 2, 100)));
		ba.add(new ArrayDatum((float) 0.6, new Reporter(null,
				(short) 2, 150)));
		ba.add(new ArrayDatum((float) 0.2, new Reporter(null, (short) 3, 25)));
		ba.add(new ArrayDatum((float) 0.3, new Reporter(null, (short) 3, 50)));
		ba.add(new ArrayDatum((float) 0.1, new Reporter(null,
				(short) 3, 75)));
		
		// Expression test experiment
		exp = new Experiment();
		this.experiments.add(exp);
		this.expressionExperiments.add(exp);
		exp.setName("Expression");
		exp.setQuantitationType(QuantitationType.LOG_2_RATIO_FOLD_CHANGE);
		ba = new DataContainingBioAssay();
		exp.add(ba);
		ba.setName("Expression");
		ba.setColor(new Color(255, 0, 0));
		ba.setId((long) 2);
		ba.add(new ArrayDatum((float) 1.0, new Reporter(null, (short) 1, 100)));
		ba.add(new ArrayDatum((float) 0.1, new Reporter(null, (short) 1, 200)));
		ba.add(new ArrayDatum((float) 0.5, new Reporter(null,
				(short) 3, 25)));
	}

	
	/**
	 * Test on both copy number and expression data.
	 */
	public void testCoViz() {
		
		// Create plot panel
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		
		// Create plot
		GenomeSnapshotPlot plot = new GenomeSnapshotPlot(this.experiments,
				this.getter, WIDTH, HEIGHT, minY, maxY);
		plot.setInterpolationType(InterpolationType.SPLINE);
		panel.add(plot);
		
		// Write graphics to file
		panel.toPngFile("co-viz.png");
	}
}
