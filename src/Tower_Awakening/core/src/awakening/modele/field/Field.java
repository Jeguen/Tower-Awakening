package awakening.modele.field;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import ta.shape3D.Triangle3D;
import ta.shape3D.mesh.MeshTA;
import awakening.modele.toolshop.monster.Monster;
import awakening.modele.toolshop.tower.Tower;

import com.badlogic.gdx.graphics.Texture;

public class Field extends MeshTA
{
	// **************************************
	// ************** VARIABLES *************
	// **************************************
	private int halfRadiusPolygon;
	private int nbSidePolygon;
	private int border;
	private int nbSpawn;
	private int nbBoxHeight;
	private int nbBoxWidth;
	private float height;
	private float width;
	private ArrayList<Box> box;
	private ArrayList<Box> spawns;
	private ArrayList<Tower> towers;
	private ArrayList<Monster> monsters;
	private ArrayList<Barrier> barriers;
	private Box finishBox;
	private int[] tabCoordX;
	private int[] tabCoordY;
	private boolean towerExist;
	// *****************************************
	// ************** CONSTRUCTORS ************
	// *****************************************
	public Field(int halfRadiusPolygon, int nbSidePolygon, int border, int nbSpawn, int fieldHeight, int fieldWidth
			, File meshFile)
	{
		this.halfRadiusPolygon = halfRadiusPolygon;
		this.nbSidePolygon = nbSidePolygon;
		this.border = border;
		this.nbSpawn = nbSpawn;
		this.nbBoxHeight =  fieldHeight/(halfRadiusPolygon*3);
		this.nbBoxWidth = fieldHeight/(halfRadiusPolygon*4);
		tabCoordX = new int[nbSidePolygon];
		tabCoordY = new int[nbSidePolygon];
		// Initialisation of list
		spawns = new ArrayList<Box>();
		monsters = new ArrayList<Monster>();
		barriers = new ArrayList<Barrier>();
		if (meshFile!=null)
		{
			this.load(meshFile);
			this.rotate((float)Math.PI/2, 0,0);
			this.translate(0, -1, 0);
		}
	}
	
