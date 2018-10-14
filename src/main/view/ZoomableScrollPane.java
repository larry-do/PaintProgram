package main.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class ZoomableScrollPane extends ScrollPane {

    private double scaleValue = 0.7;
    private double zoomIntensity = 0.02;
    private Node paintPane;
    private Node target;
    private Node zoomNode;
    private Node centerNode;

    public ZoomableScrollPane() {
        super(); // gọi đến khởi tạo Pane();
    }

    public void setTargetNode(Node node) {
        paintPane = node;
        target = new Pane(this.paintPane);
        zoomNode = new Group(target);
        centerNode = centerNode(zoomNode);
        setContent(centerNode);
        zoomOnTarget(-11.0, new Point2D(1, 1)); // cái này là trick để zoom nhỏ PaintPane khi mở thôi!
    }

    public <T extends Event> void addZoomNodeEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        centerNode.addEventHandler(eventType, eventHandler);
    }

    private Node centerNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void updateScale() {
        target.setScaleX(scaleValue);
        target.setScaleY(scaleValue);
    }

    public void zoomOnTarget(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;
        updateScale();
        this.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }

}
