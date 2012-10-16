package rttt;

import java.util.LinkedList;

public class TileCoord extends LinkedList <Integer>
{

	private static final long serialVersionUID = 2825160790352324379L;

	public TileCoord(int ... index)
	{
		super();
		for(int i : index)
			add(i);
	}
	public TileCoord(TileCoord coord, int next)
	{
		super();
		for(int i : coord)
			add(i);
		
		add(next);
	}

}
