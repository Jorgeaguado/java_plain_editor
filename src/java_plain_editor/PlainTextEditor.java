package java_plain_editor;

import javax.swing.SwingUtilities;

/**
 * Class PlaintTextEditor with threads to invokeLater
 * 
 * @author Jorge Aguado
 */
public class PlainTextEditor implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PlainTextEditor editor = new PlainTextEditor();
		SwingUtilities.invokeLater(editor);
	}

	/**
	 * Implementation of the abstract method run
	 */
	@Override
	public void run() {
		DrawFrame frame = new DrawFrame();
	}

}
