package td2.battlefield.wise;
/**
 * BattleFieldLandScape
 * 
 * @author rodolphe-c
 * @author k-vinchon  
 */
public class BattleFieldLandScape extends BattleFieldPieceWise 
{
	/**
	 * Constructor
	 */
	public BattleFieldLandScape()
	{ }
	
	/**
	 * Constructor
	 * 
	 * @param x Position X
	 * @param y Position Y
	 */
	public BattleFieldLandScape(int x, int y)
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
