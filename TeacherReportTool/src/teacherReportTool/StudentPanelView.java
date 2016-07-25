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

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;

/**
 * Created by JP on 6/28/2016.
 */
public class StudentPanelView extends BorderPane {
//public class StudentPanelView extends HBox {


    private Label studentNameLabel_;

    public StudentPanelView(TeacherReportTool trt) {
        //this.setAlignment(Pos.CENTER);
        //this.setPadding(new Insets(0,10,0,10));
        //this.setSpacing(10);
        studentNameLabel_ = new Label();
        studentNameLabel_.setAlignment(Pos.CENTER);
        studentNameLabel_.setText("First: File->Open Student Names. Second: File->Open Sentence Templates");
        studentNameLabel_.setTextAlignment(TextAlignment.CENTER);

        Image leftArrowImage = new Image("resources/LeftButton.png");
        Image rightArrowImage = new Image("resources/RightButton.png");

        Button leftArrowButton = new Button();
        Button rightArrowButton = new Button();
        leftArrowButton.setGraphic(new ImageView(leftArrowImage));
        rightArrowButton.setGraphic(new ImageView(rightArrowImage));

        leftArrowButton.setOnAction(e -> {
            trt.backToPriorStudent();
        });
        rightArrowButton.setOnAction(e -> {
            trt.advanceToNextStudent();
        });

        //this.getChildren().setAll(leftArrowButton,studentNameLabel_,rightArrowButton);
        this.setLeft(leftArrowButton);
        this.setCenter(studentNameLabel_);
        this.setRight(rightArrowButton);
    }

    public void updateStudentNameLabel(Student student, String suffix) {
        String sName = student.getName();
        String sGender = student.getGender();
        String labelText = sName;
        if (!sGender.trim().isEmpty())
            labelText += " gender:" + sGender + suffix;
        studentNameLabel_.setText(labelText);
    }

    public String getStudentName() {
        String label = studentNameLabel_.getText();
        return label.substring(0, label.indexOf(" gender:"));
    }
}
