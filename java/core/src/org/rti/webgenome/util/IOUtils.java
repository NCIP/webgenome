/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-07-26 16:45:34 $


*/

package org.rti.webgenome.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import org.rti.webgenome.core.WebGenomeSystemException;

/**
 * I/O utility methods.
 */
public final class IOUtils {
	
	/**
	 * Constructor.
	 */
	private IOUtils() {
		
	}

	/**
	 * Get input reader to given file.
	 * @param fileName File name
	 * @return Reader
	 */
	public static BufferedReader getReader(final String fileName) {
		InputStream stream = getInputStream(fileName);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		return reader;
	}
	
	
	/**
	 * Get input stream for specified file name.
	 * @param fileName Relative file name (not full path)
	 * @return An input stream
	 */
	public static InputStream getInputStream(final String fileName) {
		Thread curThread = Thread.currentThread();
		ClassLoader loader = curThread.getContextClassLoader();
		return loader.getResourceAsStream(fileName);
	}
	
	
	/**
	 * Get URL associated with given file name.
	 * @param fileName Name of file
	 * @return URL to file
	 */
	public static URL getFileUrl(final String fileName) {
		Thread curThread = Thread.currentThread();
		ClassLoader loader = curThread.getContextClassLoader();
		return loader.getResource(fileName);
	}
	
	
	/**
	 * Load contents of a text file into memory.
	 * @param in A reader
	 * @return String containing file contents
	 * @throws IOException If an io error occurs
	 */
	public static String loadFileContents(
			final Reader in) throws IOException {
		StringBuffer buff = new StringBuffer();
		int nextChar = in.read();
		while (nextChar != -1) {
			buff.append((char) nextChar);
			nextChar = in.read();
		}
		return buff.toString();
	}
	
	
	/**
	 * Null tolerant method for closing a writer.  Also catches
	 * checked exceptions and rethrows as unchecked.
	 * @param writer Writer to close
	 */
	public static void close(final Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				throw new WebGenomeSystemException("Error closing writer", e);
			}
		}
	}
	
	
	/**
	 * Close input stream re-throwing any exceptions as unchecked.
	 * @param in Input stream
	 */
	public static void close(final InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				throw new WebGenomeSystemException(
						"Error closing input stream", e);
			}
		}
	}
	
	
	/**
	 * Close input stream re-throwing any exceptions as unchecked.
	 * @param out Output stream
	 */
	public static void close(final OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				throw new WebGenomeSystemException(
						"Error closing output stream", e);
			}
		}
	}
	
	
	/**
	 * Close given reader while re-throwing {@code IOException}s
	 * as unchecked.
	 * @param reader Reader to close.
	 */
	public static void close(final Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				throw new WebGenomeSystemException("Error closing reader", e);
			}
		}
	}
}
