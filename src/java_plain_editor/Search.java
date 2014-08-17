package java_plain_editor;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.JTextComponent;

/**
 * This class search in a JtextArea and higlight the words found
 * 
 * @author Jorge Aguado
 */

public class Search {
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
	 *            where it is going to be sought the word
	 */
	public Search(JTextArea textArea) {
		int option = 0; // Option selected in a showOptionDialog
		// with two buttons  0 - Find next match	 1 - Cancel
		this.removeHighlights(textArea);
		String pattern = JOptionPane.showInputDialog(null,
		        "Enter the word to be searched:", "Search",
		        JOptionPane.QUESTION_MESSAGE);

		if ((pattern != null) && (pattern != "")) {// Not null
			try {
				pattern = pattern.toUpperCase();
				Highlighter hilite = textArea.getHighlighter();
				Document doc = textArea.getDocument();
				String text = doc.getText(0, doc.getLength());
				text = text.toUpperCase();
				int pos = 0;
				while (((pos = text.indexOf(pattern, pos)) >= 0)
				        && (option == 0)) {
					// The scroll is moved to the correct position
					Rectangle rect = textArea.modelToView(pos);
					textArea.scrollRectToVisible(rect);
					// Add a higlight
					hilite.addHighlight(pos, pos + pattern.length(),
					        this.myHighlightPainter);
					option = JOptionPane.showOptionDialog(null,
					        "Find next match", "Search",
					        JOptionPane.OK_CANCEL_OPTION,
					        JOptionPane.QUESTION_MESSAGE, null, // null para
					                                            // icono por
					                                            // defecto.
					        new Object[] { "Next", "Cancel" }, // null para YES,
					                                           // NO y CANCEL
					        null);
					if (option != 0) {
						break;
					}
					hilite.removeAllHighlights();
					pos += pattern.length();
				}
				
				//We here if the search has finished or the user has selected option = 1 (cancel)

				if (option == 0) {// If option is 0 - Find next matches
					// We have already search in all text
					JOptionPane.showMessageDialog(null,
					        "No more matches found", "Search",
					        JOptionPane.PLAIN_MESSAGE);
				}

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Delete all highlighted words
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
