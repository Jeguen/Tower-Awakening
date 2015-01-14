package awakening.control.moteur;

import java.util.Iterator;
import java.util.LinkedList;

import awakening.modele.partie.Partie;
import awakening.view.GameWidget.OnHoverableWidget;
import awakening.view.menu.PartieView;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PartieInputManagement implements InputProcessor{

	private final Vector2 actualMousePoisition = new Vector2(0,0);
	private final PartieView vue;
	private final Partie partie;
	private final LinkedList<OnHoverableWidget> widgets;
	private OnHoverableWidget onHoveredWidget;
	boolean keyPressed=false;

	
	public PartieInputManagement(PartieView vue, Partie partie) {
		this.vue = vue;
		this.partie = partie;
		widgets = new LinkedList<OnHoverableWidget>();
	}
	
	public Vector2 getMousePosition()
	{
		return actualMousePoisition;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		keyPressed=true;
		if(keycode == Input.Keys.LEFT)
		{
			vue.translateX(-2);
			new Thread(){
				@Override
				public void run()
				{
					while(keyPressed) 
					{
						vue.translateX(-2.5f);
						try {
							sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		else if(keycode ==Input.Keys.RIGHT)
		{
			vue.translateX(2);
			new Thread(){
				@Override
				public void run()
				{
					while(keyPressed) 
					{
						vue.translateX(2.5f);
						try {
							sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		else if(keycode ==Input.Keys.UP)
		{
			vue.translateZ(-2);
			new Thread(){
				@Override
				public void run()
				{
					while(keyPressed) 
					{
						vue.translateZ(-2.5f);
						try {
							sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		else if(keycode ==Input.Keys.DOWN)
		{
			vue.translateZ(2);
			new Thread(){
				@Override
				public void run()
				{
					while(keyPressed) 
					{
						vue.translateZ(2.5f);
						try {
							sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		else if(keycode ==Input.Keys.Q)
		{
			vue.switchAffichage();
		}
		else if(keycode ==Input.Keys.M)
		{

		}

		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		keyPressed=false;
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		actualMousePoisition.x = screenX; actualMousePoisition.y = screenY;
		if(onHoveredWidget==null)
		{
			boolean isOverSomething=false;
			Iterator<OnHoverableWidget> iterat = widgets.iterator();
			OnHoverableWidget w = null;
			while(!isOverSomething  && iterat.hasNext())
			{
				w = iterat.next();
				isOverSomething = w.testMousePosition(actualMousePoisition);
				
			}
			if(w!=null && isOverSomething)
			{
				w.onHoverAction();
				onHoveredWidget = w;
			}
			else
			{
				Vector3 p = vue.getUnprojectedPoint(screenX, screenY);
				actualMousePoisition.x = p.x; actualMousePoisition.y = p.z;	
			}
		}
		else{
			if(!onHoveredWidget.testMousePosition(actualMousePoisition))
			{
				onHoveredWidget.onExitAction();
				onHoveredWidget = null;	
			};
		}
		return true;
	}
	
	@Override
	public boolean scrolled(int amount) {
		vue.translateY(amount*10f);
		return true;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {			
		partie.ajoutTour();
		return false;
	}


}
