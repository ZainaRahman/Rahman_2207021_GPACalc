package com.example.gpacalc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root, 320, 240);
        stage.setResizable(false);
        stage.setWidth(600);
        stage.setHeight(600);
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }
}
