package linkjvm.debugger.client;

public class Debugger {
	public static void main(String[] args){
		new Debugger(args);
	}
	
	public Debugger(String[] args){
		
		Server server = new Server();
		GUI gui = new GUI();
		server.setOutputStream(gui.getOutputStream());
		server.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.stop();
		gui.stop();
	}	
}
