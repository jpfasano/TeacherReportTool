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

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

public class MenuBarEdit extends JMenu {

   public MenuBarEdit(TeacherReportTool trt) {
      
      setText("Edit");
      setMnemonic(KeyEvent.VK_E);
      
      JMenuItem menuItem = null;
      KeyStroke keyStroke = null;
      
      menuItem = new JMenuItem();
      menuItem.setText("Select All");
      menuItem.addActionListener(new ActionListener() {     
         public void actionPerformed(ActionEvent e) {
            trt.getContentPanel().getEditableTextPanel().selectAll();
         }
      });
      menuItem.setMnemonic(KeyEvent.VK_A);
      keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
      menuItem.setAccelerator(keyStroke);
      add(menuItem);

      menuItem = new JMenuItem();
      menuItem.setText("Cut");
      menuItem.addActionListener(new ActionListener() {     
         public void actionPerformed(ActionEvent e) {
            trt.getContentPanel().getEditableTextPanel().grabFocus();
         }
      });
      menuItem.addActionListener(new DefaultEditorKit.CutAction());
      menuItem.setMnemonic(KeyEvent.VK_T);
      keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);
      menuItem.setAccelerator(keyStroke);
      add(menuItem);

      menuItem = new JMenuItem();
      menuItem.setText("Copy");
      menuItem.addActionListener(new ActionListener() {     
         public void actionPerformed(ActionEvent e) {
            trt.getContentPanel().getEditableTextPanel().grabFocus();
         }
      });
      menuItem.addActionListener(new DefaultEditorKit.CopyAction());
      menuItem.setMnemonic(KeyEvent.VK_C);
      keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
      menuItem.setAccelerator(keyStroke);
      add(menuItem);

      menuItem = new JMenuItem();
      menuItem.setText("Paste");
      menuItem.addActionListener(new ActionListener() {     
         public void actionPerformed(ActionEvent e) {
            trt.getContentPanel().getEditableTextPanel().grabFocus();
         }
      });
      menuItem.addActionListener(new DefaultEditorKit.PasteAction());
      menuItem.setMnemonic(KeyEvent.VK_P);
      keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
      menuItem.setAccelerator(keyStroke);
      add(menuItem);

   }

}