	public Field(Texture t, float w, float h, String name)
	{
		this();
		this.setName(name);
		this.width = w;
		this.height = h;
		float nbMeshHeight = h/(t.getHeight()/5f);
		float nbMeshWidth = w/(t.getWidth()/5f);
		int divisionHeight = 1, divisionWidth = 1; 
		if(nbMeshHeight>1)
		{
			divisionHeight = Math.round(nbMeshHeight);
		}
		if(nbMeshWidth>1)
		{
			divisionWidth = Math.round(nbMeshWidth);
		}
		float meshWidth = w/divisionWidth;
		float meshHeight = h/divisionHeight;
		for(int i = 0; i<divisionWidth;i++)
			for(int j = 0; j<divisionHeight;j++)
			{
				Triangle3D t1= addTriangle();
				t1.getPoint1().x = i * meshWidth;
				t1.getPoint1().y = j * meshHeight;
				t1.getPoint2().x = (i+1) * meshWidth;
				t1.getPoint2().y = j * meshHeight;
				t1.getPoint3().x = i * meshWidth;
				t1.getPoint3().y = (j+1) * meshHeight;
				Triangle3D t2= addTriangle();
				t2.getPoint1().x = (i+1) * meshWidth;
				t2.getPoint1().y = (j+1) * meshHeight;
				t2.getPoint2().x = (i+1) * meshWidth;
				t2.getPoint2().y = j * meshHeight;
				t2.getPoint3().x = i * meshWidth;
				t2.getPoint3().y = (j+1) * meshHeight;
				t1.getPointTexture1().x = 2;
				t1.getPointTexture1().y = 2;
				t2.getPointTexture1().x = 0;
				t2.getPointTexture1().y = 0;
				t1.getPointTexture2().x = 0;
				t1.getPointTexture2().y = 2;
				t2.getPointTexture2().x = 0;
				t2.getPointTexture2().y = 2;
				t1.getPointTexture3().x = 2;
				t1.getPointTexture3().y = 0;
				t2.getPointTexture3().x = 2;
				t2.getPointTexture3().y = 0;
			}
	}
	public void addMonster(Monster m)
	{
		if (spawns.size() != 0)
		{
			// Random number between 0 and the size of the list which
			// contains spawns
			int randomNumber = (int) (Math.random() * 100)%spawns.size();
			m.setActualBox(spawns.get(randomNumber));
			monsters.add(m);
		}
	}
	public void creerPlateau()
	{
		box = new ArrayList<Box>();
		if (nbSidePolygon == 6)
		{
			boolean impair = true;
			for (int y = 2 * halfRadiusPolygon; y < nbBoxHeight * 3 * halfRadiusPolygon; y += 3 * halfRadiusPolygon)
			{
				if (impair)
				{
					for (int x = 2 * halfRadiusPolygon; x < nbBoxWidth * 4 * halfRadiusPolygon; x += 4 * halfRadiusPolygon)
					{
						remplirTableauBox(x, y);
						box.add(new Box(x, y, tabCoordX, tabCoordY));
					}
				}
				else
				{
					for (int x = 4 * halfRadiusPolygon; x < nbBoxWidth * 4 * halfRadiusPolygon + 2 * border; x += 4 * halfRadiusPolygon)
					{
						remplirTableauBox(x, y);
						box.add(new Box(x, y, tabCoordX, tabCoordY));
					}
				}
				impair = !impair;
			}
		}
		else
		{
			for (int x = 2 * halfRadiusPolygon + border; x < nbBoxHeight * 4 * halfRadiusPolygon + border; x += 4 * halfRadiusPolygon)
			{
				for (int y = 2 * halfRadiusPolygon + border; y < nbBoxWidth * 4 * halfRadiusPolygon + border; y += 4 * halfRadiusPolygon)
				{
					remplirTableauBox(x, y);
					box.add(new Box(x, y, tabCoordX, tabCoordY));
				}
			}
		}
	}
	// *************
	// Don't Touch
	// *************
	// Permet de trouver le chemin du monstre
	// Pour chaque monstre, on calcule les cases de son chemin et on remplit
	// son tableau du chemin
	// Maintenant chaque monstre pourra prendre un chemin "unique" car les
	// chemins possibles pour chaque case
	// Sont choisis alÃ©atoirement
	// *************
	public void findPathMonster()
	{
		// List which contains possible box for the path
		ArrayList<Box> possibleBox = new ArrayList<Box>();
		// Refers to the current box on which it is working
		Box currentBox;
		// For each monster
		synchronized (monsters) {
			for (Monster m : monsters)
				{
				// Clean son chemin (on recalcule à chaque fois)
				m.getPath().clear();
				// On ajoute la case sur lequel le monstre est (pour les
				// calculs)
				m.addBoxInPath(m.getBox());
				// On def la case actuelle à celle du monstre
				currentBox = m.getBox();
				do
				{
					// On parcourt toutes les BoxList1 et on filtre celle
					// qui
					// sont autour et celles qui sont -1 en distance
					for (Box c : box)
					{
						if ((currentBox.getCoordX() - c.getCoordX()) * (currentBox.getCoordX() - c.getCoordX())
						            + (currentBox.getCoordY() - c.getCoordY()) * (currentBox.getCoordY() - c.getCoordY()) < (4 * halfRadiusPolygon + 2)
						            * (4 * halfRadiusPolygon + 2))
						{
							if (currentBox.getRange() == c.getRange() + 1)
							{
								// On ajoute ces POSSIBLES BoxList1
								// (entre 1 et 3 normalement
								if (possibleBox.size() == 0)
								{
									possibleBox.add(c);
								}
								else
								{
									if (c.getNbTargeted() < possibleBox.get(0).getNbTargeted())
									{
										possibleBox.clear();
										possibleBox.add(c);
									}
									else if (c.getNbTargeted() == possibleBox.get(0).getNbTargeted())
									{
										possibleBox.add(c);
									}
								}
							}
						}
					}
					// On déf un nombre rang d'homme entre 0 et le nombre
					// de case dans la liste -1 (en gros pour choisir une
					// des BoxList1 dans la liste)
					if(possibleBox.size()>0)
					{
						int randomNumber = m.randomNumber % (possibleBox.size());
						// On get
						currentBox = possibleBox.get(randomNumber);
						// On ajoute la case au chemin
						m.addBoxInPath(currentBox);
						// On nettoye la liste "temp" pour les BoxList1
						// possibles
						// pour la r�utiliser derriere
						possibleBox.clear();
					}
				}
				// On fait ca tant que la case actuelle n'est pas la case
				// finale (UNIQUE)
				while (!(finishBox.equals(currentBox)));
			}
		}
	}
	public void findPathMonster(Monster m)
	{
		// List which contains possible box for the path
		ArrayList<Box> possibleBox = new ArrayList<Box>();
		// Refers to the current box on which it is working
		Box currentBox;
		
		// Clean son chemin (on recalcule à chaque fois)
		m.getPath().clear();
		// On ajoute la case sur lequel le monstre est (pour les
		// calculs)
		m.getPath().add(m.getBox());
		// On def la case actuelle à celle du monstre
		currentBox = m.getBox();
		do
		{
			// On parcourt toutes les BoxList1 et on filtre celle
			// qui
			// sont autour et celles qui sont -1 en distance
			for (Box c : box)
			{
				if ((currentBox.getCoordX() - c.getCoordX()) * (currentBox.getCoordX() - c.getCoordX())
				            + (currentBox.getCoordY() - c.getCoordY()) * (currentBox.getCoordY() - c.getCoordY()) < (4 * halfRadiusPolygon + 2)
				            * (4 * halfRadiusPolygon + 2))
				{
					if (currentBox.getRange() == c.getRange() + 1)
					{
						// On ajoute ces POSSIBLES BoxList1
						// (entre 1 et 3 normalement
						if (possibleBox.size() == 0)
						{
							possibleBox.add(c);
						}
						else
						{
							if (c.getNbTargeted() < possibleBox.get(0).getNbTargeted())
							{
								possibleBox.clear();
								possibleBox.add(c);
							}
							else if (c.getNbTargeted() == possibleBox.get(0).getNbTargeted())
							{
								possibleBox.add(c);
							}
						}
					}
				}
			}
			// On déf un nombre rang d'homme entre 0 et le nombre
			// de case dans la liste -1 (en gros pour choisir une
			// des BoxList1 dans la liste)
			int randomNumber = (int) (Math.random() * 100) % (possibleBox.size());
			// On get
			currentBox = possibleBox.get(randomNumber);
			// On ajoute la case au chemin
			m.getPath().add(currentBox);
			// On nettoye la liste "temp" pour les BoxList1
			// possibles
			// pour la r�utiliser derriere
			possibleBox.clear();
		}
		// On fait ca tant que la case actuelle n'est pas la case
		// finale (UNIQUE)
		while (!(finishBox.equals(currentBox)));
	}
	public ArrayList<Barrier> getBarriers()
	{
		return barriers;
	}
	public int getBorder()
	{
		return border;
	}
	public ArrayList<Box> getBox()
	{
		return box;
	}
	public int getBoxIndexByPosition(float x, float y)
	{
		float yBox = Math.round((y/(3*halfRadiusPolygon)) - 0.7f);
		float xBox = Math.round(((x - (y%2)*(2*halfRadiusPolygon))/(4*halfRadiusPolygon)));
		return (int)(xBox + yBox * (getNbBoxWidth()) + yBox/2f);
	}
	public Box getFinishBox()
	{
		return finishBox;
	}
	// *****************************************
	// ********** GETTERS & SETTERS ************
	// *****************************************
	public int getHalfRadiusPolygon()
	{
		return halfRadiusPolygon;
	}
	public ArrayList<Monster> getMonsters()
	{
		return monsters;
	}
	public int getNbBoxHeight()
	{
		return nbBoxHeight;
	}
	public int getNbBoxWidth()
	{
		return nbBoxWidth;
	}
	public int getNbSidePolygon()
	{
		return nbSidePolygon;
	}
	public int getNbSpawn()
	{
		return nbSpawn;
	}
	public ArrayList<Box> getSpawns()
	
