package rttt;

/**
 * Generic interface for player moves controller
 */
public interface IPlayerController
{
	/**
	 * Retrieves player's move.
	 * @return null if move is not ready
	 */
	public TileCoord makeMove(Board board);
	
	public void enemyMoved(Board board, TileCoord coord);
}
