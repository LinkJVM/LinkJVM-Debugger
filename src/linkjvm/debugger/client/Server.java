package linkjvm.debugger.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;


public class Server{
	public static final int PORT = 62512;
	
	private ServerSocket serverSocket;
	private final LinkedList<Socket> clients;
	
	private boolean stopAcceptThread = false;
	private boolean stopListenThread = false;
	
	private OutputStream out = null;
	
	public Server(){
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("[DEBUG]: Server started on port " + PORT);
		} catch (IOException e) {
			System.out.println("[ERROR]: Cannot create server on port " + PORT + ". Use another port.");
			e.printStackTrace();
		}
		clients = new LinkedList<Socket>();
	}
	
	public void start(){
		new Thread(new AcceptThread()).start();
		new Thread(new ListenThread()).start();
	}
	
	public void stop(){
		System.out.print("[DEBUG]: Closing down...");
		stopAcceptThread = true;
		stopListenThread = true;
		try {
			serverSocket.close();
		} catch (IOException e) { }
		if(stopListenThread){
			for(Socket client : clients){
				try {
					client.close();
				} catch (IOException e) {
					System.out.println("[ERROR]: Could not close connect to client " + client + ".");
					e.printStackTrace();
				}
			}
		}
		System.out.println("Done.");
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
					System.out.println("[DEBUG]: Added client.");
					synchronized(clients){
						clients.add(client);
					}
				} catch (IOException e) {	}
			}
		}
	}
	
	
	private class ListenThread implements Runnable {

		@Override
		public void run() {
			while(!stopListenThread){
				synchronized(clients){
					for(Socket client : clients){
						try {
							Scanner input = new Scanner(client.getInputStream());
							if(input.hasNextLine()){
								out.write(input.nextLine().getBytes());
							}
						} catch (IOException e) {	}
					}
				}
			}
		}
	}
}
