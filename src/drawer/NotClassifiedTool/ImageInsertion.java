package drawer.NotClassifiedTool;

import drawer.AreaPane;
import drawer.Tool;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;

public class ImageInsertion extends AreaPane implements Tool {

    private ImageView imageView;

    public ImageInsertion() {
        super(0, 0);
        imageView = null;
    }

    public void setImage(Image image) {
        this.imageView = new ImageView(image);
        this.getChildren().add(imageView);
        this.setSize(image.getWidth(), image.getHeight());
        this.setActiveState(true);
        this.showBorder();
    }

    @Override
    public Node mousePressedHandling(MouseEvent event) { // khi vừa press mà active state đã false có nghĩa là press vào vùng ngoài vật thể
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
        this.setMovingState(false);
        return null;
    }

    public void setOff() {
        setMovingState(false);
        setActiveState(false);
        setCursorHoveringImage(HoverState.NULL);
    }
}
