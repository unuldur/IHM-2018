package addIngredient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Allergene;
import model.Ingredient;
import model.Type;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;


public class MainIng extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addIngredient.fxml"));
        Parent root = loader.load();
        AddIngredient controller = loader.getController();
        Ingredient i = new Ingredient("noix", new HashSet<>(Arrays.asList(Allergene.ARACHIDE)), Type.AUCUN);
        controller.modifieInit(i);
        primaryStage.setTitle("Ajouter ingredient");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
