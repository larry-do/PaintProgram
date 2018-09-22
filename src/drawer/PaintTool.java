/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Admin
 */
public abstract class PaintTool {
    
    public enum ToolType {AIRBRUSH, BRUSH, CALLIGRAPHY_PEN, MARKER_PEN
                        , PENCIL, CURVE_LINE, ELLIPSE, ISOSCELES_TRIANGLE
                        , LINE, RECTANGLE, ROUND_RECTANGLE, SQUARE_TRIANGLE
                        , ERASER, FLOOD_FILLER, TEXT, NULL};

    protected Color color = Color.BLACK;
    protected double sizeOfPen = 2;
    protected StrokeLineCap strokeLineCap = StrokeLineCap.ROUND;
    protected Point2D anchorPoint = new Point2D(0, 0), curPoint = new Point2D(0, 0);
    protected Pane pane = null;
    protected Shape shape = null;

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSizeOfPen(double sizeOfPen) {
        this.sizeOfPen = sizeOfPen;
    }

    public void setStrokeLineCap(StrokeLineCap strokeLineCap) {
        this.strokeLineCap = strokeLineCap;
    }
}
