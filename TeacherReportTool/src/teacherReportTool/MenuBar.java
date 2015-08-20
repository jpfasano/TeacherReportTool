/*******************************************************************************
 * Copyright (c) 2015 JP Fasano.
 *
 * This file is part of the TeacherReportTool.
 *
 * TeacherReportTool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TeacherReportTool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TeacherReportTool.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package teacherReportTool;
/**
 * A menu bar for the TeacherReportTool trl
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import openSaveControl.OpenSaveControl;

public class MenuBar extends JMenuBar implements ActionListener {
	private TeacherReportTool trl;
	private JCheckBoxMenuItem insertAtCursor;
	private JMenuItem openNames, openSentenceTemplates, openDir, exit, showHelp, about;
	private JMenuItem save, saveAs;
	private boolean saveFileDefined = false;

	private OpenSaveControl openSaveControl;
	private TrtOpenSaveControlClient trtOpenSaveControlClient;

	public MenuBar(TeacherReportTool trl) {
		this.trl = trl;

		// "File" menu:

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		// Second level menu under "Preferences":
		JMenu preferences = new JMenu("Preferences");
		preferences.setMnemonic(KeyEvent.VK_P);
		insertAtCursor = new JCheckBoxMenuItem("Insert at cursor", false);
		insertAtCursor.setMnemonic(KeyEvent.VK_I);
		preferences.add(insertAtCursor);
		
		// Open Student Names

		openNames = new JMenuItem("Open Student Names ...");
		openNames.addActionListener(this);
		openNames.setMnemonic(KeyEvent.VK_N);

		//		Action openStudentNames = new AbstractAction("Open Student NamesXXX") {
//			 
//		    @Override
//		    public void actionPerformed(ActionEvent e) {
//		    	openSaveControl.doOpenNames();
//		    }
//		};
//		 
//		openStudentNames.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
//		openNames.setAction(openStudentNames);
		
		
		// Open Student Templates

		openSentenceTemplates = new JMenuItem("Open Sentence Templates ...");
		openSentenceTemplates.setMnemonic(KeyEvent.VK_T);
		openSentenceTemplates.addActionListener(this);
		
		// Open Directory

		openDir = new JMenuItem("Open Both Files ...");
		openDir.setMnemonic(KeyEvent.VK_B);
		openDir.addActionListener(this);

		// Save
		
		save = new JMenuItem("Save");
		save.setMnemonic(KeyEvent.VK_S);
		save.addActionListener(this);
		
		KeyStroke keyStrokeToOpen = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		save.setAccelerator(keyStrokeToOpen);
		
		// Save As

		saveAs = new JMenuItem("Save As ...");
		save.setMnemonic(KeyEvent.VK_A);
		saveAs.addActionListener(this);
		
		// Exit

		exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		exit.addActionListener(this);

		fileMenu.add(preferences);
		fileMenu.addSeparator();
		fileMenu.add(openNames);
		fileMenu.add(openSentenceTemplates);
		fileMenu.add(openDir);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		add(fileMenu);

		// "Help" menu:

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		showHelp = new JMenuItem("Help ...");
		showHelp.setMnemonic(KeyEvent.VK_H);
		showHelp.addActionListener(this);

		about = new JMenuItem("About...");
		about.setMnemonic(KeyEvent.VK_A);
		about.addActionListener(this);

		helpMenu.add(showHelp);
		helpMenu.add(about);

		add(helpMenu);

		// Must be instantiated after the menu items exist
		trtOpenSaveControlClient = new TrtOpenSaveControlClient(trl);
		openSaveControl = new OpenSaveControl(trl, trtOpenSaveControlClient);

		// Save and SaveAs are not available until after OpenDir or
		// OpenSentenceTemplates are done.
		// This could be changed so that save is only available when there are
		// some changes to the editable text.
		save.setEnabled(false);
		saveAs.setEnabled(false);

		// Names file must be opened before SentenceTemplates file.
		openSentenceTemplates.setEnabled(false);
	}

	public boolean insertAtCursorEnabled() {
		return insertAtCursor.isSelected();
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem src = (JMenuItem) e.getSource();

		if (src == openNames) {
			openSaveControl.doOpenNames();
		} else if (src == openSentenceTemplates) {
			openSaveControl.doOpenSentenceTemplates();
		} else if (src == openDir) {
			openSaveControl.doOpen();
		} else if (src == save) {
			openSaveControl.doSave();
		} else if (src == saveAs) {
			openSaveControl.doSaveAs();
		} else if (src == showHelp)
			new HelpMenuBar().showHelp();
		else if (src == about)
			HelpMenuBar.showAbout();
		else if (src == exit) {
			trl.updateStudentReportFromEditableText();
			if (!openSaveControl.unsavedWork()) {
				System.exit(0);
			}
			// System.out.println(trl.getReports());
		}
	}

	// Enable/Disable File menu bar items
	public void enableOpenNamesMenuItem(Boolean b) {
		openNames.setEnabled(b);
	}

	public void enableOpenSentenceTemplatesMenuItem(Boolean b) {
		openSentenceTemplates.setEnabled(b);
	}

	public void enableSaveMenuItem(Boolean b) {
		save.setEnabled(b);
	}

	public void enableSaveAsMenuItem(Boolean b) {
		saveAs.setEnabled(b);
	}

	public void enableSaveMenuItems(Boolean b) {
		if (!EditableTextPanel.unsavedChanges()) {
			saveAs.setEnabled(false);
			save.setEnabled(false);
		} else {
			if (b) {
				saveAs.setEnabled(true);
				if (saveFileDefined)
					save.setEnabled(true);
				else
					save.setEnabled(false);
			} else {
				saveAs.setEnabled(false);
				save.setEnabled(false);
			}
		}
	}

	public void setSaveFileDefined(boolean saveFileDefined) {
		this.saveFileDefined = saveFileDefined;
	}

}