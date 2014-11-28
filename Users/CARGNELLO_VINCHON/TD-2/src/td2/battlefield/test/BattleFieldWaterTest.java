package td2.battlefield.test;

import static org.junit.Assert.*;

import org.junit.Test;

import td2.battlefield.wise.BattleFieldLandScape;
import td2.battlefield.wise.BattleFieldRoad;
import td2.battlefield.wise.BattleFieldWater;

/**
 * BattleFieldWaterTest
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class BattleFieldWaterTest 
{

	@Test
	public void testBattleFieldWater() 
	{
		System.out.println("Test 1: BattleFieldWater functions");
		
		BattleFieldWater a = new BattleFieldWater();
		
		if(a.isDestroyable())
		{
			fail("Issues with method isDestroyable()");
		}
		
		if(a.isCompatible(new BattleFieldLandScape()))
		{
			fail("Issues with method isCompatible()");
		}
		
		if(a.isCompatible(new BattleFieldRoad()))
		{
			fail("Issues with method isCompatible()");
		}
		
		if(!a.isCompatible(new BattleFieldWater()))
		{
			fail("Issues with method isCompatible()");
		}
	}

}
