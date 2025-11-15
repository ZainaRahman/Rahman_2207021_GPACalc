package com.example.gpacalc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    private Stage stage;
    private Scene scene;


    public void swtichToScene2(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        Parent root = loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Calculator!!");
        stage.setScene(scene);
        stage.show();
    }



}
