package drawer.NotClassifiedTool;

import drawer.Pens.PenTool;
import drawer.Tool;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Eraser extends PenTool implements Tool {

    private Line line;

    public Eraser() {
        super();
        line = new Line(0, 0, 0, 0);
        strokeLineCap = StrokeLineCap.ROUND;
        color = Color.WHITE;

        imageCursor = new ImageCursor(new Image("icon/shape-cursor.png"), 100, 100);
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        anchorPoint = new Point2D(event.getX(), event.getY());
        line = connectPoint(event.getX(), event.getY(), event.getX(), event.getY());
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
