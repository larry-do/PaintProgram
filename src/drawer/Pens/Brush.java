package drawer.Pens;

import drawer.Tool;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Brush extends PenTool implements Tool {

    private Line line;
    
    public Brush() {
        super();
        this.strokeLineCap = StrokeLineCap.ROUND;
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
