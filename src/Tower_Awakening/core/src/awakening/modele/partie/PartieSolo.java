package awakening.modele.partie;

import java.io.File;

import awakening.control.moteur.TAGame;
import awakening.modele.field.Box;
import awakening.modele.field.Field;

public class PartieSolo extends Partie{
	
	
	public PartieSolo(TAGame game)
	{
		super(game);
		terrain = new Field(3, 6, 10, 5, 250, 250, new File("terrain 1.mta"));
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
	}
}
