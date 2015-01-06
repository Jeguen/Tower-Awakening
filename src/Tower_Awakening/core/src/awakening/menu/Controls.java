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

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Controls 
 * 
 * @author rodolphe-c
 *
 */
public class Controls implements Screen
{
	private Stage stage;
	private Texture backgroundTexture;
	private Image background;

	public Controls(Game game)
	{
		stage = new Stage();
		backgroundTexture = new Texture(Gdx.files.internal("Background-1.png"));
		background = new Image(backgroundTexture);
	}
	
	@Override
	public void show() 
	{
		stage.addActor(background);
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.app.log("Menu", "Controls");
		
		// TODO
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) 
	{
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
		// TODO Auto-generated method stub
		
	}
}
