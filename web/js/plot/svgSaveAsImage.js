/*
 * JavaScript functions which handle Saving SVG plot data/graphs to graphic files.
 * 
 * Use this common script anywhere that you have page which has SVG that needs
 * to be accompanied by a "Save As Graphic" function/button.
 * 
 * Usage Example (see scatterPlot.jsp for a working example), but here's a snippet of what you'd
 * need to do to orchestrate plot saves to graphic files (jpg, png):
 * 
 * <script language="JavaScript"
 *         src="<html:rewrite page="/js/plot/svgSaveAsImage.js"/>"
 *         type="text/javascript"></script>
 * <html:form action="/plot/saveAs" target="plotSaveAs">
 *     <html:hidden property="svgDOM" value=""/>
 * </html:form>
 * 
 * 
 * Author: Dean Jackman
 * 
 */

var ELEMENT_NODE = 1;
var TEXT_NODE = 3;
var CDATA_SECTION_NODE = 4;
var accumulator; // holds the serialized XML

/*
 * Serializes the Element to a String, including serializing child
 * elements and nodes.
 */
function elementToString(element) {
  if (element){
    var attribute;
    var i;
    accumulator += "<" + element.nodeName;
    
    // Add the attributes
    for (i = element.attributes.length-1; i>=0; i--){
      attribute = element.attributes.item(i);
      accumulator += " " + attribute.nodeName + '="' + attribute.nodeValue+ '"';
    }
    // Run through any children
    if (element.hasChildNodes()){
      var children = element.childNodes;
      accumulator += ">";
      for (i=0; i<children.length; i++){
        switch(children.item(i).nodeType){

          case ELEMENT_NODE :
            elementToString(children.item(i));    // RECURSE!!
            break;

          case TEXT_NODE :
            accumulator += escape(children.item(i).nodeValue);
            break;

          case CDATA_SECTION_NODE :
            accumulator += "\x3c![CDATA[";       // unescaped <
            accumulator += children.item(i).nodeValue;
            accumulator += "]]\x3e";             // unescaped >
            }
        }

          accumulator += "</" + element.nodeName + ">";
      } else {
          accumulator += " />";
      }
  }
  return accumulator;
}

function escape(markup){
  markup = markup.replace(/&/g, "&amp;");
  markup = markup.replace(/</g, "&lt;");
  markup = markup.replace(/>/g, "&gt;");
  return markup;
}

/*
 * Serializes the SVG DOM and appends this to a suitable version/doctype header.
 */
function getSVG( svgDocument ){
    var svgDocElement = svgDocument.getDocumentElement();
    accumulator = "";
    var content = elementToString( svgDocElement );
		 
	return content ;
}

/*
 * Pulls the SVG DOM out of the common plotImg element that the SVG content
 * resides in (for all plot types across webgenome) and populates this into
 * a form input variable, suitable for posting to the server-side.
 * Server-side functionality for taking this serialized DOM and generating an
 * appropriate graphic image exists. See TBD 
 */
function copyPlotImg ( ) {
	var plotImg = document.getElementById("plotImg");
    SVGDoc = plotImg.getSVGDocument();
    document.forms['plotSaveAsForm'].elements['svgDOM'].value = getSVG ( SVGDoc ) ;
}

function renderSaveAsLink() {
    
    document.write (
      "<%-- Copy the SVG, then submit save as form --%>\n" +
      "<a href=\"javascript:copyPlotImg();document.plotSaveAsForm.submit()\">Save Plot</a>\n" +
      "<span style=\"font-size: 65%; color: gray;\">(pop-up: can take some time to appear for large plots)</span>\n" ) ;

}
