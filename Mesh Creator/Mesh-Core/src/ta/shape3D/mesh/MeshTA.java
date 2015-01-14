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


/**
 * Object which is composed of Triangle3D
 * @author S Firegreen
 *
 */
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

	/**
	 * Set the Mesh's name
	 * @param name
	 */
	final public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Add a new triangle to the list
	 * @return the reference of the triangle which is added
	 */
	final public Triangle3D addTriangle()
	{
		Triangle3D t;
		if(!triangles.isEmpty())
			t =new Triangle3D(triangles.getLast().id+1);
		else
			t =new Triangle3D(0);
		triangles.add(t);
		return t;
	}
	
	/**
	 * Add a sub Mesh
	 * @return the reference of the sub mesh which is added
	 */
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
	
	/**
	 * draw all triangles of the Mesh in the OpenGL environment.
	 * @param rendu the renderer which is used for drawing triangle, you don't need to call the begin() method of this renderer
	 * before using this method.
	 * @param rendu 
	 * @param projectionMatrix The projection matrix in which the mesh will be represented 
	 */
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
	
	/**
	 * Save the mesh and the sub mesh in the File f
	 * @param f 
	 */
	public void save(File f)
	{
		try {
			DataOutputStream bos = new DataOutputStream(new FileOutputStream(f));
			bos.writeUTF("MeshTowerAwakening");
			this.save(bos, f);
			bos.flush();bos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void save(DataOutputStream bos, File f) throws IOException
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
				t.save(bos);
			}
			System.out.println("yolo");
			if(image != null){
				String s;
				if(f.getParentFile()!=null)
					s = f.getParent() +"\\"+ f.getName().split(".mta")[0] + "Image" + name +".png";
				else
					s = f.getName().split(".mta")[0] + "Image" + name +".png";
				System.out.println(s);
				bos.writeUTF(f.getName().split(".mta")[0] + "Image" + name +".png");
				TextureData td = image.getTextureData();
				td.prepare();
				PixmapIO.writePNG(new FileHandle(s), image.getTextureData().consumePixmap());
			}
			else
				bos.writeUTF(new String());
			bos.writeInt(sousMesh.size());
			for(MeshTA m : sousMesh)
			{
				m.save(bos,f);
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
	
	/**
	 * Load the Mesh from the file give in the parameter
	 * @param f
	 * @return the mesh which is imported
	 */
	public MeshTA load(File f)
	{
		if(f.exists() && f.isFile())
		{
			if(f.getName().endsWith(".mta"))
			{
				try {
					DataInputStream dis = new DataInputStream(new FileInputStream(f));
					if(dis.readUTF().equals("MeshTowerAwakening"))
					{
						this.load(f,dis);
						dis.close();
						return this;
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
	
	/**
	 * Load a Mesh from the file give in the parameter
	 * @param f
	 * @return the mesh which is imported
	 */
	public static MeshTA loadMeshTA(File f)
	{
		if(f.exists() && f.isFile())
		{
			if(f.getName().endsWith(".mta"))
			{
				try {
					DataInputStream dis = new DataInputStream(new FileInputStream(f));
					if(dis.readUTF().equals("MeshTowerAwakening"))
					{
						MeshTA retour = new MeshTA();
						retour.load(f,dis);
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
	public void load(File f, DataInputStream dis) throws IOException
	{
		this.tX = dis.readFloat();
		this.tY = dis.readFloat();
		this.tZ = dis.readFloat();
		this.rX = dis.readFloat();
		this.rY = dis.readFloat();
		this.rZ = dis.readFloat();
		this.scaleX = dis.readFloat();
		this.scaleY = dis.readFloat();
		this.scaleZ = dis.readFloat();
		this.name = dis.readUTF();
		int nbTriangles = dis.readInt();
		for(int cpt=0;cpt<nbTriangles;cpt++)
		{
			this.triangles.add(Triangle3D.load(dis));
		}
		String imageFile = dis.readUTF();
		if(!imageFile.equals("")){
			if(f.getParent()!=null)
				this.setTexture(f.getParent() +"\\"+ imageFile);
			else
				this.setTexture(imageFile);

		}
		int nbMesh = dis.readInt();
		for(int cpt=0;cpt<nbMesh;cpt++)
		{
			MeshTA m = new MeshTA();
			m.load(f,dis);
			this.sousMesh.add(m);
		}
	}
	
	
	/**
	 * Translates the mesh in the each axis
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translate(float x, float y, float z)
	{
		tX+=x;
		tY+=y;
		tZ+=z;
	}
	
	
	/**
	 * rotates the mesh in the each axis
	 * @param x the rotation value in radiant around the x Axis
	 * @param y the rotation value in radiant around the x Axis
	 * @param z the rotation value in radiant around the x Axis
	 */
	public void rotate(float x, float y, float z)
	{
		rX+=x;
		rY+=y;
		rZ+=z;
	}
	
	/**
	 * Scales the mesh by the value given
	 * @param taux 
	 */
	public void homethetie(float taux)
	{
		scaleX*=taux;
		scaleY*=taux;
		scaleZ*=taux;
	}
	/**
	 * Scales the x coordinate of the mesh by the value given
	 * @param taux 
	 */
	final public void homethetieX(float taux)
	{
		scaleX*=taux;
	}
	/**
	 * Scales the y coordinate of the mesh by the value given
	 * @param taux 
	 */
	final public void homethetieY(float taux)
	{
		scaleY*=taux;
	}
	/**
	 * Scales the z coordinate of the mesh by the value given
	 * @param taux 
	 */
	final public void homethetieZ(float taux)
	{
		scaleZ*=taux;
	}
	
	/**
	 * Clones each triangles and sub mesh of the mesh given
	 * @param m
	 */
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
	
	/**
	 * Copies the translation, rotation and scale features of the mesh given
	 * @param m
	 */
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
	
	/**
	 * Removes all triangles and subMesh
	 */
	final public void removeAll()
	{
		triangles.clear();
		sousMesh.clear();
	}
	
	public String toString()
	{
		return name; 
	}
	
	/**
	 * The implementation of the Anime interface, animate the mesh with the animation features defined with
	 * animationTX(), animationTY(), animationTZ(), animationRX(), animationRY() and animationRZ()
	 */
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
	
	/**
	 * implementation the TemporarilyAnimable, this function returns always false. Derive the class to override 
	 * this function
	 */
	@Override
	public boolean mustBeInterrupted() {
		return false;
	}
	
	/**
	 * implementation the TemporarilyAnimable, this function does nothing. Derive the class to override 
	 * this function
	 */
	@Override
	public void actionWhenInterruption() {
	}
	
	/**
	 * Change the color of all triangles of the mesh
	 * @param c the color which must be binded
	 */
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
	
	
	/**
	 * Bind a texture to the Mesh. That's involved the binding of each triangle
	 * @param imagePath
	 */
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
	final public void setAbsolutePosition(float x, float y, float z)
	{
		tX=x;
		tY=y;
		tZ=z;
	}
}
