package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public class Plat implements IElement, Comparable{
    StringProperty name = new SimpleStringProperty();
    FloatProperty prix  = new SimpleFloatProperty();
    StringProperty description = new SimpleStringProperty();
    Map<Ingredient, Boolean> ingredients;
    BooleanProperty spec = new SimpleBooleanProperty();

    public Plat(String name, float prix, String description, Map<Ingredient, Boolean> ingredients, boolean spec) {
        this.name.set(name);
        this.prix.setValue(prix);
        this.description.set(description);
        this.ingredients = ingredients;
        this.spec.set(spec);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public float getPrix() {
        return prix.get();
    }

    public FloatProperty prixProperty() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix.set(prix);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Map<Ingredient, Boolean> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, Boolean> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isSpec() {
        return spec.get();
    }

    public BooleanProperty specProperty() {
        return spec;
    }

    public void setSpec(boolean spec) {
        this.spec.set(spec);
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public int compareTo(Object o) {
        return name.get().compareTo(((Plat)o).getName());
    }
}
