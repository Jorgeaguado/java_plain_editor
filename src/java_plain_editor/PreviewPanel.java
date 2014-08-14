package java_plain_editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Class to preview the options choosen in a preview panel
 * 
 * @author Jorge Aguado
 */
public class PreviewPanel extends JPanel {
	/**
	 * 
	 */
	private int size = 14;
	private Color fontColor = Color.BLACK;
	private Color backColor = Color.WHITE;

	/**
	 * Get the current background color
	 * 
	 * @return background color
	 */
	public Color getBackgroundColor() {
		return this.backColor;
	}

	/**
	 * Get the Size of the current font
	 * 
	 * @return color
	 */
	public Color getFontColor() {
		return this.fontColor;
	}

	/**
	 * Get the Size of the current font
	 * 
	 * @return size
	 */
	public int getTheSize() {
		return this.size;
	}

	/**
	 * Paint the font with the message "This is a preview of the choosen font"
	 * 
	 * @param Graphics
	 *            to paint
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(this.backColor);
		g.setColor(this.fontColor);
		g.setFont(new Font(null, Font.PLAIN, this.size));
		g.drawString("This is a preview of the choosen font", 60, 40);
	}

	/**
	 * Set the background color
	 * 
	 * @param color
	 */
	public void setBackgroundColor(Color auxColor) {
		this.backColor = auxColor;
	}

	/**
	 * Set the color of the font
	 * 
	 * @param color
	 *            of the font
	 */
	public void setFontColor(Color auxColor) {
		this.fontColor = auxColor;
	}

	/**
	 * Set the size of the font
	 * 
	 * @param size
	 */
	public void setSize(int num) {
		this.size = num;
	}
}
