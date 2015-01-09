package awakening.toolshop.monster;

import java.util.ArrayList;

import ta.shape3D.Point.Point2D;
import ta.shape3D.mesh.MeshTA;
import awakening.field.Box;

public abstract class Monster extends MeshTA
{
	public final static String MONSTER_FLYING="air#1234";
	public final static String MONSTER_EARTH="Walk#2654612";
	protected int id;
	protected int lifePoint;
	protected float speedAttack;
	protected float vitesseDeplacement;
	protected boolean visible;
	protected String facSheet;
	protected int buildCost;
	protected int gainGold;
	protected boolean crazyStatus;
	protected int damage;
	protected ArrayList<Box> path;
	protected Box destination;
	protected Box box;
	// *****************************************
	// ************** CONSTRUCTORS ************
	// *****************************************
	public Monster(int id, int lifePoint, String name, float speedAttack, float vitesseDeplacement, boolean visible, String facSheet,
	            int buildCost, int gainGold, int damage)
	{
		super();
		this.id = id;
		this.lifePoint = lifePoint;
		this.name = name;
		this.speedAttack = speedAttack;
		this.vitesseDeplacement = vitesseDeplacement;
		this.visible = visible;
		this.facSheet = facSheet;
		this.buildCost = buildCost;
		this.gainGold = gainGold;
		this.damage = damage;
	}
	public Monster(Point2D position)
	{
		this.translate(position.x, 0, position.y);
	}
	// ***********************************************
	// ********** ABSTRACTS PROCEDURES **************
	// ***********************************************
	public abstract void normalMove();
	public abstract void crazyMove();
	// *****************************************
	// ************** PROCEDURES **************
	// *****************************************
	public Object clone()
	{
		if (this.getClass().getName().equals("MonstreAerien"))
		{
			return (Object) new FlyingMonster(id, lifePoint, name, speedAttack, vitesseDeplacement, visible, facSheet, buildCost, gainGold,
			            damage);
		}
		else
		{
			return (Object) new MonsterEarth(id, lifePoint, name, speedAttack, vitesseDeplacement, visible, facSheet, buildCost, gainGold,
			            damage);
		}
	}
	
	public Monster(MeshTA m, Point2D position)
	{
		this.copy(m);
		this.copyFeatures(m);
		this.translate(position.x, 0, position.y);
	}
	
		// *****************************************
		// ********** GETTERS & SETTERS ************
		// *****************************************
		public int getId()
		{
			return id;
		}
		public int getLifePoint()
		{
			return lifePoint;
		}
		public String getName()
		{
			return name;
		}
		public float getSpeedAttack()
		{
			return speedAttack;
		}
		public float getVitesseDeplacement()
		{
			return vitesseDeplacement;
		}
		public boolean isVisible()
		{
			return visible;
		}
		public String getFacSheet()
		{
			return facSheet;
		}
		public int getBuildCost()
		{
			return buildCost;
		}
		public int getGainGold()
		{
			return gainGold;
		}
		public boolean isCrazyStatus()
		{
			return crazyStatus;
		}
		public int getDamage()
		{
			return damage;
		}
		public ArrayList<Box> getPath()
		{
			return path;
		}
		public Box getDestination()
		{
			return destination;
		}
		public Box getBox()
		{
			return box;
		}
		public void setId(int id)
		{
			this.id = id;
		}
		public void setLifePoint(int lifePoint)
		{
			this.lifePoint = lifePoint;
		}
		public void setSpeedAttack(float speedAttack)
		{
			this.speedAttack = speedAttack;
		}
		public void setVitesseDeplacement(float vitesseDeplacement)
		{
			this.vitesseDeplacement = vitesseDeplacement;
		}
		public void setVisible(boolean visible)
		{
			this.visible = visible;
		}
		public void setFacSheet(String facSheet)
		{
			this.facSheet = facSheet;
		}
		public void setBuildCost(int buildCost)
		{
			this.buildCost = buildCost;
		}
		public void setGainGold(int gainGold)
		{
			this.gainGold = gainGold;
		}
		public void setCrazyStatus(boolean crazyStatus)
		{
			this.crazyStatus = crazyStatus;
		}
		public void setDamage(int damage)
		{
			this.damage = damage;
		}
		public void setPath(ArrayList<Box> path)
		{
			this.path = path;
		}
		public void setDestination(Box destination)
		{
			this.destination = destination;
		}
		public void setBox(Box box)
		{
			this.box = box;
		}
}
