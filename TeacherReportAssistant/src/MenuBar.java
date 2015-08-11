/**
 * A menu bar for the TeacherReportAssistant tra
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import openSaveControl.OpenSaveControl;

public class MenuBar extends JMenuBar
    implements ActionListener
{
  private TeacherReportAssistant tra;
  private JCheckBoxMenuItem insertAtCursor;
  private JMenuItem open, exit, howtoplay, about;
  private JMenuItem save, saveAs;
  
  private OpenSaveControl openSaveControl;
  private TraOpenSaveControlClient traOpenSaveControlClient;

  public MenuBar(TeacherReportAssistant tra)
  {
    this.tra = tra;
 
    // "File" menu:

    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('F');

    // Second level menu under "Preferences":
    JMenu preferences = new JMenu("Preferences");
    preferences.setMnemonic('P');
    insertAtCursor = new JCheckBoxMenuItem("Insert at cursor", false);
    insertAtCursor.setMnemonic('I');
    preferences.add(insertAtCursor);

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

    howtoplay = new JMenuItem("Help ...");
    howtoplay.setMnemonic('H');
    howtoplay.addActionListener(this);

    about = new JMenuItem("About...");
    about.setMnemonic('A');
    about.addActionListener(this);

    helpMenu.add(howtoplay);
    helpMenu.add(about);

    add(helpMenu);
    
    // Must be instantiated after the menu items exist
    traOpenSaveControlClient = new TraOpenSaveControlClient(tra);
    openSaveControl=new OpenSaveControl(tra,traOpenSaveControlClient);
    
    // Save and SaveAs are not available until after Open is done
    save.setEnabled(false);
    saveAs.setEnabled(false);
  }

  public boolean insertAtCursorEnabled()
  {
    return insertAtCursor.isSelected();
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
      new HelpMenuBar().showHelp();
    else if (src == about)
      HelpMenuBar.showAbout();
    else if (src == exit) {
      tra.updateStudentReportFromEditableText();
      System.out.println(tra.getReports());
      System.exit(0);
    }
  }
  
  // Enable/Disable File menu bar items
  public void enableSaveMenuItem(Boolean b) {
    save.setEnabled(b);
  }
  public void enableSaveAsMenuItem(Boolean b) {
     saveAs.setEnabled(b);
  }
}

