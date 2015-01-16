package awakening.view.menu;

import ta.shape3D.mesh.MeshTA;
import awakening.control.moteur.PartieInputManagement;
import awakening.control.moteur.TAGame;
import awakening.modele.field.Box;
import awakening.modele.partie.Partie;
import awakening.modele.toolshop.monster.Monster;
import awakening.view.GameWidget.BoutonShop;
import awakening.view.GameWidget.ToolShopWindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PartieView implements Screen{

	
	TAGame game;
	SpriteBatch batch;
	boolean afficheGrille=true;
    BitmapFont font;
	ShapeRenderer shape;
	Matrix4 textTransform = new Matrix4();
	ImmediateModeRenderer20 renderer;
	PerspectiveCamera camera;
	Partie partie;
	PartieInputManagement inputManager;
    BoutonShop boutonShop;
    ToolShopWindow fenetreToolShop;
	Stage stage;
	boolean boxIsClicked = false;
    float x=20,y=50, z=20;
    private MeshTA espace;
    private Action a1;
    private Action a2;
	
	public PartieView(TAGame game, Partie partie) {
		this.game = game;
		this.partie = partie;
		inputManager = new PartieInputManagement(game);
		inputManager.setView(this);
		Gdx.input.setInputProcessor(inputManager);
		partie.setInputManager(inputManager);
		espace = MeshTA.loadMeshTA(Gdx.files.internal("Field/espace.mta").file());
		espace.rotate((float) +Math.PI/2, 0, 0);
		espace.translate(0, -50, 0);
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
        camera = new PerspectiveCamera(80, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.near = 1;
		camera.far = 200;
		camera.up.set(0,1,0);
		updateCamera();
		
		fenetreToolShop = new ToolShopWindow(partie.getToolShop(), partie, Gdx.graphics.getHeight()-100
				, Gdx.graphics.getWidth()-100);
		fenetreToolShop.setPosition(50, 50);
		boutonShop = fenetreToolShop.getTheOpenButton();
		boutonShop.setPosition(0,0);
		a1 = new Action() {
			
			@Override
			public boolean act(float delta) {
				fenetreToolShop.setVisible(false);
				Gdx.input.setInputProcessor(inputManager);
				boutonShop.getActions().clear();
				boutonShop.addAction(a2);
				return false;
			}
		};
		a2 = new Action() {
			
			@Override
			public boolean act(float delta) {
				fenetreToolShop.setVisible(true);
				Gdx.input.setInputProcessor(stage);
				boutonShop.getActions().clear();
				boutonShop.addAction(a1);
				return false;
			}
		};
		boutonShop.addAction(a2);
		inputManager.addonHoverlistener(boutonShop);
		stage.addActor(boutonShop);
		stage.addActor(fenetreToolShop);
		fenetreToolShop.setVisible(false);
		textTransform.rotate(Vector3.X,-90);
		textTransform.translate(0, 0, 4);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public PerspectiveCamera getCam() {
		return camera;
	}
	public TAGame getGame() {
		return game;
	}
	public Partie getPartie() {
		return partie;
	}

	public Vector3 getUnprojectedPoint(int x, int y)
	{
		Vector3 p = camera.unproject(new Vector3(x,y,0));
		p.x -= this.x;
		p.x *= this.y;
		p.x += this.x;
		p.y = this.y;
		p.z -= this.z;
		p.z *= this.y;
		p.z += this.z;
		return p;
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void render(float delta) {
		synchronized (partie) {
			if(boxIsClicked)
			{
				partie.clickBox(inputManager.getMousePosition().x,inputManager.getMousePosition().y);
				boxIsClicked = false;
			}
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);	
		camera.update();
		
		espace.render(renderer, camera.combined);
		
		partie.getTerrain().render(renderer, camera.combined);
		for(MeshTA t : partie.getJoueur().getTowerIterator()) 
		{
			t.render(renderer, camera.combined);
		}
		for(Monster m : partie.getTerrain().getMonsters())
		{
			m.render(renderer, camera.combined);
		}
		shape.setProjectionMatrix(camera.projection);
		shape.setTransformMatrix(camera.view);
		shape.begin(ShapeType.Line);
		shape.setColor(Color.WHITE);
		if(afficheGrille){
			for(Box b : partie.getTerrain().getBox())
			{
				for(int i=0; i<b.getTabCoordX().length-1;i++)
				{
					shape.line(new Vector3(b.getTabCoordX()[i],0f,b.getTabCoordY()[i]), new Vector3(b.getTabCoordX()[i+1],0f,b.getTabCoordY()[i+1]));
				}
			}

			batch.setProjectionMatrix(camera.combined.mul(textTransform));
			batch.begin();
			for(Box b : partie.getTerrain().getBox())
			{
				font.setColor(Color.WHITE);
		        font.draw(batch,String.valueOf(b.getRange()),b.getCoordX()-partie.getTerrain().getHalfRadiusPolygon()
		        		,- b.getCoordY()+2);
			}
			batch.end();
		}
		
		shape.end();
		shape.begin(ShapeType.Filled);
		shape.rotate(1, 0, 0, 90);
		shape.setColor(Color.RED);
		shape.circle(partie.getTerrain().getFinishBox().getCoordX(), partie.getTerrain().getFinishBox().getCoordY()
				, partie.getTerrain().getHalfRadiusPolygon());
		shape.setColor(Color.ORANGE);
		synchronized (partie.getTerrain()) {
			for(Monster m : partie.getTerrain().getMonsters())
			{
				camera.update();
				batch.setProjectionMatrix(camera.combined.mul(textTransform));
				batch.begin();
				font.setColor(Color.WHITE);
		        int compteur = 0;
		        synchronized (m) {
					for(Box b : m.getPath())
					{
						shape.circle(b.getCoordX(), b.getCoordY(), partie.getTerrain().getHalfRadiusPolygon());
				        font.draw(batch,String.valueOf(compteur),b.getCoordX()-partie.getTerrain().getHalfRadiusPolygon()/2,- b.getCoordY());
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


	@Override
	public void resize(int width, int height) {
		Vector3 position = camera.position;
		Vector3 cible = camera.direction;
		Vector3 up = camera.up;
        camera = new PerspectiveCamera(80, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.near = 1;
		camera.far = 200;
		camera.position.set(position);
		camera.direction.set(cible);
		camera.up.set(up);
		camera.update();		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	
	  ////                        ////
	 /////                    ///////
	////////////////////////////////
	/////// GETTER & SETTER ///////
	//////////////////////////////
	//						   //
	
	
	public void switchAffichage()
	{
		afficheGrille ^=true;
	}

	public void translateX(float x)
	{
		this.x+=x;
		updateCamera();
	}

	public void translateY(float y)
	{
		this.y+=y;
		updateCamera();	
	}
	
	public void translateZ(float z)
	{
		this.z+=z;
		updateCamera();
	}
	
	
	public synchronized void updateCamera()
	{
		camera.position.set(x,y,z);
		camera.lookAt(x, 0, z);
	}
	
	public void clickBox()
	{
		boxIsClicked=true;
	}

}
