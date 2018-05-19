package ihm.tydrichova.upmc.fr.ihmclient.model;

/*
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
*/
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plat implements IElement, Comparable, Parcelable{
    String name;
    float prix;
    String description;
    Map<Ingredient, Boolean> ingredients;
    Boolean spec;
    float note;

    public Plat(String name, float prix, String description, Map<Ingredient, Boolean> ingredients, boolean spec) {
        this.name = name;
        this.prix = prix;
        this.description = description;
        this.ingredients = ingredients;
        this.spec = spec;
        note = 0;
    }

    public Plat(String name, float prix, String description, Map<Ingredient, Boolean> ingredients, boolean spec, float note) {
        this(name, prix, description, ingredients, spec);
        this.note = note;
    }

    public Plat(Parcel in){
        this.name = in.readString();
        this.prix = in.readFloat();
        this.description = in.readString();

        this.ingredients = new HashMap<>();
        int size = in.readInt();

        for (int i = 0; i < size; i++) {
            Ingredient key = (Ingredient) in.readValue(Ingredient.class.getClassLoader());
            Boolean value = in.readByte() != 0;
            ingredients.put(key, value);
        }
        this.spec = in.readByte() != 0;
        this.note = in.readFloat();
    }


    public String getName() {
        return name;
    }
    /*
    public StringProperty nameProperty() {
        return name;
    }
    */
    public void setName(String name) {
        this.name = name;
    }

    public float getPrix() {
        return prix;
    }



    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Ingredient, Boolean> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, Boolean> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isSpec() {
        return spec;
    }

    public void setSpec(boolean spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Plat)o).getName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeFloat(this.prix);
        dest.writeString(this.description);
        dest.writeInt(ingredients.size());
        for(Map.Entry<Ingredient,Boolean> entry : ingredients.entrySet()){
            dest.writeValue(entry.getKey());
            dest.writeByte((byte) (entry.getValue() ? 1 : 0));
        }

        dest.writeByte((byte) (this.spec ? 1 : 0));
        dest.writeFloat(note);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Plat createFromParcel(Parcel in) {
            return new Plat(in);
        }

        public Plat[] newArray(int size) {
            return new Plat[size];
        }
    };

    public float getNote() {
        return note;
    }
}