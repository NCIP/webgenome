package org.rti.webgenome.client;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Aug 25, 2005
 * Time: 5:50:12 AM
 */
public interface ReporterDTO extends Serializable {
    public String getName();
    public String getChromosome();
    public Long getChromosomeLocation();
    public String[] getAssociatedGenes();
    public String[] getAnnotations();
    public Boolean isSelected();
}
