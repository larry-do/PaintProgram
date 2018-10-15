package drawer.Shapes;

import drawer.AreaPane;
import drawer.PaintTool;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public abstract class ShapeDrawer extends PaintTool {
    
    protected AreaPane areaPane;
    
    public ShapeDrawer() {
        color = Color.BLACK;
        strokeWidth = 1;
        strokeLineCap = StrokeLineCap.ROUND;
        areaPane = new AreaPane(0, 0);
        
        imageCursor = new ImageCursor(new Image("icon/shape-cursor.png"), 100, 100);
    }
    
    public void setActiveState(boolean state) {
        areaPane.setActiveState(state);
    }
    
    public void setBorderVisiable(boolean state) {
        areaPane.setBorderVisiable(state);
    }
}
