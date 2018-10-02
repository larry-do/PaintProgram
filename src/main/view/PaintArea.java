/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

/**
 *
 * @author Admin
 */
public class PaintArea extends ZoomableScrollPane {

    private final int DEFAULT_PAINT_WIDTH = 1110;
    private final int DEFAULT_PAINT_HEIGHT = 695;

    private Pane paintPane;

    public PaintArea() {
        super();

        paintPane = new Pane();
        paintPane.setStyle("-fx-background-color: white");
        paintPane.setPrefSize(DEFAULT_PAINT_WIDTH, DEFAULT_PAINT_HEIGHT);
        setImageOfCursorInPaintPane(new Image("icon/pencil-cursor.png"), 0, 0);

        setTargetNode(paintPane);
        setPannable(false);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        setFitToHeight(true);
        setFitToWidth(true);
    }

    public Pane getPaintPane() {
        return paintPane;
    }

    public void setSizePaintPane(double width, double height) {
        paintPane.setPrefSize(width, height);
    }

    public <T extends Event> void addPaintPaneEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        paintPane.addEventHandler(eventType, eventHandler);
    }

    public WritableImage getImageOfPane() {
        WritableImage image = new WritableImage((int) paintPane.getPrefWidth(), (int) paintPane.getPrefHeight());
        paintPane.snapshot(null, image);
        return image;
    }

    public void addNodeToPaintPane(Node... nodes) {
        paintPane.getChildren().addAll(nodes);
    }

    public void removeAllNodePaintPane() {
        paintPane.getChildren().remove(0, paintPane.getChildren().size());
    }

    public boolean isPaintPaneEmpty() {
        return paintPane.getChildren().isEmpty();
    }

    public double getPaintPaneWidth() {
        return paintPane.getPrefWidth();
    }

    public double getPaintPaneHeight() {
        return paintPane.getPrefHeight();
    }

    public void setImageOfCursorInPaintPane(Image img, double x, double y) {
        paintPane.setCursor(new ImageCursor(img, x, y));
    }
}
