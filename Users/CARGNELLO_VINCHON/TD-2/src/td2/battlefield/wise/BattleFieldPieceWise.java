package td2.battlefield.wise;

/**
 * BattleFieldPieceWise
 * 
 * @author rodolphe-c
 * @author k-vinchon  
 */
public abstract class BattleFieldPieceWise
{
	protected int X;
	protected int Y;
	
	/**
	 * Return X position
	 * 
	 * @return X
	 */
	public int getX() 
	{
		return X;
	}

	/**
	 * Set X position
	 * 
	 * @param x Position X
	 */
	public void setX(int x) 
	{
		X = x;
	}

	/**
	 * Return Y position
	 * 
	 * @return Y
	 */
	public int getY() 
	{
		return Y;
	}

	/**
	 * Set Y position
	 * 
	 * @param y Position Y
	 */
	public void setY(int y) 
	{
		Y = y;
	}
	/**
	 * Test if case is compatible
	 * 
	 * @param with BattleFieldPieceWise 
	 * @return True if is compatible, false otherwise
	 */
	public abstract boolean isCompatible(BattleFieldPieceWise with);
	
	/**
	 * Test if case is destroyable
	 * 
	 * @return True if is destroyable, false otherwise
	 */
	public abstract boolean isDestroyable();
	
}
