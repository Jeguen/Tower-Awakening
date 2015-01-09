package awakening.toolshop.tower;

import java.io.File;
import java.util.LinkedList;

import ta.shape3D.Point.Point2D;
import ta.shape3D.mesh.MeshTA;
import awakening.field.Box;
import awakening.toolshop.monster.Monster;

public abstract class Tower extends MeshTA
{
	protected int id;
	protected float buildCost;
	protected int range;
	protected float speedAttaque;
	protected int level;
	protected Tower levelUp;
	protected Monster target;
	protected Box box;
	protected Point2D canonPosition= new Point2D(1,0);
	public final static float cos01 = (float) Math.cos(0.04f);
	public final static float sin01 = (float) Math.sin(0.04f);
	protected LinkedList<Missile> m;
	// *****************************************
	// ************** CONSTRUCTORS ************
	// *****************************************
	public Tower(int id, float buildCost, int range, float speedAttaque, int level)
	{
		super();
		this.id = id;
		this.buildCost = buildCost;
		this.range = range;
		this.speedAttaque = speedAttaque;
		this.level = level;
	}
	
	public Tower(String path)
	{
		this.load(new File(path));
	}
	public Tower(MeshTA m)
	{
		this.copy(m);
		this.copyFeatures(m);	
		this.range = 50;
	}
	
	// *****************************************
	// ************** GETTER SETTER ************
	// *****************************************
	final public void targetMonster(Monster target)
	{
		this.target = target;
	}
	
	final public void loseTarget()
	{
		this.target = null;
	}
	final public Monster getTarget()
	{
		return target;
	}
	
	final public boolean haveATarget()
	{
		return this.target!=null;
	}
	public Box getBox()
	{
		return box;
	}
	public int getId()
	{
		return id;
	}
	public float getBuildCost()
	{
		return buildCost;
	}
	public int getRange()
	{
		return range;
	}
	public float getSpeedAttaque()
	{
		return speedAttaque;
	}
	public int getLevel()
	{
		return level;
	}
	
	// *****************************************
	// ******** PROCEDURES TO OVERLOAD *********
	// *****************************************
	public boolean testPortee(float x, float z){
		float distanceX =getX() - x;
		float distanceY =getZ() - z;
		return (distanceX * distanceX + distanceY * distanceY < range * range);	
	}
	
	public void action(){
		float deltaZ = -(target.getZ()- getZ());
		float deltaX = (target.getX()- getX());
		float rotation=0;
		if(deltaZ>0){
			rotation = 1;
		}
		else if(deltaZ<0)
		{
			rotation = -1;
		}
		else
		{
			if(deltaX * canonPosition.x<0 || Math.abs(deltaZ-canonPosition.y)>0.1f)
			{
				 rotation = 1;
			}
		}
		animationRY(rotation);
	};
	

	// *****************************************
	// ****************** OTHER ****************
	// *****************************************
	final public void upgrade()
	{
		if(canBeUpgrade())
		{
			this.buildCost = levelUp.buildCost;
			this.range = levelUp.range;
			this.speedAttaque = levelUp.speedAttaque;
			this.level++;
			this.levelUp = levelUp.levelUp;
		}
	}
	final public boolean canBeUpgrade()
	{
		return levelUp!=null;
	}


	@Override
	public void anime() {
		this.rotate(0,getAnimationRY(),0);
	}


	@Override
	public boolean mustBeInterrupted() {
		if(!haveATarget()) return true;

		float coefCanon, coefTarget;
		float posX = (target.getX() - getX());
		float posY =(target.getZ()- getZ());
		
		if(Math.abs(canonPosition.y)<0.4f && Math.abs(posY)<10f)
		{
			if(Math.abs(posY/posX)<0.6f && canonPosition.x*posX>0 && Math.abs(canonPosition.y-posY)<5f)
			{
				return true;
			}
			else
				return false;
		}
		coefTarget = posX / posY;
		coefCanon = -canonPosition.x / canonPosition.y;
		return (Math.abs(coefTarget - coefCanon) < 0.2f && Math.signum(posX) == Math.signum(canonPosition.x)
			&& Math.signum(posY) == -Math.signum(canonPosition.y));
	}

	@Override
	public void rotate(float x, float y, float z) {
		getSousMesh(0).rotate(0, 0.04f*y, 0);
		float cosA = canonPosition.x;
		canonPosition.x *= cos01;
		canonPosition.x -= sin01*canonPosition.y * y;
		canonPosition.y *= cos01;
		canonPosition.y += cosA*sin01 * y;
	}
	


}
