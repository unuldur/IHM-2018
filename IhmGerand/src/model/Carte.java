package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Carte implements ICompoPlat{
    private StringProperty name = new SimpleStringProperty();
    private BooleanProperty use = new SimpleBooleanProperty();
    private Map<Categorie, Set<Plat>> carte = new TreeMap<>();
    private Set<Formule> formules = new TreeSet<>();

    public Carte(String name) {
        this.name.set(name);
        use.set(false);
    }

    public String getName() {
        return name.get();
    }

    public Map<Categorie, Set<Plat>> getCarte() {
        return carte;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isUse() {
        return use.get();
    }

    public BooleanProperty useProperty() {
        return use;
    }

    public void setUse(boolean use) {
        this.use.set(use);
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public Map<Categorie, Set<Plat>> getCompo() {
        return carte;
    }

    public Set<Formule> getFormules() {
        return formules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return Objects.equals(name, carte.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
