 // Copyright © 2014 Rodolphe Cargnello, rodolphe.cargnello@gmail.com
 
 // Licensed under the Apache License, Version 2.0 (the "License");
 // you may not use this file except in compliance with the License.
 // You may obtain a copy of the License at
 // 
 // http://www.apache.org/licenses/LICENSE-2.0
 // 
 // Unless required by applicable law or agreed to in writing, software
 // distributed under the License is distributed on an "AS IS" BASIS,
 // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 // See the License for the specific language governing permissions and
 // limitations under the License.

package awakening.menu;

import awakening.game.TAGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Audio
 * 
 * @author rodolphe-c
 *
 */
public class Graphics implements Screen
{
	///Game
	private TAGame game;
	private Music sound;
	private Sound effect;
	
	///Stage
	private Stage stage;
	private SpriteBatch batch;
	private Texture background;
	private OrthographicCamera camera;
	private StretchViewport view;
	
	///Widgets
	private Image widgetsBackground;
	private Label title;
	private Skin skin;
	private Label lblResolution;
	private SelectBox<DisplayMode> slbResolutions;
	private CheckBox cbxFullscreen;
	private TextButton btnBack;

	/**
	 * Constructor
	 * 
	 * @param game Tower Awakening's Game
	 * @param sound Main menu's music
	 * @param effect Button's effect
	 */
	public Graphics(TAGame game, Music sound, Sound effect)
	{
		this.game = game;
		this.sound = sound;
		this.effect = effect;
		stage = new Stage();
		
		///Viewport
		camera=new OrthographicCamera();
		view = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getWidth(), camera);
		
		///Background
		background = new Texture(Gdx.files.internal("img/menu/Background-3.png"));
		batch = new SpriteBatch();
		
		///Skin
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		///Title
		title = new Label("Audio", skin);
		
		///Widgets Background
		widgetsBackground = new Image(new Texture(Gdx.files.internal("img/widget/bg widgets.png")));
		
		///Back Button
		btnBack = new TextButton("Back", skin);
		
		///CheckBox Fullscreen
		cbxFullscreen = new CheckBox("Fullscreen", skin);
		
		///SelectBox Resolution
		slbResolutions = new SelectBox<DisplayMode>(skin);
		
		///Label Resolution
		lblResolution = new Label("Resolutions", skin);
	}
	
	@Override
	public void show() 
	{
		Gdx.input.setInputProcessor(stage);
		
		///Title
		title.setPosition(Gdx.app.getGraphics().getWidth()/2 - title.getWidth()/2, Gdx.app.getGraphics().getHeight() - 50);
		stage.addActor(title);
		
		///Widgets Background
		widgetsBackground.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2);
		stage.addActor(widgetsBackground);
		
		///Back Button
		btnBack.setWidth(widgetsBackground.getWidth()-20);
		btnBack.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30);
		
		btnBack.addListener
		(
				new ClickListener() 
				{
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						game.setScreen(new Options(game, sound, effect));
						return false;	
					}
				}
				
		);
		stage.addActor(btnBack);	
		
		///Fullscreen CheckBox
		cbxFullscreen.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 40 + btnBack.getHeight());
		if(game.isFullscreen())
		{
			cbxFullscreen.setChecked(true);
		}
		stage.addActor(cbxFullscreen);
		
		///Resolution SelectBox 
		slbResolutions.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 70 + cbxFullscreen.getHeight());
		slbResolutions.setWidth(widgetsBackground.getWidth()-20);
		slbResolutions.setItems(Gdx.graphics.getDisplayModes());
		
		int i = 0;
		while(Gdx.graphics.getDisplayModes()[i].width != Gdx.app.getGraphics().getWidth() && Gdx.graphics.getDisplayModes()[i].height != Gdx.app.getGraphics().getHeight() && i < Gdx.graphics.getDisplayModes().length)
		{
			i++;
		}
		
		System.out.println(Gdx.graphics.getDisplayModes()[i]);
		
		slbResolutions.setSelectedIndex(i);
		slbResolutions.addListener
		(
			new ChangeListener() 
			{
				public void changed (ChangeEvent event, Actor actor) 
				{
					System.out.println(slbResolutions.getSelected());
				}
			}
		);
		
		stage.addActor(slbResolutions);
		
		///Resolution Label 
		lblResolution.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 90 + slbResolutions.getHeight());
		stage.addActor(lblResolution);
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.graphics.setDisplayMode((int)game.getSize().x, (int)game.getSize().y, game.isFullscreen());
		
		///Settings
		sound.setVolume(game.getMusicVolume());
		game.setDisplay(slbResolutions.getSelected());
		game.setSize(slbResolutions.getSelected().width, slbResolutions.getSelected().height);
		if(!cbxFullscreen.isChecked() && !Gdx.app.getGraphics().isFullscreen())
		{
			game.setFullscreen(false);
			Gdx.graphics.setDisplayMode(game.getDisplay().width, game.getDisplay().height, false);
			resize(game.getDisplay().width, game.getDisplay().height); 
		}
		else if (!cbxFullscreen.isChecked() && Gdx.app.getGraphics().isFullscreen())
		{
			game.setFullscreen(false);
			Gdx.graphics.setDisplayMode(game.getDisplay().width, game.getDisplay().height, false);
			resize(game.getDisplay().width, game.getDisplay().height); 
		}
		else if (cbxFullscreen.isChecked())
		{
			game.setFullscreen(true);
			Gdx.graphics.setDisplayMode(game.getDisplay().width, game.getDisplay().height, true);
			resize(game.getDisplay().width, game.getDisplay().height); 
		}
		
		///Cursor
		Pixmap pm = new Pixmap(Gdx.files.internal("img/cursor.png"));
		Gdx.input.setCursorImage(pm, 0, 0);
		pm.dispose();
		
		///Draw Background
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
		batch.end();
		
		///Draw Stage
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) 
	{
		view.update(width, height);
		camera.setToOrtho(false,width,height);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		stage.dispose();
		skin.dispose();
		game.dispose();
		sound.dispose();
		effect.dispose();
	}
}
