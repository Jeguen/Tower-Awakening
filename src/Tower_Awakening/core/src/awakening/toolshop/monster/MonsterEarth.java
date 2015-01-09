package awakening.toolshop.monster;

import ta.shape3D.Point.Point2D;
import ta.shape3D.mesh.MeshTA;

public class MonsterEarth extends Monster
{
	public MonsterEarth(int id, int lifePoint, String name, float speedAttack, float vitesseDeplacement, boolean visible, String facSheet,
	            int buildCost, int gainGold, int damage)
	{
		super(id, lifePoint, name, speedAttack, vitesseDeplacement, visible, facSheet, buildCost, gainGold, damage);
	}
	@Override
	public void normalMove()
	{
	}
	@Override
	public void crazyMove()
	{
	}
	
	public MonsterEarth(MeshTA m, Point2D position)
	{
		super(m, position);
	}
}
