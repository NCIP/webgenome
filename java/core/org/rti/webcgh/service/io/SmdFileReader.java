/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:10 $

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

package org.rti.webcgh.service.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.BioAssayData;
import org.rti.webcgh.domain.Reporter;

/**
 * Reader for SMD (Stanford Microarray Database)-format files.
 * 
 *  * <p>The following describes the SMD data file format:</p>
 * <ol>
 * <li>Data are delimited text (e.g., comma-separated values)</li>
 * <li>Each column, with the exception of special columns,
 *     corresponds to a separate bioassay (i.e., physical array).</li>
 * <li>Each row, with the exception of special columns described below,
 *     corresponds to a reporter (i.e., probe).</li>
 * <li>The following are special columns.  Each of these is required.  Column
 *      heading names are shown in upper case, but the names may be lower case
 *      or a combination of cases.
 *         <ol style="list-style-type: lower-roman">
 *         <li></li>
 *         <li>NAME - This column contains reporter names.</li>
 *         <li>CHROMOSOME - This column contains chromosome numbers to which the
 *         corresponding reporter maps.</li>
 *         <li>LOCATION - This column contains the physical map
 *         location in base pairs
 *         of the left end of the corresponding reporter relative
 *         to the given chromosome.
 *         If the column heading contains one of the suffixes
 *         '_KB'or '_MB' (case insensitive),
 *         then values in this column are interpreted as being in
 *         units of KB (kilobases)
 *         or MB (megabases), respectively. 
 *         </ol>
 * </li>
 * <li>The heading (i.e., first row) of each column gives the identifier (name)
 *     of the corresponding bioassay.</li>
 * <li>Values in bioassay columns give the measurements of the corresponding
 *     reporters. Bioassay columns which follow can have any column name 
 *     (see point 5 above).
 *     There can be any number of them, but there must be at least one.</li>
 * </ol>
 * <p>Example:</p>
 * <pre>
 * &lt;NAME&gt; CHROMOSOME &lt;POSITION*&gt; &lt;BIOASSAY1&gt; ... &lt;BIOASSAY
 * RP-1    1            15000           0.3          ...     0.2
 * RP-2    1            34000          -0.2          ...     0.1
 * </pre>
 * * ~ name of the position column can be POSITION, KB_POSITION or MB_POSITION
 *
 * 
 * @author dhall
 *
 */
public final class SmdFileReader {
    
    // =======================================
    //      Constants
    // =======================================
    
    /**
     * Optional suffix of position column heading that indicates
     * reporter locations are given in units of KB (kilobases).
     */
    private static final String KB_SUFFIX = "_KB";
    
    /**
     * Optional suffix of position column heading that indicates
     * reporter locations are given in units of KB (kilobases).
     */
    private static final String MB_SUFFIX = "_MB";
    
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(SmdFileReader.class);
    
    /**
     * Dummy reporter object to hold the position of records (i.e., rows)
     * where reporter information could not be properly parsed.
     */
    private static final Reporter UNPARSEABLE_REPORTER = new Reporter();
    
    // =============================
    //       Attributes
    // =============================
    
    /** Rectangular file reader. */
    private final RectangularFileReader rectangularFileReader;
    
    /** List to cache reporters parsed when file is initially read. */
    private final List<Reporter> reporters = new ArrayList<Reporter>();
        
    /** Chromosome column heading.  This is treated as case insensitive. */
    private String chromosomeColumnHeading = "chromosome";
    
    /** Reporter name column heading.  This is treated as case insensitive. */
    private String reporterNameColumnHeading = "name";
    
    /**
     * Chromosome position column heading.  This is treated
     * as case insensitive.
     * */
    private String positionColumnHeading = "position";
    
    
    // ============================
    //   Getters and setters
    // ============================
    
    /**
     * Get chromosome column heading.  This is treated as
     * case insensitive.
     * @return Chromosome column header
     */
    public String getChromosomeColumnHeading() {
        return chromosomeColumnHeading;
    }


    /**
     * Set chromosome column heading.  This is treated as
     * case insensitive.
     * @param chromosomeColumnHeader Chromosome column header
     */
    public void setChromosomeColumnHeading(
            final String chromosomeColumnHeader) {
        this.chromosomeColumnHeading = chromosomeColumnHeader;
    }


