/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/DelimitedLineParser.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 21:04:54 $

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

package org.rti.webcgh.deprecated;

import java.util.*;

/**
 * Helper/Utility class which handles the task of retrieving specific delimited fields from
 * a delimited text file or series of text lines from any source really. This class doesn't do IO operations
 * on files or streams, it merely exists to provide convenient methods to extract text from lines
 * input by other classes in this package.
 */
public class DelimitedLineParser {

    private static final String DEFAULT_DELIMITER = "," ;

    private String delimiter = DEFAULT_DELIMITER ; // can be re-assigned through construction

    // Mapping of column headings to column numbers
    private Map colMap = null;
    // A case-sensitive copy of the column names
    private String[] columnNames = null ;

    // ==============================
    //        Constructors
    // ==============================

    /**
     * Uses the default delimiter ",".
     * @param headerLine - String containing the header columns.
     */
    public DelimitedLineParser ( String headerLine ) {
        initialize ( headerLine ) ;
    }

    /**
     * Constructor which lets you specify the delimiter, rather than using the
     * default delimiter value (which is ",").
     * @param delimiter
     * @param headerLine - String containing the header columns (delimited by delimiter).
     */
    public DelimitedLineParser( String delimiter, String headerLine ) {
        this.delimiter = delimiter;
        initialize ( headerLine ) ;
    }

    // ==============================
    //         Public methods
    // ==============================

    /**
     * Get String value associated with columnName from the line.
     * @param line
     * @param columnName Column name
     * @return String value associated with column name
     */
    public String getProperty(String line, String columnName ) {
        return getPropertyCell(line, columnName );
    }

    /**
     * Get String value associated with the field position specified
     * in the line.
     * This get method does a string tokenize on <em>line</em> each time its called - so, if
     * you need to do a series of gets on a sequence of values, consider
     * using getProperties (specifying the beginning index).

     * @param line
     * @param idxPosition
     * @return String value associated with idxPosition, or null if
     * there is no corresponding String value at this location (i.e. out of bounds).
     */
    public String getProperty ( String line, int idxPosition ) {
        return getPropertyCell ( line, idxPosition ) ;
    }

    // TODO, might want to do getProperty ( line, int startIdx, int endIdx ) ?

    /**
     * Get numeric value associated with columnName from line.
     * @param line
     * @param columnName Column name
     * @return Numeric value associated with column name
     */
    public double getNumericProperty(String line, String columnName ) {
        double prop = Double.NaN;
        String cell = getPropertyCell(line, columnName );
        if (cell != null)
            prop = Double.parseDouble(cell);
        return prop;
    }

    /**
     * Get a numeric value at the specified position from the line.
     * This get does a string tokenize on line each time its called - so, if
     * you know you're doing a series of gets on a sequence of values, consider
     * using getProperties and passing the range in.
     * @param line
     * @param idxPosition
     * @return numeric value found at position specified, or Double.NaN (Not-a-Number)
     * if there is no value at the position specified (i.e. out of bounds).
     */
    public double getNumericProperty ( String line, int idxPosition ) {
        double prop = Double.NaN;
        String cell = getPropertyCell ( line, idxPosition ) ;
        if ( cell != null )
            prop = Double.parseDouble ( cell ) ;
        return prop;
    }

    /**
     * Get field values from a line starting at a beginning index.
     * <p><strong>Implementation Notes:</strong></p>
     * This has been kept in line with the Java convention for arrays, with the first element in
     * the array being at position zero. This means the first column will be at position zero - if
     * you specify 1, you'll get the 2nd String field in the array!
     * @param line
     * @param beginIdx
     * @return an array of String values commencing at the specified column index and continuing to the end
     * of the available fields for the particular line.
     */
    public String[] getProperties ( String line, int beginIdx ) {
        ArrayList properties = new ArrayList () ;

        if ( beginIdx > -1 ) {
            String[] fields = tokenizeLine ( line ) ;
            if ( beginIdx < fields.length ) {
                for ( int col = beginIdx ; col < fields.length ; col++ ) {
                    properties.add ( fields [ col ] ) ;
                }
            }
        }

        String [] returnArray = new String[0];
        returnArray = (String[]) properties.toArray( returnArray );
        return returnArray ;

    }

