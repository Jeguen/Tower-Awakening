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

package awakening.game;

import com.badlogic.gdx.Input;

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
	private TAScreenSize size;
	
	private String language;
	
	private int zoomIn;
	private int zoomOut;
	private int up;
	private int down;
	private int left;
	private int right;
	
	private String zoomInKey;
	private String zoomOutKey;
	private String upKey;
	private String downKey;
	private String leftKey;
	private String rightKey;
	
	/**
	 * Constructor
	 */
	public Settings()
	{
		soundVolume = 1.0f;
		musicVolume = 0.0f;
		fullscreen = false;
		size = new TAScreenSize(640,480);
		
		language = "FRENCH";
		
		zoomIn = Input.Keys.P;
		zoomOut = Input.Keys.M;
		up = Input.Keys.UP;
		down = Input.Keys.DOWN;
		left = Input.Keys.LEFT;
		right = Input.Keys.RIGHT;
		
		upKey = "UP";
		downKey = "DOWN";
		leftKey = "LEFT";
		rightKey = "RIGHT";
		zoomInKey = "P";
		zoomOutKey = "M";
		
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
	 * Set the screen's size
	 * 
	 * @param w
	 * @param h
	 */
	public void setSize(int w, int h) 
	{
		this.size = new TAScreenSize(w,h);
	}
	
	/**
	 * Get the screen's size
	 * 
	 * @return Screen size
	 */
	public TAScreenSize getSize() 
	{
		return size;
	}

	/**
	 * Get Zoom In key
	 * 
	 * @return Zoom In key
	 */
	public int getZoomIn() 
	{
		return zoomIn;
	}

	/**
	 * Set Zoom In key
	 * 
	 * @param zoomIn
	 */
	public void setZoomIn(int zoomIn) 
	{
		this.zoomIn = zoomIn;
	}

	/**
	 * Get Zoom Out key
	 * 
	 * @return Zoom Out key
	 */
	public int getZoomOut() 
	{
		return zoomOut;
	}

	/**
	 * Set Zoom Out key
	 * 
	 * @param zoomOut
	 */
	public void setZoomOut(int zoomOut) 
	{
		this.zoomOut = zoomOut;
	}

	/**
	 * Get Up key
	 * 
	 * @return Up key
	 */
	public int getUp() 
	{
		return up;
	}

	/**
	 * Set Up key
	 * 
	 * @param up
	 */
	public void setUp(int up) 
	{
		this.up = up;
	}

	/**
	 * Get Down key
	 * 
	 * @return Down key
	 */
	public int getDown() 
	{
		return down;
	}

	/**
	 * Set Down key
	 * 
	 * @param down
	 */
	public void setDown(int down) 
	{
		this.down = down;
	}

	/**
	 * Get Left key
	 * 
	 * @return Left key
	 */
	public int getLeft() 
	{
		return left;
	}

	/**
	 * Set Left key
	 * 
	 * @param left
	 */
	public void setLeft(int left) 
	{
		this.left = left;
	}
	
	/**
	 * Get Right key
	 * 
	 * @return Right key
	 */
	public int getRight() 
	{
		return right;
	}

	/**
	 * Set Right key
	 * 
	 * @param right
	 */
	public void setRight(int right)
	{
		this.right = right;
	}

	/**
	 * Set Screen size
	 * 
	 * @param size
	 */
	public void setSize(TAScreenSize size) 
	{
		this.size = size;
	}

	/**
	 * Get Zoom In key's name
	 * 
	 * @return Screen size
	 */
	public String getZoomInKey() 
	{
		return zoomInKey;
	}

	/**
	 * Set Zoom In key's name
	 * 
	 * @param zoomInKey
	 */
	public void setZoomInKey(String zoomInKey) 
	{
		this.zoomInKey = zoomInKey;
	}

	/**
	 * Get Zoom Out key's name
	 * 
	 * @return Zoom Out key's name
	 */
	public String getZoomOutKey() 
	{
		return zoomOutKey;
	}

	/**
	 * Set Zoom Out key's name
	 * 
	 * @param zoomOutKey
	 */
	public void setZoomOutKey(String zoomOutKey) 
	{
		this.zoomOutKey = zoomOutKey;
	}

	/**
	 * Get Up key's name
	 * 
	 * @return Up key's name
	 */
	public String getUpKey() 
	{
		return upKey;
	}

	/**
	 * Set Up key's name
	 * 
	 * @param upKey
	 */
	public void setUpKey(String upKey) 
	{
		this.upKey = upKey;
	}

	/**
	 * Get Down key's name
	 * 
	 * @return Down key's name
	 */
	public String getDownKey() 
	{
		return downKey;
	}

	/**
	 * Set Down key's name
	 * 
	 * @param downKey
	 */
	public void setDownKey(String downKey) 
	{
		this.downKey = downKey;
	}

	/**
	 * Get Left key's name
	 * 
	 * @return Left key's name
	 */
	public String getLeftKey() 
	{
		return leftKey;
	}

	/**
	 * Set Left key's name
	 * 
	 * @param leftKey
	 */
	public void setLeftKey(String leftKey) 
	{
		this.leftKey = leftKey;
	}

	/**
	 * Get Right key's name
	 * 
	 * @return Right key's name
	 */
	public String getRightKey() 
	{
		return rightKey;
	}

	/**
	 * Set Right key's name
	 * 
	 * @param rightKey
	 */
	public void setRightKey(String rightKey) 
	{
		this.rightKey = rightKey;
	}

	/**
	 * Get Language
	 * 
	 * @return Language
	 */
	public String getLanguage() 
	{
		return language;
	}

	/**
	 * Set Language
	 * 
	 * @param language
	 */
	public void setLanguage(String language) 
	{
		this.language = language;
	}
	
}
