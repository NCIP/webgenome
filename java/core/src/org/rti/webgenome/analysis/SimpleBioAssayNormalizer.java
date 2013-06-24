/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

/**
 * Performs "simple" normalization, subtracting
 * either mean or median value of bioassay
 * from all values to bring to mean or median,
 * respectively, to 0.  Intended to be used
 * to normalize all data from a single bioassay.
 * @author dhall
 *
 */
public final class SimpleBioAssayNormalizer extends SimpleNormalizer
implements IntraBioAssayStatefulOperation {
    
    /**
     * Get name of operation.
     * @return Name of operation
     */
    public String getName() {
        return "Simple bioassay-based normalization";
    }
}
