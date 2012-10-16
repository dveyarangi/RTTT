package rttt;

import java.util.HashMap;
import java.util.Map;

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
import yarangi.math.Vector2D;
import yarangi.math.IVector2D;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.PickingSensor;


public class HumanController extends ActionController implements IPlayerController
{
	Map <String, IAction> actions = new HashMap <String, IAction> ();
	
	private final ICameraMan cameraMan;
	
	private final boolean isDrawing = false;
	
	private IVector2D prevLoc;
	
	private TileCoord move;

	
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

	
	public HumanController(final Scene scene)
	{
		super(scene);

		cameraMan = new CameraMover( (Camera2D) scene.getCamera() );
		
		DefaultActionFactory.appendNavActions(scene, this);
		Debug.appendDebugActions( this.getActions() );
		
		actions.put("mouse-drag", new IAction()
		{
			@Override
			public void act(UserActionEvent event)
			{
				
				IVector2D p = Vector2D.R( event.getCursor().getCanvasLocation().getX(),
										 -event.getCursor().getCanvasLocation().getY() );
				
				if(prevLoc!=null && p!=null)
				{
					IVector2D q = p.minus(prevLoc);
					cameraMan.moveRelative(q.x(), q.y());
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
				
				move = tile.getCoord();
			}
			
		});

	}
	
	@Override
	public TileCoord getMove() {
		TileCoord tempMove = move;
		move = null;
		return tempMove;
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

	@Override
	public PickingSensor.Mode getPickingMode() { return PickingSensor.Mode.FITTING; }
	
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
