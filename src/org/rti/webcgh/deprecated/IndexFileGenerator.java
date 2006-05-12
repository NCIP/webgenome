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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.rti.webcgh.core.WebcghSystemException;
//import org.rti.webcgh.dao.impl.util.FeatureIndex;
import org.rti.webcgh.util.SystemUtils;

import com.enterprisedt.net.ftp.FTPException;


/**
 * This main class finishes a workflow of:
 * 1. Check if UCSC data dump schema changed. If yes, output to screen, program exits.
 * 2. Download data dump schema file (*.sql) and data dump file (*.txt.gz) to /etc folder
 * 3. Unzip all *.gz file, delete *.gz file
 * 4. Create index file in /etc folder
 * 5. Copy index file to /classes folder
 */
public class IndexFileGenerator {

	
	/**
	 * Main method
	 * @param args Command line args
	 */
	public static void main(String[] args) {
		try {
			Properties prop = SystemUtils.getApplicationProperties();
			String currDir = System.getProperty("user.dir");
			String localPath = currDir + prop.getProperty("local.dir");
			String classPath = currDir + prop.getProperty("classpath.dir");
			
			FTPTool ftp = new FTPTool(prop.getProperty("ftp.host"), 
								prop.getProperty("ftp.user"),
								prop.getProperty("ftp.password"));
			List fileList = AnnotationUtils.getDownloadFileList();
			List nameList = AnnotationUtils.getNames();
			ftp.client.chdir(prop.getProperty("ftp.path"));
			// To see if any table schema is changed
			schemaModified(localPath, ftp, fileList);
			// download *.sql and *.txt.gz files
			downloadFiles(localPath, ftp, fileList);
			// unzip *.gz files
			GUnzipper.unzipFiles(localPath);
			// create index files
			createIndex(localPath, nameList);
			// copy files to classPath
			copyFiles(localPath, classPath, nameList);
			ftp.client.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Copy related files from /etc fold to classpath (/web/WEB-INF/classes)
	 * @param localPath User local working directory
	 * @param classPath ClassPath
	 * @param nameList A list of data dump table names
	 * @throws Exception
	 */
	private static void copyFiles(
		String localPath,
		String classPath,
		List nameList)
		throws Exception {
		for (int i = 0; i < nameList.size(); i++) {
			String name = (String)nameList.get(i);
			JCopy j = new JCopy();
			j.copyFile(new File(localPath + name + ".txt"), 
						new File(classPath + name + ".txt"));
			j.copyFile(new File(localPath + name + ".wci"), 
						new File(classPath + name + ".wci"));
		}
	}

	/**
	 * Create index files for all the data dump tables
	 * @param localPath User local working directory
	 * @param nameList A list of data dump table names
	 * @throws WebcghSystemException
	 * @throws FileNotFoundException
	 */
	private static void createIndex(String localPath, List nameList)
		throws WebcghSystemException, FileNotFoundException {
		System.out.println("Creating index files....");
		for (int i = 0; i < nameList.size(); i++) {
			String name = (String)nameList.get(i);
			//FeatureIndex.construct(name, localPath);
//			FeatureIndex index = FeatureIndex.getFeatureIndex(name);
//			PrintStream out = new PrintStream(
//				new FileOutputStream("c:\\temp\\" + name + ".out"));
//			index.print(out);
			System.out.println("Index file created for " + name);
		}
	}

	/**
	 * Download remotes files from UCSC ftp server
	 * @param localPath User local working directory
	 * @param ftp <code>FTPTool</code> object
	 * @param list A list of file names need to be downloaded
	 * @throws IOException
	 * @throws FTPException
	 */
	private static void downloadFiles(String localPath, FTPTool ftp, List list)
		throws IOException, FTPException {
		for (int i = 0; i < list.size(); i++) {
			String fileName = (String)list.get(i);
			ftp.client.get(localPath + fileName, fileName);
			System.out.println(fileName + " downloaded.");
		}
	}

	/**
	 * Determine if any table schema is changed. If yes, exit and report a message.
	 * @param localPath User local working directory
	 * @param ftp <code>FTPTool</code> object
	 * @param list A list of file names need to be downloaded
	 * @throws WebcghSystemException
	 */
	private static void schemaModified(String localPath, FTPTool ftp, List list)
		throws WebcghSystemException {
		for (int i = 0; i < list.size(); i++) {
			String fileName = (String)list.get(i);
			if (fileName.endsWith(".sql")) {
				File oldFile = new File(localPath + fileName);
				if (!oldFile.exists())
					continue;
				if (ftp.fileModified(fileName, localPath)) {
					System.out.println("File " + fileName + " changed. " + 
						"Please modify config file annotations-config.xml.");
					System.exit(0);
				}
			}
		}
		System.out.println("Data dump schemas are not changed since last download");
	}
}
