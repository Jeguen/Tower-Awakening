package awakening.modele.toolshop.tower;

import ta.shape3D.mesh.MeshTA;

import java.io.File;

import awakening.modele.toolshop.monster.Monster;

public class Projectile extends MeshTA
{
	private static MeshTA forme=MeshTA.loadMeshTA(new File("projectile.mta"));
	private int coordX;
	private int coordY; 
	private Monster target;
	private boolean deleted;
	private float damageValue;

	public Projectile(int coordX, int coordY, Monster target, float damageValue) 
	{
		this.coordX=coordX;
		this.coordY=coordY;
		this.target=target;
		deleted=false;
		this.damageValue=damageValue;
		this.copy(forme);
	}
	public void doDamage()
	{
		if(coordX==target.getCoordX() && coordY==target.getCoordY())
		{
			deleted=true;
			target.takeDamage(damageValue);
		}
	}
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
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
