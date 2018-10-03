/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller;

import drawer.NotClassifiedTool.ColorPicker;
import drawer.NotClassifiedTool.FloodFiller;
import drawer.Pens.*;
import drawer.Shapes.*;
import drawer.Tool.ToolType;
import java.util.ArrayList;
import java.util.Stack;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import main.model.Model;
import main.view.View;

/**
 *
 * @author Admin
 */
public class Controller {

    private Model model;
    private View view;

    private BooleanProperty ctrlPressed;
    private BooleanProperty keySPressed;
    private BooleanProperty keyZPressed;
    private BooleanProperty keyYPressed;
    private BooleanProperty keyVPressed;
    private BooleanBinding combineKeyCtrlAndZ;
    private BooleanBinding combineKeyCtrlAndY;
    private BooleanBinding combineKeyCtrlAndS;
    private BooleanBinding combineKeyCtrlAndV;

    private RectangleDrawer rectangleDrawer;
    private RoundedRectangleDrawer roundedRectangleDrawer;
    private SquareTriangleDrawer squareTriangleDrawer;
    private CurveLineDrawer curveLineDrawer;
    private Pencil pencil;
    private Airbrush airbrush;
    private Brush brush;
    private CalligraphyPen calligraphyPen;
    private ColorPicker colorPicker;
    private FloodFiller floodFiller;

    private ToolType currentTool;

    private Stack<WritableImage> undoStack, redoStack;

    public Controller(View v, Model m) {
        model = m;
        view = v;

        ctrlPressed = new SimpleBooleanProperty(false);
        keySPressed = new SimpleBooleanProperty(false);
        keyZPressed = new SimpleBooleanProperty(false);
        keyYPressed = new SimpleBooleanProperty(false);
        keyVPressed = new SimpleBooleanProperty(false);
        combineKeyCtrlAndS = ctrlPressed.and(keySPressed);
        combineKeyCtrlAndZ = ctrlPressed.and(keyZPressed);
        combineKeyCtrlAndY = ctrlPressed.and(keyYPressed);
        combineKeyCtrlAndV = ctrlPressed.and(keyVPressed);

        rectangleDrawer = new RectangleDrawer();
        roundedRectangleDrawer = new RoundedRectangleDrawer();
        squareTriangleDrawer = new SquareTriangleDrawer();
        curveLineDrawer = new CurveLineDrawer();
        pencil = new Pencil();
        airbrush = new Airbrush();
        brush = new Brush();
        calligraphyPen = new CalligraphyPen();
        colorPicker = new ColorPicker();
        floodFiller = new FloodFiller();

        undoStack = new Stack<>();
        redoStack = new Stack<>();

        // set default tool
        currentTool = ToolType.PENCIL;

        mouseActionHandler();
        keyboardActionHandler();
        openImageFromOutside();
        exitMenuAction();
        saveAsMenuAction();
        saveWithKeyBoard();
        toggleBtnGroupAction();
        colorChooserAction();
        sizeOfPenSliderAction();
        undoRedoActionHandler();
        addZoomPaintPaneHandler();
        copyAndPasteImage();
    }

