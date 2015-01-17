package awakening.view.GameWidget;

import com.badlogic.gdx.math.Vector2;

/**
 * Interface which allow the management of the cursor move
 * 
 * @author S Firegreen
 *
 */
public interface OnHoverableWidget {
	/**
	 * This method should be called when the mouse exit the widget area
	 */
	public void onExitAction();

	/**
	 * This method should be called when the mouse is above the widget
	 */
	public void onHoverAction();

	/**
	 * Test if the Cursor is above the widget
	 */
	public boolean testMousePosition(Vector2 mousePosition);
}
