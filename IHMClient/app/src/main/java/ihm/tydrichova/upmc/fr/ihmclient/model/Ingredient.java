package ihm.tydrichova.upmc.fr.ihmclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
*/
public class Ingredient {
    String name;
    //ObservableList<Allergene> allergenes;
    Collection<Allergene> allergenes;
    Type type;

    public Ingredient(String name, Set<Allergene> allergenes, Type type) {
        this.name = name;
        this.allergenes = new ArrayList(allergenes);
        this.type = type;
    }


    public String getName() {
        return name;
    }
    /*
    public StringProperty nameProperty() {
        return name;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Allergene> getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(Collection<Allergene> allergenes) {
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
        return name;
    }

    public void change(Ingredient i){
        name = i.getName();
        allergenes = i.getAllergenes();
        type = i.getType();
    }


}