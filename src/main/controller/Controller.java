package main.controller;

import drawer.NotClassifiedTool.ColorPicker;
import drawer.NotClassifiedTool.FloodFiller;
import drawer.NotClassifiedTool.ImageInsertion;
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

public class Controller {

    //<editor-fold defaultstate="opened" desc="Variables">
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

    private ImageInsertion imageInsertion;

    private ToolType currentTool, lastTool;

    private Stack<WritableImage> undoStack, redoStack;
    //</editor-fold>

    public Controller(View v, Model m) {

        //<editor-fold defaultstate="opened" desc="Initialize Variables">
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

        imageInsertion = new ImageInsertion();

        undoStack = new Stack<>();
        redoStack = new Stack<>();
//</editor-fold>

        // set default tool
        setTool(ToolType.PENCIL);

        mouseActionHandler();
        keyboardActionHandler();
        saveWithKeyBoard();
        toggleBtnGroupAction();
        colorChooserAction();
        sizeOfPenSliderAction();
        undoRedoActionHandler();
        copyAndPasteImage();
        addActionToMenuItemInMenuBar();
    }

    //<editor-fold defaultstate="opened" desc="Các thao tác với chuột">
    private void mouseActionHandler() {
        view.addEventHandlerIntoPaintPane(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isWithinPaintPane(event.getX(), event.getY())) {
                    view.showCurrentPositionOfMouseOnScreen((int) event.getX(), (int) event.getY());
                }
            }
        });
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
                case IMAGE_INSERTION:
                    imageInsertion.mousePressedHandling(event);
                    if (!imageInsertion.getActiveState()) {
                        setTool(lastTool);
                    }
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
                case IMAGE_INSERTION:
                    imageInsertion.mouseDraggedHandling(event);
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
                    Node node = curveLineDrawer.mouseReleasedHandling(event);
                    if (node != null) {
                        view.addNodeToPaintPane(node);
                    }
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
                case IMAGE_INSERTION:
                    imageInsertion.mouseReleasedHandling(event);
                    break;
                default:
                    break;
            }
            if (currentTool != ToolType.FLOOD_FILLER && currentTool != ToolType.COLOR_PICKER) {
                rectangleDrawer.setBorderVisiable(false);
                roundedRectangleDrawer.setBorderVisiable(false);
                squareTriangleDrawer.setBorderVisiable(false);
                curveLineDrawer.setBorderVisiable(false);
                imageInsertion.setBorderVisiable(false);
                updateUndoRedo();
                rectangleDrawer.setBorderVisiable(true);
                roundedRectangleDrawer.setBorderVisiable(true);
                squareTriangleDrawer.setBorderVisiable(true);
                curveLineDrawer.setBorderVisiable(true);
                imageInsertion.setBorderVisiable(true);
            }
        }
    }
    //</editor-fold>

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

    private void addActionToMenuItemInMenuBar() {
        // exit
        view.exitMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (openDialogToConfirmSaving()) {
                    view.exitAboutWindow();
                    Platform.exit();
                    System.exit(0);
                }
            }
        });
        // save as
        view.saveAsMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setOffAllTools(false);
                model.saveAs(view.getImageOfPane());
            }
        });
        // undo
        view.undoMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getUndo();
            }
        });
        // redo
        view.redoMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getRedo();
            }
        });
        // open image
        view.openImageFromOutside(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Image image = model.getImageFromFile();
                if (image != null) {
                    imageInsertion.setActiveState(false);
                    imageInsertion = new ImageInsertion();
                    imageInsertion.setImage(image);

                    if (openDialogToConfirmSaving()) {
                        view.removeAllNodePaintPane();
                        view.setSizePaintPane(imageInsertion.getPrefWidth(), imageInsertion.getPrefHeight());
                        view.addNodeToPaintPane(imageInsertion);
                        updateUndoRedo();
                        setTool(ToolType.IMAGE_INSERTION);
                    }
                }
            }
        });
        // paste
        view.pasteMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setPasteImage();
            }
        });
        // new
        view.newMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (openDialogToConfirmSaving()) {
                    view.removeAllNodePaintPane();
                    model.resetModel();
                }
            }
        });
        // save
        view.saveMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveImageUtility();
            }

        });
        // about
        view.aboutMenuAcion(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.displayAboutWindow();
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

    private void toggleBtnGroupAction() {
        view.addListenerInToggleGroup(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getUserData() != null) {
                    setTool((ToolType) newValue.getUserData());
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
                    saveImageUtility();
                }
            }
        });
    }

    private void copyAndPasteImage() {
        combineKeyCtrlAndV.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    setPasteImage();
                }
            }
        });
    }

    private void undoRedoActionHandler() {
        combineKeyCtrlAndZ.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    getUndo();
                }
            }
        });
        combineKeyCtrlAndY.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    getRedo();
                }
            }
        });
    }

    //<editor-fold defaultstate="opened" desc="Utility Function">
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

    private boolean isWithinPaintPane(double x, double y) {
        return !(x < 0 || y < 0 || x >= view.getPaintPaneWidth() || y >= view.getPaintPaneHeight());
    }

    private void setPasteImage() {
        imageInsertion.setOff();
        imageInsertion = new ImageInsertion();
        Image image = model.getImageFromClipboard();
        if (image != null) {
            imageInsertion.setImage(image);
            if (imageInsertion.getPrefHeight() > view.getPaintPaneHeight()) {
                view.setSizePaintPane(view.getPaintPaneWidth(), imageInsertion.getPrefHeight());
            }
            if (imageInsertion.getPrefWidth() > view.getPaintPaneWidth()) {
                view.setSizePaintPane(imageInsertion.getPrefWidth(), view.getPaintPaneHeight());
            }
            view.addNodeToPaintPane(imageInsertion);
            updateUndoRedo();
            setTool(ToolType.IMAGE_INSERTION);
        }
    }

    private void setTool(ToolType type) {
        if (type == currentTool) {
            return;
        }
        lastTool = currentTool;
        setOffAllTools(type == ToolType.IMAGE_INSERTION);
        switch (type) {
            case RECTANGLE:
                currentTool = ToolType.RECTANGLE;
                view.setImageOfCursorInPaintPane(new Image("icon/shape-cursor.png"), 100, 100);
                break;
            case ROUNDED_RECTANGLE:
                currentTool = ToolType.ROUNDED_RECTANGLE;
                view.setImageOfCursorInPaintPane(new Image("icon/shape-cursor.png"), 100, 100);
                break;
            case SQUARE_TRIANGLE:
                currentTool = ToolType.SQUARE_TRIANGLE;
                view.setImageOfCursorInPaintPane(new Image("icon/shape-cursor.png"), 100, 100);
                break;
            case CURVE_LINE:
                currentTool = ToolType.CURVE_LINE;
                view.setImageOfCursorInPaintPane(new Image("icon/shape-cursor.png"), 100, 100);
                break;
            case PENCIL:
                currentTool = ToolType.PENCIL;
                view.setImageOfCursorInPaintPane(new Image("icon/pencil-cursor.png"), 0, 200);
                break;
            case AIRBRUSH:
                currentTool = ToolType.AIRBRUSH;
                view.setImageOfCursorInPaintPane(new Image("icon/airbrush-cursor.png"), 180, 30);
                break;
            case BRUSH:
                currentTool = ToolType.BRUSH;
                view.setImageOfCursorInPaintPane(new Image("icon/brush-cursor.png"), 30, 30);
                break;
            case CALLIGRAPHY_PEN:
                currentTool = ToolType.CALLIGRAPHY_PEN;
                view.setImageOfCursorInPaintPane(new Image("icon/calligraphyPen-cursor.png"), 0, 200);
                break;
            case COLOR_PICKER:
                currentTool = ToolType.COLOR_PICKER;
                view.setImageOfCursorInPaintPane(new Image("icon/colorPicker-cursor.png"), 0, 200);
                break;
            case FLOOD_FILLER:
                currentTool = ToolType.FLOOD_FILLER;
                view.setImageOfCursorInPaintPane(new Image("icon/floodFiller-cursor.png"), 200, 200);
                break;
            case IMAGE_INSERTION:
                currentTool = ToolType.IMAGE_INSERTION;
                break;
            default:
                break;
        }
        if (lastTool == null) {
            lastTool = currentTool;
        }
    }

    //<editor-fold defaultstate="opened" desc="Undo Redo Utility">
    private void updateUndoRedo() {
        undoStack.add(view.getImageOfPane());
        redoStack.removeAllElements();
    }

    private void getUndo() {
        if (!undoStack.isEmpty()) {
            redoStack.add(undoStack.pop());
        }
        view.removeAllNodePaintPane();
        if (!undoStack.isEmpty()) {
            view.setSizePaintPane(undoStack.peek().getWidth(), undoStack.peek().getHeight());
            view.addNodeToPaintPane(new ImageView(undoStack.peek()));
        }
    }

    private void getRedo() {
        if (!redoStack.isEmpty()) {
            view.removeAllNodePaintPane();
            view.setSizePaintPane(redoStack.peek().getWidth(), redoStack.peek().getHeight());
            view.addNodeToPaintPane(new ImageView(redoStack.peek()));
            undoStack.add(redoStack.pop());
        }
    }

    private void resetUndoRedo() {
        undoStack.removeAllElements();
        redoStack.removeAllElements();
    }
    //</editor-fold>

    private void setOffAllTools(boolean hasSetToolJustSetImageInsertion) { // nói chung hàm này tắt hết các công cụ. chú ý là không được tắt khi vừa chọn Tool Image Insertion
        rectangleDrawer.setActiveState(false);
        rectangleDrawer = new RectangleDrawer();
        roundedRectangleDrawer.setActiveState(false);
        roundedRectangleDrawer = new RoundedRectangleDrawer();
        squareTriangleDrawer.setActiveState(false);
        squareTriangleDrawer = new SquareTriangleDrawer();
        curveLineDrawer.setActiveState(false);
        curveLineDrawer = new CurveLineDrawer();
        if (hasSetToolJustSetImageInsertion == false) {
            imageInsertion.setActiveState(false);
            imageInsertion = new ImageInsertion();
        }
    }

    private void saveImageUtility() {
        setOffAllTools(false);
        if (model.isFileEmpty()) {
            model.saveAs(view.getImageOfPane());
        } else {
            model.writeImageToFile(view.getImageOfPane());
        }
    }

    public boolean openDialogToConfirmSaving() { // trả về false nếu nhấn vào nút x exit
        if (view.isPaintPaneEmpty() == false) {
            view.showSavingConfirmationMessage();
            if (view.doUserPressYesInSavingConfirmationMessage()) {
                saveImageUtility();
                resetUndoRedo();
                return true;
            }
            if (view.doUserPressNoInSavingConfirmationMessage()) {
                resetUndoRedo();
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
    //</editor-fold>
}
