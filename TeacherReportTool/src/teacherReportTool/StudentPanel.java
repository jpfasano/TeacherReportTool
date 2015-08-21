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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

public class StudentPanel extends JPanel {
   // private TeacherReportTool trt;
   private JLabel studentNameLabel;

   public StudentPanel(TeacherReportTool trt) {
      super();
      // this.trt = trt;
      this.setMinimumSize(new Dimension(600, 18));
      studentNameLabel = new JLabel("", JLabel.CENTER);

      BasicArrowButton backButton = new BasicArrowButton(BasicArrowButton.WEST);

      backButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            trt.backToPriorStudent();
         }
      });

      BasicArrowButton fowardButton = new BasicArrowButton(BasicArrowButton.EAST);

      fowardButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            trt.advanceToNextStudent();
         }
      });

      // studentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      this.setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = .1;
      gbc.weighty = 0;
      gbc.fill = GridBagConstraints.BOTH;
      this.add(backButton, gbc);

      gbc.gridx = 1;
      gbc.gridy = 0;
      gbc.weightx = .8;
      gbc.weighty = 0;
      gbc.fill = GridBagConstraints.BOTH;
      this.add(studentNameLabel, gbc);

      gbc.gridx = 2;
      gbc.gridy = 0;
      gbc.weightx = .1;
      gbc.weighty = 0;
      gbc.fill = GridBagConstraints.BOTH;
      this.add(fowardButton, gbc);

      /*
       * JFrame jf = new JFrame(); jf.setContentPane(this); jf.setTitle(
       * "Test Student Panel"); jf.setSize(800, 480); jf.setVisible(true);
       */
   }

   public void updateStudentNameLabel(Student student) {
      String sName = student.getName();
      String sGender = student.getGender();
      String labelText = sName;
      if (!sGender.trim().isEmpty())
         labelText += " gender:" + sGender;
      studentNameLabel.setText(labelText);
   }

}
