import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabbedTemplatePanes extends JTabbedPane
// implements ItemListener
{
  private ArrayList<TemplatePane> templatePanes = new ArrayList<TemplatePane>();

  public TabbedTemplatePanes()
  {
    super();
  }

  public TabbedTemplatePanes(TeacherReportAssistant tra)
  {
    ArrayList<TemplateCategory> templates = tra.getTemplates();

    //for (int i=templates.size()-1; i>=0; i--)
    for (TemplateCategory tc: templates)
    {
      //TemplateCategory tc = templates.get(i);
      JPanel p = new JPanel();
      
      GridBagLayout layout = new GridBagLayout();
      p.setLayout(layout);
      
      GridBagConstraints gbc = new GridBagConstraints();
      
      gbc.gridx=0;
      gbc.gridy=0;
      gbc.weightx=1.0;
      gbc.weighty=1.0;
      gbc.anchor = GridBagConstraints.NORTHWEST;

      // Create panel to contain template check boxes
      TemplatePane tp = new TemplatePane(tra,tc);
      templatePanes.add(tp);
      

      p.add(tp,gbc);
      
      this.add(tc.getName(), p);
    }
    
    // Call apply() when tab is changed;
    ChangeListener changeListner = new ChangeListener() {
      public void stateChanged(ChangeEvent changeEvent) {
        String s=apply();
        if (s.length()>0)
           tra.getContentPanel().insertEditableText(s);
      }
    };
    this.addChangeListener(changeListner);
  }

  public void uncheckBoxes()
  {
    for (TemplatePane tp : templatePanes)
      for (TemplateCheckBox tcb : tp.getTemplateCheckBoxesInPanel())
        tcb.setSelected(false);
  }
  
  
  public String apply()
  {
    String retVal="";
    for (TemplatePane tp : templatePanes)
    {
      String paneText=tp.apply();
       retVal+=paneText;
    }
    System.out.println("|"+retVal+"|");
    uncheckBoxes();
    return retVal;
  }
}



