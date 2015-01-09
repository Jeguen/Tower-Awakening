 // Copyright © 2014, 2015 Rodolphe Cargnello, rodolphe.cargnello@gmail.com
 
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

import java.util.Locale;
import java.util.ResourceBundle;

import awakening.game.TAGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Options
 * 
 * @author rodolphe-c
 *
 */
public class Options implements Screen
{
	///Game
	private TAGame game;
	private Music sound;
	private Sound effect;
	private Locale[] locales = {Locale.ENGLISH, Locale.FRENCH, Locale.ITALIAN};
	private ResourceBundle language;
	
	///Stage
	private Stage stage;
	private Texture background;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private StretchViewport view;
	
	///Widgets
	private Image widgetsBackground;
	private Label title;
	private Skin skin;
	private TextButton btnAudio;
	private TextButton btnControls;
	private TextButton btnGraphics;
	private TextButton btnBack;
	
	/**
	 * Constructor
	 * 
	 * @param game Tower Awakening's Game
	 * @param sound Main menu's music
	 * @param effect Button's effect
	 */
	public Options(TAGame game, Music sound, Sound effect)
	{
		this.game = game;
		this.sound = sound;
		this.effect = effect;
		stage = new Stage();
		
		
		//Language
		try
		{
			if(game.getLanguage().equals("ENGLISH"))
			{
				language = ResourceBundle.getBundle("awakening.menu.res_en_EN", locales[0]);
			}
			else if (game.getLanguage().equals("FRENCH"))
			{
				language = ResourceBundle.getBundle("awakening.menu.res_fr_FR", locales[1]);
			}
			else if (game.getLanguage().equals("ITALIAN"))
			{
				language = ResourceBundle.getBundle("awakening.menu.res_it_IT", locales[2]);
			}
			else
			{
				language = ResourceBundle.getBundle("awakening.menu.res", locales[0]);
			}
		}
		catch(java.util.MissingResourceException e)
		{
			System.out.println("yolo");
		}
		
		///Viewport
		camera=new OrthographicCamera();
		view = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getWidth(), camera);
		
		///Background
		background = new Texture(Gdx.files.internal("img/menu/Background-1.png"));
		batch = new SpriteBatch();
		
		///Skin
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		///Title
		title = new Label(language.getString("label_options_menu"), skin);
		
		///Background Widget
		widgetsBackground = new Image(new Texture(Gdx.files.internal("img/widget/bg widgets.png")));
				
		///Back Button
		btnBack = new TextButton(language.getString("button_back"), skin);
		
		///Graphics Button
		btnGraphics = new TextButton(language.getString("button_graphics"), skin);
		
		///Controls Button
		btnControls = new TextButton(language.getString("button_controls"), skin);
		
		///Audio Button
		btnAudio = new TextButton(language.getString("button_audio"), skin);
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
						game.setScreen(new MainMenu(game, sound, effect));
						return false;	
					}
				}
				
		);
		stage.addActor(btnBack);
		
		///Controls Button
				btnControls.setWidth(widgetsBackground.getWidth()-20);
				btnControls.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 2*btnGraphics.getHeight());
				btnControls.addListener
				(
						new ClickListener() 
						{
							public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
							{
								effect.play(game.getSoundVolume());
								game.setScreen(new Controls(game, sound, effect));
								return false;	
							}
						}
						
				);
				stage.addActor(btnControls);
		
		///Graphics Button
		btnGraphics.setWidth(widgetsBackground.getWidth()-20);
		btnGraphics.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + btnBack.getHeight());
		btnGraphics.addListener
		(
				new ClickListener() 
				{
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						game.setScreen(new Graphics(game, sound, effect));
						return false;	
					}
				}
				
		);
		stage.addActor(btnGraphics);
		
		///Audio Button
		btnAudio.setWidth(widgetsBackground.getWidth()-20);
		btnAudio.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 3*btnControls.getHeight());
		btnAudio.addListener
		(
				new ClickListener() 
				{
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						game.setScreen(new Audio(game, sound, effect));
						return false;	
					}
				}
				
		);
		stage.addActor(btnAudio);
		
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.graphics.setDisplayMode(game.getSize().width, game.getSize().height, game.isFullscreen());
		
		///Settings
		sound.setVolume(game.getMusicVolume());
		
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
