package rttt;

import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.QVoices;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.Stage;
import yarangi.graphics.quadraturin.config.EkranConfig;
import yarangi.graphics.quadraturin.config.SceneConfig;
import yarangi.graphics.quadraturin.objects.EntityShell;

public class RTTT extends Scene
{

	public static void main(String ... args)
	{
		
		// starts the engine:
		Stage stage = Q.go();
	}



	public RTTT(SceneConfig sceneConfig, EkranConfig ekranConfig, QVoices voices)
	{
		super( sceneConfig, ekranConfig, voices );
		
	}
	
	private static final int BOARD_SIZE = 300;

	
	@Override
	public void init()
	{
		// shared controller for user interactions:
		HumanController controller = new HumanController(this);
		
		// registering user input controller with engine:
		setActionController( new EntityShell( controller, null, null));
		
		// list of participating players:
		Player [] players = new Player [] {
				new Player(IPlayerMark.X, controller),
				new Player(IPlayerMark.O, controller)
		};
		
		// game board:
		Board board = new Board(BOARD_SIZE, this, players);
		
		// constantly samples moves from controllers and tries to advance game:
		board.setBehavior( new BoardBehavior() );
		
		// preparing game board
		board.split( board.getRoot(), 3 );

		addEntity( board ); // registering with engine, so behavior is invoked
	}

	@Override
	public void destroy() { }

}
