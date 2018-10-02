/*
package drawer.NotClassifiedTool;

import drawer.PaintTool;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Eraser extends PaintTool {

public Eraser(Pane pane) {
this.pane = pane;
}

public void mousePressedHandling(MouseEvent event) {
anchorPoint = new Point2D(event.getX(), event.getY());
shape = new Line(event.getX(), event.getY(), event.getX(), event.getY());
shape.setStroke(Color.WHITE);
shape.setStrokeWidth(sizeOfPen);
shape.setStrokeLineCap(StrokeLineCap.ROUND);
pane.getChildren().add(shape);
}

public void mouseDraggedHandling(MouseEvent event) {
shape = new Line(anchorPoint.getX(), anchorPoint.getY(), event.getX(), event.getY());
anchorPoint = new Point2D(event.getX(), event.getY());
shape.setStroke(Color.WHITE);
shape.setStrokeWidth(sizeOfPen);
shape.setStrokeLineCap(StrokeLineCap.ROUND);
pane.getChildren().add(shape);
}
}*/