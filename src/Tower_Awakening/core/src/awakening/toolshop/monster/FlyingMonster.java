package awakening.toolshop.monster;

public class FlyingMonster extends Monster
{
	public FlyingMonster(int id, int lifePoint, String name, float speedAttack, float vitesseDeplacement, boolean visible, String facSheet,
	            int buildCost, int gainGold, int damage)
	{
		super(id, lifePoint, name, speedAttack, vitesseDeplacement, visible, facSheet, buildCost, gainGold, damage);
	}
	public FlyingMonster()
    {
		super();
    }
	
	@Override
	public void normalMove()
	{
	}
	@Override
	public void crazyMove()
	{
	}
}
