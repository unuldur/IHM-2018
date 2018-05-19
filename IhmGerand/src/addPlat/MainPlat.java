package addPlat;

import addIngredient.AddIngredient;
import data.Ingredients;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Allergene;
import model.Ingredient;
import model.Plat;
import model.Type;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class MainPlat extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addPlat.fxml"));
        Parent root = loader.load();
        Map<Ingredient, Boolean> ingredients = new HashMap<>();
        ingredients.put(Ingredients.getInstance().getIngredients().get(0), false);
        ingredients.put(Ingredients.getInstance().getIngredients().get(1), true);
        Plat p = new Plat("Salade de printemps", 15f, "Magnifique salade avec des fraises mais sans fraises car c'est pas la saison", ingredients, true);

        AddPlat controller = loader.getController();
        controller.modifieInit(p);
        primaryStage.setTitle("Ajouter plat");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
