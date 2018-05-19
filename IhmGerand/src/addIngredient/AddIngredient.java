package addIngredient;

import actionWindow.ActionWindow;
import customSearchBar.ComboSearchBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Allergene;
import model.Ingredient;
import model.Type;
import modifiePrint.ModifiePrint;

import java.net.URL;
import java.util.*;

public class AddIngredient implements Initializable{
    public FlowPane flowType;
    public ComboSearchBox<Allergene> searchBox;
    public FlowPane flowAll;
    public TextField nomField;
    public Button suprimerButton;
    public Button addButton;

    private Set<Allergene> allergeneSet = new HashSet<>();
    private Type type;
    private Ingredient ingredient;
    private ActionWindow action;
    private RadioButton[] radioButtons;

    public void onCancel(ActionEvent actionEvent) {
        action = ActionWindow.CANCEL;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppression");
        alert.setHeaderText("Voulez vous vraiment supprimer cet ingrédient.\n Il sera supprimé de tous les plats.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            return;
        }
        action = ActionWindow.DELETE;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onAdd(ActionEvent actionEvent) {
        if(nomField.getText().isEmpty() || type == null) return;
        Ingredient i = new Ingredient(nomField.getText(), allergeneSet, type);
        if(ingredient != null){
            ingredient.change(i);
        }else{
            ingredient = i;
        }
        action = ActionWindow.ADD;
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void modifieInit(Ingredient i){
        nomField.setText(i.getName());
        for (Allergene a :
                i.getAllergenes()) {
            addAlergene(a);
        }
        radioButtons[i.getType().ordinal()].setSelected(true);
        type = i.getType();
        suprimerButton.setManaged(true);
        ingredient = i;
        addButton.setText("Modifier");
    }

    public void nameInit(String name){
        nomField.setText(name);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public ActionWindow getAction() {
        return action;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup tg = new ToggleGroup();
        radioButtons = new RadioButton[Type.values().length];
        for (Type t:
            Type.values()) {
            RadioButton rb = new RadioButton(t.toString());
            rb.setToggleGroup(tg);
            rb.setPadding(new Insets(5,5,5,5));
            flowType.getChildren().add(rb);
            rb.setOnAction(event -> type = t);
            radioButtons[t.ordinal()] = rb;
        }
        searchBox.getAllItems().addAll(Allergene.values());
        searchBox.setChange((observable, oldValue, newValue) -> {
            if(newValue == null ) return;
            searchBox.clearSelection();
            if(allergeneSet.contains(newValue)) return;
            addAlergene(newValue);
        });
        suprimerButton.setManaged(false);
    }


    private void addAlergene(Allergene a){
        Label l = new Label(a.toString());
        l.setPadding(new Insets(5,5,5,5));
        allergeneSet.add(a);
        ModifiePrint mp = new ModifiePrint(l, Collections.singletonList(ModifiePrint.ButtonType.BUTTON_DEL));
        mp.getDelete().setOnAction(event -> {
            allergeneSet.remove(a);
            flowAll.getChildren().remove(mp);
        });
        flowAll.getChildren().add(mp);
    }
}
