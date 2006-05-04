/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/io/SmdFpDataStream.java,v $
$Revision: 1.2 $
$Date: 2006-05-04 18:31:14 $

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

package org.rti.webcgh.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.rti.webcgh.array.*;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.service.DomainObjectFactory;

/**
 * Convert a stream of floating point SMD (Stanford Microarray Database) data into data objects.
 *
 * The following describes the SMD data file format:
 * 
 * (1) Data are delimited text (e.g., comma-separated values)
 * (2) Each column, with the exception of special columns,
 *     corresponds to a separate bioassay (i.e., physical array).
 * (3) Each row, with the exception of the first (i.e., column headings),
 *     corresponds to a reporter (i.e., probe).
 * (4) The following are special columns:
 *         i.  The first column contains reporter names (can have any column name)
 *         ii. OPTIONAL: The file may contain columns with
 *             headings named 'CHROMOSOME' and 'POSITION.'
 *             These contain the chromosome number and physical
 *             position (i.e., base pair), respectively,
 *             of the corresponding reporter.
 *             The column names may be lower case or upper case.
 * (5) The heading (i.e., first row) of each column gives the identifier (name)
 *     of the corresponding bioassay.
 * (6) Values in bioassay columns give the measurements of the corresponding
 *     reporters. Bioassay columns which follow can have any column name (see point 5 above).
 *     There can be any number of them, but there must be at least one.
 *
 * <p>Example:</p>
 * <pre>
 * <NAME>	CHROMOSOME		POSITION		<BIOASSAY1>		<BIOASSAY2>  ...  <BIOASSAY n>
 * RP-1		1				15000			0.3				-0.1         ...  0.2
 * RP-2		1				34000			-0.2			0.5          ...  0.1
 * </pre>
 *
 */
public class SmdFpDataStream implements SmdDataStream {

    private static final String CHROMOSOME_COL_HEADING = "CHROMOSOME"; // optional, see Step 4.ii notes above
    private static final String POSITION_COL_HEADING   = "POSITION";   // optional, see Step 4.ii notes above
    // other columns could have any name

    // Parser for extracting field values from a delimited line of text
    private DelimitedLineParser dlp ;

    // Factory for creating and caching domain objects
    private DomainObjectFactory domainObjectFactory = new DomainObjectFactory();


    // ==============================
    //        Constructors
    // ==============================

    public SmdFpDataStream() {}

    // ==============================
    //         Public methods
    // ==============================

    /**
     * Load experiment from an input stream containing SMD (Stanford Microarray Database) floating
     * point data.
     * @param in - InputStream containing the data
     * @param qt - QuantitationType
     * @param genomeAssembly
     * @return Experiment - a representation of the the SMD input stream data, loaded into
     * an Experiment comprising BioAssays, ArrayDatums, Reporters et al.
     * @throws SmdFormatException
     */
    public Experiment loadExperiment( InputStream in,
                                      QuantitationType qt,
                                      GenomeAssembly genomeAssembly ) throws SmdFormatException {

        Experiment exp = new Experiment() ;
        BufferedReader reader = null ;

        try {
            reader = new BufferedReader(new InputStreamReader(in)) ;

            //
            //   Read the headings
            //
            String line = reader.readLine() ;
            if (line == null)
                throw new SmdFormatException("File is empty");
            // Establish a line field parser, passing in the first line which contains the headings
            dlp = new DelimitedLineParser ( line ) ;

            boolean havePositions = this.havePositions( dlp ) ;
            int bioAssayBeginIdx =  havePositions ? 3 : 1 ; // which column does our bio assay data start from?

            BioAssay[] bioAssays = createBioAssays ( dlp.getColumnNames(), bioAssayBeginIdx ) ; // create our bio assay "buckets"

            //
            //    Read the contents of the SMD stream, line by line
            //
            while ( ( line = reader.readLine() ) != null ) {
                String name = dlp.getProperty( line, 0 ) ; // could be any column name, so we just get position zero
                String[] bioAssayFields = dlp.getProperties( line, bioAssayBeginIdx ) ;

                if ( ! isEmpty ( name ) &&
                       bioAssayFields.length > 0 ) { // only process it if we have some data

                    // NAME field value --> Reporter (reporter might be cached if seen before)
                    Reporter reporter = domainObjectFactory.getReporter( name ) ;

                    if ( havePositions && ! reporter.isMapped ( genomeAssembly )) {
                        ReporterMapping reporterMapping = createReporterMapping ( line, genomeAssembly, reporter ) ;
                        reporter.setReporterMapping(reporterMapping) ;
                    }

                    populateBioAssays ( bioAssays, bioAssayFields, qt, reporter ) ;
                }
            }
            exp.add ( bioAssays ) ;

        } catch (IOException e) {
            throw new WebcghSystemException("Error reading data stream", e) ;
        } finally {
            //
            // Close up shop
            //
            if ( reader != null )
                try {
                    reader.close() ;
                } catch ( IOException ignored ) {
                    // TODO: maybe should log this as a Warning?
                }
        }
        return exp;
    }


