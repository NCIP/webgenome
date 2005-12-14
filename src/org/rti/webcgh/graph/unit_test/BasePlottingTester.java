/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/unit_test/BasePlottingTester.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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
package org.rti.webcgh.graph.unit_test;

import org.apache.log4j.Logger;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayData;
import org.rti.webcgh.array.Chromosome;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.GenomeInterval;
import org.rti.webcgh.array.GenomeLocation;
import org.rti.webcgh.array.Quantitation;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.array.Reporter;
import org.rti.webcgh.array.ReporterMapping;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.SvgDrawingCanvas;
import org.w3c.dom.Document;

import junit.framework.TestCase;

/**
 * Base class for testing plotters
 */
public class BasePlottingTester extends TestCase {
    
    private static final Logger LOGGER = Logger.getLogger(BasePlottingTester.class);
    
    protected Experiment experiment = null;
    protected BioAssayData bioAssayData = null;
    protected Document document = null;
    protected DrawingCanvas drawingCanvas = null;
    protected Chromosome chrom1 = new Chromosome(null, (short)1, (long)300000000);
    protected Chromosome chrom2 = new Chromosome(null, (short)2, (long)250000000);
    protected Chromosome chrom3 = new Chromosome(null, (short)3, (long)200000000);
    protected GenomeInterval genomeInterval1 = null;
    protected GenomeInterval genomeInterval2 = null;

    
    // ============================
    //    Attributes
    // ============================
    
    /**
     */
    public void setUp() {
        this.document = PlotTesterUtils.newTestDocument();
        this.drawingCanvas = new SvgDrawingCanvas(document);
        this.bioAssayData = new BioAssayData();
        QuantitationType qType = new QuantitationType("RAW");
        
        // Chromosome 1
        Reporter r1 = new Reporter("1");
        Reporter r2 = new Reporter("2");
        Reporter r3 = new Reporter("3");
        Reporter r4 = new Reporter("4");
        Reporter r5 = new Reporter("5");
        Reporter r6 = new Reporter("6");
        r1.setReporterMapping(new ReporterMapping(r1, new GenomeLocation(chrom1, 50000000)));
        r2.setReporterMapping(new ReporterMapping(r2, new GenomeLocation(chrom1, 75000000)));
        r3.setReporterMapping(new ReporterMapping(r3, new GenomeLocation(chrom1, 80000000)));
        r4.setReporterMapping(new ReporterMapping(r4, new GenomeLocation(chrom1, 150000000)));
        r5.setReporterMapping(new ReporterMapping(r5, new GenomeLocation(chrom1, 175000000)));
        r6.setReporterMapping(new ReporterMapping(r6, new GenomeLocation(chrom1, 225000000)));
        this.bioAssayData.add(new ArrayDatum(r1, new Quantitation((float)0.5, (float)0.5, qType)));
        this.bioAssayData.add(new ArrayDatum(r2, new Quantitation((float)-0.5, (float)0.1, qType)));
        this.bioAssayData.add(new ArrayDatum(r3, new Quantitation((float)0.3, (float)0.2, qType)));
        this.bioAssayData.add(new ArrayDatum(r4, new Quantitation((float)0.25, (float)0.01, qType)));
        this.bioAssayData.add(new ArrayDatum(r5, new Quantitation((float)-0.05, (float)0.1, qType)));
        this.bioAssayData.add(new ArrayDatum(r6, new Quantitation((float)0.95, (float)0.2, qType)));
        
        // Chromosome 2
        Reporter r7 = new Reporter("7");
        Reporter r8 = new Reporter("8");
        Reporter r9 = new Reporter("9");
        Reporter r10 = new Reporter("10");
        Reporter r11 = new Reporter("11");
        r7.setReporterMapping(new ReporterMapping(r7, new GenomeLocation(chrom2, 20000000)));
        r8.setReporterMapping(new ReporterMapping(r8, new GenomeLocation(chrom2, 35500000)));
        r9.setReporterMapping(new ReporterMapping(r9, new GenomeLocation(chrom2, 100500000)));
        r10.setReporterMapping(new ReporterMapping(r10, new GenomeLocation(chrom2, 150000000)));
        r11.setReporterMapping(new ReporterMapping(r11, new GenomeLocation(chrom2, 175000000)));
        this.bioAssayData.add(new ArrayDatum(r7, new Quantitation((float)0.01, (float)0.2, qType)));
        this.bioAssayData.add(new ArrayDatum(r8, new Quantitation((float)0.20, (float)0.4, qType)));
        this.bioAssayData.add(new ArrayDatum(r9, new Quantitation((float)1.1, (float)0.01, qType)));
        this.bioAssayData.add(new ArrayDatum(r10, new Quantitation((float)0.95, (float)0.3, qType)));
        this.bioAssayData.add(new ArrayDatum(r11, new Quantitation((float)-0.1, (float)0.7, qType)));
        
        // Chromosome 3
        Reporter r12 = new Reporter("12");
        Reporter r13 = new Reporter("13");
        Reporter r14 = new Reporter("14");
        Reporter r15 = new Reporter("15");
        r12.setReporterMapping(new ReporterMapping(r12, new GenomeLocation(chrom3, 10000000)));
        r13.setReporterMapping(new ReporterMapping(r13, new GenomeLocation(chrom3, 80000000)));
        r14.setReporterMapping(new ReporterMapping(r14, new GenomeLocation(chrom3, 120000000)));
        r15.setReporterMapping(new ReporterMapping(r15, new GenomeLocation(chrom3, 160000000)));
        this.bioAssayData.add(new ArrayDatum(r12, new Quantitation((float)-0.3, (float)0.3, qType)));
        this.bioAssayData.add(new ArrayDatum(r13, new Quantitation((float)-0.2, (float)0.4, qType)));
        this.bioAssayData.add(new ArrayDatum(r14, new Quantitation((float)-0.8, (float)0.1, qType)));
        this.bioAssayData.add(new ArrayDatum(r15, new Quantitation((float)1.0, (float)0.3, qType)));
        
        // Experiments
        BioAssay bioAssay = new BioAssay("bioAssay1", "A bioassay", "1", "db");
        bioAssay.setBioAssayData(this.bioAssayData);
        this.experiment = new Experiment("experiment1", "An experiment", 
        		"DB", false);
        this.experiment.add(bioAssay);
        
        // Genome intervals
        this.genomeInterval1 = new GenomeInterval(new GenomeLocation(chrom1, 0), 
        		new GenomeLocation(chrom1, 300000000));
        this.genomeInterval2 = new GenomeInterval(new GenomeLocation(chrom2, 0), 
        		new GenomeLocation(chrom2, 200000000));
    }

}
