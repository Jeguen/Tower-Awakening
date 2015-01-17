package awakening.control.moteur;

import java.util.ListIterator;

import ta.shape3D.Animator;
import awakening.modele.partie.Partie;
import awakening.modele.toolshop.monster.Monster;
import awakening.modele.toolshop.tower.Projectile;
import awakening.modele.toolshop.tower.Tower;

public class GameMotor extends Thread {
	private Partie partie;
	private Animator animation;

	public GameMotor(Partie partie) {
		this.partie = partie;
		animation = new Animator(40);
	}

	@Override
	public void run() {
		super.run();
		animation.start();
		try {
			while (true) {
				ListIterator<Monster> iteratM = partie.getTerrain()
						.getMonsters().listIterator();
				while (iteratM.hasNext()) {
					Monster m = iteratM.next();
					if (m.getPath().size() > 0) {
						if (m.isArrived || !m.isAlive()) {
							iteratM.remove();
						} else
							m.normalMove();
					} else
						partie.getTerrain().findPathMonster();
				}
				for (Tower t : partie.getJoueur().getTowerIterator()) {
					if (!t.haveATarget()) {
						for (Monster monstre : partie.getTerrain()
								.getMonsters())
							if (t.testPortee(monstre.getX(), monstre.getZ())) {
								t.targetMonster(monstre);
								t.action();
								animation.addTemporarily(t);
							}
					} else {
						if (!t.getTarget().isArrived && t.getTarget().isAlive())
							if (t.testPortee(t.getTarget().getX(), t
									.getTarget().getZ())) {
								t.action();
								animation.addTemporarily(t);
								ListIterator<Projectile> projectiles = t
										.getProjectiles().listIterator();
								while (projectiles.hasNext()) {
									Projectile p = projectiles.next();
									if (p.targetIsReached()) {
										p.doDamage();
										projectiles.remove();
									} else {
										p.avance();
									}
								}
							} else {
								t.loseTarget();
								System.out.println("lose target!");
							}
						else
							t.loseTarget();
					}
				}
				sleep(20);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
