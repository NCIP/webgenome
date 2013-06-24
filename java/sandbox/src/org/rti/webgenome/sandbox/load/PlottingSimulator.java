/*
$Revision: 1.2 $
$Date: 2007-08-24 21:51:58 $


*/

package org.rti.webgenome.sandbox.load;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.graphics.PlotBoundaries;
import org.rti.webgenome.graphics.widget.ScatterPlot;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.service.io.SmdFormatException;
import org.rti.webgenome.service.util.SerializedChromosomeArrayDataGetter;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.FileUtils;
import org.rti.webgenome.util.StopWatch;

/**
 * Class to simulate a large data plotting job.
 * @author dhall
 *
 */
public final class PlottingSimulator {
    
    // =================================
    //      Constants
    // =================================
    
    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(PlottingSimulator.class);
    
    /** Width of plots in pixels. */
    private static final int PLOT_WIDTH = 500;
    
    /** Height of plots in pixels. */
    private static final int PLOT_HEIGHT = 500;
    
    /**
     * Bondaries of plot in native units
     * (i.e., base pairs vs. some quantitation type.
     */
    private static final PlotBoundaries PLOT_BOUNDARIES =
        new PlotBoundaries(0, -2.0, 500000000, 2.0);
    
    
    // ===============================
    //   Business methods
    // ===============================
    
    /**
     * Simulated a plotting session.  This method reads
     * in data from an SMD-format file and generates
     * a plot for each chromosome.  Plots are saved
     * in SVG format.
     * @param smdFilePath Absolulte path to SMD-format input
     * data file
     * @param outputDirPath Absolute path of directory where
     * @param tempDirPath Absolute path to where temporary
     * files holding serialized objects will be written
     * SVG-format plots will be written
     * @throws SmdFormatException If input file is not
     * in proper SMD format
     */
    public void simulatePlotting(final String smdFilePath,
            final String outputDirPath, final String tempDirPath)
        throws SmdFormatException {
        
//        // Create directories
//        LOGGER.info("Creating directories");
//        FileUtils.createDirectory(tempDirPath);
//        FileUtils.createDirectory(outputDirPath);
//        LOGGER.info("Finished creating directories");
//        
//        // Serialize data
//        LOGGER.info("Serializing data");
//        DataFileManager mgr = new DataFileManager(tempDirPath);
//        File smdFile = new File(smdFilePath);
//        Experiment exp = mgr.convertSmdData(smdFile, new Organism());
//        LOGGER.info("Finished serializing data");
//        
//        // Create chromosome array data getter
//        SerializedChromosomeArrayDataGetter getter =
//        	new SerializedChromosomeArrayDataGetter();
//        getter.setDataFileManager(mgr);
//        
//        // Create plots
//        LOGGER.info("Creating plots");
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        SortedSet<Short> chromosomes = exp.getChromosomes();
//        Collection<Experiment> experiments = new ArrayList<Experiment>();
//        experiments.add(exp);
//        for (Short chrom : chromosomes) {
//            LOGGER.info("Creating plot of chromosome " + chrom);
////            SvgTestPanel canvas = SvgTestPanel.newSvgTestPanel();
////            canvas.setSvgDirectory(new File(outputDirPath));
////            ScatterPlot plot = new ScatterPlot(experiments, chrom, getter,
////                    PLOT_WIDTH, PLOT_HEIGHT, PLOT_BOUNDARIES);
////            canvas.add(plot, HorizontalAlignment.CENTERED,
////                    VerticalAlignment.CENTERED);
////            String fname = chrom + ".svg";
////            canvas.toSvgFile(fname);
//            LOGGER.info("Finished creating plot of chromosome " + chrom);
//        }
//        LOGGER.info("Completed plotting");
//        LOGGER.info("Elapsed time in plotting: "
//                + stopWatch.getFormattedElapsedTime());
//        
//        // Clean up
//        LOGGER.info("Deleting serialized data");
//        mgr.deleteDataFiles(exp, true);
    }
    
    
    // ===========================
    //    Main method
    // ===========================
    
    /**
     * Main method.  Creates plots using data
     * in file specified on command line.
     * @param args Command line args
     */
    public static void main(final String[] args) {
        
        // Get command line args
        if (args.length != 3) {
            printUsageAndExit(1);
        }
        String smdFilePath = args[0];
        String outputDirPath = args[1];
        String tempDirPath = args[2];
        
        // Run simulation
        PlottingSimulator sim = new PlottingSimulator();
        try {
            sim.simulatePlotting(smdFilePath, outputDirPath, tempDirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Print usage statement and exit.
     * @param exitCode Exit code
     */
    private static void printUsageAndExit(final int exitCode) {
        String usage = "java test.load.PlottingSimulator "
            + "SMD_FILE_PATH OUTPUT_DIR_PATH TEMP_DIR_PATH";
        System.err.println(usage);
        System.exit(exitCode);
    }

}
