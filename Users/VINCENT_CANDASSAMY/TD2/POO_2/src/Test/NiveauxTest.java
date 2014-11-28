package Test;

import org.junit.Test;

import LayeredBattleField.BattleFieldMatrixPW;
import LayeredBattleField.Niveaux;

public class NiveauxTest {

	@Test
	public void test() {
		Niveaux.init();
		for(BattleFieldMatrixPW carte : Niveaux.listeNiveaux)
		{
			carte.afficheCarte();
		}
		
	}

}
