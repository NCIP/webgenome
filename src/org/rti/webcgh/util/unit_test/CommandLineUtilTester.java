/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/util/unit_test/CommandLineUtilTester.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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
package org.rti.webcgh.util.unit_test;

import java.util.Properties;

import junit.framework.TestCase;

import org.rti.webcgh.util.CommandLineUtil;

/**
 * Tester for class <code>CommandLineUtilTester</code>
 */
public class CommandLineUtilTester extends TestCase {
    
    
    private CommandLineUtil clu = null;
    private Properties props = null;
    
    
    /**
     * 
     *
     */
    public void setUp() {
        clu = new CommandLineUtil(new String[]{"-b", "-p"});
    }
    
    
    /**
     * 
     */
    public void testError1() {
        try {
            String args[] = {"-a", "-a"};
            this.props = this.clu.commandLineOptionsToProperties(args);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    
    
    /**
     * 
     */
    public void testError2() {
        try {
            String args[] = {"-g", "a", "-g"};
            this.props = this.clu.commandLineOptionsToProperties(args);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    
    
    /**
     * 
     */
    public void testError3() {
        try {
            String args[] = {"-g", "a", "-g", "-p"};
            this.props = this.clu.commandLineOptionsToProperties(args);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    
    
    /**
     * 
     */
    public void testError4() {
        try {
            String args[] = {"-b", "-a", "a", "b", "-p"};
            this.props = this.clu.commandLineOptionsToProperties(args);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    
    
    /**
     *
     */
    public void testCommandLineOptionsToProperties1() {
        String args[] = {"-b"};
        this.props = this.clu.commandLineOptionsToProperties(args);
        assertEquals(props.size(), 1);
        assertTrue(props.containsKey("-b"));
    }
    
    
   /**
    *
    */
   public void testCommandLineOptionsToProperties2() {
       String args[] = {"-b", "-p", "a", "b"};
       this.props = this.clu.commandLineOptionsToProperties(args);
       assertEquals(props.size(), 2);
       assertTrue(props.containsKey("-b"));
       assertTrue(props.containsKey("-p"));
   }
   
   
  /**
   *
   */
  public void testCommandLineOptionsToProperties3() {
      String args[] = {"-b", "-p", "-a", "b", "c"};
      this.props = this.clu.commandLineOptionsToProperties(args);
      assertEquals(props.size(), 3);
      assertTrue(props.containsKey("-b"));
      assertTrue(props.containsKey("-p"));
      assertTrue(props.containsKey("-a"));
  }
  
  
 /**
  *
  */
 public void testCommandLineOptionsToProperties4() {
     String args[] = {"-a", "b", "-b", "b", "c"};
     this.props = this.clu.commandLineOptionsToProperties(args);
     assertEquals(props.size(), 2);
     assertTrue(props.containsKey("-a"));
     assertTrue(props.containsKey("-b"));
 }
  
  
 /**
  * 
  *
  */
  public void testParseArguments1() {
  	String[] raw = {"-a", "b", "-b", "-p", "a", "b"};
  	String[] args = this.clu.parseArguments(raw);
  	assertEquals(args.length, 2);
  	assertEquals(args[0], "a");
  	assertEquals(args[1], "b");
  }
  
  
  /**
   * 
   *
   */
   public void testParseArguments12() {
   	String[] raw = {"-a", "b", "c", "d"};
   	String[] args = this.clu.parseArguments(raw);
   	assertEquals(args.length, 2);
   	assertEquals(args[0], "c");
   	assertEquals(args[1], "d");
   }

}
