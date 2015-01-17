package awakening.modele.toolshop.tower;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import ta.shape3D.mesh.MeshTA;

public class OffensivTower extends Tower {
	private float damage;

	OffensivTower() {
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public OffensivTower(int id, float buildCost, int range,
			float speedAttaque, int level, float damage) {
		super(id, buildCost, range, speedAttaque, level);
		this.damage = damage;

	}

	public OffensivTower(MeshTA modele, int id, float buildCost, int range,
			float speedAttaque, int level, float damage) {
		super(modele, id, buildCost, range, speedAttaque, level);
		this.damage = damage;
	}

	public OffensivTower(Tower m) {
		super(m, m.id, m.buildCost, m.range, m.speedAttack, m.level);
		this.levelUp = m.levelUp;
		if (m.getClass().equals(OffensivTower.class))
			this.damage = ((OffensivTower) m).damage;
	}

	public OffensivTower(String path) {
		super(path);
	}

	@Override
	public void action() {
		super.action();
		if ((System.currentTimeMillis() % 500) > 0
				&& (System.currentTimeMillis() % 500) < 10) {
			System.out.println("yes");
			// if a projectile has been deleted, it will be removed by the
			// list
			for (Projectile p : projectiles) {
				if (p.isDeleted()) {
					projectiles.remove(p);
				}
			}
			Projectile p = new Projectile(box.getCoordX(), box.getCoordY(),
					target, damage);
			projectiles.add(p);
		}
	}

	@Override
	public void save(DataOutputStream bos, File f) throws IOException {
		super.save(bos, f);
		bos.writeFloat(damage);
	}

	@Override
	public void load(File f, DataInputStream dis) throws IOException {
		super.load(f, dis);
		damage = dis.readFloat();
		System.out.println(damage + " Tower loading");
	}

	public void copy(OffensivTower towerModele) {
		super.copy(towerModele);
		this.damage = towerModele.damage;
	}

	@Override
	public void upgrade() {
		if (canBeUpgrade()) {
			this.damage = ((OffensivTower) this.levelUp).damage;
			super.upgrade();
		}
	}
}
