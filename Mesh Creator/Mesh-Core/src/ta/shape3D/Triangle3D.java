package ta.shape3D;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ta.shape3D.Point.Point2D;
import ta.shape3D.Point.Point3D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;

public class Triangle3D {
	private final Point3D[] points = new Point3D[3];
	private final Color[] couleurs = new Color[3];
	private final Point2D[] pointsTexture = new Point2D[3];
	Texture image;
	public final int id;


	public Triangle3D(int id) {
		this.points[0] = new Point3D(1,0,0);
		this.points[1] = new Point3D(0,1,0);
		this.points[2] = new Point3D(-1,0,0);
		this.pointsTexture[0] = new Point2D(1,0);
		this.pointsTexture[1] = new Point2D(1,1);
		this.pointsTexture[2] = new Point2D(0,1);
		couleurs[0]=Color.WHITE;
		couleurs[1]=Color.WHITE;
		couleurs[2]=Color.WHITE;
		this.id=id;
	}
	
	public String toString()
	{
		return "Triangle " + id + " P1"+points[0]+ "- P2"+points[1]+ " P3"+points[2];
	}
	public void setTexture(Texture t)
	{
		image = t;
	}
	
	//getter and setter
	public Point3D getPoint1(){
		return this.points[0];
	}
	public Point3D getPoint2(){
		return this.points[1];
	}
	public Point3D getPoint3(){
		return this.points[2];
	}
	public Color getColor1(){
		return this.couleurs[0];
	}
	public Color getColor2(){
		return this.couleurs[1];
	}
	public Color getColor3(){
		return this.couleurs[2];
	}
	public void setColor1(Color p){
		this.couleurs[0] = p;
	}
	public void setColor2(Color p){
		this.couleurs[1] = p;
	}
	public void setColor3(Color p){
		this.couleurs[2] = p;
	}
	public Point2D getPointTexture1(){
		return this.pointsTexture[0];
	}
	public Point2D getPointTexture2(){
		return this.pointsTexture[1];
	}
	public Point2D getPointTexture3(){
		return this.pointsTexture[2];
	}

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
	
	public void save(DataOutputStream bos, String fileName)
	{
		try {
			bos.writeInt(id);
			for(Point3D p : points)
			{
				bos.writeFloat(p.x);
				bos.writeFloat(p.y);
				bos.writeFloat(p.z);
			}
			for(Point2D p : pointsTexture)
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

	public static Triangle3D load(DataInputStream dis)
	{
		Triangle3D retour = null;
		try {
			retour = new Triangle3D(dis.readInt());
			for(int cpt=0;cpt<3;cpt++)
			{
				retour.points[cpt] = new Point3D(dis.readFloat(),dis.readFloat(),dis.readFloat());
			}
			for(int cpt=0;cpt<3;cpt++)
			{
				retour.pointsTexture[cpt] = new Point2D(dis.readFloat(),dis.readFloat());
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
	
	public void translateX(float x)
	{
		for(Point3D p : points)
		{
			p.x += x;
		}
	}
	public void translateY(float y)
	{
		for(Point3D p : points)
		{
			p.y += y;
		}
	}
	public void translateZ(float z)
	{
		for(Point3D p : points)
		{
			p.z+= z;
		}
	}
	public void translate(float x, float y, float z)
	{
		for(Point3D p : points)
		{
			p.x += x;
			p.y += y;
			p.z += z;
		}
	}
	
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
	
	public void changeAllColor(Color c)
	{
		this.couleurs[0] = c;
		this.couleurs[1] = c;
		this.couleurs[2] = c;
	}
}
