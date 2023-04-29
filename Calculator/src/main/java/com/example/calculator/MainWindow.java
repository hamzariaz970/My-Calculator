/*
Hamza Riaz
414577
BSCS12-C
*/


package com.example.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindowInterface.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);

        stage.setResizable(false);
        stage.setTitle("Calculator");

        stage.show();
    }

    public void run() {

        launch();
    }
}