package rttt;


import java.util.HashMap;
import java.util.Map;

import test.Vector2D;

import yarangi.graphics.quadraturin.Camera2D;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.ActionController;
import yarangi.graphics.quadraturin.actions.CameraMover;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.actions.ICameraMan;
import yarangi.graphics.quadraturin.debug.Debug;
import yarangi.graphics.quadraturin.events.UserActionEvent;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ILayerObject;
import yarangi.math.IVector2D;
import yarangi.spatial.ISpatialFilter;


public class Controller extends ActionController
{
	Map <String, IAction> actions = new HashMap <String, IAction> ();
	
	private final ICameraMan cameraMan;
	
	private final boolean isDrawing = false;
	
	private final Board board;
	
	private Player currPlayer = X;
	
	private IVector2D prevLoc;
	
	private static final Player X = new Player(PlayerMark.X);
	private static final Player O = new Player(PlayerMark.O);
	
	public Player getNextPlayer() {
		return currPlayer == X ? O : X;
	}
	
	private final ISpatialFilter <IEntity> filter = new ISpatialFilter <IEntity> ()
	{
		@Override
		public boolean accept(IEntity entity)
		{
			if(entity instanceof Tile)
			{
				return true;
			}
			return false;
		}
	
	};

	
	public Controller(final Scene scene, final Board board)
	{
		super(scene);
		
		this.board = board;
	
		cameraMan = new CameraMover( (Camera2D) scene.getCamera() );
		
		DefaultActionFactory.appendNavActions(scene, this);
		Debug.appendDebugActions( this.getActions() );
		
		actions.put("mouse-drag", new IAction()
		{
			@Override
			public void act(UserActionEvent event)
			{
				IVector2D p = event.getCursor().getWorldLocation();
				
				if(prevLoc!=null)
				{
					IVector2D q = p.minus(prevLoc);
					
					cameraMan.moveRelative(q.x(), q.y());
					System.out.println(q.x()+" "+ q.y());
				}
				
				prevLoc = p;
			}
		});
		
		actions.put("mouse-click", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				
				ILayerObject object = event.getCursor().getEntity();
				if(object == null)
					return;
				
				Tile tile = (Tile) object;
				
				if(tile.getOwner() != null) // already has piece
					return;
				
				currPlayer = getNextPlayer();
				
				if(tile.getClaimedBy() == null) {
					tile.setClaimedBy( currPlayer.getMark() );
					currPlayer.claim(tile);
				}
				else 
					board.split( tile,  3 );
				
				if(getNextPlayer().getClaimed() != null && getNextPlayer().getClaimed() != tile)
				{
					getNextPlayer().getClaimed().setOwned();
				}
				
/*				ICursorEvent cursor = event.getCursor();
				target = cursor.getWorldLocation();
				// TODO: test olnly 
				
//				reinforcementMap.query(new ConsumingSensor(terrain, false,target.x(), target.y(), 10  ), target.x(), target.y(), 10 );
				
				if(dragged != null)
					return;
				if(cursor.getEntity() != null && (cursor.getEntity() instanceof IEntity) )
				{
					dragged = (IEntity)cursor.getEntity();
					target = cursor.getWorldLocation();
				}
				if(dragged == null) {
//					System.out.println(target);
					drawTerrain(terrain, target, false);
				}*/
			}
			
		});

	}
	
	
	@Override
	public Map<String, IAction> getActions()
	{
		return actions;
	}

	@Override
	public ISpatialFilter<IEntity> getPickingFilter() { return filter; }

	@Override
	public ICameraMan getCameraManager()
	{
		return cameraMan;
	}

/*	@Override
	public void display(GL gl, double time, RenderingContext context)
	{
		look.render( gl, time, this, context );
	}*/
	@Override
	public void hover(ILayerObject prevEntity, ILayerObject pickedEntity)
	{
		if(prevEntity != null) {
			Tile tile = (Tile) prevEntity;
			tile.setHighlighted(false);
		}
		if(pickedEntity != null) {
			Tile tile = (Tile) pickedEntity;
			tile.setHighlighted(true);
		}
	}

}
