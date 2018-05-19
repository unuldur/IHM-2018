package modifiePrint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TestModifiePrint extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Ajouter categorie");
        primaryStage.setScene(new Scene(new ModifiePrint(new Label("coucou le monde"))));
        primaryStage.show();

    }
}
