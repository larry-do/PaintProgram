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
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
    private BooleanBinding combineKey;
    private BooleanProperty isWheelScroll;

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

    private ToolType currentTool;

    public Controller(View v, Model m) {
        //<editor-fold defaultstate="collapsed" desc="Khởi tạo biến">
        model = m;
        view = v;

        ctrlPressed = new SimpleBooleanProperty(false);
        keySPressed = new SimpleBooleanProperty(false);
        combineKey = ctrlPressed.and(keySPressed);
        isWheelScroll = new SimpleBooleanProperty(false);

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
        colorPickerAction();
        sizeOfPenSliderAction();
        zoomActionHandler();
    }

    private void mouseActionHandler() {
        view.getPaintPane().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
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
                        default:
                            break;
                    }
                }
                //</editor-fold>
            }

        });
        view.getPaintPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
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
                        default:
                            break;
                    }
                }
                //</editor-fold>
            }

        });
        view.getPaintPane().addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
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
                }
                //</editor-fold>
            }

        });
    }

    private void keyboardActionHandler() {
        view.setSceneEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.CONTROL) {
                    ctrlPressed.set(true);
                } else if (event.getCode() == KeyCode.S) {
                    keySPressed.set(true);
                }
            }

        });
        view.setSceneEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.CONTROL) {
                    ctrlPressed.set(false);
                } else if (event.getCode() == KeyCode.S) {
                    keySPressed.set(false);
                }
            }

        });
    }

    private void sizeOfPenSliderAction() {
        view.sizeOfPenSliderAction(new ChangeListener<Number>() {
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

    private void colorPickerAction() {
        view.colorPickerAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                pencil.setColor(view.getColorOfColorPicker());
                floodFiller.setColor(view.getColorOfColorPicker());
                rectangleDrawer.setColor(view.getColorOfColorPicker());
                curveLineDrawer.setColor(view.getColorOfColorPicker());
                airbrush.setColor(view.getColorOfColorPicker());
                brush.setColor(view.getColorOfColorPicker());
                calligraphyPen.setColor(view.getColorOfColorPicker());
                markerPen.setColor(view.getColorOfColorPicker());
                ellipseDrawer.setColor(view.getColorOfColorPicker());
                isoscelesTriangleDrawer.setColor(view.getColorOfColorPicker());
                lineDrawer.setColor(view.getColorOfColorPicker());
                roundedRectangleDrawer.setColor(view.getColorOfColorPicker());
                squareTriangleDrawer.setColor(view.getColorOfColorPicker());
            }
        });
    }

    private void toggleBtnGroupAction() {
        view.toggleBtnGroupAction(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
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
                    default:
                        break;

                }

            }
        });
    }

    private void saveWithKeyBoard() {
        combineKey.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    ctrlPressed.set(false);
                    keySPressed.set(false);
                    model.writeImage(view.getRenderedImage());
                }
            }
        });
    }

    private void exitMenuAction() {
        view.exitMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (view.getPaintPane().getChildren().size() > 0) {
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
                model.writeImage(view.getRenderedImage());
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
                    if (view.getPaintPane().getChildren().size() > 0) {
                        // hỏi người dùng xem có muốn save không?
                    }
                    view.getPaintPane().setMinSize(img.getWidth(), img.getHeight());
                    view.getPaintPane().setMaxSize(img.getWidth(), img.getHeight());
                    view.getPaintPane().getChildren().add(imgView);
                }
            }
        });
    }

    private void zoomActionHandler() {
        view.setScrollPaneEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (ctrlPressed.getValue()) {
                    double zoomFactor = 1.05;
                    double deltaY = event.getDeltaY();
                    if (deltaY < 0) {
                        zoomFactor = 2.0 - zoomFactor;
                    }
                    view.getPaintPane().setScaleX(view.getPaintPane().getScaleY() * zoomFactor);
                    view.getPaintPane().setScaleY(view.getPaintPane().getScaleY() * zoomFactor);
                    event.consume(); // chặn cho scroll bar ko được chạy
                }
                
            }
        });
    }
}
