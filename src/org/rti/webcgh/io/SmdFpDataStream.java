/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/io/SmdFpDataStream.java,v $
$Revision: 1.8 $
$Date: 2006-06-16 19:41:23 $

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
import org.rti.webcgh.core.WebcghApplicationException;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.service.DomainObjectFactory;

/**
 * <p>Convert a stream of floating point SMD (Stanford Microarray Database) data into data objects.</p>
 *
 * <p>The following describes the SMD data file format:</p>
 * <ol>
 * <li>Data are delimited text (e.g., comma-separated values)</li>
 * <li>Each column, with the exception of special columns,
 *     corresponds to a separate bioassay (i.e., physical array).</li>
 * <li>Each row, with the exception of the first (i.e., column headings),
 *     corresponds to a reporter (i.e., probe).</li>
 * <li>The following are special columns:
 *         <ol style="list-style-type: lower-roman">
 *         <li>The first column contains reporter names (can have any column name)</li>
 *         <li>OPTIONAL: The file may contain columns with
 *             headings named 'CHROMOSOME' and
 *             either 'POSITION', 'KB_POSITION' or 'MB_POSITION'.
 *             These contain the chromosome number and physical
 *             position (i.e., base pair), respectively,
 *             of the corresponding reporter.
 *             If the position column is headed by KB_POSITION, the physical position
 *             is expected to be expressed in thousands units, therefore it
 *             will be multiplied by 1,000 to get its true position value.
 *             If the position column is headed by MB_POSITION, the position
 *             is expected to be in millions units, therefore the 
 *             it will be multiplied by 1,000,000 to get the actual position.
 *             The column names may be lower case or upper case.</li>
 *         </ol>
 * </li>
 * <li>The heading (i.e., first row) of each column gives the identifier (name)
 *     of the corresponding bioassay.</li>
 * <li>Values in bioassay columns give the measurements of the corresponding
 *     reporters. Bioassay columns which follow can have any column name (see point 5 above).
 *     There can be any number of them, but there must be at least one.</li>
 * </ol>
 * <p>Example:</p>
 * <pre>
 * &lt;NAME&gt;	CHROMOSOME &lt;POSITION*&gt;    &lt;BIOASSAY1&gt;    &lt;BIOASSAY2&gt;  ...  &lt;BIOASSAY n&gt;
 * RP-1    1            15000           0.3          -0.1      ...     0.2
 * RP-2    1            34000          -0.2           0.5      ...     0.1
 * </pre>
 * * ~ name of the position column can be POSITION, KB_POSITION or MB_POSITION
 *
 */
public class SmdFpDataStream implements SmdDataStream {

    private static final String CHROMOSOME_COL_HEADING  = "CHROMOSOME";   // optional, see Step 4.ii notes above
    // The different column names allowed for the position column.
    private static final String POSITION_COL_HEADING    = "POSITION";     // optional, see Step 4.ii notes above
    private static final String KB_POSITION_COL_HEADING = "KB_POSITION" ; // optional, see Step 4.ii notes above
    private static final String MB_POSITION_COL_HEADING = "MB_POSITION" ; // optional, see Step 4.ii notes above
    // other columns could have any name
    
