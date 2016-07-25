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

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import openSaveControl.OpenSaveControlClient;

import java.io.File;
import java.util.Optional;

//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by JP on 6/26/2016.
 */
public class OpenSaveControlView {

    TeacherReportTool trt_;
    private String defaultSaveAsFilename_ = "";

    private String defaultOpenNamesDirectory_ = "";
    private String defaultOpenSentenceTemplatesDirectory_ = "";
    private String defaultOpenDirDirectory_ = "";

    private FileChooser saveAsFileChooser_ = null;

    private FileChooser openNamesChooser_ = null;
    private FileChooser openSentenceTemplatesChooser_ = null;
    private FileChooser openDirChooser_ = null;
    private OpenSaveControlClient oscClient_ = null;


    public OpenSaveControlView(TeacherReportTool trt, OpenSaveControlClient orf) {
        trt_ = trt;
        oscClient_ = orf;
        gutsOfConstrutor();
    }


    private void gutsOfConstrutor() {
        defaultOpenNamesDirectory_ = System.getProperty("user.dir");
        defaultOpenSentenceTemplatesDirectory_ = defaultOpenNamesDirectory_;
        defaultOpenDirDirectory_ = defaultOpenNamesDirectory_;

        // Setup Open Names Dialog
        {
            openNamesChooser_ = new FileChooser();
            // Setup file filters
            openNamesChooser_.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Student names file (*.trn)", "*.trn"),
                    new FileChooser.ExtensionFilter("*.*", "*.*"));
            openNamesChooser_.setTitle("Open Student Names");
        }

