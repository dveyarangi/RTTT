package rttt;

import yarangi.graphics.quadraturin.objects.Entity;

public class Board extends Entity
{
	private final Tile root;

	private final RTTT scene;
	
	private final Player [] players;
	
	private int currPlayerIdx = 0;

	
	public Board(int size, RTTT scene, Player ...players)
	{
		root = new Tile(new TileCoord(), -size/2, -size/2, size/2, size/2, null);
		this.scene = scene;
		scene.addEntity( root );
		
		this.players = players;
	}
	
	public Tile getRoot() { return root; }
	
	
	
	private int getNextPlayerIdx() {
		
		int nextIdx = currPlayerIdx + 1;
		if(nextIdx == players.length)
			nextIdx = 0;
		
		return nextIdx;
	}
	
	public boolean makeMove()
	{
		Player currPlayer = players[currPlayerIdx];
		
		TileCoord coord = currPlayer.getController().getMove();
		if(coord == null)
			return false;
		

//		if(player != players[currPlayerIdx])
//			return false; // not your turn
		
		Tile tile = lookupTile(coord);
		
		if(tile.getOwner() != null) // already has piece
			return false;
		
		currPlayer = players[currPlayerIdx];
		
		
		if(tile.getClaimedBy() == null) 
		{
			tile.setClaimedBy( currPlayer.getMark() );
			currPlayer.claim(tile);
		}
		else 
			split( tile,  3 );
		
		Player nextPlayer = players[getNextPlayerIdx()];
		if(nextPlayer.getClaimed() != null && nextPlayer.getClaimed() != tile)
		{
			nextPlayer.getClaimed().setOwned();
			checkVictory( nextPlayer.getClaimed().getParent() );
		}

		
		currPlayerIdx = getNextPlayerIdx();
		
		return true;
	}
	
	/**
	 * Finds a tile by tile coordinate vector
	 * @param coord
	 * @return
	 */
	private Tile lookupTile(TileCoord coord)
	{
		return lookupTile( root, coord, 0 );
	}
	
	private Tile lookupTile(Tile parentTile, TileCoord coord, int depth)
	{
		if(depth == coord.size())
			return parentTile;
		
		int layerIdx = coord.get( depth );
		
		Tile [][] children = parentTile.getSubTiles();
		if(children == null)
			return parentTile;
		
		Tile nextTile = children[layerIdx / parentTile.getDim()][layerIdx % parentTile.getDim()];
		
		return lookupTile( nextTile, coord, depth + 1 );
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
			tile.getSubTiles()[i][j] = new Tile( new TileCoord(tile.getCoord(), dim*i + j),
					minx + subwidth * i, 
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
			if( tile.getSubTiles()[i][j].getSubTiles()!=null )
				merge(tile.getSubTiles()[i][j]);
			
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
			IPlayerMark p = tiles[i][0].getOwner();
			
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
			IPlayerMark p = tiles[0][j].getOwner();
			
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
			IPlayerMark p = tiles[i][j].getOwner();
			
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
			IPlayerMark p = tiles[0][0].getOwner();
			
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
