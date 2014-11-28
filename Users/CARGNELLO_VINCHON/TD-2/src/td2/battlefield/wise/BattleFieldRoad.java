package td2.battlefield.wise;

/**
 * BattleFieldRoad
 * 
 * @author rodolphe-c
 * @author k-vinchon
 */
public class BattleFieldRoad extends BattleFieldPieceWise
{
	/**
	 * Constructor
	 */
	public BattleFieldRoad()
	{ }
	
	/**
	 * Constructor
	 * 
	 * @param x Position X
	 * @param y Position Y
	 */
	public BattleFieldRoad(int x, int y)
	{
		this.X = x;
		this.Y = y;
	}
	
	@Override
	public boolean isCompatible(BattleFieldPieceWise with) 
	{
		if(with instanceof BattleFieldWater)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isDestroyable() 
	{
		return true;
	}

}
