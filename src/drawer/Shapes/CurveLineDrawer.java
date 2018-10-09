/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Shapes;

import drawer.AreaPane;
import drawer.AreaPane.HoverState;
import drawer.Tool;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

/**
 *
 * @author Admin
 */
public class CurveLineDrawer extends ShapeDrawer implements Tool {

    private CubicCurve cubicCurve;

    private int pressCount;

    private Point2D[] point;

    public CurveLineDrawer() {
        super();
        pressCount = 0;
        point = new Point2D[4];
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        pressCount++;
        if (pressCount <= 3) {
            if (pressCount == 1) {
                makeNew(event.getX(), event.getY());
                return areaPane;
            }
        } else {
            if (areaPane.getHoverState() != HoverState.NULL) {
                areaPane.setMovingState(true);
                areaPane.setAnchorPoint(event.getX(), event.getY());
            } else {
                areaPane.setMovingState(false);
                pressCount = 1;
                makeNew(event.getX(), event.getY());
                return areaPane;
            }
        }
        return null;
    }

    private void makeNew(double x, double y) {
        areaPane.setActiveState(false);
        areaPane = new AreaPane(x, y);
        point[0] = new Point2D(areaPane.getLayoutX(), areaPane.getLayoutY());

        cubicCurve = new CubicCurve(0, 0, 0, 0, 0, 0, 0, 0);
        cubicCurve.setStroke(color);
        cubicCurve.setStrokeWidth(strokeWidth);
        cubicCurve.setFill(Color.TRANSPARENT);

        areaPane.getChildren().add(cubicCurve);
    }

    @Override
    public Node mouseDraggedHandling(MouseEvent event) {
        if (!areaPane.getActiveState()) {
            switch (pressCount) {
                case 1: {
                    point[1] = new Point2D(event.getX(), event.getY());
                    areaPane.dragToCreate(point[1].getX(), point[1].getY());
                    if (point[1].getX() >= point[0].getX() && point[1].getY() >= point[0].getY()) {
                        cubicCurve.setStartX(0);
                        cubicCurve.setStartY(0);
                        cubicCurve.setEndX(areaPane.getPrefWidth());
                        cubicCurve.setEndY(areaPane.getPrefHeight());
                        cubicCurve.setControlX1(0);
                        cubicCurve.setControlY1(0);
                        cubicCurve.setControlX2(0);
                        cubicCurve.setControlY2(0);
                    } else if (point[1].getX() < point[0].getX() && point[1].getY() >= point[0].getY()) {
                        cubicCurve.setStartX(areaPane.getPrefWidth());
                        cubicCurve.setStartY(0);
                        cubicCurve.setEndX(0);
                        cubicCurve.setEndY(areaPane.getPrefHeight());
                        cubicCurve.setControlX1(0);
                        cubicCurve.setControlY1(areaPane.getPrefHeight());
                        cubicCurve.setControlX2(areaPane.getPrefWidth());
                        cubicCurve.setControlY2(0);
                    } else if (point[1].getX() >= point[0].getX() && point[1].getY() < point[0].getY()) {
                        cubicCurve.setStartX(0);
                        cubicCurve.setStartY(areaPane.getPrefHeight());
                        cubicCurve.setEndX(areaPane.getPrefWidth());
                        cubicCurve.setEndY(0);
                        cubicCurve.setControlX1(0);
                        cubicCurve.setControlY1(areaPane.getPrefHeight());
                        cubicCurve.setControlX2(areaPane.getPrefWidth());
                        cubicCurve.setControlY2(0);
                    } else if (point[1].getX() < point[0].getX() && point[1].getY() < point[0].getY()) {
                        cubicCurve.setStartX(areaPane.getPrefWidth());
                        cubicCurve.setStartY(areaPane.getPrefHeight());
                        cubicCurve.setEndX(0);
                        cubicCurve.setEndY(0);
                        cubicCurve.setControlX1(0);
                        cubicCurve.setControlY1(0);
                        cubicCurve.setControlX2(0);
                        cubicCurve.setControlY2(0);
                    }
                    break;
                }
                case 2: {
                    point[2] = new Point2D(event.getX(), event.getY());
                    Point2D point0 = new Point2D(areaPane.getLayoutX(), areaPane.getLayoutY());
                    Point2D point1 = new Point2D(areaPane.getLayoutX() + areaPane.getPrefWidth(), areaPane.getLayoutY());
                    Point2D point2 = new Point2D(areaPane.getLayoutX() + areaPane.getPrefWidth(), areaPane.getLayoutY() + areaPane.getPrefHeight());
                    Point2D point3 = new Point2D(areaPane.getLayoutX(), areaPane.getLayoutY() + areaPane.getPrefHeight());
                    if (point[2].getX() < point0.getX()) {
                        areaPane.setLayoutX(point[2].getX());
                        areaPane.setSize(point1.getX() - point[2].getX(), areaPane.getPrefHeight());
                        cubicCurve.setStartX(0 + point0.getX() - point[2].getX());
                    }
                    if (point[2].getY() < point0.getY()) {
                        areaPane.setLayoutY(point[2].getY());
                        areaPane.setSize(areaPane.getPrefWidth(), point3.getY() - point[2].getY());
                        cubicCurve.setStartY(0 + point0.getY() - point[2].getY());
                    }
                    break;
                }
                case 3: {
                    point[3] = new Point2D(event.getX(), event.getY());
                    break;
                }
                default:
                    break;
            }
        }
        if (areaPane.getMovingState()) {
            areaPane.dragToMoveAndResize(event.getX(), event.getY());
            //
        }
        return null;
    }

    @Override
    public Node mouseReleasedHandling(MouseEvent event) {
        if (pressCount == 3) {
            areaPane.showBorder();
            areaPane.setActiveState(true);
        }
        areaPane.setMovingState(false);
        return null;
    }

    @Override
    public void setActiveState(boolean state) {
        pressCount = 0;
        areaPane.setActiveState(state);
    }
}
