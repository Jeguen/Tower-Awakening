package awakening.toolshop.tower;

import java.io.File;
import java.util.LinkedList;

import ta.shape3D.Point.Point2D;
import ta.shape3D.mesh.MeshTA;
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
	protected Point2D canonPosition= new Point2D(1,0);
	public final static float cos01 = (float) Math.cos(0.2f);
	public final static float sin01 = (float) Math.sin(0.2f);
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
		this(MeshTA.load(new File(path)));
	
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
	
	// *****************************************
	// ******** PROCEDURES TO OVERLOAD *********
	// *****************************************
	public boolean testPortee(float x, float z){
		float distanceX =getX() - x;
		float distanceY =getZ() - z;
		return (distanceX * distanceX + distanceY * distanceY < range * range);	
	}
	

	// ***********************************************
	// ********** ABSTRACTS PROCEDURES **************
	// ***********************************************
	public abstract void action();
	
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
		coefTarget = posX / posY;
		coefCanon = -canonPosition.x / canonPosition.y;
		System.out.println(coefTarget + " Coef " + coefCanon);
		return (Math.abs(coefTarget - coefCanon) < 0.75f && Math.signum(posX) == Math.signum(canonPosition.x));
	}

	@Override
	public void rotate(float x, float y, float z) {
		getSousMesh(0).rotate(0, 0.2f*y, 0);
		float cosA = canonPosition.x;
		canonPosition.x *= cos01;
		canonPosition.x -= sin01*canonPosition.y * y;
		canonPosition.y *= cos01;
		canonPosition.y += cosA*sin01 * y;
	}
	


}
