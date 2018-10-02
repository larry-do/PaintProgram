/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Shapes;

import drawer.AreaPane;
import drawer.PaintTool;
import drawer.Tool;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Admin
 */
public abstract class ShapeDrawer extends PaintTool{

    protected AreaPane areaPane;

    public ShapeDrawer() {
        color = Color.BLACK;
        strokeWidth = 1;
        strokeLineCap = StrokeLineCap.ROUND;
        areaPane = new AreaPane(0, 0);
    }

    public void setOff() {
        areaPane.setActiveState(false);
    }
}
