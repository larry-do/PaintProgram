package drawer.Shapes;

import drawer.AreaPane;
import drawer.AreaPane.HoverState;
import drawer.Tool;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class LineDrawer extends ShapeDrawer implements Tool {

    private Line line;

    private Point2D anchorPoint;

    public LineDrawer() {
        super();
        line = new Line();
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        if (areaPane.getHoverState() == HoverState.NULL) {
            areaPane.setActiveState(false); // deactive old areaPane
            areaPane = new AreaPane(event.getX(), event.getY());// create a new areaPane

            line = new Line(0, 0, 0, 0);
            line.setFill(Color.TRANSPARENT);
            line.setStroke(color);
            line.setStrokeWidth(strokeWidth);
            line.setStrokeLineCap(strokeLineCap);

            areaPane.getChildren().add(line);
            areaPane.showBorder();

            anchorPoint = new Point2D(event.getX(), event.getY());
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
            if (event.getX() >= anchorPoint.getX() && event.getY() >= anchorPoint.getY()) {
                line.setStartX(0);
                line.setStartY(0);
                line.setEndX(areaPane.getPrefWidth());
                line.setEndY(areaPane.getPrefHeight());
            }
            if (event.getX() > anchorPoint.getX() && event.getY() < anchorPoint.getY()) {
                line.setStartX(0);
                line.setStartY(areaPane.getPrefHeight());
                line.setEndX(areaPane.getPrefWidth());
                line.setEndY(0);
            }
            if (event.getX() < anchorPoint.getX() && event.getY() > anchorPoint.getY()) {
                line.setStartX(areaPane.getPrefWidth());
                line.setStartY(0);
                line.setEndX(0);
                line.setEndY(areaPane.getPrefHeight());
            }
            if (event.getX() < anchorPoint.getX() && event.getY() < anchorPoint.getY()) {
                line.setEndX(0);
                line.setEndY(0);
                line.setStartX(areaPane.getPrefWidth());
                line.setStartY(areaPane.getPrefHeight());
            }
        }
        if (areaPane.getMovingState()) {
            areaPane.dragToMoveAndResize(event.getX(), event.getY());
        }
        return null;
    }

    @Override
    public Node mouseReleasedHandling(MouseEvent event) {
        areaPane.setActiveState(true);
        areaPane.setMovingState(false);
        return null;
    }

}
