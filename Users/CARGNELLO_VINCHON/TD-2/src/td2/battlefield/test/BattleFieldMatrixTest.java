package td2.battlefield.test;

import td2.battlefield.matrix.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * BattleFieldMatrixTest
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class BattleFieldMatrixTest
{
	@Test
	public void testBattleFieldMatrix() 
	{
		System.out.println("Test 1: BattleFieldMatrix functions");
		BattleFieldMatrix<Integer> a =new BattleFieldMatrix<Integer>();
		
		try
		{
			a.set(3,9,42);
		}
		catch(ArithmeticException e)
		{
			System.out.println("Negative value");
			fail("Negative value");
		}
		
		System.out.println(a.get(3, 9));
		
		if((int)a.get(3, 9) != 42)
		{
			fail("Issues with method get()");
		}
		
		if(!a.exists(3,9))
		{
			fail("Issues with method exists()");
		}
		
		if(!a.exists(1,1))
		{
			fail("Issues with method exists()");
		}
	}
}
