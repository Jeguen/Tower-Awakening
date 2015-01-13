package awakening.modele.toolshop.monster;

import java.io.File;
import java.util.ArrayList;

import ta.shape3D.mesh.MeshTA;
import awakening.modele.field.Box;

import com.badlogic.gdx.math.Vector2;

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
	protected int boxDestination;
	protected Box boxActuel;
	protected float dx;
	protected float dy;
	public boolean isArrived = false;
	public boolean destinationChanged = false;
	public int randomNumber = (int) (Math.random() * 100);
	// *****************************************
	// ************** CONSTRUCTORS ************
	// *****************************************
	
	public Monster()
    {
		path=new ArrayList<Box>();
    }
	
	public Monster(int id, int lifePoint, String name, float speedAttack, float vitesseDeplacement, boolean visible, String facSheet,
	            int buildCost, int gainGold, int damage)
	{
		this();
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
	public Monster(Vector2 position)
	{
		this();
		this.translate(position.x, 0, position.y);
	}
	public Monster(File meshfile)
	{
		this();
		this.load(meshfile);
	}
	public Monster(MeshTA m)
	{
		this();
		this.copy(m);
		this.copyFeatures(m);
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
	
	public Monster(MeshTA m, Vector2 position)
	{
		this.copy(m);
		this.copyFeatures(m);
		this.translate(position.x, 0, position.y);
	}
	
		public Monster(String fileName) {
			this.load(new File(fileName));
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
			return path.get(boxDestination);
		}
		public Box getBox()
		{
			return boxActuel;
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
		public void addBoxInPath(Box pathBox)
		{
			this.path.add(pathBox);
			boxDestination = 0;
			this.setActualBox(path.get(0));
		}
		public void setActualBox(Box box)
		{
			if(this.boxActuel==null)
				setAbsolutePosition(box.getCoordX(), 0.1f, box.getCoordY());
			else
				this.boxActuel.setFree(true);
			this.boxActuel = box;
			this.boxActuel.setFree(false);
		}
}
