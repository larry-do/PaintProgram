package drawer.NotClassifiedTool;

import java.util.LinkedList;
import java.util.Queue;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FloodFiller {

    private Point2D startPoint;

    private Color color;

    private PixelReader pixelReader;

    private PixelWriter pixelWriter;

    private double width, height;

    public FloodFiller() {
        color = Color.BLACK;
    }

    public Node mousePressedHandling(MouseEvent event, WritableImage image) {
        pixelReader = image.getPixelReader();
        pixelWriter = image.getPixelWriter();
        startPoint = new Point2D(event.getX(), event.getY());
        width = image.getWidth();
        height = image.getHeight();
        fillLeeAlgorithm(startPoint);
        ImageView imgView = new ImageView();
        imgView.setImage(image);
        return imgView;
    }

    private void fillLeeAlgorithm(Point2D startPoint) {

        Color startColor = pixelReader.getColor((int) startPoint.getX(), (int) startPoint.getY());
        if (startColor.equals(color)) {
            return;
        }

        pixelWriter.setColor((int) startPoint.getX(), (int) startPoint.getY(), color);
        Queue<Point2D> queue = new LinkedList<>();
        queue.add(startPoint);

        Point2D s, t;

        while (queue.size() > 0) {

            s = queue.remove();

            t = new Point2D(s.getX() + 1, s.getY());
            if (t.getX() < width && t.getX() >= 0 && t.getY() < height && t.getY() >= 0 && pixelReader.getColor((int) t.getX(), (int) t.getY()).equals(startColor)) {
                pixelWriter.setColor((int) t.getX(), (int) t.getY(), color);
                queue.add(t);
            }
            t = new Point2D(s.getX() - 1, s.getY());
            if (t.getX() < width && t.getX() >= 0 && t.getY() < height && t.getY() >= 0 && pixelReader.getColor((int) t.getX(), (int) t.getY()).equals(startColor)) {
                pixelWriter.setColor((int) t.getX(), (int) t.getY(), color);
                queue.add(t);
            }
            t = new Point2D(s.getX(), s.getY() + 1);
            if (t.getX() < width && t.getX() >= 0 && t.getY() < height && t.getY() >= 0 && pixelReader.getColor((int) t.getX(), (int) t.getY()).equals(startColor)) {
                pixelWriter.setColor((int) t.getX(), (int) t.getY(), color);
                queue.add(t);
            }
            t = new Point2D(s.getX(), s.getY() - 1);
            if (t.getX() < width && t.getX() >= 0 && t.getY() < height && t.getY() >= 0 && pixelReader.getColor((int) t.getX(), (int) t.getY()).equals(startColor)) {
                pixelWriter.setColor((int) t.getX(), (int) t.getY(), color);
                queue.add(t);
            }
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
