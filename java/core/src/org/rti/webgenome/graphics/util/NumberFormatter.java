/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/graphics/util/NumberFormatter.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $



*/
package org.rti.webgenome.graphics.util;

/**
 * Formats numbers for display
 */
public interface NumberFormatter {
    
    
    /**
     * Format given number
     * @param number A number
     * @return Formatted number
     */
    public String format(double number);
    
    
    /**
     * Format given number
     * @param number A number
     * @return Formatted number
     */
    public String format(long number);

}
