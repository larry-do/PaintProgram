/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.model;

import drawer.AreaPane;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.Pane;
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

    public void writeImageToFile(Image image) {
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) {
            System.out.println("Lỗi không ghi được file");
        }
    }

    public void saveAs(Image image) {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.png", "*.png"));
        fileChooser.setInitialFileName("Untitled");
        String path = System.getProperty("user.home") + "\\Desktop";
        //String path = "."; // // get the path where contains the jar (execute) file
        fileChooser.setInitialDirectory(new File(path));
        file = fileChooser.showSaveDialog(null);
        if (file != null) {
            writeImageToFile(image);
        }
    }

    public Image getImageFromFile() {
        Image image = null;
        fileChooser = new FileChooser();
        String path = System.getProperty("user.home") + "\\Desktop";
        fileChooser.setInitialDirectory(new File(path));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg",
                "*.bmp", "*.jfif", "*.gif"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            image = new Image(file.toURI().toString());
        }
        return image;
    }

    public Image getImageFromClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasFiles()) {
            List<File> filesList = clipboard.getFiles();
            return new Image(filesList.get(filesList.size() - 1).toURI().toString());
        }
        return null;
    }

    public boolean isFileEmpty() {
        return (file == null);
    }

    public void resetModel() {
        fileChooser = null;
        file = null;
    }
}
