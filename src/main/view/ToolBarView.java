package main.view;

import drawer.Tool.ToolType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ToolBarView extends GridPane {

    private ToggleGroup toggleGroup;
    private RadioButton rectangleBtn, roundedRectangleBtn, squareTriangleBtn, curveLineBtn, lineBtn;
    private RadioButton pencilBtn, airbrushBtn, brushBtn, calligraphyBtn;
    private RadioButton colorPickerBtn, floodFillerBtn;

    private Label colorChooserLabel;
    private ColorPicker colorChooser;

    private Label sizeOfPenSliderLabel;
    private Slider sizeOfPenSlider;

    private Label currentPositionOfMouseHeader, currentPositionOfMouseContent;
    private Label currentImageSizeHeader, currentImageSizeContent;

    public ToolBarView() {
        super();

        setStyle("-fx-background-color: #cccccc");
        setStyle("-fx-border-color: gray");
        setVgap(5);
        setHgap(5);
        setPadding(new Insets(5, 5, 5, 5));

        toggleGroup = new ToggleGroup();

        rectangleBtn = new RadioButton("rectangle");
        rectangleBtn.setToggleGroup(toggleGroup);
        rectangleBtn.setUserData(ToolType.RECTANGLE);
        rectangleBtn.getStyleClass().remove("radio-button");
        rectangleBtn.getStyleClass().add("toggle-button");
        ImageView rectangleIconView = new ImageView(new Image("icon/rectangle-icon.png"));
        rectangleIconView.setFitHeight(20);
        rectangleIconView.setFitWidth(20);
        rectangleBtn.setGraphic(rectangleIconView);
        rectangleBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        rectangleBtn.setTooltip(new Tooltip("Rectangle"));
        add(rectangleBtn, 0, 0);

        roundedRectangleBtn = new RadioButton("roundedRectangle");
        roundedRectangleBtn.setToggleGroup(toggleGroup);
        roundedRectangleBtn.setUserData(ToolType.ROUNDED_RECTANGLE);
        roundedRectangleBtn.getStyleClass().remove("radio-button");
        roundedRectangleBtn.getStyleClass().add("toggle-button");
        ImageView roundedRectangleIconView = new ImageView(new Image("icon/roundedRectangle-icon.png"));
        roundedRectangleIconView.setFitHeight(20);
        roundedRectangleIconView.setFitWidth(20);
        roundedRectangleBtn.setGraphic(roundedRectangleIconView);
        roundedRectangleBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        roundedRectangleBtn.setTooltip(new Tooltip("Rounded Rectangle"));
        add(roundedRectangleBtn, 1, 0);

        squareTriangleBtn = new RadioButton("squareTriangle");
        squareTriangleBtn.setToggleGroup(toggleGroup);
        squareTriangleBtn.setUserData(ToolType.SQUARE_TRIANGLE);
        squareTriangleBtn.getStyleClass().remove("radio-button");
        squareTriangleBtn.getStyleClass().add("toggle-button");
        ImageView squareTriangleIconView = new ImageView(new Image("icon/squareTriangle-icon.png"));
        squareTriangleIconView.setFitHeight(20);
        squareTriangleIconView.setFitWidth(20);
        squareTriangleBtn.setGraphic(squareTriangleIconView);
        squareTriangleBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        squareTriangleBtn.setTooltip(new Tooltip("Square Triangle"));
        add(squareTriangleBtn, 0, 1);

        curveLineBtn = new RadioButton("curveLine");
        curveLineBtn.setToggleGroup(toggleGroup);
        curveLineBtn.setUserData(ToolType.CURVE_LINE);
        curveLineBtn.getStyleClass().remove("radio-button");
        curveLineBtn.getStyleClass().add("toggle-button");
        ImageView curveLineIconView = new ImageView(new Image("icon/curveLine-icon.png"));
        curveLineIconView.setFitHeight(20);
        curveLineIconView.setFitWidth(20);
        curveLineBtn.setGraphic(curveLineIconView);
        curveLineBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        curveLineBtn.setTooltip(new Tooltip("Curve Line"));
        add(curveLineBtn, 1, 1);
        
        lineBtn = new RadioButton("line");
        lineBtn.setToggleGroup(toggleGroup);
        lineBtn.setUserData(ToolType.LINE);
        lineBtn.getStyleClass().remove("radio-button");
        lineBtn.getStyleClass().add("toggle-button");
        ImageView lineIconView = new ImageView(new Image("icon/line-icon.png"));
        lineIconView.setFitHeight(20);
        lineIconView.setFitWidth(20);
        lineBtn.setGraphic(lineIconView);
        lineBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        lineBtn.setTooltip(new Tooltip("Straight Line"));
        add(lineBtn, 1, 1);

        pencilBtn = new RadioButton("pencil");
        pencilBtn.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(pencilBtn);
        pencilBtn.setUserData(ToolType.PENCIL);
        pencilBtn.getStyleClass().remove("radio-button");
        pencilBtn.getStyleClass().add("toggle-button");
        ImageView pencilIconView = new ImageView(new Image("icon/pencil-icon.png"));
        pencilIconView.setFitHeight(20);
        pencilIconView.setFitWidth(20);
        pencilBtn.setGraphic(pencilIconView);
        pencilBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        pencilBtn.setTooltip(new Tooltip("Pencil"));
        add(pencilBtn, 0, 2);

        airbrushBtn = new RadioButton("airbrush");
        airbrushBtn.setToggleGroup(toggleGroup);
        airbrushBtn.setUserData(ToolType.AIRBRUSH);
        airbrushBtn.getStyleClass().remove("radio-button");
        airbrushBtn.getStyleClass().add("toggle-button");
        ImageView airbrushIconView = new ImageView(new Image("icon/airbrush-icon.png"));
        airbrushIconView.setFitHeight(20);
        airbrushIconView.setFitWidth(20);
        airbrushBtn.setGraphic(airbrushIconView);
        airbrushBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        airbrushBtn.setTooltip(new Tooltip("Airbrush"));
        add(airbrushBtn, 1, 2);

        brushBtn = new RadioButton("brush");
        brushBtn.setToggleGroup(toggleGroup);
        brushBtn.setUserData(ToolType.BRUSH);
        brushBtn.getStyleClass().remove("radio-button");
        brushBtn.getStyleClass().add("toggle-button");
        ImageView brushIconView = new ImageView(new Image("icon/brush-icon.png"));
        brushIconView.setFitHeight(20);
        brushIconView.setFitWidth(20);
        brushBtn.setGraphic(brushIconView);
        brushBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        brushBtn.setTooltip(new Tooltip("Brush"));
        add(brushBtn, 0, 3);

        calligraphyBtn = new RadioButton("calligraphy");
        calligraphyBtn.setToggleGroup(toggleGroup);
        calligraphyBtn.setUserData(ToolType.CALLIGRAPHY_PEN);
        calligraphyBtn.getStyleClass().remove("radio-button");
        calligraphyBtn.getStyleClass().add("toggle-button");
        ImageView calligraphyIconView = new ImageView(new Image("icon/calligraphyPen-icon.png"));
        calligraphyIconView.setFitHeight(20);
        calligraphyIconView.setFitWidth(20);
        calligraphyBtn.setGraphic(calligraphyIconView);
        calligraphyBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        calligraphyBtn.setTooltip(new Tooltip("Calligraphy Pen"));
        add(calligraphyBtn, 1, 3);

        colorPickerBtn = new RadioButton("colorPicker");
        colorPickerBtn.setToggleGroup(toggleGroup);
        colorPickerBtn.setUserData(ToolType.COLOR_PICKER);
        colorPickerBtn.getStyleClass().remove("radio-button");
        colorPickerBtn.getStyleClass().add("toggle-button");
        ImageView colorPickerIconView = new ImageView(new Image("icon/colorPicker-icon.png"));
        colorPickerIconView.setFitHeight(20);
        colorPickerIconView.setFitWidth(20);
        colorPickerBtn.setGraphic(colorPickerIconView);
        colorPickerBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        colorPickerBtn.setTooltip(new Tooltip("Color picker"));
        add(colorPickerBtn, 0, 4);

        floodFillerBtn = new RadioButton("floodFiller");
        floodFillerBtn.setToggleGroup(toggleGroup);
        floodFillerBtn.setUserData(ToolType.FLOOD_FILLER);
        floodFillerBtn.getStyleClass().remove("radio-button");
        floodFillerBtn.getStyleClass().add("toggle-button");
        ImageView floodFillerIconView = new ImageView(new Image("icon/floodFiller-icon.png"));
        floodFillerIconView.setFitHeight(20);
        floodFillerIconView.setFitWidth(20);
        floodFillerBtn.setGraphic(floodFillerIconView);
        floodFillerBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        floodFillerBtn.setTooltip(new Tooltip("Flood Filler"));
        add(floodFillerBtn, 1, 4);

        colorChooserLabel = new Label("Color Chooser");
        add(colorChooserLabel, 0, 5, 2, 1);
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
        add(colorChooser, 0, 6, 2, 1);

        sizeOfPenSliderLabel = new Label("Pen Size");
        add(sizeOfPenSliderLabel, 0, 7, 2, 1);
        sizeOfPenSlider = new Slider();
        sizeOfPenSlider.setMax(15);
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
        add(sizeOfPenSlider, 0, 8, 2, 1);

        currentPositionOfMouseHeader = new Label("Mouse Pos");
        currentPositionOfMouseContent = new Label("0 x 0");
        add(currentPositionOfMouseHeader, 0, 9, 2, 1);
        add(currentPositionOfMouseContent, 0, 10, 2, 1);

        currentImageSizeHeader = new Label("Image Size");
        currentImageSizeContent = new Label("0 x 0");
        add(currentImageSizeHeader, 0, 11, 2, 1);
        add(currentImageSizeContent, 0, 12, 2, 1);
    }

    public void showCurrentPositionOfMouseOnScreen(int x, int y) {
        currentPositionOfMouseContent.setText(x + " x " + y);
    }

    public void showCurrentImageSizeOnScreen(int x, int y) {
        currentImageSizeContent.setText(x + " x " + y);
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
