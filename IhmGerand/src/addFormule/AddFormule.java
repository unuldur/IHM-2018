package addFormule;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import model.*;
import treeview.CarteTreeItem;
import treeview.ElementTreeCell;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddFormule implements Initializable{
    public TextField nomField;
    public TextField prixField;
    public TextArea textDescription;
    public Button deleteButton;
    public Button addButton;
    public TreeView<IElement> tree;

    private Map<Ingredient, Boolean> ingredients = new HashMap<>();
    private Formule formule = new Formule();
    private ActionWindow action;

    public void onCancel(ActionEvent actionEvent) {
        action = ActionWindow.CANCEL;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppression");
        alert.setHeaderText("Voulez vous vraiment supprimer cette formule.\n Elle sera supprimé de tous les menus.");

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
        formule.setName(nomField.getText());
        formule.setDescription(textDescription.getText());
        formule.setPrix(Float.valueOf(prixField.getText()));
        action = ActionWindow.ADD;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void modifieInit(Formule f){
        TreeMap<Categorie, Set<Plat>> fom = new TreeMap<>();
        f.getCompo().forEach(((categorie, plats) -> fom.put(categorie, new TreeSet<>(plats))));
        formule = new Formule(f.getName(), f.getDescription(), f.getPrix(), fom);
        textDescription.setText(f.getDescription());
        nomField.setText(f.getName());
        prixField.setText(String.valueOf(f.getPrix()));
        tree.setRoot(new CarteTreeItem(formule));
        deleteButton.setManaged(true);
        addButton.setText("Modifier");
    }

    public void nameInit(String name){
        nomField.setText(name);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteButton.setManaged(false);
        FloatStringConverter converter = new FloatStringConverter();
        final TextFormatter<Float> formatter = new TextFormatter<>(converter,0f);
        prixField.setTextFormatter(formatter);

        tree.setRoot(new CarteTreeItem(formule));
        tree.setShowRoot(false);
        tree.setCellFactory((final TreeView<IElement> treeView) -> {
            ElementTreeCell etc = new ElementTreeCell((observable1, oldValue1, newValue1) -> {
                CarteTreeItem c = new CarteTreeItem(formule);
                tree.setRoot(c);
            });
            etc.setOnElementChange(e -> {
                CarteTreeItem c = new CarteTreeItem(formule);
                tree.setRoot(c);
            });
            return etc;
        });

    }

    public Formule getFormule() {
        return formule;
    }

    public ActionWindow getAction() {
        return action;
    }
}