	{
		return spawns;
	}
	public int[] getTabCoordX()
	{
		return tabCoordX;
	}
	public int[] getTabCoordY()
	{
		return tabCoordY;
	}
	public boolean getTowerExist()
	{
		return towerExist;
	}
	public void initTargetedBox()
	{
		for (Box b : box)
		{
			b.setNbTargeted(0);
		}
	}
	public void numeroterDistance(Box arrivee)
	{
		boolean bloque = true;
		int totalBox = 0;
		ArrayList<Box> tabBoxActuelles = new ArrayList<Box>();
		// On met toutes les Boxs � -1
		for (Box b : box)
		{
			b.setRange(-1);
		}
		// On place la Box centrale � 0
		arrivee.setRange(0);
		totalBox = 1;
		// On place le premier rang de calcul
		// On prend la Box centrale et on recherche tous ces voisins en
		// leur mettant 1 comme distance
		for (Box b : box)
		{
			if ((arrivee.getCoordX() - b.getCoordX()) * (arrivee.getCoordX() - b.getCoordX()) + (arrivee.getCoordY() - b.getCoordY())
			            * (arrivee.getCoordY() - b.getCoordY()) < (4 * halfRadiusPolygon + 2) * (4 * halfRadiusPolygon + 2)
			            && (arrivee.getCoordX() - b.getCoordX()) * (arrivee.getCoordX() - b.getCoordX())
			                        + (arrivee.getCoordY() - b.getCoordY()) * (arrivee.getCoordY() - b.getCoordY()) > 10)
			{
				if (b.getFieldType()>0)
				{
					b.setRange(1);
					bloque = false;
				}
				else
				{
					b.setRange(-2);
				}
				totalBox += 1;
				tabBoxActuelles.add(b);
			}
		}
		if (bloque)
		{
			System.out.println("BLOQUE1");
		}
		else
		{
			ArrayList<Box> tab = new ArrayList<Box>();
			while (totalBox < box.size() && !bloque)
			{
				bloque = true;
				for (Box b : tabBoxActuelles)
				{
					for (Box b1 : box)
					{
						if ((b1.getCoordX() - b.getCoordX()) * (b1.getCoordX() - b.getCoordX()) + (b1.getCoordY() - b.getCoordY())
						            * (b1.getCoordY() - b.getCoordY()) < (4 * halfRadiusPolygon + 2) * (4 * halfRadiusPolygon + 2))
						{
							if (b.getFieldType()>0)
							{
								if (b1.getRange() == -1)
								{
									b1.setRange(b.getRange() + 1);
									totalBox += 1;
									tab.add(b1);
									bloque = false;
								}
							}
							else
							{
								b.setRange(b.getFieldType());
							}
						}
					}
				}
				tabBoxActuelles.clear();
				for (Box b : tab)
				{
					tabBoxActuelles.add(b);
				}
				tab.clear();
			}
		}
	}
	// *****************************************
	// ************** PROCEDURES **************
	// *****************************************
	public void remplirTableauBox(int x, int y)
	{
		if (nbSidePolygon == 6)
		{
			tabCoordX[0] = x;
			tabCoordX[1] = x + 2 * halfRadiusPolygon;
			tabCoordX[2] = x + 2 * halfRadiusPolygon;
			tabCoordX[3] = x;
			tabCoordX[4] = x - 2 * halfRadiusPolygon;
			tabCoordX[5] = x - 2 * halfRadiusPolygon;
			tabCoordY[0] = y + 2 * halfRadiusPolygon;
			tabCoordY[1] = y + halfRadiusPolygon;
			tabCoordY[2] = y - halfRadiusPolygon;
			tabCoordY[3] = y - 2 * halfRadiusPolygon;
			tabCoordY[4] = y - halfRadiusPolygon;
			tabCoordY[5] = y + halfRadiusPolygon;
		}
		else
		{
			tabCoordX[0] = x + 2 * halfRadiusPolygon;
			tabCoordX[1] = x + 2 * halfRadiusPolygon;
			tabCoordX[2] = x - 2 * halfRadiusPolygon;
			tabCoordX[3] = x - 2 * halfRadiusPolygon;
			tabCoordY[0] = y - 2 * halfRadiusPolygon;
			tabCoordY[1] = y + 2 * halfRadiusPolygon;
			tabCoordY[2] = y + 2 * halfRadiusPolygon;
			tabCoordY[3] = y - 2 * halfRadiusPolygon;
		}
	}
	public void setBarriers(ArrayList<Barrier> barriers)
	{
		this.barriers = barriers;
	}
	public void setBorder(int border)
	{
		this.border = border;
	}
	public void setBox(ArrayList<Box> box)
	{
		this.box = box;
	}
	public void setFinishBox(Box finishBox)
	{
		this.finishBox = finishBox;
	}
	public void setHalfRadiusPolygon(int halfRadiusPolygon)
	{
		this.halfRadiusPolygon = halfRadiusPolygon;
	}
	public void setMonsters(ArrayList<Monster> monsters)
	{
		this.monsters = monsters;
	}
	public void setNbBoxHeight(int nbBoxHeight)
	{
		this.nbBoxHeight = nbBoxHeight;
	}
	public void setNbBoxWidth(int nbBoxWidth)
	{
		this.nbBoxWidth = nbBoxWidth;
	}
	public void setNbSidePolygon(int nbSidePolygon)
	{
		this.nbSidePolygon = nbSidePolygon;
	}
	public void setNbSpawn(int nbSpawn)
	{
		this.nbSpawn = nbSpawn;
	}
	public void setSpawns(ArrayList<Box> spawns)
	{
		this.spawns = spawns;
	}
	public void setTabCoordX(int[] tabCoordX)
	{
		this.tabCoordX = tabCoordX;
	}
	public void setTabCoordY(int[] tabCoordY)
	{
		this.tabCoordY = tabCoordY;
	}
	public void setTowerExist(boolean b)
	{
		this.towerExist = b;
	}
	public void updateTargetedBoxAdd()
	{
		LinkedList<Box> BoxList1 = new LinkedList<Box>();
		LinkedList<Box> BoxList12 = new LinkedList<Box>();
		LinkedList<Box> BoxListDone = new LinkedList<Box>();
		boolean par = false;
		int cpt = 1;
		Box b = towers.get(towers.size() - 1).getBox();
		if (b.getTower() != null)
		{
			for (Box b1 : box)
			{
				if ((b.getCoordX() - b1.getCoordX()) * (b.getCoordX() - b1.getCoordX()) + (b.getCoordY() - b1.getCoordY())
				            * (b.getCoordY() - b1.getCoordY()) < (4 * halfRadiusPolygon + 2) * (4 * halfRadiusPolygon + 2))
				{
					b1.setNbTargeted(b1.getNbTargeted() + 1);
					BoxList1.add(b1);
					BoxListDone.add(b1);
				}
			}
			while (b.getTower().getRange() != cpt)
			{
				if (!par)
				{
					BoxList12.clear();
					for (Box b2 : BoxList1)
					{
						for (Box b3 : box)
						{
							if ((b3.getCoordX() - b2.getCoordX()) * (b3.getCoordX() - b2.getCoordX())
							            + (b3.getCoordY() - b2.getCoordY()) * (b3.getCoordY() - b2.getCoordY()) < (4 * halfRadiusPolygon + 2)
							            * (4 * halfRadiusPolygon + 2)
							            && !BoxListDone.contains(b3))
							{
								b3.setNbTargeted(b3.getNbTargeted() + 1);
								BoxList12.add(b3);
								BoxListDone.add(b3);
							}
						}
					}
					cpt++;
					par = !par;
				}
				else
				{
					BoxList1.clear();
					for (Box b2 : BoxList12)
					{
						for (Box b3 : box)
						{
							if ((b3.getCoordX() - b2.getCoordX()) * (b3.getCoordX() - b2.getCoordX())
							            + (b3.getCoordY() - b2.getCoordY()) * (b3.getCoordY() - b2.getCoordY()) < (4 * halfRadiusPolygon + 2)
							            * (4 * halfRadiusPolygon + 2)
							            && !BoxListDone.contains(b3))
							{
								b3.setNbTargeted(b3.getNbTargeted() + 1);
								BoxList1.add(b3);
								BoxListDone.add(b3);
							}
						}
					}
					cpt++;
					par = !par;
				}
			}
		}
	}
	
