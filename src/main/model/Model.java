/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.model;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class Model {

    private FileChooser fileChooser;

    private File file;

    public Model() {

    }

    public void writeImage(Image image) {
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.png", "*.png"));
        fileChooser.setInitialFileName("Untitled");
        String path = System.getProperty("user.home") + "\\Desktop";
        //String path = "."; // // get the path where contains the jar (execute) file
        fileChooser.setInitialDirectory(new File(path));
        file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Lỗi không ghi được file");
            }
        }
    }

    public Image getImage() {
        Image img = null;
        fileChooser = new FileChooser();
        String path = System.getProperty("user.home") + "\\Desktop";
        fileChooser.setInitialDirectory(new File(path));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"
                                                                                , "*.bmp", "*.jfif", "*.gif"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            img = new Image(file.toURI().toString());
        }
        return img;

    }
}
