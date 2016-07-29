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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by JP on 6/26/2016.
 */
public class HelpWindow {
    public void display() {
        Stage stage = new Stage();
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.initModality(Modality.NONE);
        stage.setTitle("Help");
        stage.setMinWidth(250);

//        Label label = new Label();
//        String buildTime = Utilities.getTime();
//        String message = "Teacher Report Tool.\n" + "Copyright (c) 2015-16 JP Fasano.\n" + "Build Time Stamp: " + buildTime + "\n"
//                + "Java source code is available on GitHub.com";
//        label.setText(message);

        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();


        //webEngine.load(getClass().getResource("../../TeacherReportTool/resources/helpText.html").toExternalForm());
        ClassLoader classLoader = (new GenderWordPair(" ", " ")).getClass().getClassLoader();
        URL url = classLoader.getResource("resources/helpText.html");

        String urlAsString = url.toExternalForm();
        webEngine.load(urlAsString);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);

        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(scrollPane, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();

    }
}
