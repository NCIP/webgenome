/*
$Revision: 1.1 $
$Date: 2007-02-14 17:47:49 $

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

package org.rti.webcgh.graphics.widget.unit_test;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.domain.AnnotatedGenomeFeature;
import org.rti.webcgh.domain.AnnotationType;
import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.widget.AnnotationTrack;
import org.rti.webcgh.util.FileUtils;

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
		FileUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
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
