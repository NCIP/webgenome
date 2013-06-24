/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.5 $
$Date: 2007-09-13 23:42:16 $


*/

package org.rti.webgenome.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.rti.webgenome.core.WebGenomeSystemException;

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
     * Get file with given name and given directory.
     * @param directoryPath Classpath-relative path to
     * directory.
     * @param fileName File name.
     * @return A file.
     */
    public static File getFile(final String directoryPath,
    		final String fileName) {
        String absoluteName = directoryPath + "/" + fileName;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(absoluteName);
        if (url == null) {
        	throw new WebGenomeSystemException("Cannot find file '"
        			+ fileName + "' in directory '" + directoryPath
        			+ "'");
        }
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new WebGenomeSystemException("Error finding file '"
            		+ absoluteName + "'");
        }
        return file;
    }
}
