/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/PathTokenizer.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $



*/

package org.rti.webgenome.util;


/**
 * Tokenizes each directory or file in path.
 * Path can use a mixture of '/' and '\' characters.
 * @author dhall
 *
 */
public final class PathTokenizer {
    
    // =========================
    //      Attributes
    // =========================
    
    /** Path to tokenize. */
    private final String path;
    
    /** Next token. */
    private String nextToken = null;
    
    /** Position of end of current token. */
    private int position = -1;
    
    
    // =======================
    //      Constructors
    // =======================
    
    /**
     * Constructor.
     * @param path Full path
     * @throws IllegalArgumentException if given path is null
     */
    public PathTokenizer(final String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        this.path = path;
        this.advance();
    }
    
    // ======================
    //     Business methods
    // ======================
    
    /**
     * Is there another token?
     * @return T/F
     */
    public boolean hasNext() {
        return nextToken != null;
    }
    
    
    /**
     * Get next token in path.
     * @return Next token in path
     */
    public String next() {
        String next = this.nextToken;
        this.advance();
        return next;
    }
    
    
    /**
     * Advance to next token.
     */
    private void advance() {
        this.position++;
        if (this.position == 0 && this.position < this.path.length()
                && this.isPathSeparator(this.path.charAt(this.position))) {
            this.position++;
        }
        this.nextToken = null;
        if (this.position < this.path.length()) {
            int start = this.position;
            while (this.position < this.path.length()
                    && !this.isPathSeparator(
                            this.path.charAt(this.position))) {
                this.position++;
            }
            if (this.position > start) {
                this.nextToken =
                    this.path.substring(start, this.position);
                if (this.isPathSeparator(this.nextToken.charAt(0))) {
                    this.nextToken = this.nextToken.substring(1);
                }
            }
        }
    }
    
    
    /**
     * Is given character a path separator?
     * Both the '/' and '\' characters are
     * considered path separators.
     * @param c A character
     * @return T/F
     */
    private boolean isPathSeparator(final char c) {
        return c == '/' || c == '\\';
    }
    
}
