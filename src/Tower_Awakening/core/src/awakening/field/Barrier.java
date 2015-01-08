package awakening.field;


public class Barrier
{
	// **************************************
	// ************** VARIABLES *************
	// **************************************
	// Reference to the box where this barrier is
	private Box box;
	// *****************************************
	// ************** CONSTRUCTORS *************
	// *****************************************
	public Barrier(Box box)
	{
		this.box=box;
	}
	// *****************************************
	// ********** GETTERS & SETTERS ************
	// *****************************************
	public Box getBox()
	{
		return box;
	}
	public void setBox(Box box)
	{
		this.box = box;
	}
}
