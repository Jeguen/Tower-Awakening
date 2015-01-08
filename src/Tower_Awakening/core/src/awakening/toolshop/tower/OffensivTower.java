package awakening.toolshop.tower;

import ta.shape3D.mesh.MeshTA;

public class OffensivTower extends Tower {

	public OffensivTower(int id, float buildCost, int range,
			float speedAttaque, int level) {
		super(id, buildCost, range, speedAttaque, level);
		// TODO Auto-generated constructor stub
	}
	
	public OffensivTower(MeshTA m) {
		super(m);
	}
	public OffensivTower(String path) {
		super(path);
	}

	@Override
	public void action() {
		float deltaZ = -(target.getZ()- getZ());
		float deltaX = (target.getX()- getX());
		float rotation = 0;
		if(deltaZ>0){
			rotation = -1;
		}
		else if(deltaZ<0)
		{
			rotation = 1;
		}
		else
		{
			if(deltaX * canonPosition.x<0 || Math.abs(deltaZ-canonPosition.y)>1f)
			{
				 rotation = 1;
			}
		}
		System.out.println("position Target : " + deltaX + " " + deltaZ);
		System.out.println("position canon : " + canonPosition.x + " " + canonPosition.y);
		System.out.println("Rotation : " + rotation);
		animationRY(rotation);
	}

}
