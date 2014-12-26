package ta.shape3D;

import com.badlogic.gdx.math.Vector3;

public abstract class Point {
		

	public static class Point3D{
		public float x;
		public float y;
		public float z;
		public Point3D(float x, float y)
		{
			this(x,y,0);
		}
		public Point3D(float x, float y, float z)
		{
			this.x=x;this.y=y;this.z=z;
		}
		public String toString()
		{
			return "("+x+","+y+","+z+")";
		}
		public Vector3 getVector3()
		{
			return new Vector3(x,y,z);
		}
	}
	public static class Point2D{
		public float x;
		public float y;
		public Point2D(float x, float y)
		{
			this.x=x; this.y=y;
		}
	}
}
