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

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by JP on 6/30/2016.
 */
public class TemplatesView extends TabPane {

    // 2D Array of CheckBoxes
    CheckBox[][] checkBoxes_;


    public TemplatesView(TeacherReportTool trt, Templates templates) {

        super();

        checkBoxes_ = new CheckBox[templates.size()][];

        this.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        //tabPane.setStyle("STYLE_CLASS_FLOATING");
        //TabPaneDetacher.create().makeTabsDetachable(tabPane);


        // Call applySelectedTemplates when tab is changed
        this.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            trt.applySelectedTemplates();
        }));


        for (int i = 0; i < templates.size(); i++) {
            TemplateCategory tc = templates.get(i);
            checkBoxes_[i] = new CheckBox[tc.getTemplates().size()];

            Tab tab = new Tab();
            tab.setText(tc.getName());


            ScrollPane sentences = new ScrollPane();
            sentences.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            VBox vb = new VBox();
            vb.setPadding(new Insets(0, 0, 0, 5));

            for (int j = 0; j < tc.getTemplates().size(); j++) {
                String templateWComment = tc.getTemplates().get(j);
                String comment = tc.getComment(j);
                if (comment.length() > 0) comment += ":";
                String templateWoComment = tc.getTemplate(j);

                CheckBox pick = new CheckBox();
                checkBoxes_[i][j] = pick;
                Label commentLabel = new Label(comment);
                Font f = Font.getDefault();

                commentLabel.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, Font.getDefault().getSize()));
                HBox hb1 = new HBox();
                hb1.setSpacing(5);
                hb1.getChildren().addAll(pick, commentLabel);

                Label sentTemplate = new Label(templateWoComment);
                sentTemplate.setWrapText(true);


                //HBox hb2 = new HBox();
                //hb2.setSpacing(5);
                //hb2.getChildren().addAll(hb1,sentTemplate);

                GridPane hb2 = new GridPane();
                hb2.setHgap(5);
                hb2.add(hb1, 0, 0);
                hb2.add(sentTemplate, 1, 0);


                vb.getChildren().add(hb2);


            }

            sentences.setContent(vb);
            tab.setContent(sentences);
            this.getTabs().add(tab);
        }

    }


    // Set selected tab
    public void selectTab(int i) {
        int tabNum = i;
        if (i < 0) tabNum = 0;
        if (i >= checkBoxes_.length) tabNum = checkBoxes_.length - 1;
        this.getSelectionModel().select(i);
    }

    // Get index of selected tab
    public int getSelectedTab() {
        return this.getSelectionModel().getSelectedIndex();
    }


    // Get indices of selected check boxes and un-select
    public ArrayList<Pair<Integer, Integer>> getPickedSentenceTemplates() {
        ArrayList<Pair<Integer, Integer>> retVal = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < checkBoxes_.length; i++) {
            for (int j = 0; j < checkBoxes_[i].length; j++) {
                if (checkBoxes_[i][j].isSelected()) {
                    retVal.add(new Pair<Integer, Integer>(i, j));
                    checkBoxes_[i][j].setSelected(false);
                }
            }
        }
        return retVal;
    }
}
