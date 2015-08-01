package openSaveControl;

import java.io.File;

// Client methods to be implemented by users of OpenSaveControl 

public interface OpenSaveControlClient {
  
  // After open file selection dialog this method does the actual file reading
  public void openReadFile(File f);
  
  // After save dialog, this method does the actual file writing
  public void writeFile(File f);

}
