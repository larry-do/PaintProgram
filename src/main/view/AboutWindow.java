package main.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AboutWindow extends Stage {

    private Scene scene;
    private Pane pane;

    public AboutWindow() {
        super();
        pane = new Pane();

        ImageView imageView = new ImageView(new Image("icon/app-icon.png"));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setLayoutX(195);
        imageView.setLayoutY(20);

        Label header = new Label("Paint Program");
        header.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 20; -fx-text-fill: darkred;");
        header.setLayoutX(180);
        header.setLayoutY(150);

        Label content = new Label();
        content.setText("Version 1.0 \n"
                + "Copyright © 2018 SV_HVCNBCVT \n"
                + "A project for education");
        content.setTextAlignment(TextAlignment.CENTER);
        content.setLayoutX(155);
        content.setLayoutY(200);

        pane.getChildren().addAll(imageView, header, content);

        scene = new Scene(pane, 480, 320);
        setScene(scene);
        setTitle("About Paint");
        setResizable(false); // không cho thay đổi kích thước
        initStyle(StageStyle.UTILITY); // chỉ định là stage utility, không có nút phóng to/thu nhỏ
        getIcons().add(new Image("icon/app-icon.png")); // set icon
    }
}
