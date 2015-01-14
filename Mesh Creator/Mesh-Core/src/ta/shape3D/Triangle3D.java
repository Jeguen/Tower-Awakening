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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Shape which used the ImmediateModeRenderer20 to draw, in OpenGL environment, a triangle
 * @author S Firegreen
 *
 */
public class Triangle3D {
	private final Vector3[] points = new Vector3[3];
	private final Color[] couleurs = new Color[3];
	private final Vector2[] pointsTexture = new Vector2[3];
	public final int id;

	/**
	 * This constructor initializes each attributes
	 * @param id Id which define the triangle 
	 */
	public Triangle3D(int id) {
		this.points[0] = new Vector3(1,0,0);
		this.points[1] = new Vector3(0,1,0);
		this.points[2] = new Vector3(-1,0,0);
		this.pointsTexture[0] = new Vector2(0,0);
		this.pointsTexture[1] = new Vector2(1,1);
		this.pointsTexture[2] = new Vector2(0,1);
		couleurs[0]=Color.WHITE;
		couleurs[1]=Color.WHITE;
		couleurs[2]=Color.WHITE;
		this.id=id;
	}
	
	/**
	 * This constructor initializes each attributes and give 0 for id
	 */
	public Triangle3D() {
		this(0);
	}
	
	
	public String toString()
	{
		return "Triangle " + id + " P1"+points[0]+ "- P2"+points[1]+ " P3"+points[2];
	}
	
	
	/**
	 * 
	 * @return the first Point of the triangle
	 */
	public Vector3 getPoint1(){
		return this.points[0];
	}
	
	/**
	 * 
	 * @return the second Point of the triangle
	 */
	public Vector3 getPoint2(){
		return this.points[1];
	}
	
	/**
	 * 
	 * @return the third Point of the triangle
	 */
	public Vector3 getPoint3(){
		return this.points[2];
	}
	
	/**
	 * 
	 * @return the first Color of the triangle
	 */
	public Color getColor1(){
		return this.couleurs[0];
	}
	
	/**
	 * 
	 * @return the second Color of the triangle
	 */
	public Color getColor2(){
		return this.couleurs[1];
	}
	
	/**
	 * 
	 * @return the last Color of the triangle
	 */
	public Color getColor3(){
		return this.couleurs[2];
	}
	
	/**
	 * set the Color to the first point 
	 * @param p
	 */
	public void setColor1(Color p){
		this.couleurs[0] = p;
	}
	/**
	 * set the Color to the second point 
	 * @param p
	 */
	public void setColor2(Color p){
		this.couleurs[1] = p;
	}
	/**
	 * set the Color to the last point 
	 * @param p
	 */
	public void setColor3(Color p){
		this.couleurs[2] = p;
	}
	
	/**
	 * This Point Texture define the point of the image which corresponds to the
	 * first point of the triangle. the position (0,0) corresponds to the
	 * bottom left corner of the image.
	 * @return the first Point of the triangle
	 */
	public Vector2 getPointTexture1(){
		return this.pointsTexture[0];
	}
	
	/**
	 * This Point Texture define the point of the image which corresponds to the
	 * second point of the triangle. the position (0,0) corresponds to the
	 * bottom left corner of the image.
	 * @return the second Point of the triangle
	 */
	public Vector2 getPointTexture2(){
		return this.pointsTexture[1];
	}
	
	/**
	 * This Point Texture define the point of the image which corresponds to the
	 * third point of the triangle. the position (0,0) corresponds to the
	 * bottom left corner of the image.
	 * @return the third Point of the triangle
	 */
	public Vector2 getPointTexture3(){
		return this.pointsTexture[2];
	}

