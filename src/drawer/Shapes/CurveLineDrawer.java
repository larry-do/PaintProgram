/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.Shapes;

import drawer.PaintTool;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

/**
 *
 * @author Admin
 */
public class CurveLineDrawer extends PaintTool {

    private CubicCurve cubicCurve;

    private int pressCount;

    private BooleanProperty isPressed;

    public CurveLineDrawer(Pane pane) {

        this.pane = pane;

        pressCount = 0;

        isPressed = new SimpleBooleanProperty(false);

        isPressed.addListener(new ChangeListener<Boolean>() { // nếu là IntegerProperty thì phải tách ChangeListener ra thành 1 biến
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true && oldValue == false) {
                    if (pressCount >= 0 && pressCount < 3) {
                        pressCount++;
                    } else {
                        pressCount = 1;
                    }
                }
            }
        });
    }

    public void mousePressedHandling(MouseEvent event) {
        isPressed.set(true);
        if (pressCount == 1) {
            cubicCurve = new CubicCurve(event.getX(), event.getY(), event.getX(), event.getY(),
                    event.getX(), event.getY(), event.getX(), event.getY());

            cubicCurve.setStroke(color);
            cubicCurve.setStrokeWidth(sizeOfPen);
            cubicCurve.setFill(Color.TRANSPARENT);

            pane.getChildren().add(cubicCurve);
        }
    }

    public void mouseDraggedHandling(MouseEvent event) {
        switch (pressCount) {
            case 1: {
                cubicCurve.setEndX(event.getX());
                cubicCurve.setEndY(event.getY());
                break;
            }
            case 2: {
                cubicCurve.setControlX2(event.getX());
                cubicCurve.setControlY2(event.getY());
                break;
            }
            case 3: {
                cubicCurve.setControlX1(event.getX());
                cubicCurve.setControlY1(event.getY());
                break;
            }
            default:
                break;
        }
    }

    public void mouseReleasedHandling(MouseEvent event) {
        isPressed.set(false);
    }

    public void resetPressCount() {
        pressCount = 0;
    }
}
