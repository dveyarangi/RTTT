package rttt;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.math.Angles;
import yarangi.spatial.AABB;

public class TileLook implements ILook <Tile>
{

	@Override
	public void init(GL gl, IRenderingContext context)
	{
	}

	@Override
	public void render(GL gl1, Tile tile, IRenderingContext context)
	{
		GL2 gl = gl1.getGL2();
		AABB aabb = (AABB)tile.getArea();
		float minx = (float)aabb.getMinX();
		float maxx = (float)aabb.getMaxX();
		float miny = (float)aabb.getMinY();
		float maxy = (float)aabb.getMaxY();
		
		gl.glColor3f( 0.0f, 0.5f, 1.0f );
		if(tile.isHighlighted())
			gl.glBegin(GL2.GL_POLYGON);
		else
			gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f(minx, miny);
		gl.glVertex2f(minx, maxy);
		gl.glVertex2f(maxx, maxy);
		gl.glVertex2f(maxx, miny);
		gl.glVertex2f(minx, miny);
		gl.glEnd();
		
		if(tile.getClaimedBy() != null) {
			if(tile.getClaimedBy() == PlayerMark.X)
			{
				gl.glColor4f( 1.0f, 0.5f, 1.0f, tile.getOwner() == null ? 0.2f : 1.0f );
				gl.glBegin(GL.GL_LINE_STRIP);
				gl.glVertex2f(minx, miny);
				gl.glVertex2f(maxx, maxy);
				gl.glEnd();
				gl.glBegin(GL.GL_LINE_STRIP);
				gl.glVertex2f(minx, maxy);
				gl.glVertex2f(maxx, miny);
				gl.glEnd();
			} else
			if(tile.getClaimedBy() == PlayerMark.O)
			{
				gl.glColor4f( 0.0f, 1.0f, 0.5f, tile.getOwner() == null ? 0.2f : 1.0f );

				gl.glBegin(GL.GL_LINE_STRIP);
				for(double step = 0; step < Angles.TAU; step += Angles.TRIG_STEP * 5)
				gl.glVertex2f((float)( (maxx+minx)/2 + (maxx-minx)/2.1 * Angles.COS( step )), 
							  (float)( (maxy+miny)/2 + (maxy-miny)/2.1 * Angles.SIN( step )));
				gl.glEnd();
			}		
		}

	}

	@Override
	public void destroy(GL gl, IRenderingContext context)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPriority()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCastsShadow()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IVeil getVeil()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOriented()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
