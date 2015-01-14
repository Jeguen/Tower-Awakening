package awakening.modele.partie;

import awakening.modele.toolshop.ToolShop;
import awakening.modele.toolshop.tower.Tower;

public class Joueur {

	
	ToolShop inventaire;
	
	
	public Joueur() {
		inventaire = new ToolShop();
	}
	
	public void addTower(Tower t)
	{
		inventaire.addATower(t);
	}
	
	public Iterable<Tower> getTowerIterator()
	{
		return inventaire.tours;
		
	}

}
