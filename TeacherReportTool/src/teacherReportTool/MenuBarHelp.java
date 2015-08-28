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
/** Help for the TeacherReportTool 
 */

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class MenuBarHelp extends JMenu {
   private JFrame jf;

   public MenuBarHelp(TeacherReportTool trt) {
      setText("Help");
      setMnemonic(KeyEvent.VK_H);

      JMenuItem menuItem = null;

      // menuItem = new JMenuItem();
      // menuItem.setText("Select All");
      // menuItem.addActionListener(new ActionListener() {
      // public void actionPerformed(ActionEvent e) {
      // trt.getContentPanel().getEditableTextPanel().selectAll();
      // }
      // });
      // menuItem.setMnemonic(KeyEvent.VK_A);
      // add(menuItem);

      menuItem = new JMenuItem();
      menuItem.setText("Help ...");
      menuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            showHelp();
         }
      });
      menuItem.setMnemonic(KeyEvent.VK_H);
      add(menuItem);

      menuItem = new JMenuItem();
      menuItem.setText("About...");
      menuItem.setMnemonic(KeyEvent.VK_A);
      menuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            showAbout();
         }
      });
      add(menuItem);

   }

   public void showHelp() {
      String longMessage = "";

      try {

         // Read help html formated help text from resource directory
         InputStream fstream = Utilities.getResourceInputStream("helpText.html");
         //
         BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

         String strLine;

         // Read File Line By Line
         while ((strLine = br.readLine()) != null) {
            longMessage += strLine;
         }
         br.close();

         // Read each image file of a screen shot from resource directory
         // and insert into html string.
         String[] jpgs = {"trtScreenShot.jpg", "openFiles.jpg", "1stCategory.jpg", "apply.jpg", "editableText.jpg",
               "2ndCategory.jpg", "3rdCategory.jpg", "rightArrow.jpg", "saveAs.jpg", "splitPane.jpg"};
         for (int j = jpgs.length - 1; j >= 0; j--) {
            String screenShot = Utilities.getResourceURL(jpgs[j]).toString();
            longMessage = longMessage.replace("_IMAGE" + (j + 1), screenShot);
         }
      }
      catch (Exception e) {
         System.err.println(e.getMessage());
         e.printStackTrace();
      }

      // JTextArea textArea = new JTextArea();
      JTextPane textArea = new JTextPane();
      textArea.setFont(new Font("Arial", Font.PLAIN, 14));
      textArea.setContentType("text/html");
      textArea.setText(longMessage);
      textArea.setEditable(false);
      textArea.setCaretPosition(0);
      textArea.setMargin(new Insets(15, 15, 15, 15));
      
      // Used to create Help.html file
      //System.out.println(longMessage);

      // wrap a scrollpane around it
      JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

      // Button to dismiss dialog
      JButton jb = new JButton("OK");
      jb.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jf.dispose();
         }
      });

      GridBagLayout gbl = new GridBagLayout();
      GridBagConstraints gbc = new GridBagConstraints();

      JPanel jp = new JPanel();
      jp.setLayout(gbl);

      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = .9;
      gbc.weighty = .9;
      gbc.fill = GridBagConstraints.BOTH;
      jp.add(scrollPane, gbc);

      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.weightx = .0;
      gbc.weighty = .0;
      gbc.fill = GridBagConstraints.NONE;
      jp.add(jb, gbc);

      jf = new JFrame();
      jf.setContentPane(jp);
      jf.setTitle("TeacherReportTool Help");
      jf.setSize(750, 650);
      jf.setVisible(true);

   }

   public static void showAbout() {
      String buildTime = Utilities.getTime();
      JOptionPane.showMessageDialog(null,
            "Teacher Report Tool.\n" + "Copyright (c) 2015 JP Fasano.\n" + "Build Time Stamp: " + buildTime + "\n"
                  + "Java source code is available on GitHub.com",
            "About", // Dialog title
            JOptionPane.PLAIN_MESSAGE);
   }
}
