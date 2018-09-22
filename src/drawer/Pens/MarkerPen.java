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
public class MarkerPen extends PaintTool {

    public MarkerPen(Pane pane) {
        this.pane = pane;
    }

    public void mousePressedHandling(MouseEvent event) {
        anchorPoint = new Point2D(event.getX(), event.getY());
        strokeLineUsingBresehamAlgorithm(event.getX(), event.getY(), event.getX(), event.getY());
    }

    public void mouseDraggedHandling(MouseEvent event) {
        strokeLineUsingBresehamAlgorithm(anchorPoint.getX(), anchorPoint.getY(), event.getX(), event.getY());
        anchorPoint = new Point2D(event.getX(), event.getY());
    }

    private void strokeLineUsingBresehamAlgorithm(double x, double y, double x0, double y0) {
        double delta_x = Math.abs(x0 - x), delta_y = -Math.abs(y0 - y);
        double sx = (x < x0) ? 1 : -1;
        double sy = (y < y0) ? 1 : -1;
        double err = delta_x + delta_y;
        double e2;
        while (true) {

            connectPoint(x, y, x, y);

            if (x == x0 && y == y0) {
                break;
            }
            e2 = 2 * err;
            if (e2 > delta_y) {
                err += delta_y;
                x += sx;
            }
            if (e2 < delta_x) {
                err += delta_x;
                y += sy;
            }
        }
    }

    private void connectPoint(double x0, double y0, double x1, double y1) {
        shape = new Line(x0, y0, x1, y1);
        shape.setStroke(color);
        shape.setStrokeLineCap(strokeLineCap);
        shape.setStrokeWidth(sizeOfPen);
        shape.setOpacity(0.1); // chưa hoàn thiện
        pane.getChildren().add(shape);
    }
}
