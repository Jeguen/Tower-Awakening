package awakening.game;

import com.badlogic.gdx.math.Vector2;

public interface OnHoverableWidget {
	public void onHoverAction();
	public void onExitAction();
	public boolean testMousePosition(Vector2 mousePosition);
}