    /**
     * Get chromosome position column heading.  This is treated as
     * case insensitive.
     * @return Chromosome position column header
     */
    public String getPositionColumnHeading() {
        return positionColumnHeading;
    }


    /**
     * Set chromosome position column heading.  This is treated as
     * case insensitive.
     * @param positionColumnHeading Chromosome position column heading
     */
    public void setPositionColumnHeading(
            final String positionColumnHeading) {
        this.positionColumnHeading = positionColumnHeading;
    }


    /**
     * Get reporter name column heading.  This is treated as
     * case insensitive.
     * @return Reporter name column heading
     */
    public String getReporterNameColumnHeading() {
        return reporterNameColumnHeading;
    }


    /**
     * Set reporter name column heading.  This is treated as
     * case insensitive.
     * @param reporterNameColumnHeading Reporter name column heading
     */
    public void setReporterNameColumnHeading(
            final String reporterNameColumnHeading) {
        this.reporterNameColumnHeading = reporterNameColumnHeading;
    }
    
    
    /**
     * Get reporters parsed form file.
     * @return Reporters
     */
    public List<Reporter> getReporters() {
        return reporters;
    }
    
    // ==============================
    //        Constructors
    // ==============================
    


    /**
     * Constructor.
     * @param file SMD-format file
     * @throws SmdFormatException if file is not in SMD-format
     */
    public SmdFileReader(final File file)
        throws SmdFormatException {
        this.rectangularFileReader = new RectangularFileReader(file);
        this.loadReporters();
    }
    
    
    /**
     * Constructor.
     * @param absolutePath Name of absolute path to SMD-format file
     * @throws SmdFormatException if file is not in SMD-format
     */
    public SmdFileReader(final String absolutePath)
        throws SmdFormatException {
        this(new File(absolutePath));
    }
    
    
    /**
     * Load and cache reporters.
     * @throws SmdFormatException if reporter name,
     * chromosome, or position column is not present.
     */
    private void loadReporters() throws SmdFormatException {
        
        // Get index of reporter-related columns
        List<String> colHeadings =
            this.rectangularFileReader.getColumnHeadings();
        int nameColIdx = this.indexOfString(colHeadings,
                this.reporterNameColumnHeading, false);
        int chromColIdx = this.indexOfString(colHeadings,
                this.chromosomeColumnHeading, false);
        int posColIdx = this.indexOfString(colHeadings,
                this.positionColumnHeading, true);
        if (nameColIdx < 0 || chromColIdx < 0 || posColIdx < 0) {
            throw new SmdFormatException("File must have reporter name, "
                    + "chromosome, and position columns");
        }
        
        // Load reporter-related columns into memory
        List<String> nameCol = 
            this.rectangularFileReader.getColumn(nameColIdx);
        List<String> chromCol = 
            this.rectangularFileReader.getColumn(chromColIdx);
        List<String> posCol = 
            this.rectangularFileReader.getColumn(posColIdx);
        
        // Determine the multiplier for reporter locations
        String actualPositionColumnHeading =
            colHeadings.get(posColIdx).toUpperCase();
        double positionMultiplier = 1.0;
        if (actualPositionColumnHeading.endsWith(KB_SUFFIX)) {
            positionMultiplier = 1000.0;
        } else if (actualPositionColumnHeading.endsWith(MB_SUFFIX)) {
            positionMultiplier = 1000000.0;
        }
        
        // Create and cache reporters
        Iterator<String> names = nameCol.iterator();
        Iterator<String> chroms = chromCol.iterator();
        Iterator<String> poses = posCol.iterator();
        long lineNum = 1;
        while (names.hasNext() && chroms.hasNext() && poses.hasNext()) {
            try {
                String name = names.next();
                String chromStr = chroms.next();
                String posStr = poses.next();
                short chrom = Short.parseShort(chromStr);
                long pos = (long)
                    (Double.parseDouble(posStr) * positionMultiplier);
                this.reporters.add(new Reporter(name, chrom, pos));
            } catch (NumberFormatException e) {
                LOGGER.warn("Could not parse reporter information at "
                        + "line number " + lineNum);
                this.reporters.add(UNPARSEABLE_REPORTER);
            }
            lineNum++;
        }
    }
    
    
    /**
     * Find index of search string within list of strings.
     * If the <code>prefix</code> argument is set to <code>false</code>,
     * then the seach string must exactly match the target string.
     * If this argument is set to <code>true</code>, then
     * the search string must only be a prefix of the target.
     * string. Comparison is case-insensitive.
     * @param strings List of strings to search through
     * @param searchString String to search for
     * @param prefix If set to <code>false</code>,
     * then the seach string must exactly match the target string.
     * If this argument is set to <code>true</code>, then
     * the search string must only be a prefix of the target.
     * string. 
     * @return Index of search string within list of strings, or -1
     * if search string not found.
     */
    private int indexOfString(final List<String> strings,
            final String searchString, final boolean prefix) {
        assert searchString != null && searchString.length() > 0;
        String searchStringLc = searchString.toLowerCase();
        int idx = -1;
        for (int i = 0; i < strings.size() && idx < 0; i++) {
            String target = strings.get(i);
            if (prefix) {
                if (target.toLowerCase().indexOf(searchStringLc) == 0) {
                    idx = i;
                }
            } else {
                if (searchString.equalsIgnoreCase(target)) {
                    idx = i;
                }
            }
        }
        return idx;
    }
    
    
    // =================================
    //     Public methods
    // =================================

