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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MeshTA implements TemporarilyAnimable{
	final private LinkedList<Triangle3D> triangles = new LinkedList<Triangle3D>();
	final private ArrayList<MeshTA> sousMesh = new ArrayList<MeshTA>();
	private float tX,tY,tZ,rX,rY,rZ,scaleX=1,scaleY=1,scaleZ=1;
	private float animeTX, animeTY, animeTZ, animeRX, animeRY, animeRZ;
	Texture image;
	protected String name="Mesh TA" + nbMesh;
	static int nbMesh;
	
	final public float getX() {
		return tX;
	}
	final public float getY() {
		return tY;
	}
	final public float getZ() {
		return tZ;
	}
	final public float getrX() {
		return rX;
	}
	final public float getrY() {
		return rY;
	}
	final public float getrZ() {
		return rZ;
	}
	final public float getScaleX() {
		return scaleX;
	}
	final public float getScaleY() {
		return scaleY;
	}
	final public float getScaleZ() {
		return scaleZ;
	}

	public MeshTA()
	{
		nbMesh++;
	}

	final public void setName(String name)
	{
		this.name = name;
	}
	final public Triangle3D addTriangle()
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
	final public MeshTA addSousMesh(){
		MeshTA m = new MeshTA();
		this.sousMesh.add(m);
		m.copyFeatures(this);
		return m;
	}
	final public void removeSousMesh(MeshTA m)
	{
		sousMesh.remove(m);
	}
	final public MeshTA getSousMesh(int index)
	{
		return sousMesh.get(index);
	}
	
	final public void removeTriangle(Triangle3D t)
	{
		triangles.remove(t);
	}
	final public Triangle3D getTriangle(int index)
	{
		return triangles.get(index);
	}
	final public void render(ImmediateModeRenderer20 rendu, Matrix4 projectionMatrix)
	{
		projectionMatrix.translate(tX, tY, tZ);
		projectionMatrix.rotateRad(Vector3.X, rX);
		projectionMatrix.rotateRad(Vector3.Y, rY);
		projectionMatrix.rotateRad(Vector3.Z, rZ);
		projectionMatrix.scale(scaleX,scaleY,scaleZ);
		if(image!=null){
			Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
			Gdx.gl20.glBindTexture(image.glTarget, image.getTextureObjectHandle());
		}
		rendu.begin(projectionMatrix, GL20.GL_TRIANGLES);
		for(Triangle3D t : triangles)
		{
			t.render(rendu);
		}
		rendu.end();
		Gdx.gl20.glDisable(GL20.GL_TEXTURE_BINDING_2D);
		for(MeshTA m : sousMesh)
		{
			m.render(rendu, projectionMatrix);
		}
		projectionMatrix.scale(1/scaleX, 1/scaleY, 1/scaleZ);
		projectionMatrix.rotateRad(Vector3.X, -rX);
		projectionMatrix.rotateRad(Vector3.Y, -rY);
		projectionMatrix.rotateRad(Vector3.Z, -rZ);
		projectionMatrix.translate(-tX, -tY, -tZ);
	}
	
	final public void save(File f)
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
	final public void save(DataOutputStream bos, String filename) throws IOException
	{
			bos.writeFloat(tX);
			bos.writeFloat(tY);
			bos.writeFloat(tZ);
			bos.writeFloat(rX);
			bos.writeFloat(rY);
			bos.writeFloat(rZ);
			bos.writeFloat(scaleX);
			bos.writeFloat(scaleY);
			bos.writeFloat(scaleZ);
			bos.writeUTF(name);
			bos.writeInt(triangles.size());
			for(Triangle3D t : triangles)
			{
				t.save(bos, filename);
			}
			if(image != null){
				String s = filename.split(".mta")[0] + "Image" + name +".png";
				FileHandle f = new FileHandle(s);
				bos.writeUTF(s);
				TextureData td = image.getTextureData();
				td.prepare();
				PixmapIO.writePNG(f, image.getTextureData().consumePixmap());
			}
			else
				bos.writeUTF(new String());
			bos.writeInt(sousMesh.size());
			for(MeshTA m : sousMesh)
			{
				m.save(bos,filename);
			}
	}
	final public LinkedList<Triangle3D> getTriangles()
	{
		return triangles;
	}
	final public ArrayList<MeshTA> getSousMesh()
	{
		return sousMesh;
	}
	
	final public static MeshTA load(File f)
	{
		if(f.exists() && f.isFile())
		{
			if(f.getName().endsWith(".mta"))
			{
				try {
					DataInputStream dis = new DataInputStream(new FileInputStream(f));
					if(dis.readUTF().equals("MeshTowerAwakening"))
					{
						MeshTA retour = MeshTA.load(f,dis);
						dis.close();
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
	final private static MeshTA load(File f, DataInputStream dis) throws IOException
	{
		MeshTA retour = new MeshTA();
		retour.tX = dis.readFloat();
		retour.tY = dis.readFloat();
		retour.tZ = dis.readFloat();
		retour.rX = dis.readFloat();
		retour.rY = dis.readFloat();
		retour.rZ = dis.readFloat();
		retour.scaleX = dis.readFloat();
		retour.scaleY = dis.readFloat();
		retour.scaleZ = dis.readFloat();
		retour.name = dis.readUTF();
		int nbTriangles = dis.readInt();
		for(int cpt=0;cpt<nbTriangles;cpt++)
		{
			retour.triangles.add(Triangle3D.load(dis));
		}
		String imageFile = dis.readUTF();
		if(!imageFile.equals("")){
			if(f.getParent()!=null)
				retour.setTexture(f.getParent() +"\\"+ imageFile);
			else
				retour.setTexture(imageFile);

		}
		int nbMesh = dis.readInt();
		for(int cpt=0;cpt<nbMesh;cpt++)
		{
			retour.sousMesh.add(MeshTA.load(f,dis));
		}
		return retour;
	}
	
	
	
	public void translate(float x, float y, float z)
	{
		tX+=x;
		tY+=y;
		tZ+=z;
	}
	
	public void rotate(float x, float y, float z)
	{
		rX+=x;
		rY+=y;
		rZ+=z;
	}
	
	public void homethetie(float taux)
	{
		scaleX*=taux;
		scaleY*=taux;
		scaleZ*=taux;
	}
	final public void homethetieX(float taux)
	{
		scaleX*=taux;
	}
	final public void homethetieY(float taux)
	{
		scaleY*=taux;
	}
	final public void homethetieZ(float taux)
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
		image = m.image;
		for(MeshTA sousM : m.sousMesh)
		{
			MeshTA mesh = addSousMesh();
			mesh.copy(sousM);
			mesh.copyFeatures(sousM);
		}
	}
	final public void copyFeatures(MeshTA m)
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
	final public void removeAll()
	{
		triangles.clear();
		sousMesh.clear();
	}
	
	public String toString()
	{
		return name; 
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
	final public void animationTX(float tx)
	{
		this.animeTX=tx;
	}
	final public void animationTY(float ty)
	{
		this.animeTY=ty;
	}
	final public void animationTZ(float tz)
	{
		this.animeTZ=tz;
	}
	final public void animationRX(float rx)
	{
		this.animeRX=rx;
	}
	final public void animationRY(float ry)
	{
		this.animeRY=ry;

	}
	final public void animationRZ(float rz)
	{
		this.animeRZ=rz;
	}
	
	@Override
	public boolean mustBeInterrupted() {
		return false;
	}
	
	final public void changeAllColor(Color c)
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
	
	final public void setTexture(String imagePath)
	{
		image = new Texture(imagePath);
		image.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	}
	final public float getAnimationTX() {
		return animeTX;
	}
	final public float getAnimationTY() {
		return animeTY;
	}
	final public float getAnimationTZ() {
		return animeTZ;
	}
	final public float getAnimationRX() {
		return animeRX;
	}
	final public float getAnimationRY() {
		return animeRY;
	}
	final public float getAnimationRZ() {
		return animeRZ;
	}

	@Override
	public void actionWhenInterruption() {
	}
	
	final public void setAbsoluteFeature(float x, float y, float z, float rx, float rz, float ry,
			float sx, float sy, float sz)
	{
		rX=rx;
		rY=ry;
		rZ=rz;
		scaleX=sx;
		scaleY=sy;
		scaleZ=sz;
		tX=x;
		tY=y;
		tZ=z;
	}
}
