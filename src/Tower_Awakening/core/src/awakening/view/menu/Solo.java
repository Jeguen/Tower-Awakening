package awakening.view.menu;

import awakening.control.moteur.TAGame;
import awakening.modele.field.Field;
import awakening.modele.partie.PartieSolo;

public class Solo extends PartieView{


	public Solo(TAGame game, Field terrain) {
		super(game, new PartieSolo(game, terrain));	
	}


}
