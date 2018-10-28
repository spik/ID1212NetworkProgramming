package server.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;
//import java.util.concurrent.LinkedBlockingQueue;

import server.controller.ServerController;

/**
 * This class is what the client threads will perform when they are started by the server. 
 * This is where the messages are actually sent and received 
 */
public class Client implements Runnable{

	private ServerController controller = new ServerController();
	private String responseFromServer;
	private SocketChannel clientChannel;
	private final ByteBuffer fromClientBuffer = ByteBuffer.allocateDirect(8192);
	private String receivedFromClient;
	private final Queue<ByteBuffer> messageToClient = new ArrayDeque<>();
	private boolean isClientBufferEmpty;
	//private LinkedBlockingQueue<ByteBuffer> blockingQueue = new LinkedBlockingQueue<ByteBuffer>();


	Client(SocketChannel clientChannel){
		this.clientChannel = clientChannel;
	}

	@Override
	public void run() {
		while(true){
			if (isClientBufferEmpty) {
				continue;
			}
			isClientBufferEmpty = true;
			responseFromServer = controller.handleUserInput(receivedFromClient);
			if(responseFromServer.equals("Quit")) finalize();
			messageToClient.add(ByteBuffer.wrap(responseFromServer.getBytes()));
		}
	}

	/**
	 * Closes the clientchannel 
	 * @throws IOException 
	 */
	protected void finalize() {

		try {
			clientChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receiveFromClient() throws IOException{
		isClientBufferEmpty = false;
		fromClientBuffer.clear();
		int numOfReadBytes;
		numOfReadBytes = clientChannel.read(fromClientBuffer);
		if (numOfReadBytes == -1) {
			throw new IOException("Client has closed connection.");
		}
		fromClientBuffer.flip();
		byte[] bytes = new byte[fromClientBuffer.remaining()];
		fromClientBuffer.get(bytes);
		receivedFromClient = new String(bytes);

		//Create another thread to handle what to do with the message, as this can take some time and we don't want the network thread to be blocked
		new Thread(this).start();

	}

	public void sendToClient() throws IOException, InterruptedException {

		ByteBuffer msg;
		
		if(messageToClient.peek() == null) {
			Thread.sleep(500);
		}
		while ((msg = messageToClient.peek()) != null) {

			//Writes the message to the client channel, i.e. sends the message to the client
			clientChannel.write(msg);
			if (msg.hasRemaining()) {
				return;
			}
			messageToClient.remove();
		}
	}
}
