package addFormule;

import addPlat.AddPlat;
import data.Categories;
import data.Ingredients;
import data.Plats;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Categorie;
import model.Formule;
import model.Ingredient;
import model.Plat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class MainForm extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFormule.fxml"));
        Parent root = loader.load();

        Formule f = new Formule();
        f.setPrix(15f);
        f.setDescription("aie aie");
        f.setName("coucou");
        AddFormule controller = loader.getController();
        Categorie cat1 = Categories.getInstance().getCategories().get(0);
        Categorie cat2 = Categories.getInstance().getCategories().get(1);
        Set<Plat> a = new TreeSet<>();
        a.add(Plats.getInstance().getPlats().get(0));
        a.add(Plats.getInstance().getPlats().get(1));
        Set<Plat> b = new TreeSet<>();
        b.add(Plats.getInstance().getPlats().get(1));
        b.add(Plats.getInstance().getPlats().get(2));
        b.add(Plats.getInstance().getPlats().get(3));
        f.getCompo().put(cat1, a);
        f.getCompo().put(cat2, b);
        controller.modifieInit(f);
        primaryStage.setTitle("Ajouter formule");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
