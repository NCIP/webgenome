/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.rti.webgenome.core.WebcghSystemException;

/**
 * Class containing utility methods for manipulating
 * files and directories.
 * @author dhall
 *
 */
public final class FileUtils {
	
	/**
	 * Constructor.
	 */
	private FileUtils() {
		
	}
    
    
    /**
     * Create directory corresponding to given path.
     * Method also creates necessary sub-directories,
     * if they are absent.  If a directory cannot
     * be created, then method returns null.  If
     * directory is already existing, nothing new
     * is created.
     * @param path Absolute path to directory
     * @return File representing directory or null
     * if it cannot be created
     */
    public static File createDirectory(final String path) {
        PathTokenizer pt = new PathTokenizer(path);
        StringBuffer dirName = new StringBuffer();
        while (pt.hasNext()) {
            String subDirName = pt.next();
            if (dirName.length() > 0) {
                dirName.append("/");
            }
            dirName.append(subDirName);
            File dir = new File(dirName.toString());
            if (dir.exists()) {
                if (!dir.isDirectory()) {
                    return null;
                }
            } else {
                dir.mkdir();
            }
        }
        return new File(path);
    }
    
    
    /**
     * Creates a temp directory for unit tests.  The directory
     * location is a subdirector with the given name
     * off the main unit test temp directory specified
     * by the property 'temp.dir' in the file 'unit_test.properties.'
     * @param dirName Name of temp directory to create.
     * @return Directory created.
     */
    public static File createUnitTestDirectory(final String dirName) {
    	String parentPath = UnitTestUtils.getUnitTestProperty("temp.dir");
    	String dirPath = parentPath + "/" + dirName;
    	return createDirectory(dirPath);
    }
    
    
    /**
     * Get file with given name and given directory.
     * @param directoryPath Classpath-relative path to
     * directory.
     * @param fileName File name.
     * @return A file.
     */
    public static File getFile(final String directoryPath,
    		final String fileName) {
        String absoluteName = directoryPath + "/" + fileName;
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        URL url = loader.getResource(absoluteName);
        if (url == null) {
        	throw new WebcghSystemException("Cannot find file '"
        			+ fileName + "' in directory '" + directoryPath
        			+ "'");
        }
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new WebcghSystemException("Error finding file '"
            		+ absoluteName + "'");
        }
        return file;
    }
}
