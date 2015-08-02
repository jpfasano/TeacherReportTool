import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import openSaveControl.OpenSaveControlClient;

public class TraOpenSaveControlClient implements OpenSaveControlClient {

  TeacherReportAssistant tra;

  public TraOpenSaveControlClient(TeacherReportAssistant tra) {
    this.tra = tra;
  }

  public void openReadFile(File dirFile) {
    // Read 3 input data files

    //String[] fileNames = { "names.tra", "genderWordPairs.tra", "sentenceTemplates.tra" };

    String[] fileNames = { "names.tra",  "sentenceTemplates.tra" };
    ArrayList<Student> students = new ArrayList<Student>();
    Map<String, GenderWordPair> genderWordsDict = new HashMap<String, GenderWordPair>();
    ArrayList<TemplateCategory> templates = new ArrayList<TemplateCategory>();
    TemplateCategory tc = new TemplateCategory();
    String pathSeparator = System.getProperty("file.separator"); 

    // Loop once to read each data file in the directory
    for (int f = 0; f < fileNames.length; f++) {
      try {

        String fullFileName = dirFile.getAbsolutePath() + pathSeparator + fileNames[f];
        FileInputStream fstream = new FileInputStream(fullFileName);
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

          if (f == 0) {

            // Split line into gender and name.
            String[] parsed = strLine.split(" ", 2);

            // Make sure Gender is uppercase
            parsed[0] = parsed[0].toUpperCase();

            if (!parsed[0].equals("M") && !parsed[0].equals("F")) {
              throw new Exception("Invalid Gender in " + fullFileName
                  + ". Line is \"" + strLine + "\"");
            }

            Student s = new Student(parsed[0], parsed[1]);
            students.add(s);
          } else if (f == 1) {/*
            // Split line into male and female gender words
            String[] parsed = strLine.split(" ", 2);

            GenderWordPair gwp = new GenderWordPair(parsed[0], parsed[1]);
            genderWordsDict.put(parsed[0], gwp);
            genderWordsDict.put(parsed[1], gwp);

          } else if (f == 2) {*/
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
        br.close();
        if (f == 0)
          tra.setStudents(students);
        else if (f == 1) /*
          tra.setGenderWordsDict(genderWordsDict);
        else if (f == 2) */{
          if (!tc.empty())
            templates.add(tc);
          tra.setTemplates(templates);
        }
      }

      catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
    
    
    tra.getContentPane().remove(tra.getPanelWithTabs());
    ContentPanel contentPanel = new ContentPanel(tra);
    tra.setPanelWithTabs(contentPanel);
    tra.getContentPane().add(contentPanel);
    tra.invalidate();
    tra.validate();
    
    // update label on GUI to have name of first student
    //studentsIndex=-1;
    tra.advanceToNextStudent(); 

  }
  
  public void writeFile(File f) {

    //BufferedWriter out = null;
    PrintStream fileStream = null;
    try  
    {
      fileStream = new PrintStream(f);
      String reportAsString = tra.getReports();
      String [] split = reportAsString.split("\n");
      for (String s:split) {
        System.out.println(s);
        fileStream.println(s);
      }

      
      //fileStream.println(reportAsString);
       // FileWriter out = new FileWriter(f); 
       // out = new BufferedWriter(out);
        //out.write(reportAsString);
    }
    catch (IOException e)
    {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
    finally
    {
        if(fileStream != null) {
            try {
              fileStream.close();
            }
            catch (Exception e) {
              System.err.println("Error: " + e.getMessage());
              e.printStackTrace();
            }
        }
    }
    
  }
  
  // Enable/Disable File menu bar items
  public void enableSaveMenuItem(Boolean b) {
    tra.getTraMenuBar().enableSaveMenuItem(b);
  }
  public void enableSaveAsMenuItem(Boolean b)  {
    tra.getTraMenuBar().enableSaveAsMenuItem(b);
  }
  
}
