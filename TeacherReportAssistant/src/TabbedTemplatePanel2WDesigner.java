import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	    
//	    setLayout(null);
//		
//		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//		tabbedPane.setBounds(10, 11, 430, 278);
//		add(tabbedPane);
	    
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{600};
		gridBagLayout.rowHeights = new int[]{350};
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
//		
//		TemplatesPanelIncApplyWDesigner tpl0 = new TemplatesPanelIncApplyWDesigner(tra,templates.get(0));
//        tabbedPane.addTab(templates.get(0).getName(), null, tpl0, null);
//		
//		TemplatesPanelIncApplyWDesigner tpl1 = new TemplatesPanelIncApplyWDesigner(tra,templates.get(1));
//        tabbedPane.addTab(templates.get(1).getName(), null, tpl1, null);
		
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
	      for (TemplateItemCheckBoxWithDesigner tcb : tp.getTemplateCheckBoxesInPanel())
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
