/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Admin
 */
public abstract class PaintTool {

    protected Color color;
    protected double strokeWidth;
    protected StrokeLineCap strokeLineCap;

    public PaintTool() {
        color = Color.BLACK;
        strokeWidth = 1;
        strokeLineCap = StrokeLineCap.ROUND;
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
}
