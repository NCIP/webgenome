/*
$Revision: 1.2 $
$Date: 2007-04-10 22:32:41 $


*/

package org.rti.webgenome.service.io;

import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for class <code>FileSerializer</code>.
 */
public final class FileSerializerTester extends TestCase {
	
    /** Test directory path name. */
	private String testDirName = null;
    
    /** File serializer. */
	private FileSerializer fs = null;
	
    /**
     * @overrides
     */
	public void setUp() {
        this.testDirName = UnitTestUtils.newTestDirectory(
                "file_serializer_tester");
		this.fs = new FileSerializer(this.testDirName);
		this.fs.decommissionAllObjects();
	}
	
	
    /**
     * @overrides
     */
	public void tearDown() {
		this.fs.decommissionAllObjects();
	}
	
    
    /**
     * Test serialize and deserialize.
     *
     */
	public void testSerializeAndDeserialize() {
		String s1 = "Hello";
		String s2 = "world!";
		String oid1 = this.fs.serialize(s1);
		String oid2 = this.fs.serialize(s2);
		String s3 = (String) this.fs.deSerialize(oid1);
		assertEquals(s1, s3);
		s3 = (String) this.fs.deSerialize(oid2);
		assertEquals(s2, s3);
	}
}
