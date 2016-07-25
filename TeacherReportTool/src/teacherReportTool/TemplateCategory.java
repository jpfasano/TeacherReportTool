/*
 * ******************************************************************************
 *  Copyright (c) 2015-2016 JP Fasano.
 *
 *  This file is part of the TeacherReportTool.
 *
 *  TeacherReportTool is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TeacherReportTool is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with TeacherReportTool.  If not, see <http://www.gnu.org/licenses/>.
 *  ******************************************************************************
 */

package teacherReportTool;

import java.util.ArrayList;


/*
 * A TemplateCategory contains an Array of Sentence templates for a single category
 */
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

    // Templates returned contain comments
    public ArrayList<String> getTemplates() {
        return templates;
    }

    public String getComment(int i) {
        String retVal = "";
        String templateWComment = getTemplates().get(i);
        int c = templateWComment.indexOf("#");
        if (c != -1)
            retVal = templateWComment.substring(0, c).trim();
        return retVal.trim();
    }

    // Get template without comment
    public String getTemplate(int i) {
        String retVal = getTemplates().get(i);
        int c = retVal.indexOf("#");
        if (c != -1)
            retVal = retVal.substring(c + 1).trim();
        return retVal.trim();
    }


    public boolean empty() {
        return templates.size() == 0;
    }

    public String toString() {
        return "TemplateCategory [name=" + name + ", templates=" + templates + "]";
    }

}
