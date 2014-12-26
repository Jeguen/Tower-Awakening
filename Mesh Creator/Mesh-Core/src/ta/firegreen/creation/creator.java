package ta.firegreen.creation;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Collection;
import java.util.Stack;

import javax.swing.JOptionPane;

import ta.shape3D.Animator;
import ta.shape3D.Triangle3D;
import ta.shape3D.mesh.MeshTA;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public final class creator extends ApplicationAdapter {
	SpriteBatch batch;
	final public Dimension desktopDimension = Toolkit.getDefaultToolkit().getScreenSize();
	Texture img;
	ConfigurationFrame configF;
	ShapeRenderer shape;
	ImmediateModeRenderer20 rendu;
	ImmediateModeRenderer20 renduTexture;
	MeshTA trianglesSelected;
	final Stack<ToolsFrame> toolFrameQueue = new Stack<ToolsFrame>();
	File fileTexture = null;
	File fileMTA;
	PerspectiveCamera cam;
	public MeshTA mesh;
	File saveIMGFile;
	boolean afficheTexture = false;
	boolean refresh = true;
	float posX,posY,posZ=10;
	float cibleX,cibleY,cibleZ;
	float rotX,rotY,rotZ;
	MeshTA nouveau;
	Animator animateur = new Animator(30);
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		rendu = new ImmediateModeRenderer20(false, true, 0);
		renduTexture = new ImmediateModeRenderer20(false, true, 1);
		configF = new ConfigurationFrame(this);
        cam = new PerspectiveCamera(95, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); 
		shape = new ShapeRenderer();
		cam.near = 1;
		cam.far = 100;
        cam.direction.set(0,1,0);
        cam.position.set(posX,posY,posZ);
        cam.lookAt(cibleX,cibleY,cibleZ);
        cam.update();
		configF.setVisible(true);
		trianglesSelected = new MeshTA();

	}	
	
	public void setSelectedTriangles(Collection<Triangle3D> triangles)
	{
		unsetSelectedTriangle();
		trianglesSelected.copyFeatures(mesh);
		for(Triangle3D t : triangles)
		{
			Triangle3D newT = trianglesSelected.addTriangle();
			newT.setColor1(new Color(0.5f,0.5f,0.6f,0.4f));
			newT.setColor2(new Color(0.5f,0.5f,0.6f,0.4f));
			newT.setColor3(new Color(0.5f,0.5f,0.6f,0.4f));
			newT.copy(t);
		}
	}
	public void setSelectedTriangles(Triangle3D[] triangles)
	{
		unsetSelectedTriangle();
		trianglesSelected.copyFeatures(mesh);
		for(Triangle3D t : triangles)
		{
			Triangle3D newT = trianglesSelected.addTriangle();
			newT.setColor1(new Color(0.5f,0.5f,0.6f,0.1f));
			newT.setColor2(new Color(0.5f,0.5f,0.6f,0.1f));
			newT.setColor3(new Color(0.5f,0.5f,0.6f,0.1f));
			newT.copy(t);
		}
	}
	public void unsetSelectedTriangle()
	{
		trianglesSelected.removeAll();
	}

	private void bindTexture(MeshTA m, String filename)
	{
		m.setTexture(filename);
	}
	@Override
	public void render () {
		if(fileTexture!=null)
		{
			bindTexture(mesh, fileTexture.getAbsolutePath());
			fileTexture =null;
		}
		if(fileMTA !=null)
		{
			nouveau = MeshTA.load(fileMTA);
			fileMTA=null;
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        cam.position.set(posX,posY,posZ);
        cam.lookAt(cibleX,cibleY,cibleZ);
        cam.update();
		if(afficheTexture) mesh.render(renduTexture, cam.combined);
		else mesh.render(rendu, cam.combined);
		
		if(saveIMGFile!=null)
		{
			
			final Pixmap p = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
			Gdx.gl20.glReadPixels(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), GL20.GL_RGBA,
					GL20.GL_UNSIGNED_BYTE, p.getPixels());
			if(!saveIMGFile.getName().endsWith(".png"))
				if(!saveIMGFile.renameTo(new File(saveIMGFile.getName()+".png")))
				{
					JOptionPane.showMessageDialog(null, "Le fichier n'a pas pu �tre sauvegarder", "Erreur",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			PixmapIO.writePNG(new FileHandle(saveIMGFile), p);
			saveIMGFile=null;
		}
		
		shape.setProjectionMatrix(cam.projection);
		shape.setTransformMatrix(cam.view);
		shape.begin(ShapeType.Line);
		shape.setColor(Color.GREEN);
		shape.line(new Vector3(-10,0,0), new Vector3(10,0,0));
		shape.setColor(Color.RED);
		shape.line(new Vector3(0,-10,0), new Vector3(0,10,0));
		shape.setColor(Color.BLUE);
		shape.line(new Vector3(0,0,-10), new Vector3(0,0,10));
		shape.end();
		cam.translate(0, 0, 0.05f);
		cam.update();
		trianglesSelected.render(rendu, cam.combined);
		cam.translate(0, 0, -0.1f);
		cam.update();
		trianglesSelected.render(rendu, cam.combined);
		try {
			Thread.sleep(35);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}