    private void mouseActionHandler() {
        view.addEventHandlerIntoPaintPane(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isWithinPaintPane(event.getX(), event.getY())) {
                    mousePressedHandling(event);
                }
            }

        });
        view.addEventHandlerIntoPaintPane(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isWithinPaintPane(event.getX(), event.getY())) {
                    mouseDraggedHandling(event);
                }
            }

        });
        view.addEventHandlerIntoPaintPane(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseReleasedHandling(event);
            }

        });
    }

    private void mousePressedHandling(MouseEvent event) {
        if (null != currentTool) {
            Node node;
            switch (currentTool) {
                case RECTANGLE:
                    node = rectangleDrawer.mousePressedHandling(event);
                    if (node != null) {
                        view.addNodeToPaintPane(node);
                    }
                    break;
                case ROUNDED_RECTANGLE:
                    node = roundedRectangleDrawer.mousePressedHandling(event);
                    if (node != null) {
                        view.addNodeToPaintPane(node);
                    }
                    break;
                case SQUARE_TRIANGLE:
                    node = squareTriangleDrawer.mousePressedHandling(event);
                    if (node != null) {
                        view.addNodeToPaintPane(node);
                    }
                    break;
                case CURVE_LINE:
                    node = curveLineDrawer.mousePressedHandling(event);
                    if (node != null) {
                        view.addNodeToPaintPane(node);
                    }
                    break;
                case PENCIL:
                    node = pencil.mousePressedHandling(event);
                    if (node != null) {
                        view.addNodeToPaintPane(node);
                    }
                    break;
                case AIRBRUSH:
                    double t = airbrush.getStrokeWidth() * 1.5;
                    while (t-- > 0) {
                        view.addNodeToPaintPane(airbrush.mousePressedHandling(event));
                    }
                    break;
                case BRUSH:
                    view.addNodeToPaintPane(brush.mousePressedHandling(event));
                    break;
                case CALLIGRAPHY_PEN:
                    ArrayList<Line> list = calligraphyPen.mousePressedHandling(event);
                    view.addNodeToPaintPane(list.toArray(new Line[list.size()]));
                    break;
                case COLOR_PICKER:
                    Color color = colorPicker.mousePressedHandling(event, view.getImageOfPane());
                    view.setColorInColorChooser(color);
                    changeColorOfTools(color);
                    break;
                case FLOOD_FILLER:
                    node = floodFiller.mousePressedHandling(event, view.getImageOfPane());
                    if (!view.isPaintPaneEmpty()) {
                        view.removeAllNodePaintPane();
                    }
                    view.addNodeToPaintPane(node);
                    updateUndoRedo();
                    break;
                default:
                    break;
            }
        }
    }

    private void mouseDraggedHandling(MouseEvent event) {
        if (null != currentTool) {
            Node node;
            switch (currentTool) {
                case RECTANGLE:
                    rectangleDrawer.mouseDraggedHandling(event);
                    break;
                case ROUNDED_RECTANGLE:
                    roundedRectangleDrawer.mouseDraggedHandling(event);
                    break;
                case SQUARE_TRIANGLE:
                    squareTriangleDrawer.mouseDraggedHandling(event);
                    break;
                case CURVE_LINE:
                    curveLineDrawer.mouseDraggedHandling(event);
                    break;
                case PENCIL:
                    node = pencil.mouseDraggedHandling(event);
                    if (node != null) {
                        view.addNodeToPaintPane(node);
                    }
                    break;
                case AIRBRUSH:
                    double t = airbrush.getStrokeWidth() * 1.5;
                    while (t-- > 0) {
                        view.addNodeToPaintPane(airbrush.mouseDraggedHandling(event));
                    }
                    break;
                case BRUSH:
                    view.addNodeToPaintPane(brush.mouseDraggedHandling(event));
                    break;
                case CALLIGRAPHY_PEN:
                    ArrayList<Line> list = calligraphyPen.mouseDraggedHandling(event);
                    view.addNodeToPaintPane(list.toArray(new Line[list.size()]));
                    break;
                case COLOR_PICKER:
                    Color color = colorPicker.mousePressedHandling(event, view.getImageOfPane());
                    view.setColorInColorChooser(color);
                    changeColorOfTools(color);
                    break;
                default:
                    break;
            }
        }
    }

    private void mouseReleasedHandling(MouseEvent event) {
        if (null != currentTool) {
            switch (currentTool) {
                case RECTANGLE:
                    rectangleDrawer.mouseReleasedHandling(event);
                    break;
                case ROUNDED_RECTANGLE:
                    roundedRectangleDrawer.mouseReleasedHandling(event);
                    break;
                case SQUARE_TRIANGLE:
                    squareTriangleDrawer.mouseReleasedHandling(event);
                    break;
                case CURVE_LINE:
                    curveLineDrawer.mouseReleasedHandling(event);
                    break;
                case AIRBRUSH:
                    airbrush.mouseReleasedHandling(event);
                    break;
                case BRUSH:
                    brush.mouseReleasedHandling(event);
                    break;
                case CALLIGRAPHY_PEN:
                    calligraphyPen.mouseReleasedHandling(event);
                    break;
                default:
                    break;
            }
            if (currentTool != ToolType.FLOOD_FILLER && currentTool != ToolType.COLOR_PICKER) {
                updateUndoRedo();
            }
        }
    }

    private void keyboardActionHandler() {
        view.addEventHandlerInScene(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (null != event.getCode()) {
                    switch (event.getCode()) {
                        case CONTROL:
                            ctrlPressed.set(true);
                            break;
                        case S:
                            keySPressed.set(true);
                            break;
                        case Z:
                            keyZPressed.set(true);
                            break;
                        case Y:
                            keyYPressed.set(true);
                            break;
                        case V:
                            keyVPressed.set(true);
                            break;
                        default:
                            break;
                    }
                }
            }

        });
        view.addEventHandlerInScene(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (null != event.getCode()) {
                    switch (event.getCode()) {
                        case CONTROL:
                            ctrlPressed.set(false);
                            break;
                        case S:
                            keySPressed.set(false);
                            break;
                        case Z:
                            keyZPressed.set(false);
                            break;
                        case Y:
                            keyYPressed.set(false);
                            break;
                        case V:
                            keyVPressed.set(false);
                            break;
                        default:
                            break;
                    }
                }
            }

        });
    }

    private void sizeOfPenSliderAction() {
        view.addListenerInSlider(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                rectangleDrawer.setStrokeWidth(newValue.doubleValue());
                roundedRectangleDrawer.setStrokeWidth(newValue.doubleValue());
                squareTriangleDrawer.setStrokeWidth(newValue.doubleValue());
                curveLineDrawer.setStrokeWidth(newValue.doubleValue());
                pencil.setStrokeWidth(newValue.doubleValue());
                airbrush.setStrokeWidth(newValue.doubleValue());
                brush.setStrokeWidth(newValue.doubleValue());
                calligraphyPen.setStrokeWidth(newValue.doubleValue());
            }
        });
    }

    private void colorChooserAction() {
        view.addListenerInColorChooser(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                changeColorOfTools(newValue);
            }
        });
    }

    private void changeColorOfTools(Color color) {
        rectangleDrawer.setColor(color);
        roundedRectangleDrawer.setColor(color);
        squareTriangleDrawer.setColor(color);
        curveLineDrawer.setColor(color);
        pencil.setColor(color);
        airbrush.setColor(color);
        brush.setColor(color);
        calligraphyPen.setColor(color);
        floodFiller.setColor(color);
    }

    private void toggleBtnGroupAction() {
        view.addListenerInToggleGroup(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getUserData() != null) {
                    switch ((ToolType) newValue.getUserData()) {
                        case RECTANGLE:
                            currentTool = ToolType.RECTANGLE;
                            roundedRectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/shape-cursor.png"), 100, 100);
                            break;
                        case ROUNDED_RECTANGLE:
                            currentTool = ToolType.ROUNDED_RECTANGLE;
                            rectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/shape-cursor.png"), 100, 100);
                            break;
                        case SQUARE_TRIANGLE:
                            currentTool = ToolType.SQUARE_TRIANGLE;
                            rectangleDrawer.setOff();
                            roundedRectangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/shape-cursor.png"), 100, 100);
                            break;
                        case CURVE_LINE:
                            currentTool = ToolType.CURVE_LINE;
                            rectangleDrawer.setOff();
                            roundedRectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/shape-cursor.png"), 100, 100);
                            break;
                        case PENCIL:
                            currentTool = ToolType.PENCIL;
                            rectangleDrawer.setOff();
                            roundedRectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/pencil-cursor.png"), 0, 0);
                            break;
                        case AIRBRUSH:
                            currentTool = ToolType.AIRBRUSH;
                            rectangleDrawer.setOff();
                            roundedRectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/airbrush-cursor.png"), 0, 150);
                            break;
                        case BRUSH:
                            currentTool = ToolType.BRUSH;
                            rectangleDrawer.setOff();
                            roundedRectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/brush-cursor.png"), 30, 30);
                            break;
                        case CALLIGRAPHY_PEN:
                            currentTool = ToolType.CALLIGRAPHY_PEN;
                            rectangleDrawer.setOff();
                            roundedRectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/calligraphyPen-cursor.png"), 20, 180);
                            break;
                        case COLOR_PICKER:
                            currentTool = ToolType.COLOR_PICKER;
                            rectangleDrawer.setOff();
                            roundedRectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/colorPicker-cursor.png"), 30, 30);
                            break;
                        case FLOOD_FILLER:
                            currentTool = ToolType.FLOOD_FILLER;
                            rectangleDrawer.setOff();
                            roundedRectangleDrawer.setOff();
                            squareTriangleDrawer.setOff();
                            curveLineDrawer.setOff();
                            view.setImageOfCursorInPaintPane(new Image("icon/floodFiller-cursor.png"), 0, 150);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    private boolean isWithinPaintPane(double x, double y) {
        return !(x < 0 || y < 0 || x >= view.getPaintPaneWidth() || y >= view.getPaintPaneHeight());
    }

    private void saveWithKeyBoard() {
        combineKeyCtrlAndS.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    ctrlPressed.set(false);
                    keySPressed.set(false);
                    model.writeImage(view.getImageOfPane());
                }
            }
        });
    }

    private void exitMenuAction() {
        view.exitMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (view.isPaintPaneEmpty()) {
                    // hỏi người dùng xem có muốn save không?
                }
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private void saveAsMenuAction() {
        view.saveAsMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.writeImage(view.getImageOfPane());
            }
        });
    }

    private void openImageFromOutside() {
        view.openImageFromOutside(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Image img = model.getImageFromFile();
                if (img != null) {
                    ImageView imgView = new ImageView(img);
                    if (view.isPaintPaneEmpty()) {
                        // hỏi người dùng xem có muốn save không?
                    }
                    if (img.getHeight() > view.getPaintPaneHeight()) {
                        view.setSizePaintPane(view.getPaintPaneWidth(), img.getHeight());
                    }
                    if (img.getWidth() > view.getPaintPaneWidth()) {
                        view.setSizePaintPane(img.getWidth(), view.getPaintPaneHeight());
                    }
                    view.addNodeToPaintPane(imgView);
                    updateUndoRedo();
                }
            }
        });
    }

    private void addZoomPaintPaneHandler() {
        view.addZoomableScrollPaneEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.isControlDown()) {
                    event.consume();
                    view.zoomPaintPane(event.getTextDeltaY(), new Point2D(event.getX(), event.getY()));
                }
            }
        });
    }

    private void copyAndPasteImage() {
        combineKeyCtrlAndV.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    Image img = model.getImageFromClipboard();
                    if (img != null) {
                        ImageView imgView = new ImageView(img);
                        if (view.isPaintPaneEmpty()) {
                            // hỏi người dùng xem có muốn save không?
                        }
                        if (img.getHeight() > view.getPaintPaneHeight()) {
                            view.setSizePaintPane(view.getPaintPaneWidth(), img.getHeight());
                        }
                        if (img.getWidth() > view.getPaintPaneWidth()) {
                            view.setSizePaintPane(img.getWidth(), view.getPaintPaneHeight());
                        }
                        view.addNodeToPaintPane(imgView);
                        updateUndoRedo();
                    }
                }
            }
        });
    }

    private void updateUndoRedo() {
        undoStack.add(view.getImageOfPane());
        redoStack.removeAllElements();
    }

    private void undoRedoActionHandler() {
        combineKeyCtrlAndZ.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (!undoStack.isEmpty()) {
                        redoStack.add(undoStack.pop());
                    }
                    view.removeAllNodePaintPane();
                    if (!undoStack.isEmpty()) {
                        view.setSizePaintPane(undoStack.peek().getWidth(), undoStack.peek().getHeight());
                        view.addNodeToPaintPane(new ImageView(undoStack.peek()));
                    }
                }
            }
        });
        combineKeyCtrlAndY.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (!redoStack.isEmpty()) {
                        view.removeAllNodePaintPane();
                        view.setSizePaintPane(redoStack.peek().getWidth(), redoStack.peek().getHeight());
                        view.addNodeToPaintPane(new ImageView(redoStack.peek()));
                        undoStack.add(redoStack.pop());
                    }
                }
            }
        });
    }
}
