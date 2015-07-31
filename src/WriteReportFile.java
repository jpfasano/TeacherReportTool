import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import SaveSaveAs.SaveSaveAsWriteFile;

public class WriteReportFile implements SaveSaveAsWriteFile {

  TeacherReportAssistant tra;
  public WriteReportFile(TeacherReportAssistant tra) {
    this.tra=tra;
  }

  public void writeFile(File f) {

    BufferedWriter out = null;
    try  
    {
        FileWriter fstream = new FileWriter(f); 
        out = new BufferedWriter(fstream);
        String reportAsString = tra.getReports();
        out.write(reportAsString);
    }
    catch (IOException e)
    {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
    finally
    {
        if(out != null) {
            try {
              out.close();
            }
            catch (IOException e) {
              System.err.println("Error: " + e.getMessage());
              e.printStackTrace();
            }
        }
    }
    
  }

}
