package drawer.Pens;

import drawer.Tool;
import java.util.Random;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

public class Airbrush extends PenTool implements Tool {

    private Random random;

    public Airbrush() {
        super();
        random = new Random();
        
        imageCursor = new ImageCursor(new Image("icon/airbrush-cursor.png"), 180, 30);
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        return sprayAirbrush(event.getX(), event.getY());
    }

    @Override
    public Node mouseDraggedHandling(MouseEvent event) {
        return sprayAirbrush(event.getX(), event.getY());
    }

    @Override
    public Node mouseReleasedHandling(MouseEvent event) {
        return null;
    }

    private Node sprayAirbrush(double x, double y) {
        // random point within a circle
        // linewidth is radius;
        // t is number of random points per time
        double a = random.nextDouble() * 2 * Math.PI;
        double r = strokeWidth / 2 * Math.sqrt(random.nextDouble()) * 2;
        Ellipse ellipse;
        ellipse = new Ellipse(x + r * Math.cos(a), y + r * Math.sin(a), 0.01, 0.01);
        ellipse.setStroke(color);
        ellipse.setStrokeLineCap(strokeLineCap);
        return ellipse;
    }
    
    public double getStrokeWidth(){
        return strokeWidth;
    }
}
