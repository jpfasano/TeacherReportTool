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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditableTextPanel extends JPanel implements DocumentListener {

   private TeacherReportTool trt;
   private JTextArea ta;
   private static boolean unsavedChanges = false;

   public EditableTextPanel(TeacherReportTool trt) {
      super();
      this.trt = trt;
      setMinimumSize(new Dimension(200, 100));
      setPreferredSize(new Dimension(600, 250));
      ta = new JTextArea();
      ta.getDocument().addDocumentListener(this);
      ta.setFont(new Font("TRUETYPE", Font.PLAIN, 16));
      ta.setLineWrap(true);
      ta.setWrapStyleWord(true);
      ta.setEditable(true);
      // this.setText("It's also worth pointing out that charters, while growing
      // in Texas, serve a fraction of the students traditional public schools
      // educate each year. There were more than 8,000 public schools in the
      // state, and only 580 charters, as of the 2011-2012 school year (the time
      // frame the Stanford study examined).");
      // add(ta);

      JScrollPane scrollPane = new JScrollPane(ta);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

      // studentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      this.setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();

      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 1;
      gbc.weighty = 1;
      gbc.fill = GridBagConstraints.BOTH;
      this.add(scrollPane, gbc);

   }

   // Document Listener Methods
   public void insertUpdate(DocumentEvent e) {
      setUnsavedChanges(true);
      trt.getTraMenuBar().enableSaveMenuItems(true);
   }

   public void removeUpdate(DocumentEvent e) {
      setUnsavedChanges(true);
      trt.getTraMenuBar().enableSaveMenuItems(true);
   }

   public void changedUpdate(DocumentEvent e) {
      setUnsavedChanges(true);
      trt.getTraMenuBar().enableSaveMenuItems(true);
   }

   public void insert(String t) {
      int curPos = ta.getCaretPosition();
      int curTextLen = this.getText().length();
      String newText = t.trim();
      if (newText.length() == 0)
         return;

      setUnsavedChanges(true);
      trt.getTraMenuBar().enableSaveMenuItems(true);

      if (trt.getTraMenuBar().insertAtCursorEnabled()) {
         if (curPos == curTextLen && curPos != 0)
            newText = " " + newText;
         ta.insert(newText, curPos);
      }
      else {
         if (curTextLen != 0)
            newText = " " + newText;
         ta.append(newText);
      }
   }

   public void clearEditableText() {
      ta.setText("");
      setUnsavedChanges(false);
      trt.getTraMenuBar().enableSaveMenuItems(false);
   }

   public String getText() {
      return ta.getText();
   }

   public void setText(String s) {
      if (!s.equals(getText())) {
         ta.setText(s);
      }
   }

   public static void textSaved() {
      setUnsavedChanges(false);
   }

   public static boolean unsavedChanges() {
      return unsavedChanges;
   }

   private static void setUnsavedChanges(boolean unsavedChanges) {
      EditableTextPanel.unsavedChanges = unsavedChanges;
   }
   
   public void selectAll() {
      ta.selectAll();
   }

}
