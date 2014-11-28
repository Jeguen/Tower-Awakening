package fr.battleField;
import java.util.HashMap;
/**
* 
* @author Corentin Filoche and Lino Tulliez
*
*/
// PAS REUSSIT A SUPPRIMER
@SuppressWarnings("hiding")
public class BattleFieldMatrix<BattleFieldPieceWise>
{
	/**
	** Sa matrice
	*/				
	private HashMap<Integer, HashMap<Integer, BattleFieldPieceWise> > matrice;
	/**
	** Constructeur
	*				Initialisation de la matrice
	*/
	public BattleFieldMatrix()
	{
		//lignenitialisation de la matrice
		matrice=new HashMap<Integer, HashMap<Integer, BattleFieldPieceWise> >();
	}
	/**
	** Retourne la matrice de l'objet BattleFieldMatric
	* 
	* @return HashMap<Integer, HashMap<Integer, BattleFieldPieceWise>>
	* 				Retourne la matrice
	*/
	public HashMap<Integer, HashMap<Integer, BattleFieldPieceWise>> getMatrice()
	{
		return matrice;
	}
	/**
	** Test si un  élément existe dans la matrice
	*    @param ligne
	*    			Numéro de la ligne
	*    @param colonne
	*    			Numéro de la colonne
	*    @return booléen
	*    			Retourne vrai si l'élément existe dans la matrice
	*/
	public boolean elementExists(int ligne,int colonne)
	{
		if(matrice.get(ligne)==null)
		{
			return false;
		}
		else
		{
			return matrice.get(ligne).get(colonne)!=null;
		}
	}
	/**
	** Retourne un élément dont les coordonnées ont été données en paramètre
	*    @param ligne
	*    			Numéro de la ligne
	*    @param colonne
	*    			Numéro de la colonne
	*    @return BattleFieldPieceWise
	*    			Retourne l'élément qui se situe dans la matrice
	*/
	public BattleFieldPieceWise getElement(int ligne,int colonne)
	{
		// On utilise la fonction elementExists
		if(!elementExists(ligne,colonne))
		{
			return null;
		}
		else
		{
			return matrice.get(ligne).get(colonne);
		}
	}
	/**
	** Permet de remplir la matrice avec une valeur aux coordonnées passées en paramètre
	*    @param ligne
	*    			Numéro de la ligne
	*    @param colonne
	*    			Numéro de la colonne
	*    @param val
	*    			L'élément qu'on veut mettre dans la matrice (TYPE=BattleFieldPieceWise)
	*    @throws Exception 
	*/
	public void setElement(int ligne,int colonne, BattleFieldPieceWise val) throws Exception
	{
		if(ligne<0 || colonne<0)
		{
			throw new Exception();
		}
		// Si la ligne n'existe pas alorslignel faut la créer
		if(matrice.get(ligne)==null)
		{
			HashMap<Integer, BattleFieldPieceWise> matColonne = new HashMap<Integer, BattleFieldPieceWise>();
			matColonne.put(colonne,val);
			matrice.put(ligne, matColonne);
		}
		else
		{
			matrice.get(ligne).put(colonne, val);
		}
	}
	/**
	** Permet de supprimer un élément (En fait on met à null tout simplement)
	*    @param ligne
	*    			Numéro de la ligne
	*    @param colonne
	*    			Numéro de la colonne			
	*    @throws Exception 
	*/
	public void suppElement(int ligne,int colonne) throws Exception
	{
		this.setElement(ligne, colonne, null);
	}
}
