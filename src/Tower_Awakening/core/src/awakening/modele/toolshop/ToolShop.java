package awakening.modele.toolshop;

import java.io.File;
import java.util.LinkedList;

import awakening.modele.toolshop.monster.Monster;
import awakening.modele.toolshop.monster.MonsterEarth;
import awakening.modele.toolshop.tower.Tower;

import com.badlogic.gdx.Gdx;

public class ToolShop {

	
	public final LinkedList<Tower> tours;
	public final LinkedList<Monster> monstres;
	private int selectedTowerIndex;
	private int selectedMonsterIndex;

	public ToolShop() {
		tours = new LinkedList<Tower>();
		monstres = new LinkedList<Monster>();	
	}
	
	public void addAMonster(Monster m)
	{
		
	}
	
	//////////////////////////////////////////////
	///////////////GETTER & SETTER///////////////
	/////////////////////////////////////////////
	
	public void addATower(Tower t)
	{
		tours.add(t);
	}
	public Monster getSelectedMonster()
	{
		if(selectedMonsterIndex<monstres.size())
			return monstres.get(selectedMonsterIndex);
		else
			return null;
	}
	public Tower getSelectedTower()
	{
		if(selectedTowerIndex<tours.size())
			return tours.get(selectedTowerIndex);
		else
			return null;
	}
	public void loadModele()
	{
		File towerDirectory = Gdx.files.internal("Tower").file();
		for(File ft : towerDirectory.listFiles())
		{
			if(ft.isFile())
				if(ft.getName().endsWith("mta"))
					tours.add(Tower.loadTower(ft));
		}
		File monsterDirectory = Gdx.files.internal("Monster").file();
		for(File ft : monsterDirectory.listFiles())
		{
			if(ft.isFile())
				if(ft.getName().endsWith("mta"))
					monstres.add(Monster.loadMonster(ft));
		}
		for(Tower t : tours) System.out.println(t);
	}
	

}
