// Copyright © 2014, 2015 Rodolphe Cargnello, rodolphe.cargnello@gmail.com
// Copyright © 2014, 2015 Swamynathan Candassamy, swamynathan.candassamy@u-psud.fr

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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;
import java.util.ResourceBundle;

import awakening.control.moteur.TAGame;

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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Audio
 * 
 * @author rodolphe-c
 * @author Jeguen
 *
 */
public class WaitingRoomServer implements Screen {
	// /Game
	private TAGame game;
	private Music sound;
	private Sound effect;
	private Locale[] locales = { Locale.ENGLISH, Locale.FRENCH, Locale.ITALIAN };
	private ResourceBundle language;

	// /Stage
	private Stage stage;
	private SpriteBatch batch;
	private Texture background;
	private OrthographicCamera camera;
	private StretchViewport view;

	// /Widgets
	private Image widgetsBackground;
	private Label title;
	private Skin skin;
	private TextButton btnBack;
	private Table chatBox;
	private ScrollPane scroll;
	private TextField message;
	private TextButton btnSend;
	private TextButton btnStart;

	private ServerSocket serverSocket;
	private Socket connection;

	private BufferedReader input;
	private PrintWriter output;

	private Thread th1;

	public static final int portEcoute = 1134;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            Tower Awakening's Game
	 * @param sound
	 *            Main menu's music
	 * @param effect
	 *            Button's effect
	 */
	public WaitingRoomServer(TAGame game, Music sound, Sound effect) {
		this.game = game;
		this.sound = sound;
		this.effect = effect;

		try {
			serverSocket = new ServerSocket(portEcoute);
		} catch (IOException e) {
			System.err.println("Creation de la socket impossible : " + e);
			System.exit(-1);
		}

		stage = new Stage();

		try {
			if (game.getLanguage().equals("ENGLISH")) {
				language = ResourceBundle.getBundle(
						"awakening.view.menu.res_en_EN", locales[0]);
			} else if (game.getLanguage().equals("FRENCH")) {
				language = ResourceBundle.getBundle(
						"awakening.view.menu.res_fr_FR", locales[1]);
			} else if (game.getLanguage().equals("ITALIAN")) {
				language = ResourceBundle.getBundle(
						"awakening.view.menu.res_it_IT", locales[2]);
			} else {
				language = ResourceBundle.getBundle("awakening.view.menu.res",
						locales[0]);
			}
		} catch (java.util.MissingResourceException e) {
			System.out.println("yolo");
		}

		// /Viewport
		camera = new OrthographicCamera();
		view = new StretchViewport(Gdx.app.getGraphics().getWidth(), Gdx.app
				.getGraphics().getWidth(), camera);

		// /Background
		background = new Texture(
				Gdx.files.internal("img/menu/Background-2.png"));
		batch = new SpriteBatch();

		// /Skin
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		// /Title
		title = new Label(language.getString("label_waiting_room"), skin);

		// /Widgets Background
		widgetsBackground = new Image(new Texture(
				Gdx.files.internal("img/widget/window_selection.png")));
		widgetsBackground.setSize(Gdx.app.getGraphics().getWidth()
				- Gdx.app.getGraphics().getWidth() / 8, Gdx.app.getGraphics()
				.getHeight() - Gdx.app.getGraphics().getHeight() / 8);

		// /Back Button
		btnBack = new TextButton(language.getString("button_back"), skin);

		// /ChatBox
		chatBox = new Table();
		scroll = new ScrollPane(chatBox);
		scroll.setForceScroll(false, true);
		scroll.setFlickScroll(true);
		scroll.setOverscroll(false, false);

		// /Message
		message = new TextField("", skin);
		btnSend = new TextButton(language.getString("button_send"), skin);

		// /Start Button
		btnStart = new TextButton(language.getString("button_start"), skin);

	}

