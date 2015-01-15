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

package awakening.view.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import awakening.control.moteur.TAGame;
import awakening.modele.field.Field;
import awakening.view.GameWidget.BoutonShop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Audio
 * 
 * @author rodolphe-c
 *
 */
public class MapSelection implements Screen
{
	
	///Maps
	ArrayList<Field> mapListes = new ArrayList<Field>();
	
	///Game
	private TAGame game;
	private Music sound;
	private Sound effect;	
	private Locale[] locales = {Locale.ENGLISH, Locale.FRENCH, Locale.ITALIAN};
	private ResourceBundle language;
	private ArrayList<BoutonShop> maps;
	private int indexMap = 0;
	
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
	private TextButton btnJeu;
	private TextButton btnBack;

	private ImageButton btnNext;
	private ImageButton btnPrevious;

	/**
	 * Constructor
	 * 
	 * @param game Tower Awakening's Game
	 * @param sound Main menu's music
	 * @param effect Button's effect
	 */
	public MapSelection(final TAGame game, Music sound, final Sound effect)
	{
		this.game = game;
		this.sound = sound;
		this.effect = effect;
		stage = new Stage();
		
		try
		{
			File monsterDirectory = Gdx.files.internal("Field").file();
			for(File ft : monsterDirectory.listFiles())
			{
				if(ft.isFile())
					if(ft.getName().endsWith("mta"))
					{
						Field newMap = Field.loadTower(ft);
						if(newMap != null) 
							mapListes.add(newMap);
					}
			}
			
			if(game.getLanguage().equals("ENGLISH"))
			{
				language = ResourceBundle.getBundle("awakening.view.menu.res_en_EN", locales[0]);
			}
			else if (game.getLanguage().equals("FRENCH"))
			{
				language = ResourceBundle.getBundle("awakening.view.menu.res_fr_FR", locales[1]);
			}
			else if (game.getLanguage().equals("ITALIAN"))
			{
				language = ResourceBundle.getBundle("awakening.view.menu.res_it_IT", locales[2]);
			}
			else
			{
				language = ResourceBundle.getBundle("awakening.view.menu.res", locales[0]);
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
		background = new Texture(Gdx.files.internal("img/menu/Background-2.png"));
		batch = new SpriteBatch();
		
		///Skin
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		///Title
		title = new Label(language.getString("label_map_selection"), skin);
		
		///Widgets Background
		widgetsBackground = new Image(new Texture(Gdx.files.internal("img/widget/window_selection.png")));
		widgetsBackground.setSize(Gdx.app.getGraphics().getWidth() - Gdx.app.getGraphics().getWidth()/8, Gdx.app.getGraphics().getHeight() - Gdx.app.getGraphics().getHeight()/8);
		
		///Back Button
		btnBack = new TextButton(language.getString("button_back"), skin);
		btnJeu = new TextButton(language.getString("button_game"), skin);
		
		///Next Button
		Texture t1 = new Texture(Gdx.files.internal("img/widget/arrow_hover_right.png"));
		Texture t2 = new Texture(Gdx.files.internal("img/widget/arrow_right.png"));
		ImageButtonStyle style1 = new ImageButtonStyle
				(
						new TextureRegionDrawable(new TextureRegion(t1,0,0,t1.getWidth(), t1.getHeight())), 
						new TextureRegionDrawable(new TextureRegion(t2,0,0,t2.getWidth(), t2.getHeight())),
						new TextureRegionDrawable(new TextureRegion(t1,0,0,t1.getWidth(), t1.getHeight())), 
						new TextureRegionDrawable(new TextureRegion(t2,0,0,t2.getWidth(), t2.getHeight())),
						new TextureRegionDrawable(new TextureRegion(t1,0,0,t1.getWidth(), t1.getHeight())), 
						new TextureRegionDrawable(new TextureRegion(t2,0,0,t2.getWidth(), t2.getHeight()))
				);
		btnNext = new ImageButton(style1); 
			
		///Previous Button
		Texture t3 = new Texture(Gdx.files.internal("img/widget/arrow_hover_left.png"));
		Texture t4 = new Texture(Gdx.files.internal("img/widget/arrow_left.png"));
		ImageButtonStyle style2 = new ImageButtonStyle
				(
						new TextureRegionDrawable(new TextureRegion(t3,0,0,t3.getWidth(), t3.getHeight())), 
						new TextureRegionDrawable(new TextureRegion(t4,0,0,t4.getWidth(), t4.getHeight())),
						new TextureRegionDrawable(new TextureRegion(t3,0,0,t3.getWidth(), t3.getHeight())), 
						new TextureRegionDrawable(new TextureRegion(t4,0,0,t4.getWidth(), t4.getHeight())),
						new TextureRegionDrawable(new TextureRegion(t3,0,0,t3.getWidth(), t3.getHeight())), 
						new TextureRegionDrawable(new TextureRegion(t4,0,0,t4.getWidth(), t4.getHeight()))
				);
		
		btnPrevious = new ImageButton(style2);
		
		maps = new ArrayList<BoutonShop>();
		

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

	@Override
	public void hide() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.graphics.setDisplayMode(game.getSize().width, game.getSize().height, game.isFullscreen());
		
		///Settings
		effect.setVolume(0, game.getSoundVolume());
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
	public void resume() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void show() 
	{
		Gdx.input.setInputProcessor(stage);
		
		///Title
		title.setPosition(Gdx.app.getGraphics().getWidth()/2 - title.getWidth()/2, Gdx.app.getGraphics().getHeight() - 30);
		stage.addActor(title);
		
		///Widgets Background
		widgetsBackground.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2);
		stage.addActor(widgetsBackground);
		
		///Back Button
		btnBack.setWidth(100);
		btnBack.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnBack.getWidth()/2, Gdx.app.getGraphics().getHeight() - widgetsBackground.getHeight() + 10);
		
		btnBack.addListener
		(
				new ClickListener() 
				{
					@Override
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						game.setScreen(new MainMenu(game, sound, effect));
						return false;	
					}
				}
		);
		stage.addActor(btnBack);
		btnJeu.setWidth(200);
		btnJeu.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnJeu.getWidth()/2,
				Gdx.app.getGraphics().getHeight() - widgetsBackground.getHeight() + 40);
		
