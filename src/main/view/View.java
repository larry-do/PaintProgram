/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.view;

import drawer.PaintTool.ToolType;
import java.awt.image.RenderedImage;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 *
 * @author Admin
 */
public class View {

    private final int WIDTH = 1200;
    private final int HEIGHT = 720;

    private Scene scene;
    private Pane backgroundPane;

    private Pane paintPane;
    private Pane toolPane;

    private MenuBar menuBar;
    private Menu fileMenu, aboutMenu;
    private MenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem, aboutMenuItem;

    private ToggleGroup toggleGroup;
    private ToggleButton pencilBtn, eraserBtn, floodFillerBtn, rectangleDrawerBtn, curveLineDrawerBtn;
    private ToggleButton airbrushBtn;

    private ColorPicker colorPicker;

    private Slider sizeOfPenSlider;

    public View() {
        // khởi tạo scene, backgroundPane và primaryStage
        //<editor-fold defaultstate="collapsed" desc="Construct scene, stage, bgPane">
        backgroundPane = new Pane();
        backgroundPane.setMinSize(WIDTH, HEIGHT);
        scene = new Scene(backgroundPane, WIDTH, HEIGHT);
        //</editor-fold>
        // tạo menu bar
        //<editor-fold defaultstate="collapsed" desc="Menu Bar">
        menuBar = new MenuBar();

        fileMenu = new Menu("File");
        newMenuItem = new MenuItem("New");
        openMenuItem = new MenuItem("Open");
        saveMenuItem = new MenuItem("Save");
        saveAsMenuItem = new MenuItem("Save as...");
        exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem);

        aboutMenu = new Menu("About");
        aboutMenuItem = new MenuItem("About");
        aboutMenu.getItems().addAll(aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, aboutMenu);
        menuBar.setPrefWidth(WIDTH + 10);
        menuBar.setPrefHeight(25);

        backgroundPane.getChildren().add(menuBar);
        //</editor-fold>
        // tạo vùng vẽ
        //<editor-fold defaultstate="collapse" desc="Paint Pane">
        paintPane = new Pane();
        paintPane.setMinSize(1050 + 10, 695 + 10);
        paintPane.setMaxSize(1050 + 10, 695 + 10);
        paintPane.setStyle("-fx-background-color: white");
        paintPane.setLayoutX(150);
        paintPane.setLayoutY(25);

        backgroundPane.getChildren().add(paintPane);
        //</editor-fold>
        // tạo vùng chức năng 
        //<editor-fold defaultstate="collapse" desc="Tool Pane">
        toolPane = new VBox();
        toolPane.setMinSize(150, 695 + 10);
        toolPane.setLayoutX(0);
        toolPane.setLayoutY(25);
        toolPane.setStyle("-fx-background-color: gray");

        colorPicker = new ColorPicker(Color.BLACK);

        sizeOfPenSlider = new Slider();
        sizeOfPenSlider.setMax(30);
        sizeOfPenSlider.setMin(2);
        sizeOfPenSlider.setValue(2);

        toggleGroup = new ToggleGroup();

        pencilBtn = new ToggleButton("pencil");
        pencilBtn.setSelected(true);
        pencilBtn.setToggleGroup(toggleGroup);
        pencilBtn.setUserData(ToolType.PENCIL);

        eraserBtn = new ToggleButton("eraser");
        eraserBtn.setToggleGroup(toggleGroup);
        eraserBtn.setUserData(ToolType.ERASER);

        floodFillerBtn = new ToggleButton("floodFill");
        floodFillerBtn.setToggleGroup(toggleGroup);
        floodFillerBtn.setUserData(ToolType.FLOOD_FILLER);
        
        rectangleDrawerBtn = new ToggleButton("rectangle");
        rectangleDrawerBtn.setToggleGroup(toggleGroup);
        rectangleDrawerBtn.setUserData(ToolType.RECTANGLE);
        
        curveLineDrawerBtn = new ToggleButton("curveLine");
        curveLineDrawerBtn.setToggleGroup(toggleGroup);
        curveLineDrawerBtn.setUserData(ToolType.CURVE_LINE);
        
        airbrushBtn = new ToggleButton("airbrush");
        airbrushBtn.setToggleGroup(toggleGroup);
        airbrushBtn.setUserData(ToolType.AIRBRUSH);

        toolPane.getChildren().add(sizeOfPenSlider);
        toolPane.getChildren().add(colorPicker);
        toolPane.getChildren().add(pencilBtn);
        toolPane.getChildren().add(eraserBtn);
        toolPane.getChildren().add(floodFillerBtn);
        toolPane.getChildren().add(rectangleDrawerBtn);
        toolPane.getChildren().add(curveLineDrawerBtn);
        toolPane.getChildren().add(airbrushBtn);

        backgroundPane.getChildren().add(toolPane);
        //</editor-fold>
    }

    public void saveWithKeyboard(EventType<KeyEvent> eventType, EventHandler<KeyEvent> eventHandler) {
        scene.addEventHandler(eventType, eventHandler);
    }

    public void exitMenuAction(EventHandler<ActionEvent> eventHandler) {
        exitMenuItem.setOnAction(eventHandler);
    }

    public void saveAsMenuAction(EventHandler<ActionEvent> eventHandler) {
        saveAsMenuItem.setOnAction(eventHandler);
    }

    public void toggleBtnGroupAction(ChangeListener<Toggle> listener) {
        toggleGroup.selectedToggleProperty().addListener(listener);
    }

    public void colorPickerAction(EventHandler eventHandler) {
        colorPicker.setOnAction(eventHandler);
    }

    public void sizeOfPenSliderAction(ChangeListener<Number> listener) {
        sizeOfPenSlider.valueProperty().addListener(listener);
    }

    public RenderedImage getRenderedImage() {
        WritableImage image = new WritableImage((int) paintPane.getWidth(), (int) paintPane.getHeight());
        paintPane.snapshot(null, image);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        return renderedImage;
    }

    public void showAlertMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Error!");
        alert.setHeaderText("You got a error!");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public Color getColorOfColorPicker() {
        return colorPicker.getValue();
    }

    public Scene getScene() { // mình ko thích cái hàm này lắm
        return scene;
    }

    public Pane getPaintPane() {
        return paintPane;
    }

}
