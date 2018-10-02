/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Shapes;

import drawer.AreaPane;
import drawer.AreaPane.HoverState;
import drawer.Tool;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 *
 * @author Admin
 */
public class SquareTriangleDrawer extends ShapeDrawer implements Tool {

    private Polygon polygon;

    public SquareTriangleDrawer() {
        super();
        polygon = new Polygon(0, 0, 0, 0, 0, 0);
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        if (areaPane.getHoverState() == HoverState.NULL) {
            areaPane.setActiveState(false); // deactive old areaPane
            areaPane = new AreaPane(event.getX(), event.getY());// create a new areaPane

            polygon = new Polygon(0, 0, 0, 0, 0, 0);
            polygon.setFill(Color.TRANSPARENT);
            polygon.setStroke(color);
            polygon.setStrokeWidth(strokeWidth);
            polygon.setStrokeLineCap(strokeLineCap);

            areaPane.getChildren().add(polygon);
            areaPane.showBorder();
            return areaPane;
        } else {
            areaPane.setMovingState(true);
            areaPane.setAnchorPoint(event.getX(), event.getY());
        }
        return null;
    }

    @Override
    public Node mouseDraggedHandling(MouseEvent event) {
        if (!areaPane.getActiveState()) {
            areaPane.dragToCreate(event.getX(), event.getY());
        }
        if (areaPane.getMovingState()) {
            areaPane.dragToMoveAndResize(event.getX(), event.getY());
        }
        areaPane.getChildren().remove(polygon);
        polygon = new Polygon(0, 0, 0, areaPane.getPrefHeight(), areaPane.getPrefWidth(), areaPane.getPrefHeight());
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(color);
        polygon.setStrokeWidth(strokeWidth);
        polygon.setStrokeLineCap(strokeLineCap);
        areaPane.hideBoder();
        areaPane.getChildren().add(polygon);
        areaPane.showBorder();
        return null;
    }

    @Override
    public Node mouseReleasedHandling(MouseEvent event) {
        areaPane.setActiveState(true);
        areaPane.setMovingState(false);
        return null;
    }
}
