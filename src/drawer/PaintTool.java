package drawer;

import javafx.scene.ImageCursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public abstract class PaintTool {

    protected Color color;
    protected double strokeWidth;
    protected StrokeLineCap strokeLineCap;
    
    protected ImageCursor imageCursor;

    public PaintTool() {
        color = Color.BLACK;
        strokeWidth = 1;
        strokeLineCap = StrokeLineCap.ROUND;
        imageCursor = new ImageCursor();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setStrokeLineCap(StrokeLineCap strokeLineCap) {
        this.strokeLineCap = strokeLineCap;
    }
    
    public ImageCursor getImageCursor(){
        return imageCursor;
    }
}
