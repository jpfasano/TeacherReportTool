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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TemplatePanel extends JPanel {

   private ArrayList<TemplateCategoryPanel> templatePanels = new ArrayList<TemplateCategoryPanel>();
   // private TeacherReportTool trt;
   private JTabbedPane tabbedPane;

   /**
    * Create the panel.
    */
   public TemplatePanel(TeacherReportTool trt) {

      super();
      // this.trt = trt;

      this.setPreferredSize(new Dimension(600, 250));
      this.setMinimumSize(new Dimension(200, 100));

      ArrayList<TemplateCategory> templates = trt.getTemplateCategories();

      GridBagLayout gridBagLayout = new GridBagLayout();
      setLayout(gridBagLayout);

      tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
      gbc_tabbedPane.anchor = GridBagConstraints.NORTHWEST;
      gbc_tabbedPane.fill = GridBagConstraints.BOTH;
      gbc_tabbedPane.gridx = 0;
      gbc_tabbedPane.gridy = 0;
      gbc_tabbedPane.weightx = .5;
      gbc_tabbedPane.weighty = 1.;
      add(tabbedPane, gbc_tabbedPane);

      for (TemplateCategory tc : templates) {
         // Create panel to contain template check boxes
         TemplateCategoryPanel tpl = new TemplateCategoryPanel(trt, tc);
         tabbedPane.addTab(tc.getName(), null, tpl, null);
         templatePanels.add(tpl);
      }

      // Call apply() when tab is changed;
      ChangeListener changeListner = new ChangeListener() {
         public void stateChanged(ChangeEvent changeEvent) {
            apply();
         }
      };
      tabbedPane.addChangeListener(changeListner);

   }

   public void uncheckBoxes() {
      for (TemplateCategoryPanel tp : templatePanels)
         for (TemplateSentencePanel tcb : tp.getTemplateCheckBoxesInPanel())
            tcb.setSelected(false);
   }

   public void setFocusToFirstTab() {
      if (tabbedPane != null) {
         int cnt = tabbedPane.getTabCount();
         if (cnt > 0)
            tabbedPane.setSelectedIndex(0);
      }
   }

   public String apply() {
      // Get text associated with checked boxes
      String retVal = "";
      for (TemplateCategoryPanel tp : templatePanels) {
         String paneText = tp.apply();
         retVal += paneText;
      }
      uncheckBoxes();
      return retVal;
   }

}
