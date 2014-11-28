package td2.battlefield.wise;

import td2.battlefield.matrix.*;
import td2.battlefield.exception.*;

import java.util.HashMap;

/**
 * BattleFieldMatrixPW
 * 
 * @author rodolphe-c
 * @author k-vinchon  
 */
public class BattleFieldMatrixPW extends BattleFieldMatrix<BattleFieldPieceWise> 
{
	/**
	 * Constructor
	 */
	public BattleFieldMatrixPW()
	{
		this.matrix = new HashMap<Integer,HashMap<Integer, BattleFieldPieceWise>>();
	}
	
	public void remove(int i, int j) throws RemoveWiseException
	{
		HashMap<Integer, BattleFieldPieceWise> tmp = matrix.get(i);
		if(matrix.containsKey(i))
		{
			if(matrix.get(i).containsKey(j))
			{
				if(tmp.get(j).isDestroyable())
				{
					matrix.get(i).remove(j);
				}
				else
				{
					throw new RemoveWiseException (i, j);
				}
			}
			else 
			{
				throw new RemoveWiseException (i, j);
			}
		}
		else 
		{
			throw new RemoveWiseException (i, j);
		}
	}

}
