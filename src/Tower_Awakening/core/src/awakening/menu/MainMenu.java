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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * MainMenu
 * 
 * @author rodolphe-c
 *
 */
public class MainMenu implements Screen
{
	///Game
	private TAGame game;
	private Music music;
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
	private TextButton btnSolo;
	private TextButton btnMultiplayer;
	private TextButton btnOptions;
	private TextButton btnExit;

	/**
	 * Constructor
	 * 
	 * @param game Tower Awakening's Game
	 */
	public MainMenu(TAGame game)
	{
		super();
		this.game = game;
		this.music = Gdx.audio.newMusic(Gdx.files.internal("sound/menu/Dark Intro_0.ogg"));
		effect = Gdx.audio.newSound(Gdx.files.internal("sound/widget/Button Sound 53.wav"));
		stage = new Stage();
		
		///Viewport
		camera=new OrthographicCamera();
		view = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getWidth(), camera);
		
		///Background
		background = new Texture(Gdx.files.internal("img/menu/background-menu.png"));
		batch = new SpriteBatch();
				
		///Skin
		skin = new Skin(Gdx.files.internal("uiskin.json"));
				
		///Title
		title = new Label("Tower Awakening", skin);
				
		///Widgets Background
		widgetsBackground = new Image(new Texture(Gdx.files.internal("img/widget/bg widgets.png")));
				
		///Exit Button
		btnExit = new TextButton("Exit", skin);
				
		///Options Button
		btnOptions = new TextButton("Options", skin);
				
		///Multiplayer Button
		btnMultiplayer = new TextButton("Multiplayer", skin);
				
		///Solo Button
		btnSolo = new TextButton("Solo", skin);
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param game Tower Awakening's Game
	 * @param sound Main menu's music
	 * @param effect Button's effect
	 */
	public MainMenu(TAGame game, Music sound, Sound effect)
	{
		this.game = game;
		this.music = sound;
		this.effect = effect;
		stage = new Stage();
		
		///Viewport
		camera=new OrthographicCamera();
		view = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getWidth(), camera);
		
		///Background
		background = new Texture(Gdx.files.internal("img/menu/background-menu.png"));
		batch = new SpriteBatch();
		
		///Skin
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		///Title
		title = new Label("Tower Awakening", skin);
		
		///Widgets Background
		widgetsBackground = new Image(new Texture(Gdx.files.internal("img/widget/bg widgets.png")));
		
		///Exit Button
		btnExit = new TextButton("Exit", skin);
		
		///Options Button
		btnOptions = new TextButton("Options", skin);
		
		///Multiplayer Button
		btnMultiplayer = new TextButton("Multiplayer", skin);
		
		///Solo Button
		btnSolo = new TextButton("Solo", skin);
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
		
		///Exit Button
		btnExit.setWidth(widgetsBackground.getWidth()-20);
		btnExit.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30);
		btnExit.addListener
		(
				new ClickListener() 
				{
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						effect.dispose();
						music.dispose();
						Gdx.app.exit();
						return false;	
					}
				}
				
		);
		stage.addActor(btnExit);
		
		///Options Button
		btnOptions.setWidth(widgetsBackground.getWidth()-20);
		btnOptions.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + btnExit.getHeight());
		btnOptions.addListener
		(
				new ClickListener() 
				{
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						game.setScreen(new Options(game, music, effect));
						return false;	
					}
				}
				
		);
		stage.addActor(btnOptions);
		
		///Multiplayer Button
		btnMultiplayer.setWidth(widgetsBackground.getWidth()-20);
		btnMultiplayer.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 2*btnOptions.getHeight());
		btnMultiplayer.addListener
		(
				new ClickListener() 
				{
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						return false;	
					}
				}
				
		);
		stage.addActor(btnMultiplayer);
		
		///Solo Button
		btnSolo.setWidth(widgetsBackground.getWidth()-20);
		btnSolo.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2 + 10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 3*btnMultiplayer.getHeight());
		btnSolo.addListener
		(
				new ClickListener() 
				{
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						return false;	
					}
				}
				
		);
		stage.addActor(btnSolo);
		
		///Music
		if(!music.isPlaying())
		{
			music.play();
			music.setLooping(true);
		}	
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.graphics.setDisplayMode((int)game.getSize().x, (int)game.getSize().y, game.isFullscreen());
		Gdx.graphics.setVSync(true);
		
		///Show DisplayMode (Test)
		for(DisplayMode s : Gdx.app.getGraphics().getDisplayModes())
		{
			System.out.println(s);
		}
		
		///Settings
		music.setVolume(game.getMusicVolume());
		
		///Cursor Skin
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
		music.dispose();
		effect.dispose();
	}

}
