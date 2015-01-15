package awakening.modele.toolshop.tower;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import ta.shape3D.mesh.MeshTA;
import awakening.modele.field.Box;
import awakening.modele.toolshop.monster.Monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

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
	protected ArrayList<Projectile> projectiles;
	protected Vector2 canonPosition= new Vector2(1,0);
	public final static float cos01 = (float) Math.cos(0.01f);
	public final static float sin01 = (float) Math.sin(0.01f);
	public final static float cos001 = (float) Math.cos(0.001f);
	public final static float sin001 = (float) Math.sin(0.001f);
	protected LinkedList<Missile> m;
	protected Texture imageModele;
	
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
	
	public Tower(MeshTA modele, int id, float buildCost, int range, float speedAttaque, int level)
	{
		this.copy(modele);
		this.copyFeatures(modele);	
		this.id = id;
		this.buildCost = buildCost;
		this.range = range;
		this.speedAttaque = speedAttaque;
		this.level = level;
	}
	
	public Tower(MeshTA m)
	{
		this.copy(m);
		this.copyFeatures(m);	
		this.range = 50;
	}
	public Tower(String path)
	{
		this.load(new File(path));
	}
	
	protected Tower()
	{
		
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
		// if a projectile has been deleted, it will be removed by the list
		for(Projectile p: projectiles)
		{
			if(p.isDeleted())
			{
				projectiles.remove(p);
			}
		}
		Projectile p=new Projectile(box.getCoordX(), box.getCoordY(), target, 1);
		projectiles.add(p);
		for(Projectile p1: projectiles)
		{
			// go voir la fonction pour comprendre
			p1.doDamage();
		}
	}
	
	@Override
	public void anime() {
		this.rotate(0,getAnimationRY(),0);
	}
	final public boolean canBeUpgrade()
	{
		return levelUp!=null;
	}
	
	public Box getBox()
	{
		return box;
	}
	public float getBuildCost()
	{
		return buildCost;
	}
	public int getId()
	{
		return id;
	}
	public int getLevel()
	{
		return level;
	}
	public int getRange()
	{
		return range;
	}
	public float getSpeedAttaque()
	{
		return speedAttaque;
	}
	final public Monster getTarget()
	{
		return target;
	}
	
	final public boolean haveATarget()
	{
		return this.target!=null;
	}
	
	final public void loseTarget()
	{
		this.target = null;
	};
	

	@Override
	public boolean mustBeInterrupted() {
		if(!haveATarget()) return true;

		float coefCanon, coefTarget;
		float posX = (target.getX() - getX());
		float posY =(target.getZ()- getZ());
		if(canonPosition.x * posX>=0 && -canonPosition.y * posY>=0)
		{
			if(Math.abs(canonPosition.y)<0.5f && Math.abs(posY)<8f)
			{
					coefTarget = posY / posX;
					int i =1;
					do
					{
						getSousMesh(0).rotate(0, 0.001f*getAnimationRY(), 0);
						float cosA = canonPosition.x;
						canonPosition.x *= cos001;
						canonPosition.x -= sin001*canonPosition.y * getAnimationRY();
						canonPosition.y *= cos001;
						canonPosition.y += cosA*sin001 * getAnimationRY();
						coefCanon = -canonPosition.y / canonPosition.x;
						i++;
					}
					while(!(Math.abs(coefTarget - coefCanon) < 0.2f && Math.signum(posX) == Math.signum(canonPosition.x)
							&& Math.signum(posY) == -Math.signum(canonPosition.y)) && i<15);		
					return true;
	
			}
			coefTarget = posX / posY;
			coefCanon = -canonPosition.x / canonPosition.y;
			return (Math.abs(coefTarget - coefCanon) < 0.4f && Math.signum(posX) == Math.signum(canonPosition.x)
				&& Math.signum(posY) == -Math.signum(canonPosition.y));
		}
		else
		{
			rotate(0,getAnimationRY(),0);
			return false;
		}
	}
	@Override
	public void rotate(float x, float y, float z) {
		getSousMesh(0).rotate(0, 0.01f*y, 0);
		float cosA = canonPosition.x;
		canonPosition.x *= cos01;
		canonPosition.x -= sin01*canonPosition.y * y;
		canonPosition.y *= cos01;
		canonPosition.y += cosA*sin01 * y;
	}


	// *****************************************
	// ************** GETTER SETTER ************
	// *****************************************
	final public void targetMonster(Monster target)
	{
		this.target = target;
	}


	// *****************************************
	// ******** PROCEDURES TO OVERLOAD *********
	// *****************************************
	public boolean testPortee(float x, float z){
		float distanceX =getX() - x;
		float distanceY =getZ() - z;
		return (distanceX * distanceX + distanceY * distanceY < range * range);	
	}

	// *****************************************
	// ****************** OTHER ****************
	// *****************************************
	public void upgrade()
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
	
	
	@Override
	final public void save(File f)
	{
		try {
			DataOutputStream bos = new DataOutputStream(new FileOutputStream(f));
			bos.writeUTF(getClass().getName());
			this.save(bos, f);
			bos.flush();bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(DataOutputStream bos, File f) throws IOException
	{
		super.save(bos,f);
		bos.writeInt(id);
		bos.writeFloat(buildCost);
		bos.writeInt(range);
		bos.writeFloat(speedAttaque);
		bos.writeInt(level);
		if(this.canBeUpgrade())
		{
			bos.writeBoolean(true);
			this.levelUp.save(bos,f);
		}
		else
		{
			bos.writeBoolean(false);
		}	
	}
	
	
	static public Tower loadTower(File f)
	{
		if(f.exists() && f.isFile())
		{
			if(f.getName().endsWith(".mta"))
			{
				try {
					DataInputStream dis = new DataInputStream(new FileInputStream(f));
					if(dis.readUTF().equals(OffensivTower.class.getName()))
					{
						System.out.println("yolo 3");
						OffensivTower retour = new OffensivTower();
						retour.load(f,dis);
						File fileImage = new File(f.getAbsolutePath() + "\\" + retour.name+"ImageModele.png");
						if(fileImage.exists())
						{
							retour.imageModele = new Texture(fileImage.getName());
						}
						dis.close();
						return retour;
					}
					if(dis.readUTF().equals(OffensivTower.class.getName()))
					{
						OffensivTower retour = new OffensivTower();
						retour.load(f,dis);
						dis.close();
						return retour;
					}
					dis.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.err.println("Erreur de chargement Tower");
		return null;
	}
	
	@Override
	public void load(File f, DataInputStream dis) throws IOException
	{
		super.load(f, dis);
		id = dis.readInt();
		buildCost = dis.readFloat();
		range = dis.readInt();
		speedAttaque = dis.readFloat();
		level = dis.readInt();
		if(dis.readBoolean())
		{
			try {
				this.levelUp = getClass().newInstance();
				levelUp.load(f, dis);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public void setNextLevel(Tower nextLevel)
	{
		levelUp = nextLevel;
	}
	
	public void copy(Tower towerModele)
	{
		super.copy(towerModele);
		this.buildCost = towerModele.buildCost;
		this.level = towerModele.level;
		this.levelUp = towerModele.levelUp;
		this.range = towerModele.range;
	}

}
