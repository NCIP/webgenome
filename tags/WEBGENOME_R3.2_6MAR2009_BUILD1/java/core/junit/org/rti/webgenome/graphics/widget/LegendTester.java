/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.DataContainingBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.graphics.widget.Legend;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>Legend</code>.
 * @author dhall
 *
 */
public final class LegendTester extends TestCase {
    
    
    /**
     * Name of directory holding graphic files produced
     * during tests.  The absolute path will be a
     * concatenation of the 'test.dir' property in
     * the file 'unit_test.properties' and this
     * constant.
     */
    private static final String TEST_DIR_NAME = "legend-tester";
    
    
    /** Width of legend in pixels. */
    private static final int WIDTH = 400;

    
    /**
     * Test paint() method.
     *
     */
    public void testPaint() {
        RasterFileTestPlotPanel panel =
            new RasterFileTestPlotPanel(
            		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME));
        Collection<Experiment> experiments = new ArrayList<Experiment>();
        Experiment exp1 = new Experiment("Experiment 1");
        experiments.add(exp1);
        Organism org = new Organism("", "");
        BioAssay b1 = new DataContainingBioAssay("Bioassay 1", org);
        BioAssay b2 = new DataContainingBioAssay("Bioassay 2", org);
        BioAssay b3 = new DataContainingBioAssay("Bioassay 3", org);
        BioAssay b4 = new DataContainingBioAssay("Bioassay 4", org);
        BioAssay b5 = new DataContainingBioAssay("Bioassay 5", org);
        b1.setColor(Color.BLACK);
        b2.setColor(Color.BLUE);
        b3.setColor(Color.RED);
        b4.setColor(Color.GREEN);
        b5.setColor(Color.YELLOW);
        exp1.add(b1);
        exp1.add(b2);
        exp1.add(b3);
        exp1.add(b4);
        exp1.add(b5);
        Experiment exp2 = new Experiment("Experiment 2");
        BioAssay b6 = new DataContainingBioAssay("Bioassay 6", org);
        b6.setColor(Color.GRAY);
        exp2.add(b6);
        experiments.add(exp2);
        Legend legend = new Legend(experiments, WIDTH);
        panel.add(legend);
        panel.toPngFile("legend.png");
    }
}
