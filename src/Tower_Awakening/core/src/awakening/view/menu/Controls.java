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


import java.util.Locale;
import java.util.ResourceBundle;

import awakening.control.moteur.TAGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Options
 * 
 * @author rodolphe-c
 *
 */
public class Controls implements Screen
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
	private Label lblLangage;
	private SelectBox<String> slbLangages;
	private Image widgetsBackground;
	private Label title;
	private Skin skin;
	private Label lblZoomIn;
	private TextButton btnZoomIn;
	private Label lblZoomOut;
	private TextButton btnZoomOut;
	private Label lblUp;
	private TextButton btnUp;
	private Label lblDown;
	private TextButton btnDown;
	private Label lblLeft;
	private TextButton btnLeft;
	private Label lblRight;
	private TextButton btnRight;
	private TextButton btnBack;
	
	/**
	 * Constructor
	 * 
	 * @param game Tower Awakening's Game
	 * @param sound Main menu's music
	 * @param effect Button's effect
	 */
	public Controls(TAGame game, Music sound, Sound effect)
	{
		this.game = game;
		this.sound = sound;
		this.effect = effect;
		stage = new Stage();
		
		
		///Language
		try
		{
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
		background = new Texture(Gdx.files.internal("img/menu/Background-4.png"));
		batch = new SpriteBatch();
		
		///Skin
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		///Title
		title = new Label(language.getString("label_controls_menu"), skin);
		
		///Background Widget
		widgetsBackground = new Image(new Texture(Gdx.files.internal("img/widget/window_large.png")));
		
		///Langage Checkbox
		lblLangage = new Label(language.getString("label_language"), skin);
		String[] lang = {"English","Francais","Italiano"};
		slbLangages = new SelectBox<String>(skin);
		slbLangages.setItems(lang);
		
		///Back Button
		btnBack = new TextButton(language.getString("button_back"), skin);
		btnBack.setName(language.getString("button_back"));
		
		///Right Button
		lblRight = new Label(language.getString("label_right"), skin);
		btnRight = new TextButton(game.getRightKey(), skin);
		btnRight.setName("right");
		
		///Left Button
		lblLeft = new Label(language.getString("label_left"), skin);
		btnLeft = new TextButton(game.getLeftKey(), skin);
		btnLeft.setName("left");
		
		///Down Button
		lblDown = new Label(language.getString("label_down"), skin);
		btnDown = new TextButton(game.getDownKey(), skin);
		btnDown.setName("down");
		
		///Up Button
		lblUp = new Label(language.getString("label_up"), skin);
		btnUp = new TextButton(game.getUpKey(), skin);
		btnUp.setName("up");
		
		///Zoom In Button
		lblZoomIn = new Label(language.getString("label_zoom_in"), skin);
		btnZoomIn = new TextButton(game.getZoomInKey(), skin);
		btnZoomIn.setName("zoom+");
				
		///Zoom Out Button
		lblZoomOut = new Label(language.getString("label_zoom_out"), skin);
		btnZoomOut = new TextButton(game.getZoomOutKey(), skin);
		btnZoomOut.setName("zoom-");
		
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void show() 
	{		
		///Title
		title.setPosition(Gdx.app.getGraphics().getWidth()/2 - title.getWidth()/2, Gdx.app.getGraphics().getHeight() - 50);
		stage.addActor(title);
		
		///Widgets Background
		widgetsBackground.setPosition(Gdx.app.getGraphics().getWidth()/2 - widgetsBackground.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2);
		stage.addActor(widgetsBackground);
		
		///Back Button
		btnBack.setWidth(widgetsBackground.getWidth()/2.5f-20);
		btnBack.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnBack.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30);
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
		
		///Left Button
		btnLeft.setWidth(widgetsBackground.getWidth()/2.5f-20);
		btnLeft.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnLeft.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 3*btnBack.getHeight());
		btnLeft.addListener
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
		stage.addActor(btnLeft);
		lblLeft.setPosition(btnLeft.getX() - lblLeft.getWidth() -10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 3*btnBack.getHeight());
		stage.addActor(lblLeft);
		
		///Right Button
		btnRight.setWidth(widgetsBackground.getWidth()/2.5f-20);
		btnRight.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnRight.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 2*btnBack.getHeight());
		btnRight.addListener
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
		stage.addActor(btnRight);
		lblRight.setPosition(btnRight.getX() - lblRight.getWidth() -10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 2*btnBack.getHeight());
		stage.addActor(lblRight);
		
		
		///Down Button
		btnDown.setWidth(widgetsBackground.getWidth()/2.5f-20);
		btnDown.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnDown.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 4*btnLeft.getHeight());
		btnDown.addListener
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
		stage.addActor(btnDown);
		lblDown.setPosition(btnDown.getX() - lblDown.getWidth() -10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 4*btnLeft.getHeight());
		stage.addActor(lblDown);
		
		
		
		///Up Button
		btnUp.setWidth(widgetsBackground.getWidth()/2.5f-20);
		btnUp.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnUp.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 5*btnDown.getHeight());
		btnUp.addListener
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
		stage.addActor(btnUp);
		lblUp.setPosition(btnUp.getX() - lblUp.getWidth() -10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 5*btnDown.getHeight());
		stage.addActor(lblUp);
		
		///Zoom In Button
		btnZoomIn.setWidth(widgetsBackground.getWidth()/2.5f-20);
		btnZoomIn.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnUp.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 6*btnBack.getHeight());
		btnZoomIn.addListener
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
		stage.addActor(btnZoomIn);
		lblZoomIn.setPosition(btnZoomIn.getX() - lblZoomIn.getWidth() -10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 6*btnBack.getHeight());
		stage.addActor(lblZoomIn);
		
		///Zoom In Button
		btnZoomOut.setWidth(widgetsBackground.getWidth()/2.5f-20);
		btnZoomOut.setPosition(Gdx.app.getGraphics().getWidth()/2 - btnZoomOut.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 7*btnBack.getHeight());
		btnZoomOut.addListener
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
		stage.addActor(btnZoomOut);
		lblZoomOut.setPosition(btnZoomOut.getX() - lblZoomOut.getWidth() -10, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 7*btnBack.getHeight());
		stage.addActor(lblZoomOut);
		
		///Language SelectBox
		if (game.getLanguage().equals("ENGLISH"))
		{
			slbLangages.setSelected("English");
		}
		else if (game.getLanguage().equals("FRENCH"))
		{
			slbLangages.setSelected("Francais");
		}
		else if (game.getLanguage().equals("ITALIAN"))
		{
			slbLangages.setSelected("Italiano");
		}
		slbLangages.setWidth(widgetsBackground.getWidth()/2.5f-20);
		slbLangages.setPosition(Gdx.app.getGraphics().getWidth()/2 - slbLangages.getWidth()/2, Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 9*btnBack.getHeight());
		slbLangages.addListener
		(
			new ChangeListener() 
			{
				public void changed (ChangeEvent event, Actor actor) 
				{
					System.out.println(slbLangages.getSelected());
					if(slbLangages.getSelected().equals("English"))
					{
						game.setLanguage("ENGLISH");
						try
						{
							language = ResourceBundle.getBundle("awakening.menu.res_en_EN", locales[0]);
						}
						catch(java.util.MissingResourceException e)
						{
							System.out.println("yolo1");
						}
					}
					else if (slbLangages.getSelected().equals("Francais"))
					{
						game.setLanguage("FRENCH");
						try
						{
							language = ResourceBundle.getBundle("awakening.menu.res_fr_FR", locales[1]);
						}
						catch(java.util.MissingResourceException e)
						{
							System.out.println("yolo2");
						}
					}
					else if (slbLangages.getSelected().equals("Italiano"))
					{
						game.setLanguage("ITALIAN");
						try
						{
							language = ResourceBundle.getBundle("awakening.menu.res_it_IT", locales[2]);
						}
						catch(java.util.MissingResourceException e)
						{
							System.out.println("yolo3");
						}
					}
				}
			}
		);
		stage.addActor(slbLangages);
		lblLangage.setPosition(slbLangages.getX() - lblLangage.getWidth() -10,  Gdx.app.getGraphics().getHeight()/2 - widgetsBackground.getHeight()/2 + 30 + 9*btnBack.getHeight());
		stage.addActor(lblLangage);
	}

	@Override
	public void render(float delta) 
	{
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.graphics.setDisplayMode(game.getSize().width, game.getSize().height, game.isFullscreen());
		
		if(btnUp.isChecked())
		{
			btnUp.setColor(Color.RED);
			btnUp.setTouchable(Touchable.disabled);
			btnDown.setTouchable(Touchable.disabled);
			btnLeft.setTouchable(Touchable.disabled);
			btnRight.setTouchable(Touchable.disabled);
			btnZoomIn.setTouchable(Touchable.disabled);
			btnZoomOut.setTouchable(Touchable.disabled);
			btnBack.setTouchable(Touchable.disabled);
			setControls(btnUp);
		}	
		else if(btnDown.isChecked())
		{
			btnDown.setColor(Color.RED);
			btnUp.setTouchable(Touchable.disabled);
			btnDown.setTouchable(Touchable.disabled);
			btnLeft.setTouchable(Touchable.disabled);
			btnRight.setTouchable(Touchable.disabled);
			btnZoomIn.setTouchable(Touchable.disabled);
			btnZoomOut.setTouchable(Touchable.disabled);
			btnBack.setTouchable(Touchable.disabled);
			setControls(btnDown);
		}	
		else if(btnLeft.isChecked())
		{
			btnLeft.setColor(Color.RED);
			btnUp.setTouchable(Touchable.disabled);
			btnDown.setTouchable(Touchable.disabled);
			btnLeft.setTouchable(Touchable.disabled);
			btnRight.setTouchable(Touchable.disabled);
			btnZoomIn.setTouchable(Touchable.disabled);
			btnZoomOut.setTouchable(Touchable.disabled);
			btnBack.setTouchable(Touchable.disabled);
			setControls(btnLeft);
		}
		else if(btnRight.isChecked())
		{
			btnRight.setColor(Color.RED);
			btnUp.setTouchable(Touchable.disabled);
			btnDown.setTouchable(Touchable.disabled);
			btnLeft.setTouchable(Touchable.disabled);
			btnRight.setTouchable(Touchable.disabled);
			btnZoomIn.setTouchable(Touchable.disabled);
			btnZoomOut.setTouchable(Touchable.disabled);
			btnBack.setTouchable(Touchable.disabled);
			setControls(btnRight);
		}
		else if(btnZoomIn.isChecked())
		{
			btnZoomIn.setColor(Color.RED);
			btnUp.setTouchable(Touchable.disabled);
			btnDown.setTouchable(Touchable.disabled);
			btnLeft.setTouchable(Touchable.disabled);
			btnRight.setTouchable(Touchable.disabled);
			btnZoomIn.setTouchable(Touchable.disabled);
			btnZoomOut.setTouchable(Touchable.disabled);
			btnBack.setTouchable(Touchable.disabled);
			setControls(btnZoomIn);
		}
		else if(btnZoomOut.isChecked())
		{
			btnZoomOut.setColor(Color.RED);
			btnUp.setTouchable(Touchable.disabled);
			btnDown.setTouchable(Touchable.disabled);
			btnLeft.setTouchable(Touchable.disabled);
			btnRight.setTouchable(Touchable.disabled);
			btnZoomIn.setTouchable(Touchable.disabled);
			btnZoomOut.setTouchable(Touchable.disabled);
			btnBack.setTouchable(Touchable.disabled);
			setControls(btnZoomOut);
		}
		
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
	
	/**
	 * Set Key's controls
	 * 
	 * @param b TextButton
	 */
	public void setControls(TextButton b)
	{
			if (Gdx.input.isKeyPressed(Keys.A))
			{
				changeKey(b, "A", Input.Keys.A);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.B))
			{
				changeKey(b, "B", Input.Keys.B);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.C))
			{
				changeKey(b, "C", Input.Keys.C);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.D))
			{
				changeKey(b, "D", Input.Keys.D);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.E))
			{
				changeKey(b, "E", Input.Keys.E);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.F))
			{
				changeKey(b, "F", Input.Keys.F);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.G))
			{
				changeKey(b, "G", Input.Keys.G);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.H))
			{
				changeKey(b, "H", Input.Keys.H);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.I))
			{
				changeKey(b, "I", Input.Keys.I);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.J))
			{
				changeKey(b, "J", Input.Keys.J);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.K))
			{
				changeKey(b, "K", Input.Keys.K);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.L))
			{
				changeKey(b, "L", Input.Keys.L);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.M))
			{
				changeKey(b, "M", Input.Keys.M);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.N))
			{
				changeKey(b, "N", Input.Keys.N);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.O))
			{
				changeKey(b, "O", Input.Keys.O);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.P))
			{
				changeKey(b, "P", Input.Keys.P);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.Q))
			{
				changeKey(b, "Q", Input.Keys.Q);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.R))
			{
				changeKey(b, "R", Input.Keys.R);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.S))
			{
				changeKey(b, "S", Input.Keys.S);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.T))
			{
				changeKey(b, "T", Input.Keys.T);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.U))
			{
				changeKey(b, "U", Input.Keys.U);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.V))
			{
				changeKey(b, "V", Input.Keys.V);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.W))
			{
				changeKey(b, "W", Input.Keys.W);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.X))
			{
				changeKey(b, "X", Input.Keys.X);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.Y))
			{
				changeKey(b, "Y", Input.Keys.Y);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.Z))
			{
				changeKey(b, "Z", Input.Keys.Z);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.UP))
			{
				changeKey(b, "UP", Input.Keys.UP);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.DOWN))
			{
				changeKey(b, "DOWN", Input.Keys.DOWN);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.LEFT))
			{
				changeKey(b, "LEFT", Input.Keys.LEFT);
				
			}
			else if (Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				changeKey(b, "RIGHT", Input.Keys.RIGHT);
				
			}
			
	}
	
	
	/**
	 * Set key to game' settings
	 * 
	 * @param b TextButton
	 * @param key Key's Name
	 * @param keyValue Key's value
	 */
	private void changeKey(TextButton b, String key, int keyValue) 
	{
		if(b.equals(btnUp))
		{
			btnUp.setText(key);
			game.setUpKey(key);
			game.setUp(keyValue);
			
			
			
			if (game.getLeft() == keyValue)
			{
				System.out.println(btnUp.getText());
				System.out.println(b.getText());
				btnLeft.setText("UNKNOWN");
				game.setLeft(Input.Keys.UNKNOWN);
				game.setLeftKey("UNKNOWN");
			}
			else if (game.getRight() == keyValue)
			{
				btnRight.setText("UNKNOWN");
				game.setRight(Input.Keys.UNKNOWN);
				game.setRightKey("UNKNOWN");
			}
			else if (game.getDown() == keyValue)
			{
				btnDown.setText("UNKNOWN");
				game.setDown(Input.Keys.UNKNOWN);
				game.setDownKey("UNKNOWN");
			}
			else if (game.getZoomIn() == keyValue)
			{
				btnZoomIn.setText("UNKNOWN");
				game.setZoomIn(Input.Keys.UNKNOWN);
				game.setZoomInKey("UNKNOWN");
			}
			else if (game.getZoomOut() == keyValue)
			{
				btnZoomOut.setText("UNKNOWN");
				game.setZoomOut(Input.Keys.UNKNOWN);
				game.setZoomOutKey("UNKNOWN");
			}
		}
		else if (b.equals(btnDown))
		{
			btnDown.setText(key);
			game.setDownKey(key);
			game.setDown(keyValue);
			
			if (game.getLeft() == keyValue)
			{
				btnLeft.setText("UNKNOWN");
				game.setLeft(Input.Keys.UNKNOWN);
				game.setLeftKey("UNKNOWN");
			}
			else if (game.getRight() == keyValue)
			{
				btnRight.setText("UNKNOWN");
				game.setRight(Input.Keys.UNKNOWN);
				game.setRightKey("UNKNOWN");
			}
			else if (game.getUp() == keyValue)
			{
				btnUp.setText("UNKNOWN");
				game.setUp(Input.Keys.UNKNOWN);
				game.setUpKey("UNKNOWN");
			}
			else if (game.getZoomIn() == keyValue)
			{
				btnZoomIn.setText("UNKNOWN");
				game.setZoomIn(Input.Keys.UNKNOWN);
				game.setZoomInKey("UNKNOWN");
			}
			else if (game.getZoomOut() == keyValue)
			{
				btnZoomOut.setText("UNKNOWN");
				game.setZoomOut(Input.Keys.UNKNOWN);
				game.setZoomOutKey("UNKNOWN");
			}
		}
		else if (b.equals(btnLeft))
		{
			btnLeft.setText(key);
			game.setLeftKey(key);
			game.setLeft(keyValue);
			
			if (game.getUp() == keyValue)
			{
				btnUp.setText("UNKNOWN");
				game.setUp(Input.Keys.UNKNOWN);
				game.setUpKey("UNKNOWN");
			}
			else if (game.getRight() == keyValue)
			{
				btnRight.setText("UNKNOWN");
				game.setRight(Input.Keys.UNKNOWN);
				game.setRightKey("UNKNOWN");
			}
			else if (game.getDown() == keyValue)
			{
				btnDown.setText("UNKNOWN");
				game.setDown(Input.Keys.UNKNOWN);
				game.setDownKey("UNKNOWN");
			}
			else if (game.getZoomIn() == keyValue)
			{
				btnZoomIn.setText("UNKNOWN");
				game.setZoomIn(Input.Keys.UNKNOWN);
				game.setZoomInKey("UNKNOWN");
			}
			else if (game.getZoomOut() == keyValue)
			{
				btnZoomOut.setText("UNKNOWN");
				game.setZoomOut(Input.Keys.UNKNOWN);
				game.setZoomOutKey("UNKNOWN");
			}
		}
		else if(b.equals(btnRight))
		{
			btnRight.setText(key);
			game.setRightKey(key);
			game.setRight(keyValue);
			
			if (game.getLeft() == keyValue)
			{
				btnLeft.setText("UNKNOWN");
				game.setLeft(Input.Keys.UNKNOWN);
				game.setLeftKey("UNKNOWN");
			}
			else if (game.getUp() == keyValue)
			{
				btnUp.setText("UNKNOWN");
				game.setUp(Input.Keys.UNKNOWN);
				game.setUpKey("UNKNOWN");
			}
			else if (game.getDown() == keyValue)
			{
				btnDown.setText("UNKNOWN");
				game.setDown(Input.Keys.UNKNOWN);
				game.setDownKey("UNKNOWN");
			}
			else if (game.getZoomIn() == keyValue)
			{
				btnZoomIn.setText("UNKNOWN");
				game.setZoomIn(Input.Keys.UNKNOWN);
				game.setZoomInKey("UNKNOWN");
			}
			else if (game.getZoomOut() == keyValue)
			{
				btnZoomOut.setText("UNKNOWN");
				game.setZoomOut(Input.Keys.UNKNOWN);
				game.setZoomOutKey("UNKNOWN");
			}
		}
		else if(b.equals(btnZoomIn))
		{
			btnZoomIn.setText(key);
			game.setZoomInKey(key);
			game.setZoomIn(keyValue);
			
			if (game.getLeft() == keyValue)
			{
				btnLeft.setText("UNKNOWN");
				game.setLeft(Input.Keys.UNKNOWN);
				game.setLeftKey("UNKNOWN");
			}
			else if (game.getRight() == keyValue)
			{
				btnRight.setText("UNKNOWN");
				game.setRight(Input.Keys.UNKNOWN);
				game.setRightKey("UNKNOWN");
			}
			else if (game.getDown() == keyValue)
			{
				btnDown.setText("UNKNOWN");
				game.setDown(Input.Keys.UNKNOWN);
				game.setDownKey("UNKNOWN");
			}
			else if (game.getUp() == keyValue)
			{
				btnUp.setText("UNKNOWN");
				game.setUp(Input.Keys.UNKNOWN);
				game.setUpKey("UNKNOWN");
			}
			else if (game.getZoomOut() == keyValue)
			{
				btnZoomOut.setText("UNKNOWN");
				game.setZoomOut(Input.Keys.UNKNOWN);
				game.setZoomOutKey("UNKNOWN");
			}
		}
		else if(b.equals(btnZoomOut))
		{
			btnZoomOut.setText(key);
			game.setZoomOutKey(key);
			game.setZoomOut(keyValue);
			
			if (game.getLeft() == keyValue)
			{
				btnLeft.setText("UNKNOWN");
				game.setLeft(Input.Keys.UNKNOWN);
				game.setLeftKey("UNKNOWN");
			}
			else if (game.getRight() == keyValue)
			{
				btnRight.setText("UNKNOWN");
				game.setRight(Input.Keys.UNKNOWN);
				game.setRightKey("UNKNOWN");
			}
			else if (game.getDown() == keyValue)
			{
				btnDown.setText("UNKNOWN");
				game.setDown(Input.Keys.UNKNOWN);
				game.setDownKey("UNKNOWN");
			}
			else if (game.getZoomIn() == keyValue)
			{
				btnZoomIn.setText("UNKNOWN");
				game.setZoomIn(Input.Keys.UNKNOWN);
				game.setZoomInKey("UNKNOWN");
			}
			else if (game.getUp() == keyValue)
			{
				btnUp.setText("UNKNOWN");
				game.setUp(Input.Keys.UNKNOWN);
				game.setUpKey("UNKNOWN");
			}
		}
		
		b.setChecked(false);
		btnUp.setTouchable(Touchable.enabled);
		btnDown.setTouchable(Touchable.enabled);
		btnLeft.setTouchable(Touchable.enabled);
		btnRight.setTouchable(Touchable.enabled);
		btnZoomIn.setTouchable(Touchable.enabled);
		btnZoomOut.setTouchable(Touchable.enabled);
		btnBack.setTouchable(Touchable.enabled);
		slbLangages.setTouchable(Touchable.enabled);
		btnUp.setColor(btnBack.getColor());
		btnDown.setColor(btnBack.getColor());
		btnLeft.setColor(btnBack.getColor());
		btnRight.setColor(btnBack.getColor());
		btnZoomIn.setColor(btnBack.getColor());
		btnZoomOut.setColor(btnBack.getColor());
	}
}