	public void updateTargetedBoxRemove()
	{
		LinkedList<Box> BoxList1 = new LinkedList<Box>();
		LinkedList<Box> BoxList12 = new LinkedList<Box>();
		LinkedList<Box> BoxListDone = new LinkedList<Box>();
		boolean par = false;
		int cpt = 1;
		Box b = towers.get(towers.size() - 1).getBox();
		if (b.getTower() != null)
		{
			for (Box b1 : box)
			{
				if ((b.getCoordX() - b1.getCoordX()) * (b.getCoordX() - b1.getCoordX()) + (b.getCoordY() - b1.getCoordY())
				            * (b.getCoordY() - b1.getCoordY()) < (4 * halfRadiusPolygon + 2) * (4 * halfRadiusPolygon + 2))
				{
					b1.setNbTargeted(b1.getNbTargeted() - 1);
					BoxList1.add(b1);
					BoxListDone.add(b1);
				}
			}
			while (b.getTower().getRange() != cpt)
			{
				if (!par)
				{
					BoxList12.clear();
					for (Box b2 : BoxList1)
					{
						for (Box b3 : box)
						{
							if ((b3.getCoordX() - b2.getCoordX()) * (b3.getCoordX() - b2.getCoordX())
							            + (b3.getCoordY() - b2.getCoordY()) * (b3.getCoordY() - b2.getCoordY()) < (4 * halfRadiusPolygon + 2)
							            * (4 * halfRadiusPolygon + 2)
							            && !BoxListDone.contains(b3))
							{
								b3.setNbTargeted(b3.getNbTargeted() - 1);
								BoxList12.add(b3);
								BoxListDone.add(b3);
							}
						}
					}
					cpt++;
					par = !par;
				}
				else
				{
					BoxList1.clear();
					for (Box b2 : BoxList12)
					{
						for (Box b3 : box)
						{
							if ((b3.getCoordX() - b2.getCoordX()) * (b3.getCoordX() - b2.getCoordX())
							            + (b3.getCoordY() - b2.getCoordY()) * (b3.getCoordY() - b2.getCoordY()) < (4 * halfRadiusPolygon + 2)
							            * (4 * halfRadiusPolygon + 2)
							            && !BoxListDone.contains(b3))
							{
								b3.setNbTargeted(b3.getNbTargeted() - 1);
								BoxList1.add(b3);
								BoxListDone.add(b3);
							}
						}
					}
					cpt++;
					par = !par;
				}
			}
		}
	}
	
