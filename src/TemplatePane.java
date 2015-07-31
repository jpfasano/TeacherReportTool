import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

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
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(panel);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      //scrollPane.setBounds(0, 0, 900, 300);
      
      //this.setLayout(null);
    
      //panel.setLayout(new GridLayout(0,1));
      
      GridBagLayout layout = new GridBagLayout();
      panel.setLayout(layout);
      
      GridBagConstraints gbc = new GridBagConstraints();
      
      gbc.gridx=0;
      gbc.gridy=0;
      gbc.weightx=1.0;
      gbc.weighty=0.0;
      gbc.gridheight=1;
      gbc.gridwidth=5;
      //gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      
      ArrayList<String> ts=tc.getTemplates();
      // Loop once for each template in category tc
      for (String t : ts){
        TemplateCheckBox tcb = new TemplateCheckBox(t);
        
        panel.add(tcb,gbc);
        templateCheckBoxesInPanel.add(tcb);
        
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        
        //tcbp.addItemListener(this);
      }
      JButton applyButton = new JButton("Apply");
      gbc.fill = GridBagConstraints.NONE;
      gbc.gridwidth=1;
      gbc.weighty=1.0;
      gbc.anchor = GridBagConstraints.WEST;
      panel.add(applyButton,gbc);
      applyButton.addActionListener(new ActionListener() { 
        public void actionPerformed(ActionEvent e) { 
          // Get string of all checked messages.
          String s=apply();
          
          //Substitute _NAME with student name.
          String sn=tra.getStudentName();
          s=s.replace("_NAME",sn);
          
          // Replace gender specific pronouns.
          Map<String, GenderWordPair> gwd = tra.getGenderWordsDict();
          String sg=tra.getStudentGender();
          for (Map.Entry<String, GenderWordPair> entry : gwd.entrySet())
          {
            String needle="_"+entry.getKey().toUpperCase().trim();
            String replacement=entry.getValue().getGenderWord(sg).trim();
            
            // Create upper case first character replacement
            char[] r1 = replacement.toCharArray();
            r1[0] = Character.toUpperCase(r1[0]);
            String replacementUC = new String(r1);
            
            // If needle is at beginning of sentence then replacement must start with an uppercase character
            if ( s.indexOf(needle)==0) {             
              s=replacementUC+s.substring(needle.length());
            }
            
            // Look for the needle at the beginning of a sentence.
            String [] endSent={".","?","!"};
            for (String es: endSent){
              String needle1=es+" "+needle;
              s=s.replace(needle1,es+" "+replacementUC);
              needle1=es+"  "+needle;
              s=s.replace(needle1,es+"  "+replacementUC);
            }
            s=s.replace(needle,replacement);
          }
          
          tra.getContentPanel().insertEditableText(s);
        } 
       });
      
      //p.add(tcbsp);
      //this.add(tc.getName(),p);
      
      //this.setPreferredSize(new Dimension(450, 150));
      this.setLayout(new GridBagLayout());
      gbc = new GridBagConstraints();
      gbc.gridx=0;
      gbc.gridy=0;
      gbc.weightx=1.0;
      gbc.weighty=1.0;
      //gbc.gridwidth=GridBagConstraints.REMAINDER; gbc.gridheight=GridBagConstraints.REMAINDER;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      
      this.add(scrollPane,gbc);
      
      //this.add(panel);
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
