package edu.iut.exceptions;

/**
 * ProgramArgumentException
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class ProgramArgumentException extends Exception {
	
	/**
	 * Constructor
	 */
	public ProgramArgumentException()
    { super(); }
    
	/**
	 * Constructor
	 * 
	 * @param message Exception's message
	 */
    public ProgramArgumentException(String message)
    { super(message); }
    
    /**
     * Constructor
     * 
     * @param exception Exception
     */
    public ProgramArgumentException(ProgramArgumentException exception)
    { super(exception); }
    
}
