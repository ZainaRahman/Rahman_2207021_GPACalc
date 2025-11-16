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
    private TextField requiredCreditField;
    @FXML
    private TextField teacherF1;
    @FXML
    private TextField teacherF2;
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



        courseGrade.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
            }
        });


        courseGrade.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
            }
        });
    }

    @FXML
    private void Add() {
        String name = nameField.getText();
        String creditText = creditField.getText();
        String grade = courseGrade.getValue();
        String code =  CodeField.getText();
        String teacher1= teacherF1.getText();
        String teacher2=teacherF2.getText();

        if (name.isEmpty() || creditText.isEmpty() || grade == null) {
            showAlert("Please fill all fields!");
            return;
        }

        try {
            double credit = Double.parseDouble(creditText);
            courses.add(new CourseDetails(name, credit, grade,code,teacher1,teacher2));

            nameField.clear();
            creditField.clear();
            courseGrade.setValue(null);
            CodeField.clear();
            teacherF1.clear();
            teacherF2.clear();

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
        if (requiredCreditField.getText().isEmpty()) {
            showAlert("Please enter required total credit.");
            return;
        }

        double requiredCredits = Double.parseDouble(requiredCreditField.getText());

        double currentCredits = courses.stream().mapToDouble(CourseDetails::getCredit).sum();

        if (currentCredits != requiredCredits) {
            showAlert("Total credits do not match!\nRequired: " + requiredCredits + " | Current: " + currentCredits);
            return;
        }

        double totalPoints = courses.stream()
                .mapToDouble(c -> c.getCredit() * gradePoints.get(c.getGrade()))
                .sum();

        double gpa = totalPoints / requiredCredits;

        resultLabel.setText("GPA: " + String.format("%.2f", gpa));
    }

    @FXML
    private void handleClear() {
        courses.clear();
        resultLabel.setText("");
    }



    @FXML
    public void openAwardPage(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AwardScene.fxml"));
        Parent root = loader.load();

        AwardController ac = loader.getController();

        double totalCredit = courses.stream().mapToDouble(CourseDetails::getCredit).sum();

        ac.loadData(
                courses,
                totalCredit,
                resultLabel.getText()
        );

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 800));
        stage.show();
    }





    public void switchToScene1(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

}
