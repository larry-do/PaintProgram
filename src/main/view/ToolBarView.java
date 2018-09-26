/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.view;

import drawer.PaintTool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 *
 * @author Admin
 */
public class ToolBarView extends GridPane {

    private ToggleGroup toggleGroup;
    private RadioButton pencilBtn, eraserBtn, floodFillerBtn, rectangleDrawerBtn, curveLineDrawerBtn;
    private RadioButton airbrushBtn, colorPickerBtn, lineDrawerBtn;
    private Button rotateBtn;
    private ColorPicker colorChooser;
    private Slider sizeOfPenSlider;

    public ToolBarView() {
        super();

        setStyle("-fx-background-color: #cccccc");
        setStyle("-fx-border-color: gray");
        setVgap(5);
        setHgap(5);
        setPadding(new Insets(5, 5, 5, 5));

        toggleGroup = new ToggleGroup();

        pencilBtn = new RadioButton("pencil");
        pencilBtn.setSelected(true);
        pencilBtn.setToggleGroup(toggleGroup);
        pencilBtn.setUserData(PaintTool.ToolType.PENCIL);
        pencilBtn.getStyleClass().remove("radio-button");
        pencilBtn.getStyleClass().add("toggle-button");
        ImageView pencilIconView = new ImageView(new Image("icon/pencil-icon.png"));
        pencilIconView.setFitHeight(20);
        pencilIconView.setFitWidth(20);
        pencilBtn.setGraphic(pencilIconView);
        pencilBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        pencilBtn.setTooltip(new Tooltip("Pencil"));
        add(pencilBtn, 0, 0);

        eraserBtn = new RadioButton("eraser");
        eraserBtn.setToggleGroup(toggleGroup);
        eraserBtn.setUserData(PaintTool.ToolType.ERASER);
        eraserBtn.getStyleClass().remove("radio-button");
        eraserBtn.getStyleClass().add("toggle-button");
        ImageView eraserIconView = new ImageView(new Image("icon/pencil-icon.png"));
        eraserIconView.setFitHeight(20);
        eraserIconView.setFitWidth(20);
        eraserBtn.setGraphic(eraserIconView);
        eraserBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        eraserBtn.setTooltip(new Tooltip("Eraser"));
        add(eraserBtn, 1, 0);

        floodFillerBtn = new RadioButton("floodFill");
        floodFillerBtn.setToggleGroup(toggleGroup);
        floodFillerBtn.setUserData(PaintTool.ToolType.FLOOD_FILLER);
        floodFillerBtn.getStyleClass().remove("radio-button");
        floodFillerBtn.getStyleClass().add("toggle-button");
        ImageView floodFillerIconView = new ImageView(new Image("icon/pencil-icon.png"));
        floodFillerIconView.setFitHeight(20);
        floodFillerIconView.setFitWidth(20);
        floodFillerBtn.setGraphic(floodFillerIconView);
        floodFillerBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        floodFillerBtn.setTooltip(new Tooltip("Fill with color"));
        add(floodFillerBtn, 0, 1);

        colorPickerBtn = new RadioButton("colorPicker");
        colorPickerBtn.setToggleGroup(toggleGroup);
        colorPickerBtn.setUserData(PaintTool.ToolType.COLOR_PICKER);
        colorPickerBtn.getStyleClass().remove("radio-button");
        colorPickerBtn.getStyleClass().add("toggle-button");
        ImageView colorPickerIconView = new ImageView(new Image("icon/pencil-icon.png"));
        colorPickerIconView.setFitHeight(20);
        colorPickerIconView.setFitWidth(20);
        colorPickerBtn.setGraphic(colorPickerIconView);
        colorPickerBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        colorPickerBtn.setTooltip(new Tooltip("Color Picker"));
        add(colorPickerBtn, 1, 1);

        lineDrawerBtn = new RadioButton("line");
        lineDrawerBtn.setToggleGroup(toggleGroup);
        lineDrawerBtn.setUserData(PaintTool.ToolType.LINE);
        lineDrawerBtn.getStyleClass().remove("radio-button");
        lineDrawerBtn.getStyleClass().add("toggle-button");
        ImageView lineDrawerIconView = new ImageView(new Image("icon/pencil-icon.png"));
        lineDrawerIconView.setFitHeight(20);
        lineDrawerIconView.setFitWidth(20);
        lineDrawerBtn.setGraphic(lineDrawerIconView);
        lineDrawerBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        lineDrawerBtn.setTooltip(new Tooltip("Line"));
        add(lineDrawerBtn, 0, 2);

        curveLineDrawerBtn = new RadioButton("curveLine");
        curveLineDrawerBtn.setToggleGroup(toggleGroup);
        curveLineDrawerBtn.setUserData(PaintTool.ToolType.CURVE_LINE);
        curveLineDrawerBtn.getStyleClass().remove("radio-button");
        curveLineDrawerBtn.getStyleClass().add("toggle-button");
        ImageView curveLineDrawerIconView = new ImageView(new Image("icon/pencil-icon.png"));
        curveLineDrawerIconView.setFitHeight(20);
        curveLineDrawerIconView.setFitWidth(20);
        curveLineDrawerBtn.setGraphic(curveLineDrawerIconView);
        curveLineDrawerBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        curveLineDrawerBtn.setTooltip(new Tooltip("Curve Line"));
        add(curveLineDrawerBtn, 1, 2);

        colorChooser = new ColorPicker(Color.BLACK);
        colorChooser.getStyleClass().add("button");
        Tooltip colorPickerTooltip = new Tooltip(colorChooser.getValue().toString());
        colorChooser.setTooltip(colorPickerTooltip);
        colorChooser.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                colorPickerTooltip.setText(newValue.toString());
            }
        });
        add(colorChooser, 0, 5, 2, 1);

        sizeOfPenSlider = new Slider();
        sizeOfPenSlider.setMax(30);
        sizeOfPenSlider.setMin(1);
        sizeOfPenSlider.setValue(1);
        Tooltip sizeTooltip = new Tooltip(Integer.toString((int) sizeOfPenSlider.getValue()));
        sizeOfPenSlider.setTooltip(sizeTooltip);
        addListenerInSlider(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sizeTooltip.setText(Integer.toString(newValue.intValue()));
            }
        });
        add(sizeOfPenSlider, 0, 4, 2, 1);
    }

    public void addListenerInToggleGroup(ChangeListener<Toggle> listener) {
        toggleGroup.selectedToggleProperty().addListener(listener);
    }

    public void addListenerInColorChooser(ChangeListener<Color> listener) {
        colorChooser.valueProperty().addListener(listener);
    }

    public void addListenerInSlider(ChangeListener<Number> listener) {
        sizeOfPenSlider.valueProperty().addListener(listener);
    }

    public void setColorInColorChooser(Color color) {
        colorChooser.setValue(color);
    }
}
