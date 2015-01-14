package awakening.view.GameWidget;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Class derived from Button of libgdx which is design for the ToolShop
 * @author S Firegreen
 */
public class BoutonShop extends Button implements OnHoverableWidget{

	private Texture textureNormal;
	private Texture textureHover;
	private boolean isOnHover;
	
	/** 
	 * Initialize the design of the button with the image of the Tower which is selected
	 * @param imageTour
	 * @param width
	 * @param height
	 */
	public BoutonShop(Texture imageTour, float width, float height) {
		super(new TextureRegionDrawable(new TextureRegion(imageTour,
				0,0,imageTour.getWidth(), imageTour.getHeight())));
		this.setWidth(width);
		this.setHeight(height);
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA4444 );
		for(int i = 0; i<10;i++)
		{
			float color = (float)i/15+0.2f;
			pixmap.setColor(color, color, color, 1);
			pixmap.drawRectangle(i, i, pixmap.getWidth()-i*2, pixmap.getHeight()-i*2);
		}
		textureNormal = new Texture( pixmap );
		pixmap.dispose();
		pixmap = new Pixmap((int)width, (int)height, Format.RGBA4444 );
		for(int i = 0; i<10;i++)
		{
			float color = (float)i/15+0.2f;
			pixmap.setColor(color+0.3f, color+0.3f, 0.1f, 1);
			pixmap.drawRectangle(i, i, pixmap.getWidth()-i*2, pixmap.getHeight()-i*2);
		}
		textureHover = new Texture( pixmap );
		this.addAction(new Action(){

			@Override
			public boolean act(float delta) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
		pixmap.dispose();
	}
	
	/**
	 * Draw the button by using a Batch and according the state of the button
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {

		if(isOnHover)
			batch.draw(textureHover, this.getX(), this .getY(),
				this.getWidth(),this.getHeight());
		else
			batch.draw(textureNormal, this.getX(), this .getY(),
				this.getWidth(),this.getHeight());
		batch.draw(((TextureRegionDrawable)this.getStyle().up).getRegion()
				, this.getX() + 10, this .getY() + 10, this.getWidth()-20,this.getHeight()-20);
	}
	

	@Override
	public void onExitAction()
	{
		isOnHover=false;
	}
	@Override
	public void onHoverAction()
	{
		isOnHover=true;
	}
	@Override
	public boolean testMousePosition(Vector2 mousePosition) {
		float deltaX = mousePosition.x - this.getX();
		float deltaY = mousePosition.y - this.getOriginY();
		return deltaX < this.getHeight() && deltaY < this.getWidth()
				&& deltaX > 0 && deltaY > 0;		
	}

}
