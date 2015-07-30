import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TemplatePane extends JPanel
{
  private ArrayList<TemplateCheckBox> templateCheckBoxesInPanel = new ArrayList<TemplateCheckBox>();

  public TemplatePane()
  {
    super();
  }
  
  public TemplatePane(TeacherReportAssistant tra,TemplateCategory tc) {
  //public TemplatePane(TeacherReportAssistant tra) {
    
      super();
      // Create panel to contain template check boxes
      JPanel panel = new JPanel();
      JScrollPane scrollPane = new JScrollPane(panel);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setBounds(0, 0, 900, 300);
      
      this.setLayout(null);
    
      //panel.setLayout(new GridLayout(0,1));
      
      GridBagLayout layout = new GridBagLayout();
      panel.setLayout(layout);
      
      GridBagConstraints gbc = new GridBagConstraints();
      
      gbc.gridx=0;
      gbc.gridy=0;
      gbc.gridheight=1;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.FIRST_LINE_START;
      
      ArrayList<String> ts=tc.getTemplates();
      // Loop once for each template in category tc
      for (String t : ts){
        TemplateCheckBox tcb = new TemplateCheckBox(t);
        
        panel.add(tcb,gbc);
        templateCheckBoxesInPanel.add(tcb);
        
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_START;
        
        //tcbp.addItemListener(this);
      }
      JButton applyButton = new JButton("Apply");
      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.LAST_LINE_START;
      panel.add(applyButton,gbc);
      applyButton.addActionListener(new ActionListener() { 
        public void actionPerformed(ActionEvent e) { 
          String s=apply();
          tra.getContentPanel().insertEditableText(s);
        } 
       });
      
      //p.add(tcbsp);
      //this.add(tc.getName(),p);
      
      this.setPreferredSize(new Dimension(450, 150));
      this.setLayout(new GridBagLayout());
      gbc = new GridBagConstraints();
      gbc.gridx=0;
      gbc.gridy=0;
      gbc.gridwidth=GridBagConstraints.REMAINDER; gbc.gridheight=GridBagConstraints.REMAINDER;
      gbc.anchor = GridBagConstraints.FIRST_LINE_START;
      this.add(scrollPane,gbc);
      
      
    }

  
  public ArrayList<TemplateCheckBox> getTemplateCheckBoxesInPanel()
  {
    return templateCheckBoxesInPanel;
  }

  public void uncheckBoxes()
  {
    for ( TemplateCheckBox tcb : templateCheckBoxesInPanel) {
      tcb.setSelected(false);
    }
  }
  
  public String apply()
  {
    String retVal="";
    for ( TemplateCheckBox tcb : templateCheckBoxesInPanel) {
      if (tcb.isSelected())
           retVal += (tcb.getTemplateWoComment()+" ");
    }
    //System.out.println(retVal);
    uncheckBoxes();
    
    return retVal;
  }

}
/*
public class TemplatePane extends JPanel
{
  private ArrayList<TemplateCheckBox> templateCheckBoxesInPanel = new ArrayList<TemplateCheckBox>();

  public TemplatePane()
  {
    super();
  }
  
  public TemplatePane(TeacherReportAssistant tra,TemplateCategory tc) {
  //public TemplatePane(TeacherReportAssistant tra) {
    
      super();
      // Create panel to contain template check boxes
      //JPanel tcbsp = new JPanel(new GridLayout(0,1));
    
      this.setLayout(new GridLayout(0,1));
      ArrayList<String> ts=tc.getTemplates();
      // Loop once for each template in category tc
      for (String t : ts){
        TemplateCheckBox tcb = new TemplateCheckBox(t);
        
        this.add(tcb);
        templateCheckBoxesInPanel.add(tcb);
        
        //tcbp.addItemListener(this);
      }
      JButton applyButton = new JButton("Apply");
      this.add(applyButton);
      applyButton.addActionListener(new ActionListener() { 
        public void actionPerformed(ActionEvent e) { 
          String s=apply();
          tra.getContentPanel().insertEditableText(s);
        } 
       });
      
      //p.add(tcbsp);
      //this.add(tc.getName(),p);
      
      
    }

  
  public ArrayList<TemplateCheckBox> getTemplateCheckBoxesInPanel()
  {
    return templateCheckBoxesInPanel;
  }

  public void uncheckBoxes()
  {
    for ( TemplateCheckBox tcb : templateCheckBoxesInPanel) {
      tcb.setSelected(false);
    }
  }
  
  public String apply()
  {
    String retVal="";
    for ( TemplateCheckBox tcb : templateCheckBoxesInPanel) {
      if (tcb.isSelected())
           retVal += (tcb.getTemplateWoComment()+" ");
    }
    //System.out.println(retVal);
    uncheckBoxes();
    
    return retVal;
  }

}
*/
