import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;


public class ContentPanel extends JPanel
{
  
  private TeacherReportAssistant tra;
  private StudentPanel studentPanel;
  private TabbedTemplatePanel tabbedTemplatePanel;
  private EditableTextPanel editableTextPanel;
  
 

  public ContentPanel(TeacherReportAssistant tra)
  {
    super();
    this.tra=tra;

    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    
    
    // There are 3 parts to the content panel: Students, Templates, and Editable text.
    // Create each one and add to the panel.

    // Student Panel
    studentPanel=new StudentPanel(tra);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = .2;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(studentPanel,gbc);

    // Template Panel
    tabbedTemplatePanel = new TabbedTemplatePanel(tra);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0;
    gbc.weighty = .7;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(tabbedTemplatePanel,gbc);

    editableTextPanel =new EditableTextPanel(tra);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1.0;
    gbc.weighty = .7;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(editableTextPanel,gbc);
  
    
    
    
  /*
    
    // Add components Top to Bottom
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    // See: https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
    
    // Top section is student name
    studentNameLabel = new JLabel("   ", JLabel.CENTER);  
    
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
    tabbedPanes=new TabbedTemplatePanesX(tra);
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
    editableTextX = new EditableTextX(tra); 
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
    this.add(editableTextX,gbc);
    */
    
  }

  public void clearEditableText()
  {
    editableTextPanel.clearEditableText();
  }
  public void insertEditableText(String t)
  {
    editableTextPanel.insert(t) ;    
  }

  public void setEditableText(String t)
  {
    editableTextPanel.setText(t) ;    
  }
  
  public String getEditableTest()
  {
    // First update editable text to contain any current checked templates
    tabbedTemplatePanel.apply();
    return editableTextPanel.getText();
  }
  
  public void setFocusToFirstTab()
  {
    if(tabbedTemplatePanel!=null)
      tabbedTemplatePanel.setFocusToFirstTab();
  }

  
  public void updateStudentNameLabel(Student student)
  {
    studentPanel.updateStudentNameLabel(student);
  }

}
