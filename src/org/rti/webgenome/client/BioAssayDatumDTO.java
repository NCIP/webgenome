package org.rti.webgenome.client;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Aug 25, 2005
 * Time: 5:52:58 AM
 */
public interface BioAssayDatumDTO extends Serializable {
    public Double getValue();
    public String getQuantitationType();
    public ReporterDTO getReporter();
}
