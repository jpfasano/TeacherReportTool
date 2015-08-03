import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
          } else if (f == 1) {
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
        else if (f == 1){
          if (!tc.empty())
            templates.add(tc);
          tra.setTemplates(templates);
        }
      }

      catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
    
    /*
    tra.getContentPane().remove(tra.getPanelWithTabs());
    ContentPanel contentPanel = new ContentPanel(tra);
    tra.setPanelWithTabs(contentPanel);
    tra.getContentPane().add(contentPanel);
    tra.invalidate();
    tra.validate();
    */
    ContentPanel cp = new ContentPanel(tra);
    tra.setContentPanel(cp);
    
    

    // update label on GUI to have name of first student
    //studentsIndex=-1;
    tra.advanceToNextStudent(); 
    
    /*
    //Test new GUI Elements
    {
      StudentPanel sp=new StudentPanel(tra);

      TemplateCategory tc1 = tra.getTemplates().get(1);
      TemplatePanel tp=new TemplatePanel(tra,tc1);
      
      TabbedTemplatePanel ttp = new TabbedTemplatePanel(tra);

      EditableTextPanel etp=new EditableTextPanel(tra);
      
      JPanel p=new JPanel(new GridBagLayout());
      
      GridBagConstraints gbc = new GridBagConstraints();
      /*
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 1;
      gbc.weighty = .1;
      gbc.fill = GridBagConstraints.BOTH;
      p.add(sp,gbc);
      

      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.weightx = 1;
      gbc.weighty = .9;
      gbc.fill = GridBagConstraints.BOTH;
      p.add(etp,gbc);
      */
      /*
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 1;
      gbc.weighty = 1;
      gbc.fill = GridBagConstraints.BOTH;
      p.add(ttp,gbc);
      
      
      JFrame jf = new JFrame();
      jf.setContentPane(p);
      jf.setTitle("Test New Main Window");
      jf.setSize(800, 480);
      jf.setVisible(true);
      
     
    }
   */

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
