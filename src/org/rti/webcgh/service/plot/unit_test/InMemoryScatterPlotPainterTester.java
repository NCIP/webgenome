/*
$Revision: 1.1 $
$Date: 2006-09-05 14:06:45 $

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

package org.rti.webcgh.service.plot.unit_test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import junit.framework.TestCase;

import org.rti.webcgh.domain.ArrayDatumGenerator;
import org.rti.webcgh.domain.DataContainingBioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.drawing.RasterDrawingCanvas;
import org.rti.webcgh.plot.ScatterPlotParameters;
import org.rti.webcgh.service.plot.InMemoryScatterPlotPainter;
import org.rti.webcgh.util.FileUtils;
import org.rti.webcgh.util.SystemUtils;

/**
 * Tester for <code>InMemoryScatterPlotPainter</code>.
 * @author dhall
 *
 */
public final class InMemoryScatterPlotPainterTester extends TestCase {
    
    /**
     * Name of directory containing test output files.
     * This is not an absolute path.
     */
    private static final String TEST_DIR = "in-mem-scatter-plot";
    
    /** Number of datum to add to each bioassay. */
    private static final int NUM_DATUM = 200;
    
    /** Minimum Y-axis value. */
    private static final float MIN_Y = (float) -2.0;
    
    /** Maximum Y-axis value. */
    private static final float MAX_Y = (float) 2.0;
    
    /** Width of plot in pixels. */
    private static final int WIDTH = 500;
    
    /** Height of plot in pixels. */
    private static final int HEIGHT = 500;
    
    
    /**
     * Test paintScatterPlot() method.
     * @throws Exception if an error occurs while
     * writing graphics to file
     */
    public void testPaintScatterPlot() throws Exception {
        
        // Create test data
        Organism org = new Organism("Homo", "sapiens");
        Experiment exp = new Experiment("exp");
        DataContainingBioAssay b1 = new DataContainingBioAssay("b1", org);
        b1.setColor(Color.BLUE);
        DataContainingBioAssay b2 = new DataContainingBioAssay("b2", org);
        b2.setColor(Color.RED);
        ArrayDatumGenerator gen = new ArrayDatumGenerator();
        for (int i = 0; i < NUM_DATUM; i++) {
            b1.add(gen.newArrayDatum());
        }
        gen.reset();
        for (int i = 0; i < NUM_DATUM; i++) {
            b2.add(gen.newArrayDatum());
        }
        exp.add(b1);
        exp.add(b2);
        Collection<Experiment> experiments = new ArrayList<Experiment>();
        experiments.add(exp);
        
        // Create plot parameters
        ScatterPlotParameters params = new ScatterPlotParameters();
        List<Reporter> reporters = gen.getReporters();
        params.setChromosome(reporters.get(0).getChromosome());
        params.setStartLocation(reporters.get(0).getLocation());
        params.setEndLocation(reporters.get(
                reporters.size() - 1).getLocation());
        params.setMinY(MIN_Y);
        params.setMaxY(MAX_Y);
        
        // Create drawing canvas
        RasterDrawingCanvas canvas = new RasterDrawingCanvas();
        
        // Run method
        InMemoryScatterPlotPainter painter = new InMemoryScatterPlotPainter();
        painter.paintScatterPlot(experiments, canvas, params, WIDTH, HEIGHT,
                QuantitationType.LOG_2_RATIO);
        
        // Output graphics to file
        String testDirPath =
            SystemUtils.getUnitTestProperty("temp.dir") + "/" + TEST_DIR;
        FileUtils.createDirectory(testDirPath);
        String filePath = testDirPath + "/plot.png";
        BufferedImage img = canvas.toBufferedImage();
        ImageIO.write(img, "png", new File(filePath));
    }

}
