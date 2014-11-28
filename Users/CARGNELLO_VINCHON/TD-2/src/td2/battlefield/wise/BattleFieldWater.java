package td2.battlefield.wise;

/**
 * BattleFieldWater
 * 
 * @author rodolphe-c
 * @author k-vinchon
 */
public class BattleFieldWater extends BattleFieldPieceWise
{
	/**
	 * Constructor
	 */
	public BattleFieldWater()
	{ }
	
	/**
	 * Constructor
	 * 
	 * @param x Position X
	 * @param y Position Y
	 */
	public BattleFieldWater(int x, int y)
	{
		this.X = x;
		this.Y = y;
	}
	
	@Override
	public boolean isCompatible(BattleFieldPieceWise with) 
	{
		if(with instanceof BattleFieldRoad || with instanceof BattleFieldLandScape)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isDestroyable() 
	{
		return false;
	}

}
