/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:28 $

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

package org.rti.webgenome.sandbox.load;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.rti.webgenome.core.WebcghSystemException;
import org.rti.webgenome.graphics.RasterDrawingCanvas;
import org.rti.webgenome.graphics.primitive.Circle;
import org.rti.webgenome.util.MathUtils;


/**
 * A simulator that draws a large number of data points
 * on a canvas.  This class is used to test the size
 * of files of different formats and different numbers
 * of data points.
 * @author dhall
 *
 */
public final class DataPointSimulator {
    
    // ============================
    //       Constants
    // ============================
    
    /** Default radius of data points. */
    private static final int DEF_RADIUS = 3;
    
    
    // ===============================
    //       Attributes
    // ===============================
    
    /** Radius of data points. */
    private int radius = DEF_RADIUS;
        
    
    // =========================
    //    Getters/setters
    // =========================

    /**
     * Set radius of data points.
     * @param radius Radius
     */
    public void setRadius(final int radius) {
        this.radius = radius;
    }
    
    /**
     * Get radius of data points.
     * @return Radius
     */
    public int getRadius() {
        return this.radius;
    }
    
    
    // =========================
    //     Constructors
    // =========================
    
    /**
     * Constructor.
     */
    public DataPointSimulator() {
        
    }
    
    
    // ========================
    //       Business methods
    // ========================
    
    /**
     * Run simulation producing a graphic file containing
     * the specified number of random data points.
     * @param numDataPoints Number of data points to create
     * @param width Width of graphic in pixels
     * @param height Height of graphic in pixels
     * @param filePath Path to output graphic file
     */
    public void runSimulation(final int numDataPoints, final int width,
            final int height, final String filePath) {
        
        // Check args
        if (numDataPoints < 1) {
            throw new IllegalArgumentException(
                    "Number of data points must be a positive integer");
        }
        if (width < 1) {
            throw new IllegalArgumentException(
                    "Width must be a positive integer");
        }
        if (height < 1) {
            throw new IllegalArgumentException(
                    "Height must be a positive integer");
        }
        if (filePath == null) {
            throw new IllegalArgumentException("File path unspecified");
        }
        
        // Run simulation
        RasterDrawingCanvas canvas = new RasterDrawingCanvas();
        canvas.setWidth(width);
        canvas.setHeight(height);
        CircleGenerator gen = new CircleGenerator(this.radius,
                width, height);
        for (int i = 0; i < numDataPoints; i++) {
            Circle c = gen.newRandomCircle();
            canvas.add(c);
        }
        BufferedImage img = canvas.toBufferedImage();
        File file = new File(filePath);
        try {
            ImageIO.write(img, "png", file);
        } catch (IOException e) {
            throw new WebcghSystemException("Error generating graphic", e);
        }
    }
    
    
    // ===========================
    //     Main method
    // ===========================
    
    /**
     * Main method that runs a simulation
     * creating a graphic file with
     * data points.
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        
        // Get command line args
        if (args.length != 4) {
            printUsageAndExit(1);
        }
        int width = 0, height = 0, numDataPoints = 0;
        try {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            numDataPoints = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            printUsageAndExit(1);
        }
        String filePath = args[3];
        
        // Run simulation
        DataPointSimulator sim = new DataPointSimulator();
        sim.runSimulation(numDataPoints, width, height, filePath);
    }
    
    
    /**
     * Print usage message and exit.
     * @param exitCode Exit code
     */
    private static void printUsageAndExit(final int exitCode) {
        String usage = "java test.load.DataPointSimulator WIDTH "
            + "HEIGHT NUM_DATA_POINTS FILE_PATH";
        System.err.println(usage);
        System.exit(exitCode);
    }
    
    // ============================
    //     Inner classes
    // ============================
    
    /**
     * Generate circles at random locations on graphic
     * and with random colors.
     * @author dhall
     *
     */
    static class CircleGenerator {
        
        /** Maximum saturation value for a RGB color compoment. */
        private static final int MAX_SATURATION = 255;
        
        /** Minimum x-coordinate that a circle can have. */
        private int minX = 0;
        
        /** Maximum x-coordinate that a circle can have. */
        private int maxX = 0;
        
        /** Minimum y-coordinate that a circle can have. */
        private int minY = 0;
        
        /** Maximum y-coordinate that a circle can have. */
        private int maxY = 0;
        
        /** Radius of circles. */
        private int radius = 0;
        
        /**
         * Constructor.
         * @param radius Radius of circle
         * @param width Width of drawing canvas in pixels
         * @param height Height of drawing canvas in pixels
         */
        public CircleGenerator(final int radius, final int width,
        		final int height) {
            minX = radius;
            maxX = width - radius;
            minY = radius;
            maxY = height - radius;
            this.radius = radius;
        }
        
        /**
         * Generate new circle at random location on
         * graphic and with random color.
         * @return A circle
         */
        public Circle newRandomCircle() {
            int x = MathUtils.randomInt(this.minX, this.maxX);
            int y = MathUtils.randomInt(this.minY, this.maxY);
            int red = MathUtils.randomInt(0, MAX_SATURATION);
            int green = MathUtils.randomInt(0, MAX_SATURATION);
            int blue = MathUtils.randomInt(0, MAX_SATURATION);
            Color color = new Color(red, green, blue);
            return new Circle(x, y, radius, color);
        }
        
    }
}
