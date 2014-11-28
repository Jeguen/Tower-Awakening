package edu.iut.cipher.decoder;

import edu.iut.exceptions.DecodeException;

import java.io.File;

/**
 * FileDecoder
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public interface FileDecoder {
	
	/**
	 * Decode file
	 * 
	 * @param source Path source file
	 * @param destination Path destination file
	 * @param key Key decoder
	 * @throws DecodeException Decode Exception
	 */
    public void decode(File source, File destination, String key) throws DecodeException;
}
