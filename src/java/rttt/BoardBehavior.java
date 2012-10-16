package rttt;

import yarangi.graphics.quadraturin.objects.IBehavior;

/**
 * Constantly reads move from player controller,
 */
public class BoardBehavior implements IBehavior <Board>
{

	@Override
	public boolean behave(double time, Board board, boolean isVisible)
	{
		board.makeMove( );
		return false;
	}

}
