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
		super.action();
	}

}
