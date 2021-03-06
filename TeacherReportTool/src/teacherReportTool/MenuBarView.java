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

package teacherReportTool;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class MenuBarView extends MenuBar {

    private TeacherReportTool trt_;
    private OpenSaveControlView osc_ = null;
    private TrtOpenSaveControlClient trtOpenSaveControlClient_ = null;

    private Boolean insertAtCursorEnabled_ = false;
    private MenuItem openStudentNamesMenuItem_;
    private MenuItem openSentenceTemplatesMenuItem_;
    private MenuItem openBothFilesMenuItem_;
    private MenuItem saveMenuItem_;
    private MenuItem saveAsMenuItem_;
    private MenuItem exit_;

    private MenuItem pasteReportsToClipboard_, selectAll_, copy_, cut_, paste_;

    private Boolean saveFileDefined_ = false;


    public MenuBarView(TeacherReportTool trt) {
        trt_ = trt;
        trtOpenSaveControlClient_ = new TrtOpenSaveControlClient(trt_);
        osc_ = new OpenSaveControlView(trt_, trtOpenSaveControlClient_);

        // --- Menu File
        Menu menuFile = new Menu("_File");

        Menu preferences = new Menu("Preferences");
        CheckMenuItem insertAtCursor = new CheckMenuItem("Insert at cursor");
        insertAtCursor.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                insertAtCursorEnabled_ = newValue;
            }
        });
        preferences.getItems().add(insertAtCursor);


        openStudentNamesMenuItem_ = new MenuItem("Open Student Names ...");
        openStudentNamesMenuItem_.setOnAction(e -> {
            osc_.doOpenNames();
        });

        openSentenceTemplatesMenuItem_ = new MenuItem("Open Sentence Templates ...");
        openSentenceTemplatesMenuItem_.setOnAction(e -> {
            osc_.doOpenSentenceTemplates();
        });

        openBothFilesMenuItem_ = new MenuItem("Open Both Files ...");
        openBothFilesMenuItem_.setOnAction(e -> {
            osc_.doOpen();
        });

        saveMenuItem_ = new MenuItem("Save");
        saveMenuItem_.setOnAction(e -> {
            osc_.doSave();
        });
        saveMenuItem_.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        saveAsMenuItem_ = new MenuItem("Save As ...");
        saveAsMenuItem_.setOnAction(e -> {
            osc_.doSaveAs();
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> trt_.closeProgram());

        menuFile.getItems().addAll(preferences, new SeparatorMenuItem(),
                openStudentNamesMenuItem_, openSentenceTemplatesMenuItem_, openBothFilesMenuItem_, new SeparatorMenuItem(),
                saveMenuItem_, saveAsMenuItem_, new SeparatorMenuItem(),
                exit);

        // --- Menu Edit
        Menu menuEdit = new Menu("_Edit");

        pasteReportsToClipboard_ = new MenuItem("All reports to clipboard");
        pasteReportsToClipboard_.setOnAction(event -> {
            trt_.pasteReportsToClipboard();
        });

        selectAll_ = new MenuItem("Select All");
        selectAll_.setOnAction(event -> {
            trt_.selectAll();
        });
        selectAll_.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

        copy_ = new MenuItem("Copy");
        copy_.setOnAction(event -> {
            trt_.copy();
        });
        copy_.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

        cut_ = new MenuItem("Cut");
        cut_.setOnAction(event -> {
            trt_.cut();
            trt_.reportChanged();
        });
        cut_.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        paste_ = new MenuItem("Paste");
        paste_.setOnAction(event -> {
            trt_.paste();
            trt_.reportChanged();
        });
        paste_.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

        // Edit menu items are not available until student names have been read.
        pasteReportsToClipboard_.setDisable(true);
        selectAll_.setDisable(true);
        copy_.setDisable(true);
        cut_.setDisable(true);
        paste_.setDisable(true);


        menuEdit.getItems().addAll(pasteReportsToClipboard_, selectAll_, copy_, cut_, paste_);

        // --- Menu Help
        Menu menuHelp = new Menu("Help");
        MenuItem help = new MenuItem("Help ...");
        help.setOnAction(e -> (new HelpWindow()).display());
        MenuItem about = new MenuItem("About ...");
        about.setOnAction(e -> (new AboutWindow()).display());
        menuHelp.getItems().addAll(help, about);

        this.getMenus().addAll(menuFile, menuEdit, menuHelp);

        // Save and SaveAs are not available until after OpenDir or
        // OpenSentenceTemplates are done.
        // This could be changed so that save is only available when there are
        // some changes to the editable text.
        saveMenuItem_.setDisable(true);
        saveAsMenuItem_.setDisable(true);

        // Names file must be opened before SentenceTemplates file.
        openSentenceTemplatesMenuItem_.setDisable(true);

    }

    public Boolean getInsertAtCursorEnabled() {
        return insertAtCursorEnabled_;
    }


    // False after writing reports to file because nothing to save until report again changed
    // True after updated report in widget
    public void enableSaveMenuItems(Boolean b) {

        if (b) {
            saveAsMenuItem_.setDisable(false);

            // Save menu item is only enabled if there is a defined save file name
            if (trtOpenSaveControlClient_.hasDefaultSaveAsFilenameBeenWritten())
                saveMenuItem_.setDisable(false);
            else
                saveMenuItem_.setDisable(true);
        } else {
            saveAsMenuItem_.setDisable(true);
            saveMenuItem_.setDisable(true);
        }
    }


    // Enable/Disable File menu bar items
    public void enableOpenStudentNamesMenuItem(Boolean b) {
        openStudentNamesMenuItem_.setDisable(!b);
    }

    public void enableOpenSentenceTemplatesMenuItem(Boolean b) {
        openSentenceTemplatesMenuItem_.setDisable(!b);
    }

    public void enableSaveMenuItem(Boolean b) {
        saveMenuItem_.setDisable(!b);
    }

    public void enableSaveAsMenuItem(Boolean b) {
        saveAsMenuItem_.setDisable(!b);
    }

    public OpenSaveControlView getOpenSaveControl() {
        return osc_;
    }

    public TrtOpenSaveControlClient getTrtOpenSaveControlClient() {
        return trtOpenSaveControlClient_;
    }

    public void enableEditMenuItems() {
        pasteReportsToClipboard_.setDisable(false);
        selectAll_.setDisable(false);
        copy_.setDisable(false);
        cut_.setDisable(false);
        paste_.setDisable(false);
    }

}