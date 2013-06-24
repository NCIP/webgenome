/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/SmdFileGenerator.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $



*/

package org.rti.webgenome.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used for testing.  It generates
 * SMD (Stanford Microarray Database) format
 * files.
 * @author dhall
 *
 */
public final class SmdFileGenerator {
    
    
    // ============================
    //      Constructor
    // ============================
    
    /**
     * Constructor.
     */
    public SmdFileGenerator() {
        
    }
    
    
    // =============================
    //    Business methods
    // =============================
    
    /**
     * Generates an SMD-format file with comma-separated values.
     * The values of the actual
     * data points will be randomly generated over the
     * interval (-1.0, 1.0).  The physical location of reporters
     * will be uniformly distributes over chromosomes.
     * @param path Absolute path to file that will be generated
     * @param numBioAssays Number of bioassays that will be generated
     * @param reportersPerChromosome Number of reporters per chromome.
     * Each chromosome will contain the same number.
     * @param numChromosomes Number of chromosomes
     * @param chromosomeSize Size of chromosomes in base pairs.  All
     * chromosomes will be the same size.
     * @throws IOException if there is an error writing to file.
     */
    public void generateFile(final String path, final int numBioAssays,
            final int reportersPerChromosome, final int numChromosomes,
            final long chromosomeSize) throws IOException {
        List<Short> chromosomesList = new ArrayList<Short>();
        List<Integer> reportersPerChromosomeList = new ArrayList<Integer>();
        List<Long> chromosomeSizesList = new ArrayList<Long>();
        for (short i = (short) 1; i <= (short) numChromosomes; i++) {
            chromosomesList.add(i);
            reportersPerChromosomeList.add(reportersPerChromosome);
            chromosomeSizesList.add(chromosomeSize);
        }
        this.generateFile(path, numBioAssays, chromosomesList,
                reportersPerChromosomeList, chromosomeSizesList);
    }
    
    
    /**
     * Generates an SMD-format file with comma-separated values. 
     * The values of the actual
     * data points will be randomly generated over the
     * interval (-1.0, 1.0).  The physical location of reporters
     * will be uniformly distributes over chromosomes.
     * @param path Absolute path to file that will be generated
     * @param numBioAssays Number of bioassays that will be generated
     * @param chromosomes Chromosome numbers
     * @param reportersPerChromosome Number of reporters generated for
     * each chromosome.  The order of this list should reflect the
     * order of the <code>chromosomes</code> list.
     * @param chromosomeSizes The size of chromosomes in base pairs.
     * The order of this list should reflect the
     * order of the <code>chromosomes</code> list.
     * @throws IOException if there is an error writing to file
     */
    public void generateFile(final String path, final int numBioAssays,
            final List<Short> chromosomes,
            final List<Integer> reportersPerChromosome,
            final List<Long> chromosomeSizes) throws IOException {
        
        // Make sure arguments okay
        if (chromosomes == null || reportersPerChromosome == null
                || chromosomeSizes == null) {
            throw new IllegalArgumentException(
                    "All arguments must be non-null");
        }
        if (reportersPerChromosome.size() != chromosomes.size()
                || chromosomeSizes.size() != chromosomes.size()) {
            throw new IllegalArgumentException(
                    "The size of the three lists must be the same");
        }
        
        // Output file header
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        StringBuffer buff = new StringBuffer("Name,Chromosome,Position");
        for (int i = 0; i < numBioAssays; i++) {
            buff.append(",Bioassay_" + (i + 1));
        }
        out.write(buff.toString() + "\n");
        
        // Output file body
        Iterator<Short> cIt = chromosomes.iterator();
        Iterator<Integer> rIt = reportersPerChromosome.iterator();
        Iterator<Long> sIt = chromosomeSizes.iterator();
        int count = 0;
        while (cIt.hasNext() && rIt.hasNext() && sIt.hasNext()) {
            short chromosome = cIt.next();
            int reporters = rIt.next();
            long size = sIt.next();
            long gap = size / (long) reporters;
            for (int i = 0; i < reporters; i++) {
                StringBuffer line = new StringBuffer();
                
                // Reporter name
                line.append("reporter_" + (++count));
                
                // Chromosome
                line.append("," + chromosome);
                
                // Position
                line.append("," + (long) i * gap);
                
                // Bioassays
                for (int j = 0; j < numBioAssays; j++) {
                    line.append("," + this.randomFloat());
                }
                
                out.write(line.toString() + "\n");
            }
        }
        out.close();
    }
    
    
    /**
     * Generate random floating point number
     * over interval (0, 1).
     * @return A random floating point number
     */
    private float randomFloat() {
        return (float) (-1.0 + Math.random() * 2.0);
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
        if (args.length != 5) {
            printUsageAndExit(1);
        }
        String path = args[0];
        int numBioAssays = -1;
        int numChromosomes = -1;
        int numReportersPerChromosome = -1;
        long chromosomeSize = (long) -1;
        try {
            numBioAssays = Integer.parseInt(args[1]);
            numChromosomes = Integer.parseInt(args[2]);
            numReportersPerChromosome = Integer.parseInt(args[3]);
            chromosomeSize = Long.parseLong(args[4]);
        } catch (NumberFormatException e) {
            printUsageAndExit(1);
        }
        
        // Generate file
        SmdFileGenerator gen = new SmdFileGenerator();
        try {
            gen.generateFile(path, numBioAssays, numReportersPerChromosome,
                    numChromosomes, chromosomeSize);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Print "usage" message and exit with given exit code.
     * @param exitCode Exit code
     */
    private static void printUsageAndExit(final int exitCode) {
        String usage = "Usage: java.org.rti.webcgh.util.SmdFileGenerator "
            + "PATH NUM_BIOASSAYS NUM_CHROMOSOMES NUM_REPORTERS_PER_CHROMOSOME "
            + "CHROMOSOME_SIZE";
        System.err.println(usage);
        System.exit(exitCode);
    }

}
