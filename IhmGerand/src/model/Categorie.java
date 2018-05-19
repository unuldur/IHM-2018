package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Categorie implements IElement, Comparable{
    private StringProperty name = new SimpleStringProperty();
    private List<Categorie> sousCategorie = new ArrayList<>();

    public List<Categorie> getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(List<Categorie> sousCategorie) {

        this.sousCategorie = sousCategorie;
    }

    public Categorie(String name) {
        this.name.set(name);
    }
    public Categorie(String name, List<Categorie> sousCategorie) {
        this.name.set(name);
        this.sousCategorie = sousCategorie;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return name.get();
    }


    @Override
    public int compareTo(Object o) {
        return getName().compareTo(((Categorie)o).getName());
    }
}
