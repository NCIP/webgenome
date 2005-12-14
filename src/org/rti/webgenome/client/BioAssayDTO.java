package org.rti.webgenome.client;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Aug 25, 2005
 * Time: 5:54:09 AM
 */
public interface BioAssayDTO extends Serializable {
    public String getID();
    public String getName();
    public BioAssayDatumDTO[] getBioAssayData();
}
