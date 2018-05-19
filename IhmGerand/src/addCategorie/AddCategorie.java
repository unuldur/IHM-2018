package addCategorie;

import actionWindow.ActionWindow;
import addIngredient.AddIngredient;
import customSearchBar.AddSearchBar;
import data.Categories;
import data.Ingredients;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Allergene;
import model.Categorie;
import model.Ingredient;
import modifiePrint.ModifiePrint;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddCategorie implements Initializable{
    public TextField nameText;
    public Button deleteButton;
    public Button addButton;
    public FlowPane flow;
    public AddSearchBar<Categorie> addSearchBar;
    private Categorie categorie;
    private ActionWindow action;
    private List<Categorie> categorieList = new ArrayList<>();

    public void initCategorie(Categorie cat){
        nameText.setText(cat.getName());
        deleteButton.setManaged(true);
        categorie = cat;
        for (Categorie c :
                cat.getSousCategorie()) {
            setCategorie(c);
        }
        ArrayList<Categorie> categories = new ArrayList<>(Categories.getInstance().getCategories());
        categories.remove(categorie);
        addSearchBar.getItems().setAll(categories);
        addButton.setText("Modifier");
    }

    public void nameInit(String name){
        nameText.setText(name);
    }

    public void onCancel(ActionEvent actionEvent) {
        action = ActionWindow.CANCEL;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppression");
        alert.setHeaderText("Voulez vous vraiment supprimer cette categorie.\n Elle sera supprimé de toutes les formules et de tous menus.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            return;
        }
        action = ActionWindow.DELETE;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onAdd(ActionEvent actionEvent) {
        if(nameText.getText().isEmpty()) return;
        if(categorie == null) {
            categorie = new Categorie(nameText.getText(), categorieList);
        }else {
            categorie.setName(nameText.getText());
            categorie.setSousCategorie(categorieList);
        }
        action = ActionWindow.ADD;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public ActionWindow getAction() {
        return action;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteButton.setManaged(false);
        addSearchBar.getItems().setAll(Categories.getInstance().getCategories());
        addSearchBar.setOnItemSelected((observable, oldValue, newValue) -> {
            if(newValue == null) return;
            addSearchBar.resetSelection();
            if(categorieList.contains(newValue)) return;
            setCategorie(newValue);
        });
        addSearchBar.button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addCategorie.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddCategorie controller = loader.getController();
                controller.nameInit(addSearchBar.combo.getPreviousValue());
                stage.setTitle("Ajouter Ingredient");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case ADD:
                        Categorie c = controller.getCategorie();
                        setCategorie(c);
                        Categories.getInstance().getCategories().add(c);
                        addSearchBar.getItems().add(c);
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setCategorie(Categorie c){
        Label l = new Label(c.getName());
        l.setPadding(new Insets(5,5,5,5));
        categorieList.add(c);
        ModifiePrint mp = new ModifiePrint(l, Collections.singletonList(ModifiePrint.ButtonType.BUTTON_DEL));
        mp.getDelete().setOnAction(event -> {
            categorieList.remove(c);
            flow.getChildren().remove(mp);
        });
        flow.getChildren().add(mp);
    }
}
