package Test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import LayeredBattleField.BattleFieldMatrix;
import LayeredBattleField.BattleFieldMatrix.Index;

public class BattleFieldMatrixTest {

	final static Integer TESTVALUE = 7;
	final static Index TESTINDEX = new Index(3,5);
	final static Index TESTINDEXFALSE = new Index(0,1);


	@Test
	public void test() {
		BattleFieldMatrix<Integer> map = new BattleFieldMatrix<Integer>();
		map.set(TESTINDEX.getL(), TESTINDEX.getC(), TESTVALUE);
		assertEquals("Test get failed",TESTVALUE,map.get(3, 5));
		assertTrue("Test exist should return true for " + TESTINDEX,map.exists(TESTINDEX.getL(), TESTINDEX.getC()));
		assertTrue("Test exist should return false for " + TESTINDEXFALSE,!map.exists(TESTINDEXFALSE.getL(), TESTINDEXFALSE.getC()));
	}

}
