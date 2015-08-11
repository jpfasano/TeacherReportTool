
public class Student
{
  private String name_;
  private String gender_;
  private String report;
  
  public Student(String gender,String name)
  {
    super();
    name_ = name;
    gender_ = gender;
    report="";
  }
  public String getName()
  {
    return name_;
  }
  public String getGender()
  {
    return gender_;
  }
  public String getReport()
  {
    return report;
  }
  public void setReport(String report)
  {
    this.report = report;
  }
  
 }
