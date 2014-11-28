package edu.iut.cipher.encoder;
import edu.iut.exceptions.EncodeException;

import java.io.File;

/**
 * FileEncoder
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public interface FileEncoder {
	/**
	 * Encode
	 * 
	 * @param source Path source file
	 * @param destination Path destination file
	 * @param key Key encoding
	 * @throws EncodeException Exception 
	 */
    public void encode(File source, File destination, String key) throws EncodeException;
}
