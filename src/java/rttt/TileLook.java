package rttt;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.spatial.AABB;

/**
 * visual aspect of game board tile
 */
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
		// basic tile rectangle:
		gl.glColor3f( 0.0f, 0.5f, 1f );
		gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2f(minx, miny);
			gl.glVertex2f(minx, maxy);
			gl.glVertex2f(maxx, maxy);
			gl.glVertex2f(maxx, miny);
			gl.glVertex2f(minx, miny);
		gl.glEnd();
		
		// hightlighted by mouse cursor
		if(tile.isHighlighted()) {
			gl.glColor3f( 0.0f, 0.5f, 0.3f );
			gl.glBegin(GL2.GL_POLYGON);
				gl.glVertex2f(minx, miny);
				gl.glVertex2f(minx, maxy);
				gl.glVertex2f(maxx, maxy);
				gl.glVertex2f(maxx, miny);
				gl.glVertex2f(minx, miny);
			gl.glEnd();
		}

		// player mark:
		if(tile.getClaimedBy() != null) {
			tile.getClaimedBy().render( gl1, tile, context );
		}

	}

	@Override
	public void destroy(GL gl, IRenderingContext context) { }

	@Override
	public float getPriority() { return 0; }

	@Override
	public boolean isCastsShadow() { return false; }

	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return false; }
	
}
