package drawer.Pens;

import drawer.Tool;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Pencil extends PenTool implements Tool {

    private Line line;

    public Pencil() {
        super();
        line = new Line(0, 0, 0, 0);
        this.strokeLineCap = StrokeLineCap.SQUARE;
        
        imageCursor = new ImageCursor(new Image("icon/pencil-cursor.png"), 0, 200);
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