    /**
     * Get names of bioassays contained in file.  All columns
     * except the chromosome, location, and reporter name columns
     * are assumed to correspond to bioassays.  This method returns
     * the headers of these columns.
     * @return Bioassay names
     */
    public List<String> getBioAssayNames() {
        List<String> colHeadings =
            this.rectangularFileReader.getColumnHeadings();
        for (Iterator<String> it = colHeadings.iterator(); it.hasNext();) {
            if (this.isReporterColHeading(it.next())) {
                it.remove();
            }
        }
        return colHeadings;
    }
    
    
    /**
     * Is given string a reporter-related column heading?
     * Comparison to reporter-related column names is
     * case-insensitive.
     * @param string A string
     * @return T/F
     */
    private boolean isReporterColHeading(final String string) {
        return
            this.reporterNameColumnHeading.equalsIgnoreCase(string)
            || this.chromosomeColumnHeading.equalsIgnoreCase(string)
            || string.toLowerCase().indexOf(
                    this.positionColumnHeading.toLowerCase()) == 0;
    }
    
    
    /**
     * Get bioassay data corresponding to the given
     * bioassay name.  All columns
     * except the chromosome, location, and reporter name columns
     * are assumed to correspond to bioassays.
     * @param bioAssayName Name of bioassay
     * @return Bioassay data
     */
    public BioAssayData getBioAssayData(final String bioAssayName) {
        return this.getBioAssayData(bioAssayName, false);
    }
    
    
    /**
     * Get bioassay data corresponding to the given
     * bioassay name.  All columns
     * except the chromosome, location, and reporter name columns
     * are assumed to correspond to bioassays.
     * @param bioAssayName Name of bioassay
     * @param includeInvalidData SMD file may contain rows
     * where the measured value is not a valid floating point
     * number.  For some applications, the calling class may
     * wish to have these values returned.  If this parameter
     * is set to true, the method will insert
     * an <code>ArrayDatum</code> object whose
     * value is NaN for invalid data points.
     * @return Bioassay data
     */
    public BioAssayData getBioAssayData(final String bioAssayName,
            final boolean includeInvalidData) {
        int colIdx = this.indexOfString(
                this.rectangularFileReader.getColumnHeadings(),
                bioAssayName, false);
        List<String> col = this.rectangularFileReader.getColumn(colIdx);
        BioAssayData bad = new BioAssayData();
        Iterator<Reporter> reporters = this.reporters.iterator();
        Iterator<String> values = col.iterator();
        long lineNum = 1;
        while (reporters.hasNext() && values.hasNext()) {
            Reporter r = reporters.next();
            String valueStr = values.next();
            if (r != UNPARSEABLE_REPORTER) {
                try {
                    float value = Float.parseFloat(valueStr);
                    bad.add(new ArrayDatum(value, r));
                } catch (NumberFormatException e) {
                    LOGGER.warn("Error parsing bioassay data value from "
                            + "line number " + lineNum);
                    if (includeInvalidData) {
                        LOGGER.info("Adding invalid data point");
                        bad.add(new ArrayDatum(Float.NaN, Float.NaN, r));
                    }
                }
            }
            lineNum++;
        }
        return bad;
    }

}
