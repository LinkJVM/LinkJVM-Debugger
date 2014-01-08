package linkjvm.debugger.client;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream{
	private JTextArea textArea;
	
	public TextAreaOutputStream(JTextArea textArea){
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		textArea.append(Character.toString((char) b)); 
	}
}
