package fr.battleField;
import java.awt.Color;
/**
* 
* @author Corentin Filoche and Lino Tulliez
*
*/
public abstract class BattleFieldPieceWise<X extends Number, Y extends Number>
{
	/**
	* Sa première coordonnée 
	* 				Number
	*/
	protected X x;
	/**
	* Sa deuxième coordonnée 
	* 				Number
	*/
	protected Y y;
	/**
	* Sa couleur 
	* 				Color
	*/
	protected  Color couleur;
	/**
	** Retoune la premiere coordonnée
	* 
	* @return Number
	* 				Retourne le x
	*/
	public X getX()
	{
		return x;
	}
	/**
	** Retoune la deuxieme coordonnée
	* 
	* @return Number
	* 				Retourne le y
	*/
	public Y getY()
	{
		return y;
	}
	/**
	** Retoune la couleur de la case
	* 
	* @return Color
	* 				Retourne la couleur
	*/
	public Color getCouleur()
	{
		return couleur;
	}
	/**
	** Permet de changer la première coordonnée
	* 
	* @param x
	* 				Number, la première coordonnée		
	*/
	public void setX(X x)
	{
		this.x = x;
	}
	/**
	** Permet de changer la deuxième coordonnée
	* 
	* @param y
	* 				Number, la deuxième coordonnée
	*/
	public void setY(Y y)
	{
		this.y = y;
	}
	/**
	** Permet de changer la couleur de la case
	* 
	* @param c
	* 				Color, la couleur à mettre			
	*/
	public void setColor(Color c)
	{
		this.couleur=c;
	}
	/**
	** Permet de tester si une case est compatible avec une autre
	*		Classe Abstraite
	*
	* @param with
	* 				BattleFieldPieceWise
	* 
	* @return Booléen
	* 				Retoune vrai si les cases sont compatibles					
	*/
	public abstract boolean isCompatible(BattleFieldPieceWise<X,Y> with);
	/**
	** Permet de tester si une case est destructible
	*		Classe Abstraite
	*
	* @return Booléen
	* 				Retoune vrai si la case est destructible				
	*/
	public abstract boolean isDestroyable();
}
