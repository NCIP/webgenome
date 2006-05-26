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
package org.rti.webcgh.graph.widget.unit_test;

import java.awt.Color;
import java.awt.Point;

import junit.framework.TestCase;

import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.VerticalAlignment;
import org.rti.webcgh.graph.unit_test.SvgTestPanel;
import org.rti.webcgh.graph.widget.Background;
import org.rti.webcgh.graph.widget.PlotPanel;


/**
 * 
 */
public class PlotPanelTester extends TestCase {
    
    
    private SvgTestPanel panel = null;
    
    
    /**
     * 
     */
    public void setUp() {
        Background bigBg = new Background(200, 200, Color.red);
        this.panel = SvgTestPanel.newSvgTestPanel();
        this.panel.setOrigin(new Point(10, 10));
        panel.add(bigBg, HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
    }
    

    
    /**
     * 
     *
     */
    public void testLeftAbove() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.ABOVE);
        this.panel.toSvgFile("panel-left-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeftTopJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.TOP_JUSTIFIED);
        this.panel.toSvgFile("panel-left-top.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeftCenter() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
        this.panel.toSvgFile("panel-left-middle.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeftBottomJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.BOTTOM_JUSTIFIED);
        this.panel.toSvgFile("panel-left-bottom.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeftBelow() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.BELOW);
        this.panel.toSvgFile("panel-left-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testAboveLeftJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.ABOVE);
        this.panel.toSvgFile("panel-left-justified-above.svg");
    }
    
    /**
     * 
     *
     */
    public void testTopJustifiedLeftJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        this.panel.toSvgFile("panel-left-justified-top-justified.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testAboveCenter() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
        this.panel.toSvgFile("panel-middle-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testAboveRightJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.ABOVE);
        this.panel.toSvgFile("panel-right-justified-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testBelowLeftJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
        this.panel.toSvgFile("panel-left-justified-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testBelowCenter() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
        this.panel.toSvgFile("panel-middle-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testBelowRightJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.BELOW);
        this.panel.toSvgFile("panel-right-justified-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightAbove() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.ABOVE);
        this.panel.toSvgFile("panel-right-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightTopJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
        this.panel.toSvgFile("panel-right-top.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightCenter() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
        this.panel.toSvgFile("panel-right-center.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightBottomJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.BOTTOM_JUSTIFIED);
        this.panel.toSvgFile("panel-right-bottom.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightBelow() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.BELOW);
        this.panel.toSvgFile("panel-right-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testInside() {
        
        // Left
        this.panel.add(new Background(30, 30, Color.blue), 
              HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        this.panel.add(new Background(30, 30, Color.blue), 
                HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.CENTERED);
        this.panel.add(new Background(30, 30, Color.blue), 
                HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
        
        // Center
        this.panel.add(new Background(30, 30, Color.blue), 
                HorizontalAlignment.CENTERED, VerticalAlignment.TOP_JUSTIFIED);
	      this.panel.add(new Background(30, 30, Color.blue), 
	              HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
	      this.panel.add(new Background(30, 30, Color.blue), 
	              HorizontalAlignment.CENTERED, VerticalAlignment.BOTTOM_JUSTIFIED);
          
        // Right
	      this.panel.add(new Background(30, 30, Color.blue), 
	              HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
	        this.panel.add(new Background(30, 30, Color.blue), 
	                HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.CENTERED);
	        this.panel.add(new Background(30, 30, Color.blue), 
	                HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
        
        this.panel.toSvgFile("panel-inside.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testAddPanel() {
	    PlotPanel panel2 = this.panel.newChildPlotPanel();
	    panel2.add(new Background(100, 100, Color.blue), 
	        HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
	    this.panel.add(panel2, HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
        this.panel.toSvgFile("panel-add-panel.svg");
    }
    
    public void test3PanelsLeftOfLeftJustified() {
    	this.panel.add(new Background(100, 100, Color.BLUE), 
    			HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
    	this.panel.add(new Background(50, 50, Color.GREEN), 
    			HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.CENTERED);
    	this.panel.toSvgFile("panel-leftof-leftjustified.svg");
    }
    
    public void test3PanelsLeftOfLeftOf() {
    	this.panel.add(new Background(100, 100, Color.BLUE), 
    			HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
    	this.panel.add(new Background(50, 50, Color.GREEN), 
    			HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
    	this.panel.toSvgFile("panel-leftof-leftof.svg");
    }
    
    public void test3PanelsRightOfRightJustified() {
    	this.panel.add(new Background(100, 100, Color.BLUE), 
    			HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
    	this.panel.add(new Background(50, 50, Color.GREEN), 
    			HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.CENTERED);
    	this.panel.toSvgFile("panel-righttof-rightjustified.svg");
    }
    
    public void test3PanelsRightOfRightOf() {
    	this.panel.add(new Background(100, 100, Color.BLUE), 
    			HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
    	this.panel.add(new Background(50, 50, Color.GREEN), 
    			HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
    	this.panel.toSvgFile("panel-righttof-rightof.svg");
    }
    
    public void test3PanelsAboveTopJustified() {
    	this.panel.add(new Background(100, 100, Color.BLUE), 
    			HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
    	this.panel.add(new Background(50, 50, Color.GREEN), 
    			HorizontalAlignment.CENTERED, VerticalAlignment.TOP_JUSTIFIED);
    	this.panel.toSvgFile("panel-above-topjustified.svg");
    }
    
    public void test3PanelsAboveAbove() {
    	this.panel.add(new Background(100, 100, Color.BLUE), 
    			HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
    	this.panel.add(new Background(50, 50, Color.GREEN), 
    			HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
    	this.panel.toSvgFile("panel-above-above.svg");
    }
    
    public void test3PanelsBelowBottomJustified() {
    	this.panel.add(new Background(100, 100, Color.BLUE), 
    			HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
    	this.panel.add(new Background(50, 50, Color.GREEN), 
    			HorizontalAlignment.CENTERED, VerticalAlignment.BOTTOM_JUSTIFIED);
    	this.panel.toSvgFile("panel-below-bottomjustified.svg");
    }
    
    public void test3PanelsBelowBelow() {
    	this.panel.add(new Background(100, 100, Color.BLUE), 
    			HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
    	this.panel.add(new Background(50, 50, Color.GREEN), 
    			HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
    	this.panel.toSvgFile("panel-below-below.svg");
    }
}
