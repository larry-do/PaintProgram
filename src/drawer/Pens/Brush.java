/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Pens;

import drawer.PaintTool;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

/**
 *
 * @author Admin
 */
public class Brush extends PaintTool {

    private boolean isPressed;

    private AnimationTimer loop;

    private Image brush;

    public Brush(Pane panee) {

        pane = panee;

        brush = createBrush(sizeOfPen, color);

        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isPressed) {
                    strokeLineUsingBresehamAlgorithm(anchorPoint.getX(), anchorPoint.getY(), curPoint.getX(), curPoint.getY());
                }
                anchorPoint = new Point2D(curPoint.getX(), curPoint.getY());
            }

        };

        loop.start();

    }

    public void mousePressedHandling(MouseEvent event) {
        curPoint = new Point2D(event.getX(), event.getY());
        anchorPoint = new Point2D(curPoint.getX(), curPoint.getY());
        isPressed = true;
    }

    public void mouseDraggedHandling(MouseEvent event) {
        curPoint = new Point2D(event.getX(), event.getY());
        isPressed = true;
    }

    public void mouseReleasedHandling(MouseEvent event) {
        isPressed = false;
    }

    @Override
    public void setSizeOfPen(double sizeOfPen) {
        this.sizeOfPen = sizeOfPen;
        brush = createBrush(this.sizeOfPen, this.color);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        brush = createBrush(this.sizeOfPen, this.color);
    }

    private void strokeLineUsingBresehamAlgorithm(double x0, double y0, double x1, double y1) {
        double delta_x = Math.abs(x1 - x0), sx = (x0 < x1) ? 1 : -1;
        double delta_y = -Math.abs(y1 - y0), sy = (y0 < y1) ? 1 : -1;
        double err = delta_x + delta_y;
        double e2;
        while (true) {

            ImageView imgView = new ImageView(brush);
            imgView.setLayoutX(x0 - brush.getWidth() / 2);
            imgView.setLayoutY(y0 - brush.getHeight() / 2);
            //imgView.relocate(x0 - brush.getWidth() / 2, y0 - brush.getHeight() / 2);
            pane.getChildren().add(imgView);

            if (x0 == x1 && y0 == y1) {
                break;
            }
            e2 = 2 * err;
            if (e2 > delta_y) {
                err += delta_y;
                x0 += sx;
            }
            if (e2 < delta_x) {
                err += delta_x;
                y0 += sy;
            }
        }
    }

    private Image createBrush(double sizeOfPen, Color color) {
        Circle circle = new Circle(sizeOfPen / 2);
        RadialGradient gradient = new RadialGradient(0, 0, 0, 0, sizeOfPen / 2, false, CycleMethod.NO_CYCLE, new Stop(0, color.deriveColor(1, 1, 1, 0.3)), new Stop(1, color.deriveColor(1, 1, 1, 0)));
        circle.setFill(gradient);
        return createImage(circle);
    }

    private Image createImage(Circle circle) {
        WritableImage image;
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        int imageWidth = (int) circle.getBoundsInLocal().getWidth();
        int imageHeight = (int) circle.getBoundsInLocal().getHeight();
        image = new WritableImage(imageWidth, imageHeight);
        circle.snapshot(parameters, image);
        return image;
    }
}
