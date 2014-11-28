package td2.battlefield.test;

import static org.junit.Assert.*;

import org.junit.Test;

import td2.battlefield.exception.RemoveWiseException;
import td2.battlefield.wise.BattleFieldMatrixPW;
import td2.battlefield.wise.BattleFieldLandScape;
import td2.battlefield.wise.BattleFieldRoad;
import td2.battlefield.wise.BattleFieldWater;

/**
 * BattleFieldMatrixPWTest
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class BattleFieldMatrixPWTest 
{

	/**
	 * Show matrix cases
	 * 
	 * @param a matrix
	 */
	public void showMatrix(BattleFieldMatrixPW a)
	{
		for (int i = 0; i < a.getMaxX(); i++)
		{
			for(int j = 0; j < a.getMaxY(); j++)
			{
				if(a.exists(i,j))
				{
					if(a.get(i, j) instanceof BattleFieldWater)
					{
						System.out.print("| W ");
					}
					else if(a.get(i, j) instanceof BattleFieldLandScape)
					{
						System.out.print("| L ");
					}
					else if(a.get(i, j) instanceof BattleFieldRoad)
					{
						System.out.print("| R ");
					}
				}
				else
				{
					System.out.print("| V ");
				}
				
			}
			System.out.println("|\n");
		}
	}
	
	@Test
	public void testSetValue() 
	{
		System.out.println("Test 1: set value in BattleFieldMatrixPW");
		
		BattleFieldMatrixPW a = new BattleFieldMatrixPW();

		try
		{
			a.set(0, 0, new BattleFieldRoad(0, 1));
			a.set(1, 0, new BattleFieldLandScape(1, 0));
			a.set(2, 0, new BattleFieldLandScape(2, 0));
			a.set(3, 0, new BattleFieldLandScape(3, 0));
			
			a.set(0, 1, new BattleFieldWater(0, 1));
			a.set(1, 1, new BattleFieldRoad(1, 1));
			a.set(2, 1, new BattleFieldRoad(2, 1));
			a.set(3, 1, new BattleFieldRoad(3, 1));
			
			a.set(1, 2, new BattleFieldWater(1, 2));
			a.set(2, 2, new BattleFieldWater(2, 2));
			a.set(3, 2, new BattleFieldWater(3, 2));
		}
		catch(ArithmeticException e)
		{
			System.out.println("Negative value");
			fail("Negative value");
		}
		
		showMatrix(a);
		
		System.out.println("Add case (0, -5)");
		try
		{
			a.set(0, -5, new BattleFieldLandScape(0, -5));
		}
		catch(ArithmeticException e)
		{
			System.out.println("Negative value");
			fail("Negative value");
		}
	
	}
	
	@Test
	public void removingCase()
	{
		System.out.println("Test 2 : removing cases in BattleFieldMatrixPW");
		
		BattleFieldMatrixPW a = new BattleFieldMatrixPW();
		
		try
		{
			a.set(0, 0, new BattleFieldLandScape(0, 0));
			a.set(0, 1, new BattleFieldWater(0, 1));
			a.set(1, 0, new BattleFieldRoad(1, 0));
		}
		catch(ArithmeticException e)
		{
			System.out.println("Negative value");
			fail("Negative value");
		}
		
		showMatrix(a);
		
		System.out.println("Removing BattleFieldLandScape case");
		try
		{
			a.remove(0, 0);
		}
		catch(RemoveWiseException e)
		{
			e.printStackTrace();
		}
		showMatrix(a);
		
		System.out.println("Removing BattleFieldRoad case");
		try
		{
			a.remove(1, 0);
		}
		catch(RemoveWiseException e)
		{
			e.printStackTrace();
		}
		showMatrix(a);
		
		System.out.println("Removing BattleFieldWater case");
		try
		{
			a.remove(0, 1);
		}
		catch(RemoveWiseException e)
		{
			e.printStackTrace();
		}
		showMatrix(a);
		
		System.out.println("Removing empty case");
		try
		{
			a.remove(1, 1);
		}
		catch(RemoveWiseException e)
		{
			e.printStackTrace();
		}
		showMatrix(a);
	}
}


