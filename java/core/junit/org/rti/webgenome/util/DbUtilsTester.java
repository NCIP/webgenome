/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-29 21:47:51 $


*/

package org.rti.webgenome.util;

import junit.framework.TestCase;

/**
 * Tester for {@link org.rti.webgenome.util.DbUtils}.
 * @author dhall
 *
 */
public class DbUtilsTester extends TestCase {

	/**
	 * Test {@code encodeClob} and {@code decodeClob} methods.
	 * @throws Exception if anything goes wrong
	 */
	public void testEncodeClobAndDecodeClob() throws Exception {
		String[][] s = {
				{"aaa", "bbb", "c\\c"},
				{"ddd", "e,e", "fff"},
				{null, "gggg", null}
		};
		String encoding = DbUtils.encodeClob(s);
		String[][] t = DbUtils.decodeClob(encoding);
		assertNotNull(t);
		assertEquals(s.length, t.length);
		for (int i = 0; i < s.length; i++) {
			assertNotNull(t[i]);
			assertEquals(s[i].length, t[i].length);
			for (int j = 0; j < s[i].length; j++) {
				assertTrue(StringUtils.equal(s[i][j], t[i][j]));
			}
		}
	}
}
