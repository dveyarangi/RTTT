package rttt;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.math.Angles;
import yarangi.spatial.AABB;

public interface IPlayerMark extends ILook <Tile>
{
	
	public static final IPlayerMark X = new IPlayerMark() {

		@Override
		public void init(GL gl, IRenderingContext context) {}

		@Override
		public void render(GL gl1, Tile tile, IRenderingContext context)
		{
			GL2 gl = gl1.getGL2();
			AABB aabb = (AABB) tile.getArea();
			float minx = (float)aabb.getMinX();
			float maxx = (float)aabb.getMaxX();
			float miny = (float)aabb.getMinY();
			float maxy = (float)aabb.getMaxY();
			gl.glColor4f( 1.0f, 0.5f, 1.0f, tile.getOwner() == null ? 0.2f : 1.0f );
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2f(minx, miny);
			gl.glVertex2f(maxx, maxy);
			gl.glEnd();
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2f(minx, maxy);
			gl.glVertex2f(maxx, miny);
			gl.glEnd();
		}

		@Override
		public void destroy(GL gl, IRenderingContext context) {}

		@Override
		public float getPriority() { return 0; }

		@Override
		public boolean isCastsShadow() { return false; }

		@Override
		public IVeil getVeil() { return null; }

		@Override
		public boolean isOriented() { return false;}
	};
	public static final IPlayerMark O = new IPlayerMark() {
		@Override
		public void init(GL gl, IRenderingContext context) {}

		@Override
		public void render(GL gl1, Tile tile, IRenderingContext context)
		{			GL2 gl = gl1.getGL2();
			AABB aabb = (AABB) tile.getArea();
			float minx = (float)aabb.getMinX();
			float maxx = (float)aabb.getMaxX();
			float miny = (float)aabb.getMinY();
			float maxy = (float)aabb.getMaxY();
			gl.glColor4f( 0.0f, 1.0f, 0.5f, tile.getOwner() == null ? 0.2f : 1.0f );

			gl.glBegin(GL.GL_LINE_STRIP);
			for(double step = 0; step < Angles.TAU; step += Angles.TRIG_STEP * 5)
			gl.glVertex2f((float)( (maxx+minx)/2 + (maxx-minx)/2.1 * Angles.COS( step )), 
						  (float)( (maxy+miny)/2 + (maxy-miny)/2.1 * Angles.SIN( step )));
			gl.glEnd();
		}
	
		@Override
		public void destroy(GL gl, IRenderingContext context) {}
	
		@Override
		public float getPriority() { return 0; }
	
		@Override
		public boolean isCastsShadow() { return false; }
	
		@Override
		public IVeil getVeil() { return null; }
	
		@Override
		public boolean isOriented() { return false;}
	
	};

}
