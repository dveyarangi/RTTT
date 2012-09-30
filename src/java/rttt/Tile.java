package rttt;

import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.AABB;

public class Tile extends Entity
{
	private Tile [][] subtiles;
	private final float minx, miny, maxx, maxy;
	
	private int depth;
	
	private boolean isHighlighted = false;
	
	private PlayerMark claimedBy;
	private PlayerMark ownedBy;
	
	public Tile(float minx, float miny, float maxx, float maxy)
	{
		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
		setArea(AABB.createFromEdges( minx, miny, maxx, maxy, 0 ));
		setLook(new TileLook());
		subtiles = null;
		claimedBy =  null;
		ownedBy = null;
	}
	
	public float getMinX() { return minx; }
	public float getMinY() { return miny; }
	public float getMaxX() { return maxx; }
	public float getMaxY() { return maxy; }
	
	public void split(int dim)
	{
		if(ownedBy != null)
			throw new IllegalStateException("Cannot split tile: player mark exists.");
		if(subtiles != null)
			throw new IllegalStateException("Cannot split tile: already split.");
		
		subtiles = new Tile [dim][dim];
	}

	
	public Tile [][] getSubTiles() { return subtiles; }

	public void setHighlighted(boolean b)
	{
		isHighlighted = b;
	}

	public boolean isHighlighted()  { return isHighlighted; }
	
	public void setClaimedBy(PlayerMark mark) { this.claimedBy = mark; }
	public PlayerMark getClaimedBy() { return claimedBy; }

	public void setOwned()
	{
		this.ownedBy = claimedBy;
	}

	public PlayerMark getOwner()
	{
		return ownedBy;
	}
}
