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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class ContentPanel extends JPanel
{
  
  //private TeacherReportTool trt;
  private StudentPanel studentPanel;
  private EditableTextPanel editableTextPanel;
  private TemplatePanel templatePanel;
  
 
  // Constructor that creates a new content panel
  public ContentPanel(TeacherReportTool trt)
  {
    super();
    gutsOfContentPanelConstructor(trt, new StudentPanel(trt),  new TemplatePanel(trt), new EditableTextPanel(trt) );   
  }
  
//Constructor that creates a new content panel from priorContentPanel except for SentenceTemplates which are new.
 public ContentPanel(TeacherReportTool trt, ContentPanel priorContentPanel)
 {
   super(); 
   gutsOfContentPanelConstructor(trt, priorContentPanel.studentPanel,  new TemplatePanel(trt), priorContentPanel.editableTextPanel );
  
 }
 
 private void gutsOfContentPanelConstructor(TeacherReportTool trt, StudentPanel sp, TemplatePanel tp, EditableTextPanel etp)
 {
	  //this.trt=trt;

	   GridBagLayout gridBagLayout = new GridBagLayout();
	   gridBagLayout.rowWeights = new double[]{0.0};
	   gridBagLayout.columnWeights = new double[]{1.0};
	   this.setLayout(gridBagLayout);
	   GridBagConstraints gbc = new GridBagConstraints();
	   gbc.insets = new Insets(0, 0, 0, 0);
	   
	   
	   // There are 3 parts to the content panel: Students, Templates, and Editable text.
	   // Create each one and add to the panel.

	   // Student Panel
	   studentPanel=sp;


	   gbc.gridx = 0;
	   gbc.gridy = 0;
	   gbc.weightx = 1.0;
	   gbc.weighty = .0;
	   gbc.fill = GridBagConstraints.BOTH;
	   this.add(studentPanel,gbc);
	   
	   JSplitPane splitPane = new JSplitPane();
	   splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
	   GridBagConstraints gbc_splitPane = new GridBagConstraints();
	   gbc_splitPane.gridwidth = 1;
	   //gbc_splitPane.insets = new Insets(0, 0, 0, 5);
	   gbc_splitPane.fill = GridBagConstraints.BOTH;
	   gbc_splitPane.gridx = 0;
	   gbc_splitPane.gridy = 1;
	   gbc_splitPane.weightx = 1.0;
	   gbc_splitPane.weighty = .9;
	   this.add(splitPane, gbc_splitPane);
	   
	   editableTextPanel = etp;
	   splitPane.setRightComponent(editableTextPanel);
	   
	   templatePanel = tp;
	   splitPane.setLeftComponent(templatePanel);  
 }

  public void clearEditableText()
  {
	  getEditableTextPanel().clearEditableText();
  }
  public void insertEditableText(String t)
  {
	  getEditableTextPanel().insert(t) ;    
  }

  public void setEditableText(String t)
  {
	  getEditableTextPanel().setText(t) ;    
  }
  
  public String getEditableTest()
  {
    // First update editable text to contain any current checked templates
	  getTabbedTemplatePanel().apply();
    return getEditableTextPanel().getText();
  }
  
  public void setFocusToFirstTab()
  {
    if(getTabbedTemplatePanel()!=null)
    	getTabbedTemplatePanel().setFocusToFirstTab();
  }

  
  public void updateStudentNameLabel(Student student)
  {
    studentPanel.updateStudentNameLabel(student);
  }

	public EditableTextPanel getEditableTextPanel() {
		return editableTextPanel;
	}
	public TemplatePanel getTabbedTemplatePanel() {
		return templatePanel;
	}
}