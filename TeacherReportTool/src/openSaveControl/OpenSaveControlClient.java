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
package openSaveControl;

import java.io.File;

// Client methods to be implemented by users of OpenSaveControl 

public interface OpenSaveControlClient {
  
  // After open file selection dialog this method does the actual file reading
  public void openReadFile(File f);
  
  // After save dialog, this method does the actual file writing
  public void writeFile(File f);
  
  // Enable/Disable File menu bar items
  public void enableSaveMenuItem(Boolean b);
  public void enableSaveAsMenuItem(Boolean b);
  
  public boolean unsavedWork();
  
  //Corruption of design
  public boolean openReadNamesFile(File f);
  public void enableOpenNamesMenuItem(Boolean b);
  
  public boolean openReadSentenceTemplatesFile(File f);
  public void enableOpenSentenceTemplatesMenuItem(Boolean b);

}
