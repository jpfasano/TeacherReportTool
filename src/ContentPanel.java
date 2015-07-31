import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;


public class ContentPanel extends JPanel
{
  
  private TeacherReportAssistant tra;
  private JLabel studentNameLabel;
  private TabbedTemplatePanes tabbedPanes;
  private EditableText editableText;
  
 

  public ContentPanel(TeacherReportAssistant tra)
  {
    this.tra=tra;
    
    // Add components Top to Bottom
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    // See: https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
    
    // Top section is student name
    studentNameLabel = new JLabel("Student Name", JLabel.CENTER);  
    
    BasicArrowButton backButton=new BasicArrowButton(BasicArrowButton.WEST);
    
    backButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        tra.backToPriorStudent();
      } 
     });
    
    BasicArrowButton fowardButton=new BasicArrowButton(BasicArrowButton.EAST);
    
    fowardButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        tra.advanceToNextStudent();
      } 
     });
    
    JPanel studentPanel = new JPanel();
    //studentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    studentPanel.setLayout(new GridBagLayout());
    
    gbc.gridx=1;
    gbc.gridy=0;
    //gbc.gridheight=10;
    //gbc.gridwidth=50;
    gbc.anchor =GridBagConstraints.CENTER;
    //gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx=1.;
    gbc.weighty=0.;
    gbc.insets = new Insets(0,0,0,0);
    studentPanel.add(studentNameLabel,gbc);
    
    gbc.gridx=0;
    gbc.gridy=0;
    //gbc.gridheight=10;
    //gbc.gridwidth=50;
    gbc.anchor =GridBagConstraints.LINE_START;
    //gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx=0.;
    gbc.weighty=0.;
    gbc.insets = new Insets(0,0,0,0);
    studentPanel.add(backButton,gbc);
 
    gbc.gridx=3;
    gbc.gridy=0;
    //gbc.gridheight=10;
    //gbc.gridwidth=50;
    gbc.anchor =GridBagConstraints.LINE_END;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx=0.;
    gbc.weighty=0.;
    gbc.insets = new Insets(0,0,0,0);
    studentPanel.add(fowardButton,gbc);
    
    
    gbc.gridx=0;
    gbc.gridy=0;
    gbc.gridheight=10;
    gbc.gridwidth=50;
    gbc.anchor =GridBagConstraints.FIRST_LINE_START;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx=0.;
    gbc.weighty=0.;
    gbc.insets = new Insets(0,0,0,0);
    this.add(studentPanel,gbc);
    
   
    // Second section is the Tabbed Categories with template phrases
    tabbedPanes=new TabbedTemplatePanes(tra);
    gbc.gridx=0;
    gbc.gridy=1;
    gbc.gridheight=100;
    gbc.gridwidth=50;
    gbc.anchor =GridBagConstraints.LINE_START; //LAST_LINE_START
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx=0.5;
    gbc.weighty=1.;
    gbc.insets = new Insets(20,0,0,0); // top padding
    this.add(tabbedPanes,gbc);
    
    // Third section is the editable text of the report
    editableText = new EditableText(); 
    //insertEditableText("Seth Winick, a spokesperson for the Texas Charter School Association, isn't completely sure what's working, but he says it likely has to do with how intensely focused charters are on serving English-language learners, students in poverty, and other underserved populations.");
  
    gbc.gridx=0;
    gbc.gridy=111;
    gbc.gridheight=100;
    gbc.gridwidth=50;
    gbc.anchor =GridBagConstraints.LAST_LINE_START;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx=0.5;
    gbc.weighty=1.;
    gbc.insets = new Insets(0,0,0,0); // top padding
    this.add(editableText,gbc);
    
    
  }

  public void clearEditableText()
  {
    editableText.clearEditableText();
  }
  public void insertEditableText(String t)
  {
    editableText.insert(t) ;    
  }
  
  public String getEditableTest()
  {
    return editableText.getText();
  }
  
  public void setFocusToFirstTab()
  {
    tabbedPanes.setSelectedIndex(0);
  }

  
  public void updateStudentNameLabel(Student student)
  {
    String sName=student.getName();
    String sGender=student.getGender();
    studentNameLabel.setText(sName+" gender:"+sGender); 
  }

}
