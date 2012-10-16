package rttt;

import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.AABB;

/**
 * Represents a single game board tile.
 * Tiles are arranged in tree hierarchy
 *
 */
public class Tile extends Entity
{
	/**
	 * tile's parent
	 */
	private final Tile parent;
	
	/**
	 * sub-tiles
	 */
	private Tile [][] subtiles;
	
	/**
	 * tile coords on display
	 */
	private final float minx, miny, maxx, maxy;
	
	/**
	 * depth in tree; TODO: not used
	 */
	private int depth;
	
	/**
	 * tile dimensions; TODO: redundant, should rely on suntiles array instead
	 */
	private int dim=1;
	
	/**
	 * marks either tile is highlighted by mouse hover
	 */
	private boolean isHighlighted = false;
	
	/**
	 * claiming player
	 */
	private IPlayerMark claimedBy;
	
	/**
	 * owning player
	 */
	private IPlayerMark ownedBy;
	
	/**
	 * tile coordinate on game board
	 */
	private final TileCoord coord; 

	public Tile(TileCoord coord, float minx, float miny, float maxx, float maxy, Tile parent)
	{
		this.coord = coord;
		
		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
		this.parent = parent;
		setArea(AABB.createFromEdges( minx, miny, maxx, maxy, 0 ));
		setLook(new TileLook());
		subtiles = null;
		claimedBy =  null;
		ownedBy = null;
	}
	
	
	public int getDim() { return dim; }
	public Tile getParent() { return parent; }
	public Tile [][] getSubTiles() { return subtiles; }
	
	public float getMinX() { return minx; }
	public float getMinY() { return miny; }
	public float getMaxX() { return maxx; }
	public float getMaxY() { return maxy; }
	
	public IPlayerMark getClaimedBy() { return claimedBy; }
	public void setClaimedBy(IPlayerMark mark) { this.claimedBy = mark; }
	
	public IPlayerMark getOwner() { return ownedBy; }
	public void setOwned() { this.ownedBy = claimedBy; }
	public void setOwned(IPlayerMark mark) { this.ownedBy = mark; this.claimedBy=null; }

	public TileCoord getCoord() { return coord; }

	public void setHighlighted(boolean b) { isHighlighted = b; }
	public boolean isHighlighted()  { return isHighlighted; }

	/**
	 * splits tile to specified dimensions
	 * @param dim
	 */
	public void split(int dim)
	{
		if(ownedBy != null)
			throw new IllegalStateException("Cannot split tile: player mark exists.");
		if(subtiles != null)
			throw new IllegalStateException("Cannot split tile: already split.");
		
		claimedBy = null;
		subtiles = new Tile [dim][dim];
		this.dim = dim;
	}
	
	/**
	 * merges sub-tiles
	 */
	public void merge()
	{
		claimedBy = null;
		ownedBy = null;
		subtiles = null;
		dim = 0;
	}

}