    /**
     * Test method which can be used to verify whether a named column exists in the line.
     * @param testColumnName
     * @return true, if the column name was defined during construction of this parser, false otherwise.
     */
    public boolean hasColumn ( String testColumnName ) {
        boolean hasColumn = false ;

        if ( testColumnName != null ) {
            if ( ! this.colMap.isEmpty() &&
                this.colMap.get( testColumnName.toUpperCase() ) != null )
                hasColumn = true ;
        }

        return hasColumn ;
    }

    /**
     * Get's the column name at the specified column index.
     * @param columnIdx
     * @return Column Name corresponding to position <em>columnIdx</em>
     */
    public String getColumnName ( int columnIdx ) {
        String columnName = null ;

        if ( columnIdx >= 0 && columnIdx < this.columnNames.length ) {
            columnName = this.columnNames[ columnIdx ] ;
        }

        return columnName ;
    }

    public String[] getColumnNames ( ) {
        return this.columnNames ;
    }


    // ==============================
    //         Private methods
    // ==============================

    /**
     * Initialization method which sets up the column mappings for subsequent parsing.
     * @param headerLine
     */
    private void initialize ( String headerLine ) {
        String[] columnHeaders = tokenizeLine ( headerLine ) ;
        if ( columnHeaders.length > 0 )
            createColumnMappings ( columnHeaders ) ;
    }

    /**
     * Just breaks up the line into string tokens and returns these as an array of Strings.
     * @param line
     * @return String[] for each delimited field in the line
     */
    private String[] tokenizeLine ( String line ) {
        ArrayList fields = new ArrayList() ;

        if ( line != null ) {
            StringTokenizer st = new StringTokenizer ( line, delimiter ) ;
            while ( st.hasMoreTokens() ) {
                String field = st.nextToken() ;
                if ( field != null && field.length() > 0 ) {
                    fields.add( field  ) ;
                }
            }
        }

        String [] returnArray = new String[0];
        returnArray = (String[])fields.toArray( returnArray );
        return returnArray ;
    }

    /**
     * Create a mapping between column names and numbers
     * @param columnNames
     */
    private void createColumnMappings( String[] columnNames ) {
        colMap = new HashMap();
        this.columnNames = columnNames ; // case sensitive copy
        for (int i = 0; i < columnNames.length; i++) {
            colMap.put( columnNames[i].toUpperCase(), new Integer(i));
        }
    }

    /**
     * Get cell with property (column name)
     * @param line
     * @param columnName Column name
     * @return corresponding value from the line for the column name
     */
    private String getPropertyCell( String line, String columnName ) {
        String cell = null;
        Integer idx = (Integer) this.colMap.get( columnName.toUpperCase() );
        if (idx != null) {
            int columnPosition = idx.intValue();
            String[] lineFields = tokenizeLine ( line ) ;
            cell = lineFields[ columnPosition ];
        }
        return cell;
    }

    /**
     * Get a specific field value from a line at the specified position.
     * @param line
     * @param idxPosition
     * @return String corresponding to position specified, or null if there
     * is not value at the index specified (i.e. out of bounds).
     */
    private String getPropertyCell ( String line, int idxPosition ) {
        String cell = null ;

        if ( idxPosition > -1 && idxPosition < this.colMap.size() ) {
            // make sure we don't go out of bounds
            String[] lineFields = tokenizeLine ( line ) ;
            if ( idxPosition < lineFields.length ) {
                cell = lineFields [ idxPosition ] ;
            }
        }

        return cell ;
    }
}