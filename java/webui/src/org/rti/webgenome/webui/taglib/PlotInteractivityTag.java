/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/PlotInteractivityTag.java,v $
$Revision: 1.9 $
$Date: 2008-06-16 16:53:20 $



*/
package org.rti.webgenome.webui.taglib;


import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.graphics.event.MouseOverStripe;
import org.rti.webgenome.graphics.event.MouseOverStripes;
import org.rti.webgenome.graphics.io.ClickBoxes;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.util.Attribute;

/**
 * Generate plot interactivity JavaScript elements.
 * Grabs Shopping Cart from session.
 * @author mzmuda
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
		//System.out.println("clickboxes: " + clickBoxesCol);
		if(isEmptyClickBoxes(clickBoxesCol)) clickBoxesCol = null;	// nullify clickboxes collection if it is empty

		Collection<MouseOverStripes> mouseOverStripesCol = plot.getMouseOverStripes();	// get collection of mouse over stripes
		//System.out.println("mouseoverstripes: " + mouseOverStripesCol);
		if(isEmptyMouseOverStripes(mouseOverStripesCol)) mouseOverStripesCol = null;	// nullify mouseoverstripes collection if it is empty

		String defaultImage = plot.getDefaultImageFileName();	// get default plot image
		Map<String,String> imageFileMap = plot.getImageFileMap();	// get image file map

		// Subtraction from plot width necessary to keep image from repeating
		// TODO: Fix repeating imagage problem so this subtraction is not necessary
		int plotWidth = plot.getWidth() - 25;	// plot width
		int plotHeight = plot.getHeight();	// plot height

		// Get context path and image sub context path
		String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
		String imageSubContextPath = SystemUtils.getApplicationProperty("image.sub.context");

		// Show plot interactivity only if plot is of valid size and either click boxes or mouse overstripes are present
		if (((clickBoxesCol != null) || (mouseOverStripesCol != null)) && (plotWidth > 0) && (plotHeight > 0)) {

			// Print to screen
			PrintWriter out = new PrintWriter(pageContext.getOut());
			out.println("<script language=\"javascript\">");

			out.println("var pdi='" + defaultImage + "';");	// default image

			if(clickBoxesCol != null) {
				out.println("var cbxoA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes origin x array
				out.println("var cbyoA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes origin y array
				out.println("var cbwA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes width array
				out.println("var cbhA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes height array
				out.println("var cbbwA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes box width array
				out.println("var cbbhA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes box height array
				out.println("var cbA=new Array(" + clickBoxesCol.size() + ");");	// clickboxes array

				out.println("var cbxo;");	// individual clickboxes origin x
				out.println("var cbyo;");	// individual clickboxes origin y
				out.println("var cbw;");	// individual clickboxes width
				out.println("var cbh;");	// individual clickboxes height
				out.println("var cbbw;");	// individual clickboxes width
				out.println("var cbbh;");	// individual clickboxes height
				out.println("var cb;");	// individual clickboxes
			}
			if(mouseOverStripesCol != null) {
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
			}




			// init generic counter used for javascript output
			int count;

			if(clickBoxesCol != null) {
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
					out.println("cb=new Array(" + clickBoxes.numBoxes() + ");");
					
					// loop through click boxes and output JavaScript variables
					for(int i = 0 ; i < clickBoxes.getNumCols() ; i++) {
		
						// init each new row as new array
						out.println("cb[" + i + "]=new Array(" + clickBoxes.getNumRows() + ");");
		
						// loop through each row
						for(int j = 0 ; j < clickBoxes.getNumRows() ; j++) {
		
							// grab image name
							String tempImageName = imageFileMap.get(clickBoxes.getClickBoxTextOfBox(j, i));
		
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
			}

			if(mouseOverStripesCol != null) {
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
			}

			if(clickBoxesCol != null) {
				// ClickBoxes JavaScript functions - capture click
				out.println("function captureClick(e) {");

				// capture x and y coordinates
				out.println(	"var x = (e.x == undefined) ? e.layerX : e.x;");
				out.println(	"var y = (e.y == undefined) ? e.layerY : e.y;");

				//out.println(	"document.debug.clickX.value = x;");	// debug
				//out.println(	"document.debug.clickY.value = y;");	// debug

				// reset clickboxes area to -1 (none found)
				out.println(	"var ct = -1;");

				// find correct clickboxes area based on click coordinates
				out.println(	"for(var i=0 ; i<" + clickBoxesCol.size() + " ; i++) {");
				out.println(		"if( (x >= cbxoA[i]) && (x <= (cbxoA[i] + cbwA[i])) && (y >= cbyoA[i]) && (y <= (cbyoA[i] + cbhA[i])) ) {");
				out.println(			"ct = i;");
				out.println(			"break;");
				out.println(		"}");
				out.println(	"}");

				// if one was not found, reset image to default image
				out.println(	"if(ct == -1) {");

				//out.println(	"document.debug.cbX.value = 'NA';");	// debug
				//out.println(	"document.debug.cbY.value = 'NA';");	// debug

				out.println(		"document.getElementById('plotGraph').style.background = \"url(" +
						contextPath + imageSubContextPath + "/" + "\" + pdi + \")\";");
				out.println(		"return;");
				out.println(	"}");

				// adjust click coordinate to native clickbox coordinates
				out.println(	"x = x - cbxoA[i];");
				out.println(	"y = y - cbyoA[i];");

				// calculate click box corresponding to click coordinate
				out.println(	"var cbx = Math.floor(x / cbbwA[ct]);");
				out.println(	"var cby = Math.floor(y / cbbhA[ct]);");

				//out.println(	"document.debug.cbX.value = cbx;");	// debug
				//out.println(	"document.debug.cbY.value = cby;");	// debug

				// if click box contains an image, display it and update dynamic link to image
				out.println(	"if((cbA[ct][cbx][cby] != '') && (cbA[ct][cbx][cby] != undefined)) {");
				out.println(		"document.getElementById('plotGraph').style.background = \"url(" +
						contextPath + imageSubContextPath + "/" + "\" + cbA[ct][cbx][cby] + \")\";");
				out.println(		"document.getElementById('plotGraphLink').href = \"" +
						contextPath + imageSubContextPath + "/" + "\" + cbA[ct][cbx][cby] + \"\";");

				// otherwise reset to default image and update dynamic link to image
				out.println(	"} else {");
				out.println(		"document.getElementById('plotGraph').style.background = \"url(" +
						contextPath + imageSubContextPath + "/" + "\" + pdi + \")\";");
				out.println(		"document.getElementById('plotGraphLink').href = \"" +
						contextPath + imageSubContextPath + "/" + "\" + pdi + \"\";");
				out.println(	"}");
				out.println("}");
			}

			// MouseOver Stripe JavaScript functions - binary stripe search
			if(mouseOverStripesCol != null) {
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

				// capture x and y coordinates
				out.println(	"var x = (e.x == undefined) ? e.layerX : e.x;");
				out.println(	"var y = (e.y == undefined) ? e.layerY : e.y;");

				//out.println(	"document.debug.moveX.value = x;");	// debug
				//out.println(	"document.debug.moveY.value = y;");	// debug

				// reset clickboxes area to -1 (none found)
				out.println(	"var ct = -1;");
				// reset orientation
				out.println(	"var ori = '';");

				// find correct mouseoverstripes area based on move coordinates
				out.println(	"for(var i=0 ; i<" + mouseOverStripesCol.size() + " ; i++) {");
				out.println(		"if( (x >= scxoA[i]) && (x <= (scxoA[i] + scwA[i])) && (y >= scyoA[i]) && (y <= (scyoA[i] + schA[i])) ) {");
				out.println(			"ct = i;");
				out.println(			"ori = scohvA[i];");
				out.println(			"break;");
				out.println(		"}");
				out.println(	"}");

				// if one was not found, hide tooltip
				out.println(	"if(ct == -1) {");
				out.println(		"popUp(e,'plotInteractivityTooltip'+slc,0);");
				out.println(		"return;");
				out.println(	"}");

				// adjust mouse coordinate to native mouseoverstripes coordinates
				out.println(	"x = x - scxoA[i];");
				out.println(	"y = y - scyoA[i];");

				// by default, set orientation coordinate to horizontal x
				out.println(	"var crd = x;");
				// if orientation is vertical, set orientation coordinate to y
				out.println(	"if(ori == 'v') crd = y;");

				// binary search mouseoverstripes
				out.println(	"var sidx = searchStripe(ct, crd, 0, scA[ct]-1);");

				// if a stripe is found at given coordinate, show stripe tooltip
				out.println(	"if(sidx >= 0) {");
				out.println(		"if(sliA[ct]!=sidx) document.getElementById('plotInteractivityTooltip'+ct).innerHTML = svA[ct][sidx];");
				out.println(		"captureMoveHideTooltips(e);");
				out.println(		"popUp(e,'plotInteractivityTooltip'+ct,1);");
				out.println(		"slc = ct;");
				out.println(		"sliA[ct] = sidx;");
				// otherwise, hide tooltip
				out.println(	"} else {");
				out.println(		"captureMoveHideTooltips(e);");
				out.println(	"}");
				out.println("}");

				// MouseOver Stripe JavaScript functions - hide all tooltips
				out.println("function captureMoveHideTooltips(e) {");
				out.println(	"for(var i=0 ; i<" + mouseOverStripesCol.size() + " ; i++) {");
				out.println(		"popUp(e,'plotInteractivityTooltip'+i,0);");
				out.println(	"}");
				out.println("}");
	
	
				// Tooltip JavaScript functions
				out.println("var DH = 0;var an = 0;var al = 0;var ai = 0;if (document.getElementById) {ai = 1; DH = 1;}else {if (document.all) {al = 1; DH = 1;} else { browserVersion = parseInt(navigator.appVersion); if ((navigator.appName.indexOf('Netscape') != -1) && (browserVersion == 4)) {an = 1; DH = 1;}}} function fd(oi, wS) {if (ai) return wS ? document.getElementById(oi).style:document.getElementById(oi); if (al) return wS ? document.all[oi].style: document.all[oi]; if (an) return document.layers[oi];}");
				out.println("function pw() {return window.innerWidth != null? window.innerWidth: document.body.clientWidth != null? document.body.clientWidth:null;}");
				out.println("function mouseX(evt) {if (evt.pageX) return evt.pageX; else if (evt.clientX)return evt.clientX + (document.documentElement.scrollLeft ?  document.documentElement.scrollLeft : document.body.scrollLeft); else return null;}");
				out.println("function mouseY(evt) {if (evt.pageY) return evt.pageY; else if (evt.clientY)return evt.clientY + (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop); else return null;}");
				out.println("function popUp(evt,oi,hh) { if (DH) { var wp = pw(); ds = fd(oi,1); dm = fd(oi,0); if (dm.offsetWidth) ew = dm.offsetWidth; else if (dm.clip.width) ew = dm.clip.width; if (hh == 0) { ds.visibility = 'hidden'; } else { tv = mouseY(evt) + 20; lv = mouseX(evt) - (ew/4); if (lv < 2) lv = 2; else if (lv + ew > wp) lv -= ew/2; if (!an) { lv += 'px'; tv += 'px'; } ds.left = lv; ds.top = tv; ds.visibility = 'visible';}}}");
			}

			// close JavaScript and print tooltip div tags
			out.println("</script>");
			for(int i = 0 ; i < mouseOverStripesCol.size() ; i++)
				out.println("<div id=\"plotInteractivityTooltip" + i + "\" class=\"tip\"></div>\n");


			// print plots
			out.print("<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-style: solid; border-width: 1px; border-color: #888888;\" width=\"" + plotWidth + "\">");
			out.print(	"<tr>");
			out.print(		"<td valign=\"top\" align=\"left\">");

			// loading image span
			out.print(			"<span style=\"background-image:url('" + contextPath +
					"/images/image-loading.gif" + "'); background-position: center center; background-repeat: no-repeat;\">");

			// default image span shown between plot image changes
			out.print(				"<span style=\"background-image:url('" +
					contextPath + imageSubContextPath + "/" + defaultImage +
					"'); background-position: top left; background-repeat: no-repeat;\">");

			// plot interactivity container
			out.print(					"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
			out.print(						"<tr>");
			out.print(							"<td id=\"plotGraph\" valign=\"top\" align=\"left\" background=\"" +
					contextPath + imageSubContextPath + "/" + defaultImage + "\" width=\"" +
					plotWidth + "\" height=\"" + plotHeight + "\" style=\"background-repeat: no-repeat;\">");

			// plot interactivity span
			out.print(								"<span style=\"position:relative\">");
			out.print(									"<span id=\"interactivePlotSpan\" style=\"position: absolute; left:0px; top:0px; width:" +
					plotWidth + "px; height:" + plotHeight + "px\"" + (clickBoxesCol == null ? "" : " onClick=\"captureClick(event);\"") + (mouseOverStripesCol == null ? "" : " onMouseMove=\"captureMove(event);\"") + "></span>");	// add captureClick(event); to onMouseMove property for debugging purposes
			out.print(								"</span>");
			out.print(							"</td>");
			out.print(						"</tr>");
			out.print(					"</table>");

			out.print(				"</span>");
			out.print(			"</span>");
			out.print(		"</td>");
			out.print(	"</tr>");
			out.println("</table>");

			// dynamic download link for image
			out.println("<div style=\"margin:8px;\"><a id=\"plotGraphLink\" target=\"blank\" href=\"" + contextPath + imageSubContextPath + "/" + defaultImage + "\">Download image</a></div>");

			// debug
			//out.println("<form name=\"debug\">");
			//out.println("<div>Move: x<input type=\"text\" name=\"moveX\"> y<input type=\"text\" name=\"moveY\"></div>");
			//out.println("<div>Click: x<input type=\"text\" name=\"clickX\"> y<input type=\"text\" name=\"clickY\"></div>");
			//out.println("<div>ClickBox: x<input type=\"text\" name=\"cbX\"> y<input type=\"text\" name=\"cbY\"></div>");
			//out.println("</form>");

			out.flush();

		}
		else {
			// Print message
			PrintWriter out = new PrintWriter(pageContext.getOut());

			// print plot
			out.print("<table cellpadding=\"0\" cellspacing=\"0\" style=\"border-style: solid; border-width: 1px; border-color: #888888;\">");
			out.print(	"<tr>");
			out.print(		"<td id=\"plotGraph\" valign=\"top\" align=\"left\" background=\""
					+ contextPath + imageSubContextPath + "/" + defaultImage + "\" width=\"" +
					plotWidth + "\" height=\"" + plotHeight + "\">");

			out.print(			"<span style=\"position:relative\">");
			out.print(				"<span id=\"interactivePlotSpan\" style=\"position: absolute; left:0px; top:0px; width:" +
					plotWidth + "px; height:" + plotHeight + "px\"></span>");
			out.print(			"</span>");

			out.print(		"</td>");
			out.print(	"</tr>");
			out.println("</table>");

			// dynamic download link for image
			out.println("<div style=\"margin:8px;\"><a id=\"plotGraphLink\" target=\"blank\" href=\"" + contextPath + imageSubContextPath + "/" + defaultImage + "\">Download image</a></div>");

			out.flush();
		}

		return SKIP_BODY;
	}

	/**
	 * Helper method for determining if all elements within a clickboxes collection have empty values
	 * @param clickBoxCol collection of clickboxes
	 * @return empty status of collection
	 */
	private boolean isEmptyClickBoxes(Collection<ClickBoxes> clickBoxesCol) {
		if(clickBoxesCol == null) return true;	// if col is null, automatic empty
		for(ClickBoxes clickBoxes : clickBoxesCol) {	// loop through all clickboxes
			for(String test : clickBoxes.getClickBox()) {	// loop through all clickbox entries
				if(test == null) continue;	// if null string found, continue looking
				if(!test.trim().equals("")) return false;	// if non-empty found, return not empty so no need to search further
			}
		}
		return true;	// no non-empty entries found
	}

	/**
	 * Helper method for determining if all elements within a clickboxes collection have empty values
	 * @param mouseOverStripesCol collection of mouseoverstripes
	 * @return empty status of collection
	 */
	private boolean isEmptyMouseOverStripes(Collection<MouseOverStripes> mouseOverStripesCol) {
		if(mouseOverStripesCol == null) return true;	// if col is null, automatic empty
		for(MouseOverStripes mouseOverStripes : mouseOverStripesCol) {	// loop through all mouseoverstripes
			if(mouseOverStripes.getStripes() == null) continue;	// if null stripes found, continue looking
			if(!mouseOverStripes.getStripes().isEmpty()) return false;	// if non-empty found, return not empty so no need to search further
		}
		return true;	// no non-empty entries found
	}
}