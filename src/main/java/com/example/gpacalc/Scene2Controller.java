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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

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
    private String resultLabel;
    @FXML
    private Button gpaButton;
    private double gpaValue;
    private double requiredCredits;
    private double currentCredits;


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

        courseTable.setEditable(true);
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colCode.setCellFactory(TextFieldTableCell.forTableColumn());
        colGrade.setCellFactory(TextFieldTableCell.forTableColumn());
        colCredit.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));


        courseGrade.getItems().addAll(gradePoints.keySet());

        colName.setOnEditCommit(event -> {
            CourseDetails c = event.getRowValue();
            c.setName(event.getNewValue());
        });

        colCode.setOnEditCommit(event -> {
            CourseDetails c = event.getRowValue();
            c.setCode(event.getNewValue());
        });

        colGrade.setOnEditCommit(event -> {
            CourseDetails c = event.getRowValue();
            c.setGrade(event.getNewValue());
        });

        colCredit.setOnEditCommit(event -> {
            CourseDetails c = event.getRowValue();
            c.setCredit(event.getNewValue());
        });
        requiredCreditField.textProperty().addListener((obs, oldVal, newVal) -> check());


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
            check();

        } catch (Exception e) {
            showAlert("Invalid credit value!");
        }

    }

    @FXML
    private void editSelected() {
        CourseDetails selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No row selected!");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected.getTeacher1());
        dialog.setHeaderText("Edit teacher1: " + selected.getTeacher1());
        dialog.setContentText("Enter new teacher1 name:");

        dialog.showAndWait().ifPresent(newTeacher1 -> {
            selected.setTeacher1(newTeacher1);

        });

        TextInputDialog dialog1 = new TextInputDialog(selected.getTeacher2());
        dialog1.setHeaderText("Edit teacher2: " + selected.getTeacher2());
        dialog1.setContentText("Enter new teacher2 name:");

        dialog1.showAndWait().ifPresent(newTeacher2 -> {
            selected.setTeacher2(newTeacher2);

        });


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

    private void check(){
        try {
            requiredCredits = Double.parseDouble(requiredCreditField.getText());
        } catch (NumberFormatException e) {
            gpaButton.setDisable(true);
            return;
        }

        currentCredits = courses.stream().mapToDouble(CourseDetails::getCredit).sum();
        gpaButton.setDisable(currentCredits != requiredCredits);
    }




    private void handleCalculate() {
        if (requiredCreditField.getText().isEmpty()) {
            showAlert("Please enter required total credit.");

            return;
        }


        if (currentCredits != requiredCredits) {
            showAlert("Total credits do not match!\nRequired: " + requiredCredits + " | Current: " + currentCredits);

            return;
        }

        double totalPoints = courses.stream()
                .mapToDouble(c -> c.getCredit() * gradePoints.get(c.getGrade()))
                .sum();

        gpaValue = totalPoints / requiredCredits;

    }


    @FXML
    private void handleClear() {
        courses.clear();

    }



    @FXML
    public void openAwardPage(ActionEvent event) throws IOException {

        handleCalculate();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AwardScene.fxml"));
        Parent root = loader.load();

        AwardController ac = loader.getController();

        double totalCredit = courses.stream().mapToDouble(CourseDetails::getCredit).sum();

        ac.loadData(
                courses,
                totalCredit,
                String.valueOf(gpaValue)
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
