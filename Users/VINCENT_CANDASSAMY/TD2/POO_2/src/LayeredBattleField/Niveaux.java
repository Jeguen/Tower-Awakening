package LayeredBattleField;

import java.util.ArrayList;

/**
 * Liste de carte dynamique -
 * Initialiser Niveaux avec init()
 * @author S Firegreen
 *
 */
public class Niveaux {
	
	final public static ArrayList<BattleFieldMatrixPW> listeNiveaux
	= new ArrayList<BattleFieldMatrixPW>(4);

	static public void init() {
		BattleFieldMatrixPW niveau;
		for(int cpt=3; cpt<6;cpt++)
		{
			niveau = new BattleFieldMatrixPW();
			for(int i=0;i<cpt*3;i++)
			{
				for(int j=0;j<cpt*3;j++)
				{
					
					int random = (int) (((Math.random()*100.5f*(j+1)*(i+1)) % 5 )+1);
					boolean permis=true;
					BattleFieldPieceWise caseAjout;
					switch(random)
					{
					case 1: 
						caseAjout = new BattleFieldPieceWise.BattleFieldWater();
						break;
						
					case 2:
						caseAjout = new BattleFieldPieceWise.BattleFieldRoad();
						break;
					
					case 3: 
						caseAjout = new BattleFieldPieceWise.BattleFieldLandScape();
						break;
						
					default:
						caseAjout = null;
						permis=false;
						break;	
					}
					BattleFieldPieceWise caseAdjHaut = niveau.get(i, j-1), caseAdjGauche = niveau.get(i-1, j);
					if(permis)
					{
						if(caseAdjHaut!=null) 
						{
							permis = caseAdjHaut.isCompatible(caseAjout);
						}
						if(permis && caseAdjGauche!=null) 
						{
							permis = caseAdjGauche.isCompatible(caseAjout);
						}
						if(permis)
						{
							niveau.set(i, j, caseAjout);
						}
					}

				}
			}
			listeNiveaux.add(niveau);
		}
	}
	
	static public void main(String[] args)
	{
		Niveaux.init();
		for(BattleFieldMatrixPW carte : Niveaux.listeNiveaux)
		{
			carte.afficheCarte();
		}
	}

}
