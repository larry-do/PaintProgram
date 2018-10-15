package drawer.Pens;

import java.util.ArrayList;
import java.util.Collection;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class CalligraphyPen extends PenTool {

    private ArrayList<Line> list;

    public CalligraphyPen() {
        super();
        strokeLineCap = StrokeLineCap.SQUARE;
        list = new ArrayList<>();
        
        imageCursor = new ImageCursor(new Image("icon/calligraphyPen-cursor.png"), 0, 200);
    }

    public ArrayList<Line> mousePressedHandling(MouseEvent event) {
        list = new ArrayList<>();
        anchorPoint = new Point2D(event.getX(), event.getY());
        curPoint = new Point2D(event.getX(), event.getY());
        strokeLineUsingBresehamAlgorithm((int)curPoint.getX(), (int)curPoint.getY(), (int)anchorPoint.getX(), (int)anchorPoint.getY());
        return list;
    }

    public ArrayList<Line> mouseDraggedHandling(MouseEvent event) {
        list = new ArrayList<>();
        curPoint = new Point2D(event.getX(), event.getY());
        strokeLineUsingBresehamAlgorithm((int)curPoint.getX(), (int)curPoint.getY(), (int)anchorPoint.getX(), (int)anchorPoint.getY());
        anchorPoint = new Point2D(curPoint.getX(), curPoint.getY());
        return list;
    }

    public void mouseReleasedHandling(MouseEvent event) {
        list.removeAll((Collection<?>) list.clone());
    }

    private void strokeLineUsingBresehamAlgorithm(int x, int y, int x0, int y0) {
        double delta_x = Math.abs(x0 - x), delta_y = -Math.abs(y0 - y);
        double sx = (x < x0) ? 1 : -1;
        double sy = (y < y0) ? 1 : -1;
        double err = delta_x + delta_y;
        double e2;
        while (true) {
            list.add(connectPoint(x - (strokeWidth / 2), y - (strokeWidth / 2), x + (strokeWidth / 2), y + (strokeWidth / 2)));
            list.add(connectPoint(x - (strokeWidth / 2) - 1, y - (strokeWidth / 2), x + (strokeWidth / 2), y + (strokeWidth / 2) + 1));
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

    @Override
    protected Line connectPoint(double x0, double y0, double x1, double y1) {
        Line line = new Line(x0, y0, x1, y1);
        line.setStroke(color);
        line.setStrokeLineCap(strokeLineCap);
        return line;
    }
}
