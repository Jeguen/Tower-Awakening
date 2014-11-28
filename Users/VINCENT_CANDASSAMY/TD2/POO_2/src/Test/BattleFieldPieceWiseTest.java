package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import LayeredBattleField.BattleFieldPieceWise;
import LayeredBattleField.BattleFieldPieceWise.BattleFieldLandScape;
import LayeredBattleField.BattleFieldPieceWise.BattleFieldRoad;
import LayeredBattleField.BattleFieldPieceWise.BattleFieldWater;

public class BattleFieldPieceWiseTest {

	@Test
	public void test() {
		BattleFieldPieceWise eau = new BattleFieldWater(), plaine = new BattleFieldLandScape(), route = new BattleFieldRoad();
		assertTrue("Test water should be not destroyable ",!eau.isDestroyable());
		assertTrue("Test landScape should be destroyable ",plaine.isDestroyable());
		assertTrue(route.isDestroyable());
		assertTrue("Test water should be not compatible with road ",!(eau.isCompatible(route)));
		assertTrue("Test water should be not compatible with landScape ",!(eau.isCompatible(plaine)));
		assertTrue("Test landScape should be compatible with road ", plaine.isCompatible(route));	
	}

}
