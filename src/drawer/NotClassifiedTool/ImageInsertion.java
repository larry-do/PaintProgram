/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawer.NotClassifiedTool;

import drawer.AreaPane;
import drawer.Tool;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;

/**
 *
 * @author Admin
 */
public class ImageInsertion extends AreaPane implements Tool {

    private Image image;
    private ImageView imageView;

    public ImageInsertion() {
        super(0, 0);
        image = null;
        imageView = null;
    }

    public void setImage(Image image) {
        this.image = image;
        this.imageView = new ImageView(image);
        this.getChildren().add(imageView);
        this.setSize(image.getWidth(), image.getHeight());
        this.setActiveState(true);
        this.showBorder();

    }

    @Override
    public Node mousePressedHandling(MouseEvent event) {
        if (this.getHoverState() == HoverState.NULL) {
            this.setMovingState(false);
            this.setActiveState(false);
        } else {
            this.setMovingState(true);
            this.setAnchorPoint(event.getX(), event.getY());
        }
        return null;
    }

    @Override
    public Node mouseDraggedHandling(MouseEvent event) {
        double width = this.getPrefWidth();
        double height = this.getPrefHeight();
        if (this.getMovingState()) {
            this.dragToMoveAndResize(event.getX(), event.getY());
        }
        Scale scale = new Scale();
        scale.setX(getPrefWidth() / width);
        scale.setY(getPrefHeight() / height);
        scale.setPivotX(0);
        scale.setPivotY(0);
        imageView.getTransforms().addAll(scale);
        return null;
    }

    @Override
    public Node mouseReleasedHandling(MouseEvent event) {
        return null;
    }
}