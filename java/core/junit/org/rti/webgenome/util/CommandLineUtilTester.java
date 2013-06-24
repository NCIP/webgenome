/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/util/CommandLineUtilTester.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/
package org.rti.webgenome.util;

import java.util.Properties;

import junit.framework.TestCase;

import org.rti.webgenome.util.CommandLineUtil;

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
