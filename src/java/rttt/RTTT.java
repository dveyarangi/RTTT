package rttt;

import java.net.InetAddress;
import java.net.UnknownHostException;

import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.QVoices;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.config.EkranConfig;
import yarangi.graphics.quadraturin.config.SceneConfig;
import yarangi.graphics.quadraturin.objects.EntityShell;

public class RTTT extends Scene
{

	public static void main(String ... args)
	{
		// starting the engine:
		Q.go();
	}



	public RTTT(SceneConfig sceneConfig, EkranConfig ekranConfig, QVoices voices)
	{
		super( sceneConfig, ekranConfig, voices );
		
	}
	
	private static final int BOARD_SIZE = 300;

	
	@Override
	public void init()
	{
		
		// game board:
		Board board = new Board(BOARD_SIZE, this);
		
		// constantly samples moves from controllers and tries to advance game:
		board.setBehavior( new BoardBehavior() );
		
		// preparing game board
		board.split( board.getRoot(), 3 );

		addEntity( board ); // registering with engine, so behavior is invoked
		
		
		
		// shared controller for user interactions:
		HumanController controller = new HumanController(this, board);
		// registering user input controller with engine:
		setActionController( new EntityShell( controller, null, null));
		
		
		
		P2PController netController = new P2PController(board);
	
		
		board.addPlayer( new Player(IPlayerMark.X, controller) );
		board.addPlayer( new Player(IPlayerMark.O, netController));		
		
		try
		{
//			netController.listen( 3333 );
			netController.connect( InetAddress.getByName( "localhost" ), 3333 );
		} 
		catch ( Exception e ) { e.printStackTrace(); }
	}

	@Override
	public void destroy() { }

}
