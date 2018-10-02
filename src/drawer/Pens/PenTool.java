package drawer.Pens;

import drawer.PaintTool;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public abstract class PenTool extends PaintTool {

    protected Point2D anchorPoint, curPoint;

    public PenTool() {
        super();
        anchorPoint = new Point2D(0, 0);
        curPoint = new Point2D(0, 0);
    }

    protected Line connectPoint(double x0, double y0, double x1, double y1) {
        Line line = new Line(x0, y0, x1, y1);
        line.setStroke(color);
        line.setStrokeLineCap(strokeLineCap);
        line.setStrokeWidth(strokeWidth);
        return line;
    }
}
