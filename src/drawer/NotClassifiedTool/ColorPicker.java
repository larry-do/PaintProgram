package drawer.NotClassifiedTool;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ColorPicker {

    private PixelReader pixelReader;

    private ImageCursor imageCursor;

    public ColorPicker() {
        imageCursor = new ImageCursor(new Image("icon/colorPicker-cursor.png"), 0, 200);
    }

    public Color mousePressedHandling(MouseEvent event, WritableImage image) {
        pixelReader = image.getPixelReader();
        return pixelReader.getColor((int) event.getX(), (int) event.getY());
    }

    public Color mouseDraggedHandling(MouseEvent event) {
        return pixelReader.getColor((int) event.getX(), (int) event.getY());
    }

    public ImageCursor getImageCursor() {
        return imageCursor;
    }
}
