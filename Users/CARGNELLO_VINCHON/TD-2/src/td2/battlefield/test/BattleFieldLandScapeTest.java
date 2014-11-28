package td2.battlefield.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import td2.battlefield.wise.BattleFieldLandScape;
import td2.battlefield.wise.BattleFieldRoad;
import td2.battlefield.wise.BattleFieldWater;

/**
 * BattleFieldLandScapeTest
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class BattleFieldLandScapeTest 
{

	@Test
	public void testBattleFieldLandScape() 
	{
		System.out.println("Test 1: BattleFieldLandScape functions");
		
		BattleFieldLandScape a = new BattleFieldLandScape();
		
		if(!a.isDestroyable())
		{
			fail("Issues with method isDestroyable()");
		}
		
		if(!a.isCompatible(new BattleFieldLandScape()))
		{
			fail("Issues with method isCompatible()");
		}
		
		if(!a.isCompatible(new BattleFieldRoad()))
		{
			fail("Issues with method isCompatible()");
		}
		
		if(a.isCompatible(new BattleFieldWater()))
		{
			fail("Issues with method isCompatible()");
		}
	}

}
