package drawer;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class AreaPane extends Pane {

    public enum HoverState {
        NULL, UL, UM, UR, MR, BR, BM, BL, ML, MOVING
    };

    class ResizeDot extends Rectangle {

        public final int WIDTH = 6;
        public final int HEIGHT = 6;
        private final Color borderColor = Color.BLACK;
        private final Color insideColor = Color.WHITE;
        private final int strokeWidth = 1;

        public ResizeDot() {
            super();
            setWidth(WIDTH);
            setHeight(HEIGHT);
            setStroke(borderColor);
            setFill(insideColor);
            setStrokeWidth(strokeWidth);
        }

        public void setPosition(double x, double y) {
            setX(x - WIDTH / 2);
            setY(y - HEIGHT / 2);
        }
    }

    private Rectangle borderRect1;
    private Rectangle borderRect2;
    private ResizeDot dotUL, dotUM, dotUR, dotMR, dotBR, dotBM, dotBL, dotML;

    private boolean activeState;
    private boolean isMoving;
    private EventHandler<MouseEvent> hoverEventHandler;
    private Point2D anchorPoint, anchorPoint_2, startPoint;
    private HoverState state;

    public AreaPane(double x, double y) {
        super();
        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(0, 0);
        setStyle("-fx-background-color: transparent");

        borderRect2 = new Rectangle(0, 0, 0, 0);
        borderRect2.setStroke(Color.WHITE);
        borderRect2.setFill(Color.TRANSPARENT);
        borderRect2.setStrokeLineCap(StrokeLineCap.ROUND);
        borderRect2.setStrokeWidth(2);

        borderRect1 = new Rectangle(0, 0, 0, 0);
        borderRect1.setStroke(Color.valueOf("blue"));
        borderRect1.setFill(Color.TRANSPARENT);
        borderRect1.setStrokeLineCap(StrokeLineCap.ROUND);
        borderRect1.setStrokeWidth(2);
        borderRect1.getStrokeDashArray().addAll(5d);

        dotUL = new ResizeDot();
        dotUL.setPosition(dotUL.WIDTH / 2, dotUL.HEIGHT / 2);
        dotUM = new ResizeDot();
        dotUM.setPosition(getPrefWidth() / 2, dotUM.HEIGHT / 2);
        dotUR = new ResizeDot();
        dotUR.setPosition(getPrefWidth() - dotUR.WIDTH / 2, dotUR.HEIGHT / 2);
        dotMR = new ResizeDot();
        dotMR.setPosition(getPrefWidth() - dotMR.WIDTH / 2, getPrefHeight() / 2);
        dotBR = new ResizeDot();
        dotBR.setPosition(getPrefWidth() - dotBR.WIDTH / 2, getPrefHeight() - dotBR.HEIGHT / 2);
        dotBM = new ResizeDot();
        dotBM.setPosition(getPrefWidth() / 2, getPrefHeight() - dotBM.HEIGHT / 2);
        dotBL = new ResizeDot();
        dotBL.setPosition(dotBL.WIDTH / 2, getPrefHeight() - dotBL.HEIGHT / 2);
        dotML = new ResizeDot();
        dotML.setPosition(dotML.WIDTH / 2, getPrefHeight() / 2);

        hoverEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursorHoveringImage(getHoverState());
            }
        };
        anchorPoint = new Point2D(x, y);
        anchorPoint_2 = new Point2D(x, y);
        startPoint = new Point2D(x, y);
    }

    public void dragToCreate(double eventX, double eventY) {
        boolean x = eventX < anchorPoint.getX();
        boolean y = eventY < anchorPoint.getY();
        if (x == false && y == false) {
            setLayoutX(anchorPoint.getX());
            setLayoutY(anchorPoint.getY());
            setSize(eventX - getLayoutX(), eventY - getLayoutY());
        } else if (x == true && y == false) {
            setLayoutX(eventX);
            setSize(anchorPoint.getX() - getLayoutX(), eventY - getLayoutY());
        } else if (x == false && y == true) {
            setLayoutY(eventY);
            setSize(eventX - getLayoutX(), anchorPoint.getY() - getLayoutY());
        } else {
            setLayoutX(eventX);
            setLayoutY(eventY);
            setSize(anchorPoint.getX() - getLayoutX(), anchorPoint.getY() - getLayoutY());
        }
    }

    public void setAnchorPoint(double startX, double startY) {
        state = getHoverState();
        anchorPoint = new Point2D(getLayoutX(), getLayoutY());
        anchorPoint_2 = new Point2D(getLayoutX() + getPrefWidth(), getLayoutY() + getPrefHeight());
        startPoint = new Point2D(startX, startY);
    }

    public void dragToMoveAndResize(double eventX, double eventY) {
        if (null != state) {
            switch (state) {
                //<editor-fold defaultstate="collapsed" desc="Case of state">
                case MOVING:
                    setLayoutX(anchorPoint.getX() + (eventX - startPoint.getX()));
                    setLayoutY(anchorPoint.getY() + (eventY - startPoint.getY()));
                    break;
                case MR:
                    if (eventX - anchorPoint.getX() > 5) {
                        setSize(eventX - anchorPoint.getX(), getPrefHeight());
                    } else {
                        setSize(5, getPrefHeight());
                    }
                    break;
                case ML:
                    if (anchorPoint_2.getX() - eventX > 5) {
                        setLayoutX(eventX);
                        setSize(anchorPoint_2.getX() - eventX, getPrefHeight());
                    } else {
                        setLayoutX(anchorPoint_2.getX() - 5);
                        setSize(5, getPrefHeight());
                    }
                    break;
                case UM:
                    if (anchorPoint_2.getY() - eventY > 5) {
                        setLayoutY(eventY);
                        setSize(getPrefWidth(), anchorPoint_2.getY() - eventY);
                    } else {
                        setLayoutY(anchorPoint_2.getY() - 5);
                        setSize(getPrefWidth(), 5);
                    }
                    break;
                case BM:
                    if (eventY - anchorPoint.getY() > 5) {
                        setSize(getPrefWidth(), eventY - anchorPoint.getY());
                    } else {
                        setSize(getPrefWidth(), 5);
                    }
                    break;
                case UL:
                    if (anchorPoint_2.getY() - eventY > 5) {
                        setLayoutY(eventY);
                        setSize(getPrefWidth(), anchorPoint_2.getY() - eventY);
                    } else {
                        setLayoutY(anchorPoint_2.getY() - 5);
                        setSize(getPrefWidth(), 5);
                    }
                    if (anchorPoint_2.getX() - eventX > 5) {
                        setLayoutX(eventX);
                        setSize(anchorPoint_2.getX() - eventX, getPrefHeight());
                    } else {
                        setLayoutX(anchorPoint_2.getX() - 5);
                        setSize(5, getPrefHeight());
                    }
                    break;
                case UR:
                    if (anchorPoint_2.getY() - eventY > 5) {
                        setLayoutY(eventY);
                        setSize(getPrefWidth(), anchorPoint_2.getY() - eventY);
                    } else {
                        setLayoutY(anchorPoint_2.getY() - 5);
                        setSize(getPrefWidth(), 5);
                    }
                    if (eventX - anchorPoint.getX() > 5) {
                        setSize(eventX - anchorPoint.getX(), getPrefHeight());
                    } else {
                        setSize(5, getPrefHeight());
                    }
                    break;
                case BR:
                    if (eventX - anchorPoint.getX() > 5) {
                        setSize(eventX - anchorPoint.getX(), getPrefHeight());
                    } else {
                        setSize(5, getPrefHeight());
                    }
                    if (eventY - anchorPoint.getY() > 5) {
                        setSize(getPrefWidth(), eventY - anchorPoint.getY());
                    } else {
                        setSize(getPrefWidth(), 5);
                    }
                    break;
                case BL:
                    if (eventY - anchorPoint.getY() > 5) {
                        setSize(getPrefWidth(), eventY - anchorPoint.getY());
                    } else {
                        setSize(getPrefWidth(), 5);
                    }
                    if (anchorPoint_2.getX() - eventX > 5) {
                        setLayoutX(eventX);
                        setSize(anchorPoint_2.getX() - eventX, getPrefHeight());
                    } else {
                        setLayoutX(anchorPoint_2.getX() - 5);
                        setSize(5, getPrefHeight());
                    }
                    break;
                default:
                    break;
                //</editor-fold>
            }
        }
    }

    public void showBorder() {
        getChildren().addAll(borderRect2, borderRect1, dotUL, dotUM, dotUR, dotMR, dotBR, dotBM, dotBL, dotML);
    }

    public void hideBorder() {
        getChildren().removeAll(borderRect2, borderRect1, dotUL, dotUM, dotUR, dotMR, dotBR, dotBM, dotBL, dotML);
    }

    public void setMovingState(boolean state) {
        isMoving = state;
        if (isMoving == true) {
            removeEventFilter(MouseEvent.ANY, hoverEventHandler);
            setCursorHoveringImage(getHoverState());
        } else {
            addEventFilter(MouseEvent.ANY, hoverEventHandler);
        }
    }

    public boolean getMovingState() {
        return isMoving;
    }

    public void setCursorHoveringImage(HoverState state) {
        if (null != state) {
            switch (state) {
                case MOVING: {
                    if (isMoving) {
                        Image image = new Image("icon/move-cursor.png");
                        setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    } else {
                        Image image = new Image("icon/move-cursor-not.png");
                        setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    }
                    break;
                }
                case UL: {
                    Image image = new Image("icon/diagonal-cursor-2.png");
                    setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    break;
                }
                case UM: {
                    Image image = new Image("icon/vertical-cursor.png");
                    setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    break;
                }
                case UR: {
                    Image image = new Image("icon/diagonal-cursor-1.png");
                    setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    break;
                }
                case MR: {
                    Image image = new Image("icon/horizontal-cursor.png");
                    setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    break;
                }
                case BR: {
                    Image image = new Image("icon/diagonal-cursor-2.png");
                    setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    break;
                }
                case BM: {
                    Image image = new Image("icon/vertical-cursor.png");
                    setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    break;
                }
                case BL: {
                    Image image = new Image("icon/diagonal-cursor-1.png");
                    setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    break;
                }
                case ML: {
                    Image image = new Image("icon/horizontal-cursor.png");
                    setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                    break;
                }
                default:
                    setCursor(null);
                    break;
            }
        }
    }

    public void setActiveState(boolean state) {
        activeState = state;
        if (state == false) {
            removeEventFilter(MouseEvent.ANY, hoverEventHandler);
            setCursorHoveringImage(HoverState.NULL);
            hideBorder();
        } else {
            addEventFilter(MouseEvent.ANY, hoverEventHandler);
        }
    }

    public boolean getActiveState() {
        return activeState;
    }

    public void setSize(double width, double height) {
        setPrefSize(width, height);

        borderRect1.setWidth(width);
        borderRect1.setHeight(height);
        borderRect2.setWidth(width);
        borderRect2.setHeight(height);

        dotUL.setPosition(dotUL.WIDTH / 2, dotUL.HEIGHT / 2);
        dotUM.setPosition(width / 2, dotUM.HEIGHT / 2);
        dotUR.setPosition(width - dotUR.WIDTH / 2, dotUR.HEIGHT / 2);
        dotMR.setPosition(width - dotMR.WIDTH / 2, height / 2);
        dotBR.setPosition(width - dotBR.WIDTH / 2, height - dotBR.HEIGHT / 2);
        dotBM.setPosition(width / 2, height - dotBM.HEIGHT / 2);
        dotBL.setPosition(dotBL.WIDTH / 2, height - dotBL.HEIGHT / 2);
        dotML.setPosition(dotML.WIDTH / 2, height / 2);
    }

    public HoverState getHoverState() {
        // nên khai báo ntn để không phải thực hiện nhiều lần isHover()
        boolean paneHover = this.isHover();
        boolean dotULHover = dotUL.isHover();
        boolean dotUMHover = dotUM.isHover();
        boolean dotURHover = dotUR.isHover();
        boolean dotMRHover = dotMR.isHover();
        boolean dotBRHover = dotBR.isHover();
        boolean dotBMHover = dotBM.isHover();
        boolean dotBLHover = dotBL.isHover();
        boolean dotMLHover = dotML.isHover();
        if (paneHover && !dotULHover && !dotUMHover && !dotURHover && !dotMRHover
                && !dotBRHover && !dotBMHover && !dotBLHover && !dotMLHover) {
            return HoverState.MOVING;
        } else if (paneHover && dotULHover && !dotUMHover && !dotURHover && !dotMRHover
                && !dotBRHover && !dotBMHover && !dotBLHover && !dotMLHover) {
            return HoverState.UL;
        } else if (paneHover && !dotULHover && dotUMHover && !dotURHover && !dotMRHover
                && !dotBRHover && !dotBMHover && !dotBLHover && !dotMLHover) {
            return HoverState.UM;
        } else if (paneHover && !dotULHover && !dotUMHover && dotURHover && !dotMRHover
                && !dotBRHover && !dotBMHover && !dotBLHover && !dotMLHover) {
            return HoverState.UR;
        } else if (paneHover && !dotULHover && !dotUMHover && !dotURHover && dotMRHover
                && !dotBRHover && !dotBMHover && !dotBLHover && !dotMLHover) {
            return HoverState.MR;
        } else if (paneHover && !dotULHover && !dotUMHover && !dotURHover && !dotMRHover
                && dotBRHover && !dotBMHover && !dotBLHover && !dotMLHover) {
            return HoverState.BR;
        } else if (paneHover && !dotULHover && !dotUMHover && !dotURHover && !dotMRHover
                && !dotBRHover && dotBMHover && !dotBLHover && !dotMLHover) {
            return HoverState.BM;
        } else if (paneHover && !dotULHover && !dotUMHover && !dotURHover && !dotMRHover
                && !dotBRHover && !dotBMHover && dotBLHover && !dotMLHover) {
            return HoverState.BL;
        } else if (paneHover && !dotULHover && !dotUMHover && !dotURHover && !dotMRHover
                && !dotBRHover && !dotBMHover && !dotBLHover && dotMLHover) {
            return HoverState.ML;
        }
        return HoverState.NULL;
    }

    public void setBorderVisiable(boolean state) {
        borderRect2.setVisible(state);
        borderRect1.setVisible(state);
        dotUL.setVisible(state);
        dotUM.setVisible(state);
        dotUR.setVisible(state);
        dotMR.setVisible(state);
        dotBR.setVisible(state);
        dotBM.setVisible(state);
        dotBL.setVisible(state);
        dotML.setVisible(state);
    }
}
