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
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Admin
 */
public class RectangleDrawer extends PaintTool {

    private Rectangle rect;

    public RectangleDrawer(Pane pane) {
        this.pane = pane;
    }

    public void mousePressedHandling(MouseEvent event) {
        anchorPoint = new Point2D(event.getX(), event.getY());
        rect = new Rectangle(event.getX(), event.getY(), 0, 0);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(color);
        rect.setStrokeWidth(sizeOfPen);
        pane.getChildren().add(rect);
    }

    public void mouseDraggedHandling(MouseEvent event) {
        boolean x = event.getX() < anchorPoint.getX();
        boolean y = event.getY() < anchorPoint.getY();
        if (x == false && y == false) {
            rect.setX(anchorPoint.getX());
            rect.setY(anchorPoint.getY());
            rect.setWidth(event.getX() - rect.getX());
            rect.setHeight(event.getY() - rect.getY());
        } else if (x == true && y == false) {
            rect.setX(event.getX());
            rect.setWidth(anchorPoint.getX() - rect.getX());
            rect.setHeight(event.getY() - rect.getY());
        } else if (x == false && y == true) {
            rect.setY(event.getY());
            rect.setWidth(event.getX() - rect.getX());
            rect.setHeight(anchorPoint.getY() - rect.getY());
        } else {
            rect.setX(event.getX());
            rect.setY(event.getY());
            rect.setWidth(anchorPoint.getX() - rect.getX());
            rect.setHeight(anchorPoint.getY() - rect.getY());
        }
    }
}
