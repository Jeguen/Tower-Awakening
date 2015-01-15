package awakening.modele.partie;

import com.badlogic.gdx.Gdx;

import awakening.control.moteur.PartieInputManagement;
import awakening.control.moteur.TAGame;
import awakening.modele.field.Box;
import awakening.modele.field.Field;
import awakening.modele.toolshop.ToolShop;
import awakening.modele.toolshop.tower.OffensivTower;
import awakening.modele.toolshop.tower.Tower;
import awakening.view.menu.MainMenu;

public abstract class Partie {

	final ToolShop toolshop;
	final Joueur joueurUtilisateur;
	Joueur joueurAdverse;
	Field terrain;
	PartieInputManagement inputManager;
	TAGame game;
	
	
	public Partie(TAGame game) {
		toolshop = new ToolShop();
		toolshop.loadModele();
		joueurUtilisateur = new Joueur();
		joueurAdverse = new Joueur();
		this.game=game;
	}
	
	public ToolShop getToolShop()
	{
		return toolshop;
	}
	
	public void clickBox(float x, float y)
	{
		int i = terrain.getBoxIndexByPosition(x, y);
		if(i>=0  && i<terrain.getBox().size()){
			if(terrain.getBox().get(i).isFree())
			{
				Tower t = new OffensivTower(toolshop.getSelectedTower());
				joueurUtilisateur.addTower(t);
				terrain.getBox().get(i).setTower(t);
				terrain.getBox().get(i).setFieldType(Box.FIELD_TOWER);
				terrain.setTowerExist(true);
				terrain.numeroterDistance(terrain.getFinishBox());
				t.homethetie(3);
				t.translate(terrain.getBox().get(i).getCoordX(),0,terrain.getBox().get(i).getCoordY());
				terrain.findPathMonster();
			}
			else
			{
				if(terrain.getBox().get(i).getTower()!=null)
				{
					terrain.getBox().get(i).getTower().upgrade();
				}
			}
		}
	}
	public void getBackToMainMenu()
	{
		game.setScreen(new MainMenu(game));
	}
	
	public Joueur getJoueur()
	{
		return joueurUtilisateur;
	}
	
	public Field getTerrain()
	{
		return terrain;
	}

	
	public void setInputManager(PartieInputManagement inputManager)
	{
		this.inputManager = inputManager;
	}
	
	public PartieInputManagement getInputManager()
	{
		return inputManager;
	}
	
	public void setTerrain(Field terrain)
	{
		this.terrain = terrain;
	}
}
