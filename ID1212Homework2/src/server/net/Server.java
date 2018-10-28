package server.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {

	private Selector selector;
	private ServerSocketChannel serverSocketChannel;

	public static void main(String[] args) {
		Server server = new Server();
		server.acceptSockets();
	}

	private void acceptSockets() {
		try {
			//Open selector
			selector = Selector.open();

			//Initialize server socket 
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(8080));
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			//Our selecting thread sits in a loop waiting until one of the channels registered with the selector is in a state that matches the 
			//interest operations we've registered for it.
			while(true){

				//Wait for an event one of the registered channels
				//Blocked waiting for a connection request on the server socket. When there is such a request, the select methid returns. 
				//So for example, if the operation we are waiting for is read, the select() method will block until data becomes available on th esocet channel
				selector.select();

				//Iterate over the set of keys for which events are available
				Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

				while (selectedKeys.hasNext()) {
					SelectionKey key = (SelectionKey) selectedKeys.next();
					
					//Remove the key from the iterator as we should not handle it again
					selectedKeys.remove();

					//The key is valid unless the channel has been closed for some reason
					if (!key.isValid()) {
						continue;
					}
					if (key.isAcceptable()) {
						acceptAndStartClient(key);
					}	
					else if (key.isReadable()) {
						read(key);
					} 
					else if (key.isWritable()) {
						write(key);
					}
				}

			}
		} catch (IOException e) {
			System.out.println("Client Disconnected");
		}

	}
	
	private void acceptAndStartClient(SelectionKey key) throws IOException {
		
		//Retrieve the serverSoceketChannel from the key 
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		
		//Accept to establish the connection
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        
        //The first thing we want to do is read
        clientChannel.register(selector, SelectionKey.OP_READ, new Client(clientChannel));
	}

	private void write(SelectionKey key) {
		
        Client client = (Client) key.attachment();
        try {
			client.sendToClient();
			key.interestOps(SelectionKey.OP_READ);
		} catch (IOException | InterruptedException e) {
			client.finalize();
			key.cancel();
		}

	}

	private void read(SelectionKey key) throws IOException{
		
        Client client = (Client) key.attachment();
        try {
        	 client.receiveFromClient();
        	 key.interestOps(SelectionKey.OP_WRITE);
        } catch (IOException e) {
            client.finalize();
            key.cancel();
        }
	}
}
