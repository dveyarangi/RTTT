package rttt;

import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.AABB;

public class Tile extends Entity
{
	private final Tile parent;
	private Tile [][] subtiles;
	private final float minx, miny, maxx, maxy;
	
	private int depth;
	private int dim=1;
	
	private boolean isHighlighted = false;
	
	private IPlayerMark claimedBy;
	private IPlayerMark ownedBy;
	
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
	
	public boolean isHighlighted()  { return isHighlighted; }
	
	public int getDim() { return dim; }
	public float getMinX() { return minx; }
	public float getMinY() { return miny; }
	public float getMaxX() { return maxx; }
	public float getMaxY() { return maxy; }
	public Tile getParent() { return parent; }
	public IPlayerMark getOwner() { return ownedBy; }
	public Tile [][] getSubTiles() { return subtiles; }
	public IPlayerMark getClaimedBy() { return claimedBy; }
	
	public void setOwned() { this.ownedBy = claimedBy; }
	public void setOwned(IPlayerMark mark) { this.ownedBy = mark; this.claimedBy=null; }
	public void setHighlighted(boolean b) { isHighlighted = b; }
	public void setClaimedBy(IPlayerMark mark) { this.claimedBy = mark; }
	
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
	
	public void merge()
	{
		claimedBy = null;
		ownedBy = null;
		subtiles = null;
		dim = 0;
	}

	public TileCoord getCoord()
	{
		return coord;
	}
}