	/**
	 * draw the triangle in the OpenGL environment. The Color of triangle depends of the tree colors defined. 
	 * If the colors is different, the color of the triangle will be a blend of these colors. 
	 * If the TEXTURE_2D is enable, the texture which is defined will be displayed according to
	 *  the textures points defined.
	 * @param rendu the renderer which is used for drawing triangle, you need to call the begin() method of this renderer
	 * before using this method.
	 */
	public void render(ImmediateModeRenderer20 rendu){
		//if(image!=null) image.bind(GL20.GL_TEXTURE_2D);
		rendu.texCoord(pointsTexture[0].x, pointsTexture[0].y);
		rendu.color(couleurs[0]);
		rendu.vertex(points[0].x, points[0].y, points[0].z);
		rendu.texCoord(pointsTexture[1].x, pointsTexture[1].y);
		rendu.color(couleurs[1]);
		rendu.vertex(points[1].x, points[1].y, points[1].z);
		rendu.texCoord(pointsTexture[2].x, pointsTexture[2].y);
		rendu.color(couleurs[2]);
		rendu.vertex(points[2].x, points[2].y, points[2].z);
	}
	
	
	/**
	 * Save the object by using the stream defined
	 * @param bos the stream which will be use for saving the information of the triangle to load
	 */
	public void save(DataOutputStream bos)
	{
		try {
			bos.writeInt(id);
			for(Vector3 p : points)
			{
				bos.writeFloat(p.x);
				bos.writeFloat(p.y);
				bos.writeFloat(p.z);
			}
			for(Vector2 p : pointsTexture)
			{
				bos.writeFloat(p.x);
				bos.writeFloat(p.y);
			}
			for(Color c : couleurs)
			{
				bos.writeByte((int)(c.a*255));
				bos.writeByte((int)(c.r*255));
				bos.writeByte((int)(c.g*255));
				bos.writeByte((int)(c.b*255));
			}
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		

	}

	/**
	 * Create and load a triangle by using the stream defined
	 * @param dis the stream which contains the information of the triangle to load
	 * @return the loaded triangle 
	 */
	public static Triangle3D load(DataInputStream dis)
	{
		Triangle3D retour = null;
		try {
			retour = new Triangle3D(dis.readInt());
			for(int cpt=0;cpt<3;cpt++)
			{
				retour.points[cpt] = new Vector3(dis.readFloat(),dis.readFloat(),dis.readFloat());
			}
			for(int cpt=0;cpt<3;cpt++)
			{
				retour.pointsTexture[cpt] = new Vector2(dis.readFloat(),dis.readFloat());
			}
			for(int cpt=0;cpt<3;cpt++)
			{
				int a = dis.readUnsignedByte();
				int r = dis.readUnsignedByte();
				int g = dis.readUnsignedByte();
				int b = dis.readUnsignedByte();
				retour.couleurs[cpt]= new Color(Color.toIntBits(a,b,g,r));
			}
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return retour;
		

	}
	
	/**
	 * Translate the triangle in the X axis
	 * @param x D
	 */
	public void translateX(float x)
	{
		for(Vector3 p : points)
		{
			p.x += x;
		}
	}
	
	/**
	 * Translate the triangle in the Y axis
	 * @param y
	 */
	public void translateY(float y)
	{
		for(Vector3 p : points)
		{
			p.y += y;
		}
	}
	/**
	 * Translate the triangle in the Z axis
	 * @param z
	 */
	public void translateZ(float z)
	{
		for(Vector3 p : points)
		{
			p.z+= z;
		}
	}
	
	/**
	 * Translate the triangle in the each axis
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translate(float x, float y, float z)
	{
		for(Vector3 p : points)
		{
			p.x += x;
			p.y += y;
			p.z += z;
		}
	}
	
	/**
	 * Copy each attributes of the Triangle t
	 * @param t
	 */
	public void copy(Triangle3D t)
	{
		this.getPoint1().x = t.getPoint1().x;
		this.getPoint1().y = t.getPoint1().y;
		this.getPoint1().z = t.getPoint1().z;
		this.getPoint2().x = t.getPoint2().x;
		this.getPoint2().y = t.getPoint2().y;
		this.getPoint2().z = t.getPoint2().z;
		this.getPoint3().x = t.getPoint3().x;
		this.getPoint3().y = t.getPoint3().y;
		this.getPoint3().z = t.getPoint3().z;
		this.getPointTexture1().x = t.getPointTexture1().x;
		this.getPointTexture1().y = t.getPointTexture1().y;
		this.getPointTexture2().x = t.getPointTexture2().x;
		this.getPointTexture2().y = t.getPointTexture2().y;
		this.getPointTexture3().x = t.getPointTexture3().x;
		this.getPointTexture3().y = t.getPointTexture3().y;
		this.couleurs[0] = t.couleurs[0];
		this.couleurs[1] = t.couleurs[1];
		this.couleurs[2] = t.couleurs[2];
	}
	
	/**
	 * Make a unique color for the triangle
	 * @param c the color which will be bind on the triangle
	 */
	public void changeAllColor(Color c)
	{
		this.couleurs[0] = c;
		this.couleurs[1] = c;
		this.couleurs[2] = c;
	}
}
