/*
$Revision$
$Date$

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

package org.rti.webcgh.io.unit_test;

import org.rti.webcgh.io.FileSerializer;
import org.rti.webcgh.util.UnitTestUtils;

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
                "/file_serializer_tester");
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
	
    /**
     * Test decomissioning.
     *
     */
	public void testDecomission() {
		String oid = this.fs.serialize("Hello");
		assertEquals(0 + FileSerializer.FILE_EXTENSION, oid);
		oid = this.fs.serialize("world!");
		assertEquals("1" + FileSerializer.FILE_EXTENSION, oid);
		this.fs.decommissionObject(oid);
		FileSerializer fs2 = new FileSerializer(this.testDirName);
		oid = fs2.serialize("Hello again");
		assertEquals(1 + FileSerializer.FILE_EXTENSION, oid);
		fs2.decommissionAllObjects();
		fs2 = new FileSerializer(this.testDirName);
		oid = fs2.serialize("Hello again again");
		assertEquals(0 + FileSerializer.FILE_EXTENSION, oid);
	}
}
