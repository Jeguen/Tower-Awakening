package edu.iut.exceptions;
/**
 * DecodeException
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class DecodeException extends Exception {
	/**
	 * Constructor
	 */
    public DecodeException()
    { super(); }
    
    /**
     * Constructor
     * 
     * @param message Exception's message
     */
    public DecodeException(String message)
    { super(message); }
    
    /**
     * Constructor
     * 
     * @param exception Exception
     */
    public DecodeException(DecodeException exception)
    { super(exception); }
    
}
