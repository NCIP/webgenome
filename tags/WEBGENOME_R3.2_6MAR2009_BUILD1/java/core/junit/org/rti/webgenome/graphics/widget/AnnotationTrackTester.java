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

package org.rti.webgenome.graphics.widget;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.graphics.widget.AnnotationTrack;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>AnnotationTrack</code>.
 * @author dhall
 *
 */
public final class AnnotationTrackTester extends TestCase {

	//
	//     STATICS
	//
	
	/**
	 * Name (not absolute path) of directory where test files
	 * will be written.
	 */
	private static final String TEST_DIR_NAME = "annotation-track-test";
	
	/** Directory where test files will be written. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Width of track in pixels. */
	private static final int WIDTH = 500;
	
	
	//
	//     TEST CASES
	//
	
	/**
	 * Test on genes showing feature labels.
	 */
	public void testGenesWithLabels() {
		
		// Set up
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		SortedSet<AnnotatedGenomeFeature> features =
			new TreeSet<AnnotatedGenomeFeature>();
		
		// First gene, running off left side
		AnnotatedGenomeFeature gene = new AnnotatedGenomeFeature(
				(short) 1, 10, 70, AnnotationType.GENE,
				"Gene 1");
		features.add(gene);
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				10, 15, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				65, 70, AnnotationType.EXON));
		
		// Second gene
		gene = new AnnotatedGenomeFeature(
				(short) 1, 100, 200, AnnotationType.GENE,
				"Gene 2");
		features.add(gene);
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				100, 110, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				150, 180, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				185, 200, AnnotationType.EXON));
		
		// Third gene, overlaping second
		gene = new AnnotatedGenomeFeature(
				(short) 1, 160, 300, AnnotationType.GENE,
				"Gene 3");
		features.add(gene);
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				160, 165, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				170, 180, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				250, 270, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				290, 300, AnnotationType.EXON));
		
		// Third gene, running off right
		gene = new AnnotatedGenomeFeature(
				(short) 1, 900, 1100, AnnotationType.GENE,
				"Gene 3");
		features.add(gene);
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				900, 920, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				1050, 1100, AnnotationType.EXON));
		
		// Create track
		AnnotationTrack track = new AnnotationTrack(features,
				WIDTH, 50, 1000, "Genes", true,
				panel.getDrawingCanvas());
		panel.add(track);
		
		// Write to file
		panel.toPngFile("genes-labels.png");
	}
	
	
	/**
	 * Test on genes without feature labels.
	 */
	public void testGenesWithoutLabels() {
		
		// Set up
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		SortedSet<AnnotatedGenomeFeature> features =
			new TreeSet<AnnotatedGenomeFeature>();
		
		// First gene, running off left side
		AnnotatedGenomeFeature gene = new AnnotatedGenomeFeature(
				(short) 1, 10, 70, AnnotationType.GENE,
				"Gene 1");
		features.add(gene);
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				10, 15, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				65, 70, AnnotationType.EXON));
		
		// Second gene
		gene = new AnnotatedGenomeFeature(
				(short) 1, 100, 200, AnnotationType.GENE,
				"Gene 2");
		features.add(gene);
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				100, 110, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				150, 180, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				185, 200, AnnotationType.EXON));
		
		// Third gene, overlaping second
		gene = new AnnotatedGenomeFeature(
				(short) 1, 160, 300, AnnotationType.GENE,
				"Gene 3");
		features.add(gene);
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				160, 165, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				170, 180, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				250, 270, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				290, 300, AnnotationType.EXON));
		
		// Third gene, running off right
		gene = new AnnotatedGenomeFeature(
				(short) 1, 900, 1100, AnnotationType.GENE,
				"Gene 3");
		features.add(gene);
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				900, 920, AnnotationType.EXON));
		gene.addChild(new AnnotatedGenomeFeature((short) 1,
				1050, 1100, AnnotationType.EXON));
		
		// Create track
		AnnotationTrack track = new AnnotationTrack(features,
				WIDTH, 50, 1000, "Genes", false,
				panel.getDrawingCanvas());
		panel.add(track);
		
		// Write to file
		panel.toPngFile("genes-no-labels.png");
	}
}
