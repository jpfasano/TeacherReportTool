/**
 * A menu bar for the TeacherReportAssistant tra
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;

import openSaveControl.OpenSaveControl;

public class MenuBar extends JMenuBar
    implements ActionListener
{
  private TeacherReportAssistant tra;
  private JCheckBoxMenuItem sound;
  private JMenuItem open, exit, howtoplay, about;
  private JMenuItem save, saveAs;
  
  private OpenSaveControl openSaveControl;
  private TraOpenSaveControlClient traOpenSaveControlClient;

  public MenuBar(TeacherReportAssistant tra)
  {
    this.tra = tra;
    
    traOpenSaveControlClient = new TraOpenSaveControlClient(tra);
    openSaveControl=new OpenSaveControl(tra,traOpenSaveControlClient);

    // "File" menu:

    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('F');

    // Second level menu under "Preferences":
    JMenu preferences = new JMenu("Preferences");
    preferences.setMnemonic('P');
    sound = new JCheckBoxMenuItem("Play Sound", true);
    sound.setMnemonic('S');
    preferences.add(sound);

    open  = new JMenuItem("Open Directory ...");
    open.setMnemonic('O');
    open.addActionListener(this);
    

    save  = new JMenuItem("Save");
    save.setMnemonic('S');
    save.addActionListener(this);  

    saveAs  = new JMenuItem("Save As ...");
    //save.setMnemonic('S');
    saveAs.addActionListener(this);
    
    

    exit = new JMenuItem("Exit");
    exit.setMnemonic('x');
    exit.addActionListener(this);

    fileMenu.add(preferences);
    fileMenu.addSeparator();
    fileMenu.add(open);
    fileMenu.addSeparator();
    fileMenu.add(save);
    fileMenu.add(saveAs);
    fileMenu.addSeparator();
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

    if (src == open) {
      openSaveControl.doOpen();
    }
    else if (src ==save){
      openSaveControl.doSave();
    }
    else if (src ==saveAs){
      openSaveControl.doSaveAs();
    }
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

