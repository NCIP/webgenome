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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.deprecated.AnnotationUtils;
import org.rti.webcgh.util.SystemUtils;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * Class for file transfer (FTP)
 */
public class FTPTool {
	static Logger logger = Logger.getLogger(FTPTool.class);
	/**
	 * <code>FTPClient</code>, public field for this class
	 */
	public FTPClient client = null;
	String host;
	String user;
	String password;

	/**
	 * constructor
	 */
	public FTPTool() {
	}

	/**
	 * constructor
	 * @param host
	 * @param user
	 * @param password
	 * @throws WebcghSystemException
	 */
	public FTPTool(String host, String user, String password)
		throws WebcghSystemException {
		this.host = host;
		this.user = user;
		this.password = password;
		client = connect();
	}

	/**
	 * Check if the server name is correct
	 * @param host Host URL
	 * @return true if host exists
	 */
	public static boolean hostExist(String host) {
		FTPClient myFTP = null;

		try {
			myFTP = new FTPClient(host);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Connect to the ftp server
	 * @return <code>FTPClient</code> object
	 * @throws WebcghSystemException
	 */
	private FTPClient connect() throws WebcghSystemException {
		FTPClient myFTP = null;
		try {
			myFTP = new FTPClient(host);
			myFTP.login(user, password);
			myFTP.setType(FTPTransferType.BINARY);
		} catch (IOException e) {
			throw new WebcghSystemException(
				"Error connecting to FTP server",
				e);
		} catch (FTPException e) {
			throw new WebcghSystemException(
				"Error connecting to FTP server",
				e);
		}
		return myFTP;
	}

	/**
	 * Check if the target file exists
	 * @param targetName Remote file want to download
	 * @return true if the target file exists
	 * @throws WebcghSystemException
	 */
	public boolean targetExist(String targetName)
		throws WebcghSystemException {
		String[] folders = null;
		try {
			folders = client.dir();
		} catch (IOException e) {
			throw new WebcghSystemException(
				"Error connecting to FTP server",
				e);
		} catch (FTPException e) {
			throw new WebcghSystemException(
				"Error connecting to FTP server",
				e);
		}
		List folderList = Arrays.asList(folders);
		return folderList.contains(targetName);
	}

	/**
	 * Check if the path on the FTP server is correct
	 * @param path
	 * @return true if the path on remote server is correct
	 * @throws WebcghSystemException
	 */
	public boolean pathExist(String path) throws WebcghSystemException {
		try {
			client.chdir(path);
			String[] folders = client.dir();
			return true;
		} catch (Exception e) {
			throw new WebcghSystemException(
				"Error connecting to FTP server",
				e);
		}
	}

	/**
	 * @param filename File name
	 * @param localpath Local file directory
	 * @return true if the remote file size is equal to local file size
	 * @throws WebcghSystemException
	 */
	public boolean fileModified(String filename, String localpath)
		throws WebcghSystemException {
		try {
			File localFile = new File(localpath + filename);
			long remoteSize = client.size(filename);
			long localSize = localFile.length();
			if (localSize == remoteSize)
				return false;
			return true;
		} catch (Exception e) {
			throw new WebcghSystemException(
				"Error connecting to FTP server",
				e);
		}
	}

	/**
	 * Main method to test class functions
	 * @param args
	 */
	public static void main(String args[]) {
		System.out.println(
			"Class path " + System.getProperty("java.class.path"));
		System.out.println("User dir " + System.getProperty("user.dir"));
		//System.exit(0);

		try {
			Properties prop = SystemUtils.getApplicationProperties();
			String host = prop.getProperty("ftp.host");
			// test host exist?
			if (FTPTool.hostExist(host))
				System.out.println("Host exists");
			else
				System.out.println("Connection failed");
			FTPTool ftp =
				new FTPTool(
					host,
					prop.getProperty("ftp.user"),
					prop.getProperty("ftp.password"));
			// test path exist?
			String path = prop.getProperty("ftp.path");
			ftp.client.chdir(path);
			//			if (ftp.pathExist(path))
			//				System.out.println("Path exists");
			// test getting files
			List fileList = AnnotationUtils.getDownloadFileList();
			String localPath = System.getProperty("user.dir") + "/etc/";
			for (int i = 0; i < fileList.size(); i++) {
				String file = (String) fileList.get(i);
				System.out.println(
					"file " + file + " exists? " + ftp.targetExist(file));
				ftp.client.get(localPath + file, file);
				//System.out.println(ftp.client.size(file));
				System.out.println(
					"file " + file + " changed? " + 
					ftp.fileModified(file, localPath));
			}
			ftp.client.quit();
			System.out.println("Finish");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
