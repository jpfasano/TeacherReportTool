/**
 * This is the main class for the TeacherReportAssistant game.
 *  See Help for the rules.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class TeacherReportAssistant extends JFrame
{
  private MenuBar menuBar;
  private ArrayList<Student> students;
  private int studentsIndex;
  private Map<String, GenderWordPair> genderWordsDict;
  private ArrayList<TemplateCategory> templates;
  //private JLabel studentNameLabel;
  //private JTabbedPane tempatePane;
  private ContentPanel contentPanel;
 

  public TeacherReportAssistant()
  {
    super("TeacherReportAssistant");
    
    templates = new ArrayList<TemplateCategory>();
    students = new ArrayList<Student>();

    menuBar = new MenuBar(this);
    setJMenuBar(menuBar);
       
    
    contentPanel = new ContentPanel(this);
    getContentPane().add(contentPanel);

  }

  public void open()
  {
    //controlPanel.open(this);
    
    {
      // Dispaly dialog to get directory containing data files
      String initialPath_ = System.getProperty("user.dir") + "/";
      JFileChooser dirChooser = new JFileChooser(initialPath_);
      dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int result = dirChooser.showOpenDialog(this);
      if (result == JFileChooser.CANCEL_OPTION)
        return;
      File dir = dirChooser.getSelectedFile();
      initialPath_ = dir.getAbsolutePath();

      // Loop once to read each data file in the directory
      String[] fileNames = {"names.tra", "genderWordPairs.tra","sentenceTemplates.tra"};
      ArrayList<Student> students = new ArrayList<Student>();
      Map<String, GenderWordPair> genderWordsDict = new HashMap<String,GenderWordPair>();
      ArrayList<TemplateCategory> templates = new ArrayList<TemplateCategory>();
      TemplateCategory tc= new TemplateCategory();

      for (int f = 0; f < fileNames.length; f++)
      {
        try
        {
          String fullFileName = dir + "\\" + fileNames[f];
          FileInputStream fstream = new FileInputStream(fullFileName);
          BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

          String strLine;

          // Read File Line By Line
          while ((strLine = br.readLine()) != null)
          {
            strLine=strLine.trim();
                     
            // check for a comment or empty line
            if (strLine.length()==0) continue;
            if (strLine.substring(0,1).equals("#")) continue;

            if (f == 0)
            {

              // Split line into gender and name.
              String[] parsed = strLine.split(" ", 2);

              // Make sure Gender is uppercase
              parsed[0] = parsed[0].toUpperCase();

              if (!parsed[0].equals("M") && !parsed[0].equals("F"))
              {
                throw new Exception("Invalid Gender in " + fullFileName
                    + ". Line is \"" + strLine + "\"");
              }

              Student s = new Student(parsed[0], parsed[1]);
              students.add(s);
            }
            else if (f == 1)
            {
              // Split line into male and female gender words
              String[] parsed = strLine.split(" ", 2);
              
              GenderWordPair gwp = new GenderWordPair(parsed[0],parsed[1]);
              genderWordsDict.put(parsed[0],gwp);
              genderWordsDict.put(parsed[1],gwp);
         
            }
            else if (f==2)
            {             
              // Does strLine identifies a new category?
              if (strLine.substring(0,1).equals("\"")) {
                
                // strLine identifies a new category
                if (!tc.empty())
                  templates.add(tc);    
                  tc= new TemplateCategory(strLine.substring(1));
              }
              else {
                // strLine is another template line
                tc.addTemplate(strLine);
              }
            }

          }
          br.close();
          if (f == 0)
            setStudents(students);
          else if (f==1)
            setGenderWordsDict(genderWordsDict);
          else if (f==2) {
            if (!tc.empty()) templates.add(tc);    
            setTemplates(templates);
          }
        }

        catch (Exception e)
        {
          System.err.println(e.getMessage());
        }
      }

    }
 

    
    getContentPane().remove(contentPanel);
    contentPanel = new ContentPanel(this);
    getContentPane().add(contentPanel);
    invalidate();
    validate();
    
    // update label on GUI to have name of first student
    studentsIndex=-1;
    advanceToNextStudent();   
  }
  
  public void advanceToNextStudent()
  {
    updateStudentReportFromEditableText();
    studentsIndex++;
    if (studentsIndex>=students.size()) studentsIndex=students.size()-1;
    updateStudentNameLabel();
    contentPanel.setFocusToFirstTab();
    contentPanel.clearEditableText();
  }

  public void backToPriorStudent()
  {
    updateStudentReportFromEditableText();
    studentsIndex--;
    if (studentsIndex<0)studentsIndex=0;
    if (students.size()==0) studentsIndex=-1;
    updateStudentNameLabel();
    contentPanel.setFocusToFirstTab();
    contentPanel.clearEditableText();
  }
  
  public void updateStudentReportFromEditableText()
  {
    if (students.size()==0) return;
    if (studentsIndex==-1) return;
    String r=contentPanel.getEditableTest();
    students.get(studentsIndex).setReport(r);
  }
  
  public String getReports()
  {
    String retVal="";
    for (Student s : getStudents()) {
      String sReport=s.getReport().trim();
      if (sReport.length()==0) continue;
      retVal+="\\n\\n"+s.getName();
      retVal+="\\n"+s.getReport();
    }
    return retVal;
  }
  
  private void updateStudentNameLabel()
  {
    if(studentsIndex<0)return;
    contentPanel.updateStudentNameLabel(students.get(studentsIndex));
    /*
    String sName=students.get(studentsIndex).getName();
    String sGender=students.get(studentsIndex).getGender();
    studentNameLabel.setText(sName+" gender:"+sGender);
    */
  }
  
  public String getStudentName()
  {
    return students.get(studentsIndex).getName();
  }

  public String getStudentGender()
  {
    return students.get(studentsIndex).getGender();
  }
  
  
  public String getStudentReport()
  {
    return students.get(studentsIndex).getReport();
  }

  public void setStudentReport(String r)
  {
    students.get(studentsIndex).setReport(r);
  }


 
  public ArrayList<TemplateCategory> getTemplates()
  {
    return templates;
  }

  public void setTemplates(ArrayList<TemplateCategory> templates)
  {
    this.templates = templates;
  }

  public Map<String, GenderWordPair> getGenderWordsDict()
  {
    return genderWordsDict;
  }

  public void setGenderWordsDict(Map<String, GenderWordPair> genderWordsDict)
  {
    this.genderWordsDict = genderWordsDict;
  }

  public void setStudents(ArrayList<Student> students)
  {
    this.students = students;
  }

  public ArrayList<Student> getStudents()
  {
    return students;
  }
  
  

  public ContentPanel getContentPanel()
  {
    return contentPanel;
  }

  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ex)
    {
    }

    TeacherReportAssistant window = new TeacherReportAssistant();
    //window.setBounds(100, 100, 900, 600);
    window.setBounds(100, 100, 900, 300);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
  }
}

