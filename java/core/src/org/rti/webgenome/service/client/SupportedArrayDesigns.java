/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-10-23 16:17:07 $


*/

package org.rti.webgenome.service.client;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

public final class SupportedArrayDesigns {

	private static String[] arrayDesigns = {"HG-U133_Plus_2", "HG-U133A_2", "HT_HG-U133A",
			"Hu6800", "HumanWG-6"};
	
	public static Collection<String> getArrayDesigns(){
		Collection<String> collDesigns = new ArrayList<String>();
		
		for (String s : arrayDesigns)
			collDesigns.add(s);
		
		return collDesigns;
	}
	
	/**
	 * Check if design is supported. 
	 * 
	 * @param designToCheck
	 * @return
	 * @throws Exception
	 */
	public static boolean isSupported(String designToCheck) throws Exception{
		for (String ad : arrayDesigns){
			if (ad.equals(designToCheck))
				return true;
		}
		return false;
	}
	
	public static String getCaArraySupportedDesignsAsString(final HttpServletRequest request){		
	    String 	supportedDesignsStr = "";
		
		for(String s : arrayDesigns)
			supportedDesignsStr += s + "\n";
		
		return supportedDesignsStr;
	}
	
}
