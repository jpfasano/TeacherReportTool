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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TemplateSentencePanel extends JPanel {

   private String templateWoComment;
   private String comment;
   private JCheckBox checkBox;

   /**
    * Create the panel.
    */
   public TemplateSentencePanel(String template) {

      super();
      this.setMinimumSize(new Dimension(200, 18));
      this.setPreferredSize(new Dimension(1030, 18));

      // find comment character if present
      int c = template.indexOf("#");
      if (c == -1) {
         comment = "";
         templateWoComment = template.trim();
      }
      else {
         comment = template.substring(0, c).trim();
         templateWoComment = template.substring(c + 1).trim();
      }

      GridBagLayout gridBagLayout = new GridBagLayout();

      setLayout(gridBagLayout);

      checkBox = new JCheckBox(templateWoComment);

      checkBox.setMinimumSize(new Dimension(180, 18));
      checkBox.setPreferredSize(new Dimension(1000, 18));

      GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
      gbc_chckbxNewCheckBox.fill = GridBagConstraints.BOTH;
      gbc_chckbxNewCheckBox.anchor = GridBagConstraints.NORTHWEST;
      // gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
      gbc_chckbxNewCheckBox.gridx = 1;
      gbc_chckbxNewCheckBox.gridy = 0;

      gbc_chckbxNewCheckBox.weightx = 1.0;
      gbc_chckbxNewCheckBox.weighty = 0.0001;

      this.add(checkBox, gbc_chckbxNewCheckBox);

      // Add comment before checkbox.
      JLabel commentLabel = new JLabel(comment);
      commentLabel.setHorizontalAlignment(SwingConstants.CENTER);
      GridBagConstraints gbc_label = new GridBagConstraints();
      gbc_label.fill = GridBagConstraints.BOTH;
      gbc_label.anchor = GridBagConstraints.NORTHWEST;
      // gbc_label.insets = new Insets(0, 0, 5, 0);
      gbc_label.gridx = 0;
      gbc_label.gridy = 0;

      gbc_label.weightx = .2;
      gbc_label.weighty = 0.0001;
      commentLabel.setMinimumSize(new Dimension(20, 18));
      commentLabel.setPreferredSize(new Dimension(30, 18));

      if (!comment.isEmpty())
         commentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

      this.add(commentLabel, gbc_label);

   }

   public void setSelected(boolean b) {
      checkBox.setSelected(b);

   }

   public boolean isSelected() {
      return checkBox.isSelected();
   }

   public String getTemplateWoComment() {
      return templateWoComment;
   }

   public String getComment() {
      return comment;
   }

   public String getTemplate() {
      return getComment() + getTemplateWoComment();
   }

}
