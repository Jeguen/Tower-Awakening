package awakening.modele.toolshop.monster;

public class FlyingMonster extends Monster {
	public FlyingMonster() {
		super();
	}

	public FlyingMonster(int id, int lifePoint, String name, float speedAttack,
			float vitesseDeplacement, boolean visible, String facSheet,
			float buildCost, int gainGold, int damage) {
		super(id, lifePoint, name, speedAttack, vitesseDeplacement, visible,
				facSheet, buildCost, gainGold, damage);
	}

	@Override
	public void crazyMove() {
	}

	@Override
	public void normalMove() {
	}
}
