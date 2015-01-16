package awakening.modele.partie;

import awakening.control.moteur.TAGame;
import awakening.modele.field.Box;
import awakening.modele.field.Field;

public class PartieSolo extends Partie{
	
	
	public PartieSolo(TAGame game, Field terrain)
	{
		super(game);
		this.terrain = terrain;
		terrain.creerPlateau();
		terrain.getSpawns().add(terrain.getBox().get(99));
		terrain.getBox().get(99).setFieldType(Box.FIELD_SPAWNS);
		terrain.getSpawns().add(terrain.getBox().get(3));
		terrain.getBox().get(3).setFieldType(Box.FIELD_SPAWNS);
		terrain.getSpawns().add(terrain.getBox().get(50));
		terrain.getBox().get(50).setFieldType(Box.FIELD_SPAWNS);
		terrain.getSpawns().add(terrain.getBox().get(120));
		terrain.getBox().get(120).setFieldType(Box.FIELD_SPAWNS);
		terrain.setFinishBox(terrain.getBox().get(100));
		terrain.numeroterDistance(terrain.getFinishBox());
		moteurJeu.start();
	}
}
