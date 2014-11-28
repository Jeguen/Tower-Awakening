package td2.battlefield.test;

import static org.junit.Assert.*;

import org.junit.Test;

import td2.battlefield.wise.BattleFieldLandScape;
import td2.battlefield.wise.BattleFieldRoad;
import td2.battlefield.wise.BattleFieldWater;

/**
 * BattleFieldRoadTest
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class BattleFieldRoadTest 
{

	@Test
	public void testBattleFieldRoad() 
	{
		System.out.println("Test 1: BattleFieldRoad functions");
		
		BattleFieldRoad a = new BattleFieldRoad();
		
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
