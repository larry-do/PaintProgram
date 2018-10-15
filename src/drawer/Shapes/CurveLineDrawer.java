package drawer.Shapes;

import drawer.AreaPane;
import drawer.AreaPane.HoverState;
import drawer.Tool;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

public class CurveLineDrawer extends ShapeDrawer implements Tool {

    private int pressCount;

    private CubicCurve cubicCurve;

    private Point2D p0, p1; // lưu tọa độ cũ của khung

    public CurveLineDrawer() {
        super();
        pressCount = 0;
        cubicCurve = new CubicCurve();
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        if (areaPane.getHoverState() == HoverState.NULL) {
            pressCount++;
            if (pressCount == 1) {
                makeNew(event.getX(), event.getY());
                return cubicCurve;
            }
        } else {
            areaPane.setMovingState(true);
            areaPane.setAnchorPoint(event.getX(), event.getY());
        }
        return null;
    }

    @Override
    public Node mouseDraggedHandling(MouseEvent event) {
        if (areaPane.getMovingState()) {
            // lưu lại tọa độ cũ
            p0 = new Point2D(areaPane.getLayoutX(), areaPane.getLayoutY());
            p1 = new Point2D(areaPane.getLayoutX() + areaPane.getPrefWidth(), areaPane.getLayoutY() + areaPane.getPrefHeight());

            areaPane.dragToMoveAndResize(event.getX(), event.getY());

            changeSizeCubicCurve(); // thay đổi curve Line theo khung đã được thay đổi
        } else {
            changeSizeAreaPane(event.getX(), event.getY());
            switch (pressCount) {
                case 1:
                    cubicCurve.setEndX(event.getX());
                    cubicCurve.setEndY(event.getY());
                    break;
                case 2:
                    cubicCurve.setControlX2(event.getX());
                    cubicCurve.setControlY2(event.getY());
                    break;
                case 3:
                    cubicCurve.setControlX1(event.getX());
                    cubicCurve.setControlY1(event.getY());
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    @Override
    public Node mouseReleasedHandling(MouseEvent event) {
        if (pressCount >= 3) {
            areaPane.showBorder();
            areaPane.setActiveState(true);
            pressCount = 0;
            return areaPane;
        }
        areaPane.setMovingState(false);
        return null;
    }

    @Override
    public void setActiveState(boolean state) {
        if (!state) {
            pressCount = 0;
        }
        areaPane.setActiveState(state);
    }

    private void makeNew(double x, double y) {
        areaPane.setActiveState(false);
        areaPane = new AreaPane(x, y);

        cubicCurve = new CubicCurve(x, y, x, y, x, y, x, y);
        cubicCurve.setStroke(color);
        cubicCurve.setStrokeWidth(strokeWidth);
        cubicCurve.setFill(Color.TRANSPARENT);
        cubicCurve.setStrokeLineCap(StrokeLineCap.ROUND);
    }

    private void changeSizeAreaPane(double x, double y) {
        p0 = new Point2D(areaPane.getLayoutX(), areaPane.getLayoutY());
        p1 = new Point2D(areaPane.getLayoutX() + areaPane.getPrefWidth(), areaPane.getLayoutY() + areaPane.getPrefHeight());

        if (p0.getX() > x) {
            areaPane.setSize(areaPane.getPrefWidth() + p0.getX() - x, areaPane.getPrefHeight());
            areaPane.setLayoutX(x);
        }
        if (p1.getX() < x) {
            areaPane.setSize(areaPane.getPrefWidth() + x - p1.getX(), areaPane.getPrefHeight());
        }
        if (p1.getY() < y) {
            areaPane.setSize(areaPane.getPrefWidth(), areaPane.getPrefHeight() + y - p1.getY());
        }
        if (p0.getY() > y) {
            areaPane.setSize(areaPane.getPrefWidth(), areaPane.getPrefHeight() + p0.getY() - y);
            areaPane.setLayoutY(y);
        }
    }

    private void changeSizeCubicCurve() {
        cubicCurve.setStartX(areaPane.getPrefWidth() / (p1.getX() - p0.getX()) * (cubicCurve.getStartX() - p0.getX()) + areaPane.getLayoutX());
        cubicCurve.setStartY(areaPane.getPrefHeight() / (p1.getY() - p0.getY()) * (cubicCurve.getStartY() - p0.getY()) + areaPane.getLayoutY());

        cubicCurve.setEndX(areaPane.getPrefWidth() / (p1.getX() - p0.getX()) * (cubicCurve.getEndX() - p0.getX()) + areaPane.getLayoutX());
        cubicCurve.setEndY(areaPane.getPrefHeight() / (p1.getY() - p0.getY()) * (cubicCurve.getEndY() - p0.getY()) + areaPane.getLayoutY());

        cubicCurve.setControlX1(areaPane.getPrefWidth() / (p1.getX() - p0.getX()) * (cubicCurve.getControlX1() - p0.getX()) + areaPane.getLayoutX());
        cubicCurve.setControlY1(areaPane.getPrefHeight() / (p1.getY() - p0.getY()) * (cubicCurve.getControlY1() - p0.getY()) + areaPane.getLayoutY());

        cubicCurve.setControlX2(areaPane.getPrefWidth() / (p1.getX() - p0.getX()) * (cubicCurve.getControlX2() - p0.getX()) + areaPane.getLayoutX());
        cubicCurve.setControlY2(areaPane.getPrefHeight() / (p1.getY() - p0.getY()) * (cubicCurve.getControlY2() - p0.getY()) + areaPane.getLayoutY());
    }
}
