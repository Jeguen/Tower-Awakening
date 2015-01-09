package awakening.field;

import awakening.toolshop.tower.Tower;

public class Box
{
	// **************************************
	// ************** VARIABLES *************
	// **************************************
	// Constant variable to define type of the box
	public final static int FIELD_EARTH = 1;
	public final static int FIELD_BARRIER = -2;
	public static final int FIELD_SPAWNS = 2;
	public static final int FIELD_TOWER = -3;
	// Identification number of the box
	private int id;
	// Total number of created box
	private static int nbCaseCreated = 0;
	// Coordinates of the center of the box
	private int coordX;
	private int coordY;
	// Array of coordinates used to draw hexagon box
	private int[] tabCoordX = new int[6];
	private int[] tabCoordY = new int[6];
	// String which contains the type of the box
	private int fieldType;
	// Value of the range to go to the goal
	private int range;
	// Value of the handicap of the box
	private float handicap;
	// Reference to the tower on this box if it exists
	private Tower tower;
	// Total of tower which can target this box
	private int nbTargeted;
	// *****************************************
	// ************** CONSTRUCTORS *************
	// *****************************************
	public Box(int x, int y, int[] arrayX, int[] arrayY)
	{
		this.id = nbCaseCreated + 1;
		this.coordX = x;
		this.coordY = y;
		// Copy of both array
		for (int i = 0; i < 6; i++)
		{
			this.tabCoordX[i] = arrayX[i];
			this.tabCoordY[i] = arrayY[i];
		}
		// As default, a box is composed of earth
		this.fieldType = FIELD_EARTH;
		// As default, a box has a standard handicap : 1
		this.handicap = 1.2f;
		// As default, a box is no targeted
		this.nbTargeted=0;
	}
	// *****************************************
	// ********** GETTERS & SETTERS ************
	// *****************************************
	public int getID()
	{
		return id;
	}
	public static int getNbCaseCreated()
	{
		return nbCaseCreated;
	}
	public int getCoordX()
	{
		return coordX;
	}
	public int getCoordY()
	{
		return coordY;
	}
	public int[] getTabCoordX()
	{
		return tabCoordX;
	}
	public int[] getTabCoordY()
	{
		return tabCoordY;
	}
	public int getFieldType()
	{
		return fieldType;
	}
	public int getRange()
	{
		return range;
	}
	public float getHandicap()
	{
		return handicap;
	}
	public Tower getTower()
	{
		return tower;
	}
	public int getNbTargeted()
	{
		return  nbTargeted;
	}
	public void setID(int id)
	{
		this.id = id;
	}
	public static void setNbCaseCreated(int nbCaseCreated)
	{
		Box.nbCaseCreated = nbCaseCreated;
	}
	public void setCoordX(int coordX)
	{
		this.coordX = coordX;
	}
	public void setCoordY(int coordY)
	{
		this.coordY = coordY;
	}
	public void setTabCoordX(int[] tabCoordX)
	{
		this.tabCoordX = tabCoordX;
	}
	public void setTabCoordY(int[] tabCoordY)
	{
		this.tabCoordY = tabCoordY;
	}
	public void setFieldType(int fieldType)
	{
		this.fieldType = fieldType;
	}
	public void setRange(int range)
	{
		this.range = range;
	}
	public void setHandicap(float handicap)
	{
		this.handicap = handicap;
	}
	public void setTower(Tower tower)
	{
		this.tower = tower;
	}
	public void setNbTargeted(int nbTargeted)
	{
		this.nbTargeted=nbTargeted;
	}
}
