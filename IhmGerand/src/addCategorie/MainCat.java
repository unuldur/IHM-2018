package addCategorie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Categorie;

import java.io.IOException;

public class MainCat extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCategorie.fxml"));
        Parent root = loader.load();
        AddCategorie controller = loader.getController();
        primaryStage.setTitle("Ajouter categorie");
        primaryStage.setOnCloseRequest(event -> {
            if(controller.getAction() == null) return;
            switch (controller.getAction()){
                case ADD:
                    System.out.println("Add");
                    System.out.println(controller.getCategorie());
                    break;
                case CANCEL:
                    System.out.println("Cancel");
                    System.out.println(controller.getCategorie());
                    break;
                case DELETE:
                    System.out.println("Delete");
                    System.out.println(controller.getCategorie());
                    break;
            }
        });
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
