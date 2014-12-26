package ta.shape3D;

import java.util.LinkedList;
import java.util.ListIterator;

public class Animator extends Thread {

	public static interface Animable {
		public void anime();
	}
	public static interface TemporarilyAnimable extends Animable{
		public boolean mustBeInterrupted();
	}
	
	private boolean isRunning=false;
	private int interval;
	private LinkedList<Animable> animeListe;
	private LinkedList<TemporarilyAnimable> tempAnimeListe;
	
	public void arret()
	{
		isRunning=false;
		this.interrupt();
	}

	@Override
	public void run() {
		isRunning=true;
		super.run();
		try {
			System.out.println("start");
			while(isRunning)
			{
				for(Animable a : animeListe)
				{
					a.anime();
				}
				ListIterator<TemporarilyAnimable> iterat = tempAnimeListe.listIterator();
				while(iterat.hasNext())
				{
					TemporarilyAnimable ta = iterat.next();
					if(ta.mustBeInterrupted()) iterat.remove();
					else ta.anime();
				}
	
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			
		}
	}

	public Animator(int interval) {
		this.interval=interval;
		this.animeListe = new LinkedList<Animable>();
		this.tempAnimeListe = new LinkedList<TemporarilyAnimable>();
 	}
	
	public void setInterval(int interval)
	{
		this.interval = interval;
	}
	public int getInterval()
	{
		return interval;
	}
	
	public void addAnimable(Animable a)
	{
		this.animeListe.add(a);
	}
	public void addTemporarily(TemporarilyAnimable a)
	{
		this.tempAnimeListe.add(a);
	}
	public void removeAnimable(Animable a)
	{
		this.removeAnimable(a);
	}
	public void removeTemporarily(TemporarilyAnimable a)
	{
		this.tempAnimeListe.remove(a);
	}

}
