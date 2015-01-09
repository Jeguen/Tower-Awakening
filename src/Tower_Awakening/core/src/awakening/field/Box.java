package awakening.field;

import awakening.toolshop.tower.Tower;

public class Box
{
	// **************************************
	// ************** VARIABLES *************
	// **************************************
	// Constant variable to define type of the box
	public final static String FIELD_EARTH = "Earth#12348";
	public final static String FIELD_BARRIER = "Barrier#23565";
	public static final String FIELD_SPAWNS = "Spawns#6541";
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
	private String fieldType;
	// Value of the range to go to the goal
	private int range;
	// Value of the handicap of the box
	private float handicap;
	// Reference to the tower on this box if it exists
	private Tower tower;
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
	public String getFieldType()
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
	public void setFieldType(String fieldType)
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
}
