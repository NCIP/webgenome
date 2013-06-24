/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source$
$Revision$
$Date$

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

import java.io.BufferedReader;
import java.io.IOException;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.deprecated.array.ArrayDatum;
import org.rti.webcgh.deprecated.array.ArrayDatumFactory;

/**
 * This class should normally be used only for unit testing.
 * The class generates array datum values.
 *
 */
public final class ArrayDatumGenerator {

    /**
     * Name of file containing list of array datum values.  Data
     * values are read in from a file instead of being randomly generated
     * so that generated data are more realistic.  This file should be
     * in the same package this class.  The file itself should 
     * be text format and contain a sequence of floating point values,
     * each on a separate line.
     */
    private static final String DATA_FILE_NAME = 
        "org/rti/webcgh/util/bioassay_data.txt";
    
    /** Reader for file containing data values. */
    private BufferedReader reader = null;
    
    
    /**
     * Array datum factory for generating new <code>ArrayDatum</code> objects.
     */
    private final ArrayDatumFactory arrayDatumFactory = new ArrayDatumFactory();

    
    /**
     * Constructor.
     */
    public ArrayDatumGenerator() {
        
    }
    
    
    /**
     * Constructor.
     * @param seed Seed value, like for random
     * number generator.  Seed should not be too
     * large, however.
     */
    public ArrayDatumGenerator(final long seed) {
        this();
        for (long i = 0; i < seed; i++) {
            this.nextLine();
        }
    }
    
    
    /**
     * Generate new array datum.
     * @param name Name of reporter
     * @param chromosome Chromosome number
     * @param location Chromosome location in base pair units
     * @return A new array datum
     */
    public ArrayDatum newArrayDatum(final String name, final 
            short chromosome, final long location) {
        float value = Float.parseFloat(this.nextLine());
        ArrayDatum datum = this.arrayDatumFactory.newArrayDatum(name, 
                chromosome, location, value);
        return datum;
    }
    
    
    /**
     * Return next line in data file.
     * @return Next line in data file
     */
    private String nextLine() {
        String line = null;
        if (this.reader == null) {
            this.reader = IOUtils.getReader(DATA_FILE_NAME);
        }
        try {
            line = this.reader.readLine();
            if (line == null) {
                this.reader = IOUtils.getReader(DATA_FILE_NAME);
                line = this.reader.readLine();
            }
        } catch (IOException e) {
            throw new WebcghSystemException(
                    "Error reading from array data file");
        }
        if (line == null) {
            throw new WebcghSystemException("Array data file is empty");
        }
        return line;
    }
}
