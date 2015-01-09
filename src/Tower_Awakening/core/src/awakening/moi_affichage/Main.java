package awakening.moi_affichage;

import awakening.field.Field;


public class Main
{
	public static Field terrain;
	private static int halfRadiusPolygon = 10;
	private static int nbSidePolygon = 6;
	private static int border = 10;
	private static int nbSpawn = 1;
	private static int nbBoxHeight =20;
	private static int nbBoxWidth = 20;
	public static void main(String[] args)
	{
		terrain = new Field(halfRadiusPolygon, nbSidePolygon, border, nbSpawn, nbBoxHeight, nbBoxWidth, null);
		terrain.creerPlateau();
		Fenetre f = new Fenetre(nbBoxWidth * 4 * halfRadiusPolygon + border * 2 + halfRadiusPolygon * 2, nbBoxHeight * 3 * halfRadiusPolygon
		            + border * 3);
		f.setVisible(true);
	}
}
