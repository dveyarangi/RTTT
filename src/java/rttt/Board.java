package rttt;

import yarangi.graphics.quadraturin.objects.Entity;

public class Board extends Entity
{
	private final Tile root;

	private final RTTT scene;
	
	public Board(int size, RTTT scene)
	{
		root = new Tile(-size/2, -size/2, size/2, size/2);
		this.scene = scene;
		scene.addEntity( root );
	}
	
	
	public void split(Tile tile, int dim)
	{
		scene.removeEntity( tile );
		tile.split( dim );
		
		float minx = tile.getMinX();
		float miny = tile.getMinY();
		float maxx = tile.getMaxX();
		float maxy = tile.getMaxY();
		
		float subwidth  = ( maxx-minx ) / dim;
		float subheight = ( maxy-miny ) / dim; 
		
		for(int i = 0; i < dim; i ++) for(int j = 0; j < dim; j ++)
		{
			tile.getSubTiles()[i][j] = new Tile( minx + subwidth * i, miny + subheight * j, minx + subwidth * (i+1), miny + subheight * (j+1) );

			scene.addEntity( tile.getSubTiles()[i][j] );
		}
	}
	
	public Tile getRoot() { return root; }
}
