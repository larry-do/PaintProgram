/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Pens;

import drawer.Tool;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Admin
 */
public class Pencil extends PenTool implements Tool {

    private Line line;

    public Pencil() {
        super();
        line = new Line(0, 0, 0, 0);
        this.strokeLineCap = StrokeLineCap.SQUARE;
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        anchorPoint = new Point2D(event.getX(), event.getY());
        line = connectPoint(anchorPoint.getX(), anchorPoint.getY(), event.getX(), event.getY());
        return line;
    }

    @Override
    public Node mouseDraggedHandling(MouseEvent event) {
        line = connectPoint(anchorPoint.getX(), anchorPoint.getY(), event.getX(), event.getY());
        anchorPoint = new Point2D(event.getX(), event.getY());
        return line;
    }

    @Override
    public Node mouseReleasedHandling(MouseEvent event) {
        return null;
    }
}
