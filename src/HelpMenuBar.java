/** Help for the TeacherReportAssistant game
 */

import javax.swing.JOptionPane;

public class HelpMenuBar
{
  public static void showHelp()
  {
    JOptionPane.showMessageDialog(null,
        "Make complete words (3 to 5 letters) in the bottom row.\n" +
        "Use cursor keys <- and -> to move the" +
        " falling cube left or right.\n" +
        "Use up and down cursor keys to rotate the cube.\n" +
        "Press Ctrl+F or Ctrl+S for faster or slower falling cube.\n" +
        "Press <Enter> to enter the word.\n" +
        "Press spacebar to flush the bottom row without entering.\n" +
        "Scoring:\n\n" +
        "  Entered valid word: " +
        "  3 letters -- 3 pts; 4 letters -- 6 pts;" +
        " 5 letters -- 12 pts\n" +
        "  15 points penalty for misspelled entered word\n" +
        "  3 points penalty for each flushed row\n",
        "How to Play",       // Dialog title
        JOptionPane.PLAIN_MESSAGE);
  }

  public static void showAbout()
  {
    JOptionPane.showMessageDialog(null,
        "This game serves as a case study for \"Java Methods A&AB\"\n" +
        "by Maria Litvin and Gary Litvin\n" +
        "Copyright (C) 2006 by Skylight Publishing, Andover, MA\n" +
        "ISBN 0-9727055-7-0.\n" +
        "You are allowed to copy and use this game and its code\n" +
        "for teaching and educational purposes only.\n" +
        "You are not allowed to change or remove this message.\n",
        "About",       // Dialog title
        JOptionPane.PLAIN_MESSAGE);
  }
}
