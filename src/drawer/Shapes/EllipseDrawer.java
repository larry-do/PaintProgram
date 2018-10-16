package drawer.Shapes;

import drawer.AreaPane;
import drawer.AreaPane.HoverState;
import drawer.Tool;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class EllipseDrawer extends ShapeDrawer implements Tool {

    private Ellipse ellipse;

    public EllipseDrawer() {
        super();
        ellipse = new Ellipse(0, 0, 0, 0);
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        if (areaPane.getHoverState() == HoverState.NULL) {
            areaPane.setActiveState(false);
            areaPane = new AreaPane(event.getX(), event.getY());

            ellipse = new Ellipse(event.getX(), event.getY(), 0, 0);
            ellipse.setFill(Color.TRANSPARENT);
            ellipse.setStroke(color);
            ellipse.setStrokeWidth(strokeWidth);
            ellipse.setStrokeLineCap(strokeLineCap);

            areaPane.getChildren().add(ellipse);
            areaPane.showBorder();
            areaPane.setBorderVisiable(false);
            return areaPane;
        } else {
            areaPane.setMovingState(true);
            areaPane.setAnchorPoint(event.getX(), event.getY());
        }
        return null;
    }

    @Override
    public Node mouseDraggedHandling(MouseEvent event) {
        if (!areaPane.getActiveState()) {
            areaPane.dragToCreate(event.getX(), event.getY());
        }
        if (areaPane.getMovingState()) {
            areaPane.dragToMoveAndResize(event.getX(), event.getY());
        }
        ellipse.setCenterX(areaPane.getPrefWidth() / 2);
        ellipse.setCenterY(areaPane.getPrefHeight() / 2);
        ellipse.setRadiusX(areaPane.getPrefWidth() / 2);
        ellipse.setRadiusY(areaPane.getPrefHeight() / 2);
        return null;
    }

    @Override
    public Node mouseReleasedHandling(MouseEvent event) {
        areaPane.setActiveState(true);
        areaPane.setMovingState(false);
        areaPane.setBorderVisiable(true);
        return null;
    }

}
