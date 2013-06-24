/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/

package org.rti.webgenome.sandbox.load;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.rti.webgenome.core.WebGenomeSystemException;
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
            throw new WebGenomeSystemException("Error generating graphic", e);
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
