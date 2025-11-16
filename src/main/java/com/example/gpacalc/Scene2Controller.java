package com.example.gpacalc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Scene2Controller {
    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<CourseDetails> courseTable;
    @FXML
    private TableColumn<CourseDetails, String> colName;
    @FXML
    private TableColumn<CourseDetails, Double> colCredit;
    @FXML
    private TableColumn<CourseDetails, String> colGrade;
    @FXML
    private TableColumn<CourseDetails, String> colCode;

    @FXML
    private TextField nameField;
    @FXML
    private TextField creditField;
    @FXML
    private ComboBox<String> courseGrade;
    @FXML
    private TextField CodeField;
    @FXML
    private Label resultLabel;

    private ObservableList<CourseDetails> courses = FXCollections.observableArrayList();
    private final Map<String, Double> gradePoints = new HashMap<>() {{
        put("A+", 4.0);
        put("A", 3.75);
        put("A-", 3.5);
        put("B+", 3.25);
        put("B", 3.0);
        put("B-", 2.75);
        put("C+", 2.5);
        put("C", 2.0);
        put("D", 1.0);
        put("F", 0.0);
    }};

    @FXML
    public void initialize() {
        colName.setCellValueFactory(cell -> cell.getValue().nameProperty());
        colCredit.setCellValueFactory(cell -> cell.getValue().creditProperty().asObject());
        colGrade.setCellValueFactory(cell -> cell.getValue().gradeProperty());
        colCode.setCellValueFactory(cell -> cell.getValue().codeProperty());

        courseTable.setItems(courses);

        courseGrade.getItems().addAll(gradePoints.keySet());
    }

    @FXML
    private void Add() {
        String name = nameField.getText();
        String creditText = creditField.getText();
        String grade = courseGrade.getValue();
        String code =  CodeField.getText();

        if (name.isEmpty() || creditText.isEmpty() || grade == null) {
            showAlert("Please fill all fields!");
            return;
        }

        try {
            double credit = Double.parseDouble(creditText);
            courses.add(new CourseDetails(name, credit, grade,code));

            nameField.clear();
            creditField.clear();
            courseGrade.setValue(null);
            CodeField.clear();

        } catch (Exception e) {
            showAlert("Invalid credit value!");
        }
    }
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    private void DeleteSelected() {
        CourseDetails selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) courses.remove(selected);
    }

    @FXML
    private void handleCalculate() {
        if (courses.isEmpty()) {
            showAlert("No courses added!");
            return;
        }

        double totalCredits = 0;
        double totalPoints = 0;

        for (CourseDetails c : courses) {
            totalCredits += c.getCredit();
            totalPoints += c.getCredit() * gradePoints.get(c.getGrade());
        }

        double gpa = totalPoints / totalCredits;
        resultLabel.setText("GPA: " + String.format("%.2f", gpa));
    }



    /*@FXML
    private void goToAward(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AwardScene.fxml"));
        Parent root = loader.load();

        AwardController awardController = loader.getController();
        awardController.setCourseData(courses);
        awardController.setGPA(resultLabel.getText());

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 500));
        stage.show();
    }*/




    public void switchToScene1(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

}
