/*

$Source$
$Revision$
$Date$

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

package org.rti.webcgh.plot.unit_test;

import java.util.ArrayList;
import java.util.Collection;

import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.plot.Legend;
import org.rti.webcgh.util.FileUtils;
import org.rti.webcgh.util.SystemUtils;

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
            new RasterFileTestPlotPanel(this.getPathToTestDir());
        Collection<Experiment> experiments = new ArrayList<Experiment>();
        Experiment exp1 = new Experiment("Experiment 1");
        experiments.add(exp1);
        Legend legend = new Legend(experiments, WIDTH);
        panel.add(legend);
        panel.toPngFile("legend.png");
    }
    
    
    /**
     * Get absolute path to directory that contains
     * test output files.  If necessary, method creates
     * directory.
     * @return Absolute path to directory that contains
     * test output files
     */
    private String getPathToTestDir() {
        String masterTestDirPath =
            SystemUtils.getUnitTestProperty("temp.dir");
        String dirPath = masterTestDirPath + "/"
            + LegendTester.TEST_DIR_NAME;
        FileUtils.createDirectory(dirPath);
        return dirPath;
    }
}
