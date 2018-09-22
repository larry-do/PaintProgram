/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Shapes;

import drawer.PaintTool;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Admin
 */
public class SquareTriangleDrawer extends PaintTool {

    private Polygon polygon;

    public SquareTriangleDrawer(Pane pane) {
        this.pane = pane;
    }

    public void mousePressedHandling(MouseEvent event) {
        anchorPoint = new Point2D(event.getX(), event.getY());
        polygon = new Polygon(0, 0, 0, 0, 0, 0);
        polygon.setStroke(color);
        polygon.setStrokeWidth(sizeOfPen);
        polygon.setFill(Color.TRANSPARENT);
        pane.getChildren().add(polygon);
    }

    public void mouseDraggedHandling(MouseEvent event) {
        pane.getChildren().remove(polygon);
        polygon = new Polygon(event.getX(), event.getY(), anchorPoint.getX(), anchorPoint.getY(), anchorPoint.getX(), event.getY());
        polygon.setStroke(color);
        polygon.setStrokeWidth(sizeOfPen);
        polygon.setFill(Color.TRANSPARENT);
        pane.getChildren().add(polygon);
    }
}
