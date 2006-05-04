/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/io/unit_test/DelimitedLineParserTester.java,v $
$Revision: 1.1 $
$Date: 2006-05-04 18:33:02 $

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
import org.rti.webcgh.io.DelimitedLineParser;
import java.util.StringTokenizer;

/**
 * Unit Test for the Delimited Line Parser.
 *
 * @see org.rti.webcgh.io.DelimitedLineParser
 */
public class DelimitedLineParserTester extends TestCase {

    private static final String testData =
        "Name,Chromosome,Position,BioAssay1,BioAssay2\n" +
        "RP11-82D16,1,2,0.106308,0.106318\n" +
        "RP11-62M23,1,3,-0.010037,-0.010027\n" +
        "RP11-111O5,1,4,-0.010037,-0.010027\n" +
        "RP11-51B4,1,5,0.053391,0.053401\n" +
        "RP11-60J11,1,6,0.055755,0.055765\n" +
        "RP11-813J5,1,7,-0.008973,-0.008963\n" +
        "RP11-199O1,1,8,0.056527,0.056537\n" +
        "RP11-188F7,1,9,-0.087739,-0.087729\n" +
        "RP11-178M15,1,10,-0.058804,-0.058794" ;

    /**
     * Test the Delimited Line Parser for comma-separated values.
     */
    public void testDelimitedLineParserForCSV ( ) {
        System.out.println ( this.getClass().getName() + " entered" ) ;
        StringTokenizer st = new StringTokenizer ( testData, "\n" ) ;

        String line = st.nextToken() ; // heading line
        DelimitedLineParser dlp = new DelimitedLineParser ( line ) ;

        this.setName( "Delimited Line Parser Test for comma-separated value lines:" );

        // hasColumn() tests using a mixture of cases
        System.out.println ( "Testing hasColumn()" ) ;
        assertTrue ( dlp.hasColumn ( "pOSITION" ) ) ;
        assertTrue ( dlp.hasColumn ( "name" ) ) ;
        assertTrue ( dlp.hasColumn ( "CHROMOSOME" )) ;
        assertTrue ( dlp.hasColumn ( "BioAssay1" )) ;
        assertTrue ( dlp.hasColumn ( "Bioassay2" )) ;
        assertFalse ( dlp.hasColumn ( "noSuchColumn" )) ;
        assertFalse ( dlp.hasColumn ( "Name,Chromosome,Position,BioAssay1,BioAssay")) ;

        // getProperty() tests
        System.out.println ( "Testing getProperty()" ) ;
        line = st.nextToken() ; // 2nd line
        assertEquals ( dlp.getProperty ( line, "NAME"), "RP11-82D16" ) ;
        assertEquals ( dlp.getProperty ( line, 0 ), "RP11-82D16" ) ;
        assertEquals ( dlp.getProperty ( line, 4 ), "0.106318" ) ;
        assertEquals ( dlp.getProperty ( line, 2 ), "2" ) ;
        assertEquals ( dlp.getProperty ( line, "bioassay1" ), "0.106308" ) ;

        // getNumericProperty() tests
        System.out.println ( "Testing getNumericProperty()" ) ;
        line = st.nextToken() ;
        line = st.nextToken() ; // 4th line - RP11-111O5,1,4,-0.010037,-0.010027
        assertEquals ( dlp.getNumericProperty ( line, "bioaSSAY2" ), -0.010027 ) ;
        assertEquals ( dlp.getNumericProperty ( line, "cHromoSOME" ), 1.0 ) ;
        assertEquals ( dlp.getNumericProperty ( line, 3 ), -0.010037 ) ;
        double doubleVal = dlp.getNumericProperty ( line, 1 ) ;
        assertFalse  ( doubleVal == Double.NaN ) ;

        // getProperties() tests
        System.out.println ( "Testing getProperties()" ) ;
        line = st.nextToken() ; // 5th line - RP11-51B4,1,5,0.053391,0.053401
        String[] values = dlp.getProperties( line, 2 ) ;
        assertEquals ( values.length, 3 ) ;
        assertEquals ( values[0], "5" ) ;
        assertEquals ( values[1], "0.053391" ) ;
        assertEquals ( values[2], "0.053401" ) ;
        line = st.nextToken() ; // 6th line - RP11-60J11,1,6,0.055755,0.055765
        values = dlp.getProperties ( line, 10 ) ;
        assertEquals ( values.length, 0 ) ;

        // getColumnName() tests
        System.out.println ( "Testing getColumnName()" ) ;

        assertEquals ( dlp.getColumnName ( 0 ), "Name" ) ;
        assertEquals ( dlp.getColumnName ( 3 ), "BioAssay1" ) ;
        assertEquals ( dlp.getColumnName ( 4 ), "BioAssay2" ) ;
    }
}
