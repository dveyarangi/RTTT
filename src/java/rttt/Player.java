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
	
	
	public Player(IPlayerMark mark, IPlayerController controller)  
	{
		this.mark = mark;
		this.controller = controller;
	}

	public IPlayerMark getMark()
	{
		return mark;
	}

	public IPlayerController getController()
	{
		return controller;
	}
}
