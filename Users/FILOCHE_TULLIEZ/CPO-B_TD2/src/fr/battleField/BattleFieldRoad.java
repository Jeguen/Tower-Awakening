package fr.battleField;
import java.awt.Color;
/**
* 
* @author Corentin Filoche and Lino Tulliez
*
*/
public class BattleFieldRoad extends BattleFieldPieceWise<Number, Number>
{
	/**
	** Constructeur
	* 				Initialisation de l'objet
	*/
	public BattleFieldRoad()
	{
		couleur=Color.gray;	
	}
	@Override
	public boolean isCompatible(BattleFieldPieceWise<Number, Number> with)
	{
		if(with.getClass()==BattleFieldLandscape.class || with.getClass()==this.getClass())
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
