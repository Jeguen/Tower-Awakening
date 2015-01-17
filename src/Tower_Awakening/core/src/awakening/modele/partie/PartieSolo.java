package awakening.modele.partie;

import awakening.control.moteur.TAGame;
import awakening.modele.field.Box;
import awakening.modele.field.Field;

public class PartieSolo extends Partie {

	public PartieSolo(TAGame game, Field terrain) {
		super(game);
		this.terrain = terrain;
		terrain.creerPlateau();
		terrain.getSpawns().add(
				terrain.getBox().get(terrain.getBoxIndexByPosition(200, 200)));
		terrain.getBox().get(terrain.getBoxIndexByPosition(200, 200))
				.setFieldType(Box.FIELD_SPAWNS);
		terrain.getSpawns().add(
				terrain.getBox().get(terrain.getBoxIndexByPosition(150, 20)));
		terrain.getBox().get(terrain.getBoxIndexByPosition(150, 20))
				.setFieldType(Box.FIELD_SPAWNS);
		terrain.setFinishBox(terrain.getBox().get(
				terrain.getBoxIndexByPosition(10, 20)));
		terrain.numeroterDistance(terrain.getFinishBox());
		moteurJeu.start();
	}
}
