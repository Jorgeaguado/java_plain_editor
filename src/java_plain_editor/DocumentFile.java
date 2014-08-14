package java_plain_editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class manages the documents. It closes and opens files. It registers the
 * current path and whether It is modified or It has been saved
 * 
 * @author Jorge Aguado
 */
public class DocumentFile {

	private File fileDir = new File(".");
	private boolean saved = false;// The text has been saved
	private boolean modified = false;// the text has been modified

	/**
	 * Constructor with default values
	 */
	public DocumentFile() {
		this.fileDir = new File(".");
	}

	/**
	 * Get the path of the file
	 */
	public String getPath() {
		return this.fileDir.getAbsolutePath();
	}

	/**
	 * @return true if the file is modified
	 */
	public boolean isDocumentModified() {
		return this.modified;
	}

	/**
	 * @return the field saved
	 */
	public boolean isDocumentSaved() {
		return this.saved;
	}

	/**
	 * Action open document from the menu
	 * 
	 * @param JTextArea
	 *            where the content of the file is going to be display
	 * @return true if the file is found and opened
	 **/
	public boolean openDocument(JTextArea text) {
		boolean documentOpened = false;
		this.fileDir = new File(".");
		JFileChooser chooser = new JFileChooser(this.fileDir);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new FileNameExtensionFilter("TXT Documents",
		        "txt"));

		int option = chooser.showOpenDialog(null);
		StringBuilder contents = new StringBuilder();

		if (option == JFileChooser.APPROVE_OPTION) {
			text.setText("");
			this.fileDir = chooser.getSelectedFile();
			try {
				BufferedReader input = new BufferedReader(new FileReader(
				        this.fileDir));
				try {
					String line = null;
					while ((line = input.readLine()) != null) {
						contents.append(line);
						contents.append(System.getProperty("line.separator"));
					}
				} finally {
					input.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			text.append(contents.toString());
			documentOpened = true;
		}

		return documentOpened;
	}

	/**
	 * Save the document that has been modified
	 * 
	 * @param JTextArea
	 *            to save
	 * @return true is the file is saved
	 */
	public boolean savedModifiedDocument(JTextArea text) {
		boolean savedModifiedDocument = false;
		int option = JOptionPane.showOptionDialog(null,
		        "Do you want to save this document ?", "Save file",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
		        new Object[] { "Yes", "No" }, "Yes");

		if (option == JOptionPane.YES_OPTION) {
			if (this.saved) {
				this.saveDocument(text);
			} else {
				this.saveDocumentAs(text);
			}

			savedModifiedDocument = true;
		} else {
			savedModifiedDocument = false;
		}

		return savedModifiedDocument;
	}

	/**
	 * Save the document
	 * 
	 * @param JTextArea
	 *            to save
	 * @return true is the file is saved
	 */
	public boolean saveDocument(JTextArea text) {
		boolean saveDocument = false;
		try {
			BufferedWriter buffFile = new BufferedWriter(new FileWriter(
			        this.fileDir));
			text.write(buffFile);
			saveDocument = true;

		} catch (IOException ex) {
			saveDocument = false;
			ex.printStackTrace();
		}

		return saveDocument;
	}

	/**
	 * Save the document with name
	 * 
	 * @param JTextArea
	 *            to save
	 * @return true is the file is saved
	 */
	public boolean saveDocumentAs(JTextArea text) {
		boolean saveDocuentAs = false;
		this.fileDir = new File(".");
		JFileChooser chooser = new JFileChooser(this.fileDir);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new FileNameExtensionFilter("TXT Documents",
		        "txt"));
		int option = chooser.showSaveDialog(null);

		if (option == JFileChooser.APPROVE_OPTION) {
			String filePath = chooser.getSelectedFile().getPath();

			if (!filePath.toLowerCase().endsWith(".txt")) {
				this.fileDir = new File(chooser.getSelectedFile()
				        .getAbsolutePath() + ".txt");
				this.saveDocument(text);
			} else {
				this.fileDir = new File(chooser.getSelectedFile()
				        .getAbsolutePath());
				this.saveDocument(text);
			}

			saveDocuentAs = true;
		}

		return saveDocuentAs;
	}

	/**
	 * Set the current directory
	 */
	public void setCurrentDirectory() {
		this.fileDir = new File(".");
	}

	/**
	 * Set the field modified to true
	 */
	public void setModified(boolean modifi) {
		this.modified = modifi;
	}

	/**
	 * Set the field saved
	 */
	public void setSaved(boolean paramsav) {
		this.saved = paramsav;
	}

}
