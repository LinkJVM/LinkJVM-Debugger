package linkjvm.debugger.client;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GUI {
	private JFrame frame;
	private Container contentPane;
	private TextAreaOutputStream out;
	
	private JTextArea outputArea;
	
	public GUI(){
		frame = new JFrame();
		frame.setTitle("LinkJVM Debugger");
		contentPane = frame.getContentPane();
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		contentPane.add(outputArea);
		frame.setVisible(true);
		frame.setSize(600, 400);
		out = new TextAreaOutputStream(outputArea);
	}
	
	public TextAreaOutputStream getOutputStream(){
		return out;
	}
	
	public void stop(){
		frame.dispose();
	}
}
