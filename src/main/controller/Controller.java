/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller;

import drawer.NotClassifiedTool.*;
import drawer.PaintTool.ToolType;
import drawer.Pens.*;
import drawer.Shapes.*;
import java.util.Stack;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
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
    private BooleanBinding combineKeyCtrlAndZ;
    private BooleanBinding combineKeyCtrlAndY;
    private BooleanBinding combineKeyCtrlAndS;

    private Pencil pencil;
    private Eraser eraser;
    private FloodFiller floodFiller;
    private RectangleDrawer rectangleDrawer;
    private CurveLineDrawer curveLineDrawer;
    private Airbrush airbrush;
    private Brush brush;
    private CalligraphyPen calligraphyPen;
    private MarkerPen markerPen;
    private EllipseDrawer ellipseDrawer;
    private IsoscelesTriangleDrawer isoscelesTriangleDrawer;
    private LineDrawer lineDrawer;
    private RoundedRectangleDrawer roundedRectangleDrawer;
    private SquareTriangleDrawer squareTriangleDrawer;
    private ColorPicker colorPicker;

    private ToolType currentTool;

    private Stack<WritableImage> undoStack, redoStack;

    public Controller(View v, Model m) {
        //<editor-fold defaultstate="collapsed" desc="Khởi tạo biến">
        model = m;
        view = v;

        ctrlPressed = new SimpleBooleanProperty(false);
        keySPressed = new SimpleBooleanProperty(false);
        keyZPressed = new SimpleBooleanProperty(false);
        keyYPressed = new SimpleBooleanProperty(false);
        combineKeyCtrlAndS = ctrlPressed.and(keySPressed);
        combineKeyCtrlAndZ = ctrlPressed.and(keyZPressed);
        combineKeyCtrlAndY = ctrlPressed.and(keyYPressed);

        pencil = new Pencil(view.getPaintPane());
        eraser = new Eraser(view.getPaintPane());
        floodFiller = new FloodFiller(view.getPaintPane());
        rectangleDrawer = new RectangleDrawer(view.getPaintPane());
        curveLineDrawer = new CurveLineDrawer(view.getPaintPane());
        airbrush = new Airbrush(view.getPaintPane());
        brush = new Brush(view.getPaintPane());
        calligraphyPen = new CalligraphyPen(view.getPaintPane());
        markerPen = new MarkerPen(view.getPaintPane());
        ellipseDrawer = new EllipseDrawer(view.getPaintPane());
        isoscelesTriangleDrawer = new IsoscelesTriangleDrawer(view.getPaintPane());
        lineDrawer = new LineDrawer(view.getPaintPane());
        roundedRectangleDrawer = new RoundedRectangleDrawer(view.getPaintPane());
        squareTriangleDrawer = new SquareTriangleDrawer(view.getPaintPane());
        colorPicker = new ColorPicker(view.getPaintPane());

        undoStack = new Stack<>();
        redoStack = new Stack<>();
        //</editor-fold>

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
    }

    private void mouseActionHandler() {
        view.addPaintPaneEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //<editor-fold defaultstate="collapsed" desc="Thực hiện tùy theo currentTool">
                if (null != currentTool) {
                    switch (currentTool) {
                        case PENCIL:
                            pencil.mousePressedHandling(event);
                            break;
                        case ERASER:
                            eraser.mousePressedHandling(event);
                            break;
                        case FLOOD_FILLER:
                            floodFiller.mousePressedHandling(event);
                            updateUndoRedo(); // vì t để hàm này khi chuột release nên khi dùng floodFiller sẽ bị lỗi
                            break;
                        case RECTANGLE:
                            rectangleDrawer.mousePressedHandling(event);
                            break;
                        case CURVE_LINE:
                            curveLineDrawer.mousePressedHandling(event);
                            break;
                        case AIRBRUSH:
                            airbrush.mousePressedHandling(event);
                            break;
                        case BRUSH:
                            brush.mousePressedHandling(event);
                            break;
                        case CALLIGRAPHY_PEN:
                            calligraphyPen.mousePressedHandling(event);
                        case MARKER_PEN:
                            markerPen.mousePressedHandling(event);
                            break;
                        case ELLIPSE:
                            ellipseDrawer.mousePressedHandling(event);
                            break;
                        case ISOSCELES_TRIANGLE:
                            isoscelesTriangleDrawer.mousePressedHandling(event);
                            break;
                        case LINE:
                            lineDrawer.mousePressedHandling(event);
                            break;
                        case ROUNDED_RECTANGLE:
                            roundedRectangleDrawer.mousePressedHandling(event);
                            break;
                        case SQUARE_TRIANGLE:
                            squareTriangleDrawer.mousePressedHandling(event);
                            break;
                        case COLOR_PICKER:
                            Color color = colorPicker.mousePressedHandling(event);
                            view.setColorInColorChooser(color);
                            changeColorOfTools(color);
                            break;
                        default:
                            break;
                    }
                }
                //</editor-fold>
            }

        });
        view.addPaintPaneEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //<editor-fold defaultstate="collapsed" desc="Thực hiện tùy theo currentTool">
                if (null != currentTool) {
                    switch (currentTool) {
                        case PENCIL:
                            pencil.mouseDraggedHandling(event);
                            break;
                        case ERASER:
                            eraser.mouseDraggedHandling(event);
                            break;
                        case RECTANGLE:
                            rectangleDrawer.mouseDraggedHandling(event);
                            break;
                        case CURVE_LINE:
                            curveLineDrawer.mouseDraggedHandling(event);
                            break;
                        case AIRBRUSH:
                            airbrush.mouseDraggedHandling(event);
                            break;
                        case BRUSH:
                            brush.mouseDraggedHandling(event);
                            break;
                        case CALLIGRAPHY_PEN:
                            calligraphyPen.mouseDraggedHandling(event);
                            break;
                        case MARKER_PEN:
                            markerPen.mouseDraggedHandling(event);
                            break;
                        case ELLIPSE:
                            ellipseDrawer.mouseDraggedHandling(event);
                            break;
                        case ISOSCELES_TRIANGLE:
                            isoscelesTriangleDrawer.mouseDraggedHandling(event);
                            break;
                        case LINE:
                            lineDrawer.mouseDraggedHandling(event);
                            break;
                        case ROUNDED_RECTANGLE:
                            roundedRectangleDrawer.mouseDraggedHandling(event);
                            break;
                        case SQUARE_TRIANGLE:
                            squareTriangleDrawer.mouseDraggedHandling(event);
                            break;
                        case COLOR_PICKER:
                            Color color = colorPicker.mouseDraggedHandling(event);
                            view.setColorInColorChooser(color);
                            changeColorOfTools(color);
                            break;
                        default:
                            break;
                    }
                }
                //</editor-fold>
            }

        });
        view.addPaintPaneEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //<editor-fold defaultstate="collapsed" desc="Thực hiện tùy theo currentTool">
                if (null != currentTool) {
                    switch (currentTool) {
                        case CURVE_LINE:
                            curveLineDrawer.mouseReleasedHandling(event);
                            break;
                        case AIRBRUSH:
                            airbrush.mouseReleasedHandling(event);
                            break;
                        case BRUSH:
                            brush.mouseReleasedHandling(event);
                            break;
                        default:
                            break;
                    }
                    if (currentTool != ToolType.FLOOD_FILLER) {
                        updateUndoRedo();
                    }
                }
                //</editor-fold>
            }

        });
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
                        default:
                            break;
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

    private void sizeOfPenSliderAction() {
        view.addListenerInSlider(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pencil.setSizeOfPen(newValue.doubleValue());
                eraser.setSizeOfPen(newValue.doubleValue());
                rectangleDrawer.setSizeOfPen(newValue.doubleValue());
                curveLineDrawer.setSizeOfPen(newValue.doubleValue());
                airbrush.setSizeOfPen(newValue.doubleValue());
                brush.setSizeOfPen(newValue.doubleValue());
                calligraphyPen.setSizeOfPen(newValue.doubleValue());
                markerPen.setSizeOfPen(newValue.doubleValue());
                ellipseDrawer.setSizeOfPen(newValue.doubleValue());
                isoscelesTriangleDrawer.setSizeOfPen(newValue.doubleValue());
                lineDrawer.setSizeOfPen(newValue.doubleValue());
                roundedRectangleDrawer.setSizeOfPen(newValue.doubleValue());
                squareTriangleDrawer.setSizeOfPen(newValue.doubleValue());
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
        pencil.setColor(color);
        floodFiller.setColor(color);
        rectangleDrawer.setColor(color);
        curveLineDrawer.setColor(color);
        airbrush.setColor(color);
        brush.setColor(color);
        calligraphyPen.setColor(color);
        markerPen.setColor(color);
        ellipseDrawer.setColor(color);
        isoscelesTriangleDrawer.setColor(color);
        lineDrawer.setColor(color);
        roundedRectangleDrawer.setColor(color);
        squareTriangleDrawer.setColor(color);
    }

    private void toggleBtnGroupAction() {
        view.addListenerInToggleGroup(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getUserData() != null) {
                    if (newValue.getUserData() != ToolType.CURVE_LINE) {
                        curveLineDrawer.resetPressCount();
                    }
                    switch ((ToolType) newValue.getUserData()) {
                        case PENCIL:
                            currentTool = ToolType.PENCIL;
                            break;
                        case ERASER:
                            currentTool = ToolType.ERASER;
                            break;
                        case FLOOD_FILLER:
                            currentTool = ToolType.FLOOD_FILLER;
                            break;
                        case RECTANGLE:
                            currentTool = ToolType.RECTANGLE;
                            break;
                        case CURVE_LINE:
                            currentTool = ToolType.CURVE_LINE;
                            break;
                        case AIRBRUSH:
                            currentTool = ToolType.AIRBRUSH;
                            break;
                        case BRUSH:
                            currentTool = ToolType.BRUSH;
                            break;
                        case CALLIGRAPHY_PEN:
                            currentTool = ToolType.CALLIGRAPHY_PEN;
                            break;
                        case MARKER_PEN:
                            currentTool = ToolType.MARKER_PEN;
                            break;
                        case ELLIPSE:
                            currentTool = ToolType.ELLIPSE;
                            break;
                        case ISOSCELES_TRIANGLE:
                            currentTool = ToolType.ISOSCELES_TRIANGLE;
                            break;
                        case LINE:
                            currentTool = ToolType.LINE;
                            break;
                        case ROUNDED_RECTANGLE:
                            currentTool = ToolType.ROUNDED_RECTANGLE;
                            break;
                        case SQUARE_TRIANGLE:
                            currentTool = ToolType.SQUARE_TRIANGLE;
                            break;
                        case COLOR_PICKER:
                            currentTool = ToolType.COLOR_PICKER;
                            break;
                        default:
                            break;
                    }
                }
            }
        });
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
                Image img = model.getImage();
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
}
