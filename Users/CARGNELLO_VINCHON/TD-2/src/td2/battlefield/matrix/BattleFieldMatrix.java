package td2.battlefield.matrix;

import java.util.HashMap;

/**
 * BattleFieldMatrix
 * 
 * @author rodolphe-c
 * @author k-vinchon  
 *
 * @param <GenericData> Generic Object
 */
public class BattleFieldMatrix<GenericData>
{

	protected HashMap<Integer,HashMap<Integer, GenericData>> matrix;
	int maxX;
	int maxY;
	
	/**
	 * Constructor
	 */
	public BattleFieldMatrix()
	{
		matrix = new HashMap<Integer,HashMap<Integer, GenericData>>();
		maxX = 0;
		maxY = 0;
	}
	
	/**
	 * Return Matrix's case
	 * 
	 * @param i Line
	 * @param j Column
	 * @return Matrix's case
	 */
	public GenericData get(int i, int j)
	{
		return matrix.get(i).get(j);
	}
	
	/**
	 * Set Matrix's case
	 * 
	 * @param i Line
	 * @param j Column
	 * @param a Case
	 */
	public void set(int i, int j, GenericData a) throws ArithmeticException
	{
		if(i < 0 || j < 0)
		{
			throw new ArithmeticException();
		}
		
		if(maxX < i+1)
		{
			maxX = i+1;
		}
		
		if(maxY < j+1)
		{
			maxY = j+1;
		}
		HashMap<Integer, GenericData> tmp = matrix.get(i);
		if(tmp != null)
		{
			matrix.get(i).put(j, a);
		}
		else
		{
			HashMap<Integer, GenericData> tmp2 = new HashMap<Integer, GenericData>();
			tmp2.put(j, a);
			matrix.put(i, tmp2);
		}
		
	}
	
	/**
	 * Test if case exists
	 * 
	 * @param i Line
	 * @param j Column
	 * @return True if case[i;j] exists, false otherwise
	 */
	public boolean exists(int i, int j)
	{
		if(matrix.containsKey(i))
		{
			if(matrix.get(i).containsKey(j))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public int getMaxX()
	{
		return maxX;
	}
	
	public int getMaxY()
	{
		return maxY;
	}
}
