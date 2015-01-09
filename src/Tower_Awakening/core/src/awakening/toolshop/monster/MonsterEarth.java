package awakening.toolshop.monster;

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
		if(Math.abs(getX() - path.get(boxDestination).getCoordX())<1f  && Math.abs(getZ() - path.get(boxDestination).getCoordY())<1f)
		{
			if(boxDestination<path.size()-1)
			{
				boxActuel = path.get(boxDestination);
				boxDestination++;
				System.out.println("boxDestination  : " + path.get(boxDestination).getCoordX()
						+ " " + path.get(boxDestination).getCoordY());
				System.out.println("boxActuel : " + boxActuel.getCoordX() + " " + boxActuel.getCoordY());

				dx = (float)(path.get(boxDestination).getCoordX() - boxActuel.getCoordX()) / 100f;
				dy = (float)(path.get(boxDestination).getCoordY() - boxActuel.getCoordY()) / 100f;
			}
			else
			{
				isArrived=true;
			}
		}
		else
		{
			this.translate(dx, 0, dy);
		}
	}
	@Override
	public void crazyMove()
	{
	}
	
	public MonsterEarth(MeshTA m)
	{
		super(m);
	}
}
