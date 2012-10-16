package rttt;

/**
 * Incapsulates player aspects 
 */
public class Player
{
	/**
	 * Player visual representation
	 */
	private final IPlayerMark mark;
	
	/**
	 * Player moves controller
	 */
	private final IPlayerController controller;
	
	/**
	 * Last tile claimed by this player
	 */
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
