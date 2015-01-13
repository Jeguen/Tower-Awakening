package awakening.view.partie;

import java.io.File;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;

import ta.shape3D.Animator;
import ta.shape3D.Triangle3D;
import ta.shape3D.mesh.MeshTA;
import awakening.modele.field.Box;
import awakening.modele.field.Field;
import awakening.modele.toolshop.monster.Monster;
import awakening.modele.toolshop.monster.MonsterEarth;
import awakening.modele.toolshop.tower.OffensivTower;
import awakening.modele.toolshop.tower.Tower;
import awakening.view.GameWidget.BoutonShop;
import awakening.view.GameWidget.OnHoverableWidget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class PartieSolo extends Partie implements Screen{

	
	private static float halfRadiusPolygon = 3;
	private static int nbSidePolygon = 6;
	private static int border = 10;
	private static int nbSpawn = 5;
	OnHoverableWidget onHoveredWidget;
	int indexBox;
	SpriteBatch batch;
	boolean afficheGrille=true;
    BitmapFont font;
	ShapeRenderer shape;
	boolean keyPressed=false;
	Matrix4 textTransform = new Matrix4();
	ImmediateModeRenderer20 renderer;
	PerspectiveCamera cam;
    byte numTour;
    MeshTA QG;
    boolean addTours;
    final Vector2 mousePosition = new Vector2(0, 0);
    Vector3 p = new Vector3();
    boolean inAction = false;
    LinkedList<Tower> toursModeles = new LinkedList<Tower>();
    LinkedList<Monster> monstreModeles = new LinkedList<Monster>();
    LinkedList<Tower> tours = new LinkedList<Tower>();
    LinkedList<MeshTA> balles = new LinkedList<MeshTA>();
    int compteur;
    float x=20,y=50, z=20;
    Animator animation = new Animator(35);
    Field terrain;
    private BoutonShop boutonShop;
	private Stage stage;
	ReentrantLock l = new ReentrantLock();

    
    Thread moteur = new Thread(){

		@Override
		public void run() {
			super.run();
			try{
				while(true)
				{
					ListIterator<Monster> iteratM = terrain.getMonsters().listIterator();
					while(iteratM.hasNext())
					{
						Monster m = iteratM.next();
						if(m.getPath().size()>0)
						{
							if(m.isArrived){
								iteratM.remove();
							}
							else
								m.normalMove();
						}
						else
							terrain.findPathMonster();
					}
					for(Tower t : tours)
					{
						if(!t.haveATarget())
						{
							for(Monster monstre : terrain.getMonsters())
								if(t.testPortee(monstre.getX(), monstre.getZ()))
								{
									System.out.println("shoot!");
									t.targetMonster(monstre);
									t.action();
									animation.addTemporarily(t);
								}
						}
						else
						{
							if(!t.getTarget().isArrived)
								if(t.testPortee(t.getTarget().getX(), t.getTarget().getZ()))
								{
									t.action();
									animation.addTemporarily(t);
								}
								else
								{
									System.out.println("lose target!");
									t.loseTarget();
								}
							else
								t.loseTarget();
						}
						compteur++;
					}
					sleep(20);
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
		stage = new Stage();
		batch = new SpriteBatch();
	    font = new BitmapFont();
	    font.setScale(0.4f, 0.2f);
	    font.setColor(Color.CYAN);
		shape = new ShapeRenderer();
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
        renderer = new ImmediateModeRenderer20(false, true, 1);
        cam = new PerspectiveCamera(80, Gdx.graphics.getWidth(),(float) Gdx.graphics.getHeight());
		cam.near = 1;
		cam.far = 200;
		terrain = new Field((int) halfRadiusPolygon, nbSidePolygon, border, nbSpawn, 250, 250, new File("terrain 1.mta"));
		terrain.creerPlateau();
		terrain.getSpawns().add(terrain.getBox().get(99));
		terrain.getBox().get(99).setFieldType(Box.FIELD_SPAWNS);
		terrain.getSpawns().add(terrain.getBox().get(3));
		terrain.getBox().get(99).setFieldType(Box.FIELD_SPAWNS);
		terrain.getSpawns().add(terrain.getBox().get(50));
		terrain.getBox().get(99).setFieldType(Box.FIELD_SPAWNS);
		terrain.getSpawns().add(terrain.getBox().get(120));
		terrain.getBox().get(99).setFieldType(Box.FIELD_SPAWNS);
		/*terrain.getSpawns().add(terrain.getBox().get(51));
		terrain.getBox().get(51).setFieldType(Box.FIELD_SPAWNS);*/

		terrain.setFinishBox(terrain.getBox().get(55));
		terrain.numeroterDistance(terrain.getFinishBox());
        QG = MeshTA.loadMeshTA(new File("QG.mta"));
        QG.translate(20, 1, 125);
        QG.rotate(0, -(float) (Math.PI/2), 0);
        QG.homethetieX(5);
        QG.homethetieZ(5);
		cam.up.set(0,1,0);
		cam.position.set(x,y,z);
		cam.lookAt(x, 0, z);
		cam.update();
		File towerDirectory = new File("Tower");
		for(File ft : towerDirectory.listFiles())
		{
			if(ft.isFile())
				if(ft.getName().endsWith("mta"))
					toursModeles.add(new OffensivTower(ft.getAbsolutePath()));
		}
		File monsterDirectory = new File("Monster");
		for(File ft : monsterDirectory.listFiles())
		{
			if(ft.isFile())
				if(ft.getName().endsWith("mta"))
					monstreModeles.add(new MonsterEarth(ft.getAbsolutePath()));
		}
		final MeshTA mesh = new MeshTA();
		Triangle3D t = mesh.addTriangle();
		t.getPoint1().y = 0;
		t.getPoint1().x = -1;
		t.getPoint1().z = 0;
		t.getPoint2().y = 0;
		t.getPoint2().x = 0;
		t.getPoint2().z = -1;
		t.getPoint3().y = 0;
		t.getPoint3().x = 0;
		t.getPoint3().z = 1;

		textTransform.rotate(Vector3.X,-90);
		textTransform.translate(0, 0, 4);

		

		Gdx.input.setInputProcessor(new InputProcessor() {
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {			
				int y = Math.round((mousePosition.y/(3*halfRadiusPolygon)) - 0.5f);
				int x = Math.round(((mousePosition.x - (y%2)*(2*halfRadiusPolygon))/(4*halfRadiusPolygon) - 0.5f));
				indexBox = x + y * (terrain.getNbBoxWidth()) + y/2;
				if(button == Input.Buttons.LEFT)
				{
					if(indexBox>=0  && indexBox<terrain.getBox().size()){
						if(terrain.getBox().get(indexBox).isFree())
						{
							addTours = true;
						}
					}
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
				y+=amount*10;
				cam.position.set(x, y, z);
				cam.lookAt(x,0,z);
				cam.update();
				return true;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				mousePosition.x = screenX; mousePosition.y = screenY;
				if(onHoveredWidget==null)
				{
					boolean isOverSomething=boutonShop.testMousePosition(mousePosition);					
					if(isOverSomething)
					{
						boutonShop.onHoverAction();
						onHoveredWidget = boutonShop;
					}
					else
					{
						Vector3 p = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
						p.x -= x;
						p.x *= y;
						p.x += x;
						p.y = y;
						p.z -= z;
						p.z *= y;
						p.z += z;
						mousePosition.x = p.x; mousePosition.y = p.z;	
					}
				}
				else{
					if(!onHoveredWidget.testMousePosition(mousePosition))
					{
						onHoveredWidget.onExitAction();
						onHoveredWidget = null;	
					};
				}
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
					x-=2;
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
					x+=2;
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
					z-=2;
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
					z+=2;
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
				}
				else if(keycode ==Input.Keys.Q)
				{
					afficheGrille ^=true;
				}
				else if(keycode ==Input.Keys.M)
				{
					MonsterEarth monstre = new MonsterEarth(monstreModeles.get(0));
					monstre.homethetie(3);					
					terrain.addMonster(monstre);
					x=monstre.getBox().getCoordX();
					z=monstre.getBox().getCoordY();
					cam.position.set(x, y, z);
					cam.lookAt(x, 0, z);
					terrain.findPathMonster();
				}

				return false;
			}
		});
		final Texture texture = new Texture("tower1.png");
		boutonShop = new BoutonShop(texture, stage.getWidth()/5, stage.getHeight()/5);
		stage.addActor(boutonShop);
		boutonShop.setPosition(stage.getWidth()-boutonShop.getWidth(),stage.getHeight()-boutonShop.getHeight());
		Window w = new Window("Radar" , new Skin(Gdx.files.internal("uiskin.json")));
		stage.addActor(w);
		w.setSize(stage.getWidth()/4, stage.getWidth()/4);
		w.setPosition(stage.getWidth() - w.getWidth() - 10,5);
		/*boutonShop.addListener
		(
				new ClickListener() 
				{
					public boolean touchDown(InputEvent e, float x, float y, int pointer, int button)
					{
						effect.play(game.getSoundVolume());
						effect.dispose();
						music.dispose();
						Gdx.app.exit();
						return false;	
					}
				}
				
		);*/
		moteur.start();
		animation.start();
	}
	
	public synchronized void ajoutTours()
	{
					Tower t = new OffensivTower(toursModeles.get(numTour));
					addTours=false;
					tours.add(t);
					terrain.getBox().get(indexBox).setTower(t);
					terrain.getBox().get(indexBox).setFieldType(Box.FIELD_TOWER);
					terrain.setTowerExist(true);
					terrain.numeroterDistance(terrain.getFinishBox());
					t.homethetie(3);
					t.translate(terrain.getBox().get(indexBox).getCoordX(),0,terrain.getBox().get(indexBox).getCoordY());
					terrain.findPathMonster();
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
		for(Monster m : terrain.getMonsters())
		{
			m.render(renderer, cam.combined);
		}
		shape.setProjectionMatrix(cam.projection);
		shape.setTransformMatrix(cam.view);
		shape.begin(ShapeType.Line);
		shape.setColor(Color.WHITE);
		if(afficheGrille){
			for(Box b : terrain.getBox())
			{
				for(int i=0; i<b.getTabCoordX().length-1;i++)
				{
					shape.line(new Vector3(b.getTabCoordX()[i],0.1f,b.getTabCoordY()[i]), new Vector3(b.getTabCoordX()[i+1],0.1f,b.getTabCoordY()[i+1]));
				}
			}

			batch.setProjectionMatrix(cam.combined.mul(textTransform));
			batch.begin();
	        //int compteur = 0;
			for(Box b : terrain.getBox())
			{
				font.setColor(Color.WHITE);
		        font.draw(batch,String.valueOf(b.getRange()),b.getCoordX()-halfRadiusPolygon,- b.getCoordY()+2);
			}
			batch.end();
		}
		
		shape.end();
		
		shape.begin(ShapeType.Filled);
		shape.rotate(1, 0, 0, +90);
		shape.setColor(Color.RED);
		shape.circle(terrain.getFinishBox().getCoordX(), terrain.getFinishBox().getCoordY(), halfRadiusPolygon);
		shape.setColor(Color.ORANGE);
		synchronized (terrain) {
			for(Monster m : terrain.getMonsters())
			{
				cam.update();
				batch.setProjectionMatrix(cam.combined.mul(textTransform));
				batch.begin();
				font.setColor(Color.WHITE);
		        int compteur = 0;
		        synchronized (m) {
					for(Box b : m.getPath())
					{
						shape.circle(b.getCoordX(), b.getCoordY(), halfRadiusPolygon);
				        font.draw(batch,String.valueOf(compteur),b.getCoordX()-halfRadiusPolygon/2,- b.getCoordY());
				        compteur++;
					}	
				}
				batch.end();
			}	
		}
		shape.rotate(1, 0, 0, -90);
		shape.end();
		
        stage.draw(); 
	
		
		try {
			Thread.sleep(35);
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
		
	}

}
