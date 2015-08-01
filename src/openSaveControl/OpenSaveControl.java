package openSaveControl;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OpenSaveControl {

  private String defaultSaveAsFilename = "report.txt";
  private String defaultDirectory = "";
  
  private JFileChooser saveAsFileChooser = null;
  
  private JFileChooser openDirChooser=null;
  private OpenSaveControlClient oscClient = null;
  
  private JFrame frame;

  public OpenSaveControl(JFrame f, OpenSaveControlClient orf, String defaultSaveAsFilename){
    super();
    frame = f;
    oscClient = orf;
    this.defaultSaveAsFilename=defaultSaveAsFilename;
    gutsOfConstrutor(); 
  }
  
  public OpenSaveControl(JFrame f, OpenSaveControlClient orf) {
    super();
    frame = f;
    oscClient = orf;
    gutsOfConstrutor();
  }
  
  private void gutsOfConstrutor() {
    defaultDirectory = System.getProperty("user.dir");

    // Setup SaveAs Dialog
    {
    saveAsFileChooser = new JFileChooser();

    saveAsFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    // Setup file filters
    // The filters should not be hard coded.
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
        "txt files (*.txt)", "txt");
    FileNameExtensionFilter docFilter = new FileNameExtensionFilter(
        "doc files (*.doc)", "doc");
    saveAsFileChooser.addChoosableFileFilter(txtFilter);
    saveAsFileChooser.addChoosableFileFilter(docFilter);
    saveAsFileChooser.setFileFilter(txtFilter);
    }
    
    // Setup Open Directory Dialog (directory containing input data files)
    {
      openDirChooser = new JFileChooser();
      openDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
    
    // Until a file is opened then there is nothing to save
    // oscClient.enableSaveMenuItem(false);
    // oscClient.enableSaveAsMenuItem(false);

  }
  
  public void doOpen() {

    File dir = new File(defaultDirectory);
    openDirChooser.setSelectedFile(dir);
    
    int result = openDirChooser.showOpenDialog(frame);
    if (result != JFileChooser.APPROVE_OPTION) return;
    dir = openDirChooser.getSelectedFile();
    defaultDirectory = dir.getAbsolutePath(); 
    
    // File either doesn't exist or it is ok to overwrite;
    oscClient.openReadFile(dir);
    
    oscClient.enableSaveMenuItem(false);
    oscClient.enableSaveAsMenuItem(true);
    
  }
  

  public void doSaveAs() {

    String pathSeparator = System.getProperty("file.separator");
    File file = new File(defaultDirectory + pathSeparator + defaultSaveAsFilename);
    saveAsFileChooser.setSelectedFile(file);
    
    // JFileChooser chooser = new JFileChooser(initialPath);
    int result = saveAsFileChooser.showOpenDialog(frame);
    if (result != JFileChooser.APPROVE_OPTION)
      return;
    File saveAsFile = saveAsFileChooser.getSelectedFile();

    defaultSaveAsFilename = saveAsFile.getName();
    defaultDirectory = saveAsFile.getParent(); 
    
    // Check to see if the existing file exists. 
    if(saveAsFile.exists()) {
      // File exists, so ask about overwriting
        result = JOptionPane.showConfirmDialog(frame,"The file exists, overwrite?",
                "Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
        if(result!=JOptionPane.YES_OPTION) return;
    }
    
    // File either doesn't exist or it is ok to overwrite;
    oscClient.writeFile(saveAsFile);

    oscClient.enableSaveMenuItem(true);
  }
  

  public void doSave() {

    String pathSeparator = System.getProperty("file.separator");
    File file = new File(defaultDirectory + pathSeparator + defaultSaveAsFilename);
    
    oscClient.writeFile(file);
  }
  
                  
}
    
    
