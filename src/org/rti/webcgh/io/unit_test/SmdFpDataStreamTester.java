/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/io/unit_test/SmdFpDataStreamTester.java,v $
$Revision: 1.2 $
$Date: 2006-05-26 17:25:10 $

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
package org.rti.webcgh.io.unit_test;

import junit.framework.TestCase;

import java.io.*;
import java.util.List;

import org.rti.webcgh.io.SmdFpDataStream;
import org.rti.webcgh.array.*;

public class SmdFpDataStreamTester extends TestCase {

    private String testData =
            "Name,Chromosome,Position,BioAssay1,BioAssay2,AnyName\n" +
            "RP11-82D16,1,2,0.106308,0.106318,0.1\n" +
            "RP11-62M23,1,3,-0.010037,-0.010027,0.2\n" +
            "RP11-111O5,1,4,-0.010037,-0.010027,0.3\n" +
            "RP11-51B4,1,5,0.053391,0.053401,0.4\n" +
            "RP11-60J11,1,6,0.055755,0.055765,0.5\n" +
            "RP11-813J5,1,7,-0.008973,-0.008963,-1.6\n" +
            "RP11-199O1,1,8,0.056527,0.056537,-1.7\n" +
            "RP11-188F7,1,9,-0.087739,-0.087729,0.8\n" +
            "RP11-178M15,1,10,-0.058804,-0.058794,0.9" ;

    // Data set without Chromosome and Position
    private String testDataWithoutPosition =
            "Name,BioAssay1,BioAssay2\n" +
            "RP11-82D16,0.106308,0.106318\n" +
            "RP11-62M23,-0.010037,-0.010027" ;


    /**
     * Test the loadExperiment() method.
     *
     * @throws Exception
     */
    public void testLoadExperiment( ) throws Exception {
        try {
            // Convert the string to a input stream
            InputStream is = new ByteArrayInputStream( testData.getBytes("UTF-8") );

            GenomeAssembly ga = GenomeAssembly.DUMMY_GENOME_ASSEMBLY ;

            SmdFpDataStream smd = new SmdFpDataStream() ;
            Experiment exp = smd.loadExperiment( is, QuantitationType.LOG_2_RATIO, ga ) ;

            // * Did we get anything?
            assertTrue ( exp != null ) ;

            // * Should be 3 BioAssays
            assertTrue ( exp.getBioAssays().size() == 3 ) ;

            // * Check that BioAssay names match what we expect
            assertTrue ( exp.getBioAssay ( "BioAssay1" ) != null ) ;
            assertTrue ( exp.getBioAssay ( "BioAssay2" ) != null ) ;
            assertTrue ( exp.getBioAssay ( "AnyName" ) != null ) ;
            BioAssay bioAnyName = exp.getBioAssay( "AnyName" ) ;
            assertEquals ( bioAnyName.getName(), "AnyName" ) ;
            BioAssay bio1= exp.getBioAssay( "AnyName" ) ;
            assertEquals ( bio1.getName(), "AnyName" ) ;

            // * Take a look at BioAssay1's BioAssayData
            BioAssay b1 = exp.getBioAssay ( "BioAssay1" ) ;
            assertTrue ( b1 != null ) ;
            BioAssayData bioAssayData = b1.getBioAssayData() ;
            assertTrue ( bioAssayData.containsArrayData() ) ;
            assertTrue ( bioAssayData.numArrayDatum() == 9 ) ;

            // * Test ArrayDatum's Quantitation values for BioAssay1
            List <ArrayDatum> adList = bioAssayData.getArrayData() ;
            assertEquals ( getQuantitationValue ( adList.get( 0 ) ), (float) 0.106308 ) ;
            assertEquals ( getQuantitationValue ( adList.get( 1 ) ), (float) -0.010037 ) ;
            assertEquals ( getQuantitationValue ( adList.get( 8 ) ), (float) -0.058804 ) ;

            // * Test Reporter for BioAssay1's ArrayDatum
            ArrayDatum ad3 = adList.get( 3 ) ;
            Reporter rep = ad3.getReporter() ;
            assertEquals ( rep.getName(), "RP11-51B4" ) ;

            // * Test Reporter Chromosome and Position
            ReporterMapping rm = rep.getReporterMapping() ;
            GenomeLocation gl = rm.getGenomeLocation() ;
            Chromosome chromo = gl.getChromosome() ;
            assertEquals ( chromo.getNumber(), 1 ) ;
            assertEquals ( gl.getLocation(), 5 ) ;

        }
        catch ( Exception e ) {
            System.err.println ( e ) ;
        }
    }

    /**
     * Test the loadExperiment() method on a data set which doesn't have CHROMOSOME and POSITION.
     *
     * @throws Exception
     */
    public void testLoadExperimentWithoutPositionData( ) throws Exception {
        try {
            // Convert the string to a input stream
            InputStream is = new ByteArrayInputStream( testDataWithoutPosition.getBytes("UTF-8") );

            GenomeAssembly ga = GenomeAssembly.DUMMY_GENOME_ASSEMBLY ;

            SmdFpDataStream smd = new SmdFpDataStream() ;
            Experiment exp = smd.loadExperiment( is, QuantitationType.LOG_2_RATIO, ga ) ;

            // * Did we get anything?
            assertTrue ( exp != null ) ;

            // * Should be 2 BioAssays
            assertTrue ( exp.getBioAssays().size() == 2 ) ;

            // * Check that BioAssay names match what we expect
            assertTrue ( exp.getBioAssay ( "BioAssay1" ) != null ) ;
            assertTrue ( exp.getBioAssay ( "BioAssay2" ) != null ) ;

            // * Take a look at BioAssay2's BioAssayData
            BioAssay b1 = exp.getBioAssay ( "BioAssay2" ) ;
            assertTrue ( b1 != null ) ;
            BioAssayData bioAssayData = b1.getBioAssayData() ;
            assertTrue ( bioAssayData.containsArrayData() ) ;
            assertTrue ( bioAssayData.numArrayDatum() == 2 ) ;

            // * Test ArrayDatum's Quantitation values for BioAssay1
            List <ArrayDatum> adList = bioAssayData.getArrayData() ;
            assertEquals ( getQuantitationValue ( adList.get( 0 ) ), (float) 0.106318 ) ;
            assertEquals ( getQuantitationValue ( adList.get( 1 ) ), (float) -0.010027 ) ;

            // * Test Reporter for BioAssay1's ArrayDatum
            ArrayDatum ad1 = adList.get( 1 ) ;
            Reporter rep = ad1.getReporter() ;
            assertEquals ( rep.getName(), "RP11-62M23" ) ;

        }
        catch ( Exception e ) {
            System.err.println ( e ) ;
        }
    }

    private float getQuantitationValue ( ArrayDatum ad ) {
        return ad.getQuantitation().getValue() ;
    }
}
