package view;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static Stage primaryStage;
    private static ProgressBar progressBar;

    public static ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/sample.fxml"));
        Label label = new Label();
        label.setText("Load excel file by clicking 'Choose file'."
                + '\n' + "Click 'start' to begin work process."
                + '\n' + "In the first row must be product values."
                + '\n' + "In the first column must be markets."
                + '\n' + "In cell [1][1] must be 1, in other cells"
                + '\n' + "values must be 1 or empty.");
        root.getChildren().add(label);
        progressBar = new ProgressBar();
        root.getChildren().add(progressBar);
        AnchorPane.setBottomAnchor(label, 120.0);
        AnchorPane.setLeftAnchor(label, 10.0);
        AnchorPane.setRightAnchor(label, 10.0);
        AnchorPane.setBottomAnchor(progressBar, 50.0);
        AnchorPane.setLeftAnchor(progressBar, 10.0);
        AnchorPane.setRightAnchor(progressBar, 10.0);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setResizable(false);
        this.primaryStage = primaryStage;
        initUI();
        primaryStage.show();
    }

    private void initUI() {
    }


    public static void main(String[] args) {
        launch(args);
    }
}