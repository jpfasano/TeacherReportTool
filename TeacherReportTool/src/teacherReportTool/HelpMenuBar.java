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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class HelpMenuBar {
	private JFrame jf;

	// This is a Kludge. The objective is to get the resource loaded when running from
	// The Eclipse IDE or an executable jar file.
	public static URL getResourceURL(String resourceName) {
		URL retVal = null;
		try {

			
			ClassLoader classLoader = (new HelpMenuBar()).getClass().getClassLoader();

			System.out.println("got here 0");
			retVal = classLoader.getResource("/teacherReportTool/resources/" + resourceName);
			System.out.println("got here 1");
			if (retVal==null) {
				System.out.println("got here 2");
				retVal = classLoader.getResource("teacherReportTool/resources/" + resourceName);
				System.out.println("got here 3");
			}
			if (retVal==null) {
				System.out.println("got here 4");
				retVal = classLoader.getResource("resources/" + resourceName);
				System.out.println("got here 5");
			}
			if (retVal==null) {
				System.out.println("got here 6");
				retVal = classLoader.getResource("/resources/" + resourceName);
				System.out.println("got here 7");
			}
			if (retVal==null) {
				System.out.println("got here 8");
				retVal = classLoader.getResource("/" + resourceName);
				System.out.println("got here 9");
			}
			if (retVal==null) {
				System.out.println("got here A");
				retVal = classLoader.getResource( resourceName);
				System.out.println("got here B");
			}
		} catch (Exception e) {
				System.err.println(e.getMessage());
				
		}

		return retVal;
	}

	public void showHelp() {
		String longMessage = "";

		try {

			// Read help html formated help text from resource directory
			URL url=getResourceURL("helpText.html");
			System.out.println("URL is "+url);
			String fileAsString = url.getFile();
			System.out.println("fileAsString is" +fileAsString);
			File file = new File(fileAsString); 

			System.out.println("File is" +file);
			FileInputStream fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				longMessage += strLine;
			}
			br.close();

			// Read image file of screen shot from resource directory
			//String screenShot1 = classLoader.getResource("resources/trtScreenShot.jpg").toString();
			String screenShot1 = getResourceURL("trtScreenShot.jpg").toString();

			// Put screen image into html text
			longMessage = longMessage.replace("_IMAGE1", screenShot1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// JTextArea textArea = new JTextArea();
		JTextPane textArea = new JTextPane();
		textArea.setFont(new Font("Arial", Font.PLAIN, 14));
		textArea.setContentType("text/html");
		textArea.setText(longMessage);
		textArea.setEditable(false);
		textArea.setCaretPosition(0);
		textArea.setMargin(new Insets(15, 15, 15, 15));

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
		JOptionPane.showMessageDialog(null,
				"Teacher Report Tool.\n" + "Copyright (c) 2015 JP Fasano.\n"
						+ "Java source code is available on GitHub.com",
				"About", // Dialog title
				JOptionPane.PLAIN_MESSAGE);
	}
}