package ihm.tydrichova.upmc.fr.ihmclient.model;
/*
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
*/
public class Categorie implements IElement, Comparable{
    private String name;

    public Categorie(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public int compareTo(Object o) {
        return getName().compareTo(((Categorie)o).getName());
    }
}
