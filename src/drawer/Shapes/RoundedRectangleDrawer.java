package drawer.Shapes;

import drawer.AreaPane;
import drawer.Tool;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RoundedRectangleDrawer extends ShapeDrawer implements Tool {

    private Rectangle rectangle;

    public RoundedRectangleDrawer() {
        super();
        rectangle = new Rectangle(0, 0, 0, 0);
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        if (areaPane.getHoverState() == AreaPane.HoverState.NULL) {
            areaPane.setActiveState(false); // deactive old areaPane
            areaPane = new AreaPane(event.getX(), event.getY());// create a new areaPane

            rectangle = new Rectangle(0, 0, 0, 0); // create a rectangle on the new areaPane
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(color);
            rectangle.setStrokeWidth(strokeWidth);
            rectangle.setStrokeLineCap(strokeLineCap);
            rectangle.setArcWidth(30);
            rectangle.setArcHeight(30);

            areaPane.getChildren().add(rectangle); // add rectangle firstly
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
        rectangle.setWidth(areaPane.getPrefWidth());
        rectangle.setHeight(areaPane.getPrefHeight());
        return null;
    }
    
    @Override
    public Node mouseReleasedHandling(MouseEvent event){
        areaPane.setActiveState(true);
        areaPane.setMovingState(false);
        areaPane.setBorderVisiable(true);
        return null;
    }
}
