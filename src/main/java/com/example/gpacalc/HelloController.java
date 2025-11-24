package com.example.gpacalc;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController implements Initializable {
    private Stage stage;
    private Scene scene;

    @FXML
    private Label label;

    private SimpleStringProperty text= new SimpleStringProperty("WELCOME TO THE CALCULATIVE WORLD");
    private int index=0;




    public void swtichToScene2(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        Parent root = loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Calculator!!");
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MyTask task=new MyTask();
        Timer timer=new Timer(true);
        timer.scheduleAtFixedRate(task,10,100);
        label.textProperty().bind(text);


    }

    class MyTask extends TimerTask{

        @Override
        public void run() {
            String original= "WELCOME TO THE CALCULATIVE WORLD";
            if (index > original.length()) {
                //index = 0;
                this.cancel();
                return;
            }

            String partial = original.substring(0, index++);

            Platform.runLater(() -> {
                text.set(partial);
            });


        }
    }
}
