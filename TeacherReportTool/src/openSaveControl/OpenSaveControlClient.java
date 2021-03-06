/*
 * ******************************************************************************
 *  Copyright (c) 2015-2016 JP Fasano.
 *
 *  This file is part of the TeacherReportTool.
 *
 *  TeacherReportTool is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TeacherReportTool is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with TeacherReportTool.  If not, see <http://www.gnu.org/licenses/>.
 *  ******************************************************************************
 */

package openSaveControl;

import java.io.File;

// Client methods to be implemented by users of OpenSaveControl 

public interface OpenSaveControlClient {

    // After open file selection dialog this method does the actual file reading
    void openReadFile(File f);

    // After save dialog, this method does the actual file writing
    void writeFile(File f);

    // Enable/Disable File menu bar items
    void enableSaveMenuItem(Boolean b);

    void enableSaveAsMenuItem(Boolean b);

    void enableSaveMenuItems(Boolean b);

    //public boolean unsavedWork();

    // Corruption of design
    boolean openReadNamesFile(File f);

    void enableOpenNamesMenuItem(Boolean b);

    boolean openReadSentenceTemplatesFile(File f);

    void enableOpenSentenceTemplatesMenuItem(Boolean b);

    void defaultSaveAsFilenameHasNotBeenWritten();

}
