package awakening.modele.toolshop.tower;

import awakening.modele.toolshop.monster.Monster;

public class Projectile
{
	private int coordX;
	private int coordY; 
	private Monster target;
	private float speed;
	private boolean deleted;
	private int damageValue;

	public Projectile(int coordX, int coordY, Monster target, int speed) 
	{
		this.coordX=coordX;
		this.coordY=coordY;
		this.target=target;
		this.speed=speed;
		deleted=false;
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
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public int getDamageValue() {
		return damageValue;
	}
	public void setDamageValue(int damageValue) {
		this.damageValue = damageValue;
	}
	
	

}
