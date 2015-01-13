package awakening.modele.toolshop;

import java.io.File;
import java.util.LinkedList;

import awakening.modele.toolshop.monster.Monster;
import awakening.modele.toolshop.monster.MonsterEarth;
import awakening.modele.toolshop.tower.OffensivTower;
import awakening.modele.toolshop.tower.Tower;

public class ToolShop {

	
	LinkedList<Tower> tours;
	LinkedList<Monster> monstres;
	public ToolShop() {}
	
	public void loadModele()
	{
		File towerDirectory = new File("Tower");
		for(File ft : towerDirectory.listFiles())
		{
			if(ft.isFile())
				if(ft.getName().endsWith("mta"))
					tours.add(new OffensivTower(ft.getAbsolutePath()));
		}
		File monsterDirectory = new File("Monster");
		for(File ft : monsterDirectory.listFiles())
		{
			if(ft.isFile())
				if(ft.getName().endsWith("mta"))
					monstres.add(new MonsterEarth(ft.getAbsolutePath()));
		}
	}
	
	//////////////////////////////////////////////
	///////////////GETTER & SETTER///////////////
	/////////////////////////////////////////////
	
	public void addAMonster(Monster m)
	{
		
	}
	public void getAMonster(int i)
	{
		
	}
	public void addATower(Tower t)
	{
		
	}
	public void getATower(Tower t)
	{
		
	}
	

}
