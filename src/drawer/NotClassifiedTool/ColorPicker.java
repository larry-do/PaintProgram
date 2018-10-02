package drawer.NotClassifiedTool;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ColorPicker {

    private PixelReader pixelReader;

    public ColorPicker() {
        
    }

    public Color mousePressedHandling(MouseEvent event, WritableImage image) {
        pixelReader = image.getPixelReader();
        return pixelReader.getColor((int) event.getX(), (int) event.getY());
    }

    public Color mouseDraggedHandling(MouseEvent event) {
        return pixelReader.getColor((int) event.getX(), (int) event.getY());
    }
}