	public void save(File f)
	{
		try {
			DataOutputStream bos = new DataOutputStream(new FileOutputStream(f));
			bos.writeUTF("FieldTowerAwakening");
			this.save(bos, f);
			bos.flush();bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(DataOutputStream dos, File f) throws IOException
	{
		super.save(dos, f);
		dos.writeFloat(height);
		dos.writeFloat(width);
	}
	
	@Override
	public void load(File f, DataInputStream dis) throws IOException
	{
		super.load(f, dis);
		height = dis.readFloat();
		width = dis.readFloat();
		this.halfRadiusPolygon = 4;
		this.nbSidePolygon = 6;
		this.border = 10;
		this.nbSpawn = 5;
		this.nbBoxHeight =  (int) (height/(halfRadiusPolygon*3));
		this.nbBoxWidth = (int) (width/(halfRadiusPolygon*4));
		tabCoordX = new int[nbSidePolygon];
		tabCoordY = new int[nbSidePolygon];
		// Initialisation of list
		spawns = new ArrayList<Box>();
		monsters = new ArrayList<Monster>();
		barriers = new ArrayList<Barrier>();
	}
	
	static public Field loadTower(File f)
	{
		if(f.exists() && f.isFile())
		{
			if(f.getName().endsWith(".mta"))
			{
				try {
					DataInputStream dis = new DataInputStream(new FileInputStream(f));
					if(dis.readUTF().equals("FieldTowerAwakening"))
					{
						Field retour = new Field();
						retour.load(f,dis);
						retour.rotate((float) +Math.PI/2, 0, 0);
						retour.translate(0, -0.2f, 0);
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
		System.err.println("Erreur de chargement Field");
		return null;
	}
	
	private Field(){
		super();		
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public float getWidth()
	{
		return width;
	}
}
