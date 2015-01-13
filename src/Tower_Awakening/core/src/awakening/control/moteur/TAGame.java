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

package awakening.control.moteur;
 
import awakening.view.menu.MainMenu;

import com.badlogic.gdx.Game;


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
	 * Get Down key
	 * 
	 * @return Down key
	 */
	public int getDown() 
	{
		return setting.getDown();
	}

	/**
	 * Get Down key's name
	 * 
	 * @return Down key's name
	 */
	public String getDownKey() 
	{
		return setting.getDownKey();
	}

	/**
	 * Get Language
	 * 
	 * @return Language
	 */
	public String getLanguage() 
	{
		return setting.getLanguage();
	}

	/**
	 * Get Left key
	 * 
	 * @return Left key
	 */
	public int getLeft() 
	{
		return setting.getLeft();
	}

	/**
	 * Get Left key's name
	 * 
	 * @return Left key's name
	 */
	public String getLeftKey() 
	{
		return setting.getLeftKey();
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
	 * Get Right key
	 * 
	 * @return Right key
	 */
	public int getRight() 
	{
		return setting.getRight();
	}
	
	/**
	 * Get Right key's name
	 * 
	 * @return Right key's name
	 */
	public String getRightKey() 
	{
		return setting.getRightKey();
	}

	/**
	 * Get the screen's size
	 * 
	 * @return Screen size
	 */
	public TAScreenSize getSize() 
	{
		return setting.getSize();
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
	 * Get Up key
	 * 
	 * @return Up key
	 */
	public int getUp() 
	{
		return setting.getUp();
	}

	/**
	 * Get Up key's name
	 * 
	 * @return Up key's name
	 */
	public String getUpKey() 
	{
		return setting.getUpKey();
	}

	/**
	 * Get Zoom In key
	 * 
	 * @return Zoom In key
	 */
	public int getZoomIn() 
	{
		return setting.getZoomIn();
	}

	/**
	 * Get Zoom In key's name
	 * 
	 * @return Screen size
	 */
	public String getZoomInKey() 
	{
		return setting.getZoomInKey();
	}

	/**
	 * Get Zoom Out key
	 * 
	 * @return Zoom Out key
	 */
	public int getZoomOut() 
	{
		return setting.getZoomOut();
	}

	/**
	 * Get Zoom Out key's name
	 * 
	 * @return Zoom Out key's name
	 */
	public String getZoomOutKey() 
	{
		return setting.getZoomOutKey();
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
	 * Set Down key
	 * 
	 * @param down
	 */
	public void setDown(int down) 
	{
		setting.setDown(down);
	}
	
	/**
	 * Set Down key's name
	 * 
	 * @param downKey
	 */
	public void setDownKey(String downKey) 
	{
		setting.setDownKey(downKey);
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
	 * Set Language
	 * 
	 * @param language
	 */
	public void setLanguage(String language) 
	{
		setting.setLanguage(language);
	}

	/**
	 * Set Left key
	 * 
	 * @param left
	 */
	public void setLeft(int left) 
	{
		setting.setLeft(left);
	}

	/**
	 * Set Left key's name
	 * 
	 * @param leftKey
	 */
	public void setLeftKey(String leftKey) 
	{
		setting.setLeftKey(leftKey);
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
	 * Set Right key
	 * 
	 * @param right
	 */
	public void setRight(int right)
	{
		setting.setRight(right);
	}

	/**
	 * Set Right key's name
	 * 
	 * @param rightKey
	 */
	public void setRightKey(String rightKey) 
	{
		setting.setRightKey(rightKey);
	}

	/**
	 * Set the screen's size
	 * 
	 * @param w
	 * @param h
	 */
	public void setSize(int w, int h) 
	{
		setting.setSize(new TAScreenSize(w,h));
	}

	/**
	 * Set Screen size
	 * 
	 * @param size
	 */
	public void setSize(TAScreenSize size) 
	{
		setting.setSize(size);
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
	 * Set Up key
	 * 
	 * @param up
	 */
	public void setUp(int up) 
	{
		setting.setUp(up);
	}

	/**
	 * Set Up key's name
	 * 
	 * @param upKey
	 */
	public void setUpKey(String upKey) 
	{
		setting.setUpKey(upKey);
	}

	/**
	 * Set Zoom In key
	 * 
	 * @param zoomIn
	 */
	public void setZoomIn(int zoomIn) 
	{
		setting.setZoomIn(zoomIn);
	}

	/**
	 * Set Zoom In key's name
	 * 
	 * @param zoomInKey
	 */
	public void setZoomInKey(String zoomInKey) 
	{
		setting.setZoomInKey(zoomInKey);
	}

	/**
	 * Set Zoom Out key
	 * 
	 * @param zoomOut
	 */
	public void setZoomOut(int zoomOut) 
	{
		setting.setZoomOut(zoomOut);
	}

	/**
	 * Set Zoom Out key's name
	 * 
	 * @param zoomOutKey
	 */
	public void setZoomOutKey(String zoomOutKey) 
	{
		setting.setZoomOutKey(zoomOutKey);
	}
	
}
