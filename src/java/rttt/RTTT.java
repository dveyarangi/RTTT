package rttt;

import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.QVoices;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.Stage;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;
import yarangi.graphics.quadraturin.config.EkranConfig;
import yarangi.graphics.quadraturin.config.SceneConfig;
import yarangi.graphics.quadraturin.debug.Debug;
import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.spatial.AABB;

public class RTTT extends Scene
{

	public static void main(String ... args)
	{
		Stage stage = Q.go();
	}

	public RTTT(SceneConfig sceneConfig, EkranConfig ekranConfig, QVoices voices)
	{
		super( sceneConfig, ekranConfig, voices );
		
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	private static final int BOARD_SIZE = 300;

	
	@Override
	public void init()
	{
		
		Board board = new Board(BOARD_SIZE, this);
		Controller controller = new Controller(this, board);
		
		setActionController( new EntityShell( controller, null, null));
//		ILook <Board> look = new BoardLook();
//		board.setLook( look );
//		board.setArea( AABB.createSquare( 0, 0, BOARD_SIZE, 0 ) );
		
		board.split( board.getRoot(), 3 );
//		board.split(board.getRoot().getSubTiles()[1][1],  3 );
//		board.split(board.getRoot().getSubTiles()[0][1],  4 );
//		board.split(board.getRoot().getSubTiles()[0][1].getSubTiles()[1][1],  3 );

		
//		addEntity( board );
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}
	
	
	

}
