package fr.battleField;
import java.awt.Color;
/**
* 
* @author Corentin Filoche and Lino Tulliez
*
*/
public class BattleFieldWater extends BattleFieldPieceWise<Number, Number>
{
	/**
	** Constructeur
	* 				Initialisation de l'objet
	*/
	public BattleFieldWater()
	{
		couleur=Color.blue;		
	}
	@Override
	public boolean isCompatible(BattleFieldPieceWise<Number, Number> with)
	{
		return false;
	}
	@Override
	public boolean isDestroyable()
	{
		return false;
	}
}
