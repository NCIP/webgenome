/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:09 $

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

package org.rti.webcgh.sandbox.load;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

import org.apache.log4j.Logger;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.graphics.PlotBoundaries;
import org.rti.webcgh.graphics.widget.ScatterPlot;
import org.rti.webcgh.service.io.DataFileManager;
import org.rti.webcgh.service.io.SmdFormatException;
import org.rti.webcgh.service.util.SerializedChromosomeArrayDataGetter;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.VerticalAlignment;
import org.rti.webcgh.util.FileUtils;
import org.rti.webcgh.util.StopWatch;

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
        
        // Create directories
        LOGGER.info("Creating directories");
        FileUtils.createDirectory(tempDirPath);
        FileUtils.createDirectory(outputDirPath);
        LOGGER.info("Finished creating directories");
        
        // Serialize data
        LOGGER.info("Serializing data");
        DataFileManager mgr = new DataFileManager(tempDirPath);
        File smdFile = new File(smdFilePath);
        Experiment exp = mgr.convertSmdData(smdFile, new Organism());
        LOGGER.info("Finished serializing data");
        
        // Create chromosome array data getter
        SerializedChromosomeArrayDataGetter getter =
        	new SerializedChromosomeArrayDataGetter();
        getter.setDataFileManager(mgr);
        
        // Create plots
        LOGGER.info("Creating plots");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        SortedSet<Short> chromosomes = exp.getChromosomes();
        Collection<Experiment> experiments = new ArrayList<Experiment>();
        experiments.add(exp);
        for (Short chrom : chromosomes) {
            LOGGER.info("Creating plot of chromosome " + chrom);
//            SvgTestPanel canvas = SvgTestPanel.newSvgTestPanel();
//            canvas.setSvgDirectory(new File(outputDirPath));
//            ScatterPlot plot = new ScatterPlot(experiments, chrom, getter,
//                    PLOT_WIDTH, PLOT_HEIGHT, PLOT_BOUNDARIES);
//            canvas.add(plot, HorizontalAlignment.CENTERED,
//                    VerticalAlignment.CENTERED);
//            String fname = chrom + ".svg";
//            canvas.toSvgFile(fname);
            LOGGER.info("Finished creating plot of chromosome " + chrom);
        }
        LOGGER.info("Completed plotting");
        LOGGER.info("Elapsed time in plotting: "
                + stopWatch.getFormattedElapsedTime());
        
        // Clean up
        LOGGER.info("Deleting serialized data");
        mgr.deleteDataFiles(exp, true);
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
