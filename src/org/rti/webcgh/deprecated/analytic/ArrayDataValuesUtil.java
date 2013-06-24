/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/analytic/ArrayDataValuesUtil.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:32:37 $

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
package org.rti.webcgh.deprecated.analytic;

import org.apache.commons.collections.primitives.ArrayDoubleList;
import org.apache.commons.collections.primitives.DoubleIterator;
import org.apache.commons.collections.primitives.DoubleList;
import org.rti.webcgh.core.WebcghApplicationException;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.deprecated.array.ArrayDatum;
import org.rti.webcgh.deprecated.array.ArrayDatumIterator;
import org.rti.webcgh.deprecated.array.BioAssay;


/**
 * Utility for getting and setting array data values en masse
 */
public class ArrayDataValuesUtil {
    
    /**
     * Extract values
     * @param gad Genome array data
     * @return Extracted values
     */
    public static DoubleList extractValues(BioAssay gad) {
        DoubleList list = new ArrayDoubleList();
        for (ArrayDatumIterator it = gad.arrayDatumIterator(); it.hasNext();) {
            ArrayDatum datum = it.next();
            list.add(datum.magnitude());
        }
        return list;
    }
    
    
    /**
     * Bulk set of values
     * @param newBioAssay Genome array data for which to set values
     * @param templateBioAssay Template bioassay
     * @param values Values
     */
    public static void setValues(BioAssay newBioAssay, BioAssay templateBioAssay,
            DoubleList values) {
        ArrayDatumIterator adIt = templateBioAssay.arrayDatumIterator();
        DoubleIterator dIt = values.iterator();
        while (adIt.hasNext() && dIt.hasNext()) {
            ArrayDatum datum = adIt.next();
            ArrayDatum newDatum = new ArrayDatum(datum);
            double value = dIt.next();
            newDatum.setMagnitude((float)value);
            
            // Wrap checked exception as unchecked,
			// as it would not be expected to occur
            try {
				newBioAssay.add(newDatum);
			} catch (WebcghApplicationException e) {
				throw new WebcghSystemException(e);
			}
        }
    }
}
