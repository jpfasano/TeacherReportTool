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
import openSaveControl.OpenSaveControlClient;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by JP on 6/26/2016.
 */
public class TrtOpenSaveControlClient implements OpenSaveControlClient {

    private TeacherReportTool trt;
    private boolean defaultSaveAsFilenameHasBeenWritten_;


    public TrtOpenSaveControlClient(TeacherReportTool trt) {
        this.trt = trt;
    }

    // Return false if unsuccessful
    public boolean openReadNamesFile(File nf) {
        BufferedReader br = null;

        ArrayList<Student> students = new ArrayList<Student>();
        // String pathSeparator = System.getProperty("file.separator");

        try {

            String fullFileName = nf.getAbsolutePath();

            FileInputStream fstream = new FileInputStream(nf);

            br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();

                // check for a comment
                if (isComment(strLine))
                    continue;

                // Split line into gender and name.
                String[] parsed = strLine.split(" ", 2);

                // Make sure Gender is uppercase
                parsed[0] = parsed[0].toUpperCase();

                if (!parsed[0].equals("M") && !parsed[0].equals("F")) {
                    String errorMsg = "Invalid Gender in " + fullFileName + ". Line is \"" + strLine + "\"\n"
                            + "Line must begin with an M or F followed by a space. "
                            + "Gender Not Specified";
                    Alert errorMessageDialog = new Alert(Alert.AlertType.ERROR, errorMsg);
                    errorMessageDialog.showAndWait();
                    return false;

                }

                Student s = new Student(parsed[0], parsed[1].trim());
                students.add(s);

            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                }
            trt.setStudents(students);
        }

        trt.namesFileOpened();

        return true;

    }

    public boolean openReadSentenceTemplatesFile(File nf) {
        boolean retVal = true;
        BufferedReader br = null;

        Templates templates = new Templates();
        TemplateCategory tc = new TemplateCategory();

        try {

            String fullFileName = nf.getAbsolutePath();

            FileInputStream fstream = new FileInputStream(nf);

            br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();

                // check for a comment
                if (isComment(strLine))
                    continue;

                // Does strLine identifies a new category?
                if (strLine.substring(0, 1).equals("\"")) {

                    // strLine identifies a new category
                    if (!tc.empty())
                        templates.add(tc);
                    tc = new TemplateCategory(strLine.substring(1));
                } else {
                    // strLine is another template line
                    tc.addTemplate(strLine);
                }

            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    retVal = false;
                }
            if (!tc.empty())
                templates.add(tc);

            trt.setTemplates(templates);
        }


        return retVal;
    }

    private boolean isComment(String strLine) {
        // check for a comment or empty line
        if (strLine.length() == 0)
            return true;
        return strLine.substring(0, 1).equals("#");
    }

    // Open Both Files.
    public void openReadFile(File dirFile) {
        // Read 2 input data files

        // String[] fileNames = { "names.trt", "sentenceTemplates.trt" };
        // String pathSeparator = System.getProperty("file.separator");

        String nameFullFileName = dirFile.getAbsolutePath();
        File namesFile = new File(nameFullFileName);
        boolean success = openReadNamesFile(namesFile);

        if (success) {
            String templatesFullFileName = nameFullFileName.substring(0, nameFullFileName.length() - 3) + "trs";
            File templatesFile = new File(templatesFullFileName);
            success = openReadSentenceTemplatesFile(templatesFile);
        }
        if (success) {
            this.enableOpenSentenceTemplatesMenuItem(true);
        }

    }

    public void writeFile(File f) {

        // BufferedWriter out = null;
        PrintStream fileStream = null;
        try {
            fileStream = new PrintStream(f);
            String reportAsString = trt.getReports();
            String[] split = reportAsString.split("\n");
            for (String s : split) {
                //System.out.println(s);
                fileStream.println(s);
            }


        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                    defaultSaveAsFilenameHasBeenWritten_ = true;
                    trt.reportSaved();
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    }


    public void enableOpenSentenceTemplatesMenuItem(Boolean b) {
        trt.getMenuBar().enableOpenSentenceTemplatesMenuItem(b);
    }

    public void enableOpenNamesMenuItem(Boolean b) {
        //trt.getTraMenuBar().enableOpenNamesMenuItem(b);
        trt.getMenuBar().enableOpenStudentNamesMenuItem(b);
    }


    // Enable/Disable File menu bar items
    public void enableSaveMenuItem(Boolean b) {
        //trt.getTraMenuBar().enableSaveMenuItem(b);
        trt.getMenuBar().enableSaveMenuItem(b);
    }

    public void enableSaveAsMenuItem(Boolean b) {
        //trt.getTraMenuBar().enableSaveAsMenuItem(b);
        trt.getMenuBar().enableSaveAsMenuItem(b);
    }

    public void enableSaveMenuItems(Boolean b) {
        // trt.getTraMenuBar().enableSaveMenuItems(b);
        trt.getMenuBar().enableSaveMenuItems(b);
    }


    public boolean hasDefaultSaveAsFilenameBeenWritten() {
        return defaultSaveAsFilenameHasBeenWritten_;
    }


    public void defaultSaveAsFilenameHasNotBeenWritten() {
        defaultSaveAsFilenameHasBeenWritten_ = false;
    }

}
