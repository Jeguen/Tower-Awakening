package awakening.game;

import java.io.File;
import java.util.LinkedList;

import ta.shape3D.Animator;
import ta.shape3D.Point.Point2D;
import ta.shape3D.mesh.MeshTA;
import awakening.toolshop.monster.Monster;
import awakening.toolshop.tower.OffensivTower;
import awakening.toolshop.tower.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class PartieSolo implements Screen{

	

	SpriteBatch batch;
	ShapeRenderer shape;
	boolean keyPressed=false;
	ImmediateModeRenderer20 renderer;
	PerspectiveCamera cam;
    MeshTA terrain;
    byte numTour;
    MeshTA QG;
    boolean addTours;
    final Point2D mousePosition = new Point2D(0, 0);
    Vector3 p = new Vector3();
    boolean inAction = false;
    LinkedList<Tower> toursModeles = new LinkedList<Tower>();
    LinkedList<Tower> tours = new LinkedList<Tower>();
    LinkedList<MeshTA> balles = new LinkedList<MeshTA>();
    int compteur;
    float x=20,y=50, z=20;
    Animator animation = new Animator(30);
    Thread moteur = new Thread(){

		@Override
		public void run() {
			super.run();
			try{
				while(true)
				{
					for(Tower t : tours)
					{
						if(!t.haveATarget())
							if(t.testPortee(30	,30))
							{
								System.out.println("tir!");
								t.targetMonster(new Monster(new Point2D(30,30)) {
									
									@Override
									public void normalMove() {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void crazyMove() {
										// TODO Auto-generated method stub
										
									}
								});
								t.action();
								animation.addTemporarily(t);
							}
					}
					sleep(500);
				}
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	};
	
	@Override
	public void show() {
		Gdx.graphics.setVSync(true);
		Gdx.graphics.setContinuousRendering(false);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
        renderer = new ImmediateModeRenderer20(false, true, 1);
        cam = new PerspectiveCamera(80, Gdx.graphics.getWidth(),(float) Gdx.graphics.getHeight());
		cam.near = 1;
		cam.far = 200;
        terrain = MeshTA.load(new File("terrain 1.mta"));
		terrain.rotate((float)Math.PI/2, 0,0);
		terrain.translate(0, -1, 0);
        QG = MeshTA.load(new File("QG.mta"));
        QG.translate(20, 1, 125);
        QG.rotate(0, -(float) (Math.PI/2), 0);
        QG.homethetieX(5);
        QG.homethetieZ(5);
		cam.up.set(0,1,0);
		cam.position.set(x,y,z);
		cam.lookAt(x, 0, z);
		cam.update();
		toursModeles.add(new OffensivTower("tower2.mta"));
		toursModeles.add(new OffensivTower("tower.mta"));
		Gdx.input.setInputProcessor(new InputProcessor() {
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				if(button == Input.Buttons.LEFT)
				{
					p.x = mousePosition.x;
					p.z = mousePosition.y;
					addTours = true;
					return true;
				}
				return false;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}
			
			@Override
			public boolean scrolled(int amount) {
				y+=amount;
				return true;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				mousePosition.x = screenX; mousePosition.y = screenY;
				Vector3 p = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
				p.x -= x;
				p.x *= y;
				p.x += x;
				p.y = y;
				p.z -= z;
				p.z *= y;
				p.z += z;
				mousePosition.x = p.x; mousePosition.y = p.z;
				return true;
			}
			
			@Override
			public boolean keyUp(int keycode) {
				keyPressed=false;
				return false;
			}
			
			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean keyDown(int keycode) {
				keyPressed=true;
				if(keycode == Input.Keys.LEFT)
				{
					x-=5;
					cam.position.set(x,y,z);
					cam.lookAt(x, 0, z);
					new Thread(){
						public void run()
						{
							while(keyPressed) 
							{
								x-=2.5f;
								cam.position.set(x,y,z);
								cam.lookAt(x, 0, z);
								try {
									sleep(50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}.start();
				}
				else if(keycode ==Input.Keys.RIGHT)
				{
					x+=5;
					cam.position.set(x,y,z);
					cam.lookAt(x, 0, z);
					new Thread(){
						public void run()
						{
							while(keyPressed) 
							{
								x+=2.5f;
								cam.position.set(x,y,z);
								cam.lookAt(x, 0, z);
								try {
									sleep(50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}.start();
				}
				else if(keycode ==Input.Keys.UP)
				{
					z-=5;
					cam.position.set(x,y,z);
					cam.lookAt(x, 0, z);
					new Thread(){
						public void run()
						{
							while(keyPressed) 
							{
								z-=2.5f;
								cam.position.set(x,y,z);
								cam.lookAt(x, 0, z);
								try {
									sleep(50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}.start();
				}
				else if(keycode ==Input.Keys.DOWN)
				{
					z+=5;
					cam.position.set(x,y,z);
					cam.lookAt(x, 0, z);
					new Thread(){
						public void run()
						{
							while(keyPressed) 
							{
								z+=2.5f;
								cam.position.set(x,y,z);
								cam.lookAt(x, 0, z);
								try {
									sleep(50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}.start();
				}
				else if(keycode ==Input.Keys.A)
				{
					numTour ++;
					numTour %=2;
					System.out.println(numTour);
				}
				return false;
			}
		});
		moteur.start();
		animation.start();
	}
	
	public synchronized void ajoutTours()
	{

					Tower t = new OffensivTower(toursModeles.get(numTour));

					addTours=false;
					tours.add(t);
					t.homethetie(2);
					t.translate(p.x,0,p.z);
	}
	public synchronized void ajoutBalles()
	{
					final MeshTA balle = new MeshTA(){

						@Override
						public void actionWhenInterruption() {
							ajoutBalles();
						}

						@Override
						public void anime() {
							this.translate(1f, 0, 0);
						}

						@Override
						public boolean mustBeInterrupted() {
							return this.getX()>(p.x+20);
						}
						
					};
					balles.add(balle);
					balle.copy(toursModeles.get(2));
					animation.addTemporarily(balle);
					balle.translate(p.x,0,p.z);
	}

	@Override
	public void render(float delta) {
		compteur++;
		if(addTours)
		{
			ajoutTours();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);	
		cam.update();
		terrain.render(renderer, cam.combined);
		QG.render(renderer, cam.combined);
		for(MeshTA t : tours) 
		{
			t.render(renderer, cam.combined);
		}
		for(MeshTA b : balles) 
		{
			b.render(renderer, cam.combined);
		}
		shape.setProjectionMatrix(cam.projection);
		shape.setTransformMatrix(cam.view);
		shape.begin(ShapeType.Line);
		shape.setColor(Color.GREEN);
		shape.line(new Vector3(-10,0,0), new Vector3(0,0,0));
		shape.setColor(new Color(0.5f,1,0.5f,1));
		shape.line(new Vector3(10,0,0), new Vector3(0,0,0));
		shape.setColor(Color.RED);
		shape.line(new Vector3(0,-10,0), new Vector3(0,10,0));
		shape.setColor(Color.BLUE);
		shape.line(new Vector3(0,0,-10), new Vector3(0,0,0));
		shape.setColor(new Color(0.5f,0.5f,1f,1));
		shape.line(new Vector3(0,0,10), new Vector3(0,0,0));
		
		for(int cpt = 0; cpt<25;cpt++)
		{
			shape.setColor(Color.PURPLE);
			shape.line(new Vector3(cpt*10,1,250), new Vector3(cpt*10,1,0));
			shape.line(new Vector3(250,1,cpt*10), new Vector3(0,1,cpt*10));
		}
		shape.end();
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gdx.graphics.requestRendering();
	}
	public void resize(int width, int height) {
		Vector3 position = cam.position;
		Vector3 cible = cam.direction;
		Vector3 up = cam.up;
        cam = new PerspectiveCamera(80, Gdx.graphics.getWidth(),(float) Gdx.graphics.getHeight());
        cam.near = 1;
		cam.far = 200;
		cam.position.set(position);
		cam.direction.set(cible);
		cam.up.set(up);
		cam.update();
    }
	public PartieSolo() {
	}
	public void animeTour()
	{
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