        // Setup Sentence Templates Directory Dialog
        {
            openSentenceTemplatesChooser_ = new FileChooser();
            // Setup file filters
            openSentenceTemplatesChooser_.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Sentence templates file (*.trn)", "*.trs"),
                    new FileChooser.ExtensionFilter("*.*", "*.*"));
            openSentenceTemplatesChooser_.setTitle("Open Sentence Templates");
        }
        // Setup Open Directory Dialog (directory containing input data files)
        {
            openDirChooser_ = new FileChooser();
            // Setup file filters
            openDirChooser_.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Student names file (*.trn)", "*.trn"),
                    new FileChooser.ExtensionFilter("*.*", "*.*"));
            openDirChooser_.setTitle("Open both names and templates. Select names.");
            // FileNameExtensionFilter trsFilter = new
            // FileNameExtensionFilter("Sentence templates file (*.trs)",
            // "trs");
            // openDirChooser_.addChoosableFileFilter(trnFilter);
            // openDirChooser_.addChoosableFileFilter(trsFilter);
            // openDirChooser_.setAcceptAllFileFilterUsed(false);
            // openDirChooser_.setFileFilter(trnFilter);
        }

        // Setup SaveAs Dialog
        {
            saveAsFileChooser_ = new FileChooser();

            //saveAsFileChooser_.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //saveAsFileChooser_.setApproveButtonText("Save");

            // Setup file filters
            saveAsFileChooser_.getExtensionFilters().addAll(
                    //new FileChooser.ExtensionFilter("txt files (*.txt)", "txt"),
                    new FileChooser.ExtensionFilter("*.*", "*.*"));
            saveAsFileChooser_.setTitle("Save Reports");
        }

        // Until a file is opened then there is nothing to save
        // oscClient_.enableSaveMenuItem(false);
        // oscClient_.enableSaveAsMenuItem(false);

    }

    // Return true if there is unsaved work that needs to be saved.
    //public boolean unsavedWork() {
    //    return trt_.unsavedWork();
    //}

    public void doOpenNames() {

        if (!trt_.isThereUnsavedWork() || okDiscardUnsavedWork()) {

            File initDir = new File(defaultOpenDirDirectory_);
            openNamesChooser_.setInitialDirectory(initDir);

            File file = openNamesChooser_.showOpenDialog(trt_.getStage());
            if (file == null) return;
            defaultOpenSentenceTemplatesDirectory_ = file.getParent();
            defaultOpenNamesDirectory_ = file.getAbsolutePath();

            defaultOpenDirDirectory_ = file.getParent();
            String temp = file.getName();
            String tempWExt = temp.substring(0, temp.length() - 3) + "txt";
            if (defaultSaveAsFilename_.equals(tempWExt)) {
                defaultSaveAsFilename_ = tempWExt;
                oscClient_.defaultSaveAsFilenameHasNotBeenWritten();
            }

            boolean success = oscClient_.openReadNamesFile(file);

            if (success) {
                oscClient_.enableOpenSentenceTemplatesMenuItem(true);
                // oscClient_.enableSaveAsMenuItem(true);
            }
        }

    }

    public void doOpenSentenceTemplates() {

        File dir = new File(defaultOpenSentenceTemplatesDirectory_);
        // openSentenceTemplatesChooser_.setSelectedFile(file);
        openSentenceTemplatesChooser_.setInitialDirectory(dir);

        //int result = openSentenceTemplatesChooser_.showOpenDialog(frame);
        //if (result != JFileChooser.APPROVE_OPTION)
        //    return;
        //file = openSentenceTemplatesChooser_.getSelectedFile();

        File file = openSentenceTemplatesChooser_.showOpenDialog(trt_.getStage());
        if (file == null) return;


        defaultOpenSentenceTemplatesDirectory_ = file.getParent();

        oscClient_.openReadSentenceTemplatesFile(file);

        // oscClient_.enableSaveMenuItems(true);

    }


    public boolean okDiscardUnsavedWork() {
        boolean retVal = false;

        Alert errorMessageDialog = new Alert(Alert.AlertType.CONFIRMATION,
                "The student report is unsaved. Discard unsaved work?");
        Optional<ButtonType> result = errorMessageDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
            retVal = true;

        return retVal;
    }

    // Do Open Both
    public void doOpen() {
        if (!trt_.isThereUnsavedWork() || okDiscardUnsavedWork()) {

            File dir = new File(defaultOpenDirDirectory_);
            // openDirChooser_.setSelectedFile(dir);
            openDirChooser_.setInitialDirectory(dir);

            //int result = openDirChooser_.showOpenDialog(frame);
            //if (result != JFileChooser.APPROVE_OPTION)
            //    return;
            //dir = openDirChooser_.getSelectedFile();
            File file = openDirChooser_.showOpenDialog(trt_.getStage());
            if (file == null) return;

            //defaultOpenDirDirectory_ = dir.getAbsolutePath();
            defaultOpenDirDirectory_ = file.getParent();
            String temp = file.getName();
            defaultSaveAsFilename_ = temp.substring(0, temp.length() - 3) + "txt";

            oscClient_.openReadFile(file);
            defaultOpenSentenceTemplatesDirectory_ = defaultOpenDirDirectory_;

            // oscClient_.enableSaveMenuItems(true);
        }

    }


    public void doSaveAs() {

        File file = new File(defaultOpenDirDirectory_);
        saveAsFileChooser_.setInitialFileName(defaultSaveAsFilename_);
        saveAsFileChooser_.setInitialDirectory(file);

        File saveAsFile = saveAsFileChooser_.showSaveDialog(trt_.getStage());
        if (saveAsFile == null) return;

        defaultSaveAsFilename_ = saveAsFile.getName();
        defaultOpenDirDirectory_ = saveAsFile.getParent();

        // File either doesn't exist or it is ok to overwrite;
        oscClient_.writeFile(saveAsFile);

    }

    public void doSave() {

        String pathSeparator = System.getProperty("file.separator");
        File file = new File(defaultOpenDirDirectory_ + pathSeparator + defaultSaveAsFilename_);

        oscClient_.writeFile(file);
    }

    public boolean isSaveFileNameDefined() {
        return defaultSaveAsFilename_.length() != 0;
    }

}
