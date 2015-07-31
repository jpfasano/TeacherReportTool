package SaveSaveAs;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Save {

  private String defaultSaveAsFilename = "report.txt";
  private JFileChooser saveAsFileChooser = null;
  private SaveSaveAsWriteFile writeFileObj = null;
  private JFrame frame;

  public Save(JFrame f,SaveSaveAsWriteFile obj, String defaultSaveAsFilename){
    super();
    frame = f;
    writeFileObj = obj;
    this.defaultSaveAsFilename=defaultSaveAsFilename;
    gutsOfConstrutor(); 
  }
  
  public Save(JFrame f,SaveSaveAsWriteFile obj) {
    super();
    frame = f;
    writeFileObj = obj;
    gutsOfConstrutor();
  }
  
  private void gutsOfConstrutor() {

    // Setup SaveAs Dialog
    saveAsFileChooser = new JFileChooser();
    String initialPath = System.getProperty("user.dir") + "/";
    File initalFile = new File(initialPath + "/"+defaultSaveAsFilename);
    saveAsFileChooser.setSelectedFile(initalFile);

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

  public void doSaveAs() {
    // JFileChooser chooser = new JFileChooser(initialPath);
    int result = saveAsFileChooser.showOpenDialog(frame);
    if (result != JFileChooser.APPROVE_OPTION)
      return;
    File saveAsFile = saveAsFileChooser.getSelectedFile();
    defaultSaveAsFilename = saveAsFile.getName();
    
    // Check to see if the existing file exists. 
    if(saveAsFile.exists()) {
      // File exists, so ask about overwriting
        result = JOptionPane.showConfirmDialog(frame,"The file exists, overwrite?",
                "Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
        if(result!=JOptionPane.YES_OPTION) return;
    }
    
    // File either doesn't exist or it is ok to overwrite;
    writeFileObj.writeFile(saveAsFile);
  }
                  
}
    
    
