/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-09-11 22:52:24 $


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
