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
	protected String name;
	protected float speedAttack;
	protected float vitesseDeplacement;
	protected boolean visible;
	protected String facSheet;
	protected int buildCost;
	protected int gainGold;
	protected boolean crazyStatus;
	protected int damage;
	protected ArrayList<Box> path;
	protected Box saDestination;
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
}
