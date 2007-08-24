/*
$Revision: 1.2 $
$Date: 2007-08-24 21:51:58 $

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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

import org.apache.log4j.Logger;
import org.rti.webgenome.analysis.AnalyticException;
import org.rti.webgenome.analysis.SlidingWindowSmoother;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataSerializedBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.service.io.SmdFormatException;
import org.rti.webgenome.util.FileUtils;


/**
 * This class simulates the manipulation
 * of data during the upload of a large
 * file, statistical processing, and image
 * generation.  These tests are used to
 * ensure that the design is adequate to
 * scale to very large data sets.
 * @author dhall
 *
 */
public class DataManipulationSimulator {
    
    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(DataManipulationSimulator.class);
    
    // ==============================
    //    Business methods
    // ==============================
    
    
    /**
     * Simulate the manipulation of data that come
     * from an SMD (Stanford Microarray Database)
     * file.  Method extracts data from the file,
     * serializes it, then accesses all data to
     * simulate performing an analytic operation
     * on data.
     * @param smdFilePath Path to input SMD-format data file
     * @param tempDirPath Path to temporary directory that will
     * contain serialized data
     * @param numDataAccesses Number of times the entire
     * set of data will be recovered from disk during simulation
     * of analytic operations
     * @return The number of milliseconds of system time
     * that elapse during the execution of the simulation
     * @throws SmdFormatException if the input SMD file is not
     * in proper SMD format
     */
    public final long simulateDataManipulation(final String smdFilePath,
            final String tempDirPath, final int numDataAccesses)
        throws SmdFormatException {
        long startTime = System.currentTimeMillis();
        
//        // Serialize data
//        LOGGER.info("Serializing data");
//        FileUtils.createDirectory(tempDirPath);
//        DataFileManager mgr = new DataFileManager(tempDirPath);
//        File smdFile = new File(smdFilePath);
//        Experiment exp = mgr.convertSmdData(smdFile, new Organism());
//        LOGGER.info("Finished serializing data");
//        
//        // Perform simulation
//        LOGGER.info("Performing simulations");
//        AnalyticOperationSimulator sim =
//            new SingleBioassayInMemoryAnalyticOperationSimulator();
//        sim.perform(mgr, exp, numDataAccesses);
//        LOGGER.info("Finished simulations");
//        
//        long endTime = System.currentTimeMillis();
//        
//        // Remove serialized data
//        mgr.deleteDataFiles(exp, true);
//        
//        return endTime - startTime;
        return 1;
    }
    
    
    // =================================
    //       Main method
    // =================================
    
    /**
     * Main method.
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        
        // Get command line args
        if (args.length != 3) {
            printUsageAndExit(1);
        }
        String smdFilePath = args[0];
        String tempDirPath = args[1];
        int numIterations = -1;
        try {
            numIterations = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            printUsageAndExit(1);
        }
        File smdFile = new File(smdFilePath);
        File tempDir = new File(tempDirPath);
        if (!smdFile.exists() || !tempDir.exists()
                || !tempDir.isDirectory()) {
            printUsageAndExit(1);
        }
        
        // Run simulations
        DataManipulationSimulator sim = new DataManipulationSimulator();
        try {
            sim.simulateDataManipulation(smdFilePath,
                    tempDirPath, numIterations);
        } catch (SmdFormatException e) {
            System.err.println("SMD file not in proper format");
            printUsageAndExit(1);
        }
    }
    
    
    /**
     * Print usage statement and exit.
     * @param exitCode Exit code
     */
    private static void printUsageAndExit(final int exitCode) {
        String usage = "java test.load.DataManipulationSimulator "
            + "SMD_FILE_PATH TEMP_DIR_PATH NUM_ITERATIONS";
        System.err.println(usage);
        System.exit(exitCode);
    }
    
    
    // ===============================
    //    Inner classes
    // ===============================
    
    
    /**
     * Interface defining operations that will be
     * performed by classes that simulate analytic
     * operations on the data.
     * @author dhall
     *
     */
    static interface AnalyticOperationSimulator {
        
        /**
         * Perform simulation.
         * @param dataFileManager Manager of data files
         * that will be loaded for this simulation
         * @param experiment Experiment from which to
         * load data
         * @param numIterations Number of iterations
         * of the simulation
         */
        void perform(DataFileManager dataFileManager, Experiment experiment,
                int numIterations);
    }
    
    
    /**
     * Simulates analytic operations that must
     * load data from all analytic operations into
     * memory at the same time.  Method retrieves
     * data chromosome by chromosome.  In other words,
     * the method iterates over chromosomes and loads
     * data from that chromosome from all bioassays.
     * @author dhall
     *
     */
    static class AllBioassayInMemoryAnalyticOperationSimulator
        implements AnalyticOperationSimulator {
                
        /**
         * Perform simulation.
         * @param dataFileManager Manager of data files
         * that will be loaded for this simulation
         * @param experiment Experiment from which to
         * load data
         * @param numIterations Number of iterations
         * of the simulation
         */
        public void perform(final DataFileManager dataFileManager,
                final Experiment experiment, final int numIterations) {
            for (int i = 0; i < numIterations; i++) {
                LOGGER.info("Starting simulation iteration " + (i + 1));
                SortedSet<Short> chromosomes =
                    experiment.getChromosomes();
                for (Short chrom : chromosomes) {
                    LOGGER.info("Loading all data from chromosome "
                            + chrom);
                    Collection<ChromosomeArrayData> cads =
                        new ArrayList<ChromosomeArrayData>();
                    for (BioAssay ba : experiment.getBioAssays()) {
                        cads.add(dataFileManager.loadChromosomeArrayData(
                        		(DataSerializedBioAssay) ba,
                                chrom));
                    }
                }
                LOGGER.info("Completed simulation iteration " + (i + 1));
            }
        }
    }
    
    
    /**
     * Simulates analytic operations that
     * load data from a single analytic operations into
     * memory at any given time.  Class actually calls
     * a simple smoothing operation.
     * @author dhall
     *
     */
    static class SingleBioassayInMemoryAnalyticOperationSimulator
        implements AnalyticOperationSimulator {
                
        /**
         * Perform simulation.  Method actually calls
         * a smoothing operation.
         * @param dataFileManager Manager of data files
         * that will be loaded for this simulation
         * @param experiment Experiment from which to
         * load data
         * @param numIterations Number of iterations
         * of the simulation
         */
        public void perform(final DataFileManager dataFileManager,
                final Experiment experiment, final int numIterations) {
            for (int i = 0; i < numIterations; i++) {
                LOGGER.info("Starting simulation iteration " + (i + 1));
                SortedSet<Short> chromosomes =
                    experiment.getChromosomes();
                for (Short chrom : chromosomes) {
                    LOGGER.info("Loading all data from chromosome "
                            + chrom);
                    for (BioAssay ba : experiment.getBioAssays()) {
                        ChromosomeArrayData in =
                            dataFileManager.loadChromosomeArrayData(
                                    (DataSerializedBioAssay) ba, chrom);
                        SlidingWindowSmoother smoother =
                            new SlidingWindowSmoother();
                        try {
                            smoother.perform(in);
                        } catch (AnalyticException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                LOGGER.info("Completed simulation iteration " + (i + 1));
            }
        }
    }

}