		btnJeu.addListener
		(
				new ClickListener() 
				{
					@Override
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						game.setScreen(new Solo(game, mapListes.get(indexMap)));
						return true;	
					}
				}
		);
		stage.addActor(btnJeu);
		
		///Next Button
		btnNext.setWidth(75);
		btnNext.setHeight(75);
		btnNext.setPosition(Gdx.app.getGraphics().getWidth() - Gdx.app.getGraphics().getWidth()/8 - btnNext.getWidth(), Gdx.app.getGraphics().getHeight()/2);
		btnNext.addListener
		(
				new ClickListener() 
				{
					@Override
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						disableMap();
						indexMap++;
						indexMap%=mapListes.size();
						changeMap();
						return false;	
					}
				}
				
		);
		stage.addActor(btnNext);
		
		///Previous Button
		btnPrevious.setWidth(75);
		btnPrevious.setHeight(75);
		btnPrevious.setPosition(Gdx.app.getGraphics().getWidth()/8, Gdx.app.getGraphics().getHeight()/2);
		btnPrevious.addListener
		(
				new ClickListener() 
				{
					@Override
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						disableMap();
						indexMap--;
						if(indexMap<0)
							indexMap=mapListes.size()-1;
						changeMap();
						return false;	
					}
				}
				
		);
		stage.addActor(btnPrevious);
		for(Field f : mapListes)
		{
			BoutonShop mapButton = new BoutonShop(f.getTexture(), Gdx.app.getGraphics().getWidth()/4, Gdx.app.getGraphics().getWidth()/4);
			mapButton.setPosition(Gdx.app.getGraphics().getWidth()/2 - mapButton.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - mapButton.getHeight()/2);
			mapButton.addListener
			(
					new ClickListener() 
					{
						@Override
						public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
						{
							effect.play(game.getSoundVolume());
							return false;	
						}
					}
			);
			stage.addActor(mapButton);
			maps.add(mapButton);
		}
	}
	
	public void disableMap()
	{
		maps.get(indexMap).setVisible(false);
		maps.get(indexMap).setTouchable(Touchable.disabled);
	}
	
	public void changeMap()
	{
		maps.get(indexMap).setVisible(true);
		maps.get(indexMap).setTouchable(Touchable.enabled);;
	}
}
