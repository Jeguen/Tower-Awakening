package awakening.modele.partie;

import awakening.control.moteur.GameMotor;
import awakening.control.moteur.PartieInputManagement;
import awakening.control.moteur.TAGame;
import awakening.modele.field.Box;
import awakening.modele.field.Field;
import awakening.modele.toolshop.ToolShop;
import awakening.modele.toolshop.monster.Monster;
import awakening.modele.toolshop.tower.OffensivTower;
import awakening.view.menu.MainMenu;

public abstract class Partie {

	final ToolShop toolshop;
	final Joueur joueurUtilisateur;
	Joueur joueurAdverse;
	Field terrain;
	PartieInputManagement inputManager;
	TAGame game;
	GameMotor moteurJeu;

	public Partie(TAGame game) {
		toolshop = new ToolShop();
		toolshop.loadModele();
		joueurUtilisateur = new Joueur();
		joueurAdverse = new Joueur();
		moteurJeu = new GameMotor(this);
		this.game = game;
	}

	public ToolShop getToolShop() {
		return toolshop;
	}

	public void clickBox(Box b) {
		if (b.isFree()) {
			OffensivTower t = new OffensivTower(toolshop.getSelectedTower());
			System.out.println("Tower adding " + t.getDamage());
			joueurUtilisateur.addTower(t);
			b.setTower(t);
			b.setFieldType(Box.FIELD_TOWER);
			t.setBox(b);
			terrain.setTowerExist(true);
			terrain.numeroterDistance(terrain.getFinishBox());
			t.homethetie(3);
			t.translate(b.getCoordX(), 0, b.getCoordY());
			terrain.findPathMonster();
		} else {
			if (b.getTower() != null) {
				b.getTower().upgrade();
			}
		}
	}

	public void getBackToMainMenu() {
		game.setScreen(new MainMenu(game));
	}

	public Joueur getJoueur() {
		return joueurUtilisateur;
	}

	public Field getTerrain() {
		return terrain;
	}

	public void setInputManager(PartieInputManagement inputManager) {
		this.inputManager = inputManager;
	}

	public PartieInputManagement getInputManager() {
		return inputManager;
	}

	public void setTerrain(Field terrain) {
		this.terrain = terrain;
	}

	public GameMotor getGameMotor() {
		return moteurJeu;
	}

	public void addMonster(Monster m) {
		terrain.addMonster(m);
	}
}
