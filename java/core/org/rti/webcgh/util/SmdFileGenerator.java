/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/org/rti/webcgh/util/SmdFileGenerator.java,v $
$Revision: 1.1 $
$Date: 2007-03-27 19:42:07 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.util;

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
