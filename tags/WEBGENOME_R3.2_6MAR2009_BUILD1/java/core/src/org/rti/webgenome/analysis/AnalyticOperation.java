/*
$Revision: 1.2 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.analysis;

import java.util.Collection;
import java.util.List;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.QuantitationType;


/**
 * Represents an analytic operation.
 * @author dhall
 *
 */
public interface AnalyticOperation {

    /**
     * Get name of operation.
     * @return Name of operation
     */
    String getName();
    
    
    /**
     * Get user configurable properties.
     * @param qTypes Quantitation types
     * @return User configurable properties
     */
    List<UserConfigurableProperty> getUserConfigurableProperties(
    		Collection<QuantitationType> qTypes);
    
    
    /**
     * Set some property of the operation.  The name of this
     * property should correspond to one of user configurable
     * property names.
     * @param name Name of property to set.
     * @param value Value of property.
     * @throws BadUserConfigurablePropertyException if value is invalid.
     */
    void setProperty(String name, String value)
    throws BadUserConfigurablePropertyException;
    
    /**
     * Determine the number of bioassays that would result
     * from a proper running of the given experiments through
     * this operation.
     * @param experiments Some experiments
     * @return The number of bioassays that would result
     * from a proper running of the given experiments through
     * this operation.
     */
    int numResultingBioAssays(Collection<Experiment> experiments);
}
