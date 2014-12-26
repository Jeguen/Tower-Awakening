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

package awakening.game;
 
import awakening.menu.MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.math.Vector2;

/**
 * TAGame
 * 
 * @author rodolphe-c
 *
 */
public class TAGame extends Game {
 
	private Settings setting;
	
	/**
	 * Constructor
	 */
	public TAGame()
	{
		super();
		setting = new Settings();
	}
	
    @Override
    public void create() 
    {
    	setScreen(new MainMenu(this));
    }

    /**
	 * Get the effect sounds volume
	 * 
	 * @return Volume
	 */
	public float getSoundVolume() 
	{
		return setting.getSoundVolume();
	}

	/**
	 * Set the effect sounds volume
	 * 
	 * @param soundVolume volume
	 */
	public void setSoundVolume(float soundVolume) 
	{
		setting.setSoundVolume(soundVolume);;
	}

	/**
	 * Get the music volume
	 * 
	 * @return Volume
	 */
	public float getMusicVolume() 
	{
		return setting.getMusicVolume();
	}

	/**
	 * Set the music volume
	 * 
	 * @param Volume
	 */
	public void setMusicVolume(float musicVolume) 
	{
		setting.setMusicVolume(musicVolume);
	}

	/**
	 * Test if the app is fullscreen
	 * 
	 * @return True if is fullscreen, false otherwhise
	 */
	public boolean isFullscreen() 
	{
		return setting.isFullscreen();
	}

	/**
	 * Set the app fullscreen or not
	 * 
	 * @param fullscreen
	 */
	public void setFullscreen(boolean fullscreen) 
	{
		setting.setFullscreen(fullscreen);
	}

	/**
	 * Get the display mode
	 * 
	 * @return Display mode
	 */
	public DisplayMode getDisplay() 
	{
		return setting.getDisplay();
	}

	/**
	 * Set the screen's size
	 * 
	 * @param w
	 * @param h
	 */
	public void setSize(int w, int h) 
	{
		setting.setSize(w, h);
	}
	
	/**
	 * Get the screen's size
	 * 
	 * @return Screen size
	 */
	public Vector2 getSize() 
	{
		return setting.getSize();
	}

	/**
	 * Set the display mode
	 * 
	 * @param display
	 */
	public void setDisplay(DisplayMode display) 
	{
		setting.setDisplay(display);
	}
	
}
