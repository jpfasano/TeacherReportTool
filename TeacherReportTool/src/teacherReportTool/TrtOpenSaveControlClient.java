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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import openSaveControl.OpenSaveControlClient;

public class TrtOpenSaveControlClient implements OpenSaveControlClient {

	TeacherReportTool trt;

	public TrtOpenSaveControlClient(TeacherReportTool trt) {
		this.trt = trt;
	}

	// Return false if unsuccessful
	public boolean openReadNamesFile(File nf) {
		BufferedReader br = null;

		ArrayList<Student> students = new ArrayList<Student>();
		//String pathSeparator = System.getProperty("file.separator");

		try {

			String fullFileName = nf.getAbsolutePath();

			// Check to make sure the file exists.
			// File file = new File(fullFileName);
			if (!nf.exists() || nf.isDirectory()) {
				JOptionPane.showMessageDialog(trt, "File with student names does not exist: " + fullFileName,
						"File does not exist", JOptionPane.ERROR_MESSAGE);
				return false;
			}

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
					JOptionPane.showMessageDialog(trt,
							"Invalid Gender in " + fullFileName + ". Line is \"" + strLine + "\"\n"
									+ "Line must begin with an M or F followed by a space.",
							"Gender Not Specfied", JOptionPane.ERROR_MESSAGE);
					return false;

				}

				Student s = new Student(parsed[0], parsed[1]);
				students.add(s);

			}

		}

		catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			trt.setStudents(students);
		}

		ContentPanel cp = new ContentPanel(trt);
		trt.setContentPanel(cp);

		trt.getTraMenuBar().setSaveFileDefined(false);
		trt.getTraMenuBar().enableSaveMenuItems(false);
		return true;

	}

	public boolean openReadSentenceTemplatesFile(File nf) {
		boolean retVal = true;
		BufferedReader br = null;

		Templates templates = new Templates();
		TemplateCategory tc = new TemplateCategory();
		// String pathSeparator = System.getProperty("file.separator");

		// Loop once to read each data file in the directory
		// for (int f = 0; f < fileNames.length; f++) {
		try {

			String fullFileName = nf.getAbsolutePath();

			// Check to make sure the file exists.
			// File file = new File(fullFileName);
			if (!nf.exists() || nf.isDirectory()) {
				JOptionPane.showMessageDialog(trt, "File does not exist: " + fullFileName,
						"Sentence Template File Does Not Exist", JOptionPane.ERROR_MESSAGE);
				return false;
			}

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

		}

		catch (Exception e) {
			System.err.println(e.getMessage());
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
		// }

		ContentPanel cp = new ContentPanel(trt, trt.getContentPanel());
		trt.setContentPanel(cp);

		return retVal;
	}

	private boolean isComment(String strLine) {
		// check for a comment or empty line
		if (strLine.length() == 0)
			return true;
		if (strLine.substring(0, 1).equals("#"))
			return true;
		return false;
	}

	// Open Both Files.
	public void openReadFile(File dirFile) {
		// Read 2 input data files

		//String[] fileNames = { "names.trt", "sentenceTemplates.trt" };
		//String pathSeparator = System.getProperty("file.separator");

		String nameFullFileName = dirFile.getAbsolutePath();
		File namesFile = new File(nameFullFileName);
		boolean success = openReadNamesFile(namesFile);

		if (success) {
			String templatesFullFileName = nameFullFileName.substring(0,nameFullFileName.length()-3)+"trs";
			File templatesFile = new File(templatesFullFileName);
			openReadSentenceTemplatesFile(templatesFile);
			
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

			// fileStream.println(reportAsString);
			// FileWriter out = new FileWriter(f);
			// out = new BufferedWriter(out);
			// out.write(reportAsString);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (fileStream != null) {
				try {
					fileStream.close();
					EditableTextPanel.textSaved();
					trt.getTraMenuBar().setSaveFileDefined(true);
					trt.getTraMenuBar().enableSaveMenuItems(false);
				} catch (Exception e) {
					System.err.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

	}
	
	// Return true if there is unsaved work that needs to be saved.
	public boolean unsavedWork() {
		boolean retVal = false;
		if (EditableTextPanel.unsavedChanges()) {
			// There is unsaved work, so ask if it is ok to discard it
//			int result = JOptionPane.showConfirmDialog(trt, "The student report is unsaved. Discard unsaved work?",
//					"Unsaved Report Text", JOptionPane.YES_NO_CANCEL_OPTION);
//			if (result != JOptionPane.YES_OPTION)
//				retVal = true;
			
			
			Object[] options = {"Yes","No","Cancel"};
			int result = JOptionPane.showOptionDialog(trt, "The student report is unsaved. Discard unsaved work?",
					"Unsaved Report",
					JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE, 
	                  null, options, options[1]);
			if (result != 0)  // If Yes was not chosen
				retVal = true;
			
			
		}
		return retVal;
	}

	public void enableOpenNamesMenuItem(Boolean b) {
		trt.getTraMenuBar().enableOpenNamesMenuItem(b);
	}

	public void enableOpenSentenceTemplatesMenuItem(Boolean b) {
		trt.getTraMenuBar().enableOpenSentenceTemplatesMenuItem(b);
	}

	// Enable/Disable File menu bar items
	public void enableSaveMenuItem(Boolean b) {
		trt.getTraMenuBar().enableSaveMenuItem(b);
	}

	public void enableSaveAsMenuItem(Boolean b) {
		trt.getTraMenuBar().enableSaveAsMenuItem(b);
	}
	

	public void enableSaveMenuItems(Boolean b) {
		trt.getTraMenuBar().enableSaveMenuItems(b);
	}

}