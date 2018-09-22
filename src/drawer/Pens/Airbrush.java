/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Pens;

import drawer.PaintTool;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Admin
 */
public class Airbrush extends PaintTool {

    private Random random = new Random();

    private AnimationTimer loop;

    private boolean isPressed = false;

    public Airbrush(Pane pane) {

        this.pane = pane;

        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isPressed) {
                    sprayAirbrush(curPoint.getX(), curPoint.getY());
                }
            }

        };

        loop.start();

    }

    public void mousePressedHandling(MouseEvent event) {
        curPoint = new Point2D(event.getX(), event.getY());
        isPressed = true;
    }
    public void mouseDraggedHandling(MouseEvent event) {
        curPoint = new Point2D(event.getX(), event.getY());
        isPressed = true;
    }
    public void mouseReleasedHandling(MouseEvent event) {
        isPressed = false;
    }

    private void sprayAirbrush(double x, double y) {
        // random point within a circle
        // linewidth is radius;
        // t is number of random points per time
        int t = (int) sizeOfPen / 3 * 2;
        while (t-- > 0) {
            double a = random.nextDouble() * 2 * Math.PI;
            double r = sizeOfPen / 2 * Math.sqrt(random.nextDouble());

            shape = new Ellipse(x + r * Math.cos(a), y + r * Math.sin(a), 0.1, 0.1);
            shape.setStroke(color);
            shape.setStrokeLineCap(strokeLineCap);
            pane.getChildren().add(shape);
        }
    }
}