    private static final String DB_NAME = "File Upload"; // db name we need to be consistent with the rest of the system
    

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
     * point data. Uses the default delimiter to parse the input stream data.
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
        return loadExperiment ( DEFAULT_DELIMITER, in, qt, genomeAssembly ) ; 
    }


    /**
     * Load experiment from an input stream containing SMD (Stanford Microarray Database) floating
     * point data.
     * @param in - InputStream containing the data
     * @param qt - QuantitationType
     * @param genomeAssembly
     * @param experimentName - the name of the experiment
     * @return Experiment - a representation of the the SMD input stream data, loaded into
     * an Experiment comprising BioAssays, ArrayDatums, Reporters et al.
     * @throws SmdFormatException
     */
    public Experiment loadExperiment( InputStream in,
                                      QuantitationType qt,
                                      GenomeAssembly genomeAssembly, String experimentName) throws SmdFormatException {
    	Experiment exp = loadExperiment( DEFAULT_DELIMITER, in, qt, genomeAssembly);
    	exp.setName(experimentName);
    	return exp;
    }
    
    /**
     * Load experiment from an input stream containing SMD (Stanford Microarray Database) floating
     * point data.
     * @param delimiter - The delimiter String to use when decollating/parsing the input stream.
     * @param in - InputStream containing the data
     * @param qt - QuantitationType
     * @param genomeAssembly
     * @return Experiment - a representation of the the SMD input stream data, loaded into
     * an Experiment comprising BioAssays, ArrayDatums, Reporters et al.
     * @throws SmdFormatException
     */
    public Experiment loadExperiment( String delimiter,
                                      InputStream in,
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
            
            dlp = new DelimitedLineParser ( delimiter, line ) ;
            
            // Sort out whether we have chromosome and position info in the data
            boolean havePositions = this.havePositions( dlp ) ;
            int bioAssayBeginIdx =  havePositions ? 3 : 1 ; // which column does our bio assay data start from?
            String positionHeading = null ;
            double positionMultiplier = 1.0 ;
            if ( havePositions ) {
                positionHeading = getPositionHeading ( dlp ) ;
                positionMultiplier = getPositionMultiplier ( positionHeading ) ;
            }

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
                        ReporterMapping reporterMapping = createReporterMapping ( positionHeading, positionMultiplier, line, genomeAssembly, reporter ) ;
                        reporter.setReporterMapping(reporterMapping) ;
                    }

                    try {
                        populateBioAssays ( bioAssays, bioAssayFields, qt, reporter ) ;
                    } catch (WebcghApplicationException e) {
                        throw new SmdFormatException(e);
                    }
                }
            }
            exp.add ( bioAssays ) ;
            
            // Init database to be compliant with other data sources
            exp.setDatabaseName(DB_NAME);
            
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


    /**
     * Load experiment from an input stream containing SMD (Stanford Microarray Database) floating
     * point data.
     * @param delimiter - the delimiter string to use to decollate the data for parsing
     * @param in - InputStream containing the data
     * @param qt - QuantitationType
     * @param genomeAssembly
     * @return Experiment - a representation of the the SMD input stream data, loaded into
     * an Experiment comprising BioAssays, ArrayDatums, Reporters et al.
     * @throws SmdFormatException
     */
    public Experiment loadExperiment( String delimiter,
                                      InputStream in,
                                      QuantitationType qt,
                                      GenomeAssembly genomeAssembly,
                                      String experimentName ) throws SmdFormatException {
        Experiment exp = loadExperiment ( delimiter, in, qt, genomeAssembly ) ;
        exp.setName ( experimentName ) ;
        return exp ;
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
                                     Reporter reporter ) throws WebcghApplicationException {

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
    private ReporterMapping createReporterMapping ( String positionHeading,
                                                    double positionMultiplier,
                                                    String line,
                                                    GenomeAssembly genomeAssembly,
                                                    Reporter reporter ) {
        double chromosome = dlp.getNumericProperty( line, CHROMOSOME_COL_HEADING ) ;
        
        //
        //  Position may be expressed in BP, KB and MB units.
        //  For KB and MB we multiply to get the actual value.
        //
        double position =   dlp.getNumericProperty( line, positionHeading )  ;
        if ( positionMultiplier > 1.0 )
            position = position * positionMultiplier ;
        

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
    private boolean havePositions( DelimitedLineParser dlp ) throws SmdFormatException {
        boolean hasPositions = false ;
        
        boolean hasChromosome = dlp.hasColumn ( CHROMOSOME_COL_HEADING ) ;
        boolean hasPosition = dlp.hasColumn ( POSITION_COL_HEADING )    ||
                              dlp.hasColumn ( KB_POSITION_COL_HEADING ) ||
                              dlp.hasColumn ( MB_POSITION_COL_HEADING ) ;
        
        if ( hasChromosome && ! hasPosition )
            throw new SmdFormatException ( "Have Chromosome, but missing Position column in data" ) ;
        
        if ( ! hasChromosome && hasPosition )
            throw new SmdFormatException ( "Has Position, but missing Chromosome column in data" ) ;
        
        if ( hasChromosome && hasPosition )
            hasPositions = true ;
        
        return hasPositions ;
    }
    
    /**
     * Get the actual position heading present in the heading line. It could either
     * be POSITION, KB_POSITION or MB_POSITION
     * @param dlp
     * @return String
     */
    private String getPositionHeading ( DelimitedLineParser dlp ) {
        String positionHeading = null ;
        
        if ( dlp.hasColumn ( POSITION_COL_HEADING ) )
            positionHeading = POSITION_COL_HEADING ;
        else if ( dlp.hasColumn ( KB_POSITION_COL_HEADING ) )
            positionHeading = KB_POSITION_COL_HEADING ;
        else if ( dlp.hasColumn ( MB_POSITION_COL_HEADING ) )
            positionHeading = MB_POSITION_COL_HEADING ;
        
        return positionHeading ;
    }
    
    /**
     * Checks the position column heading and determines the multiplier
     * needed to obtain the actual position.
     * @param positionHeading
     * @return double - the multiplier we'll use later to get the actual (raw) value of the position
     */
    private double getPositionMultiplier ( String positionHeading ) {
        double positionMultiplier = 1.0 ;
        
        if ( positionHeading.equalsIgnoreCase ( KB_POSITION_COL_HEADING ) )
            positionMultiplier =    1000.0 ;
        else if ( positionHeading.equalsIgnoreCase ( MB_POSITION_COL_HEADING ) ) 
            positionMultiplier = 1000000.0 ;
        
        return positionMultiplier ;
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
