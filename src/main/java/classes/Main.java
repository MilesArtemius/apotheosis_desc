package classes;

import classes.activities.ModeChoosingActivity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage main;

    @Override
    public void start(Stage primaryStage) {
        main = primaryStage;
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(ModeChoosingActivity.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(Scene scene) {
        main.setScene(scene);
    }
}
