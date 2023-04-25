package com.k.gui;

import com.k.logic.GameFlow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("Layout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        primaryStage.setTitle("TetrisClone");
        Scene scene = new Scene(root, 400, 460);
        primaryStage.setScene(scene);
        primaryStage.show();
        new GameFlow(c);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
