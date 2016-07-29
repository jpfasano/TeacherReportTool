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

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JP on 6/26/2016.
 */
public class TeacherReportTool extends Application {


    private Stage stage_;
    private MenuBarView menuBar_;
    private TemplatesView templatesFx_;
    private ReportView reportView_;

    private ArrayList<Student> students_;
    private int studentsIndex_ = -1;
    private Map<String, GenderWordPair> genderWordsDict_;

    private Templates templates_;

    private StudentPanelView studentPanelFx_;

    private BorderPane sp1_;
    private boolean unsavedWork_ = false;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        this.stage_ = stage;
        setWindowTitle(false);

        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        BorderPane topLevel = new BorderPane();
        Scene scene = new Scene(topLevel, 600, 350);
        scene.setFill(Color.OLDLACE);

        menuBar_ = new MenuBarView(this);


        BorderPane splitWidget = new BorderPane();


        studentPanelFx_ = new StudentPanelView(this);


        SplitPane sp = new SplitPane();
        sp.setOrientation(Orientation.VERTICAL);
        sp1_ = new BorderPane();

        //sp1_.getChildren().add(new Label("Button One"));
        //sp1_.setCenter(new Label("TabbedPane Area"));
        Button apply = new Button("Apply");
        apply.setOnAction(e -> {
            applySelectedTemplates();
        });
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(apply);
        sp1_.setBottom(hb);


        reportView_ = new ReportView(this);
        reportView_.setWrapText(true);

        sp.getItems().addAll(sp1_, reportView_);
        sp.setDividerPositions(0.6f);


        //splitWidget.getChildren().setAll(studentPanelFx_,sp);
        splitWidget.setTop(studentPanelFx_);
        splitWidget.setCenter(sp);


        //topLevel.getChildren().addAll(menuBar_, vbox);
        topLevel.setTop(menuBar_);
        topLevel.setCenter(splitWidget);
        //((Vbox) scene.getRoot()).getChildren().addAll(menuBar_, vbox);
        stage.setScene(scene);
        stage.show();

