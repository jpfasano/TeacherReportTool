import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;


public class ContentPanel extends JPanel
{
  
  private TeacherReportAssistant tra;
  private StudentPanel studentPanel;
  private TabbedTemplatePanel2WDesigner tabbedTemplatePanel;
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
    tabbedTemplatePanel = new TabbedTemplatePanel2WDesigner(tra);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0;
    gbc.weighty = .7;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.WEST;
    this.add(tabbedTemplatePanel,gbc);

    editableTextPanel =new EditableTextPanel(tra);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1.0;
    gbc.weighty = .7;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(editableTextPanel,gbc);
  
   
   
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
