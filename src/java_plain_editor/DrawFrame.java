package java_plain_editor;

import static java.awt.event.InputEvent.ALT_DOWN_MASK;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.TransferHandler;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.DefaultEditorKit;

public class DrawFrame extends JFrame implements MenuListener, ActionListener,
        DocumentListener {
	/**
	 * 
	 */
	private JMenuBar menuBar = new JMenuBar();
	private final JTextArea textArea = new JTextArea();
	private final FontFormatPanel fontPanel = new FontFormatPanel();
	private final JFrame frameFont = new JFrame();
	private DocumentFile file = new DocumentFile();
	private boolean wrap = false;
	private JScrollPane scrollV = new JScrollPane(this.textArea);
	private JScrollPane scrollH = new JScrollPane(this.textArea);

	/**
	 * Constructor with default values
	 */
	public DrawFrame() {
		int inset = 120;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(inset, inset, screenSize.width - (inset * 2),
		        screenSize.height - (inset * 2));
		// Parameters for the JTextArea
		this.textArea.getDocument().addDocumentListener(this);
		this.textArea.setEditable(true);
		this.textArea.setVisible(true);
		this.textArea.setOpaque(true);
		this.textArea.setFont(new Font("SanSerif", Font.PLAIN, 14));
		this.textArea.setDragEnabled(true);
		// Scrolls is added
		this.scrollV
		        .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollH
		        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// The way the document is closed
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (DrawFrame.this.file.isDocumentModified()) {
					DrawFrame.this.file
					        .savedModifiedDocument(DrawFrame.this.textArea);
				}
				System.exit(0);
			}
		});

		// All is added to the frame
		this.setVisible(true);
		this.setJMenuBar(this.createMenuBar());
		this.add(this.scrollV);
		this.add(this.scrollH);
		this.setTitle(this.file.getPath());

	}

	/**
	 * All the operations done when clicked in the method createMenuBar
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if ("New".equals(e.getActionCommand())) {
			if (this.file.isDocumentModified()) {
				this.file.savedModifiedDocument(this.textArea);// The old
				                                               // document is
			}
			// saved
			this.file.setCurrentDirectory();
			this.textArea.setText("");
			this.file.setModified(false);
			this.file.setSaved(false);
			this.setTitle(this.file.getPath());
		} else if ("Open".equals(e.getActionCommand())) {
			boolean open = false;
			if (this.file.isDocumentModified()) {
				this.file.savedModifiedDocument(this.textArea);// The old
				                                               // document is
			}
			// saved
			open = this.file.openDocument(this.textArea);
			if (open) {
				this.setTitle(this.file.getPath());
				this.file.setModified(false);
				this.file.setSaved(true);// This is a new document so It is
				                         // saved yet

			}
		} else if ("Save".equals(e.getActionCommand())) {
			boolean saved = false;
			if (this.file.isDocumentSaved()) {
				saved = this.file.saveDocument(this.textArea);// If it has been
				                                              // yet saved
				// before
			} else {
				saved = this.file.saveDocumentAs(this.textArea);
			}

			if (saved) {
				this.setTitle(this.file.getPath());
				this.file.setModified(false);
				this.file.setSaved(true);
			}
		} else if ("Save as".equals(e.getActionCommand())) {
			boolean saved = false;
			saved = this.file.saveDocumentAs(this.textArea);
			if (saved) {
				this.setTitle(this.file.getPath());
				this.file.setModified(false);
				this.file.setSaved(true);
			}
		} else if ("Exit".equals(e.getActionCommand())) {
			if (this.file.isDocumentModified()) {
				this.file.savedModifiedDocument(this.textArea);
			}
			System.exit(0);
		} else if ("Copy".equals(e.getActionCommand())) {
			this.copy();
		} else if ("Paste".equals(e.getActionCommand())) {
			this.paste();
		} else if ("Cut".equals(e.getActionCommand())) {
			this.copy();
			this.textArea.replaceSelection("");
		} else if ("Search".equals(e.getActionCommand())) {
			Search search = new Search(this.textArea);
			search.removeHighlights(this.textArea);
		} else if ("Replace".equals(e.getActionCommand())) {
			Replace replace = new Replace(this.textArea);
			replace.removeHighlights(this.textArea);
		} else if ("Font".equals(e.getActionCommand())) {
			this.fontPanel.updatePanel();
			int option = JOptionPane.showOptionDialog(null, this.fontPanel,
			        "Set the font", JOptionPane.OK_CANCEL_OPTION,
			        JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Ok",
			                "Cancel" }, "opcion 1");

			if (option == 0) {
				this.fontPanel.updateSelectedOptions();
				this.textArea.setFont(new Font("Serif", Font.PLAIN,
				        this.fontPanel.getFontSize()));
				this.textArea.setForeground(this.fontPanel.getFontColor());
				this.textArea
				        .setBackground(this.fontPanel.getBackgroundColor());
			}
		} else if ("Word Wrap".equals(e.getActionCommand())) {
			if (this.wrap == false) {
				this.textArea.setLineWrap(true);
				this.textArea.setWrapStyleWord(true);
				this.wrap = true;
			} else {
				this.wrap = false;
				this.textArea.setLineWrap(false);
				this.textArea.setWrapStyleWord(false);
			}
		} else if (("About").equals(e.getActionCommand())) {
			JOptionPane
			        .showMessageDialog(
			                null,
			                "This software has been proudly developed by Jorge Aguado.\n It can be distributed with GPLv3 license.",
			                "About", JOptionPane.PLAIN_MESSAGE);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		this.setTitle(this.file.getPath() + "*");// The title bar is modified
		this.file.setModified(true);
	}

	/**
	 * Save the text selected in the clipboard
	 */
	private void copy() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		TransferHandler transferHandler = this.textArea.getTransferHandler();
		transferHandler.exportToClipboard(this.textArea, clipboard,
		        TransferHandler.COPY);
	}

	/**
	 * Actions to display in the menu bar
	 */
	private JMenuBar createMenuBar() {

		JMenu myMenu = new JMenu("File");// *** File menu ***
		JMenuItem myItem = new JMenuItem("New");
		myItem.setActionCommand("New");
		myItem.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_DOWN_MASK));
		myItem.addActionListener(this);
		myMenu.add(myItem);
		myMenu.addSeparator();
		myItem = new JMenuItem("Open");
		myItem.setActionCommand("Open");
		myItem.setAccelerator(KeyStroke.getKeyStroke('O', CTRL_DOWN_MASK));
		myItem.addActionListener(this);
		myMenu.add(myItem);
		myMenu.addSeparator();
		myItem = new JMenuItem("Save");
		myItem.setActionCommand("Save");
		myItem.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));
		myItem.addActionListener(this);
		myMenu.add(myItem);
		myMenu.addSeparator();
		myItem = new JMenuItem("Save as");
		myItem.setActionCommand("Save as");
		myItem.setAccelerator(KeyStroke.getKeyStroke('S', ALT_DOWN_MASK));
		myItem.addActionListener(this);
		myMenu.add(myItem);
		myMenu.addSeparator();
		myItem = new JMenuItem("Exit");
		myItem.setActionCommand("Exit");
		myItem.setAccelerator(KeyStroke.getKeyStroke('E', CTRL_DOWN_MASK));
		myItem.addActionListener(this);
		myMenu.add(myItem);

		JMenu menuEdit = new JMenu("Edit");// *** Edit menu ***
		JMenuItem itemEdit = new JMenuItem("Copy");
		itemEdit.setActionCommand("Copy");
		itemEdit.setAccelerator(KeyStroke.getKeyStroke('C', CTRL_DOWN_MASK));
		itemEdit.addActionListener(this);
		menuEdit.add(itemEdit);
		menuEdit.addSeparator();
		itemEdit = new JMenuItem(new DefaultEditorKit.CutAction());
		itemEdit.setText("Cut");
		itemEdit.setAccelerator(KeyStroke.getKeyStroke('X', CTRL_DOWN_MASK));
		menuEdit.add(itemEdit);
		menuEdit.addSeparator();
		itemEdit = new JMenuItem(new DefaultEditorKit.PasteAction());
		itemEdit.setText("Paste");
		itemEdit.setAccelerator(KeyStroke.getKeyStroke('V', CTRL_DOWN_MASK));
		menuEdit.add(itemEdit);
		menuEdit.addSeparator();
		itemEdit = new JMenuItem("Search");
		itemEdit.setActionCommand("Search");
		itemEdit.setAccelerator(KeyStroke.getKeyStroke('F', CTRL_DOWN_MASK));
		itemEdit.addActionListener(this);
		menuEdit.add(itemEdit);
		menuEdit.addSeparator();
		itemEdit = new JMenuItem("Replace");
		itemEdit.setActionCommand("Replace");
		itemEdit.setAccelerator(KeyStroke.getKeyStroke('R', CTRL_DOWN_MASK));
		itemEdit.addActionListener(this);
		menuEdit.add(itemEdit);

		JMenu menuFormat = new JMenu("Format");// *** Format menu ***
		JMenuItem itemFormat = new JMenuItem("Font");
		itemFormat.setActionCommand("Font");
		itemFormat.setAccelerator(KeyStroke.getKeyStroke('F', ALT_DOWN_MASK));
		itemFormat.addActionListener(this);
		menuFormat.add(itemFormat);
		menuFormat.addSeparator();
		itemFormat = new JMenuItem("Word Wrap");
		itemFormat.setActionCommand("Word Wrap");
		itemFormat.setAccelerator(KeyStroke.getKeyStroke('W', ALT_DOWN_MASK));
		itemFormat.addActionListener(this);
		menuFormat.add(itemFormat);

		JMenu menuHelp = new JMenu("Help");// *** Help menu ***
		JMenuItem itemHelp = new JMenuItem("About");
		itemHelp.setActionCommand("About");
		itemHelp.setAccelerator(KeyStroke.getKeyStroke('A', ALT_DOWN_MASK));
		itemHelp.addActionListener(this);
		menuHelp.add(itemHelp);

		myMenu.addMenuListener(this);
		menuEdit.addMenuListener(this);
		menuFormat.addMenuListener(this);

		this.menuBar.add(myMenu);
		this.menuBar.add(menuEdit);
		this.menuBar.add(menuFormat);
		this.menuBar.add(menuHelp);

		return this.menuBar;
	}

	/**
	 * A signal is writing in the title bar when the document is modified
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		this.setTitle(this.file.getPath() + "*");// The title bar is modified
		this.file.setModified(true);
	}

	@Override
	public void menuCanceled(MenuEvent me) {
	}

	@Override
	public void menuDeselected(MenuEvent me) {
	}

	@Override
	public void menuSelected(MenuEvent me) {
	}

	/**
	 * Write the text from the clipboard in the position of the cursor
	 */
	private void paste() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		TransferHandler transferHandler = this.textArea.getTransferHandler();
		transferHandler.importData(this.textArea, clipboard.getContents(null));
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.setTitle(this.file.getPath() + "*");// The title bar is modified
		this.file.setModified(true);
	}

}
