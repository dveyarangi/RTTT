package rttt;

import java.util.LinkedList;

/**
 * Tile coordinate is represented as list on indexes of following values: 
 * 0 1 2
 * 3 4 5
 * 6 7 8
 * 
 */
public class TileCoord extends LinkedList <Integer>
{

	private static final long serialVersionUID = 2825160790352324379L;

	public TileCoord()
	{
		super();
	}
	
	/**
	 * appending constructor
	 * @param coord
	 * @param next
	 */
	public TileCoord(TileCoord coord, int next)
	{
		super();
		for(int i : coord)
			add(i);
		
		add(next);
	}

}
