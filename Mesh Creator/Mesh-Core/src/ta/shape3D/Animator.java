package ta.shape3D;

import java.util.LinkedList;
import java.util.ListIterator;

public class Animator extends Thread {

	public static interface Animable {
		public void anime();
	}
	public static interface TemporarilyAnimable extends Animable{
		public boolean mustBeInterrupted();
		public void actionWhenInterruption();
	}
	
	private boolean isRunning=false;
	private int interval;
	private LinkedList<Animable> animeListe;
	private LinkedList<TemporarilyAnimable> tempAnimeListe;
	public boolean onModification=false;
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
			while(isRunning)
			{
				for(Animable a : animeListe)
				{
					a.anime();
				}
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
		this.animeListe.addLast(a);
	}
	public void addTemporarily(final TemporarilyAnimable a)
	{
		if(!onModification) this.tempAnimeListe.addLast(a);
		else new Thread(){
			
			public void run()
			{
				while(onModification)
					try {
						sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				tempAnimeListe.addLast(a);
			}
			
		}.start();
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
