/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.service.plot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.DataContainingBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.graphics.widget.RasterFileTestPlotPanel;
import org.rti.webgenome.service.dao.AnnotatedGenomeFeatureDao;
import org.rti.webgenome.service.plot.AnnotationPlotPainter;
import org.rti.webgenome.service.plot.AnnotationPlotParameters;
import org.rti.webgenome.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;


/**
 * Tester for <code>AnnotationPlotPainter</code>.
 * @author dhall
 *
 */
public final class AnnotationPlotPainterTester extends TestCase {
	
	//
	//     STATICS
	//
	
	/**
	 * Name of directory that will contain test output files.
	 * The absolute path will be a subdirectory to the
	 * main test directory defined by the property
	 * 'temp.dir' in the file 'unit_test.properties.'
	 */
	private static final String TEST_DIR_NAME = "annotation-plot-painter-test";
	
	/** Directory that will hold test output files. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Plot width in pixels. */
	private static final int WIDTH = 500;
	
	
	//
	//     TEST CASES
	//
	
	/**
	 * Test paintPlot() method.
	 */
	public void testPaintPlot() {
		
		// Create test data
		Organism organism = new Organism("genus", "species");
		DataContainingBioAssay ba = new DataContainingBioAssay(
				"Bioassay 1", organism);
		ba.add(new ArrayDatum((float) 1.0,
				new Reporter(null, (short) 1, 1)));
		ba.add(new ArrayDatum((float) 0.5,
				new Reporter(null, (short) 1, 100)));
		ba.add(new ArrayDatum((float) 0.3,
				new Reporter(null, (short) 1, 120)));
		ba.add(new ArrayDatum((float) 1.2,
				new Reporter(null, (short) 1, 150)));
		ba.add(new ArrayDatum((float) 0.1,
				new Reporter(null, (short) 1, 300)));
		ba.add(new ArrayDatum((float) 0.2,
				new Reporter(null, (short) 1, 325)));
		ba.add(new ArrayDatum((float) 0.5,
				new Reporter(null, (short) 1, 400)));
		ba.add(new ArrayDatum((float) 1.0,
				new Reporter(null, (short) 1, 500)));
		Experiment exp = new Experiment("Experiment 1", organism,
				QuantitationType.COPY_NUMBER);
		exp.add(ba);
		Collection<Experiment> experiments = new ArrayList<Experiment>();
		experiments.add(exp);
		
		// Create plot painter
		AnnotationPlotPainter painter = new AnnotationPlotPainter(
				new InMemoryChromosomeArrayDataGetter());
		painter.setAnnotatedGenomeFeatureDao(
				new MockAnnotatedGenomeFeatureDao());
		
		// Create plot parameters
		AnnotationPlotParameters params = new AnnotationPlotParameters();
		params.add(AnnotationType.GENE);
		params.setDrawFeatureLabels(true);
		params.setUnits(BpUnits.BP);
		params.add(new GenomeInterval((short) 1, 1, 500));
		params.setWidth(WIDTH);
		
		// Paint plot
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		painter.paintPlot(panel, experiments, params);
		
		// Send plot to file
		panel.toPngFile("plot.png");
	}
	
	
	//
	//     HELPER CLASSES
	//
	
	/**
	 * Mock object implementing <code>AnnotatedGenomeFeatureDao</code>
	 * which returns annotatated genome features specifically for
	 * these test cases.
	 */
	private static final class MockAnnotatedGenomeFeatureDao
	implements AnnotatedGenomeFeatureDao	 {
		
		/**
		 * Persist given feature.
		 * @param feature Feature to persist
		 */
		public void save(final AnnotatedGenomeFeature feature) {
			
		}
		
		/**
		 * Un-persist all features of given type and organism.
		 * @param annotationType Feature type
		 * @param organism Organism
		 */
		public void deleteAll(final AnnotationType annotationType,
				final Organism organism) {
			
		}
		
		/**
		 * Load all annotated features of given type
		 * and organism in given genomic
		 * range.
		 * @param chromosome Chromosome number
		 * @param startPos Starting position of range
		 * @param endPos Ending position of range
		 * @param annotationType Annotation feature type
		 * @param organism Organism
		 * @return Annotated features
		 */
		public SortedSet<AnnotatedGenomeFeature> load(
				final short chromosome, final long startPos,
				final long endPos,
				final AnnotationType annotationType,
				final Organism organism) {
			SortedSet<AnnotatedGenomeFeature> feats =
				new TreeSet<AnnotatedGenomeFeature>();
			
			// Add first gene
			AnnotatedGenomeFeature gene = new AnnotatedGenomeFeature(
					(short) 1, 100, 200, AnnotationType.GENE,
					"Gene 1");
			feats.add(gene);
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					100, 110, AnnotationType.EXON));
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					150, 180, AnnotationType.EXON));
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					185, 200, AnnotationType.EXON));
			
			// Add second gene
			gene = new AnnotatedGenomeFeature(
					(short) 1, 160, 300, AnnotationType.GENE,
					"Gene 2");
			feats.add(gene);
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					160, 170, AnnotationType.EXON));
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					190, 195, AnnotationType.EXON));
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					230, 250, AnnotationType.EXON));
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					290, 300, AnnotationType.EXON));
			
			// Add third gene
			gene = new AnnotatedGenomeFeature(
					(short) 1, 400, 500, AnnotationType.GENE,
					"Gene 3");
			feats.add(gene);
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					400, 410, AnnotationType.EXON));
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					450, 455, AnnotationType.EXON));
			gene.addChild(new AnnotatedGenomeFeature((short) 1,
					480, 500, AnnotationType.EXON));
			
			return feats;
		}
		
		/**
		 * Return all organisms who have gene data loaded in
		 * database.
		 * @return All organisms who have gene data loaded in
		 * database
		 */
		public Set<Organism> organismsWithLoadedGenes() {
			return null;
		}
		
		/**
		 * Get all annotation types with data for given organism.
		 * @param org An organism
		 * @return Annotation types
		 */
		public Set<AnnotationType> availableAnnotationTypes(
				final Organism org) {
			return null;
		}
	}

}
