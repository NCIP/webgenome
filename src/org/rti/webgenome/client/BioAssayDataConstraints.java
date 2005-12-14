package org.rti.webgenome.client;

import java.io.Serializable;


/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 15, 2005
 * Time: 3:49:36 AM
 */
public class BioAssayDataConstraints implements Serializable {
    private Long startPosition;
    private Long endPosition;
    private String chromosome;
    private String quantitationType;

    public Long getStartPosition() {
        return startPosition;
    }

    public Long getEndPosition() {
        return endPosition;
    }

    public String getChromosome() {
        return chromosome;
    }

    public String getQuantitationType() {
        return quantitationType;
    }

    public void setPositions(Long startPos, Long endPos) {
       this.startPosition = startPos;
       this.endPosition = endPos;
    }

    public void setChromosome(String chromsomeNumber) {
        this.chromosome = chromsomeNumber;
    }

    public void setQuantitationType(String quantitaionType) {
       this.quantitationType = quantitaionType;
    }
}
