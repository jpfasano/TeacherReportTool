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
    this.setFont(new Font("Serif", Font.ITALIC, 16));
    this.setLineWrap(true);
    this.setWrapStyleWord(true);
    this.setEditable(true);
    // this.setText("It's also worth pointing out that charters, while growing in Texas, serve a fraction of the students traditional public schools educate each year. There were more than 8,000 public schools in the state, and only 580 charters, as of the 2011-2012 school year (the time frame the Stanford study examined).");
    //add(ta); 
  }
  
  public void insert(String t) {
    String curText = this.getText();
    super.setText(curText+" "+t);  
    //super.append(t);
  }
  
  public String getText() {
    return super.getText();
  }

}
