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

import javax.swing.JFrame;
import javax.swing.UIManager;

public class TeacherReportAssistant extends JFrame
{
  private MenuBar traMenuBar;
  private ArrayList<Student> students;
  private int studentsIndex;
  private Map<String, GenderWordPair> genderWordsDict;
  private ArrayList<TemplateCategory> templates;
  //private JLabel studentNameLabel;
  //private JTabbedPane tempatePane;
  private ContentPanel panelWithTabs;
 

  public TeacherReportAssistant()
  {
    super("TeacherReportAssistant");
    
    templates = new ArrayList<TemplateCategory>();
    students = new ArrayList<Student>();
    genderWordsDict = new HashMap<String, GenderWordPair>();

    traMenuBar = new MenuBar(this);
    setJMenuBar(traMenuBar);
       
    panelWithTabs = new ContentPanel(this);
    getContentPane().add(panelWithTabs);
    
    readGenderWordPairs();

  }

  
  
  public void advanceToNextStudent()
  {
    // copy checked templates to editable text
    updateStudentReportFromEditableText();
    studentsIndex++;
    if (studentsIndex>=students.size()) studentsIndex=students.size()-1;
    updateStudentNameLabel();
    panelWithTabs.setFocusToFirstTab();
    
    // Set editable text to text for student
    updateEditableTextFromStudentReport();
  }

  public void backToPriorStudent()
  {
    updateStudentReportFromEditableText();
    studentsIndex--;
    if (studentsIndex<0)studentsIndex=0;
    if (students.size()==0) studentsIndex=-1;
    updateStudentNameLabel();
    panelWithTabs.setFocusToFirstTab();

    // Set editable text to text for student
    updateEditableTextFromStudentReport();
  }
  
  public void updateStudentReportFromEditableText()
  {
    if (students.size()==0) return;
    if (studentsIndex==-1) return;
    String r=panelWithTabs.getEditableTest();
    students.get(studentsIndex).setReport(r);
  }
  

  public void updateEditableTextFromStudentReport()
  {
    String sr="";
    if (students.size()==0)
      sr="";
    else if (studentsIndex==-1) 
      sr="";
    else
      sr = students.get(studentsIndex).getReport();
    panelWithTabs.setEditableText(sr);
  }
  
  public String getReports()
  {
    // Make sure current editable text is copied to student
    updateStudentReportFromEditableText();
    
    String retVal="";
    for (Student s : getStudents()) {
      String sReport=s.getReport().trim();
      if (sReport.length()==0) continue;
      retVal+="\n\n"+s.getName();
      retVal+="\n"+s.getReport();
    }
    return retVal;
  }
  
  private void updateStudentNameLabel()
  {
    if(studentsIndex<0)return;
    panelWithTabs.updateStudentNameLabel(students.get(studentsIndex));
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
    studentsIndex = -1;
  }

  public ArrayList<Student> getStudents()
  {
    return students;
  }
  
  

  public void setPanelWithTabs(ContentPanel panelWithTabs) {
    this.panelWithTabs = panelWithTabs;
  }
  

  public ContentPanel getPanelWithTabs() {
    return panelWithTabs;
  }

  public ContentPanel getContentPanel()
  {
    return panelWithTabs;
  }
  
  private void readGenderWordPairs() {
    String pathSeparator = System.getProperty("file.separator"); 
    try {

     
      ClassLoader classLoader = getClass().getClassLoader();
      //File file = new File(classLoader.getResource(".."+pathSeparator+"resources"+pathSeparator+"genderWordPairs.tra").getFile());
      File file = new File(classLoader.getResource("resources/genderWordPairs.tra").getFile());
      FileInputStream fstream = new FileInputStream(file);
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
        genderWordsDict.put(parsed[0], gwp);
        genderWordsDict.put(parsed[1], gwp);
      }
      br.close();
    }
    catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
  



  public MenuBar getTraMenuBar() {
    return traMenuBar;
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

