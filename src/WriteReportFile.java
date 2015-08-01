import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import openSaveControl.SaveSaveAsWriteFile;

public class WriteReportFile implements SaveSaveAsWriteFile {

  TeacherReportAssistant tra;
  public WriteReportFile(TeacherReportAssistant tra) {
    this.tra=tra;
  }

  public void writeFile(File f) {

    //BufferedWriter out = null;
    PrintStream fileStream = null;
    try  
    {
      fileStream = new PrintStream(f);
      String reportAsString = tra.getReports();
      String [] split = reportAsString.split("\n");
      for (String s:split) {
        System.out.println(s);
        fileStream.println(s);
      }

      
      //fileStream.println(reportAsString);
       // FileWriter out = new FileWriter(f); 
       // out = new BufferedWriter(out);
        //out.write(reportAsString);
    }
    catch (IOException e)
    {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
    finally
    {
        if(fileStream != null) {
            try {
              fileStream.close();
            }
            catch (Exception e) {
              System.err.println("Error: " + e.getMessage());
              e.printStackTrace();
            }
        }
    }
    
  }

}
