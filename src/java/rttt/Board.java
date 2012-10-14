package rttt;

import yarangi.graphics.quadraturin.objects.Entity;

public class Board extends Entity
{
	private final Tile root;

	private final RTTT scene;
	
	public Board(int size, RTTT scene)
	{
		root = new Tile(-size/2, -size/2, size/2, size/2, null);
		this.scene = scene;
		scene.addEntity( root );
	}
	
	public Tile getRoot() { return root; }
	
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
			tile.getSubTiles()[i][j] = new Tile( minx + subwidth * i, 
												 miny + subheight * j, 
												 minx + subwidth * (i+1), 
												 miny + subheight * (j+1),
												 tile );
			scene.addEntity( tile.getSubTiles()[i][j] );
		}
	}
	
	public void merge(Tile tile)
	{
		for(int i=0; i<tile.getDim() ; ++i ) for(int j=0; j<tile.getDim() ; ++j )
		{
			scene.removeEntity( tile.getSubTiles()[i][j] );
		}
		tile.merge();
		scene.addEntity(tile);
	}
	
	public void checkVictory( Tile tile )
	{
		if( tile==null )
			return;
		
		Tile[][] tiles = tile.getSubTiles();
		
		if(tiles==null)
			return;
		
		//cols
		for( int i=0 ; i<tile.getDim() ; ++i )
		{
			int j=0;
			PlayerMark p = tiles[i][0].getOwner();
			
			while( p!=null && j<tile.getDim() )
			{
				if( p != tiles[i][j].getOwner() )
					break;
				
				++j;
			}
			
			if( j==tile.getDim() )
			{
				merge( tile );
				tile.setClaimedBy(p);
				tile.setOwned();
				checkVictory(tile.getParent());
				return;
			}
		}

		//rows
		for( int j=0 ; j<tile.getDim() ; ++j )
		{
			int i=0;
			PlayerMark p = tiles[0][j].getOwner();
			
			while( p!=null && i<tile.getDim() )
			{
				if( p != tiles[i][j].getOwner() )
					break;
				
				++i;
			}
			
			if( i==tile.getDim() )
			{
				merge( tile );
				tile.setClaimedBy(p);
				tile.setOwned();
				checkVictory(tile.getParent());
				return;
			}
		}

		//primary diagonal
		{
			int i=0, j=tile.getDim()-1;
			PlayerMark p = tiles[i][j].getOwner();
			
			while( p!=null && i<tile.getDim() )
			{
				if( p != tiles[i][j].getOwner() )
					break;
				
				++i;
				--j;
			}
			
			if( i==tile.getDim() )
			{
				merge( tile );
				tile.setClaimedBy(p);
				tile.setOwned();
				checkVictory(tile.getParent());
				return;
			}
		}
		
		//secondary diagonal
		{
			int i=0;
			PlayerMark p = tiles[0][0].getOwner();
			
			while( p!=null && i<tile.getDim() )
			{
				if( p != tiles[i][i].getOwner() )
					break;
				
				++i;
			}
			
			if( i==tile.getDim() )
			{
				merge( tile );
				tile.setClaimedBy(p);
				tile.setOwned();
				checkVictory(tile.getParent());
				return;
			}
		}
	}
}
