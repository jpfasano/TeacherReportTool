

public class GenderWordPair
{
  String maleVersion;
  String femaleVersion;

  /**
   * 
   */
  public GenderWordPair(String mv, String fm)
  {
    maleVersion = mv;
    femaleVersion = fm;
  }
  
  public String getGenderWord(String g)
  {
    if ( g.toUpperCase().equals("M") ) return maleVersion;
    else return femaleVersion;
  }

  public String toString()
  {
    return "[m=" + maleVersion + ", f="
        + femaleVersion + "]";
  }
  
  

}
