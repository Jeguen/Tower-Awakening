package fr.battleField;
import java.awt.Color;
/**
* 
* @author Corentin Filoche and Lino Tulliez
*
*/
public class BattleFieldLandscape extends BattleFieldPieceWise<Number, Number>
{
	/**
	** Constructeur
	* 				Initialisation de l'objet
	*/
	public BattleFieldLandscape()
	{
		couleur=Color.green;		
	}
	@Override
	public boolean isCompatible(BattleFieldPieceWise<Number, Number> with)
	{
		if(with.getClass()==BattleFieldRoad.class || with.getClass()==this.getClass())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	@Override
	public boolean isDestroyable()
	{
		return true;
	}
}
