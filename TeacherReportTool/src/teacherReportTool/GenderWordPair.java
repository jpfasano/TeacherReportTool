/*******************************************************************************
 * Copyright (c) 2015 JP Fasano.
 *
 * This file is part of the TeacherReportTool.
 *
 * TeacherReportTool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TeacherReportTool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TeacherReportTool.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package teacherReportTool;


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
