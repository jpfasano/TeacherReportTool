import javax.swing.JCheckBox;

public class TemplateCheckBox extends JCheckBox
{

  private String templateWoComment;
  private String comment;
  


  public TemplateCheckBox(String template)
  {
    super();
    
    // find comment character if present
    int c=template.indexOf("#");
    if (c==-1) {
      comment="";
      templateWoComment=template.trim();
    }
    else {
      comment=template.substring(0,c+1);
      templateWoComment=template.substring(c+1).trim();
    }
    
    this.setText(template);
    
  }

  public String getTemplateWoComment()
  {
    return templateWoComment;
  }

  public String getComment()
  {
    return comment;
  }
  
  public String getTemplate() {
    return getComment()+getTemplateWoComment();
  }

}
