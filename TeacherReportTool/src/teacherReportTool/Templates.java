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

import java.util.ArrayList;

//

public class Templates extends ArrayList<TemplateCategory> {

   public Templates() {
      super();
   }

   public int maxTemplateCategorySize() {
      int retVal = 0;
      for (TemplateCategory tc : this) {
         if (tc.getTemplates().size() > retVal)
            retVal = tc.getTemplates().size();
      }
      return retVal;
   }

}
