package awakening.modele.toolshop.monster;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ta.shape3D.mesh.MeshTA;
import awakening.modele.field.Box;
import awakening.modele.toolshop.tower.OffensivTower;
import awakening.modele.toolshop.tower.Tower;

import com.badlogic.gdx.graphics.Texture;
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
	protected float buildCost;
	protected int gainGold;
	protected boolean crazyStatus;
	protected int damage;
	protected ArrayList<Box> path;
	protected int boxDestination;
	protected Box boxActuel;
	protected float dx;
	protected float dy;
	protected int coordX;
	protected int coordY;
	public boolean isArrived = false;
	public boolean destinationChanged = false;
	public int randomNumber = (int) (Math.random() * 100);
	private boolean alive=true;
	protected Texture imageModele;
	// *****************************************
	// ************** CONSTRUCTORS ************
	// *****************************************
	
	public Monster()
    {
		path=new ArrayList<Box>();
    }
	
	public Monster(File meshfile)
	{
		this();
		this.load(meshfile);
	}
	public Monster(int id, int lifePoint, String name, float speedAttack, float vitesseDeplacement, boolean visible, String facSheet,
	            float buildCost, int gainGold, int damage)
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
	
	public Monster(MeshTA m)
	{
		this();
		this.copy(m);
		this.copyFeatures(m);
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
	public Monster(Vector2 position)
	{
		this();
		this.translate(position.x, 0, position.y);
	}
	public void addBoxInPath(Box pathBox)
	{
		this.path.add(pathBox);
		boxDestination = 0;
		this.setActualBox(path.get(0));
	}
	
	// *****************************************
	// ************** PROCEDURES **************
	// *****************************************
	@Override
	public Object clone()
	{
		if (this.getClass().getName().equals("MonstreAerien"))
		{
			return new FlyingMonster(id, lifePoint, name, speedAttack, vitesseDeplacement, visible, facSheet, buildCost, gainGold,
			            damage);
		}
		else
		{
			return new MonsterEarth(id, lifePoint, name, speedAttack, vitesseDeplacement, visible, facSheet, buildCost, gainGold,
			            damage);
		}
	}
	// turn boolean if monster dies after taking damages
	public void takeDamage(float damageValue)
	{
		lifePoint=(int) (lifePoint-damageValue);
		if(lifePoint<0)
		{
			alive=false;
		}
	}
	
		public abstract void crazyMove();

		public Box getBox()
		{
			return boxActuel;
		}
		public float getBuildCost()
		{
			return buildCost;
		}
		public int getDamage()
		{
			return damage;
		}
		public Box getDestination()
		{
			return path.get(boxDestination);
		}
		
		public boolean isAlive() {
			return alive;
		}

		public void setAlive(boolean alive) {
			this.alive = alive;
		}

		public String getFacSheet()
		{
			return facSheet;
		}
		public int getGainGold()
		{
			return gainGold;
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
		public ArrayList<Box> getPath()
		{
			return path;
		}
		public float getSpeedAttack()
		{
			return speedAttack;
		}
		public float getVitesseDeplacement()
		{
			return vitesseDeplacement;
		}
		public boolean isCrazyStatus()
		{
			return crazyStatus;
		}
		public boolean isVisible()
		{
			return visible;
		}
		// ***********************************************
		// ********** ABSTRACTS PROCEDURES **************
		// ***********************************************
		public abstract void normalMove();
		public void setActualBox(Box box)
		{
			if(this.boxActuel==null)
				setAbsolutePosition(box.getCoordX(), 0.1f, box.getCoordY());
			else
				this.boxActuel.setFree(true);
			this.boxActuel = box;
			this.boxActuel.setFree(false);
		}
		public void setBuildCost(float buildCost)
		{
			this.buildCost = buildCost;
		}
		public void setCrazyStatus(boolean crazyStatus)
		{
			this.crazyStatus = crazyStatus;
		}
		public void setDamage(int damage)
		{
			this.damage = damage;
		}
		public void setFacSheet(String facSheet)
		{
			this.facSheet = facSheet;
		}
		public void setGainGold(int gainGold)
		{
			this.gainGold = gainGold;
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
		public void setVisible(boolean visible)
		{
			this.visible = visible;
		}
		public void setVitesseDeplacement(float vitesseDeplacement)
		{
			this.vitesseDeplacement = vitesseDeplacement;
		}

		public int getCoordX() {
			return coordX;
		}

		public void setCoordX(int coordX) {
			this.coordX = coordX;
		}

		public int getCoordY() {
			return coordY;
		}

		public void setCoordY(int coordY) {
			this.coordY = coordY;
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
		}
		
		
		static public Monster loadMonster(File f)
		{
			if(f.exists() && f.isFile())
			{
				if(f.getName().endsWith(".mta"))
				{
					try {
						DataInputStream dis = new DataInputStream(new FileInputStream(f));
						if(dis.readUTF().equals(MonsterEarth.class.getName()))
						{
							MonsterEarth retour = new MonsterEarth();
							retour.load(f,dis);
							File fileImage = new File(f.getParent() + "\\" + retour.name+"ImageModele.png");
							if(fileImage.exists())
							{
								retour.imageModele = new Texture(f.getParent() + "\\" + retour.name+"ImageModele.png");
							}
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
		}

		public void copy(Monster monsterModele)
		{
			super.copy(monsterModele);
			this.buildCost = monsterModele.buildCost;
		}
		
		public Texture getModeleImage()
		{
			return imageModele;
		}
}
