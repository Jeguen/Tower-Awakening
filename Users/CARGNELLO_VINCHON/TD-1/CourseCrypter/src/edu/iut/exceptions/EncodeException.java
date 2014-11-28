package edu.iut.exceptions;

/**
 * EncodeException
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class EncodeException extends Exception {
	/**
	 * Constructor
	 */
    public EncodeException()
    { super(); }
    
    /**
     * Constructor
     * 
     * @param message Exception's message
     */
    public EncodeException(String message)
    { super(message); }
    
    /**
     * Constructor
     * 
     * @param exception Exception
     */
    public EncodeException(EncodeException exception)
    { super(exception); }
    
}
