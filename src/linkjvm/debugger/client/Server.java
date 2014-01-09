package linkjvm.debugger.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class Server{
	public static final int PORT = 62512;
	
	private ServerSocket serverSocket;
	private final LinkedList<ClientThread> clients;
	
	private boolean stopAcceptThread = false;
	
	private OutputStream out = null;
	
	public Server(){
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("[DEBUG]: Server started on port " + PORT);
		} catch (IOException e) {
			System.out.println("[ERROR]: Cannot create server on port " + PORT + ". Use another port.");
			e.printStackTrace();
		}
		clients = new LinkedList<ClientThread>();
	}
	
	public void start(){
		new Thread(new AcceptThread()).start();
	}
	
	public void stop(){
		System.out.print("[DEBUG]: Closing down...");
		stopAcceptThread = true;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(ClientThread client : clients){
			client.stop();
		}
		System.out.println("Done.");
	}
	
	private void removeClient(ClientThread client){
		synchronized (clients) {
			clients.remove(client);
		}
	}
	
	public void setOutputStream(OutputStream out){
		this.out = out;
	}
	
	private class AcceptThread implements Runnable {

		@Override
		public void run() {
			while(!stopAcceptThread){
				Socket client = null;
				try {
					client = serverSocket.accept();
					synchronized(clients){
						clients.add(new ClientThread(client));
					}
				} catch (IOException e) {	}
			}
		}
	}
	
	private class ClientThread implements Runnable {
		private Socket socket;
		private BufferedReader inputReader;
		
		public ClientThread(Socket socket){
			this.socket = socket;
			try {
				inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			new Thread(this).start();
		}
		
		@Override
		public void run() {
			int input;
			try {
				while((input = inputReader.read()) != -1){
					synchronized (out) {
						out.write(input);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					removeClient(this);
				}
			}
			
		}
		
		public void stop(){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
}
