/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.NotClassifiedTool;

import java.util.LinkedList;
import java.util.Queue;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Admin
 */
public class FloodFiller {

    private Point2D startPoint;

    private Color color = Color.BLACK;

    private WritableImage image;

    private PixelReader pixelReader;

    private PixelWriter pixelWriter;
    
    private Pane pane;

    public FloodFiller(Pane panee) {
        pane = panee;
    }

    public void mousePressedHandling(MouseEvent event) {
        image = new WritableImage((int) pane.getMinWidth(), (int) pane.getMinHeight());
        pane.snapshot(null, image);
        pixelReader = image.getPixelReader();
        pixelWriter = image.getPixelWriter();
        startPoint = new Point2D(event.getX(), event.getY());
        fillLeeAlgorithm(startPoint);
        if (pane.getChildren().size() > 0) {
            pane.getChildren().remove(0, pane.getChildren().size() - 1);
        }
        ImageView imgView = new ImageView();
        imgView.setImage(image);
        pane.getChildren().add(imgView);
    }

    private void fillLeeAlgorithm(Point2D startPoint) {

        double width = image.getWidth();
        double height = image.getHeight();

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
