package addPlat;

import actionWindow.ActionWindow;
import addIngredient.AddIngredient;
import customSearchBar.AddSearchBar;
import data.Ingredients;
import data.Plats;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.NumberStringConverter;
import model.Allergene;
import model.Ingredient;
import model.Plat;
import model.Type;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddPlat implements Initializable{
    public TextField nomField;
    public TextField prixField;
    public TextArea textDescription;
    public AddSearchBar<Ingredient> searchIngredient;
    public CheckBox checkSpe;
    public Button deleteButton;
    public GridPane gridIngredients;
    public Button addButton;

    private Map<Ingredient, Boolean> ingredients = new HashMap<>();
    private Plat plat;
    private ActionWindow action;
    public void onCancel(ActionEvent actionEvent) {
        action = ActionWindow.CANCEL;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppression");
        alert.setHeaderText("Voulez vous vraiment supprimer ce plat.\n Il sera supprimé de tous les menus et de toutes les formules.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            return;
        }
        action = ActionWindow.DELETE;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onAdd(ActionEvent actionEvent) {
        if(nomField.getText().isEmpty()) return;
        if(plat == null) {
            plat = new Plat(nomField.getText(), Float.valueOf(prixField.getText()), textDescription.getText(), ingredients, checkSpe.isSelected());
        }else{
            plat.setName(nomField.getText());
            plat.setPrix(Float.valueOf(prixField.getText()));
            plat.setDescription(textDescription.getText());
            plat.setIngredients(ingredients);
            plat.setSpec(checkSpe.isSelected());
        }
        action = ActionWindow.ADD;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void modifieInit(Plat p){
        nomField.setText(p.getName());
        prixField.setText(String.valueOf(p.getPrix()));
        textDescription.setText(p.getDescription());
        checkSpe.setSelected(p.isSpec());
        for (Map.Entry<Ingredient, Boolean> i:
             p.getIngredients().entrySet()) {
            addIngredients(i.getKey(), i.getValue());
        }
        deleteButton.setManaged(true);
        plat = p;
        addButton.setText("Modifier");
    }

    public void nameInit(String name){
        nomField.setText(name);
    }

    private int rowact = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteButton.setManaged(false);
        FloatStringConverter converter = new FloatStringConverter();
        final TextFormatter<Float> formatter = new TextFormatter<>(converter,0f);
        prixField.setTextFormatter(formatter);

        searchIngredient.getItems().addAll(Ingredients.getInstance().getIngredients());
        searchIngredient.setOnItemSelected((observable, oldValue, newValue) -> {
            if(newValue == null) return;
            searchIngredient.resetSelection();
            if(ingredients.containsKey(newValue)) return;
            addIngredients(newValue, false);
        });

        searchIngredient.button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addIngredient/addIngredient.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddIngredient controller = loader.getController();
                controller.nameInit(searchIngredient.combo.getPreviousValue());
                stage.setTitle("Ajouter Ingredient");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case ADD:
                        Ingredient i = controller.getIngredient();
                        addIngredients(i, false);
                        Ingredients.getInstance().getIngredients().add(i);
                        searchIngredient.getItems().add(i);
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addIngredients(Ingredient e, boolean use){
        ingredients.put(e, use);
        Label l = new Label(e.getName());
        l.textProperty().bind(e.nameProperty());

        CheckBox b = new CheckBox();
        b.setSelected(use);
        b.setOnAction(event -> ingredients.put(e, b.isSelected()));

        Button del = new Button();
        Button mod = new Button();

        mod.getStyleClass().add("icon-button");
        mod.setPickOnBounds(true);

        Region iconModifie = new Region();
        iconModifie.getStyleClass().add("icon-modifie");
        mod.setGraphic(iconModifie);

        del.getStyleClass().add("icon-button");
        del.setPickOnBounds(true);

        Region iconDelete = new Region();
        iconDelete.getStyleClass().add("icon-delete");
        del.setGraphic(iconDelete);

        HBox hb = new HBox();
        hb.getChildren().addAll(mod, del);

        mod.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addIngredient/addIngredient.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddIngredient controller = loader.getController();
                controller.modifieInit(e);
                stage.setTitle("Modifier ingredient");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case DELETE:
                        Ingredients.getInstance().getIngredients().remove(e);
                        searchIngredient.getItems().remove(e);
                        for (Plat p :
                                Plats.getInstance().getPlats()) {
                            p.getIngredients().remove(e);
                        }
                        gridIngredients.getChildren().remove(l);
                        gridIngredients.getChildren().remove(b);
                        gridIngredients.getChildren().remove(hb);
                        ingredients.remove(e);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        del.setOnAction(event -> {
            gridIngredients.getChildren().remove(l);
            gridIngredients.getChildren().remove(b);
            gridIngredients.getChildren().remove(hb);
            ingredients.remove(e);
        });
        gridIngredients.addRow(rowact, l, b, hb);
        rowact++;
    }

    public Plat getPlat() {
        return plat;
    }

    public ActionWindow getAction() {
        return action;
    }
}
