package awakening.field;

import java.util.ArrayList;

import awakening.toolshop.monster.Monster;

public class Field
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
	private ArrayList<Box> box;
	private ArrayList<Box> spawns;
	private ArrayList<Monster> monsters;
	private ArrayList<Barrier> barriers;
	private Box finishBox;
	private int[] tabCoordX;
	private int[] tabCoordY;
	// *****************************************
	// ************** CONSTRUCTORS ************
	// *****************************************
	public Field(int halfRadiusPolygon, int nbSidePolygon, int border, int nbSpawn, int nbBoxHeight, int nbBoxWidth)
	{
		this.halfRadiusPolygon = halfRadiusPolygon;
		this.nbSidePolygon = nbSidePolygon;
		this.border = border;
		this.nbSpawn = nbSpawn;
		this.nbBoxHeight = nbBoxHeight;
		this.nbBoxWidth = nbBoxWidth;
		tabCoordX = new int[nbSidePolygon];
		tabCoordY = new int[nbSidePolygon];
		// Initialisation of list
		spawns = new ArrayList<Box>();
		monsters = new ArrayList<Monster>();
		barriers = new ArrayList<Barrier>();
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
	public void creerPlateau()
	{
		box = new ArrayList<Box>();
		if (nbSidePolygon == 6)
		{
			boolean impair = true;
			for (int y = 2 * halfRadiusPolygon + border; y < nbBoxHeight * 3 * halfRadiusPolygon + 2 * border; y += 3 * halfRadiusPolygon)
			{
				if (impair)
				{
					for (int x = 2 * halfRadiusPolygon + border; x < nbBoxWidth * 4 * halfRadiusPolygon + 2 * border; x += 4 * halfRadiusPolygon)
					{
						remplirTableauBox(x, y);
						box.add(new Box(x, y, tabCoordX, tabCoordY));
					}
				}
				else
				{
					for (int x = 4 * halfRadiusPolygon + border; x < nbBoxWidth * 4 * halfRadiusPolygon + 2 * border; x += 4 * halfRadiusPolygon)
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
	public void numeroterDistance(Box arrivee)
	{
		boolean bloque = true;
		int totalBox = 0;
		ArrayList<Box> tabBoxActuelles = new ArrayList<Box>();
		// On met toutes les Boxs à -1
		for (Box b : box)
		{
			b.setRange(-1);
		}
		// On place la Box centrale à  0
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
				if (b.getFieldType().equals(Box.FIELD_EARTH) || b.getFieldType().equals(Box.FIELD_SPAWNS))
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
			while (totalBox < nbBoxHeight * nbBoxWidth && !bloque)
			{
				bloque = true;
				for (Box b : tabBoxActuelles)
				{
					for (Box b1 : box)
					{
						if ((b1.getCoordX() - b.getCoordX()) * (b1.getCoordX() - b.getCoordX()) + (b1.getCoordY() - b.getCoordY())
						            * (b1.getCoordY() - b.getCoordY()) < (4 * halfRadiusPolygon + 2) * (4 * halfRadiusPolygon + 2))
						{
							if (b.getFieldType().equals(Box.FIELD_EARTH) || b.getFieldType().equals(Box.FIELD_SPAWNS))
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
								b.setRange(-2);
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
	public void addMonster(Monster m)
	{
		if (spawns.size() != 0)
		{
			// Random number between 0 and the size of the list which
			// contains spawns
			int randomNumber = (int) (Math.random() * ((spawns.size() - 1) + 1));
			m.setBox(spawns.get(randomNumber));
			monsters.add(m);
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
		ArrayList<Box> PossibleBox = new ArrayList<Box>();
		// Refers to the current box on which it is working
		Box currentBox;
		// For each monster
		for (Monster m : monsters)
		{
			// Clean son chemin (on recalcule Ã  chaque fois)
			m.getPath().clear();
			// On ajoute la case sur lequel le monstre est (pour les
			// calculs)
			m.getPath().add(m.getBox());
			// On def la case actuelle Ã  celle du monstre
			currentBox = m.getBox();
			do
			{
				// On parcourt toutes les cases et on filtre celle qui
				// sont autour et celles qui sont -1 en distance
				for (Box c : box)
				{
					if ((currentBox.getCoordX() - c.getCoordX()) * (currentBox.getCoordX() -c.getCoordX()) + (currentBox.getCoordY() - c.getCoordY())
					            * (currentBox.getCoordY() - c.getCoordY()) < (4 * halfRadiusPolygon + 2) * (4 * halfRadiusPolygon + 2))
					
					{
						if (currentBox.getRange() == c.getRange() + 1)
						{
							// On ajoute ces POSSIBLES cases
							// (entre 1 et 3 normalement)
							PossibleBox.add(c);
						}
					}
				}
				// On dÃ©f un nombre rang d'homme entre 0 et le nombre
				// de case dans la liste -1 (en gros pour choisir une
				// des cases dans la liste)
				int randomNumber = (int) (Math.random() * 100) % (PossibleBox.size());
				// On get
				currentBox = PossibleBox.get(randomNumber);
				// On ajoute la case au chemin
				m.getPath().add(currentBox);
				// On nettoye la liste "temp" pour les cases possibles
				// pour la réutiliser derriere
				PossibleBox.clear();
			}
			// On fait ca tant que la case actuelle n'est pas la case
			// finale (UNIQUE)
			while (!(finishBox.equals(currentBox)));
		}
	}
	// *****************************************
	// ********** GETTERS & SETTERS ************
	// *****************************************
	public int getHalfRadiusPolygon()
	{
		return halfRadiusPolygon;
	}
	public int getNbSidePolygon()
	{
		return nbSidePolygon;
	}
	public int getBorder()
	{
		return border;
	}
	public int getNbSpawn()
	{
		return nbSpawn;
	}
	public int getNbBoxHeight()
	{
		return nbBoxHeight;
	}
	public int getNbBoxWidth()
	{
		return nbBoxWidth;
	}
	public ArrayList<Box> getBox()
	{
		return box;
	}
	public ArrayList<Box> getSpawns()
	{
		return spawns;
	}
	public ArrayList<Monster> getMonsters()
	{
		return monsters;
	}
	public ArrayList<Barrier> getBarriers()
	{
		return barriers;
	}
	public Box getFinishBox()
	{
		return finishBox;
	}
	public int[] getTabCoordX()
	{
		return tabCoordX;
	}
	public int[] getTabCoordY()
	{
		return tabCoordY;
	}
	public void setHalfRadiusPolygon(int halfRadiusPolygon)
	{
		this.halfRadiusPolygon = halfRadiusPolygon;
	}
	public void setNbSidePolygon(int nbSidePolygon)
	{
		this.nbSidePolygon = nbSidePolygon;
	}
	public void setBorder(int border)
	{
		this.border = border;
	}
	public void setNbSpawn(int nbSpawn)
	{
		this.nbSpawn = nbSpawn;
	}
	public void setNbBoxHeight(int nbBoxHeight)
	{
		this.nbBoxHeight = nbBoxHeight;
	}
	public void setNbBoxWidth(int nbBoxWidth)
	{
		this.nbBoxWidth = nbBoxWidth;
	}
	public void setBox(ArrayList<Box> box)
	{
		this.box = box;
	}
	public void setSpawns(ArrayList<Box> spawns)
	{
		this.spawns = spawns;
	}
	public void setMonsters(ArrayList<Monster> monsters)
	{
		this.monsters = monsters;
	}
	public void setBarriers(ArrayList<Barrier> barriers)
	{
		this.barriers = barriers;
	}
	public void setFinishBox(Box finishBox)
	{
		this.finishBox = finishBox;
	}
	public void setTabCoordX(int[] tabCoordX)
	{
		this.tabCoordX = tabCoordX;
	}
	public void setTabCoordY(int[] tabCoordY)
	{
		this.tabCoordY = tabCoordY;
	}
}
