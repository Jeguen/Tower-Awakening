package fr.exception;

public class monException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public void nombreNegative()
	{
		System.out.println("Il ne peut pas y avoir de nombre négatif en coordonnées");
	}
}
