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

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar /* implements ActionListener */ {
   private MenuBarFile mbf;

   public MenuBar(TeacherReportTool trt) {

      // "File" menu:
      mbf = new MenuBarFile(trt);
      add(mbf);

      // "Edit" menu:
      add(new MenuBarEdit(trt));

      // "Help" menu:
      add(new MenuBarHelp(trt));

   }

   public boolean insertAtCursorEnabled() {
      return mbf.insertAtCursorEnabled();
   }

   // Enable/Disable File menu bar items
   public void enableOpenNamesMenuItem(Boolean b) {
      mbf.enableOpenNamesMenuItem(b);
   }

   public void enableOpenSentenceTemplatesMenuItem(Boolean b) {
      mbf.enableOpenSentenceTemplatesMenuItem(b);
   }

   public void enableSaveMenuItem(Boolean b) {
      mbf.enableSaveMenuItem(b);
   }

   public void enableSaveAsMenuItem(Boolean b) {
      mbf.enableSaveAsMenuItem(b);
   }

   public void enableSaveMenuItems(Boolean b) {
      mbf.enableSaveMenuItems(b);
   }

   public void setSaveFileDefined(boolean saveFileDefined) {
      mbf.enableSaveMenuItems(saveFileDefined);
   }

}
