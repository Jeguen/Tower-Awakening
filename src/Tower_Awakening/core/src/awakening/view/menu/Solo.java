package awakening.view.menu;

import awakening.control.moteur.PartieInputManagement;
import awakening.control.moteur.TAGame;
import awakening.modele.field.Field;
import awakening.modele.partie.PartieSolo;

import com.badlogic.gdx.Gdx;

public class Solo extends PartieView{


	public Solo(TAGame game, Field terrain) {
		super(game, new PartieSolo(game, terrain));
		inputManager = new PartieInputManagement(this, partie);
		Gdx.input.setInputProcessor(inputManager);
		partie.setInputManager(inputManager);
	}


}
