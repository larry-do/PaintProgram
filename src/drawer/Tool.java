/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Admin
 */
public interface Tool {

    public enum ToolType {
        AIRBRUSH, BRUSH, CALLIGRAPHY_PEN, PENCIL, CURVE_LINE, ELLIPSE, ISOSCELES_TRIANGLE, LINE, RECTANGLE, ROUNDED_RECTANGLE, SQUARE_TRIANGLE, ERASER, FLOOD_FILLER, TEXT, COLOR_PICKER, IMAGE_INSERTION, NULL
    };

    public abstract Node mousePressedHandling(MouseEvent event);

    public abstract Node mouseDraggedHandling(MouseEvent event);

    public abstract Node mouseReleasedHandling(MouseEvent event);
}
