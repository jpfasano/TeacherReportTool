import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;


public class ContentPanelWDesigner extends JPanel
{
  
  private TeacherReportAssistant tra;
  private StudentPanel studentPanel;
  private EditableTextPanel editableTextPanel;
  private TabbedTemplatePanel2WDesigner tabbedTemplatePanel;
  private GridBagConstraints gbc_1;
  
 

  public ContentPanelWDesigner(TeacherReportAssistant tra)
  {
    super();
    this.tra=tra;

    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0};
    gridBagLayout.columnWeights = new double[]{1.0};
    this.setLayout(gridBagLayout);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 0, 5, 0);
    
    
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
    
    tabbedTemplatePanel = new TabbedTemplatePanel2WDesigner(tra);
    GridBagConstraints gbc_tabbedTemplatePanelWDesigner = new GridBagConstraints();
    gbc_tabbedTemplatePanelWDesigner.insets = new Insets(0, 0, 5, 0);
    gbc_tabbedTemplatePanelWDesigner.fill = GridBagConstraints.BOTH;
    gbc_tabbedTemplatePanelWDesigner.gridx = 0;
    gbc_tabbedTemplatePanelWDesigner.gridy = 1;
    add(tabbedTemplatePanel, gbc_tabbedTemplatePanelWDesigner);

    editableTextPanel =new EditableTextPanel(tra);
    gbc_1 = new GridBagConstraints();
    gbc_1.gridx = 0;
    gbc_1.gridy = 2;
    gbc_1.weightx = 1.0;
    gbc_1.weighty = .7;
    gbc_1.fill = GridBagConstraints.BOTH;
    this.add(editableTextPanel,gbc_1);
  
   
   
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
