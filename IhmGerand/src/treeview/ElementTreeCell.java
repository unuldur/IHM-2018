package treeview;

import addCategorie.AddCategorie;
import addFormule.AddFormule;
import addPlat.AddPlat;
import customSearchBar.AddSearchBar;
import data.Cartes;
import data.Categories;
import data.Formules;
import data.Plats;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import modifiePrint.ModifiePrint;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class ElementTreeCell extends TreeCell<IElement> {

    private ChangeListener<IElement> listener;
    private ActionListener onElementChange;
    public void setOnElementChange(ActionListener onElementChange) {
        this.onElementChange = onElementChange;
    }

    public ElementTreeCell(ChangeListener<IElement> listener) {
        this.listener = listener;
    }

    @Override
    protected void updateItem(final IElement value, final boolean empty) {
        super.updateItem(value, empty);
        textProperty().unbind();
        setText(null);
        setStyle(null);
        setGraphic(null);

        if (value == null || empty) return;
        if(value instanceof PlusElement){
            if(value.getName().equals(Categorie.class.getSimpleName())){
                categorie(value);
                return;
            }
            if(value.getName().equals(Plat.class.getSimpleName())){
                plat(value);
                return;
            }
            if(value.getName().equals(Formule.class.getSimpleName())){
                formule(value);
                return;
            }
            setText(value.getName());
            return;
        }
        Label name = new Label(value.getName());
        ModifiePrint mp = new ModifiePrint(name);
        mp.setMouseEntered(event -> name.setStyle("-fx-font-weight: bold; -fx-font-size: 15"));
        mp.setMouseExited(event -> name.setStyle(""));
        if(value instanceof Categorie){
           buttonsCat(mp);
        }
        if(value instanceof Plat){
            buttonsPlat(mp);
        }
        if(value instanceof Formule){
            buttonsFormule(mp);
        }
        setGraphic(mp);

    }

    private void buttonsCat(ModifiePrint mp){
        mp.getModifie().setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addCategorie/addCategorie.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddCategorie controller = loader.getController();
                stage.setTitle("Modifier categorie");
                controller.initCategorie((Categorie)getTreeItem().getValue());
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case ADD:
                        onElementChange.actionPerformed(null);
                        break;
                    case DELETE:
                        Categories.getInstance().getCategories().remove(controller.getCategorie());
                        removeCategorie(controller.getCategorie());
                        Cartes.getInstance().getCartes().forEach(c -> c.getCarte().remove(controller.getCategorie()));
                        Formules.getInstance().getFormules().forEach(f -> f.getCompo().remove(controller.getCategorie()));
                        onElementChange.actionPerformed(null);
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mp.getDelete().setOnAction(e -> {
            removeCategorie((Categorie) getTreeItem().getValue());
            onElementChange.actionPerformed(null);
        });
    }

    private void buttonsFormule(ModifiePrint mp){
        mp.getModifie().setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addFormule/addFormule.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddFormule controller = loader.getController();
                stage.setTitle("Modifier formule");
                controller.modifieInit((Formule) getTreeItem().getValue());
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case ADD:
                        Formule f = controller.getFormule();
                        Formule mf = (Formule) getTreeItem().getValue();
                        mf.setPrix(f.getPrix());
                        mf.setDescription(f.getDescription());
                        mf.setName(f.getName());
                        mf.setFormule(f.getCompo());
                        onElementChange.actionPerformed(null);
                        break;
                    case DELETE:
                        Cartes.getInstance().getCartes().forEach(carte -> carte.getFormules().remove(controller.getFormule()));
                        Formules.getInstance().getFormules().remove(controller.getFormule());
                        onElementChange.actionPerformed(null);
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mp.getDelete().setOnAction(e -> {
            removeFormule((Formule) getTreeItem().getValue());
            onElementChange.actionPerformed(null);
        });
    }

    private void removeFormule(Formule value) {
        Carte c = (Carte) getTreeItem().getParent().getParent().getValue();
        c.getFormules().remove(value);
    }

    private void buttonsPlat(ModifiePrint mp){
        mp.getModifie().setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addPlat/addPlat.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddPlat controller = loader.getController();
                stage.setTitle("Modifier plat");
                controller.modifieInit((Plat) getTreeItem().getValue());
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case ADD:
                        onElementChange.actionPerformed(null);
                        break;
                    case DELETE:
                        Plats.getInstance().getPlats().remove(controller.getPlat());
                        Cartes.getInstance().getCartes().forEach(c -> {
                            for (Categorie cat :
                                    c.getCarte().keySet()) {
                                c.getCarte().get(cat).remove(controller.getPlat());
                            }
                        });
                        Formules.getInstance().getFormules().forEach(c -> {
                            for (Categorie cat :
                                    c.getCompo().keySet()) {
                                c.getCompo().get(cat).remove(controller.getPlat());
                            }
                        });
                        onElementChange.actionPerformed(null);
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mp.getDelete().setOnAction(e -> {
            removePlat((Plat) getTreeItem().getValue());
            onElementChange.actionPerformed(null);
        });
    }



    private void plat(IElement value){
        AddSearchBar<Plat> sb = new AddSearchBar<>();
        sb.setText(value.getName());
        sb.getItems().addAll(Plats.getInstance().getPlats());
        sb.setOnItemSelected((observable, oldValue, newValue) ->{
           insertPlat(newValue);
            listener.changed(observable, oldValue, newValue);
        });
        sb.button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addPlat/addPlat.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddPlat controller = loader.getController();
                stage.setTitle("Ajouter plat");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);

                controller.nameInit(sb.combo.getPreviousValue());
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case ADD:
                        Plats.getInstance().getPlats().add(controller.getPlat());
                        sb.getItems().add(controller.getPlat());
                        insertPlat(controller.getPlat());
                        onElementChange.actionPerformed(null);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        setGraphic(sb);
    }

    private void formule(IElement value){
        AddSearchBar<Formule> sb = new AddSearchBar<>();
        sb.setText(value.getName());
        sb.getItems().addAll(Formules.getInstance().getFormules());
        sb.setOnItemSelected((observable, oldValue, newValue) ->{
            insertFormule(newValue);
            listener.changed(observable, oldValue, newValue);
        });
        sb.button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addFormule/addFormule.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddFormule controller = loader.getController();
                controller.nameInit(sb.combo.getPreviousValue());
                stage.setTitle("Ajouter formule");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case ADD:
                        Formules.getInstance().getFormules().add(controller.getFormule());
                        insertFormule(controller.getFormule());
                        onElementChange.actionPerformed(null);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        setGraphic(sb);
    }

    private void insertFormule(Formule newValue) {
        Carte c = (Carte) getTreeItem().getParent().getParent().getValue();
        c.getFormules().add(newValue);
    }


    private void categorie(IElement value){
        AddSearchBar<Categorie> sb = new AddSearchBar<>();
        sb.setText(value.getName());
        sb.getItems().addAll(Categories.getInstance().getCategories());
        sb.setOnItemSelected((observable, oldValue, newValue) ->{
            insertCategorie(newValue);
            listener.changed(observable, oldValue, newValue);
        });
        sb.button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addCategorie/addCategorie.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                AddCategorie controller = loader.getController();
                controller.nameInit(sb.combo.getPreviousValue());
                stage.setTitle("Ajouter categorie");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (controller.getAction() == null) return;
                switch (controller.getAction())
                {
                    case ADD:
                        Categories.getInstance().getCategories().add(controller.getCategorie());
                        sb.getItems().add(controller.getCategorie());
                        insertCategorie(controller.getCategorie());
                        onElementChange.actionPerformed(null);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        setGraphic(sb);
    }

    private void insertCategorie(Categorie categorie){
        ICompoPlat c = (ICompoPlat) getTreeItem().getParent().getValue();
        if(!c.getCompo().containsKey(categorie)) {
            c.getCompo().put(categorie, new TreeSet<>());
        }
    }

    private void removeCategorie(Categorie categorie){
        ICompoPlat c = (ICompoPlat) getTreeItem().getParent().getValue();
        c.getCompo().remove(categorie);
    }


    private void insertPlat(Plat p){
        ICompoPlat c = (ICompoPlat) getTreeItem().getParent().getParent().getValue();
        Categorie cat = (Categorie) getTreeItem().getParent().getValue();
        c.getCompo().get(cat).add(p);
    }

    private void removePlat(Plat p){
        ICompoPlat c = (ICompoPlat) getTreeItem().getParent().getParent().getValue();
        Categorie cat = (Categorie) getTreeItem().getParent().getValue();
        c.getCompo().get(cat).remove(p);
    }



}
