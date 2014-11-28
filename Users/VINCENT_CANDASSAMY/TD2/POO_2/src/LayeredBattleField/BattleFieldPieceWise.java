package LayeredBattleField;

/**
 * Case de BattleField
 * @author S Firegreen
 *
 */
public abstract class BattleFieldPieceWise {

	private int X,Y;
	
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	/**
	 * Verifie si la cible peut être mis à coté d'une autre case
	 * @param with case dont on verifie la compatibilité avec la cible
	 * @return true si les deux cases peuvent être placé l'un à coté de l'autre
	 */
	abstract public boolean isCompatible(BattleFieldPieceWise with);
	abstract public boolean isDestroyable();
	
	protected BattleFieldPieceWise(){};
	
		/**
		 * case de type eau
		 * @author S Firegreen
		 *
		 */
		public static class BattleFieldWater 
		extends BattleFieldPieceWise
		{
	
			@Override
			public boolean isCompatible(BattleFieldPieceWise with) {
				return !with.getClass().equals(BattleFieldRoad.class) && 
					   !with.getClass().equals(BattleFieldLandScape.class);
			}
	
			@Override
			public boolean isDestroyable() {
				return false;
			}
			
		}
		
		/**
		 * classe de type route
		 * @author S Firegreen
		 *
		 */
		public static class BattleFieldRoad
		extends BattleFieldPieceWise
		{
	
			@Override
			public boolean isCompatible(BattleFieldPieceWise with) {
				return !with.getClass().equals(BattleFieldWater.class) && 
					    (with.getClass().equals(BattleFieldLandScape.class) ||
					    		with.getClass().equals(BattleFieldRoad.class));
			}
	
			@Override
			public boolean isDestroyable() {
				return true;
			}
			
		}
		
		/**
		 * case de type plaine
		 * @author S Firegreen
		 *
		 */
		public static class BattleFieldLandScape
		extends BattleFieldPieceWise
		{
	
			@Override
			public boolean isCompatible(BattleFieldPieceWise with) {
				return !with.getClass().equals(BattleFieldWater.class) && 
					    (with.getClass().equals(BattleFieldRoad.class) ||
					    		with.getClass().equals(BattleFieldLandScape.class));
			}
	
			@Override
			public boolean isDestroyable() {
				return true;
			}
			
		}

}
