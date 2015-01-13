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

/**
 * TAScreenSize
 * 
 * @author rodolphe-c
 *
 */
public class TAScreenSize implements Comparable<Object>
{
	public int width;
	public int height;
	
	public TAScreenSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public int compareTo(Object o) 
	{
		if(this.width < ((TAScreenSize) o).width)
		{
			return -1;
		}
		else if (this.width > ((TAScreenSize) o).width)
		{
			return 1;
		}
		else if (this.width == ((TAScreenSize) o).width)
		{
			if(this.height < ((TAScreenSize) o).height)
			{
				return -1;
			}
			else if (this.height > ((TAScreenSize) o).height)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}

	@Override
	public String toString() 
	{
		return width + " x " + height;
	}
	
}