	@Override
	public void dispose() {
		th1.interrupt();
		stage.dispose();
		skin.dispose();
		game.dispose();
		sound.dispose();
		effect.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.graphics.setDisplayMode(game.getSize().width,
				game.getSize().height, game.isFullscreen());

		// /Settings
		effect.setVolume(0, game.getSoundVolume());
		sound.setVolume(game.getMusicVolume());

		// /Cursor
		Pixmap pm = new Pixmap(Gdx.files.internal("img/cursor.png"));
		Gdx.input.setCursorImage(pm, 0, 0);
		pm.dispose();

		// /Draw Background
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, Gdx.app.getGraphics().getWidth(), Gdx.app
				.getGraphics().getHeight());
		batch.end();

		// /Draw Stage
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height);
		camera.setToOrtho(false, width, height);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		// /Title
		title.setPosition(
				Gdx.app.getGraphics().getWidth() / 2 - title.getWidth() / 2,
				Gdx.app.getGraphics().getHeight() - 30);
		stage.addActor(title);

		// /Widgets Background
		widgetsBackground.setPosition(Gdx.app.getGraphics().getWidth() / 2
				- widgetsBackground.getWidth() / 2, Gdx.app.getGraphics()
				.getHeight() / 2 - widgetsBackground.getHeight() / 2);
		stage.addActor(widgetsBackground);

		// /Back Button
		btnBack.setWidth(100);
		btnBack.setPosition(
				Gdx.app.getGraphics().getWidth() / 2 - btnBack.getWidth() / 2,
				Gdx.app.getGraphics().getHeight()
						- widgetsBackground.getHeight() + 10);

		btnBack.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent e, float x, float y,
					int pointer, int button) {
				effect.play(game.getSoundVolume());
				th1.interrupt();
				game.setScreen(new MainMenu(game, sound, effect));
				return false;
			}
		});
		stage.addActor(btnBack);

		scroll.setSize(widgetsBackground.getWidth()
				- ((1 / 8) * widgetsBackground.getWidth()),
				widgetsBackground.getHeight() - widgetsBackground.getHeight()
						/ 2);
		scroll.setPosition(
				Gdx.app.getGraphics().getWidth() / 2 - scroll.getWidth() / 2,
				Gdx.app.getGraphics().getHeight() / 2 - scroll.getHeight() / 2);
		stage.addActor(scroll);

		btnSend.setWidth(100);
		btnSend.setPosition(btnBack.getX(),
				btnBack.getY() + btnBack.getHeight() + 5);
		btnSend.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent e, float x, float y,
					int pointer, int button) {
				if (!message.getText().equals("")) {
					TextArea t = new TextArea("Server : " + message.getText(),
							skin);
					t.setTouchable(Touchable.disabled);
					chatBox.row();
					chatBox.add(t).height(75).width(500);
					if (output != null) {
						output.println(t.getText());
					}
					message.setText("");

				}

				return false;
			}
		});
		stage.addActor(btnSend);

		message.setPosition(btnSend.getX() - message.getWidth(), btnSend.getY());
		stage.addActor(message);

		btnStart.setWidth(100);
		btnStart.setPosition(btnSend.getX() + btnSend.getWidth(),
				btnSend.getY());
		btnStart.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent e, float x, float y,
					int pointer, int button) {
				effect.play(game.getSoundVolume());
				// / TODO
				return false;
			}
		});
		stage.addActor(btnStart);

		th1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Attente Serveur ");
					connection = serverSocket.accept();
					System.out.println("Connecté fdp " + connection.getPort());
					input = new BufferedReader(new InputStreamReader(connection
							.getInputStream()));
					output = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(
									connection.getOutputStream())), true);
					while (true) {
						String messagePlayer = input.readLine();
						if (messagePlayer != null) {
							TextArea t = new TextArea(messagePlayer, skin);
							t.setTouchable(Touchable.disabled);
							chatBox.row();
							chatBox.add(t).height(75).width(500);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		th1.start();

	}

}
