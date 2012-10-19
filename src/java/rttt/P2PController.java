package rttt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class P2PController extends Thread implements IPlayerController
{
	
	// client/server threads interface:
	private interface Connector extends Runnable { public void stop(); }
	
	// thread for listening:
	private Connector listener;
	// thread for connecting:
	private Connector client;
	
	// communication channel:
	private Socket comm;
	
	// connection selection lock:
	private final Object socklock = new Object();
	
	private boolean isAlive = false;

	
	///////////////////////////////////////////
	private final Board board;
	
	private TileCoord move;
	
	private Queue <TileCoord> localMoves = new LinkedList <TileCoord> ();
	
	
	public P2PController(Board board)
	{
		this.board = board;
	}

	/*********************************************************************************************************************/
	/** Communication thread */
	/*********************************************************************************************************************/
	
	public void init(final Socket comm) {
		this.comm = comm;
		
		if(client != null)
			client.stop();
		
		if(listener != null)
			listener.stop();
		
		isAlive = true;
		
		new Thread(new Runnable() {

			@Override
			public void run()
			{
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(comm.getInputStream()))) {
					while(isAlive) {
						readMove( reader );
						Thread.sleep( 100 );
					}
				}
				catch(IOException e) {e.printStackTrace();} 
				catch ( InterruptedException e ) { e.printStackTrace(); }
			}}).start();
		
		start();
	}
	
	@Override
	public void run() {
		System.out.println("Talking...");
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(comm.getOutputStream())))
		{
		
			while(isAlive)
			{
				Queue <TileCoord> moves;
				synchronized (socklock) {
					moves = localMoves;
					localMoves = new LinkedList <TileCoord> ();
				}
				
				
				while(!moves.isEmpty()) {
					TileCoord localMove = moves.poll();
					
					for(int idx : localMove)
					{
						writer.write( idx + ",");
					}
					writer.write( "\n" );
					writer.flush();
					System.out.println("Sending move: " + localMove);
					
				}
				
				Thread.sleep( 100 );
			

			}
			
		}
		catch ( IOException e ) { e.printStackTrace(); } 
		catch ( InterruptedException e ) { e.printStackTrace(); }
	}
	

	@Override
	public void enemyMoved(Board board, TileCoord coord)
	{
		synchronized(socklock) {
			localMoves.add( coord );
		}
	}
	
	private void readMove(BufferedReader reader) throws NumberFormatException, IOException {
		String message = null;
		if((message = reader.readLine()) != null)
		{
			String [] indexes = message.split( "," );
			TileCoord remoteMove = new TileCoord();
			for(String idxStr : indexes)
				remoteMove.add(Integer.parseInt( idxStr ));
			
			move = remoteMove;
			System.out.println("Remote move arrived: " + move);
		}

	}
	
	

	@Override
	public TileCoord makeMove(Board board)
	{
//		synchronized (socklock) {
		if(move != null)
			System.out.println("Move picked from " + this);
			TileCoord tempMove = move;
			move = null;
			return tempMove;
//		}
	}
	
	
	public void safeStop()
	{
		try
		{
			System.out.println("Closing communication socket...");
			comm.close();
		} catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*********************************************************************************************************************/
	/** Listening thread */
	/*********************************************************************************************************************/
	public void listen(final int localPort) {
		listener = new Connector() {
			
			private ServerSocket server;
			
			@Override
			public void run()
			{
				
				try
				{
					server = new ServerSocket(localPort);
					System.out.println("Listening on port [" + localPort + "]...");
					Socket socket = server.accept();
					synchronized ( socklock )
					{
						System.out.println("Client connected: " + socket.getInetAddress() + ":" + socket.getPort());
						init(socket);
					}
				} 
				catch ( IOException e )
				{
					e.printStackTrace();
					stop();
				}
			}
			
			@Override
			public void stop() {
				System.out.println("Closing server socket.");
				if(server != null)
					try { server.close(); } 
					catch ( IOException e ) { e.printStackTrace(); }
			}
			
		};
		
		new Thread(listener).start();
	}
	
	/*********************************************************************************************************************/
	/** Client connection thread */
	/*********************************************************************************************************************/
	public void connect(final InetAddress remoteAddress, final int remotePort)
	{
		client = new Connector() {

			Socket socket;
			@Override
			public void run()
			{
				try {
					System.out.println("Connecting to server on " + remoteAddress + ":" + remotePort);
					socket = new Socket(remoteAddress, remotePort);
				
					synchronized ( socklock )
					{
						System.out.println("Connected to server.");
						init(socket);
					}
				} 
				catch ( IOException e ) { 
					e.printStackTrace();
					stop();
				}
				
			}
			@Override
			public void stop() {
			}
		
		};
		
		new Thread(client).start();
	}


}