    // ===============================
    //         Private methods
    // ===============================

    /**
     * Create the initial BioAssays and assign them their names.
     * @param allColumnNames
     * @return Empty BioAssay array for subsequent population
     */
    private BioAssay[] createBioAssays ( String[] allColumnNames,
                                         int bioAssayBeginIdx ) {
        // Pare down the bioAssayNames from the set of all column names
        BioAssay[] bioAssays = new BioAssay[ allColumnNames.length - bioAssayBeginIdx ] ;

        // Add BioAssay placeholders for subsequent population (see populateBioAssays)
        int bioAssayIdx = 0 ;
        for ( int i = bioAssayBeginIdx; i < allColumnNames.length ; i++ ) {
            BioAssay bioAssay = new BioAssay () ;
            bioAssay.setName( allColumnNames [i] );
            BioAssayData bad = new BioAssayData() ;
            bioAssay.setBioAssayData( bad );
            bioAssays[bioAssayIdx++] = bioAssay ;
        }
        return bioAssays ;
    }

    /**
     * Create an ArrayDatum from a line field and add it to its correspdonding BioAssay column.
     * There is a BioAssay object for each column in the SMD file with a name that matches the column.
     *
     * This method is responsible for:
     * 1/ making a Quantitation from the BioAssay data field
     * 2/ making an ArrayDatum from this, by combining it with its associated Reporter
     * 3/ Adding this ArrayDatum to the Array of ArrayDatums kept for each BioAssay column
     *
     * @param bioAssays
     * @param bioAssayFields
     * @param qt
     * @param reporter
     */
    private void populateBioAssays ( BioAssay[] bioAssays,
                                     String[] bioAssayFields,
                                     QuantitationType qt,
                                     Reporter reporter ) {

        for ( int i = 0 ; i < bioAssayFields.length ; i++ ) {
            try {
                double bioAssayRatio = Double.valueOf( bioAssayFields[i] ) ;

                Quantitation quantitation = new Quantitation( (float) bioAssayRatio, qt ) ;
                ArrayDatum datum = new ArrayDatum( reporter, quantitation ) ;

                bioAssays[i].getBioAssayData().add( datum ) ;
            }
            catch ( NumberFormatException currentlyIgnored ) {
                // should this be logged
            }
        }
    }

    /**
     * Create a reporter mapping for our chromosome/position
     * @param line
     * @param genomeAssembly
     * @param reporter
     * @return ReporterMapping
     */
    private ReporterMapping createReporterMapping ( String line,
                                                    GenomeAssembly genomeAssembly,
                                                    Reporter reporter ) {
        double chromosome = dlp.getNumericProperty( line, CHROMOSOME_COL_HEADING ) ;
        double position =   dlp.getNumericProperty( line, POSITION_COL_HEADING ) ;

        Chromosome chromosomeObj = domainObjectFactory.getChromosome (
                genomeAssembly, (short) chromosome ) ;
        GenomeLocation genomeLocation = new GenomeLocation(chromosomeObj, (long) position ) ;
        return new ReporterMapping(reporter, genomeLocation) ;
    }

    /**
     * Test whether the chromosone number and physical
     * position (i.e. base pair) are present in the input stream. Their corresponding
     * column headings will be present in the first line of the input stream (we use
     * the delimited line parser class for determining this).
     * @return true - if present, otherwise false.
     */
    private boolean havePositions( DelimitedLineParser dlp ) {
        return dlp.hasColumn ( CHROMOSOME_COL_HEADING ) &&
               dlp.hasColumn ( POSITION_COL_HEADING ) ;
    }

    /**
     * Test whether a value is empty.
     * @param value
     * @return true, if the value is empty or null, false otherwise
     */
    private boolean isEmpty ( String value ) {
        boolean isEmpty = true ;
        if ( value != null && value.trim().length() > 0 )
            isEmpty = false ;
        return isEmpty ;
    }
}
