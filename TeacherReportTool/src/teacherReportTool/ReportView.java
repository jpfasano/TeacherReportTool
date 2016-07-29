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

import javafx.scene.control.TextArea;

/**
 * Created by JP on 6/30/2016.
 */
public class ReportView extends TextArea {
    private TeacherReportTool trt_;

    public ReportView(TeacherReportTool trt) {
        super();
        trt_ = trt;
        this.setWrapText(true);
        this.setEditable(true);
    }

    public String getReport() {
        return getText();
    }

    public void insertText(String text) {
        int pos = this.getCaretPosition();
        String curText = getText();
        if (pos >= curText.length()) {
            this.appendText(text);
            return;
        }
        if (pos == 0) {
            text = text + " ";
        } else if (pos > 1 && pos < curText.length() - 1) {
            String charBeforeCaret = curText.substring(pos - 1, pos);
            if (!charBeforeCaret.equals(" "))
                text = " " + text;
        } else if (curText.length() < pos) {
            if (!curText.substring(pos, pos + 1).equals(" "))
                text = text + " ";
        }
        super.insertText(getCaretPosition(), text);
    }

    public void appendText(String text) {
        if (text.length() == 0) return;
        if (getReport().length() != 0) text = " " + text;
        super.appendText(text);
        trt_.reportChanged();
    }

    public void setReport(String text) {
        if (text.equals(getText())) return;
        //if(text.trim().length()==0) return;
        this.setText(text);
        //trt_.reportChanged();
    }


    public void selectAll() {
        super.selectAll();
    }
}
