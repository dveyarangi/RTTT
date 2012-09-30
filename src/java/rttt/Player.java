package rttt;


public class Player
{
	private final PlayerMark mark;
	
	private Tile lastClaimed;
	
	public Player(PlayerMark mark)  {
		this.mark = mark;
	}
	
	public void claim(Tile tile) { this.lastClaimed = tile; }
	
	public Tile getClaimed() { return lastClaimed; }

	public PlayerMark getMark()
	{
		return mark;
	}
}
