/*
 * JavaScript functions which handle Saving SVG plot data/graphs to graphic files.
 * 
 * Use this common script anywhere that you have page which has SVG that needs
 * to be accompanied by a "Save As Graphic" function/button.
 * 
 * Usage Example - see scatterPlot.jsp for a working example
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
    SVGDoc = "" ;
}

function saveAsWindow() {
    window.open('','plotSaveAs','resizable=yes,width=600,height=400,status=1,scrollbars=1');
}

function submitToSaveAs () {
    // setup a timer and show a please wait animated gif
    var waitIconDiv = document.getElementById ( "waitIcon" ) ;
    waitIconDiv.style.visibility = "visible" ;
    // need this silly timer to get the wait icon shown
    // for some reason, if we go straight into the rest of the submit operation, i.e.
    // progress onto copyPlotImg() etc. the wait icon doesn't shown in IE
    setTimeout ( "startSubmit()", 400 ) ;
}

function startSubmit() {
    copyPlotImg();  // copy the plot img svg document
    saveAsWindow(); // make a new window with suitable popup parameters
    document.plotSaveAsForm.submit();
    document.forms['plotSaveAsForm'].elements['svgDOM'].value = "" ;
    var waitIconDiv = document.getElementById ( "waitIcon" ) ;
    waitIconDiv.style.visibility = "hidden" ;
}

function renderSaveAsLink( ) {
    
    document.write (
      "<table>" +
          "<tr>" +
              "<td valign=\"middle\">" +
                  "<a href=\"javascript:submitToSaveAs();\">Save Plot</a>" +
              "</td>" +
              "<td valign=\"middle\">" +
                  "<div id=\"waitIcon\" style=\"display:inline;visibility:hidden;\">" +
                  "<img src=\"../images/watch.gif\" border=\"0\" alt=\"Please Wait\">" +
                  "&nbsp;(loading ... please wait)" +
                  "</div>" +
              "</td>" +
          "</tr>" +
      "</table" ) ;

}
