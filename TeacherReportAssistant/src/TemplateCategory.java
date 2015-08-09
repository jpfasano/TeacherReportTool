import java.util.ArrayList;
public class TemplateCategory
{

  private String name;
  private ArrayList<String> templates;
  
  public TemplateCategory(String name, ArrayList<String> templates)
  {
    super();
    this.name = name;
    this.templates = templates;
  }
  
  public TemplateCategory(String name)
  {
    super();
    this.name = name;
    this.templates = new ArrayList<String>();
  }
  
  public TemplateCategory()
  {
    super();
    this.name = "undefined";
    this.templates = new ArrayList<String>();
  }
  
  public void addTemplate(String t)
  {
    templates.add(t);
  }
  
  public String getName()
  {
    return name;
  }

  public ArrayList<String> getTemplates()
  {
    return templates;
  }
  
  boolean empty()
  {
    if (templates.size()==0) return true;
    return false;
  }

  public String toString()
  {
    return "TemplateCategory [name=" + name + ", templates=" + templates + "]";
  }

  
  

}
