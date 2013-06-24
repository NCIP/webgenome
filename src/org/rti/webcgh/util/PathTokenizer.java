/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

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

package org.rti.webcgh.util;


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
