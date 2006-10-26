/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/PlotInteractivityTag.java,v $
$Revision: 1.1 $
$Date: 2006-10-26 21:33:15 $

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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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
package org.rti.webcgh.webui.taglib;


import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.rti.webcgh.domain.Plot;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.util.SystemUtils;
import org.rti.webcgh.webui.util.Attribute;
import org.rti.webcgh.webui.util.ClickBoxes;
import org.rti.webcgh.webui.util.MouseOverStripe;
import org.rti.webcgh.webui.util.MouseOverStripes;

/**
 * Print plot interactivity JavaScript elements.
 * Grabs Shopping Cart from session.
 */
public class PlotInteractivityTag extends TagSupport {
    
	private String plotAttributeName = "";
	private static final Logger LOGGER = Logger.getLogger(PlotInteractivityTag.class);
	
	
	/**
	 * @return Returns the plotAttributeName.
	 */
	public String getPlotAttributeName() {
		return plotAttributeName;
	}


	/**
	 * @param plotAttributeName The plotAttributeName to set.
	 */
	public void setPlotAttributeName(final String plotAttributeName) {
		this.plotAttributeName = plotAttributeName;
	}


	/**
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		
		// Get attached exception
		//Exception ex = (Exception)pageContext.findAttribute(Attribute.EXCEPTION);

		// Get plot and associated interactivity attributes
		Plot plot = (Plot)pageContext.findAttribute(plotAttributeName);	// plot

		Collection<ClickBoxes> clickBoxesCol = plot.getClickBoxes();	// get collection of click boxes
		Collection<MouseOverStripes> mouseOverStripesCol = plot.getMouseOverStripes();	// get collection of mouse over stripes

		String defaultImage = plot.getDefaultImageFileName();	// get default plot image
		Map<String,String> imageFileMap = plot.getImageFileMap();	// get image file map

		int plotWidth = plot.getWidth();	// plot width
		int plotHeight = plot.getHeight();	// plot height

		// string prefixed + suffixed to each mouseoverstripe tooltip
		String mouseOverStripesTooltipPrefix = SystemUtils.getApplicationProperty("mouseoverstripes.tooltip.prefix");
		String mouseOverStripesTooltipSuffix = SystemUtils.getApplicationProperty("mouseoverstripes.tooltip.suffix");

		// Show plot interactivity only if plot is of valid size and both click boxes + mouse overstripes are present
		if ((clickBoxesCol != null) && (mouseOverStripesCol != null) && (plotWidth > 0) && (plotHeight > 0)) {

			// Get context path and image sub context path
			String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
			String imageSubContextPath = SystemUtils.getApplicationProperty("image.sub.context");

			// Print to screen
			PrintWriter out = new PrintWriter(pageContext.getOut());
			out.println("<script language=\"javascript\">");

			out.println("var pdi='" + defaultImage + "';");	// default image

			out.println("var cbxoA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes origin x array
			out.println("var cbyoA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes origin y array
			out.println("var cbwA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes width array
			out.println("var cbhA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes height array
			out.println("var cbbwA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes box width array
			out.println("var cbbhA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes box height array
			out.println("var cbA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes array

			out.println("var scxoA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes origin x array
			out.println("var scyoA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes origin y array
			out.println("var scohvA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes orientation array
			out.println("var scwA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes width array
			out.println("var schA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes height array
			out.println("var scA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes array
			out.println("var sliA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes last index array 
			out.println("var sfA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes range from coordinate array
			out.println("var stA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes range to coordinate array
			out.println("var svA=new Array(" + mouseOverStripesCol.size() + ");");	// mouseoverstripes range value array

			out.println("var cbxo;");	// individual clickboxes origin x
			out.println("var cbyo;");	// individual clickboxes origin y
			out.println("var cbw;");	// individual clickboxes width
			out.println("var cbh;");	// individual clickboxes height
			out.println("var cbbw;");	// individual clickboxes width
			out.println("var cbbh;");	// individual clickboxes height
			out.println("var cb;");	// individual clickboxes

			out.println("var scxo;");	// individual mouseoverstripes origin x
			out.println("var scyo;");	// individual mouseoverstripes origin y
			out.println("var scohv;");	// individual mouseoverstripes orientation
			out.println("var scw;");	// individual mouseoverstripes width
			out.println("var sch;");	// individual mouseoverstripes height
			out.println("var sc;");	// individual mouseoverstripes
			out.println("var sli;");	// individual mouseoverstripes last index
			out.println("var slc=0;");	// last mouseoverstripe index
			out.println("var sf;");	// individual mouseoverstripes range from coordinate
			out.println("var st;");	// individual mouseoverstripes range to coordinate
			out.println("var sv;");	// individual mouseoverstripes range value


			// init generic counter used for javascript output
			int count;

			// Iterate through click boxes and output javascript
			count = 0;	// reset counter
			for(ClickBoxes clickBoxes : clickBoxesCol) {

				// ClickBoxes JavaScript variables
				out.println("cbxo=" + clickBoxes.getOrigin().x + ";");
				out.println("cbyo=" + clickBoxes.getOrigin().y + ";");
				out.println("cbw=" + clickBoxes.getWidth() + ";");
				out.println("cbh=" + clickBoxes.getHeight() + ";");
				out.println("cbbw=" + clickBoxes.getBoxWidth() + ";");
				out.println("cbbh=" + clickBoxes.getBoxHeight() + ";");
				out.println("cb=new Array(" + clickBoxes.getClickBox().length + ");");
	
				// loop through click boxes and output JavaScript variables
				for(int i = 0 ; i < clickBoxes.getClickBox().length ; i++) {
	
					// init each new row as new array
					out.println("cb[" + i + "]=new Array(" + clickBoxes.getClickBox()[i].length + ");");
	
					// loop through each row
					for(int j = 0 ; j < clickBoxes.getClickBox()[i].length ; j++) {
	
						// grab image name
						String tempImageName = imageFileMap.get(clickBoxes.getClickBox()[i][j]);
	
						// output blank if null, image name if not null
						out.println("cb[" + i + "][" + j + "]='" + ((tempImageName == null) ? "" : tempImageName) + "';");
	
					}
				}

				// store values into master array and increase related counter
				out.println("cbxoA[" + count + "]=cbxo;");
				out.println("cbyoA[" + count + "]=cbyo;");
				out.println("cbwA[" + count + "]=cbw;");
				out.println("cbhA[" + count + "]=cbh;");
				out.println("cbbwA[" + count + "]=cbbw;");
				out.println("cbbhA[" + count + "]=cbbh;");
				out.println("cbA[" + count + "]=cb;");
				count++;
			}

			// Iterate through mouse over stripes and output javascript
			count = 0;	// reset counter
			for(MouseOverStripes mouseOverStripes : mouseOverStripesCol) {

				Collection<MouseOverStripe> mouseOverStripeCol = mouseOverStripes.getStripes();

				// MouseOver Stripe JavaScript variables
				out.println("scxo=" + mouseOverStripes.getOrigin().x + ";");
				out.println("scyo=" + mouseOverStripes.getOrigin().y + ";");
				String mosOrientation = "h";
				if(mouseOverStripes.getOrientation() == Orientation.VERTICAL) mosOrientation = "v";
				out.println("scohv='" + mosOrientation + "';");
				out.println("scw=" + mouseOverStripes.getWidth() + ";");
				out.println("sch=" + mouseOverStripes.getHeight() + ";");
				out.println("sc=" + mouseOverStripeCol.size() + ";");
				out.println("sli=-1;");
				out.println("sf=new Array(sc);");
				out.println("st=new Array(sc);");
				out.println("sv=new Array(sc);");
	
				// output list of MouseOverStripe JavaScript variables
				int mosCount = 0;
				for(MouseOverStripe mos : mouseOverStripeCol) {
					out.println("sf[" + mosCount + "]=" + mos.getStart() + ";\tst[" + mosCount +
							"]=" + mos.getEnd() + ";\tsv[" + mosCount + "]='" + mos.getText() + "';");
					mosCount++;
				}

				// store values into master array and increase related counter
				out.println("scxoA[" + count + "]=scxo;");
				out.println("scyoA[" + count + "]=scyo;");
				out.println("scohvA[" + count + "]=scohv;");
				out.println("scwA[" + count + "]=scw;");
				out.println("schA[" + count + "]=sch;");
				out.println("scA[" + count + "]=sc;");
				out.println("sliA[" + count + "]=sli;");
				out.println("sfA[" + count + "]=sf;");
				out.println("stA[" + count + "]=st;");
				out.println("svA[" + count + "]=sv;");
				count++;
			}

			// ClickBoxes JavaScript functions - capture click
			out.println("function captureClick(e) {");
			out.println(	"var x = (e.x == undefined) ? e.layerX : e.x;");
			out.println(	"var y = (e.y == undefined) ? e.layerY : e.y;");
			out.println(	"var ct = -1;");
			out.println(	"for(var i=0 ; i<" + clickBoxesCol.size() + " ; i++) {");
			out.println(		"if( (x >= cbxoA[i]) && (x <= (cbxoA[i] + cbwA[i])) && (y >= cbyoA[i]) && (y <= (cbyoA[i] + cbhA[i])) ) {");
			out.println(			"ct = i;");
			out.println(			"break;");
			out.println(		"}");
			out.println(	"}");
			out.println(	"if(ct == -1) {");
			out.println(		"document.getElementById('plotGraph').style.background = \"url(" +
					contextPath + imageSubContextPath + "/" + "\" + pdi + \")\";");
//			out.println(		"document.statusClick.image.value = \"url(" +
//					contextPath + imageSubContextPath + "/" + "\" + pdi + \")\";");
			out.println(		"return;");
			out.println(	"}");
			out.println(	"x = x - cbxoA[i];");
			out.println(	"y = y - cbyoA[i];");
//			out.println(	"document.statusClick.coordx.value = x;");
//			out.println(	"document.statusClick.coordy.value = y;");
//			out.println(	"document.statusClick.idx.value = ct;");
//			out.println(	"document.statusClick.boxx.value = Math.floor(x / cbbwA[ct]);");
//			out.println(	"document.statusClick.boxy.value = Math.floor(y / cbbhA[ct]);");
			out.println(	"var cbx = Math.floor(x / cbbwA[ct]);");
			out.println(	"var cby = Math.floor(y / cbbhA[ct]);");
			out.println(	"if((cbA[ct][cbx][cby] != '') && (cbA[ct][cbx][cby] != undefined)) {");
			out.println(		"document.getElementById('plotGraph').style.background = \"url(" +
					contextPath + imageSubContextPath + "/" + "\" + cbA[ct][cbx][cby] + \")\";");
//			out.println(		"document.statusClick.image.value = \"url(" +
//					contextPath + imageSubContextPath + "/" + "\" + cbA[ct][cbx][cby] + \")\";");
			out.println(	"} else {");
			out.println(		"document.getElementById('plotGraph').style.background = \"url(" +
					contextPath + imageSubContextPath + "/" + "\" + pdi + \")\";");
//			out.println(		"document.statusClick.image.value = \"url(" +
//					contextPath + imageSubContextPath + "/" + "\" + pdi + \")\";");
			out.println(	"}");
			out.println("}");

			// MouseOver Stripe JavaScript functions - binary stripe search
			out.println("function searchStripe(ct, value, left, right) {");
			out.println(	"if(right < left)");
			out.println(		"return -1;");
			out.println(	"var mid = Math.floor((left+right)/2);");
			out.println(	"if(value > stA[ct][mid])");
			out.println(		"return searchStripe(ct, value, mid+1, right);");
			out.println(	"else if(value < sfA[ct][mid])");
			out.println(		"return searchStripe(ct, value, left, mid-1);");
			out.println(	"else");
			out.println(		"return mid;");
			out.println("}");

			// MouseOver Stripe JavaScript functions - capture move
			out.println("function captureMove(e) {");
			out.println(	"var x = (e.x == undefined) ? e.layerX : e.x;");
			out.println(	"var y = (e.y == undefined) ? e.layerY : e.y;");
			out.println(	"var ct = -1;");
			out.println(	"var ori = '';");
			out.println(	"for(var i=0 ; i<" + mouseOverStripesCol.size() + " ; i++) {");
			out.println(		"if( (x >= scxoA[i]) && (x <= (scxoA[i] + scwA[i])) && (y >= scyoA[i]) && (y <= (scyoA[i] + schA[i])) ) {");
			out.println(			"ct = i;");
			out.println(			"ori = scohvA[i];");
			out.println(			"break;");
			out.println(		"}");
			out.println(	"}");
			out.println(	"if(ct == -1) {");
			out.println(		"popUp(e,'plotInteractivityTooltip'+slc,0);");
//			out.println(		"document.statusMove.desc.value = '?' + ' ' + slc + ' last'");
			out.println(		"return;");
			out.println(	"}");
			out.println(	"x = x - scxoA[i];");
			out.println(	"y = y - scyoA[i];");
//			out.println(	"document.statusMove.coordx.value = x;");
//			out.println(	"document.statusMove.coordy.value = y;");
//			out.println(	"document.statusMove.idx.value = ct;");
//			out.println(	"document.statusMove.ori.value = ori;");
			out.println(	"var crd = x;");
			out.println(	"if(ori == 'v') crd = y;");
			out.println(	"var sidx = searchStripe(ct, crd, 0, scA[ct]-1);");
			out.println(	"if(sidx >= 0) {");
			out.println(		"if(sliA[ct]!=sidx) document.getElementById('plotInteractivityTooltip'+ct).innerHTML = '" + mouseOverStripesTooltipPrefix + "' + svA[ct][sidx] + '" + mouseOverStripesTooltipSuffix + "';");
			out.println(		"popUp(e,'plotInteractivityTooltip'+ct,1);");
			out.println(		"slc = ct;");
			out.println(		"sliA[ct] = sidx;");
//			out.println(		"document.statusMove.desc.value = svA[ct][sidx] + ' ' + ct;");
			out.println(	"} else {");
			out.println(		"popUp(e,'plotInteractivityTooltip'+ct,0);");
//			out.println(		"document.statusMove.desc.value = '?' + ' ' + ct");
			out.println(	"}");
			out.println("}");


			// Tooltip JavaScript functions
			out.println("var DH = 0;var an = 0;var al = 0;var ai = 0;if (document.getElementById) {ai = 1; DH = 1;}else {if (document.all) {al = 1; DH = 1;} else { browserVersion = parseInt(navigator.appVersion); if ((navigator.appName.indexOf('Netscape') != -1) && (browserVersion == 4)) {an = 1; DH = 1;}}} function fd(oi, wS) {if (ai) return wS ? document.getElementById(oi).style:document.getElementById(oi); if (al) return wS ? document.all[oi].style: document.all[oi]; if (an) return document.layers[oi];}");
			out.println("function pw() {return window.innerWidth != null? window.innerWidth: document.body.clientWidth != null? document.body.clientWidth:null;}");
			out.println("function mouseX(evt) {if (evt.pageX) return evt.pageX; else if (evt.clientX)return evt.clientX + (document.documentElement.scrollLeft ?  document.documentElement.scrollLeft : document.body.scrollLeft); else return null;}");
			out.println("function mouseY(evt) {if (evt.pageY) return evt.pageY; else if (evt.clientY)return evt.clientY + (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop); else return null;}");
			out.println("function popUp(evt,oi,hh) { if (DH) { var wp = pw(); ds = fd(oi,1); dm = fd(oi,0); if (dm.offsetWidth) ew = dm.offsetWidth; else if (dm.clip.width) ew = dm.clip.width; if (hh == 0) { ds.visibility = 'hidden'; } else { tv = mouseY(evt) + 20; lv = mouseX(evt) - (ew/4); if (lv < 2) lv = 2; else if (lv + ew > wp) lv -= ew/2; if (!an) { lv += 'px'; tv += 'px'; } ds.left = lv; ds.top = tv; ds.visibility = 'visible';}}}");


			// close JavaScript and print tooltip div tag
			out.println("</script>");
			for(int i = 0 ; i < mouseOverStripesCol.size() ; i++)
				out.println("<div id=\"plotInteractivityTooltip" + i + "\" class=\"tip\"></div>\n");


			// print plots
			out.println("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td id=\"plotGraph\" valign=\"top\" align=\"left\" background=\""
					+ contextPath + imageSubContextPath + "/" + defaultImage + "\" width=\"" +
					plotWidth + "\" height=\"" + plotHeight + "\"><span style=\"position:relative\"");
			out.println("><span id=\"interactivePlotSpan\" style=\"position: absolute; left:0px; top:0px; width:" +
					plotWidth + "px; height:" + plotHeight + "px\" onClick=\"captureClick(event);\" onMouseMove=\"captureMove(event);\"></span");
			out.println("></span></td></tr></table>");
			out.flush();

		}
		else {
			// Print message
			PrintWriter out = new PrintWriter(pageContext.getOut());
			out.println("<p>Plot Interactivity missing.</p>");
			out.flush();
		}

		return SKIP_BODY;
	}
}