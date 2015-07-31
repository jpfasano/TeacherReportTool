/**
 * A menu bar for the TeacherReportAssistant tra
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;

public class MenuBar extends JMenuBar
    implements ActionListener
{
  private TeacherReportAssistant tra;
  private JCheckBoxMenuItem sound;
  private JMenuItem open, exit, howtoplay, about;

  public MenuBar(TeacherReportAssistant tra)
  {
    this.tra = tra;

    // "File" menu:

    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('F');

    // Second level menu under "Preferences":
    JMenu preferences = new JMenu("Preferences");
    preferences.setMnemonic('P');
    sound = new JCheckBoxMenuItem("Play Sound", true);
    sound.setMnemonic('S');
    preferences.add(sound);

    open  = new JMenuItem("Open ...");
    open.setMnemonic('O');
    open.addActionListener(this);

    exit = new JMenuItem("Exit");
    exit.setMnemonic('x');
    exit.addActionListener(this);

    fileMenu.add(preferences);
    fileMenu.addSeparator();
    fileMenu.add(open);
    fileMenu.add(exit);

    add(fileMenu);

    // "Help" menu:

    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic('H');

    howtoplay = new JMenuItem("How to play...");
    howtoplay.setMnemonic('p');
    howtoplay.addActionListener(this);

    about = new JMenuItem("About...");
    about.setMnemonic('A');
    about.addActionListener(this);

    helpMenu.add(howtoplay);
    helpMenu.add(about);

    add(helpMenu);
  }

  public boolean soundEnabled()
  {
    return sound.isSelected();
  }

  public void actionPerformed(ActionEvent e)
  {
    JMenuItem src = (JMenuItem)e.getSource();

    if (src == open)
      tra.open();
    else if (src == howtoplay)
      HelpMenuBar.showHelp();
    else if (src == about)
      HelpMenuBar.showAbout();
    else if (src == exit) {
      tra.updateStudentReportFromEditableText();
      System.out.println(tra.getReports());
      System.exit(0);
    }
  }
}

