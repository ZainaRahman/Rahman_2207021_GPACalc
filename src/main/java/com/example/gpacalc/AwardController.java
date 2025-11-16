package com.example.gpacalc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AwardController {
    @FXML private VBox courseContainer;
    @FXML
    private Label totalCreditLabel;
    @FXML
    private Label totalGPALabel;

    public void loadData(
            java.util.List<CourseDetails> courses,
            double totalCredit,
            String calculatedGPA
    ) {
        totalCreditLabel.setText(String.valueOf(totalCredit));
        totalGPALabel.setText(calculatedGPA);

        courseContainer.getChildren().clear();

        for (CourseDetails c : courses) {

            VBox block = new VBox(8);
            block.setStyle("-fx-padding: 15; -fx-border-color: gray; -fx-background-color: #ffffff;");
            block.setSpacing(6);

            // 1. Course name, code, credit
            HBox line1 = new HBox(20);
            line1.getChildren().addAll(
                    new Label("Course Name: " + c.getName()),
                    new Label("Course Code: " + c.getCode()),
                    new Label("Course Credit: " + c.getCredit())
            );

            // 2. Teacher names
            HBox line2 = new HBox(20);
            line2.getChildren().addAll(
                    new Label("Teacher 1 Name: " + c.getTeacher1()),
                    new Label("Teacher 2 Name: " + c.getTeacher2())
            );

            // 3. Earned Grade
            HBox line3 = new HBox(20);
            line3.getChildren().add(
                    new Label("Earned Grade: " + c.getGrade())
            );

            block.getChildren().addAll(line1, line2, line3);
            courseContainer.getChildren().add(block);
        }
    }

    @FXML
    public void goBack(javafx.event.ActionEvent event) throws java.io.IOException {
        javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(
                getClass().getResource("Scene2.fxml")
        );

        javafx.stage.Stage stage = (javafx.stage.Stage)
                ((javafx.scene.Node)event.getSource()).getScene().getWindow();

        stage.setScene(new javafx.scene.Scene(root, 600, 700));
        stage.show();
    }

}
