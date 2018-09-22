/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Pens;

import drawer.PaintTool;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 *
 * @author Admin
 */
public class Pencil extends PaintTool {

    public Pencil(Pane panee) {
        pane = panee;
    }

    public void mousePressedHandling(MouseEvent event) {
        anchorPoint = new Point2D(event.getX(), event.getY());
        connectPoint(anchorPoint.getX(), anchorPoint.getY(), event.getX(), event.getY());
    }

    public void mouseDraggedHandling(MouseEvent event) {
        connectPoint(anchorPoint.getX(), anchorPoint.getY(), event.getX(), event.getY());
        anchorPoint = new Point2D(event.getX(), event.getY());
    }

    private void connectPoint(double x0, double y0, double x1, double y1) {
        shape = new Line(x0, y0, x1, y1);
        shape.setStroke(color);
        shape.setStrokeLineCap(strokeLineCap);
        shape.setStrokeWidth(sizeOfPen);
        pane.getChildren().add(shape);
    }
}
