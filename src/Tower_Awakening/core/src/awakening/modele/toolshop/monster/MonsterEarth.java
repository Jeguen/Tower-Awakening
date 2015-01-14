package awakening.modele.toolshop.monster;

import ta.shape3D.mesh.MeshTA;


public class MonsterEarth extends Monster
{
	
	public MonsterEarth(int id, int lifePoint, String name, float speedAttack, float vitesseDeplacement, boolean visible, String facSheet,
	            int buildCost, int gainGold, int damage)
	{
		super(id, lifePoint, name, speedAttack, vitesseDeplacement, visible, facSheet, buildCost, gainGold, damage);
	}
	public MonsterEarth(MeshTA m)
	{
		super(m);
	}
	public MonsterEarth(String string)
	{
		super(string);
	}
	
	@Override
	public void crazyMove()
	{
	}
	
	@Override
	public void normalMove()
	{
		float deltaX = Math.abs(getX() - path.get(boxDestination).getCoordX());
		float deltaY = Math.abs(getZ() - path.get(boxDestination).getCoordY());
		if(deltaX * deltaX + deltaY * deltaY <0.2f)
		{
			this.setAbsolutePosition(path.get(boxDestination).getCoordX(), 0, path.get(boxDestination).getCoordY());
			if(boxDestination<path.size()-1)
			{
				setActualBox(path.get(boxDestination));
				boxDestination++;
				System.out.println("boxActuel : " + boxActuel.getCoordX() + " " + boxActuel.getCoordY());

				dx = (path.get(boxDestination).getCoordX() - this.getX()) / 100f;
				dy = (path.get(boxDestination).getCoordY() - this.getZ()) / 100f;
			}
			else 
			{
				isArrived=true;
			}
		}
		else if(boxDestination>0)
		{
			this.translate(dx, 0, dy);
		}
		else
		{
			if(path.size()>1)
			{
				boxDestination++;
				dx = (path.get(boxDestination).getCoordX() - this.getX()) / 100f;
				dy = (path.get(boxDestination).getCoordY() - this.getZ()) / 100f;
			}
			else 
			{
				isArrived=true;
			}
		}
	}
}
