
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class EditableTextPanel extends JPanel
{
  
  private TeacherReportAssistant tra;
  JTextArea ta;
  

  public EditableTextPanel(TeacherReportAssistant tra)
  {
    super();
    this.tra = tra;
    this.setMinimumSize(new Dimension(600,200));
    ta = new JTextArea();
    ta.setFont(new Font("TRUETYPE", Font.PLAIN, 16));
    ta.setLineWrap(true);
    ta.setWrapStyleWord(true);
    ta.setEditable(true);
    // this.setText("It's also worth pointing out that charters, while growing in Texas, serve a fraction of the students traditional public schools educate each year. There were more than 8,000 public schools in the state, and only 580 charters, as of the 2011-2012 school year (the time frame the Stanford study examined).");
    //add(ta); 
    
    JScrollPane scrollPane = new JScrollPane(ta);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    

    //studentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(scrollPane,gbc);

    /*
    JFrame jf = new JFrame();
    jf.setContentPane(this);
    jf.setTitle("Test Editable Text Panel");
    jf.setSize(800, 480);
    jf.setVisible(true);
    */
  
    
  }
  
  public void insert(String t) {
    int curPos = ta.getCaretPosition();
    int curTextLen = this.getText().length();
    String newText = t.trim();
    if (newText.length()==0)return;
    
    if (tra.getTraMenuBar().insertAtCursorEnabled() ) {
        if (curPos==curTextLen && curPos!=0) newText=" "+newText;
        ta.insert(newText,curPos);
    }
    else {
      if (curTextLen!=0) newText=" "+newText;
      ta.append(newText);
    }
  }
  
  public void clearEditableText()
  {
    ta.setText("");
  }
  
  public String getText() {
    return ta.getText();
  }

  public void setText(String s) {
      ta.setText(s);
  }

}
