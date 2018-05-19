package customSearchBar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by julien on 16/04/2018.
 */
public class TestCustom extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new AddSearchBar<String>();
        ((AddSearchBar) root).getItems().addAll("Coucou", "CouOCu2", "fesse", "chose", "gur", "vache", "pizza");
        ((AddSearchBar) root).setOnItemSelected((obs, old, aze) -> System.out.println(aze));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
