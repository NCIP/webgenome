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
package org.rti.webcgh.deprecated;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import org.apache.log4j.Logger;


/**
 * Class to unzip compressed files
 */
public class GUnzipper {
  static Logger logger = Logger.getLogger(GUnzipper.class);
  /**
   * suffix for zip files
   */
  public static final String GZIP_SUFFIX = ".gz";

  /**
   * construct
   */
  public GUnzipper() {
  }

  /**
   * @return nothing, unzip all input zip files
   *
   * @param zipfilenames
   */
  public static ArrayList unzip(String[] zipfilenames) {
	ArrayList unzipped = new ArrayList();
	System.out.println("start unzipping.");

	for (int i = 0; i < zipfilenames.length; i++) {
	  if (zipfilenames[i].toLowerCase().endsWith(GZIP_SUFFIX)) {
		FileInputStream fin = null;
		GZIPInputStream in = null;
		FileOutputStream out = null;

		try {
		  fin = new FileInputStream(zipfilenames[i]);
		  in = new GZIPInputStream(fin);

		  String filename = zipfilenames[i].substring(0,
			  zipfilenames[i].length() - 3);
		  unzipped.add(filename);
		  out = new FileOutputStream(filename);

		  byte[] buffer = new byte[256];

		  // read unzipped file and write content to a file
		  while (true) {
			int bytesRead = in.read(buffer);

			if (bytesRead == -1) {
			  break;
			}

			out.write(buffer, 0, bytesRead);
		  }
		  System.out.println(zipfilenames[i] + " unzipped");
		} catch (IOException e) {
		  System.err.println(e);
		} finally {
		  try {
			out.close();
			in.close();
			fin.close();
			File zipFile = new File(zipfilenames[i]);
			zipFile.delete();
		  } catch (IOException e1) {
			e1.printStackTrace();
		  }
		}
	  } 
//	  else {
//		System.err.println(zipfilenames[i] +
//		  " does not appear to be a gzipped file.");
//	  }
	}
	return unzipped;
  }

  /**
   * Unzip all the downloaded zipped files
   * @param path Path for the files
   * @return A list of all the unzipped files
   */
  public static ArrayList unzipFiles(String path) {
	File localpath = new File(path);
	String[] filenames = localpath.list();
	filenames = getAbsolutePath(filenames, path);

	ArrayList unzipped = GUnzipper.unzip(filenames);
	logger.info(unzipped.size() + " zip files unzipped.");

	return unzipped;
  }

  /**
   * Get absolute path
   * @param filenames
   * @param path
   * @return An array of absolute paths
   */
  private static String[] getAbsolutePath(String[] filenames, String path) {
	for (int i = 0; i < filenames.length; i++) {
	  filenames[i] = path + filenames[i];
	}

	return filenames;
  }

  /**
   * unzip single file
   * @param filename
   * @return File name
   */
  public static String unzip(String filename) {
	String[] filenames = { filename };
	ArrayList oneFile = unzip(filenames);

	return (String) oneFile.get(0);
  }
  
  /**
   * Main method to test class functions
   * @param args
   */
public static void main(String args[]) {
  		GUnzipper.unzipFiles(System.getProperty("user.dir") + "/etc/");
  }
}

