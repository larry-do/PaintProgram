/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.NotClassifiedTool;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Admin
 */
public class ColorPicker {
    
    private WritableImage image;

    private PixelReader pixelReader;
    
    private Pane pane;

    public ColorPicker(Pane pane) {
        
        this.pane = pane;
        
    }

    public Color mousePressedHandling(MouseEvent event) {
        image = new WritableImage((int) pane.getPrefWidth(), (int) pane.getPrefHeight());
        pane.snapshot(null, image);
        pixelReader = image.getPixelReader();
        return pixelReader.getColor((int) event.getX(), (int) event.getY());
    }

    public Color mouseDraggedHandling(MouseEvent event) {
        return pixelReader.getColor((int) event.getX(), (int) event.getY());
    }
}
