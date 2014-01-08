package linkjvm.debugger.client;

public class Debugger {
	public static void main(String[] args){
		Server server = new Server();
		server.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.stop();
	}
}
