package java_plain_editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class FontFormatPanel uses to set the font of the set This contains the
 * class PreviewPanel used to preview the chosen options
 * 
 * @author Jorge Aguado
 */
public class FontFormatPanel extends JPanel {
	/**
	 * 
	 */
	private JComboBox<String> comboSize;// Size
	private JComboBox<String> comboBackground; // Color of Background
	private JComboBox<String> comboLetterColor;// Color of letter
	// These sizes shown in a combobox
	private String sizes[] = { "8", "10", "12", "14", "16", "18", "20", "22",
	        "24" };
	// These colors shown in a combobox
	private String color[] = { "BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY",
	        "GREEN", "LIGHT_GRAY", "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK",
	        "RED", "WHITE", "YELLOW" };
	// colorCode contains codes in the same position than color array
	private Color colorCode[] = { Color.BLACK, Color.BLUE, Color.CYAN,
	        Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY,
	        Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK,
	        Color.RED, Color.WHITE, Color.YELLOW };

	private JLabel labelSize = new JLabel("Size");
	private JLabel labelBackgroundColor = new JLabel("Background Color");
	private JLabel labelLetterColor = new JLabel("Font Color");

	private int selectedIndexSize = 3;// This is the initial size of the text
	private int preSelectedIndexSize = 3;// This is the preview size of the text
	private int selectedIndexColorFont = 0;
	private int preSelectedIndexColorFont = 0;
	private int selectedIndexColorBackg = 12;// This is the current background
	                                         // color of the text
	private int preSelectedIndexColorBack = 12;// This is the preview background
	                                           // color of the text

	final PreviewPanel panelSouth = new PreviewPanel();
	private JPanel panelNorth = new JPanel(new FlowLayout());
	private JPanel panelCenter = new JPanel(new FlowLayout());

	/**
	 * Constructor with default values
	 */
	public FontFormatPanel() {
		this.panelSouth.setPreferredSize(new Dimension(50, 50));// Panel with a
		// paintComponent
		// method used to
		// preview
		this.setLayout(new BorderLayout());

		this.previewSize();// It contains the combo for the size
		this.previewBackgroundColor();// It contains the combo for the
		                              // background
		// color
		this.previewFontColor();// It contains the combo for the font color

		this.add(this.panelNorth, BorderLayout.NORTH);
		this.add(this.panelCenter, BorderLayout.CENTER);
		this.add(this.panelSouth, BorderLayout.SOUTH);

	}

	/**
	 * @return the current background color
	 */
	public Color getBackgroundColor() {
		return this.colorCode[this.preSelectedIndexColorBack];
	}

	/**
	 * @return the current color of the font from the array colors
	 */
	public Color getFontColor() {
		return this.colorCode[this.preSelectedIndexColorFont];
	}

	/**
	 * @return the current size of the font from the array sizes
	 */
	public int getFontSize() {
		return Integer.valueOf(this.sizes[this.selectedIndexSize]).intValue();
	}

	/**
	 * Draw the preview background color before accepting it
	 */
	public void previewBackgroundColor() {
		this.comboBackground = new JComboBox<String>(this.color);
		this.comboBackground.setMaximumRowCount(4);
		this.comboBackground.setSelectedIndex(this.selectedIndexColorBackg);

		this.comboBackground.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					FontFormatPanel.this.panelSouth
					        .setBackgroundColor(FontFormatPanel.this.colorCode[FontFormatPanel.this.comboBackground
					                .getSelectedIndex()]);
					FontFormatPanel.this.panelSouth.repaint();
					FontFormatPanel.this.preSelectedIndexColorBack = FontFormatPanel.this.comboBackground
					        .getSelectedIndex();
				}
			}
		});
		this.panelNorth.add(this.labelBackgroundColor);
		this.panelNorth.add(this.comboBackground);
	}

	/**
	 * Draw the preview font color before accepting it
	 */
	public void previewFontColor() {
		this.comboLetterColor = new JComboBox<String>(this.color);
		this.comboLetterColor.setMaximumRowCount(4);
		this.comboLetterColor.setSelectedIndex(this.selectedIndexColorFont);

		this.comboLetterColor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					FontFormatPanel.this.panelSouth
					        .setFontColor(FontFormatPanel.this.colorCode[FontFormatPanel.this.comboLetterColor
					                .getSelectedIndex()]);
					FontFormatPanel.this.panelSouth.repaint();
					FontFormatPanel.this.preSelectedIndexColorFont = FontFormatPanel.this.comboLetterColor
					        .getSelectedIndex();
				}
			}
		});
		this.panelCenter.add(this.labelLetterColor);
		this.panelCenter.add(this.comboLetterColor);
	}

	/**
	 * Draw the preview size before accepting it
	 */
	public void previewSize() {
		this.comboSize = new JComboBox<String>(this.sizes);
		this.comboSize.setMaximumRowCount(4);
		this.comboSize.setSelectedIndex(this.selectedIndexSize);
		this.comboSize.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					FontFormatPanel.this.panelSouth.setSize(Integer
					        .valueOf(FontFormatPanel.this.sizes[FontFormatPanel.this.comboSize
					                .getSelectedIndex()]));
					FontFormatPanel.this.panelSouth.repaint();
					FontFormatPanel.this.preSelectedIndexSize = FontFormatPanel.this.comboSize
					        .getSelectedIndex();
				}
			}
		});
		this.panelNorth.add(this.labelSize);
		this.panelNorth.add(this.comboSize);
	}

	/**
	 * Update the options of the FontPanel with the current values of the opened
	 * document
	 */
	public void updatePanel() {
		this.comboBackground.setSelectedIndex(this.selectedIndexColorBackg);
		this.comboSize.setSelectedIndex(this.selectedIndexSize);
		this.comboLetterColor.setSelectedIndex(this.selectedIndexColorFont);
	}

	/**
	 * Update the options choosen in the panel in the opened document
	 */
	public void updateSelectedOptions() {
		this.selectedIndexSize = this.preSelectedIndexSize;
		this.selectedIndexColorFont = this.preSelectedIndexColorFont;
		this.selectedIndexColorBackg = this.preSelectedIndexColorBack;
	}

}
