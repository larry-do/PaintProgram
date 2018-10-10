/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.controller.Controller;
import main.model.Model;
import main.view.View;

/**
 *
 * @author Admin
 */
public class FXMain extends Application {

    private static Model model;
    private static View view;
    private static Controller controller;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new Model();
        view = new View();
        controller = new Controller(view, model);

        primaryStage.setTitle("Paint");
        primaryStage.setScene(view.getScene());
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("icon/app-icon.png"));
        primaryStage.show();
    }

}
