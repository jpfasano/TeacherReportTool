/** Help for the TeacherReportAssistant game
 */

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class HelpMenuBar
{
  private JFrame jf;
  
  public void showHelp()
  {
    String longMessage = "<html> <font color=\"red\"> Come and listen to a story about a man named Jed "
        + "A poor mountaineer, barely kept his family fed, "
        + "Then one day he was shootin at some food, "
        + "And up through the ground came a bubblin crude. "
        + "Oil that is, black gold, Texas tea. "
        + "Well the first thing you know ol Jed\'s a millionaire ...</font></html>";
    
    
    //JTextArea textArea = new JTextArea();
    JTextPane textArea = new JTextPane();
    //textArea.setContentType("text/html");
    textArea.setText(longMessage);
    textArea.setEditable(false);
    //textArea.setLineWrap(true);
    //textArea.setWrapStyleWord(true);
     
    // wrap a scrollpane around it
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    
    // Button to dismiss dialog
    JButton jb = new JButton("OK");
    jb.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jf.dispose();
      }
    });
    
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    
    JPanel jp = new JPanel();
    jp.setLayout(gbl);

    gbc.gridx=0;
    gbc.gridy=0;
    gbc.weightx = .9;
    gbc.weighty = .9;
    gbc.fill = GridBagConstraints.BOTH;
    jp.add(scrollPane,gbc);
    

    gbc.gridx=0;
    gbc.gridy=1;
    gbc.weightx = .1;
    gbc.weighty = .1;
    gbc.fill = GridBagConstraints.NONE;
    jp.add(jb,gbc);
    
    
    
    jf=new JFrame();
    jf.setContentPane(jp);
    jf.setTitle("TeacherReportAssistant Help");
    jf.setSize(480,480);
    jf.setVisible(true);
  

  }

  public static void showAbout()
  {
    JOptionPane.showMessageDialog(null,
        "Teacher Report Assistant.\n" +
        "Java Source Code is available on GitHub",
        "About",       // Dialog title
        JOptionPane.PLAIN_MESSAGE);
  }
}
