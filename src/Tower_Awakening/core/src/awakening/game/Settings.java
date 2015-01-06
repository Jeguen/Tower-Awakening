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

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.math.Vector2;

/**
 * Settings
 * 
 * @author rodolphe
 *
 */
public class Settings 
{
	private float soundVolume;
	private float musicVolume;
	private boolean fullscreen;
	private DisplayMode display;
	private Vector2 size;
	
	/**
	 * Constructor
	 */
	public Settings()
	{
		soundVolume = 1.0f;
		musicVolume = 1.0f;
		fullscreen = false;
		//display = Gdx.graphics.getDisplayModes()[0];
		size = new Vector2(640,480);
	}

	/**
	 * Get the effect sounds volume
	 * 
	 * @return Volume
	 */
	public float getSoundVolume() 
	{
		return soundVolume;
	}

	/**
	 * Set the effect sounds volume
	 * 
	 * @param soundVolume volume
	 */
	public void setSoundVolume(float soundVolume) 
	{
		this.soundVolume = soundVolume;
	}

	/**
	 * Get the music volume
	 * 
	 * @return Volume
	 */
	public float getMusicVolume() 
	{
		return musicVolume;
	}

	/**
	 * Set the music volume
	 * 
	 * @param Volume
	 */
	public void setMusicVolume(float musicVolume) 
	{
		this.musicVolume = musicVolume;
	}

	/**
	 * Test if the app is fullscreen
	 * 
	 * @return True if is fullscreen, false otherwhise
	 */
	public boolean isFullscreen() 
	{
		return fullscreen;
	}

	/**
	 * Set the app fullscreen or not
	 * 
	 * @param fullscreen
	 */
	public void setFullscreen(boolean fullscreen) 
	{
		this.fullscreen = fullscreen;
	}

	/**
	 * Get the display mode
	 * 
	 * @return Display mode
	 */
	public DisplayMode getDisplay() 
	{
		return display;
	}

	/**
	 * Set the screen's size
	 * 
	 * @param w
	 * @param h
	 */
	public void setSize(int w, int h) 
	{
		this.size = new Vector2(w,h);
	}
	
	/**
	 * Get the screen's size
	 * 
	 * @return Screen size
	 */
	public Vector2 getSize() 
	{
		return size;
	}

	/**
	 * Set the display mode
	 * 
	 * @param display
	 */
	public void setDisplay(DisplayMode display) 
	{
		this.display = display;
	}
	
}
