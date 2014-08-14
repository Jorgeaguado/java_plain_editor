package java_plain_editor;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.JTextComponent;

/**
 * This class replace the searched words in a JtextArea and higlight the word
 * found
 * 
 * @author Jorge Aguado
 */
public class Replace {

	/**
	 * MyHighlightPainter uses to highlight with a color the word found
	 */
	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public MyHighlightPainter(Color color) {
			super(color);
		}
	}

	private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(
	        Color.yellow);

	/**
	 * Constructor
	 * 
	 * @param textArea
	 *            where the word is going to be sought
	 */
	public Replace(JTextArea textArea) {

		this.removeHighlights(textArea);
		String pattern = JOptionPane.showInputDialog(null,
		        "Enter the word to be replace:", "Replace",
		        JOptionPane.QUESTION_MESSAGE);

		if (pattern != null) {
			try {
				pattern = pattern.toUpperCase();// Upper pattern
				Highlighter hilite = textArea.getHighlighter();
				Document doc = textArea.getDocument();
				String text = doc.getText(0, doc.getLength());
				text = text.toUpperCase();// Upper text
				int pos = 0;
				// The search is done witht the pattern and the text in upper
				while ((pos = text.indexOf(pattern, pos)) >= 0) {
					hilite.addHighlight(pos, pos + pattern.length(),
					        this.myHighlightPainter);
					pos += pattern.length();
				}

				String replaceWith = JOptionPane.showInputDialog(null,
				        " Replace the word with :", "Replace",
				        JOptionPane.QUESTION_MESSAGE);

				if (replaceWith != null) {
					pos = 0;
					// The same modifications are done in the original text and
					// the text with upper letters
					while ((pos = text.indexOf(pattern, pos)) >= 0) {
						// textArea is the original text
						textArea.replaceRange(replaceWith, pos,
						        pos + pattern.length());
						// text is the same than textArea but with upper letters
						text = text.substring(0, pos)
						        + replaceWith
						        + text.substring(pos + pattern.length(),
						                text.length());
						pos += replaceWith.length();
					}
				}

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * removeHighlights deletes all highlighted words
	 */
	public void removeHighlights(JTextComponent textComp) {
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (Highlight hilite2 : hilites) {
			if (hilite2.getPainter() instanceof MyHighlightPainter) {
				hilite.removeHighlight(hilite2);
			}
		}
	}
}
