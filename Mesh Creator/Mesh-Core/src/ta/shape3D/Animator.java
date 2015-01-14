 // Copyright © 2014, 2015 VINCENT Steeve, steeve.vincent@gmail.com
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

package ta.shape3D;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * This class run continually to modify objects which implements the interface Animable regurarly 
 * @author S Firegreen
 *
 */
public class Animator extends Thread {

	/**
	 * Implement this class for animate object by using Animator
	 * @author S Firegreen
	 *
	 */
	public static interface Animable {
		/**
		 * Method which is called by the animator to modify the object
		 */
		public void anime();
	}
	
	/**
	 * Derived from animable, this interface allows moreover object to be stopped by the Animator 
	 * @author S Firegreen
	 *
	 */
	public static interface TemporarilyAnimable extends Animable{
		/**
		 * Method which is called by the animator, before animating object, to know if the object must be stopped
		 * @return true if the object need to be Interrupted
		 */
		public boolean mustBeInterrupted();
		/**
		 * Method which is called by the animator, when the object is interrupt
		 */
		public void actionWhenInterruption();
	}
	
	private boolean isRunning=false;
	private boolean onModification=false;
	
	private int interval;
	
	final private LinkedList<Animable> animeListe;
	final private LinkedList<TemporarilyAnimable> tempAnimeListe;
	
	/**
	 * stop the animation and interrupt the Thread
	 */
	public void arret()
	{
		isRunning=false;
		try {
			sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.interrupt();
	}

	@Override
	public void run() {
		isRunning=true;
		super.run();
		try {
			while(isRunning)
			{
				for(Animable a : animeListe)
				{
					a.anime();
				}
				while(onModification)
					try {
						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				onModification = true;
				ListIterator<TemporarilyAnimable> iterat = tempAnimeListe.listIterator();
				while(iterat.hasNext())
				{
					TemporarilyAnimable ta = iterat.next();
					if(ta.mustBeInterrupted())
					{
						iterat.remove();
						iterat = tempAnimeListe.listIterator(iterat.nextIndex());
						ta.actionWhenInterruption();
					}
					else ta.anime();
					
				}
				onModification = false;
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			
		}
	}

	/**
	 * Initialize the lists and define the interval between each animation
	 * @param interval interval in milliseconds
	 */
	public Animator(int interval) {
		this.interval=interval;
		this.animeListe = new LinkedList<Animable>();
		this.tempAnimeListe = new LinkedList<TemporarilyAnimable>();
 	}
	
	/**
	 * redefine the interval 
	 * @param interval interval in milliseconds
	 */
	public void setInterval(int interval)
	{
		this.interval = interval;
	}
	
	/**
	 * get the Interval
	 * @return interval between each animation in  milliseconds
	 */
	public int getInterval()
	{
		return interval;
	}
	
	/**
	 * Add an object to animate
	 * @param a object which implements Animable
	 */
	public void addAnimable(final Animable a)
	{
		if(!onModification) this.animeListe.addLast(a);
		else new Thread(){
			
			public void run()
			{
				try {
					while(onModification)
						sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				animeListe.addLast(a);
			}
		}.start();	}
	/**
	 * Add an object to animate temporarily 
	 * @param a an object which implements TemporarilyAnimable
	 */
	public void addTemporarily(final TemporarilyAnimable a)
	{
		if(!onModification) this.tempAnimeListe.addLast(a);
		else new Thread(){
			
			public void run()
			{
				try {
					while(onModification)
						sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tempAnimeListe.addLast(a);
			}
		}.start();
	}
	
	/**
	 * remove an object which is being animated
	 * @param a object which implements Animable
	 */
	public void removeAnimable(final Animable a)
	{
		if(!onModification) this.animeListe.remove(a);

		else new Thread(){
			
			public void run()
			{
				while(onModification)
					try {
						while(onModification)
							sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				animeListe.remove(a);
			}
		}.start();
	}
	/**
	 * remove an object which is being animated
	 * @param a object which implements Animable
	 */
	public void removeTemporarily(final TemporarilyAnimable a)
	{
		if(!onModification) this.tempAnimeListe.remove(a);
		else new Thread(){
			
			public void run()
			{
				try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tempAnimeListe.remove(a);
			}
		}.start();
	}

}
