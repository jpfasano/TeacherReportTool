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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import openSaveControl.OpenSaveControl;

public class MenuBarFile extends JMenu {
   
   private TeacherReportTool trt;
   private JCheckBoxMenuItem insertAtCursor;
   private OpenSaveControl openSaveControl = null;
   private TrtOpenSaveControlClient trtOpenSaveControlClient = null;
   private boolean saveFileDefined = false;

   private JMenuItem openNames, openSentenceTemplates;
   private JMenuItem save,saveAs;

   public MenuBarFile(TeacherReportTool trt) {
      this.trt = trt;

      setText("File");
      setMnemonic(KeyEvent.VK_F);

      JMenuItem menuItem = null;
      KeyStroke keyStroke = null;

      // Preferences
      {
         // Second level menu under "Preferences":
         JMenu preferences = new JMenu("Preferences");
         preferences.setMnemonic(KeyEvent.VK_P);
         insertAtCursor = new JCheckBoxMenuItem("Insert at cursor", false);
         insertAtCursor.setMnemonic(KeyEvent.VK_I);
         preferences.add(insertAtCursor);
         add(preferences);
         addSeparator();
      }

      // Open Student Names
      openNames = new JMenuItem();
      openNames.setText("Open Student Names ...");
      openNames.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            openSaveControl.doOpenNames();
         }
      });
      openNames.setMnemonic(KeyEvent.VK_N);
      add(openNames);

      // Open Templates
      openSentenceTemplates = new JMenuItem();
      openSentenceTemplates.setText("Open Sentence Templates ...");
      openSentenceTemplates.setMnemonic(KeyEvent.VK_T);
      openSentenceTemplates.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            openSaveControl.doOpenSentenceTemplates();
         }
      });
      add(openSentenceTemplates);

      // Open Both files
      menuItem = new JMenuItem();
      menuItem.setText("Open Both Files ...");
      menuItem.setMnemonic(KeyEvent.VK_B);
      menuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            openSaveControl.doOpen();
         }
      });
      add(menuItem);
      addSeparator();

      // Save
      save = new JMenuItem();
      save.setText("Save");
      save.setMnemonic(KeyEvent.VK_S);
      save.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            openSaveControl.doSave();
         }
      });
      keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
      save.setAccelerator(keyStroke);
      add(save);

      // Save As
      saveAs = new JMenuItem();
      saveAs.setText("Save As ...");
      saveAs.setMnemonic(KeyEvent.VK_A);
      saveAs.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            openSaveControl.doSaveAs();
         }
      });
      add(saveAs);
      

      // Exit
      menuItem = new JMenuItem();
      menuItem.setText("Exit");
      menuItem.setMnemonic(KeyEvent.VK_X);
      menuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            trt.updateStudentReportFromEditableText();
            if (!openSaveControl.unsavedWork()) {
               System.exit(0);
            }
         }
      });
      add(menuItem);

      // Must be instantiated after the menu items exist
      trtOpenSaveControlClient = new TrtOpenSaveControlClient(trt);
      openSaveControl = new OpenSaveControl(trt, trtOpenSaveControlClient);
      
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
   
   public void enableSaveMenuItems(Boolean b) {
      if (!EditableTextPanel.unsavedChanges()) {
         saveAs.setEnabled(false);
         save.setEnabled(false);
      }
      else {
         if (b) {
            saveAs.setEnabled(true);
            if (saveFileDefined)
               save.setEnabled(true);
            else
               save.setEnabled(false);
         }
         else {
            saveAs.setEnabled(false);
            save.setEnabled(false);
         }
      }

      String newTitle = "TeacherReportTool";
      if (EditableTextPanel.unsavedChanges()) {
         newTitle += "*";
      }
      trt.setTitle(newTitle);
   }
   
   public void setSaveFileDefined(boolean saveFileDefined) {
      this.saveFileDefined = saveFileDefined;
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
   
}
