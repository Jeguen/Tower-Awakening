package ta.shape3D.mesh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import ta.shape3D.Animator.TemporarilyAnimable;
import ta.shape3D.Triangle3D;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MeshTA implements TemporarilyAnimable{
	final private LinkedList<Triangle3D> triangles = new LinkedList<Triangle3D>();
	final private ArrayList<MeshTA> sousMesh = new ArrayList<MeshTA>();
	private float tX,tY,tZ,rX,rY,rZ,scaleX=1,scaleY=1,scaleZ=1;
	private float animeTX, animeTY, animeTZ, animeRX, animeRY, animeRZ;
	private Vector3 axeRX = Vector3.X, axeRY = Vector3.Y, axeRZ = Vector3.Z;
	Texture image;

	public float getAnimationTX() {
		return animeTX;
	}
	public float getAnimationTY() {
		return animeTY;
	}
	public float getAnimationTZ() {
		return animeTZ;
	}
	public float getAnimationRX() {
		return animeRX;
	}
	public float getAnimationRY() {
		return animeRY;
	}
	public float getAnimationRZ() {
		return animeRZ;
	}
	public Triangle3D addTriangle()
	{
		Triangle3D t;
		if(!triangles.isEmpty())
			t =new Triangle3D(triangles.getLast().id+1);
		else
			t =new Triangle3D(0);
		if(image!=null) t.setTexture(image);
		triangles.add(t);
		return t;
	}
	public MeshTA addSousMesh(){
		MeshTA m = new MeshTA();
		this.sousMesh.add(m);
		return m;
	}
	public void removeSousMesh(MeshTA m)
	{
		sousMesh.remove(m);
	}
	public MeshTA getSousMesh(int index)
	{
		return sousMesh.get(index);
	}
	
	public void removeTriangle(Triangle3D t)
	{
		triangles.remove(t);
	}
	public Triangle3D getTriangle(int index)
	{
		return triangles.get(index);
	}
	public void render(ImmediateModeRenderer20 rendu, Matrix4 projectionMatrix)
	{
		projectionMatrix.translate(tX, tY, tZ);
		projectionMatrix.rotateRad(axeRX, rX);
		projectionMatrix.rotateRad(axeRY, rY);
		projectionMatrix.rotateRad(axeRZ, rZ);
		projectionMatrix.scale(scaleX,scaleY,scaleZ);
		rendu.begin(projectionMatrix, GL20.GL_TRIANGLES);
		for(Triangle3D t : triangles)
		{
			t.render(rendu);
		}
		
		rendu.end();
		for(MeshTA m : sousMesh)
		{
			m.render(rendu, projectionMatrix);
		}
		projectionMatrix.scale(1, 1, 1);
		projectionMatrix.rotateRad(axeRX, -rX);
		projectionMatrix.rotateRad(axeRY, -rY);
		projectionMatrix.rotateRad(axeRZ, -rZ);
		projectionMatrix.translate(-tX, -tY, -tZ);
	}
	
	public void save(File f)
	{
		try {
			DataOutputStream bos = new DataOutputStream(new FileOutputStream(f));
			bos.writeUTF("MeshTowerAwakening");
			this.save(bos, f.getName());
			bos.flush();bos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void save(DataOutputStream bos, String filename) throws IOException
	{
			bos.writeInt(triangles.size());
			for(Triangle3D t : triangles)
			{
				t.save(bos, filename);
			}
			if(image != null){
				String s = filename.split(".mta")[0] + "Image" + hashCode() +".png";
				bos.writeUTF(s);
				TextureData td = image.getTextureData();
				td.prepare();
				PixmapIO.writePNG(new FileHandle(s), image.getTextureData().consumePixmap());
			}
			else
				bos.writeUTF(new String());
			bos.writeInt(sousMesh.size());
			for(MeshTA m : sousMesh)
			{
				m.save(bos,filename);
			}
	}
	public LinkedList<Triangle3D> getTriangles()
	{
		return triangles;
	}
	public ArrayList<MeshTA> getSousMesh()
	{
		return sousMesh;
	}
	
	public static MeshTA load(File f)
	{
		if(f.exists() && f.isFile())
		{
			if(f.getName().endsWith(".mta"))
			{
				try {
					DataInputStream dis = new DataInputStream(new FileInputStream(f));
					if(dis.readUTF().equals("MeshTowerAwakening"))
					{
						MeshTA retour = MeshTA.load(dis);
						return retour;
					}
					dis.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		JOptionPane.showMessageDialog(null, "Le fichier n'est pas valide", "Erreur",
				JOptionPane.ERROR_MESSAGE);
		return null;
	}
	public static MeshTA load(DataInputStream dis) throws IOException
	{
		MeshTA retour = new MeshTA();
		int nbTriangles = dis.readInt();
		System.out.println(nbTriangles);
		for(int cpt=0;cpt<nbTriangles;cpt++)
		{
			System.out.println(cpt);
			retour.triangles.add(Triangle3D.load(dis));
		}
		String imageFile = dis.readUTF();
		if(!imageFile.isEmpty()){
			retour.setTexture(imageFile);
		}
		int nbMesh = dis.readInt();
		for(int cpt=0;cpt<nbMesh;cpt++)
		{
			retour.sousMesh.add(MeshTA.load(dis));
		}
		return retour;
	}
	
	
	
	public void translate(float x, float y, float z)
	{
		tX+=x;
		tY+=y;
		tZ+=z;
		for(MeshTA m : sousMesh)
		{
			m.translate(x, y, z);
		}
	}
	
	public void rotate(float x, float y, float z)
	{
		rX+=x;
		rY+=y;
		rZ+=z;
		for(MeshTA m : sousMesh)
		{
			m.rotate(x, y, z);
		}
	}
	
	public void homethetie(float taux)
	{
		scaleX*=taux;
		scaleY*=taux;
		scaleZ*=taux;
	}
	public void homethetieX(float taux)
	{
		scaleX*=taux;
	}
	public void homethetieY(float taux)
	{
		scaleY*=taux;
	}
	public void homethetieZ(float taux)
	{
		scaleZ*=taux;
	}
	
	public void copy(MeshTA m)
	{
		this.sousMesh.clear(); this.triangles.clear();
		for(Triangle3D t : m.triangles)
		{
			Triangle3D triangle = addTriangle();
			triangle.copy(t);
		}
		for(MeshTA sousM : m.sousMesh)
		{
			MeshTA mesh = addSousMesh();
			mesh.copy(sousM);
		}
	}
	public void copyFeatures(MeshTA m)
	{
		rX=m.rX;
		rY=m.rY;
		rZ=m.rZ;
		scaleX=m.scaleX;
		scaleY=m.scaleY;
		scaleZ=m.scaleZ;
		tX=m.tX;
		tY=m.tY;
		tZ=m.tZ;

	}
	public void removeAll()
	{
		triangles.clear();
		sousMesh.clear();
	}
	
	public String toString()
	{
		return "Mesh TA " + hashCode(); 
	}
	@Override
	public void anime() {
		translate(animeTX, animeTY, animeTZ);
		rotate(animeRX,animeRY,animeRZ);
		for(MeshTA m : sousMesh)
		{
			m.anime();
		}
	}
	public void animationTX(float tx)
	{
		this.animeTX=tx;
	}
	public void animationTY(float ty)
	{
		this.animeTY=ty;
	}
	public void animationTZ(float tz)
	{
		this.animeTZ=tz;
	}
	public void animationRX(float rx)
	{
		this.animeRX=rx;
	}
	public void animationRY(float ry)
	{
		this.animeRY=ry;

	}
	public void animationRZ(float rz)
	{
		this.animeRZ=rz;
	}
	
	public void setAxeRotationX(Vector3 axe)
	{
		this.axeRX=axe;
	}
	public void setAxeRotationY(Vector3 axe)
	{
		this.axeRY=axe;
	}
	public void setAxeRotationZ(Vector3 axe)
	{
		this.axeRZ=axe;
	}
	
	@Override
	public boolean mustBeInterrupted() {
		return false;
	}
	
	public void changeAllColor(Color c)
	{
		for(Triangle3D t : triangles)
		{
			t.changeAllColor(c);
		}
		for(MeshTA m : sousMesh)
		{
			m.changeAllColor(c);
		}
	}
	
	public void setTexture(String imagePath)
	{
		image = new Texture(imagePath);
		image.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		for(Triangle3D t : triangles) t.setTexture(image);
	}

}
