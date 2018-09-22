/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Shapes;

import drawer.PaintTool;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Admin
 */
public class LineDrawer extends PaintTool {

    private Line line;

    public LineDrawer(Pane pane) {

        this.pane = pane;

    }

    public void mousePressedHandling(MouseEvent event) {
        line = new Line(event.getX(), event.getY(), event.getX(), event.getY());
        line.setStroke(color);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeWidth(sizeOfPen);
        pane.getChildren().add(line);
    }

    public void mouseDraggedHandling(MouseEvent event) {
        line.setEndX(event.getX());
        line.setEndY(event.getY());
    }
}
