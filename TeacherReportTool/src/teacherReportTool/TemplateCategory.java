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

public class TemplateCategory {

   private String name;
   private ArrayList<String> templates;

   public TemplateCategory(String name, ArrayList<String> templates) {
      super();
      this.name = name;
      this.templates = templates;
   }

   public TemplateCategory(String name) {
      super();
      this.name = name;
      this.templates = new ArrayList<String>();
   }

   public TemplateCategory() {
      super();
      this.name = "No Category Specified";
      this.templates = new ArrayList<String>();
   }

   public void addTemplate(String t) {
      templates.add(t);
   }

   public String getName() {
      return name;
   }

   public ArrayList<String> getTemplates() {
      return templates;
   }

   boolean empty() {
      if (templates.size() == 0)
         return true;
      return false;
   }

   public String toString() {
      return "TemplateCategory [name=" + name + ", templates=" + templates + "]";
   }

}
