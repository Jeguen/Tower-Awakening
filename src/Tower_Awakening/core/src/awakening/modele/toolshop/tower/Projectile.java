package awakening.modele.toolshop.tower;

import java.io.File;

import ta.shape3D.mesh.MeshTA;
import awakening.modele.toolshop.monster.Monster;

public class Projectile extends MeshTA {
	private static MeshTA forme = MeshTA.loadMeshTA(new File("projectile.mta"));
	private float dx;
	private int iteration;
	private float dy;
	private Monster target;
	private boolean deleted;
	private float damageValue;

	public Projectile(float coordX, float coordY, Monster target,
			float damageValue) {
		this.translate(coordX, 1, coordY);
		this.target = target;
		deleted = false;
		this.damageValue = damageValue;
		dx = (target.getX() - coordX) / 25;
		dy = (target.getZ() - coordY) / 25;
		this.homethetie(4);
		this.copy(forme);
	}

	public void doDamage() {
		target.takeDamage(damageValue);
	}

	public boolean targetIsReached() {
		System.out.println("Missile " + this.getX() + " " + this.getZ());
		return iteration == 25;
	}

	public void avance() {
		this.translate(dx, 0, dy);
		iteration++;
	}

	public Monster getTarget() {
		return target;
	}

	public void setTarget(Monster target) {
		this.target = target;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDamageValue(int damageValue) {
		this.damageValue = damageValue;
	}
}
