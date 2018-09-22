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
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Admin
 */
public class EllipseDrawer extends PaintTool {

    private Ellipse ellipse;

    public EllipseDrawer(Pane pane) {
        this.pane = pane;
    }

    public void mousePressedHandling(MouseEvent event) {
        anchorPoint = new Point2D(event.getX(), event.getY());
        ellipse = new Ellipse(event.getX(), event.getY(), 0, 0);
        ellipse.setStroke(color);
        ellipse.setStrokeWidth(sizeOfPen);
        ellipse.setFill(Color.TRANSPARENT);
        pane.getChildren().add(ellipse);
    }

    public void mouseDraggedHandling(MouseEvent event) {
        boolean x = event.getX() < anchorPoint.getX();
        boolean y = event.getY() < anchorPoint.getY();
        if (x == false && y == false) {
            ellipse.setCenterX((event.getX() - anchorPoint.getX()) / 2 + anchorPoint.getX());
            ellipse.setCenterY((event.getY() - anchorPoint.getY()) / 2 + anchorPoint.getY());
            ellipse.setRadiusX(ellipse.getCenterX() - anchorPoint.getX());
            ellipse.setRadiusY(ellipse.getCenterY() - anchorPoint.getY());
        } else if (x == true && y == false) {
            ellipse.setCenterX(anchorPoint.getX() - (anchorPoint.getX() - event.getX()) / 2);
            ellipse.setCenterY((event.getY() - anchorPoint.getY()) / 2 + anchorPoint.getY());
            ellipse.setRadiusX(anchorPoint.getX() - ellipse.getCenterX());
            ellipse.setRadiusY(ellipse.getCenterY() - anchorPoint.getY());
        } else if (x == false && y == true) {
            ellipse.setCenterX((event.getX() - anchorPoint.getX()) / 2 + anchorPoint.getX());
            ellipse.setCenterY(anchorPoint.getY() - (anchorPoint.getY() - event.getY()) / 2);
            ellipse.setRadiusX(ellipse.getCenterX() - anchorPoint.getX());
            ellipse.setRadiusY(anchorPoint.getY() - ellipse.getCenterY());
        } else {
            ellipse.setCenterX(anchorPoint.getX() - (anchorPoint.getX() - event.getX()) / 2);
            ellipse.setCenterY(anchorPoint.getY() - (anchorPoint.getY() - event.getY()) / 2);
            ellipse.setRadiusX(anchorPoint.getX() - ellipse.getCenterX());
            ellipse.setRadiusY(anchorPoint.getY() - ellipse.getCenterY());
        }
    }
}