        readGenderWordPairs();
    }

    public void closeProgram() {
        if (!isThereUnsavedWork()) {
            // there is not unsaved work, so OK to close
            stage_.close();
        } else if (getMenuBar().getOpenSaveControl().okDiscardUnsavedWork()) {
            // There is unsaved work, but user said ok to discard
            stage_.close();
        }
    }

    public Stage getStage() {
        return stage_;
    }


    public MenuBarView getMenuBar() {
        return menuBar_;
    }

    private void readGenderWordPairs() {

        // Read resource file with gender word pair substitutions
        genderWordsDict_ = new HashMap<String, GenderWordPair>();
        try {

            InputStream fstream = Utilities.getResourceInputStream("resources/genderWordPairs.trt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();

                // check for a comment or empty line
                if (strLine.length() == 0)
                    continue;
                if (strLine.substring(0, 1).equals("#"))
                    continue;
                // Split line into male and female gender words
                String[] parsed = strLine.split(" ", 2);

                GenderWordPair gwp = new GenderWordPair(parsed[0], parsed[1]);
                genderWordsDict_.put(parsed[0], gwp);
                genderWordsDict_.put(parsed[1], gwp);
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

//        String[] male = {"his", "he", "him", "son", "man","boy","himself"};
//        String[] female = {"her", "she", "her", "daughter", "woman","girl","herself"};
//        genderWordsDict_ = new HashMap<String, GenderWordPair>();
//        for (int i = 0; i < male.length; i++) {
//            GenderWordPair gwp = new GenderWordPair(male[i], female[i]);
//            genderWordsDict_.put(male[i], gwp);
//            genderWordsDict_.put(female[i], gwp);
//        }

    }

    public void setStudents(ArrayList<Student> students) {
        // First time
        if (this.students_ == null) {
            students_ = students;
            studentsIndex_ = Math.min(0, students_.size() - 1);
        } else {
            // Read a second names file (unusual).  May be shouldn't do this and just position to beginning
            this.students_ = students;
            if (studentsIndex_ >= students_.size())
                studentsIndex_ = Math.min(0, students_.size() - 1);
        }
        updateStudentNameLabel();
    }

    public ArrayList<Student> getStudents() {
        return students_;
    }


    // Get reports for all students
    public String getReports() {
        // Make sure current editable text is copied to student
        updateStudentReportFromEditableText();

        String retVal = "";
        for (Student s : getStudents()) {
            String sReport = s.getReport().trim();
            if (sReport.length() == 0)
                continue;
            retVal += "\n\n" + s.getName();
            retVal += "\n" + s.getReport();
        }
        return retVal;
    }


    public String getStudentName() {
        return students_.get(studentsIndex_).getName();
    }

    public String getStudentGender() {
        return students_.get(studentsIndex_).getGender();
    }

    public String getStudentReport() {
        return students_.get(studentsIndex_).getReport();
    }

    public void setStudentReport(String r) {
        students_.get(studentsIndex_).setReport(r);
    }

    public Templates getTemplateCategories() {
        return templates_;
    }

    public void setTemplates(Templates templates) {
        // Get current template tab
        int saveTabNum = 0;
        if (templatesFx_ != null)
            saveTabNum = templatesFx_.getSelectedTab();

        this.templates_ = templates;

        // update display widget
        templatesFx_ = new TemplatesView(this, templates);
        templatesFx_.selectTab(saveTabNum);
        sp1_.setCenter(templatesFx_);
    }

    public Map<String, GenderWordPair> getGenderWordsDict() {
        return genderWordsDict_;
    }

    public void setGenderWordsDict(Map<String, GenderWordPair> genderWordsDict) {
        this.genderWordsDict_ = genderWordsDict;
    }

    public void advanceToNextStudent() {

        if (students_ == null) return;
        // copy checked templates to editable text

        if(templatesFx_!=null) {
            applySelectedTemplates();
            templatesFx_.selectTab(0);
        }

        updateStudentReportFromEditableText();

        studentsIndex_++;
        if (studentsIndex_ >= students_.size())
            studentsIndex_ = students_.size() - 1;
        updateStudentNameLabel();


        // Set focus to first tab
        if (templatesFx_ != null)
            templatesFx_.selectTab(0);

        // Set editable text to text for student
        updateEditableTextFromStudentReport();
    }

    public void backToPriorStudent() {

        if (students_ == null) return;
        applySelectedTemplates();
        templatesFx_.selectTab(0);

        updateStudentReportFromEditableText();

        studentsIndex_--;
        if (studentsIndex_ < 0)
            studentsIndex_ = 0;
        if (students_.size() == 0)
            studentsIndex_ = -1;
        updateStudentNameLabel();

        // Set focus to first tab
        templatesFx_.selectTab(0);

        // Set editable text to text for student
        updateEditableTextFromStudentReport();
    }

    private void updateStudentNameLabel() {
        if (studentsIndex_ < 0)
            return;
        if (studentPanelFx_ == null)
            return;
        String suffix = " " + (studentsIndex_ + 1) + " of " + students_.size();
        studentPanelFx_.updateStudentNameLabel(students_.get(studentsIndex_), suffix);
    }

    public void applySelectedTemplates() {
        if (templates_ == null) return;
        if (templatesFx_ == null) return;
        String templates = templates_.getSentenceTemplates(templatesFx_.getPickedSentenceTemplates());
        templates = substituteWords(templates);
        if (menuBar_.getInsertAtCursorEnabled())
            reportView_.insertText(templates);
        else
            reportView_.appendText(templates);
        //System.out.println(templates);
    }

    // Replace place holders in sentence template with correct words.
    private String substituteWords(String template) {
        template = substituteName(template);
        template = substituteGenderSpecificWords(template);
        return template;
    }

    // In sentence replace all occurrences of _Name with student Name.
    private String substituteName(String sentence) {
        String studentName = getStudentName();
        String[] segments = sentence.split("_NAME");
        String retVal = segments[0];

        for (int i = 1; i < segments.length; i++) {
            String seg = segments[i];

            // Is last character of studentName an s?
            if (studentName.substring(studentName.length() - 1).equals("s")) {
                // Does the segment begin with 's?
                if (seg.substring(0, 2).equals("'s")) {
                    // Need to change the 's to just '
                    seg = "'" + seg.substring(2);
                }
            }
            retVal += (studentName + seg);
        }

        return retVal;
    }

    // Substitute gender specfic words
    private String substituteGenderSpecificWords(String sentence) {
        String retVal = sentence;
        // Replace gender specific pronouns.
        String sg = this.getStudentGender();
        for (Map.Entry<String, GenderWordPair> entry : genderWordsDict_.entrySet()) {
            String needle = "_" + entry.getKey().toUpperCase().trim();
            String replacement = entry.getValue().getGenderWord(sg).trim();

            // Create upper case first character replacement
            char[] r1 = replacement.toCharArray();
            r1[0] = Character.toUpperCase(r1[0]);
            String replacementUC = new String(r1);

            // If needle is at beginning of sentence then replacement must start
            // with an uppercase character
            if (retVal.indexOf(needle) == 0) {
                retVal = replacementUC + retVal.substring(needle.length());
            }

            // Look for the needle at the beginning of a sentence.
            String[] endSent = {".", "?", "!"};
            for (String es : endSent) {
                String needle1 = es + " " + needle;
                retVal = retVal.replace(needle1, es + " " + replacementUC);
                needle1 = es + "  " + needle;
                retVal = retVal.replace(needle1, es + "  " + replacementUC);
            }
            retVal = retVal.replace(needle, replacement);
        }

        return retVal;
    }


    public void updateStudentReportFromEditableText() {
        if (students_.size() == 0)
            return;
        if (studentsIndex_ == -1)
            return;
        if (reportView_ == null)
            return;
        String r = reportView_.getReport();
        setStudentReport(r);
    }

    public void updateEditableTextFromStudentReport() {
        String sr;
        if (students_.size() == 0)
            sr = "";
        else if (studentsIndex_ == -1)
            sr = "";
        else
            sr = students_.get(studentsIndex_).getReport();
        if (reportView_ != null)
            reportView_.setReport(sr);
    }

    // Perform all actions required when the report has been updated
    public void reportChanged() {
        unsavedWork_ = true;
        menuBar_.enableSaveMenuItems(true);
        setWindowTitle(true);
    }

    public void reportSaved() {
        unsavedWork_ = false;
        menuBar_.enableSaveMenuItems(false);
        setWindowTitle(false);
    }

    // Names file just read
    public void namesFileOpened() {
        unsavedWork_ = false;
        // save and saveAs unavailable
        menuBar_.enableSaveMenuItems(false);
        // Window Title without Star
        setWindowTitle(false);
        // Reset Template Widget to first tab
        if (templatesFx_ != null) templatesFx_.selectTab(0);
        // Clear reports widget
        if (reportView_ != null) reportView_.setReport("");
        // Enable Edit menu items
        menuBar_.enableEditMenuItems();
    }

    private void setWindowTitle(boolean withStar) {
        String title = "Teacher Report Tool";
        if (withStar) title += "*";
        stage_.setTitle(title);
    }

    public boolean isThereUnsavedWork() {
        return unsavedWork_;
    }

    public void selectAll() {
        if ( reportView_==null) return;
        reportView_.selectAll();
    }

    public void copy() {
        if ( reportView_==null) return;
        reportView_.copy();
    }

    public void cut() {
        if ( reportView_==null) return;
        reportView_.cut();
    }

    public void paste() {
        if ( reportView_==null) return;
        reportView_.paste();
    }

    public void pasteReportsToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        String reports = getReports();
        content.putString(reports);
        clipboard.setContent(content);
    }



}
