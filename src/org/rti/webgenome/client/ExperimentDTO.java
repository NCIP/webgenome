package org.rti.webgenome.client;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 13, 2005
 * Time: 7:44:00 AM
 */
public interface ExperimentDTO extends Serializable {
    String getExperimentID();
    BioAssayDTO[] getBioAssays();
}
