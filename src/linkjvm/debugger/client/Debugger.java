package linkjvm.debugger.client;

public class Debugger {
	public static void main(String[] args){
		new Debugger(args);
	}
	
	public Debugger(String[] args){
		Server server = new Server();
		if(args.length > 0 && (args[0].equals("--no-gui") || args[0].equals("--cli"))){
			server.setOutputStream(System.out);
		}
		else{
			GUI gui = new GUI();
			server.setOutputStream(gui.getOutputStream());
		}
		server.start();
	}	
}
