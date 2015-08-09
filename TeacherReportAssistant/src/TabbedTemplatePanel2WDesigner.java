import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

public class TabbedTemplatePanel2WDesigner extends JPanel {
	
	private ArrayList<TemplatesPanelIncApplyWDesigner> templatePanels = new ArrayList<TemplatesPanelIncApplyWDesigner>();
	  private TeacherReportAssistant tra;
	  private JTabbedPane tabbedPane;

	/**
	 * Create the panel.
	 */
	public TabbedTemplatePanel2WDesigner(TeacherReportAssistant tra) {
		

	    super();
	    this.tra = tra;
	    ArrayList<TemplateCategory> templates = tra.getTemplateCategories();
	    
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{220, 0};
		gridBagLayout.rowHeights = new int[]{10, 0};
		gridBagLayout.columnWeights = new double[]{0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		setLayout(gridBagLayout);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		add(tabbedPane, gbc_tabbedPane);
		
		for (TemplateCategory tc: templates)
	    {
	      // Create panel to contain template check boxes
	      TemplatesPanelIncApplyWDesigner tpl = new TemplatesPanelIncApplyWDesigner(tra,tc);
	      tabbedPane.addTab(tc.getName(), null, tpl, null);
	    }
		
		 // Call apply() when tab is changed;
	    ChangeListener changeListner = new ChangeListener() {
	      public void stateChanged(ChangeEvent changeEvent) {
	        apply();
	      }
	    };
	    tabbedPane.addChangeListener(changeListner);

	}
	
	public void uncheckBoxes()
	  {
	    for (TemplatesPanelIncApplyWDesigner tp : templatePanels)
	      for (TemplateCheckBox tcb : tp.getTemplateCheckBoxesInPanel())
	        tcb.setSelected(false);
	  }
	  
	  public void setFocusToFirstTab()
	  {
	    if (tabbedPane != null) {
	      int cnt = tabbedPane.getTabCount();    
	      if (cnt > 0)
	    	  tabbedPane.setSelectedIndex(0);
	    }
	  }
	  
	  
	  public String apply()
	  {
	    // Get text associated with checked boxes
	    String retVal="";
	    for (TemplatesPanelIncApplyWDesigner tp : templatePanels)
	    {
	      String paneText=tp.apply();
	       retVal+=paneText;
	    }
	    uncheckBoxes();
	    return retVal;
	  }

}
