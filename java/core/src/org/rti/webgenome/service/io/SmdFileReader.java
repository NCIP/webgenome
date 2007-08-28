/*
$Revision: 1.3 $
$Date: 2007-08-28 17:24:13 $

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

package org.rti.webgenome.service.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssayData;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.units.BpUnits;

/**
 * Reader for SMD (Stanford Microarray Database)-format files.
 * 
 * These files are 'rectangular,' i.e. text format with
 * lines (rows) consisting of an equal number of columns
 * of values separated by a delimiting character, typically
 * ',' or '\t.'  The name, chromosome, and physical chromosome
 * position of reporters must be provided in a reporter annotation
 * file provided to the constructor.  Then, any number of data
 * files may be read.  These data files are also SMD format
 * with one column giving reporter name and additional columns
 * providing experimental results.
 * @author dhall
 *
 */
public final class SmdFileReader {
    
    // =======================================
    //      Constants
    // =======================================
    
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(SmdFileReader.class);
    
    
    // =============================
    //       Attributes
    // =============================
    
    /** Cache of reporter objects indexed on name. */
    private final Map<String, Reporter> reporters =
    	new HashMap<String, Reporter>();
    
    /** Units for {@code positionColumnHeading}. */
    private final BpUnits units;
    

    
    // ==============================
    //        Constructors
    // ==============================
    
    
    /**
     * Constructor.
     * @param reporterFile File containing reporter 'annotations,' including
     * reporter names, chromosomes, and physical chromosomal locations
     * @param reporterNameColumnHeading Heading of column in reporter file
     * containing reporter names
     * @param chromosomeColumnHeading Heading of column in reporter file
     * containing chromosome numbers
     * @param positionColumnHeading Heading of column in reporter file
     * containing physical chromosome positions
     * @param units Units of physical chromosome positions
     * @param format File format
     * @throws SmdFormatException If file is not in SMD format
     */
    public SmdFileReader(final File reporterFile,
    		final String reporterNameColumnHeading,
    		final String chromosomeColumnHeading,
    		final String positionColumnHeading,
    		final BpUnits units,
    		final RectangularTextFileFormat format)
        throws SmdFormatException {
        this.units = units;
        this.loadReporters(reporterFile, reporterNameColumnHeading,
        		chromosomeColumnHeading, positionColumnHeading,
        		format);
    }
    
    
    /**
     * Constructor.
     * @param absolutePath Name of absolute path to SMD-format file
     * @param reporterNameColumnHeading Heading of column in reporter file
     * containing reporter names
     * @param chromosomeColumnHeading Heading of column in reporter file
     * containing chromosome numbers
     * @param positionColumnHeading Heading of column in reporter file
     * containing physical chromosome positions
     * @param units Units of physical chromosome positions
     * @param format File format
     * @throws SmdFormatException If file is not in SMD format
     */
    public SmdFileReader(final String absolutePath,
    		final String reporterNameColumnHeading,
    		final String chromosomeColumnHeading,
    		final String positionColumnHeading,
    		final BpUnits units,
    		final RectangularTextFileFormat format)
        throws SmdFormatException {
        this(new File(absolutePath), reporterNameColumnHeading,
        		chromosomeColumnHeading, positionColumnHeading, units,
        		format);
    }
    
    
    /**
     * Load and cache reporters.
     * @param reporterFile File containing reporter 'annotations,' including
     * reporter names, chromosomes, and physical chromosomal locations
     * @param reporterNameColumnHeading Heading of column in reporter file
     * containing reporter names
     * @param chromosomeColumnHeading Heading of column in reporter file
     * containing chromosome numbers
     * @param positionColumnHeading Heading of column in reporter file
     * containing physical chromosome positions
     * @param format File format
     * @throws SmdFormatException if reporter name,
     * chromosome, or position column is not present.
     */
    private void loadReporters(final File reporterFile,
    		final String reporterNameColumnHeading,
    		final String chromosomeColumnHeading,
    		final String positionColumnHeading,
    		final RectangularTextFileFormat format)
    throws SmdFormatException {
    	RectangularFileReader reader = new RectangularFileReader(reporterFile);
    	reader.setDelimiter(format.getDelimiter());
        
        // Get index of reporter-related columns
        List<String> colHeadings =
            reader.getColumnHeadings();
        int nameColIdx = this.indexOfString(colHeadings,
                reporterNameColumnHeading, false);
        int chromColIdx = this.indexOfString(colHeadings,
                chromosomeColumnHeading, false);
        int posColIdx = this.indexOfString(colHeadings,
                positionColumnHeading, true);
        if (nameColIdx < 0 || chromColIdx < 0 || posColIdx < 0) {
            throw new SmdFormatException("File must have reporter name, "
                    + "chromosome, and position columns");
        }
        
        // Load reporter-related columns into memory
        List<String> nameCol = 
            reader.getColumn(nameColIdx);
        List<String> chromCol = 
            reader.getColumn(chromColIdx);
        List<String> posCol = 
            reader.getColumn(posColIdx);
               
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
                long pos = this.units.toBp((long)
                    (Double.parseDouble(posStr)));
                this.reporters.put(name, new Reporter(name, chrom, pos));
            } catch (NumberFormatException e) {
                LOGGER.warn("Could not parse reporter information at "
                        + "line number " + lineNum);
            } finally {
            	lineNum++;
            }
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
     * Get reporters parsed form file.
     * @return Reporters
     */
    public List<Reporter> getReporters() {
        List<Reporter> reps = new ArrayList<Reporter>();
        reps.addAll(this.reporters.values());
        Collections.sort(reps);
        return reps;
    }
    
    
    /**
     * Get bioassay data corresponding to the given
     * data column name.  If any records in the file
     * have reporters that were not the reporter file
     * provided in the constructor, these will be excluded.
     * @param file Data file to parse
     * @param dataColumnName Name of column containing bioassay data
     * to parse
     * @param reporterNameColumnName Name of column providing
     * reporter names
     * @param format File format
     * @return Bioassay data
     */
    public BioAssayData getBioAssayData(
    		final File file, final String dataColumnName,
    		final String reporterNameColumnName,
    		final RectangularTextFileFormat format) {
    	RectangularFileReader reader = new RectangularFileReader(file);
    	reader.setDelimiter(format.getDelimiter());
        int dataColIdx = this.indexOfString(reader.getColumnHeadings(),
                dataColumnName, false);
        int nameColIdx = this.indexOfString(reader.getColumnHeadings(),
        		reporterNameColumnName, false);
        List<String> nameCol = reader.getColumn(nameColIdx);
        List<String> dataCol = reader.getColumn(dataColIdx);
        BioAssayData bad = new BioAssayData();
        long lineNum = 1;
        Iterator<String> names = nameCol.iterator();
        Iterator<String> values = dataCol.iterator();
        while (names.hasNext() && values.hasNext()) {
        	String name = names.next();
            Reporter r = this.reporters.get(name);
            if (r == null) {
            	LOGGER.warn("Reporter '" + name + "' in data file '"
            			+ file.getName()
            			+ "' unknown");
            } else {
            	String valueStr = values.next();
            	try {
            		float value = Float.parseFloat(valueStr);
            		bad.add(new ArrayDatum(value, r));
            	} catch (NumberFormatException e) {
                    LOGGER.warn("Error parsing bioassay data value from "
                            + "line number " + lineNum);
                }
            }
            lineNum++;
        }
        return bad;
    }
}
