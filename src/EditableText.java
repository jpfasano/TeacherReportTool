import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;


//public class EditableText extends JScrollPane
public class EditableText extends JTextArea
{
  //JTextArea ta = new JTextArea();

  public EditableText()
  {
    super();
    //ta = new JTextArea();
    this.setFont(new Font("TRUETYPE", Font.PLAIN, 16));
    this.setLineWrap(true);
    this.setWrapStyleWord(true);
    this.setEditable(true);
    // this.setText("It's also worth pointing out that charters, while growing in Texas, serve a fraction of the students traditional public schools educate each year. There were more than 8,000 public schools in the state, and only 580 charters, as of the 2011-2012 school year (the time frame the Stanford study examined).");
    //add(ta); 
  }
  
  public void insert(String t) {
    int curPos = getCaretPosition();
    int curTextLen = this.getText().length();
    String newText = t.trim();
    if (newText.length()==0)return;
    if (curPos==curTextLen && curPos!=0) newText=" "+newText;
    super.insert(newText,curPos);
    //String curText = this.getText();
    //String newText = (curText+" "+t).trim();
    //super.setText(newText);  
    //super.append(t);
  }
  
  public void clearEditableText()
  {
    super.setText("");
  }
  
  public String getText() {
    return super.getText();
  }

}
