package awakening.view.menu;

import com.badlogic.gdx.Gdx;

import awakening.control.moteur.PartieInputManagement;
import awakening.control.moteur.TAGame;
import awakening.modele.partie.PartieSolo;

public class Solo extends PartieView{


	public Solo(TAGame game) {
		super(game, new PartieSolo(game));
		inputManager = new PartieInputManagement(this, partie);
		Gdx.input.setInputProcessor(inputManager);
		partie.setInputManager(inputManager);
	}


}
