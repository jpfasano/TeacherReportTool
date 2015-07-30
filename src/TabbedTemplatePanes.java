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

    for (TemplateCategory tc : templates)
    {
      JPanel p = new JPanel();

      // Create panel to contain template check boxes
      TemplatePane tp = new TemplatePane(tra,tc);
      templatePanes.add(tp);
      

      p.add(tp);
      
      this.add(tc.getName(), p);
    }
    
    // Call apply() when tab is changed;
    ChangeListener changeListner = new ChangeListener() {
      public void stateChanged(ChangeEvent changeEvent) {
        String s=apply();
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

/*
 * public String apply() { String retVal=""; for ( TemplateCheckBox tcb :
 * templateCheckBoxesInPanel) { if (tcb.isSelected()) retVal +=
 * (tcb.getText()+" "); } System.out.println(retVal); return retVal; }
 */

/*
 * public void itemStateChanged(ItemEvent e) { TemplateCheckBox tcb =
 * (TemplateCheckBox) e.getItemSelectable(); System.out.println(tcb.getText());
 * 
 * }
 */

