package awakening.view.partie;

import awakening.modele.field.Field;
import awakening.modele.toolshop.ToolShop;

public class Partie {

	ToolShop toolshop;
	Joueur joueurUtilisateur;
	Joueur joueurAdverse;
	Field terrain;
	
	public Partie() {
		toolshop = new ToolShop();
		toolshop.loadModele();
		joueurUtilisateur = new Joueur();
	}

}
