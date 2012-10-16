package rttt;


public class Player
{
	private final IPlayerMark mark;
	
	private final IPlayerController controller;
	
	private Tile lastClaimed;
	
	public Player(IPlayerMark mark, IPlayerController controller)  
	{
		this.mark = mark;
		this.controller = controller;
	}
	
	public void claim(Tile tile) { this.lastClaimed = tile; }
	
	public Tile getClaimed() { return lastClaimed; }

	public IPlayerMark getMark()
	{
		return mark;
	}

	public IPlayerController getController()
	{
		return controller;
	}
}
