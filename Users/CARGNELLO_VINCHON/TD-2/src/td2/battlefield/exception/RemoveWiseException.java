package td2.battlefield.exception;

/**
 * RemoveWiseException
 * 
 * @author rodolphe-c
 *@author k-vinchon
 *
 */
public class RemoveWiseException extends Exception
{ 
	/**
	 * Constructor
	 * 
	 * @param i Line i
	 * @param j Column j
	 */
	  public RemoveWiseException(int i, int j)
	  {
	    System.out.println("Wise "+i+ ", "+j+" is not destroyable !");
	  }  
}