package rttt;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;

public class BoardLook implements ILook <Board>
{
	
	private final int size;
	
	public BoardLook(int size)
	{
		this.size = size;
	}

	@Override
	public void init(GL gl, IRenderingContext context)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GL gl, Board board, IRenderingContext context)
	{
		renderTile(gl, -size/2, -size/2, size/2, size/2, board.getRoot(), context, 0);
	}

	private void renderTile(GL gl, float minx, float miny, float maxx, float maxy, Tile entile, IRenderingContext context, int depth)
	{
		gl.glColor3f( 0.0f, 0.5f, 1.0f );
		if(entile.getMark() != null || entile.getSubTiles() == null) {
			gl.glBegin(GL.GL_LINE_STRIP);
				gl.glVertex2f(minx, miny);
				gl.glVertex2f(minx, maxy);
				gl.glVertex2f(maxx, maxy);
				gl.glVertex2f(maxx, miny);
				gl.glVertex2f(minx, miny);
			gl.glEnd();
		}
		else if(entile.getSubTiles() != null) 
		{
			
			int dim = entile.getSubTiles().length;
			float subwidth  = ( maxx-minx ) / dim;
			float subheight = ( maxy-miny ) / dim; 
			for(int i = 0; i < dim; i ++) for(int j = 0; j < dim; j ++)
				renderTile(gl, minx + i * subwidth, miny + j * subheight, minx + (i+1) * subwidth, miny + (j+1) * subheight, entile.getSubTiles()[i][j], context, depth+1);
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
