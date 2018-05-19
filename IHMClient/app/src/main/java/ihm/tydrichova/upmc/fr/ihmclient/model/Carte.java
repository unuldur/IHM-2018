package ihm.tydrichova.upmc.fr.ihmclient.model;
/*
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
*/
import java.util.*;

public class Carte implements ICompoPlat{
    private String name;
    private Boolean use;
    private Map<Categorie, Set<Plat>> carte = new TreeMap<>();
    private Set<Formule> formules = new TreeSet<>();

    public Carte(String name) {
        this.name = name;
        this.use = false;
    }

    public String getName() {
        return name;
    }

    public Map<Categorie, Set<Plat>> getCarte() {
        return carte;
    }
    /*
    public StringProperty nameProperty() {
        return name;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUse() {
        return use;
    }

   public void setUse(boolean use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return name;
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