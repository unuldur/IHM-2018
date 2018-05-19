package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;

public class Ingredient {
    StringProperty name = new SimpleStringProperty();
    ObservableList<Allergene> allergenes;
    Type type;

    public Ingredient(String name, Set<Allergene> allergenes, Type type) {
        this.name.set(name);
        this.allergenes = FXCollections.observableArrayList(allergenes);
        this.type = type;
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

    public ObservableList<Allergene> getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(ObservableList<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name.get();
    }

    public void change(Ingredient i){
        name.set(i.getName());
        allergenes = i.getAllergenes();
        type = i.getType();
    }
}
