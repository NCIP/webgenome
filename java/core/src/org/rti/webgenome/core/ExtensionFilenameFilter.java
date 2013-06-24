/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $


*/


package org.rti.webgenome.core;

import java.io.File;
import java.io.FilenameFilter;

/**
 * File filter based on file extension.  Filter is case sensitive.
 */
public final class ExtensionFilenameFilter implements FilenameFilter {
    
    
    // ==================================================================
    //                      Internal state variables
    // ==================================================================
	
	/** File extension. */
    private final String extension;
    
    
    
    // ==================================================================
    //                  Constructors
    // ==================================================================
    
    /**
     * Constructor.
     * @param extension File extension.  May or may not contain the '.'
     * character.
     */
    public ExtensionFilenameFilter(final String extension) {
        if (extension == null || extension.length() < 1) {
            throw new IllegalArgumentException("Invalid file extension '"
            		+ extension + "'");
        }
        this.extension = extension;
    }
    

    // ====================================================================
    //       Implementation of FileFilter interface
    // ====================================================================
    
    /**
     * Is file name acceptable?
     * @param dir A directory
     * @param name A file name
     * @return <code>true</code> if file name passes filter, <code>false</code>
     * otherwise.
     */
    public boolean accept(final File dir, final String name) {
        boolean acceptable = false;
        if (name != null) {
	        int index = name.indexOf(this.extension);
	        if (index >= 0 && index == name.length() - extension.length()) {
	            acceptable = true;
	        }
        }
        return acceptable;
    }

}
