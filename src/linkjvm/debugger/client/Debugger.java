package linkjvm.debugger.client;

public class Debugger {
	public static void main(String[] args){
		new Debugger(args);
	}
	
	public Debugger(String[] args){
		Server server = new Server();
		GUI gui = new GUI();
		server.setOutputStream((args.length > 0 && (args[0].equals("--no-gui") || args[0].equals("--cli"))) ? System.out : gui.getOutputStream());
		server.start();
	}	
}
